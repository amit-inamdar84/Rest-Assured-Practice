package com.rest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

//Query parameters are used to filter the response. Useful when response body is huge.
//When we add query parameter, url is appended with ? and then key pair value.
//Multiple query params are separated by &.
//

public class QueryParameters {
	@Test
	public void singleQueryParameter(){
		given().
		        baseUri("https://postman-echo.com").
		        queryParam("foo", "bar").
		        log().all().
		when().
		       get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
	
	@Test
	public void multipleQueryParameters(){
		given().
		        baseUri("https://postman-echo.com").
		        queryParam("foo", "bar").
		        queryParam("foo1", "bar1").
		        log().all().
		when().
		       get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
	
	@Test
	public void multipleQueryParametersUsingMap(){
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("op1", "val1");
		queryParam.put("op2", "val2");
		
		given().
		        baseUri("https://postman-echo.com").
		        queryParams(queryParam).
		        log().all().
		when().
		       get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
	
	@Test
	public void multiValueQueryParameters(){
		
		given().
		        baseUri("https://postman-echo.com").
		        //queryParam("Key", "Val1", "Val2").
		        queryParam("Key", "Val1;Val2").
		        log().all().
		when().
		       get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}

}
