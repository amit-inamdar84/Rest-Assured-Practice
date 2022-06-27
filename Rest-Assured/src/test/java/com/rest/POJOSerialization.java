package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.pojo.SimplePojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class POJOSerialization {
	@BeforeClass
	public void beforeClass(){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://65195db8-f1d2-4c9a-a775-100d660074ab.mock.pstmn.io").
		                                        addHeader("x-mock-match-request-body", "true").
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
	public void pojo_serialization_deserialization() throws JsonProcessingException {
		//Two ways for initializing POJO class
		  SimplePojo simplePojo = new SimplePojo("value1", "value2");
		/*
		 * SimplePojo simplePojo = new SimplePojo(); simplePojo.setKey1("value1");
		 * simplePojo.setKey2("value2");
		 */
		  
		  SimplePojo deserializedReponse = given().
                body(simplePojo).//Internally rest assured uses Jackson to convert POJO object to JSON.i.e. Serialization
        when().
                post("/postSimplePojo").
        then().
                log().all().
                extract().
                response().
                as(SimplePojo.class);//Deserializes json response and converts to Java object.
		  
		ObjectMapper objectMapper = new ObjectMapper();
		String deserializedRespStr = objectMapper.writeValueAsString(deserializedReponse);//Serializing response POJO to string
		String expectedPojoStr = objectMapper.writeValueAsString(simplePojo);//Serializing request POJO to string
		//In assert we are again deserializing JSON as string to JsonNode which is an object.
		//The overloaded assertThat() method here compared two Json Nodes.
		assertThat(objectMapper.readTree(deserializedRespStr), equalTo(objectMapper.readTree(expectedPojoStr)));
	}

}
