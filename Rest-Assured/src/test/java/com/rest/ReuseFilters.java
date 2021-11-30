package com.rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

//Refer RestFilters.java
public class ReuseFilters {
	@BeforeClass
	public void beforeClass() throws FileNotFoundException{
		PrintStream printStream = new PrintStream(new File("restassured.log"));
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://postman-echo.com").
		                                        addFilter(new RequestLoggingFilter(printStream)).
		                                        addFilter(new ResponseLoggingFilter(LogDetail.STATUS, printStream));
		                                        
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder();
		RestAssured.responseSpecification = responseSpecBuilder.build();
		
	}
	
	@Test
	public void filtersReuse(){
		
		given().
		        
		when().
		       get("/get").
		then().
		       assertThat().
		       statusCode(200);
	
	}

}
