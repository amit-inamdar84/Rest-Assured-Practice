package com.request.pojo.environment;

import java.util.List;

public class Environment {
	private String name;
	private List<Variables> values;
	
	public Environment() {
		
	}
	
	public Environment(String name, List<Variables> values) {
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Variables> getValues() {
		return values;
	}

	public void setValues(List<Variables> values) {
		this.values = values;
	}

}
