package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.pojo.SimplePojo;
import com.request.pojo.workspace.Workspace;
import com.request.pojo.workspace.WorkspaceRoot;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class ComplexJsonPojoSerDeser {
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
	public void pojo_serialization_deserialization() throws JsonProcessingException {
		  Workspace workspace = new Workspace("RestWork", "personal", "Rest assured work");//Create pojo object and initialize with JSON data
		  WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);//Create parent node POJO and initialize with child node.
		  HashMap<String, String> myMap = new HashMap<String, String>();//Just for testing Jackson annotations
		  workspace.setMyMap(myMap);
		  
		  WorkspaceRoot deserializedReponse = given().
                body(workspaceRoot).//Internally rest assured uses Jackson to convert POJO object to JSON.i.e. Serialization
        when().
                post("/workspaces").
        then().
                log().all().
                extract().
                response().
                as(WorkspaceRoot.class);//Deserializes json response and converts to Java object.
		  
		assertThat(deserializedReponse.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
		assertThat(deserializedReponse.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
		
	}


}
