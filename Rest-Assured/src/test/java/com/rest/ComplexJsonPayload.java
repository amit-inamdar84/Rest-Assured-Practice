package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ComplexJsonPayload {

	ResponseSpecification customResponseSpecification;
	
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
		customResponseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void validate_post_request_mockServer_complexjson(){//Passing complex nested json using hashmap and array list.
		List<Integer> batterID = new ArrayList<Integer>();
		batterID.add(5);
		batterID.add(9);
		
		HashMap<String,Object> batterIDObject1 = new HashMap<String,Object>();
		batterIDObject1.put("id", batterID);
		batterIDObject1.put("type", "Chocolate");
		
		HashMap<String,Object> batterIDObject2 = new HashMap<String,Object>();
		batterIDObject2.put("id", "1001");
		batterIDObject2.put("type", "Regular");
		
		List<HashMap<String, Object>> batterArray = new ArrayList<HashMap<String, Object>>();
		batterArray.add(batterIDObject2);
		batterArray.add(batterIDObject1);
		
		HashMap<String,List<HashMap<String, Object>>> battersHashMap = new HashMap<String,List<HashMap<String, Object>>>();
		battersHashMap.put("batter", batterArray);
		
		List<String> toppingTypeList = new ArrayList<String>();
		toppingTypeList.add("test1");
		toppingTypeList.add("test2");
		
		HashMap<String,Object> toppingID1 = new HashMap<String,Object>();
		toppingID1.put("id", "5002");
		toppingID1.put("type", toppingTypeList);
		
		HashMap<String,Object> toppingID2 = new HashMap<String,Object>();
		toppingID2.put("id", "5001");
		toppingID2.put("type", "None");
		
		List<HashMap<String, Object>> toppingArray = new ArrayList<HashMap<String, Object>>();
		toppingArray.add(toppingID2);
		toppingArray.add(toppingID1);
		
		HashMap<String,Object> mainHashMap = new HashMap<String,Object>();
		mainHashMap.put("id", "0001");
		mainHashMap.put("type", "donut");
		mainHashMap.put("name", "Cake");
		mainHashMap.put("ppu", 0.55);
		mainHashMap.put("batters", battersHashMap);
		mainHashMap.put("topping", toppingArray);

		given().
                body(mainHashMap).
        when().
                post("/complexjson").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
	}



}
