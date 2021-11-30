package com.rest;

import org.testng.annotations.Test;

import com.helper.testdata.DataProviderClass;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class AutomateGet {
	@Test(dataProvider = "GetKey" , dataProviderClass = DataProviderClass.class)
	public void validate_get_status_code(String apiKey){
		given().
		        baseUri("https://api.postman.com").
		        header("X-Api-Key", apiKey).
		when().
		        get("/workspaces/").
		then().
		        log().all().
		        assertThat().
		        statusCode(200).
		        //body is a method within rest assured library that will help to use hamcrest matchers
		        body("workspaces.name",hasItems("Test2","API Practice","Test1","Team Workspace"),
		        	 "workspaces[0].name",equalTo("Test2"),
		        	 "workspaces[0].id",is(equalTo("465db5be-50dc-4e31-ad08-5094a1367e1e")),
		        	 "workspaces.size()",equalTo(4),
		        	 "workspaces.type",hasItem("personal"));
		        
	}

}
