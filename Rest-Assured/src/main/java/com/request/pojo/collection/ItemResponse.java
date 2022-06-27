package com.request.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse extends ItemBase{
    RequestResponse request;
    
	public ItemResponse() {

	}

	public ItemResponse(String name, RequestResponse request) {
		super(name);
		this.request = request;
	}
	
	public RequestResponse getRequest() {
		return request;
	}

	public void setRequest(RequestResponse request) {
		this.request = request;
	}

}
