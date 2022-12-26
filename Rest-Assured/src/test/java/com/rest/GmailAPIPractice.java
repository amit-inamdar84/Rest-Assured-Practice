package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class GmailAPIPractice {
	String messageID;
	ResponseSpecification customResponseSpecification;
	String access_token = "ya29.a0AeTM1icshd6H3twOOhQf1uP60l_J9U7Ofd5jBRHqHdwrR2PyY-TV0inHL-Vi8EIAqGJEZMDdUV0woYq0j6C4BZfxmx0C3wkTbgV14YgaYW_XgnGtg7Z61CIehozvAeA7aI1STwZwu8LRxz94gDVpdWfVq7tVmP0aCgYKAZMSARASFQHWtWOmf3yNyOLStuGknNh97y9gww0166";
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
	
	@Test(priority = 0)
	public void getUserProfile() {
		given().
		        basePath("gmail/v1").
		        pathParam("userid","amit.inamdar84@gmail.com").
		when().
		       get("/users/{userid}/profile").
		then().spec(customResponseSpecification);		       
	}
	
	@Test(priority = 1)
	public void sendEmailMessage() {
		File file = new File("src/main/resources/payload/EmailMessage.json");
		        messageID = given().
		        basePath("gmail/v1").
		        pathParam("userid","amit.inamdar84@gmail.com").
		        body(file).
		when().
		       post("/users/{userid}/messages/send").
		then().spec(customResponseSpecification).extract().response().path("id");		       
	}
	
	@Test(priority = 2)
	public void deleteEmail() {
		Response response = given().
		        basePath("gmail/v1").
		        pathParam("userid","amit.inamdar84@gmail.com").
		        pathParam("id", messageID).
		when().
		        delete("/users/{userid}/messages/{id}").
		then().
		        assertThat().
		        statusCode(204).
		        extract().
		        response();
		System.out.println("Delete response: "+response.asString());
		
	}
	
	@Test(priority = 3)
	public void getEmailMessages() {
		Response response = given().
		        basePath("gmail/v1").
                pathParam("userid","amit.inamdar84@gmail.com").
                pathParam("id", "184f4f6c360de901").
		when().
		        get("/users/{userid}/messages/{id}").
		then().
		        spec(customResponseSpecification).extract().response();
		assertThat(response.path("id"), equalTo("184f4f6c360de901"));
	}
	
	@Test(priority = 4)
	public void listEmailMessages() {
		Response response = given().
		        basePath("gmail/v1").
                pathParam("userid","amit.inamdar84@gmail.com").
		when().
		        get("/users/{userid}/messages").
		then().
		        spec(customResponseSpecification).
		        body("messages.size()", equalTo(100)).extract().response();
		assertThat(response.path("messages.length"), hasSize(100));
		
	}
	
	@Test(priority = 5)
	public void modifyEmailMessage() {
		String payload = "{\r\n"
				+ "  \"addLabelIds\": [\r\n"
				+ "    \"Label_8\"\r\n"
				+ "  ],\r\n"
				+ "  \"removeLabelIds\": [\r\n"
				+ "      \"Label_10\"\r\n"
				+ "  ]\r\n"
				+ "}";
		Response response = given().
		        basePath("gmail/v1").
                pathParam("userid","amit.inamdar84@gmail.com").
                body(payload).
		when().
		        post("/users/{userid}/messages/185001a9954b2a36/modify").
		then().
		        spec(customResponseSpecification).extract().response();
		assertThat(response.path("labelIds[0]"), equalTo("Label_8"));
	}
	
}
