package com.rest;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class GetGmailProfile {
	ResponseSpecification customResponseSpecification;
	String access_token = "ya29.a0AVA9y1veMGReJndF75UbBlW7z13T65UXcLWvyEB0k3qUoMtyLp0gVtqF02S1g6ryQ3U8sw87Cny9w_Tgxis_NzViSU3Wvowdz1idQEI_ef4IARX5LooiBo62xLSXX5HvEsKBSvcSZaV9Ps57gZZkAtm8nmoiLg";
	@BeforeClass
	public void beforeClass(){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
				addHeader("Authorization", "Bearer " + access_token).
		                                        setBaseUri("https://gmail.googleapis.com").
		                                        setContentType(ContentType.JSON).
		                                        log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder().
		expectStatusCode(200).
		expectContentType(ContentType.JSON).
		log(LogDetail.ALL);
		customResponseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void getUserProfile() {
		given().
		        basePath("gmail/v1").
		        pathParam("userid","amit.inamdar84@gmail.com").
		when().
		       get("/users/{userid}/profile").
		then().spec(customResponseSpecification);		       
	}
	

}
