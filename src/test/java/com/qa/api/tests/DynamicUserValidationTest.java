package com.qa.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DynamicUserValidationTest extends BaseTest{
	
	@Test
	public void getUserTest() {

		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);

		List<Map<String, Object>> allUsers = JsonPathValidator.readListOfMaps(response, "$");
		
		 // Step 4: Validate that we have users in the response
        Assert.assertFalse(allUsers.isEmpty(), "User list is empty");

        // Step 5: Validate each user has required fields
        for (Map<String, Object> user : allUsers) {
            Assert.assertTrue(user.containsKey("name"), "Username is missing");
            Assert.assertTrue(user.containsKey("email"), "Email is missing");

        }
		
	}

}
