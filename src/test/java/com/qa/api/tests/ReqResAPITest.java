package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.ConfigManager;
import com.qa.api.contants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResAPITest extends BaseTest {
	
	
	
	
	@Test
    public void testReqRestTest() {        
        //GET:
        Response responseGet = restClient.get(BASE_URL_REQ_RES, "/api/users?page=2", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(responseGet.getStatusCode(), 200);
    }

}
