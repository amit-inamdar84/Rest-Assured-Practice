package com.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


import java.io.File;

import org.testng.annotations.Test;

public class JsonSchemaValidation {
	@Test
	public void validateSchema(){
		given().
		        baseUri("https://postman-echo.com").
		        log().all().
		when().
		       get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       //body(matchesJsonSchemaInClasspath("EchoGet.json")).//Used when json file is placed directly under src/main/resources or
		       //src/test/java
		       body(matchesJsonSchema(new File("src/main/resources/JsonSchema/EchoGet.json")));
	}
}
