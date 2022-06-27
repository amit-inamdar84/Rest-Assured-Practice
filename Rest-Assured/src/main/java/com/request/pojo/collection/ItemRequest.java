package com.request.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequest extends ItemBase{
    RequestRequest request;
    
	public ItemRequest() {

	}

	public ItemRequest(String name, RequestRequest request) {
		super(name);
		this.request = request;
	}
	
	public RequestRequest getRequest() {
		return request;
	}

	public void setRequest(RequestRequest request) {
		this.request = request;
	}

}
