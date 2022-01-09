package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
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
import com.request.pojo.collection.CollectionRequestNoFolder;
import com.request.pojo.collection.CollectionRootRequest;
import com.request.pojo.collection.CollectionRootRequestNoFolder;
import com.request.pojo.collection.CollectionRootResponse;
import com.request.pojo.collection.CollectionRootResponseNoFolder;
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
//Tests for different combinations of create collection json - Assignment
//Collection with empty folder.
//Collection with multiple folders.
//Collection with one folder and multiple requests.
//Collection with only one request and without any folder.
//Collection with a request with multiple headers.

public class ComplexPojo3 {

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
	
	//@Test
	public void create_empty_collection() throws JsonProcessingException, JSONException {
		List<FolderRequest> folderList = new ArrayList<FolderRequest>();
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
		    assertThat(objectMapper.readTree(actual), equalTo(objectMapper.readTree(expected)));
	}
	
	//@Test
	public void create_multiple_folder_collection() throws JsonProcessingException, JSONException {
		//Adding folder1
		Header header1 = new Header("Content-Type", "application/json");
		List<Header> headerList1 = new ArrayList<Header>();
		headerList1.add(header1);
		Body body1 = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request1 = new RequestRequest("https://postman-echo.com/post", "POST", headerList1, body1, "This is a sample POST Request");
		ItemRequest itemRequest1 = new ItemRequest("Sample POST Request", request1);
		List<ItemRequest> requestList1 = new ArrayList<ItemRequest>();
		requestList1.add(itemRequest1);
		FolderRequest folder1 = new FolderRequest("This is a folder1", requestList1);
		
		//Adding folder2
		Header header2 = new Header("Content-Type", "text/plain");
		List<Header> headerList2 = new ArrayList<Header>();
		headerList2.add(header2);
		Body body2 = new Body("raw", "Duis posuere augue vel cursus pharetra. In luctus a ex nec pretium. Praesent neque quam, tincidunt nec leo eget, rutrum vehicula magna.Maecenas consequat elementum elit, id semper sem tristique et. Integer pulvinar enim quis consectetur interdum volutpat.");
		RequestRequest request2 = new RequestRequest("https://postman-echo.com/post", "POST", headerList2, body2, "This is a sample POST Request");
		ItemRequest itemRequest2 = new ItemRequest("Sample POST Request", request2);
		List<ItemRequest> requestList2 = new ArrayList<ItemRequest>();
		requestList2.add(itemRequest2);
		FolderRequest folder2 = new FolderRequest("This is a folder2", requestList2);
		
		//Adding folders to list to pass it on to Collection
		List<FolderRequest> folderList = new ArrayList<FolderRequest>();
		folderList.add(folder1);
		folderList.add(folder2);
		
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
		    
		    JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.LENIENT, 
                    new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
						public boolean equal(Object o1, Object o2) {
							return true;//By returning true we are ignoring url field. Need more study on this.
						}		    	                    
					})));
		    
		  //Separately asserting URL field as request URL is string and response URL field is Object.
		    List<String> requestURL = new ArrayList<String>();
		    List<String> responseURL = new ArrayList<String>();
		    
		    for(FolderRequest fetchRequestURL : folderList) {
		    	List<ItemRequest> itemRequest = fetchRequestURL.getItem();
		    	for(ItemRequest item : itemRequest) {
		    		requestURL.add(item.getRequest().getUrl());
		    	}
		    }
		    System.out.println("Request payload URL: "+requestURL);
		    
			
			List<FolderResponse> folderRespList = deserializedGetCallReponse.getCollection().getItem();
			for (FolderResponse folderItems : folderRespList) {
				List<ItemResponse> requestItems = folderItems.getItem();
				for (ItemResponse allItems : requestItems) {
					URL url = allItems.getRequest().getUrl();
					responseURL.add(url.getRaw());
					System.out.println(url.getRaw());
				}
			}
			assertThat(responseURL, containsInAnyOrder(requestURL.toArray()));

	}
	
	//@Test
	public void create_collection_singleFolder_Multiple_Requests() throws JsonProcessingException, JSONException {
		Header header = new Header("Content-Type", "application/json");
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(header);
		Body body = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");
		ItemRequest itemRequest = new ItemRequest("Sample POST Request", request);
		
		Header header1 = new Header("Content-Type", "application/json");
		List<Header> headerList1 = new ArrayList<Header>();
		headerList1.add(header1);
		Body body1 = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request1 = new RequestRequest("https://postman-echo.com/post", "POST", headerList1, body1, "This is another sample POST Request");
		ItemRequest itemRequest1 = new ItemRequest("Sample POST Request", request1);
		
		List<ItemRequest> requestList = new ArrayList<ItemRequest>();
		requestList.add(itemRequest);
		requestList.add(itemRequest1);
		
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
		    //Assertion is failing here due to multiple requests in one folder. Need to check.
		    //Later it worked when STRICT_ORDER is used instead of LENIENT.
		    JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.STRICT_ORDER, 
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
	
	//@Test
	public void create_collection_noFolder_singleRequest() throws JsonProcessingException, JSONException {
		Header header = new Header("Content-Type", "application/json");
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(header);
		Body body = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");
		ItemRequest itemRequest = new ItemRequest("Sample POST Request", request);
		List<ItemRequest> requestList = new ArrayList<ItemRequest>();
		requestList.add(itemRequest);
			
		Info info = new Info("Sample Collection 909", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
		CollectionRequestNoFolder collection = new CollectionRequestNoFolder(info, requestList);
		CollectionRootRequestNoFolder collectionRoot = new CollectionRootRequestNoFolder(collection);
		
		    String collectionUID = given().//POSt call
	                body(collectionRoot).
	        when().
	                post("/collections").
	        then().
	                log().all().
	                extract().
	                response().path("collection.uid");
		    
		    CollectionRootResponseNoFolder deserializedGetCallReponse = given().//Get call using ID extracted from POST
		            pathParam("collectionUID", collectionUID).
		    when().
		            get("/collections/{collectionUID}").
		    then().
		           log().all().
		           extract().
                   response().
                   as(CollectionRootResponseNoFolder.class);
		    
		    ObjectMapper objectMapper = new ObjectMapper();
		    String expected = objectMapper.writeValueAsString(collectionRoot);
		    String actual = objectMapper.writeValueAsString(deserializedGetCallReponse);
		    //New dependency for creating new library for full json assertion. Here we have ignore URL field validation.
		    JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.LENIENT, 
                                    new Customization("collection.item[*].request.url", new ValueMatcher<Object>() {
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
		    
		    List<ItemResponse> reqRespList = deserializedGetCallReponse.getCollection().getItem();
		    for(ItemResponse reqItems : reqRespList) {
		    	 URL url = reqItems.getRequest().getUrl();
		    		responseURL.add(url.getRaw());
		    		System.out.println(url.getRaw());
		    	}
		    assertThat(responseURL, containsInAnyOrder(requestURL.toArray()));	
	}
	
	//@Test
	public void create_collection_noFolder_singleRequest_multipleHeaders() throws JsonProcessingException, JSONException {
		Header header = new Header("Content-Type", "application/json");
		Header header1 = new Header("Connection", "Close");
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(header);
		headerList.add(header1);
		Body body = new Body("raw", "{\"data\": \"123\"}");
		RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");
		ItemRequest itemRequest = new ItemRequest("Sample POST Request", request);
		List<ItemRequest> requestList = new ArrayList<ItemRequest>();
		requestList.add(itemRequest);
			
		Info info = new Info("Sample Collection 909", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
		CollectionRequestNoFolder collection = new CollectionRequestNoFolder(info, requestList);
		CollectionRootRequestNoFolder collectionRoot = new CollectionRootRequestNoFolder(collection);
		
		    String collectionUID = given().//POSt call
	                body(collectionRoot).
	        when().
	                post("/collections").
	        then().
	                log().all().
	                extract().
	                response().path("collection.uid");
		    
		    CollectionRootResponseNoFolder deserializedGetCallReponse = given().//Get call using ID extracted from POST
		            pathParam("collectionUID", collectionUID).
		    when().
		            get("/collections/{collectionUID}").
		    then().
		           log().all().
		           extract().
                   response().
                   as(CollectionRootResponseNoFolder.class);
		    
		    ObjectMapper objectMapper = new ObjectMapper();
		    String expected = objectMapper.writeValueAsString(collectionRoot);
		    String actual = objectMapper.writeValueAsString(deserializedGetCallReponse);
		    //New dependency for creating new library for full json assertion. Here we have ignore URL field validation.
		    JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.LENIENT, 
                                    new Customization("collection.item[*].request.url", new ValueMatcher<Object>() {
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
		    
		    List<ItemResponse> reqRespList = deserializedGetCallReponse.getCollection().getItem();
		    for(ItemResponse reqItems : reqRespList) {
		    	 URL url = reqItems.getRequest().getUrl();
		    		responseURL.add(url.getRaw());
		    		System.out.println(url.getRaw());
		    	}
		    assertThat(responseURL, containsInAnyOrder(requestURL.toArray()));	
	}
	
	@Test
	public void test11() {
		Header header = new Header("Content-Type", "application/json");
		List<String> headerList = new ArrayList<String>();
		headerList.add(header.getKey());
	}
}
