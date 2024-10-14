package com.qa.api.base;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.api.client.ConfigManager;
import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

    protected RestClient restClient;

    
    @Parameters({"baseUrl"})
    @BeforeTest
    public void setup(@Optional String baseUrl) {
    	
		RestAssured.filters(new AllureRestAssured());
		
		//set base url in prop:
		if(baseUrl!=null) {
			ConfigManager.set("baseUrl", baseUrl);
		}
    	
        // Initialize RestClient and any other setup needed before each test
        restClient = new RestClient();
    }
}

