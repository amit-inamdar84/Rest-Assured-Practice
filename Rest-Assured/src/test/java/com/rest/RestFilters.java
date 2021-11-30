package com.rest;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.testng.annotations.Test;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
//A filter allows you to inspect and alter a request before it's actually committed and also inspect and alter the response before
//it's returned to the expectations.
//Filters can be used to implement custom authentication schemes, session management, logging etc. 
//To create a filter you need to implement the io.restassured.filter.Filter interface.

public class RestFilters {
	@Test
	public void learnFilters() throws FileNotFoundException{
		//RequestLoggingFilter class has a constructor-RequestLoggingFilter(PrintStream printStream)
		//We need to make an object of PrintStream class and pass as an argument to RequestLoggingFilter.
		PrintStream printStream = new PrintStream(new File("restassured.log"));
		given().
		        baseUri("https://postman-echo.com").
		        filter(new RequestLoggingFilter(LogDetail.BODY)).//Use LogDetail enum as optional parameter to filter further.
		        filter(new ResponseLoggingFilter(LogDetail.STATUS)).//If no parameter is used it prints everything.
		        filter(new RequestLoggingFilter(printStream)).//Print output to log file.
		        filter(new ResponseLoggingFilter(LogDetail.BODY, printStream)).
		when().
		       get("/get").
		then().
		       assertThat().
		       statusCode(200);
	}

}
