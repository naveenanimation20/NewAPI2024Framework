package com.qa.api.tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.internal.path.json.JSONAssertion;

public class UserAPITestWithDynamicJsonFile {
	
	
	private JsonNode readJsonFile(String filePath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(Files.readAllBytes(Paths.get(filePath)));
    }

	@Test
    public void createUser() throws Exception {
        String jsonFilePath = "src/test/resources/jsons/user.json";
        
        // Read the JSON into a JsonNode
        JsonNode userNode = readJsonFile(jsonFilePath);
        
        // Generate a unique email
        String uniqueEmail = "api" + UUID.randomUUID().toString() + "@automation.com";
        
        // Update the email field
        ObjectNode obj = ((ObjectNode) userNode);
        obj.put("email", uniqueEmail);
        
        
        // Convert the JsonNode back to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedJsonString = objectMapper.writeValueAsString(userNode);
        System.out.println("updatedJsonString ====>" + updatedJsonString);

        RestAssured.given().log().all()
            .contentType("application/json")
            .body(updatedJsonString)
            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
        .when()
            .post("https://gorest.co.in/public/v2/users")
        .then().log().all()
            .statusCode(201); 
        
        
      
    }
	

}
