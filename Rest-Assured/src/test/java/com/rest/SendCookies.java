package com.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class SendCookies {
//Program for sending and getting cookies
	@BeforeClass
	public void beforeClass(){
		RestAssured.requestSpecification = new RequestSpecBuilder().
				                           setRelaxedHTTPSValidation().//Ignore https
				                           setBaseUri("https://localhost:8443").
				                           build();
	}
	
	//@Test
	public void form_auth_csrf_and_cookie() {//pass cookie instead of session ID and CSRF to login and make subsequent calls.
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
		        cookie("JSESSIONID", sfilter.getSessionId()).//Another way to set cookie in request. Here we can send entire cookie
		        log().all().
		when().
		       get("/profile/index").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
	}
	
	//@Test
	public void form_auth_csrf_with_cookie_builder() {//pass cookie builder and CSRF to login and make subsequent calls.
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
		
		Cookie cookie = new Cookie.Builder("JSESSIONID", sfilter.getSessionId()).setSecured(true).setHttpOnly(true).setComment("my cookie").build();
		given().
		        cookie(cookie).
		        log().all().
		when().
		       get("/profile/index").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
	}
	
	//@Test
	public void send_multiple_cookies() {//Pass multiple cookies in request
		SessionFilter sfilter = new SessionFilter();//This will work only if session ID is named as JSESSIONID. If not refer:
		//https://github.com/rest-assured/rest-assured/wiki/Usage#session-config
		//API to login to app
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
		
		Cookie cookie = new Cookie.Builder("JSESSIONID", sfilter.getSessionId()).setSecured(true).setHttpOnly(true).setComment("my cookie").build();
		Cookie cookie1 = new Cookie.Builder("dummy", "dummyvalue").build();
		Cookies cookies = new Cookies(cookie,cookie1);
		
		//API to get profile after login
		given().
		        cookies(cookies).
		        log().all().
		when().
		       get("/profile/index").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
	}
	
	@Test
	public void fetch_cookies() {
		Response response = given().
		        log().all().
		when().
		        get("/profile/index").
		then().
		        log().all().
	            assertThat().
	            statusCode(200).
	            extract().
	            response();
		System.out.println("Fetching single cookie value: " +response.getCookie("JSESSIONID"));//Returns just the cookie value
		System.out.println("Fetching single cookie value: " +response.getDetailedCookie("JSESSIONID"));//Returns detailed cookie with all attributes
		
		Map<String,String> cookies = response.getCookies();
		for(Map.Entry<String, String> cookie : cookies.entrySet()) {
			System.out.println("Cookie name: "+cookie.getKey() + " Cookie value:  "+cookie.getValue());//Will fetch only name and value
		}
		
		Cookies cookies1 = response.getDetailedCookies();
		List<Cookie> cookieList = cookies1.asList();
		for(Cookie cookie: cookieList) {
			System.out.println("Fetching multiple cookies with all attributes: " +cookie.toString());//Will fetch cookie name, value and attributes
		}
	}

}
