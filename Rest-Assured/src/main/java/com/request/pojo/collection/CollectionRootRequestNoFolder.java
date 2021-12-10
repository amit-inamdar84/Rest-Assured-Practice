package com.request.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRootRequestNoFolder extends CollectionRootBase{
	CollectionRequestNoFolder collection;

	public CollectionRootRequestNoFolder() {
		
	}
	
	public CollectionRootRequestNoFolder(CollectionRequestNoFolder collection) {
		this.collection = collection;
	}
	
	public CollectionRequestNoFolder getCollection() {
		return collection;
	}

	public void setCollection(CollectionRequestNoFolder collection) {
		this.collection = collection;
	}
}
