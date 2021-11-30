package com.rest;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
//Run this test using testng.xml to get APIKey parameter.
public class AutomatePost {
//https://documenter.getpostman.com/view/631643/JsLs/ - Documentation of all postman API's
	
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
		expectContentType(ContentType.JSON).
		log(LogDetail.ALL);//This is not working.
		RestAssured.responseSpecification = responseSpecBuilder.build();
	}
	
	//@Test
	public void validate_post_request(){
		String payload = "{\n" +
            "\"workspace\": {\n" +
            "\"name\": \"Payload workspace\",\n" +
            "\"type\": \"personal\", \n" +
            "\"description\": \"Rest assured practice\" \n" +
            "}\n" +
"}";

		given().
		        body(payload).
		when().
		        post("/workspaces").
		then().
		        log().all().
		        assertThat().
		        body("workspace.name", equalTo("Payload workspace"),
		        		"workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
		        
	
	}
	
	//@Test
	public void validate_post_request_non_bdd_style(){//Passing Payload using json file
		File file = new File("src/main/resources/payload/CreateWorkspacePayload.json");
		
		Response response = given().
		body(file).
		post("/workspaces");
		
		assertThat(response.<String>path("workspace.name"), equalTo("PTP"));
		assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
	}
	
	@Test
	public void validate_post_request_hasMapPayload(){//Passing Payload using Hash Map.
		HashMap<String,Object> mainObject = new HashMap<String,Object>();
		//Need jackson databind library as a dependency to convert hash map into JSON object i.e. serialization.
		//Here we are using Jackson under the hood. To use it as an external library and
		//for actual serialization examples refer: JacksonToJson.java
		HashMap<String,String> nestedObject = new HashMap<String,String>();
		nestedObject.put("name", "Pay1");
		nestedObject.put("type", "personal");
		nestedObject.put("description", "Pay1");
		
		mainObject.put("workspace", nestedObject);
		
		given().
                body(mainObject).
        when().
                post("/workspaces").
        then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("Pay1"),
        		"workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
	}

}
