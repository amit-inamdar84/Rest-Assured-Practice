package com.request.pojo.monitor;

public class MonitorRoot {
	private Monitor monitor;
	
	public MonitorRoot() {
		
	}
	
	public MonitorRoot(Monitor monitor) {
		this.monitor = monitor;
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

}
