package com.rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class MultiPartFormData {
	@Test
	public void formData(){
		given().
		        baseUri("https://postman-echo.com").
		        multiPart("foo1", "bar1").//Sending text form data.
		        multiPart("foo2", "bar2").
		        log().all().
		when().
		       post("/post").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
}
