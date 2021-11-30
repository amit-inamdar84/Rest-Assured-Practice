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

public class AutomatePUT {
		
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
		public void validate_put_request(){
			String workspaceID = "5d9c3254-656d-4ec3-b8e0-d9abe2e5fa56";
			String payload = "{\n" +
	            "\"workspace\": {\n" +
	            "\"name\": \"Java workspace\",\n" +
	            "\"type\": \"personal\", \n" +
	            "\"description\": \"Rest assured practice\" \n" +
	            "}\n" +
	"}";

			given().
			        body(payload).
			when().
			        put("/workspaces/"+workspaceID).
			then().
			        log().all().
			        assertThat().
			        body("workspace.name", equalTo("Java workspace"),
			        		"workspace.id" , matchesPattern("^[a-z0-9-]{36}$"),
			        		"workspace.id" , equalTo(workspaceID));
			        
		
		}
		
		@Test
		public void validate_put_request_non_bdd_style(){
			String workspaceID = "4642c6a1-4c50-4105-931f-45ba67581bf2";
			String payload = "{\n" +
		            "\"workspace\": {\n" +
		            "\"name\": \"Test20\",\n" +
		            "\"type\": \"personal\", \n" +
		            "\"description\": \"Rest assured practice\" \n" +
		            "}\n" +
		"}";
			
			Response response = given().
			body(payload).
			pathParam("workspaceID", workspaceID).
			put("/workspaces/{workspaceID}");
			
			assertThat(response.<String>path("workspace.name"), equalTo("Test20"));
			assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
			assertThat(response.<String>path("workspace.id"), equalTo(workspaceID));
		}

}
