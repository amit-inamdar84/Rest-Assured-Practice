package com.rest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
//Practice of all concepts learned in the course.
public class AllConcepts {
		ResponseSpecification customResponseSpecification;
		String token = "Bearer BQBL0270jQH_VhsuwAnSY19chGkVvzZ778irkuxwe9LnSaSTL_QYbNaNWtm0RRMG0rJ0r36voAXXvYQWY5H3zZY2Hl_vnU-Bfi4xVU2Aud5SL0WhIzKVY041OfK3b0NJbQB8x2JEnDkyUGZlLY0SNk52f5Dfa0HmDP9SwqeBCnfsdH9s38BQbgCUX18jmZ4oo6c419oH8CciCzm6iQGbF8JkOcGdXm99jyaBy4kFNd7r8FRgLVHMbB1bVDB_gtQNBbHx7nn8tAnk9Jfv";
		
		@BeforeClass
		public void beforeClass() {
			RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
					                                setBaseUri("https://api.spotify.com/v1").
					                                addHeader("Authorization" , token).
					                                //addHeader("Content-Type", "application/json").
					                                log(LogDetail.ALL);
			RestAssured.requestSpecification = requestSpecBuilder.build();
			
			ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
					                                  expectStatusCode(201).
					                                  expectContentType(ContentType.JSON).
					                                  log(LogDetail.ALL);
			customResponseSpecification = responseSpecBuilder.build();	                                
		}
		
		@Test (priority=0)
		public void addItemsToPlaylist() {
			Header header = new Header("Content-Type", "application/json");
			String payload="{\r\n"
					+ "  \"uris\": [\r\n"
					+ "    \"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"\r\n"
					+ "  ],\r\n"
					+ "  \"position\": 0\r\n"
					+ "}";
			Response response = given().
			        header(header).
			        //contentType(ContentType.JSON).
			        body(payload).
			when().
			        post("/playlists/2DS6oEwmhMy86d0YOzFp9I/tracks").
			then().
	                extract().
	                response();
			System.out.println("Printing response:-----" +response.asString());
			System.out.println("Extracting single field:----"+response.path("snapshot_id"));
		}
		
		@Test (priority=1)
		public void addItemsToPlaylist1() {//JSON file payload, POST, Extract headers
			File file = new File("src/main/resources/payload/PlayListItems.json");
			Headers extractHeader = given().
			        contentType(ContentType.JSON).
			        body(file).
			when().
			        post("/playlists/2DS6oEwmhMy86d0YOzFp9I/tracks").
			then().
	                extract().
	                headers();
			assertThat(extractHeader.get("date").getName(), equalTo("date"));
			System.out.println("Extract response header name: "+extractHeader.get("date").getName());
			System.out.println("Extract response header value: "+extractHeader.get("date").getValue());
			
			for(Header header: extractHeader) {
				System.out.print("Header name: "+header.getName()+ " , ");
				System.out.println("Header value: "+header.getValue());
			}
			
		}
		
		@Test (priority=2)
		public void getPlaylistItems() {//GET example, query param, 
			Response response = given().
			        contentType(ContentType.JSON).
			        queryParam("market", "IN").
			when().
			        get("/playlists/2DS6oEwmhMy86d0YOzFp9I/tracks").
			then().
			       log().all().
			       assertThat().
			       statusCode(200).
			       extract().
			       response();//Contains huge response. Need to practice later
			       assertThat(response.path("items[1].track.album.name"), equalTo("Cut To The Feeling"));
			
		}
		
		@Test (priority=3)
		public void deletePlaylistItems() {//Payload using hashmap
			HashMap<String, String> track0 = new HashMap<String, String>();
			track0.put("uri", "spotify:track:4iV5W9uYEdYUVa79Axb7Rh");
			
			HashMap<String, String> track1 = new HashMap<String, String>();
			track1.put("uri", "spotify:track:11dFghVXANMlKmJXsNCbNl");
			
			ArrayList<Object> tracks = new ArrayList<Object>();
			tracks.add(track0);
			tracks.add(track1);
			
			HashMap<String, ArrayList> mainObject = new HashMap<String, ArrayList>();
			mainObject.put("tracks", tracks);
			
			/*
			 * String payload = "{\r\n" + "   \"tracks\":[\r\n" + "      {\r\n" +
			 * "         \"uri\":\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"\r\n" +
			 * "      },\r\n" + "      {\r\n" +
			 * "         \"uri\":\"spotify:track:11dFghVXANMlKmJXsNCbNl\"\r\n" +
			 * "      }\r\n" + "   ]\r\n" + "}";
			 */
			Response response = given().
			        contentType(ContentType.JSON).
			        body(mainObject).
			when().
			        delete("/playlists/2DS6oEwmhMy86d0YOzFp9I/tracks").
			then().
			        log().all().
		            assertThat().
		            statusCode(200).
		            extract().
		            response();
			assertThat(response.path("snapshot_id"), matchesPattern("^[A-Za-z0-9=]{60}$"));
			
		}
		
		@Test (priority=4)
		public void getUserProfile() {//Path Param
			given().
			        contentType(ContentType.JSON).
			        pathParam("userID", "31pb3fllty6s6e36u4ikhfienoyi").
			when().
			        get("users/{userID}").
			then().
			        log().all().
			        assertThat().
			        statusCode(200).
			        body("display_name", equalTo("Amit"),
			        	"id", equalTo("31pb3fllty6s6e36u4ikhfienoyi")).
			        body	
			        	(matchesJsonSchema(new File("src/main/resources/JsonSchema/UserProfileGet.json")));
			//assertThat(response.path("display_name"), equalTo("Amit"));
			//assertThat(response.path("id"), equalTo("31pb3fllty6s6e36u4ikhfienoyi"));
		}

	}

