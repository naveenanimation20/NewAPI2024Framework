package com.qa.api.base;


import org.testng.annotations.BeforeMethod;

import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

    protected RestClient restClient;

    @BeforeMethod
    public void setup() {
    	
		RestAssured.filters(new AllureRestAssured());
    	
        // Initialize RestClient and any other setup needed before each test
        restClient = new RestClient();
    }
}

