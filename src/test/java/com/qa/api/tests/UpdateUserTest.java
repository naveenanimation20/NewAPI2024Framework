package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {
	
	
	@Test
    public void testPUTWithPOJOLombokBuilder() {
    	User user = User.builder()
    		.name("Naveen")
    		.status("active")
    		.email("naveentestapi7@gmail.com")
    		.gender("male")
    		.build();
    	Response response = restClient.post(BASE_URL_GOREST, "public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
        
        String userId = response.jsonPath().getString("id");
        System.out.println("user id ==>"+ userId);
        
        //GET:
        Response responseGet = restClient.get(BASE_URL_GOREST, "public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(responseGet.getStatusCode(), 200);
        Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
        
        
        user.setStatus("inactive");
        user.setEmail("newnaveentest7@gmail.com");
        
        //PUT:
        Response responsePUT = restClient.put(BASE_URL_GOREST, "public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(responsePUT.getStatusCode(), 200);
        Assert.assertEquals(responsePUT.jsonPath().getString("id"), userId);
        Assert.assertEquals(responsePUT.jsonPath().getString("status"), user.getStatus());
        Assert.assertEquals(responsePUT.jsonPath().getString("email"), user.getEmail());

        
    }

}
