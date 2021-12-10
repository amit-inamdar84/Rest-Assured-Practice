package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponseNoFolder extends CollectionBase {
	List<ItemResponse> item;

	public CollectionResponseNoFolder() {

	}

	public CollectionResponseNoFolder(Info info, List<ItemResponse> item) {
		super(info);
		this.item = item;
	}
	
	public List<ItemResponse> getItem() {
		return item;
	}

	public void setItem(List<ItemResponse> item) {
		this.item = item;
	}

}
