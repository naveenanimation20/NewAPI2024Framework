package com.qa.api.base;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

	
	protected final String BASE_URL_REQ_RES = "https://reqres.in";
	protected final String BASE_URL_GOREST = "https://gorest.co.in";
	protected final String BASE_URL_PRODUCT = "https://fakestoreapi.com";
	protected final String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com/";
	protected final String BASE_URL_CIRCUIT = "http://ergast.com";
	protected final String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final String BASE_URL_AMEDUS = "https://test.api.amadeus.com";


	
    protected RestClient restClient;

    
    @BeforeTest
    public void setup(@Optional String baseUrl) {
    	
		RestAssured.filters(new AllureRestAssured());
    	
        // Initialize RestClient and any other setup needed before each test
        restClient = new RestClient();
    }
}

