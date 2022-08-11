package com.example.elasticsearch.route;

import org.springframework.stereotype.Component;

import com.example.elasticsearch.config.XmlRouteConstructor;

@Component
public class ElasticsearchRouteConstructor extends XmlRouteConstructor<ElasticsearchRouteProperties, ElasticsearchRouteTemplate> {

	public static final String ROUTE_ID = ElasticsearchRouteConstructor.class.getCanonicalName();
	
	public ElasticsearchRouteConstructor() {
		super(ElasticsearchRouteProperties.class, ElasticsearchRouteTemplate.class, ROUTE_ID, ElasticsearchRouteTemplate.TEMPLATE_ID);
	}
	
	@Override
	protected ElasticsearchRouteTemplate constructRouteTemplate(ElasticsearchRouteProperties p) {
		return new ElasticsearchRouteTemplate(p);
	}
	
}
