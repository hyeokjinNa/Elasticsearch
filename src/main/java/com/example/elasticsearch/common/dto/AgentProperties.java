package com.example.elasticsearch.common.dto;

import com.example.elasticsearch.common.BooleanYnDeserializer;
import com.example.elasticsearch.common.BooleanYnSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "agent")
public class AgentProperties {

	@JacksonXmlProperty(isAttribute = true)
	protected String agentId;
	
	@JacksonXmlProperty(isAttribute = true)
	protected String serverId;
	
	@JsonSerialize(using = BooleanYnSerializer.class)
	@JsonDeserialize(using = BooleanYnDeserializer.class)
	@JacksonXmlProperty(localName = "useYn", isAttribute = true)
	protected boolean used;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "AgentProperties [agentId=" + agentId + ", serverId=" + serverId + ", used=" + used + "]";
	}
	
}
