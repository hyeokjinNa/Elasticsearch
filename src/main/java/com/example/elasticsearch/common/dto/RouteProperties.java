package com.example.elasticsearch.common.dto;

import com.example.elasticsearch.common.BooleanYnDeserializer;
import com.example.elasticsearch.common.BooleanYnSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "route")
public class RouteProperties {

	@JacksonXmlProperty(isAttribute = true)
	protected String templateId;
	
	@JacksonXmlProperty(isAttribute = true)
	protected String routeId;
	
	@JsonSerialize(using = BooleanYnSerializer.class)
	@JsonDeserialize(using = BooleanYnDeserializer.class)
	@JacksonXmlProperty(localName = "useYn", isAttribute = true)
	protected boolean used;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "RouteProperties [templateId=" + templateId + ", routeId=" + routeId + ", used=" + used + "]";
	}
	
}
