package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResponse extends FolderBase {
	List<ItemResponse> item;

	public FolderResponse() {

	}

	public FolderResponse(String name, List<ItemResponse> item) {
		super(name);
		this.item = item;
	}

	public List<ItemResponse> getItem() {
		return item;
	}

	public void setItem(List<ItemResponse> item) {
		this.item = item;
	}

}
