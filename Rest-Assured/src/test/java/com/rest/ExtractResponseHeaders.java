package com.rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class ExtractResponseHeaders {
	@Test
	public void extracting_headers(){
		
		HashMap<String, String> hmapHeaders = new  HashMap<String, String>();
		hmapHeaders.put("header", "value2");
		hmapHeaders.put("x-mock-match-request-headers", "header");
		
		Headers extractHeader = given().
		        baseUri("https://65195db8-f1d2-4c9a-a775-100d660074ab.mock.pstmn.io").
		        headers(hmapHeaders).
		        
		when().
		        get("/get").
		then().
		       assertThat().
		       statusCode(200).
		       extract().
		       headers();
		System.out.println("Header name: "+extractHeader.get("responseHeader").getName());//Extract individual headers. Useful when passing header to another request.
		System.out.println("Header value: "+extractHeader.get("responseHeader").getValue());
		
		for(Header header: extractHeader){//Looping through all headers
			System.out.print("Header name: "+header.getName()+ " , ");
			System.out.println("Header value: "+header.getValue());
		}
		
		List<String> values = extractHeader.getValues("multiHeaderValue");
		for(String value : values){
			System.out.println("Printing multi value header here: "+value);
		}
	}
	





}
