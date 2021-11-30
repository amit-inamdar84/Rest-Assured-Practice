package com.rest;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;



//The above mentioned static imports if used can avoid typing the class name. We can directly use static method. This will help
//code optimization and readability. 
//However, disadvantage is for beginners, we will not know which class the method belongs to. And which methods are available in a class.
//For ex: Instead of RestAssured.given() we use given().
//Static imports can be used: 1. By experts 2.When we write test cases using given,when and then format.
//Do not use static imports if writing test cases like: 
/*RequestSpecification requestSpecification = RestAssured.given();
requestSpecification.contentType(ContentType.JSON);
Response response = requestSpecification.get(url);
if (response.getStatusCode() == 200) {
	getTodos = gson.fromJson(response.getBody().asString(), GetTodos[].class);
	Assert.assertEquals("Actual result", "Expected result");
}*/
//given().when().then(); This is called method chaining. We can chain methods when all methods return an object of same type.

public class MethodChaining {
	@Test
	public void test(){
		given().
		when().
		then();
	}
}
