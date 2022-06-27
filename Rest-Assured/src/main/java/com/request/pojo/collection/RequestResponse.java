package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse extends RequestBase{
	URL url;

	public RequestResponse() {
		
	}
	
	public RequestResponse(URL url, String method, List<Header> header, Body body, String description) {
		super(method, header, body, description);
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
