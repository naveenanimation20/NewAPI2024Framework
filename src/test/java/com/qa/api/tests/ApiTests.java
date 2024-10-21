package com.qa.api.tests;



import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiTests extends BaseTest {

    @Test
    public void testGetWithJsonContentType() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "naveen");

        Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    @Test
    public void testGetWithJsonContentTypeWithAuthHeader() {
        Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    @Test
    public void testPostWithJsonFile() {
        // Path to the JSON file that contains the request body
        File jsonFile = new File("./src/test/resources/jsons/user.json");
        Response response = restClient.postWithFile(BASE_URL_GOREST, "/public/v2/users", jsonFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    
    
 // Utility method to generate a random email
    public String generateRandomEmail() {
        String randomString = UUID.randomUUID().toString().substring(0, 5);  // generates a 5-character random string
        return randomString + "@example.com";
    }

    // DataProvider method supplying different User objects with random email IDs
    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        return new Object[][] {
            { new User(null, "Naveen", generateRandomEmail(), "male", "active") },
            { new User(null, "Priya", generateRandomEmail(), "female", "active") },
            { new User(null, "John", generateRandomEmail(), "male", "inactive") }
        };
    }

    // Test method that takes User data from the DataProvider
    @Test(dataProvider = "userDataProvider")
    public void testPostWithPOJOLombok(User user) {
        RestClient restClient = new RestClient();
        Response response = restClient.post(BASE_URL_GOREST, "public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    
    
    
//    @Test
//    public void testPostWithPOJOLombok() {
//    	User user = new User("Naveen", "Naveenapifw2@gmail.com", "male", "active");
//    	Response response = restClient.post("public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
//        Assert.assertEquals(response.getStatusCode(), 201);
//    }
    
    @Test
    public void testPostWithPOJOLombokBuilder() {
    	User user = User.builder()
    		.name("Naveen")
    		.status("active")
    		.email("naveentestapiz@gmail.com")
    		.gender("male")
    		.build();
    	Response response = restClient.post(BASE_URL_GOREST, "public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    

    
}

