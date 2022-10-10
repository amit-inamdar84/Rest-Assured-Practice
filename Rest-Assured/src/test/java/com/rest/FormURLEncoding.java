package com.rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
//Form data, form url encoding and raw are types of request body that are sent in post request.
public class FormURLEncoding {

	@Test
	public void formURLEncoded(){
	    given().
		        baseUri("https://postman-echo.com").
		        //configuration to tell Rest assured to avoid adding the charset to content-type header automatically 
		        config(RestAssured.config.encoderConfig(
	                    EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
		        formParam("key1", "value1").
		        formParam("key 2", "value 2").
		        log().all().
		when().
		       post("/post").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
		
	}
}
