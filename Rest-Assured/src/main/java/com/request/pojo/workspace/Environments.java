package com.request.pojo.workspace;

public class Environments {

	private String id;
	private String name;
	private String uid;
	
	public Environments() {
		
	}
	
	public Environments(String id, String name, String uid) {
		this.id = id;
		this.name = name;
		this.uid = uid;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
