package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
//Here we have used UTF-8 format in request. It is the encoding format supported by HTML pages.
//Unicode - List of characters represented by decimal numbers.
//Encoding - Mechanism of converting unicode characters to binary numbers.
//If we do not specify encoding mechanism Rest assured will use default one.
//So either we have to tell rest assured not to used any default encoding mechanism or explicitly specify encoding mechanism.
//The above concept as explained in video is not happening now. So commented the setConfig code in request spec.
import io.restassured.specification.ResponseSpecification;
public class JSONPayloadArrayList {
	ResponseSpecification customResponseSpecification;
	
	@BeforeClass
	public void beforeClass(){
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
		                                        setBaseUri("https://65195db8-f1d2-4c9a-a775-100d660074ab.mock.pstmn.io").
		                                        addHeader("x-mock-match-request-body", "true").
		                                        //setConfig(RestAssured.config.encoderConfig(
		                                                //EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
		                                        setContentType(ContentType.JSON).
		                                        //setContentType("application/json;charset=utf-8"). For learning.
		                                        log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder  responseSpecBuilder = new ResponseSpecBuilder().
		expectStatusCode(200).
		expectContentType(ContentType.JSON).
		log(LogDetail.ALL);//This is not working.
		customResponseSpecification = responseSpecBuilder.build();
	}
	
	@Test
	public void validate_post_request_mockServer_arrayList(){//Passing Payload using json as array list
		HashMap<String,String> object5001 = new HashMap<String,String>();
		object5001.put("id", "5001");
		object5001.put("type", "None");
		HashMap<String,String> object5002 = new HashMap<String,String>();	
		object5002.put("id", "5002");
		object5002.put("type", "Glazed");
		
		List<HashMap> jsonInput = new ArrayList<HashMap>();
		jsonInput.add(object5001);
		jsonInput.add(object5002);

		given().
                body(jsonInput).
        when().
                post("/post").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
	}
	
	@Test
	public void create_Workspace_Using_Array_List() throws JsonProcessingException{
				HashMap<String,String> object5001 = new HashMap<String,String>();
				object5001.put("id", "5001");
				object5001.put("type", "None");
				HashMap<String,String> object5002 = new HashMap<String,String>();	
				object5002.put("id", "5002");
				object5002.put("type", "Glazed");
				
				List<HashMap> jsonInput = new ArrayList<HashMap>();
				jsonInput.add(object5001);
				jsonInput.add(object5002);
				
				ObjectMapper objectMapper = new ObjectMapper();//External serialization.
				String mainStr = objectMapper.writeValueAsString(jsonInput);

				given().
		                body(mainStr).
		        when().
		                post("/post").
		        then().spec(customResponseSpecification).
		                assertThat().
		                body("msg", equalTo("Success"));
			
	}

}
