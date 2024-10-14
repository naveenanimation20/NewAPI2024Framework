package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {
	
	
	@Test
    public void testDeleteWithPOJOLombokBuilder() {
    	User user = User.builder()
    		.name("Naveen")
    		.status("active")
    		.email("naveentestapi12@gmail.com")
    		.gender("male")
    		.build();
    	Response response = restClient.post("public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);
        
        String userId = response.jsonPath().getString("id");
        System.out.println("user id ==>"+ userId);
        
        //GET:
        Response responseGet = restClient.get("public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(responseGet.getStatusCode(), 200);
        Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
        
                
        //DELETE:
        Response responseDelete = restClient.delete("public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(responseDelete.getStatusCode(), 204);
        
        //GET the same user again:
        Response responseGet1 = restClient.get("public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(responseGet1.getStatusCode(), 404);
        Assert.assertEquals(responseGet1.jsonPath().getString("message"), "Resource not found");

    }

}
