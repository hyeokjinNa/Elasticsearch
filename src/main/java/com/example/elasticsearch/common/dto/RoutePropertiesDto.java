package com.example.elasticsearch.common.dto;

import org.apache.commons.lang3.StringUtils;

import com.example.elasticsearch.common.StringKeyMap;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "route")
public class RoutePropertiesDto extends RouteProperties {

	private StringKeyMap params = new StringKeyMap();

	@JsonAnySetter
	public void add(String key, Object value) {

		if (value == null) {
			value = StringUtils.EMPTY;
		}

		params.put(key, value);
	}

	@JsonAnyGetter
	public StringKeyMap getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "RoutePropertiesDto [params=" + params + ", templateId=" + templateId + ", routeId=" + routeId + ", used=" + used + "]";
	}

}
