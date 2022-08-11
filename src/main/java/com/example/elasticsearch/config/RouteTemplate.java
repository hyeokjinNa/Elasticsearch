package com.example.elasticsearch.config;

import com.example.elasticsearch.common.dto.RouteProperties;

public abstract class RouteTemplate<P extends RouteProperties> extends RouteTemplateBuilder {

	public static final String TEMPLATE_ID = "Sample";
	
	protected P props;
	
	public RouteTemplate(P props) {
		this.props = props;
	}

}
