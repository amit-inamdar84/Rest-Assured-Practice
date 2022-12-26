package com.request.pojo.monitor;

public class Monitor {

	private String name;
	private Schedule schedule;
	private String collection;
	private String environment;
	
	public Monitor() {

	}
	
	public Monitor(String name, Schedule schedule, String collection, String environment) {
		this.name = name;
		this.schedule = schedule;
		this.collection = collection;
		this.environment = environment;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
		
}
