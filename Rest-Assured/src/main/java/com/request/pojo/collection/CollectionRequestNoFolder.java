package com.request.pojo.collection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRequestNoFolder extends CollectionBase {
	List<ItemRequest> item;

	public CollectionRequestNoFolder() {

	}

	public CollectionRequestNoFolder(Info info, List<ItemRequest> item) {
		super(info);
		this.item = item;
	}
	
	public List<ItemRequest> getItem() {
		return item;
	}

	public void setItem(List<ItemRequest> item) {
		this.item = item;
	}

}
