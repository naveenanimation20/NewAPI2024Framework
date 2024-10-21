package com.qa.api.tests;

import java.util.Map;

import org.apache.groovy.util.Maps;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAuth2APITest extends BaseTest{
	
	String accessToken;
	
//	@BeforeMethod
//	public void getToken() {
//				
//		Response response = restClient.post(BASE_URL_AMEDUS, "/v1/security/oauth2/token", null, null, null, AuthType.OAUTH2, ContentType.URLENC);
//		response.prettyPrint();
//		accessToken = response.jsonPath().getString("access_token");
//		System.out.println("Access token ===>" + accessToken);
//		
//	}

	@Test
	public void getFlightDetailsTest() {
		//RestAssured.baseURI = "https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200";
		
		Map<String, String> queryParams = Maps.of("origin", "PAR", "maxPrice", "200");
		Response response = restClient.get(BASE_URL_AMEDUS, "/v1/shopping/flight-destinations", queryParams, null, AuthType.OAUTH2, ContentType.JSON);
		response.prettyPrint();

		

	}
	
	
	
	
}
