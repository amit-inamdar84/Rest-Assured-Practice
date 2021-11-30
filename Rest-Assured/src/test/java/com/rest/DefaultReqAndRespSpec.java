package com.rest;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

//Both request and response spec builder are not working. Need to fix this later.
public class DefaultReqAndRespSpec {
	
	@BeforeClass()
	public void beforeClass(){
		//
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("https://api.postman.com");
		requestSpecBuilder.addHeader("X-Api-Key", "APIKey");
		requestSpecBuilder.log(LogDetail.ALL);
		
		RestAssured.requestSpecification = requestSpecBuilder.build();
	    
	    //Instead of below commented code, we can use ResponseSpecBuilder. logging works with this method.
	    /*responseSpecification = RestAssured.expect().
	    		statusCode(200).
	    		contentType(ContentType.JSON);*/
	    System.out.println("Before----------------");
	    RestAssured.responseSpecification = new ResponseSpecBuilder().
	    		expectStatusCode(201).
	    		expectContentType(ContentType.JSON).
	    		log(LogDetail.ALL).
	    		build();
	    System.out.println("After----------------");
	    
	    //Instead of responseSpecification reference we can use static variable as mentioned below. This will avoid using instance variable - ResponseSpecification responseSpecification;
	    //responseSpecification = responseSpecBuilder.build();
	   // RestAssured.responseSpecification = responseSpecBuilder.build();//This is called defaultrespspec. Its value will retain throughout the program.
	    System.out.println("Later----------------");
	}

	@Test
	public void validate_status_code(){
		        //get("/workspaces/");
				//then().spec(responseSpecification).
		System.out.println("-----------------------------------------------------------------------");
	}
	
	//@Test
	public void validate_resp_body(){/*
		Response reponse = requestSpecification.get("/workspaces/").
				then().
				log().all().
				contentType(ContentType.JSON).
				extract().
				response();
		Assert.assertEquals(reponse.path("workspaces[0].name").toString(), "Test2");
	*/}


}
