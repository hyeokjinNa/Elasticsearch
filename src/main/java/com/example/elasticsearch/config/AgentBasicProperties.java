package com.example.elasticsearch.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "elasticsearch.agent")
@Component
public class AgentBasicProperties {


	private String agentId;
	private String homeDir;
	private String routeDir;
	private String routeLogTopic;

	private String mountDir = ".";


	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}

	public String getRouteDir() {
		return routeDir;
	}

	public void setRouteDir(String routeDir) {
		this.routeDir = routeDir;
	}

	public String getRouteLogTopic() {
		return routeLogTopic;
	}

	public void setRouteLogTopic(String routeLogTopic) {
		this.routeLogTopic = routeLogTopic;
	}

	public String getMountDir() {
		return mountDir;
	}

	public void setMountDir(String mountDir) {
		this.mountDir = mountDir;
	}

	public String getKafkaClientId(String groupId, int threadNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("consumer");
		sb.append("-");
		sb.append(StringUtils.replace(groupId, "-", ""));
		sb.append("-");
	
		sb.append("-");
		sb.append(threadNum);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "AgentBasicProperties [agentId=" + agentId + ","
				+ ", homeDir=" + homeDir + ", routeDir=" + routeDir + ", mountDir=" + mountDir + ", routeLogTopic="
				+ routeLogTopic + "]";
	}

}
