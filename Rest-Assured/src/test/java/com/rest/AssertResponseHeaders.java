package com.rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.Test;

public class AssertResponseHeaders {

	@Test
	public void asserting_headers(){
		
		HashMap<String, String> hmapHeaders = new  HashMap<String, String>();
		hmapHeaders.put("header", "value1");
		hmapHeaders.put("x-mock-match-request-headers", "header");
		
		
		HashMap<String, String> respHeaders = new  HashMap<String, String>();
		hmapHeaders.put("responseHeader", "resValue1");
		hmapHeaders.put("Content-Type", "application/json; charset=utf-8");
		
		given().
		        baseUri("https://65195db8-f1d2-4c9a-a775-100d660074ab.mock.pstmn.io").
		        headers(hmapHeaders).
		        
		when().
		        get("/get").
		then().
		       assertThat().
		       statusCode(200).
		       headers("responseHeader", "resValue1", "Content-Type", "application/json; charset=utf-8").
		       headers(respHeaders);//Overloaded method.
	}
	



}
