package com.rest;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

public class Fileupload {
	@Test
	public void fileUpload(){
		String attributes = "{ \"name\":\"temp.txt\",  \"parent\":{\"id\":\"123456\"}}";//Get this data from HTML code.
		given().
		        baseUri("https://postman-echo.com").
		        multiPart("file", new File("temp.txt")).
		        multiPart("attributes", attributes, "application/json").//Need more understanding on this.
		        log().all().
		when().
		       post("/post").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
}
