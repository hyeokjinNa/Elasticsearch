package com.example.elasticsearch.metric;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(LocalMetricCollector.NAME)
public class LocalMetricCollector implements Processor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String NAME = "LocalMetricCollector";
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		LocalMetric metric = new LocalMetric();
		getCpuUsage(metric);
		getMemoryUsage(metric);
		getDiskUsage(metric, ".");
		getExtractCount(metric);
		
		Map<String, Object> map = new HashMap<>();
		map.put(MetricMaster.cpuUsage, metric.getCpuUsage());
		map.put(MetricMaster.diskTotalSpace, metric.getDiskTotalSpace());
		map.put(MetricMaster.diskUsedSpace, metric.getDiskUsedSpace());
		map.put(MetricMaster.memoryMaxSpace, metric.getMemoryMaxSpace());
		map.put(MetricMaster.memoryUsedSpace, metric.getMemoryUsedSpace());
		map.put(MetricMaster.timestamp, metric.getTimestamp());
		map.put(MetricMaster.count,metric.getCount());
		map.put(MetricMaster.extractError,metric.getExtractError());
		map.put(MetricMaster.collectError,metric.getCollectError());
		map.put(MetricMaster.memoryTotalSpace, metric.getMemoryTotalSpace());
		map.put(MetricMaster.memoryFreeSpace, metric.getMemoryFreeSpace());
		map.put(MetricMaster.serverId, "HJ");
		exchange.getMessage().setBody(map);
	}
	
	private void getMemoryUsage(LocalMetric metric) {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

		long max = memoryMXBean.getHeapMemoryUsage().getMax();
		long used = memoryMXBean.getHeapMemoryUsage().getUsed();
	
		metric.setMemoryMaxSpace(max);
		metric.setMemoryUsedSpace(used);
	}
	
	private void getDiskUsage(LocalMetric metric, String mountDirPath) {
		File mountDir = new File(mountDirPath);

		long total = mountDir.getTotalSpace();
		long free = mountDir.getFreeSpace();
		long used = total - free;
		
		metric.setDiskTotalSpace(total);
		metric.setDiskUsedSpace(used);
	}
	
	private void getCpuUsage(LocalMetric metric) {
		
		double cpuUsage = 0;
		long memoryTotalSpace = 0;
		long memoryFreeSpace = 0;
	
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
			for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
				method.setAccessible(true);
				if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
					try {
						Object value = method.invoke(operatingSystemMXBean);
						log.trace(method.getName() + "=" + value);
						if (method.getName().equals("getSystemCpuLoad")) {
							cpuUsage = (double) value;
						} else if (method.getName().equals("getTotalPhysicalMemorySize")) {
							memoryTotalSpace = (long) value;
						} else if (method.getName().equals("getFreePhysicalMemorySize")) {
							memoryFreeSpace = (long) value;
						}
					} catch (Exception e) {
						log.error("Collect server metric error", e);
					}
				}
			}
			
			metric.setCpuUsage(cpuUsage);
			metric.setMemoryTotalSpace(memoryTotalSpace);
			metric.setMemoryFreeSpace(memoryTotalSpace - memoryFreeSpace);
	}
	
	private void getExtractCount(LocalMetric metric) {
		
		Random ran = new Random();
		
		String[] randomSF = {"N","N","N","N","N","Y","N","N","N","N"};
		
		metric.setCount(ran.nextInt(1000));
		metric.setCollectError(randomSF[ran.nextInt(10)]);
		metric.setExtractError(randomSF[ran.nextInt(10)]);
	}
	
}
