package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRequest extends CollectionBase {
	List<FolderRequest> item;

	public CollectionRequest() {

	}

	public CollectionRequest(Info info, List<FolderRequest> item) {
		super(info);
		this.item = item;
	}

	public List<FolderRequest> getItem() {
		return item;
	}

	public void setItem(List<FolderRequest> item) {
		this.item = item;
	}

}
