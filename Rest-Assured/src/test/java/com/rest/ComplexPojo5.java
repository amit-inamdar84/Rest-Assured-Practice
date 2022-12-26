package com.rest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

//Default request and response configuration practice for interview

public class ComplexPojo5 {
	ResponseSpecification customResponseSpecification;
	@BeforeClass
	public void beforeClass(){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
				                                setBaseUri("https://api.getpostman.com").
				                                addHeader("X-API-Key","PMAK-6163dd9a886923005f2aad79-cdd431c2dbd9109d3e5c010d48a558567c").
				                                addHeader("Content-Type","application/json").
				                                log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
				                                  expectStatusCode(200).
				                                  expectContentType(ContentType.JSON).
		                                          log(LogDetail.ALL);
		customResponseSpecification = responseSpecBuilder.build();
		
	}
	
	@Test
	public void create_Postman_API() {
		String payload = "{\r\n"
				+ "	\"api\": {\r\n"
				+ "		\"name\": \"Fake API1\",\r\n"
				+ "		\"summary\": \"This is supposed to be a short summary.\",\r\n"
				+ "		\"description\": \"This is description.\"\r\n"
				+ "	}\r\n"
				+ "}";
		
		Response response = given().
		        body(payload).
		        queryParam("workspace", "4642c6a1-4c50-4105-931f-45ba67581bf2").
		when().
		        post("/apis").
		then().spec(customResponseSpecification).
		       extract().
		       response();
		assertThat(response.path("api.name"), equalTo("Fake API1"));
		
	}

}
