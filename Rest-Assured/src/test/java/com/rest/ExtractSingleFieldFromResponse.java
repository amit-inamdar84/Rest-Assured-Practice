package com.rest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.helper.testdata.DataProviderClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ExtractSingleFieldFromResponse {
	
	@Test(dataProvider = "GetKey" , dataProviderClass = DataProviderClass.class)
	public void extract_single_value_from_response(String apiKey){
		Response response = given().
		        baseUri("https://api.postman.com").
		        header("X-Api-Key", apiKey).
		when().
		        get("/workspaces/").
		then().
		        assertThat().
		        statusCode(200).
		        extract().
		        response();
//There are multiple ways to extract single field value from response. They are listed below.
		//Option1: On reference of Response interface call method path() which accepts json path as argument and returns value.
		System.out.println("Workspace name: "+response.path("workspaces[0].name"));
		
		//Option2: Create an object of JsonPath class constructor JsonPath(String). On the object reference call method .getString
		//and pass Json Path as argument.
		JsonPath jsonPath = new JsonPath(response.getBody().asString());//Constructor argument is response body as string.
		System.out.println("Workspace name: "+jsonPath.getString("workspaces[0].name"));//Instead of getString we can use getList etc for other data types
		
		//Option3: Call static method .from of JsonPath class that accepts response as string and returns JsonPath object. On that call
		//.getString() method same like in option2 above.
		System.out.println("Workspace name: "+JsonPath.from(response.asString()).getString("workspaces[0].name"));
		System.out.println("------------------------------------------------------------------------------------------");
		
		//Hamcrest assertions - Visible only after importing import static org.hamcrest.MatcherAssert.assertThat;
		//Or should add maven dependency
		//Example below is to show how hamcrest can be used outside of rest assured methods.
		assertThat(jsonPath.getString("workspaces[0].name"), equalTo("Team Workspace"));
		assertThat(response.path("workspaces[0].id"), equalTo("f971e759-4aaf-48ed-8f5c-5be09ea6d2ef"));
		
		//TestNG assertion
		Assert.assertEquals(jsonPath.getString("workspaces[0].name"), "Team Workspace");
		
	}

}
