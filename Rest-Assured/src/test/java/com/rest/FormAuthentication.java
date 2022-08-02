package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
//Program for form auth
public class FormAuthentication {
	@BeforeClass
	public void beforeClass(){
		RestAssured.requestSpecification = new RequestSpecBuilder().
				                           setRelaxedHTTPSValidation().//Ignore https
				                           setBaseUri("https://localhost:8443").
				                           build();
	}
	
	@Test
	public void form_auth_csrf() {//pass session ID and CSRF to login and make subsequent calls.
		SessionFilter sfilter = new SessionFilter();//This will work only if session ID is named as JSESSIONID. If not refer:
		//https://github.com/rest-assured/rest-assured/wiki/Usage#session-config
		given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                withAutoDetectionOfCsrf()).//Identify CSRF token from HTML of login page
                filter(sfilter).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);
		System.out.println("Session filter: " +sfilter.getSessionId());
		
		given().
		        sessionId(sfilter.getSessionId()).//Here we can send only cookie value
		        log().all().
		when().
		       get("/profile/index").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
	}
}
