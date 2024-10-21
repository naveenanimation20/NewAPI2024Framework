package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FakeProductAPITest extends BaseTest {
	
	@Test
    public void testFakeProductGETAPI() {        
        //GET:
        Response responseGet = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(responseGet.getStatusCode(), 200);
    }

}
