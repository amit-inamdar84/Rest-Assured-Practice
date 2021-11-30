package com.rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.helper.testdata.DataProviderClass;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestSpec {
	RequestSpecification  requestSpecification;
	
	@BeforeClass()
	public void beforeClass() throws IOException{
		//Here creating reference of RequestSpecification means creating object of class that is implementing RequestSpecification interface
		//Alternatively instead of RequestSpecification, we can use RequestSpecBuilder class for building the request. Refer video no 79.
	    requestSpecification = given().//Instead of given(), we can use with()
				baseUri("https://api.postman.com").
		        header("X-Api-Key", "APIKey");
	}

	@Test
	public void understanding_reqspec(){
		Response reponse = requestSpecification.get("/workspaces/").then().log().all().extract().response();
		//Assert.assertEquals(reponse.statusCode(), 200);
		assertThat(reponse.statusCode(), is(equalTo(200)));//Hamcrest assertion
	}
	
	@Test
	public void validate_resp_body(){
		Response reponse = requestSpecification.get("/workspaces/").then().log().all().extract().response();
		Assert.assertEquals(reponse.path("workspaces[0].name").toString(), "Test2");
	}
	
	@Test
	public void queryTest(){
		//Here we can query the request specification parameters.
		QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
		System.out.println("Printing specification parameters: " +queryableRequestSpecification.getBaseUri());
	}


}
