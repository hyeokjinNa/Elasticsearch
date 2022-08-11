package com.example.elasticsearch.route;

import org.apache.camel.model.dataformat.JsonLibrary;

import com.example.elasticsearch.config.RouteTemplate;
import com.example.elasticsearch.metric.LocalMetricCollector;

public class ElasticsearchRouteTemplate extends RouteTemplate<ElasticsearchRouteProperties> {

	public static final String TEMPLATE_ID = "Elasticsearch";
	
	public ElasticsearchRouteTemplate(ElasticsearchRouteProperties props) {
		super(props);
		
	}
	
	@Override
	public void configure() throws Exception {
		from(timer(props.getRouteId()).period(props.getPeriodMs())).routeId(props.getRouteId())
		.log(props.getRouteId()+" START - ${date:now:yyyy.MM.dd HH:mm:ss}")
		.process(LocalMetricCollector.NAME)
		.marshal().json(JsonLibrary.Jackson)
		.to(elasticsearchRest(props.getRouteId())
				.operation(props.getElasticOperation())
				.hostAddresses(props.getElasticUrl())
				.indexName(props.getElasticIndex()))
		.log(props.getRouteId()+" END - ${date:now:yyyy.MM.dd HH:mm:ss}")
		.end();
		
	}
	
}
