package com.request.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRootResponseNoFolder extends CollectionRootBase{
	CollectionResponseNoFolder collection;

	public CollectionRootResponseNoFolder() {
		
	}
	
	public CollectionRootResponseNoFolder(CollectionResponseNoFolder collection) {
		this.collection = collection;
	}
	
	public CollectionResponseNoFolder getCollection() {
		return collection;
	}

	public void setCollection(CollectionResponseNoFolder collection) {
		this.collection = collection;
	}
}
