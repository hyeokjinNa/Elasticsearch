package com.example.elasticsearch.config;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.file.watch.constants.FileEventEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.elasticsearch.common.dto.RouteProperties;



public abstract class XmlRouteConstructor<P extends RouteProperties, R extends RouteTemplate<P>> extends EndpointRouteBuilder {
	
	public static final String MARSHAL_XML_PREFIX = "marshal-jacksonxml-";
	
	@Autowired
	protected AgentBasicProperties agentProps;
	
	protected String templateId;
	protected String constructorRouteId;
	
	protected final Class<P> routePropsClass;
	protected final Class<R> routeTemplateClass;
	
	public XmlRouteConstructor(Class<P> routePropsClass, Class<R> routeTemplateClass, String constructorRouteId, String templateId) {
		this.routePropsClass = routePropsClass;
		this.routeTemplateClass = routeTemplateClass;
		this.constructorRouteId = constructorRouteId;
		this.templateId = templateId;
	}
	
	@Override
	public void configure() throws Exception {
		
		from(file(agentProps.getRouteDir() + File.separator + templateId)
			.antInclude("*.xml").noop(true).initialDelay(3000).delay(3000))
			.routeId(constructorRouteId)
		.log("Read file: ${header.CamelFileAbsolutePath}")
		.unmarshal().jacksonxml(routePropsClass)
		.process(exchange -> {
			P props = exchange.getMessage().getBody(routePropsClass);
			if (props.isUsed()) {
				getContext().addRoutes(constructRouteTemplate(props));
			}
			log.info("Created a Route. {}", props);
		});
		
		from(fileWatch(agentProps.getRouteDir() + File.separator + templateId).antInclude("*.xml"))
	    .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
	    .process(exchange -> {
	    	FileEventEnum eventType = exchange.getMessage().getHeader("CamelFileEventType", FileEventEnum.class);
	    	
	    	if (eventType == FileEventEnum.CREATE) {
	    		exchange.setProperty("Operation", "CREATE");
	    	} else if (eventType == FileEventEnum.MODIFY) { 
	    		exchange.setProperty("Operation", "MODIFY");
	    	} else if (eventType == FileEventEnum.DELETE) { 
	    		exchange.setProperty("Operation", "DELETE"); // 수동으로 파일을 삭제했을 때.
	    	}
	    })
	    
	    .choice()
			.when(simple("${exchangeProperty.Operation} == 'DELETE'"))
				.process(exchange -> {
					String fileName = (String) exchange.getIn().getHeader("CamelFileName"); // ex. VRF_BL01M_001.xml
					String routeId = StringUtils.substring(fileName, 0, fileName.lastIndexOf("."));
					
					getContext().getRouteController().stopRoute(routeId);
					boolean result = getContext().removeRoute(routeId);
					log.info("Delete Route. (RouteId = {}, result = {})", routeId, result);
				})
			.when(simple("${exchangeProperty.Operation} == 'MODIFY'"))
				.unmarshal().jacksonxml(routePropsClass)
			    .process(exchange -> {
					P props = exchange.getMessage().getBody(routePropsClass);
					getContext().getRouteController().stopRoute(props.getRouteId());
					if (props.isUsed()) {
						getContext().addRoutes(constructRouteTemplate(props));
					} else {
						getContext().getRouteController().stopRoute(props.getRouteId());
						boolean result = getContext().removeRoute(props.getRouteId());
						log.info("Delete Route. (RouteId = {}, result = {})", props.getRouteId(), result);
						if (result == true) {
							exchange.setProperty("RouteId", props.getRouteId());
							exchange.setProperty("IsDelete", true);
							exchange.setProperty("RouteDirectoryPath", agentProps.getRouteDir());
						}
					}
					log.info("Modified a Route. (RouteTemplateId = {}, RouteId = {})", props.getTemplateId(), props.getRouteId());
				})
			    
			    .filter()
			    	// 삭제한 route파일은 해당 라우트 파일 하위 delete폴더에 저장
			    	.simple("${exchangeProperty.IsDelete} == true")
			    		.process(exchange -> {
			    			File file = new File(agentProps.getRouteDir() + File.separator + templateId + File.separator + exchange.getProperty("RouteId") + ".xml");
			    			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			    			String deletedFolderPath = agentProps.getRouteDir() + File.separator + templateId + File.separator + "delete" + File.separator;
			    			String deletedFileName = exchange.getProperty("RouteId") + ".xml." + date;
			    			
			    			File deletedFolder = new File(deletedFolderPath);
			    			File deletedFile = new File(deletedFolderPath + deletedFileName);
			    			if (!deletedFolder.exists())
			    				deletedFolder.mkdirs();
			    			
			    			file.renameTo(deletedFile);
			    			
			    			log.info("Transfer Deleted Route xml file successfully. [ location = " + deletedFile.getAbsolutePath() + "]");
			    		})
		    	.end()
		.end();
		
		from(direct(MARSHAL_XML_PREFIX + templateId)).routeId(MARSHAL_XML_PREFIX + templateId)
		.marshal().jacksonxml(true)
		.log("Marshal to " + templateId + " RouteTemplate XML Config:\n${body}");
		//.unmarshal().json(JsonLibrary.Jackson, routePropsClass);
		
	}
	
	protected abstract R constructRouteTemplate(P p);

}
