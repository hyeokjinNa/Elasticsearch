package com.example.elasticsearch.common.dto;

import com.example.elasticsearch.common.StringKeyMap;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "agent")
public class AgentPropertiesDto extends AgentProperties {
	
	private StringKeyMap params = new StringKeyMap();
	
	@JsonAnySetter 
	public void add(String key, Object value) {
		params.put(key, value);
	}

	@JsonAnyGetter
	public StringKeyMap getParams() {
	    return params;
	}

	@Override
	public String toString() {
		return "AgentPropertiesDto [params=" + params + ", agentId=" + agentId + ", serverId=" + serverId + ", used="
				+ used + "]";
	}
	
}
