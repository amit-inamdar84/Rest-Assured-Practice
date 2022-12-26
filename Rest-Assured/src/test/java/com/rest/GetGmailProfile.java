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
	String access_token = "ya29.a0AeTM1ifbJnparco73KpiAZIyfomcldhm1NfU_us2zV5v_WGYqMnuJ1310kMWApAhfurJZbXruibR5RjOvsWEgSXywypBa_kYhFR867c26sNk6g5bvegaUYHXFdx5yQEqtd6kIJxI6O_YsQThcOfnpPKTXl6HfeQaCgYKAd8SARASFQHWtWOmaHmaB_RXc9ypEn1j5lUTkQ0166";
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
