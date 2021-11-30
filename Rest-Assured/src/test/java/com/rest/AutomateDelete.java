package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AutomateDelete {
	
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
		expectContentType(ContentType.JSON).
		log(LogDetail.ALL);//This is not working.
		RestAssured.responseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void validate_delete_request(){
		String workspaceID = "465db5be-50dc-4e31-ad08-5094a1367e1e";

		given().
	
		when().
		        delete("/workspaces/"+workspaceID).
		then().
		        log().all().
		        assertThat().
		        body("workspace.id" , matchesPattern("^[a-z0-9-]{36}$"),
		        		"workspace.id" , equalTo(workspaceID));
		        
	}
	
	@Test
	public void validate_delete_request_non_bdd_style(){
		String workspaceID = "62f273b5-c0cd-4aa0-b5ff-b7d4e61acf1e";
		Response response = given().
		pathParam("workspaceID", workspaceID).
		delete("/workspaces/{workspaceID}");
		
		assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
		assertThat(response.<String>path("workspace.id"), equalTo(workspaceID));
	}



}
