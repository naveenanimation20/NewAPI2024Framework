package com.qa.api.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CircuitJsonPathTest extends BaseTest{
	
	
	@Test
	public void getCircuitTest() {
		
		Response response = restClient.get(BASE_URL_CIRCUIT, "/api/f1/2017/circuits.json", null, null, AuthType.NO_AUTH, ContentType.JSON);
		
		Assert.assertEquals(response.statusCode(), 200);
		
		List<String> countryList = JsonPathValidator.readList(response, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
		System.out.println(countryList);
		Assert.assertTrue(countryList.contains("China"));						
		
	}
	

}
