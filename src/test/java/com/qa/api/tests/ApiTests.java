package com.qa.api.tests;



import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiTests extends BaseTest {

    @Test
    public void testGetWithJsonContentType() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "naveen");

        Response response = restClient.get("/public/v2/users", queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    @Test
    public void testGetWithJsonContentTypeWithAuthHeader() {
        Response response = restClient.get("/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    @Test
    public void testPostWithJsonFile() {
        // Path to the JSON file that contains the request body
        File jsonFile = new File("./src/test/resources/jsons/user.json");
        Response response = restClient.postWithFile("/public/v2/users", jsonFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    
    @Test
    public void testPostWithPOJOLombok() {
    	User user = new User("Naveen", "Naveenapifw2@gmail.com", "male", "active");
    	Response response = restClient.post("public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    
    @Test
    public void testPostWithPOJOLombokBuilder() {
    	User user = User.builder()
    		.name("Naveen")
    		.status("active")
    		.email("naveentestapi2@gmail.com")
    		.gender("male")
    		.build();
    	Response response = restClient.post("public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    

    
}

