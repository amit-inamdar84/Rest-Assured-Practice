package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderRequest extends FolderBase {
	List<ItemRequest> item;

	public FolderRequest() {

	}

	public FolderRequest(String name, List<ItemRequest> item) {
		super(name);
		this.item = item;
	}

	public List<ItemRequest> getItem() {
		return item;
	}

	public void setItem(List<ItemRequest> item) {
		this.item = item;
	}

}
