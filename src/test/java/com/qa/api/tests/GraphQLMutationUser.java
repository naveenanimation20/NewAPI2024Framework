package com.qa.api.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GraphQLMutationUser {
    
    private static final String GRAPHQL_ENDPOINT = "https://gorest.co.in/public/v2/graphql";
    
    public String getRandomEmailID() {
		return "testautomation" + System.currentTimeMillis() + "@opencart.com";
	}
    
    @Test
    public void testCreateUserMutation() {
        // GraphQL mutation as a String - using standard string concatenation
        String mutation = "mutation CreateUser {\n"
        		+ "    createUser(\n"
        		+ "        input: {\n"
        		+ "            name: \"naveen\"\n"
        		+ "            email: \"naveenql4@gmail.com\"\n"
        		+ "            gender: \"male\"\n"
        		+ "            status: \"active\"\n"
        		+ "        }\n"
        		+ "    ) {\n"
        		+ "        user {\n"
        		+ "            email\n"
        		+ "            gender\n"
        		+ "            id\n"
        		+ "            name\n"
        		+ "            status\n"
        		+ "        }\n"
        		+ "    }\n"
        		+ "}";

        // Create JSON request body
        String requestBody = "{\"query\": \"" + mutation.replace("\n", "").replace("\"", "\\\"") + "\"}";

        // Make the POST request and validate response
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
            .body(requestBody)
            // Add any required headers here
            // .header("Authorization", "Bearer your-token")
        .when()
            .post(GRAPHQL_ENDPOINT)
        .then().log().all()
            .statusCode(200)
            .body("data.createUser.user.name", equalTo("naveen"))
            .body("data.createUser.user.email", equalTo("naveenql4@gmail.com"))
            .body("data.createUser.user.gender", equalTo("male"))
            .body("data.createUser.user.status", equalTo("active"))
            .body("data.createUser.user.id", notNullValue());
    }

    // Alternative approach using Map for request body
    @Test
    public void testCreateUserMutationUsingMap() {
    	String newEmaiId = getRandomEmailID();
        String mutation = "mutation CreateUser {\n"
        		+ "    createUser(\n"
        		+ "        input: {\n"
        		+ "            name: \"naveen\"\n"
        		+ "            email: \""+newEmaiId+"\"\n"
        		+ "            gender: \"male\"\n"
        		+ "            status: \"active\"\n"
        		+ "        }\n"
        		+ "    ) {\n"
        		+ "        user {\n"
        		+ "            email\n"
        		+ "            gender\n"
        		+ "            id\n"
        		+ "            name\n"
        		+ "            status\n"
        		+ "        }\n"
        		+ "    }\n"
        		+ "}";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", mutation);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
            .body(requestBody)
        .when()
            .post(GRAPHQL_ENDPOINT)
        .then().log().all()
            .statusCode(200)
            .body("data.createUser.user.name", equalTo("naveen"))
            .body("data.createUser.user.email", equalTo(newEmaiId))
            .body("data.createUser.user.gender", equalTo("male"))
            .body("data.createUser.user.status", equalTo("active"))
            .body("data.createUser.user.id", notNullValue());
    }
}