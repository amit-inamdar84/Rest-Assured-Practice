package com.rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class PathParameter {
	@Test
	public void pathParameter(){
		given().
		        baseUri("https://reqres.in").
		        pathParam("userid", "2").
		        log().all().
		when().
		       get("/api/users/{userid}").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}

}
