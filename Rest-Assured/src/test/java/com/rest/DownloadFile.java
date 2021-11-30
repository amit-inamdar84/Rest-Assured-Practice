package com.rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.testng.annotations.Test;

public class DownloadFile {
	@Test
	public void fileDownload() throws IOException{
		byte[] bytes = given().//Store file contents in a byte array
		        baseUri("https://raw.githubusercontent.com").
		        log().all().
		when().
		       get("/appium/appium/master/run_selenium.png").
		then().
		       log().all().
		       assertThat().
		       statusCode(200).
		       extract().
		       response().asByteArray();
		
		OutputStream os = new FileOutputStream(new File("run_selenium.png"));//Define file name where contents need to be copied.
		os.write(bytes);//Write the file contents stored in byte array to output stream.
		os.close();
	}


}
