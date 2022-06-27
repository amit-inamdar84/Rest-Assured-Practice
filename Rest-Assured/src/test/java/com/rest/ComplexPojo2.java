package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.pojo.collection.Body;
import com.request.pojo.collection.CollectionRequest;
import com.request.pojo.collection.CollectionRootRequest;
import com.request.pojo.collection.CollectionRootResponse;
import com.request.pojo.collection.FolderRequest;
import com.request.pojo.collection.FolderResponse;
import com.request.pojo.collection.Header;
import com.request.pojo.collection.Info;
import com.request.pojo.collection.ItemRequest;
import com.request.pojo.collection.ItemResponse;
import com.request.pojo.collection.RequestRequest;
import com.request.pojo.collection.URL;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
//Example to create POJO from complex nested json. Pass the POJO as payload to POSt call. Then extract ID from response and pass to
//GET call as path parameter. Assert the GET call response by comparing with POST call payload.
//Challenges: Different data type for URL field in payload and response. Not asserting the URL field. We have ignore it in JSON assert.
//

public class ComplexPojo2 {

	@BeforeClass
	@Parameters({"APIKey"})
	public void beforeClass(String APIKey){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://api.getpostman.com").
		                                        addHeader("X-Api-Key", APIKey).
		                                        setContentType(ContentType.JSON).
		                                        log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder().
		expectStatusCode(200).
		expectContentType(ContentType.JSON);
		RestAssured.responseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void create_collection() throws JsonProcessingException, JSONException {
		Header header = new Header("Content-Type", "application/json");
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(header);
		Body body = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");
		ItemRequest itemRequest = new ItemRequest("Sample POST Request", request);
		List<ItemRequest> requestList = new ArrayList<ItemRequest>();
		requestList.add(itemRequest);
		FolderRequest folder = new FolderRequest("This is a folder", requestList);
		List<FolderRequest> folderList = new ArrayList<FolderRequest>();
		folderList.add(folder);
		Info info = new Info("Sample Collection 909", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
		CollectionRequest collection = new CollectionRequest(info, folderList);
		CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);
		
		    String collectionUID = given().//POSt call
	                body(collectionRoot).
	        when().
	                post("/collections").
	        then().
	                log().all().
	                extract().
	                response().path("collection.uid");
		    
		    CollectionRootResponse deserializedGetCallReponse = given().//Get call using ID extracted from POST
		            pathParam("collectionUID", collectionUID).
		    when().
		            get("/collections/{collectionUID}").
		    then().
		           log().all().
		           extract().
                   response().
                   as(CollectionRootResponse.class);
		    
		    ObjectMapper objectMapper = new ObjectMapper();
		    String expected = objectMapper.writeValueAsString(collectionRoot);
		    String actual = objectMapper.writeValueAsString(deserializedGetCallReponse);
		    //New dependency for creating new library for full json assertion. Here we have ignore URL field validation.
		    JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.LENIENT, 
                                    new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
										public boolean equal(Object o1, Object o2) {
											return true;//By returning true we are ignoring url field. Need more study on this.
										}		    	                    
									})));
		    
		    //Separately asserting URL field as request URL is string and response URL field is Object.
		    List<String> requestURL = new ArrayList<String>();
		    List<String> responseURL = new ArrayList<String>();
		    
		    for(ItemRequest fetchRequestURL : requestList) {
		    	requestURL.add(fetchRequestURL.getRequest().getUrl());
		    }
		    System.out.println("Request payload URL: "+requestURL);
		    
		    List<FolderResponse> folderRespList = deserializedGetCallReponse.getCollection().getItem();
		    for(FolderResponse folderItems : folderRespList) {
		    	List<ItemResponse> requestItems = folderItems.getItem();
		    	for(ItemResponse allItems : requestItems) {
		    		URL url = allItems.getRequest().getUrl();
		    		responseURL.add(url.getRaw());
		    		System.out.println(url.getRaw());
		    	}
		    }
		    assertThat(responseURL, containsInAnyOrder(requestURL.toArray()));
		
	}

}
