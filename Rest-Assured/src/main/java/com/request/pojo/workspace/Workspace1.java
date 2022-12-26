package com.request.pojo.workspace;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)//This Jackson annotation will help exclude null values in request body. String id in this case.
//We can set it at the class level or variable level.
@JsonIgnoreProperties(value={"type", "description", "collections", "environments", "mocks", "monitors"}, allowGetters=true)
public class Workspace1 {
	@JsonInclude(Include.NON_DEFAULT)//Exclude variables that have default value in request body.
	private int i;
	private String id;
	private String name;
	private String type;
	private String description;
	private List<Collections> collections;
	private List<Environments> environments;
	private List<Mocks> mocks;
	private List<Monitors> monitors;
	
	public Workspace1() {
		
	}

	public Workspace1(String name, String type, String description, List<Collections> collections, List<Environments> environments, List<Mocks> mocks, List<Monitors> monitors) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.collections = collections;
		this.environments = environments;
		this.mocks = mocks;
		this.monitors = monitors;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Collections> getCollections() {
		return collections;
	}

	public void setCollections(List<Collections> collections) {
		this.collections = collections;
	}

	public List<Environments> getEnvironments() {
		return environments;
	}

	public void setEnvironments(List<Environments> environments) {
		this.environments = environments;
	}

	public List<Mocks> getMocks() {
		return mocks;
	}

	public void setMocks(List<Mocks> mocks) {
		this.mocks = mocks;
	}

	public List<Monitors> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitors> monitors) {
		this.monitors = monitors;
	}
}
