package com.rest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.request.pojo.environment.Environment;
import com.request.pojo.environment.EnvironmentRoot;
import com.request.pojo.environment.Variables;
import com.request.pojo.monitor.Monitor;
import com.request.pojo.monitor.MonitorRoot;
import com.request.pojo.monitor.Schedule;
import com.request.pojo.workspace.Collections;
import com.request.pojo.workspace.Environments;
import com.request.pojo.workspace.Mocks;
import com.request.pojo.workspace.Monitors;
import com.request.pojo.workspace.Workspace1;
import com.request.pojo.workspace.WorkspaceRoot1;
//Raw scripts for interview practice
public class ComplexPojo4 {
	//@Test
	public void create_Postman_Environment() {
		Variables variables1 = new Variables("var1","val1");
		Variables variables2 = new Variables("var2","val2");
		List<Variables> variableList = new ArrayList<Variables>();
		variableList.add(variables1);
		variableList.add(variables2);
		
		Environment environment = new Environment("AutoEnv", variableList);
		EnvironmentRoot environmentRoot = new EnvironmentRoot(environment);
		given().
		        baseUri("https://api.getpostman.com").
		        header("X-API-Key","APIKEY").
		        header("Content-Type","application/json").
		        body(environmentRoot).
		        log().all().
		when().
		       post("/environments").
		then().
		       log().all().
               assertThat().
               statusCode(200);
	}
	
	//@Test
	public void create_Postman_Monitor() {
		/*
		 * String payload = "{\r\n" + "  \"monitor\": {\r\n" +
		 * "    \"name\": \"Test Monitor\",\r\n" + "    \"schedule\": {\r\n" +
		 * "      \"cron\": \"0 0 * * *\",\r\n" +
		 * "      \"timezone\": \"Asia/Kolkata\"\r\n" + "    },\r\n" +
		 * "    \"collection\": \"4787604-b410d379-55e2-4012-b906-9798dfe7e198\",\r\n" +
		 * "    \"environment\": \"4787604-45f6715c-1e65-4721-9786-dc0b67bd6d2e\"\r\n" +
		 * "  }\r\n" + "}";
		 */
		Schedule schedule = new Schedule("0 0 * * *", "Asia/Kolkata");
		Monitor monitor = new Monitor("Test Monitor", schedule, "4787604-b410d379-55e2-4012-b906-9798dfe7e198", "4787604-45f6715c-1e65-4721-9786-dc0b67bd6d2e");
		MonitorRoot monitorRoot = new MonitorRoot(monitor);
		
		given().
		        baseUri("https://api.getpostman.com").
		        header("X-API-Key","PMAK-6163dd9a886923005f2aad79-cdd431c2dbd9109d3e5c010d48a558567c").
		        header("Content-Type","application/json").
		        body(monitorRoot).
		        log().all().
		when().
		        post("/monitors").
		then().
		        log().all().
		        assertThat().
		        statusCode(200);
		
	}
	
	//@Test
	public void create_Workspace_With_Single_Component() {
		Collections collections = new Collections("8822456d-f70c-41eb-a65a-18cdb31c710c", "New Collection1", "4787604-8822456d-f70c-41eb-a65a-18cdb31c710c");
		List<Collections> collectionsList = new ArrayList<Collections>();
		collectionsList.add(collections);
		
		Environments environments = new Environments("22acf948-125c-407e-92be-12385737dc3b", "FakeEnv", "4787604-22acf948-125c-407e-92be-12385737dc3b");
		List<Environments> environmentList = new ArrayList<Environments>();
		environmentList.add(environments);
		
		Mocks mocks = new Mocks("69081670-cb7e-4764-b7a1-c244e1edb208");
		List<Mocks> mockList = new ArrayList<Mocks>();
		mockList.add(mocks);
		
		Monitors monitors = new Monitors("69081670-cb7e-4764-b7a1-c244e1edb208");
		List<Monitors> monitorList = new ArrayList<Monitors>();
		monitorList.add(monitors);
		
		Workspace1 workspace1 = new Workspace1("Space2", "personal", "Automated with Rest assured", collectionsList, environmentList, mockList, monitorList);
		WorkspaceRoot1 workspaceRoot1 = new WorkspaceRoot1(workspace1);
		
		
		WorkspaceRoot1 deserializedReponse = given().
		        baseUri("https://api.getpostman.com").
		        header("X-API-Key","PMAK-6163dd9a886923005f2aad79-cdd431c2dbd9109d3e5c010d48a558567c").
		        header("Content-Type","application/json").
		        body(workspaceRoot1).
		        log().all().
		when().
		        post("/workspaces").
		then().
		        log().all().
                assertThat().
                statusCode(200).
                extract().
                response().
                as(WorkspaceRoot1.class);
		assertThat(deserializedReponse.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
		assertThat(deserializedReponse.getWorkspace().getName(), equalTo(workspaceRoot1.getWorkspace().getName()));
		
	}
	
	//Future practice - Create workspace with no name, invalid type, no desc, no collections, multiple collections so on.

}
