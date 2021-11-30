package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;

public class ResponseSpec {

	RequestSpecification  requestSpecification;
	ResponseSpecification responseSpecification;
	
	@BeforeClass()
	public void beforeClass() throws IOException{
		//
	    requestSpecification = given().//Instead of given(), we can use with()
				baseUri("https://api.postman.com").
		        header("X-Api-Key", "APIKey");
	    
	    //This will prebuild responseSpecification so that code can be reused in all @Test methods instead of boilerplate code
	    responseSpecification = RestAssured.expect().
	    		statusCode(200).
	    		contentType(ContentType.JSON);
	}

	@Test
	public void validate_status_code(){
		//We can build the response specification in @BeforeClass so that we dont need to use below commented lines.
		//See the difference in next test case validate_resp_body()
		        requestSpecification.get("/workspaces/").
				then().spec(responseSpecification).
				log().all();
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
