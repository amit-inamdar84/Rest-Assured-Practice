package com.request.pojo.monitor;

public class Schedule {
	private String cron;
	private String timezone;
	
	public Schedule() {
		
	}
	
	public Schedule(String cron, String timezone) {
		this.cron = cron;
		this.timezone = timezone;
	}
	
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
