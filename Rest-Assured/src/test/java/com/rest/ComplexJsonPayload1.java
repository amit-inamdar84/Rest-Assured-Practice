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

public class ComplexJsonPayload1 {
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
	
	@Test //Passing complex nested json using hashmap and array list. Assignment exercise
	public void validate_post_request_mockServer_complexjson1(){
		List<Integer> rgbaArray = new ArrayList<Integer>();
		rgbaArray.add(255);
		rgbaArray.add(255);
		rgbaArray.add(255);
		rgbaArray.add(1);
		
		HashMap<String,Object> codeMap = new HashMap<String,Object>();
		codeMap.put("rgba", rgbaArray);
		codeMap.put("hex", "#000");
		
		HashMap<String,Object> colorsObject1 = new HashMap<String,Object>();
		colorsObject1.put("color", "black");
		colorsObject1.put("category", "hue");
		colorsObject1.put("type", "primary");
		colorsObject1.put("code", codeMap);
		
		List<Integer> rgbaArray1 = new ArrayList<Integer>();
		rgbaArray1.add(0);
		rgbaArray1.add(0);
		rgbaArray1.add(0);
		rgbaArray1.add(1);
		
		HashMap<String,Object> codeMap1 = new HashMap<String,Object>();
		codeMap1.put("rgba", rgbaArray1);
		codeMap1.put("hex", "#FFF");
		
		HashMap<String,Object> colorsObject2 = new HashMap<String,Object>();
		colorsObject2.put("color", "white");
		colorsObject2.put("category", "value");
		colorsObject2.put("code", codeMap1);
		
		List<HashMap<String, Object>> colorsArray = new ArrayList<HashMap<String, Object>>();
		colorsArray.add(colorsObject1);
		colorsArray.add(colorsObject2);
		
		HashMap<String,Object> mainHashMap = new HashMap<String,Object>();
		mainHashMap.put("colors", colorsArray);

		given().
                body(mainHashMap).
        when().
                post("/complexjson1").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
	}

}
