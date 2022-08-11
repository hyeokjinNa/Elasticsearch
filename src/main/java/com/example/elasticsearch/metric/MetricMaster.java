package com.example.elasticsearch.metric;

public interface MetricMaster {
	String cpuUsage = "cpuUsage";
	String memoryMaxSpace = "memoryMaxSpace";
	String memoryUsedSpace = "memoryUsedSpace";
	String diskTotalSpace = "diskTotalSpace";
	String diskUsedSpace = "diskUsedSpace";
	String timestamp = "@timestamp";
	String count = "count";
	String extractError = "extractError";
	String collectError = "collectError";
	String memoryTotalSpace = "memoryTotalSpace";
	String memoryFreeSpace = "memoryFreeSpace";
	String serverId = "serverId";
}
