package com.helper.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderClass {
	
	@DataProvider(name = "GetKey")
	public Object[] getAPIKey() throws IOException{
		File file = new File("D:/Git/Rest-Assured/src/main/java/com/helper/configFile/config.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties OR = new Properties();
		OR.load(fis);
		//Object[] data = new Object[]{OR.getProperty("APIKey")};This is alternative way to convert String to Object array.
		Object[] data = new Object[1];//Declaring object array of size 1
		data[0] = OR.getProperty("APIKey");//Storing string type data in 0th position of Object array.
		return data;
	}
	
	@Test
	public void testData(){
		
	}

}
