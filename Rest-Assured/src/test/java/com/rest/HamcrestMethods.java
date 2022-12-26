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
		        body("workspaces.name", contains("Team Workspace","Test20","API Practice", "Automated workspace", "Automated workspace1"),//Passes if all elements present in same order and are the only elements in collection.
		             "workspaces.name", hasItems("non BDD workspace","API Practice"),//Passes if elements specified exist. Extra elements in collection can exist and are ignored.
		             "workspaces.name", containsInAnyOrder("API Practice","Test20","non BDD workspace","Team Workspace"),//Passes if all elements are present in collection are the only elements. Order is ignored.
		             "workspaces.name", not(empty()),//Checks if specified collection is empty or not. Remove "not" to check for empty.
		             "workspaces.name", not(emptyArray()),//Checks if specified array(not collection) is empty.
		             "workspaces.name", hasSize(4),//Validates the size of specified collection.
		             "workspaces.name", not(everyItem(startsWith("T"))),//Verifies all items in collection start with a specified string. Remove "not"
		             "workspaces[0]", hasKey("id"),//Checks key of a map
		             "workspaces[0]", hasValue("f971e759-4aaf-48ed-8f5c-5be09ea6d2ef"),//Checks value of a map
		             "workspaces[1]", hasEntry("id", "4642c6a1-4c50-4105-931f-45ba67581bf2"),//Checks both key and value of a map.
		             "workspaces.name", not(equalTo(Collections.EMPTY_MAP)),//Checks if map is empty.
		             "workspaces[0].visibility", containsString("team"),
		             "workspaces[0].visibility", not(emptyString())
		             //Practice and study other matchers mentioned in Notes.txt
		        		);
	}







}
