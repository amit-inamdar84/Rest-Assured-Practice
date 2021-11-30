package com.rest;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RespSpecBuilder {


	RequestSpecification  requestSpecification;
	ResponseSpecification responseSpecification;
	
	@BeforeClass()
	public void beforeClass() throws IOException{
		//
	    requestSpecification = given().//Instead of given(), we can use with()
				baseUri("https://api.postman.com").
		        header("X-Api-Key", "APIKEY");
	    
	    //Instead of below commented code, we can use ResponseSpecBuilder. logging works with this method.
	    /*responseSpecification = RestAssured.expect().
	    		statusCode(200).
	    		contentType(ContentType.JSON);*/
	    
	    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
	    		expectStatusCode(200).
	    		expectContentType(ContentType.JSON).
	    		log(LogDetail.ALL);
	    
	    responseSpecification = responseSpecBuilder.build();
	}

	@Test
	public void validate_status_code(){
		//no code changes required here when using ResponseSpecBuilder
		        requestSpecification.get("/workspaces/").
				then().spec(responseSpecification);
				//log().all();
				//contentType(ContentType.JSON).
				//extract().
				//response();
	
		//assertThat(reponse.statusCode(), is(equalTo(200)));//Hamcrest assertion
		System.out.println("-----------------------------------------------------------------------");
	}
	
	@Test
	public void validate_resp_body(){
		Response reponse = requestSpecification.get("/workspaces/").
				then().
				log().all().
				contentType(ContentType.JSON).
				extract().
				response();
		Assert.assertEquals(reponse.path("workspaces[0].name").toString(), "Test2");
	}
}
