package com.rest;
import org.testng.annotations.Test;

import com.helper.testdata.DataProviderClass;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ExtractResponse {
	@Test(dataProvider = "GetKey" , dataProviderClass = DataProviderClass.class)
	public void extract_response(String apiKey){
		Response response = given().
		        baseUri("https://api.postman.com").
		        //queryParam("key", "value"). Need to create a separate test file for query and path parameters.
		        //queryParam("anotherparam", "multiplevalues").
		        //queryParams(parametersMap). Can add all parameters to map and pass as argument.
		        //queryParam("key", "value","value2","value3"). Multi value query parameter.
		        pathParam("userid", "f971e759-4aaf-48ed-8f5c-5be09ea6d2ef").
		        header("X-Api-Key", apiKey).
		when().
		        get("/workspaces/{userid}").//Inserted {userid} to include path parameter.
		then().
		        assertThat().
		        statusCode(200).
		        extract().
		        response();//Returns the entire response as an object and stores in reference of Response interface.
		System.out.println("Response: "+response.asString());
	}



}
