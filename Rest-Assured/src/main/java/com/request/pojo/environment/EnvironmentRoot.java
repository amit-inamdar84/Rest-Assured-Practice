package com.request.pojo.environment;

public class EnvironmentRoot {
	private Environment environment;

	public EnvironmentRoot() {
		
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public EnvironmentRoot(Environment environment) {
		this.environment = environment;
	}

}
