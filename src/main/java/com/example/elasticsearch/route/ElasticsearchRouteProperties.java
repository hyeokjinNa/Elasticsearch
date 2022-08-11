package com.example.elasticsearch.route;

import com.example.elasticsearch.common.dto.RouteProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "route")
public class ElasticsearchRouteProperties extends RouteProperties {

	private String elasticOperation;
	private String elasticUrl;
	private String elasticIndex;
	private long periodMs;
	
	public String getElasticOperation() {
		return elasticOperation;
	}
	public void setElasticOperation(String elasticOperation) {
		this.elasticOperation = elasticOperation;
	}
	public String getElasticUrl() {
		return elasticUrl;
	}
	public void setElasticUrl(String elasticUrl) {
		this.elasticUrl = elasticUrl;
	}
	public String getElasticIndex() {
		return elasticIndex;
	}
	public void setElasticIndex(String elasticIndex) {
		this.elasticIndex = elasticIndex;
	}
	public long getPeriodMs() {
		return periodMs;
	}
	public void setPeriodMs(long periodMs) {
		this.periodMs = periodMs;
	}

}
