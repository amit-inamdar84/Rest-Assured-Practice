package com.rest;
//This class demonstrates various ways in which headers can be passed with a request.
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class PassingHeaders {
	@Test
	public void passing_headers(){
		Header header = new Header("header", "value2");
		Header matchHeader = new Header("x-mock-match-request-headers", "header");
		Headers multiHeaders = new Headers(header, matchHeader);
		
		HashMap<String, String> hmapHeaders = new  HashMap<String, String>();
		hmapHeaders.put("header", "value1");
		hmapHeaders.put("x-mock-match-request-headers", "header");
		
		given().
		        baseUri("https://65195db8-f1d2-4c9a-a775-100d660074ab.mock.pstmn.io").
		        //header("header", "value1").//Passing header with the request
		        //header("x-mock-match-request-headers", "header").//Another header. Basically chaining of multiple header methods.
		        //header(header).//Another way. Passing header object.
		        //header(matchHeader).//Another way. Passing header object.
		        header("multiValueHeader", "value1", "value2").//Can be passed using object reference as well
		        header("header", "value1","x-mock-match-request-headers", "header").//Multiple headers in one request.
		        //headers("header", "value1","x-mock-match-request-headers", "header").//Multiple headers in one request.
		        //headers(multiHeaders).//Multiple headers can be passed at once using reference of Headers class.
		        headers(hmapHeaders).//Storing headers in map and passing object reference
		        log().headers().
		        
		when().
		        get("/get").
		then().
		       log().all().
		       assertThat().
		       statusCode(200);
	}
	

}
