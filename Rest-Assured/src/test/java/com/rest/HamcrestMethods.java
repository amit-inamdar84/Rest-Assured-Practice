package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.helper.testdata.DataProviderClass;

import static org.hamcrest.Matchers.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class HamcrestMethods {
	
	@Test(dataProvider = "GetKey" , dataProviderClass = DataProviderClass.class)
	public void validate_response_body_hamcrest_matchers(String apiKey){
		given().
		        baseUri("https://api.postman.com").
		        header("X-Api-Key", apiKey).
		when().
		        get("/workspaces/").
		then().
		        log().all().
		        assertThat().
		        statusCode(200).
		        body("workspaces.name", contains("Test2","API Practice","Test1","Team Workspace"),//Passes if all elements present in same order and are the only elements in collection.
		             "workspaces.name", hasItems("Test2","API Practice","Test1"),//Passes if elements specified exist. Extra elements in collection can exist and are ignored.
		             "workspaces.name", containsInAnyOrder("Team Workspace","API Practice","Test1","Test2"),//Passes if all elements are present in collection are the only elements. Order is ignored.
		             "workspaces.name", not(empty()),//Checks if specified collection is empty or not. Remove "not" to check for empty.
		             "workspaces.name", not(emptyArray()),//Checks if specified array(not collection) is empty.
		             "workspaces.name", hasSize(4),//Validates the size of specified collection.
		             "workspaces.name", not(everyItem(startsWith("T"))),//Verifies all items in collection start with a specified string. Remove "not"
		             "workspaces[0]", hasKey("id"),//Checks key of a map
		             "workspaces[0]", hasValue("465db5be-50dc-4e31-ad08-5094a1367e1e"),//Checks value of a map
		             "workspaces[1]", hasEntry("id", "9336507d-859e-4846-80f8-b7d670d0c4f6"),//Checks both key and value of a map.
		             "workspaces.name", not(equalTo(Collections.EMPTY_MAP))//Checks if map is empty.
		             //Practice and study other matchers mentioned in Notes.txt
		        		);
	}







}
