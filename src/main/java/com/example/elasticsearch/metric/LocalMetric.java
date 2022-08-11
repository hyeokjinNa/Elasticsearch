package com.example.elasticsearch.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalMetric {

	private double cpuUsage;
	
	@JsonProperty("@timestamp")
	private long timestamp;
	
	private long memoryMaxSpace;
	private long memoryUsedSpace;
	private long diskTotalSpace;
	private long diskUsedSpace;
	private long memoryTotalSpace;
	private long memoryFreeSpace;
	
	
	private int count;
	private String extractError;
	private String collectError;
	
	public LocalMetric() {
		this.timestamp = System.currentTimeMillis();
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public long getMemoryMaxSpace() {
		return memoryMaxSpace;
	}

	public void setMemoryMaxSpace(long memoryMaxSpace) {
		this.memoryMaxSpace = memoryMaxSpace;
	}

	public long getMemoryUsedSpace() {
		return memoryUsedSpace;
	}

	public void setMemoryUsedSpace(long memoryUsedSpace) {
		this.memoryUsedSpace = memoryUsedSpace;
	}

	public long getDiskTotalSpace() {
		return diskTotalSpace;
	}

	public void setDiskTotalSpace(long diskTotalSpace) {
		this.diskTotalSpace = diskTotalSpace;
	}

	public long getDiskUsedSpace() {
		return diskUsedSpace;
	}

	public void setDiskUsedSpace(long diskUsedSpace) {
		this.diskUsedSpace = diskUsedSpace;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getExtractError() {
		return extractError;
	}

	public void setExtractError(String extractError) {
		this.extractError = extractError;
	}

	public String getCollectError() {
		return collectError;
	}

	public void setCollectError(String collectError) {
		this.collectError = collectError;
	}

	public long getMemoryTotalSpace() {
		return memoryTotalSpace;
	}

	public void setMemoryTotalSpace(long memoryTotalSpace) {
		this.memoryTotalSpace = memoryTotalSpace;
	}

	public long getMemoryFreeSpace() {
		return memoryFreeSpace;
	}

	public void setMemoryFreeSpace(long memoryFreeSpace) {
		this.memoryFreeSpace = memoryFreeSpace;
	}
	
}
