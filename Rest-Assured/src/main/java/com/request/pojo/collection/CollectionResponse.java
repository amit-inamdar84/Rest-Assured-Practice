package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponse extends CollectionBase {
	List<FolderResponse> item;

	public CollectionResponse() {

	}

	public CollectionResponse(Info info, List<FolderResponse> item) {
		super(info);
		this.item = item;
	}

	public List<FolderResponse> getItem() {
		return item;
	}

	public void setItem(List<FolderResponse> item) {
		this.item = item;
	}

}
