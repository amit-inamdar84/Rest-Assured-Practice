package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

//Here we are using Jackson external library for serialization.
public class JacksonToJson {
	@BeforeClass
	@Parameters({"APIKey"})
	public void beforeClass(String APIKey){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://api.getpostman.com").
		                                        addHeader("X-Api-Key", APIKey).
		                                        setContentType(ContentType.JSON).
		                                        log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder().
		expectStatusCode(200).
		expectContentType(ContentType.JSON);
		RestAssured.responseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void create_Workspace_Using_Hashmap() throws JsonProcessingException{
				HashMap<String,Object> mainObject = new HashMap<String,Object>();
				HashMap<String,String> nestedObject = new HashMap<String,String>();
				nestedObject.put("name", "Pay1");
				nestedObject.put("type", "personal");
				nestedObject.put("description", "Pay1");
				
				mainObject.put("workspace", nestedObject);
				
				ObjectMapper objectMapper = new ObjectMapper();
				String mainString = objectMapper.writeValueAsString(mainObject);//Here the java object is converted to json and represented in string.
				
				given().
		                body(mainString).
		        when().
		                post("/workspaces").
		        then().
		                log().all().
		                assertThat().
		                body("workspace.name", equalTo("Pay1"),
		        		"workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
			
	}
	
	@Test
	public void create_workspace_using_object_node() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode nestedobjectNode = objectMapper.createObjectNode();
		nestedobjectNode.put("name", "JacksonNodeSpace");
		nestedobjectNode.put("type", "personal");
		nestedobjectNode.put("description", "JacksonNodeSpace");
		
		ObjectNode mainObjectNode = objectMapper.createObjectNode();
		mainObjectNode.set("workspace", nestedobjectNode);
		
		String mainString = objectMapper.writeValueAsString(mainObjectNode);
		//Instead of string we can directly pass the object node. i.e.mainObjectNode
		given().
                body(mainString).
        when().
                post("/workspaces").
        then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("JacksonNodeSpace"),
        		"workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
	}

}
