package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.request.pojo.MockUser.Address;
import com.request.pojo.MockUser.Geo;
import com.request.pojo.MockUser.RootUser;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class ComplexPojo1 {
	@BeforeClass
	public void beforeClass(){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://jsonplaceholder.typicode.com").
		                                        setContentType(ContentType.JSON).
		                                        log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder().
		expectStatusCode(201).
		expectContentType(ContentType.JSON);
		RestAssured.responseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void create_user() {
		Geo geo = new Geo("-37.3159", "81.1496");
		Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
		RootUser rootUser = new RootUser("Leanne Graham", "Bret", "Sincere@april.biz", address);
		
		RootUser deserializedReponse = given().
	                body(rootUser).
	        when().
	                post("/users").
	        then().
	                log().all().
	                body("$.", hasKey("id")).
	                extract().
	                response().
	                as(RootUser.class);
		
		    assertThat(deserializedReponse.getId(), not(equalTo(null)));
	}

}
