package com.request.pojo.workspace;

import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//Included jackson annotation in this file as it is difficult to create another test for small concepts.
//@JsonInclude will work during serialization.
//@JsonIgnore will work during serialization and deserialization. It can be used with variable or getter, setter method.
//@JsonIgnoreProperties - Have option to select either serialization or deserialization. Class level Format is as below: 
//@JsonIgnoreProperties(value={"id"}, allowSetters=true) or @JsonIgnoreProperties(value="id", allowGetters=true) 
//where id is private class variable that we want to ignore. Multiple variables can be set here.
//For serialization, set allowSetters=true; for deserialization set allowGetters=true.

@JsonInclude(Include.NON_NULL)//This Jackson annotation will help exclude null values in request body. String id in this case.
//We can set it at the class level or variable level.
public class Workspace {
	@JsonInclude(Include.NON_DEFAULT)//Exclude variables that have default value in request body.
	private int i;
	@JsonInclude(Include.NON_EMPTY)//Exclude non empty. NULL is subset of Empty.
	private HashMap<String, String> myMap;
	private String id;
	private String name;
	private String type;
	private String description;
	
	public Workspace() {
		
	}

	public Workspace(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
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
	
	public HashMap<String, String> getMyMap() {
		return myMap;
	}

	public void setMyMap(HashMap<String, String> myMap) {
		this.myMap = myMap;
	}
}
