package com.qa.api.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import net.javacrumbs.jsonunit.core.Option;



public class CompareTwoJSONSTest extends BaseTest{
	
//	JSONAssert is a popular library for comparing JSON objects in Java.
//	It allows you to perform strict or lenient comparisons.
//	Strict Comparison checks that both JSON objects are identical, including their structure.
//	Lenient Comparison checks that all fields in the expected JSON exist in the actual JSON but may ignore the order of elements.
	
	
	@Test
	public void jsonCompareTest() {

		String expectedJson = "{\"name\":\"John\",\"age\":30}";
		String actualJson = "{\"age\":30,\"name\":\"John\"}";
		
		// Strict comparison
		JSONAssert.assertEquals(expectedJson, actualJson, true);

		// Lenient comparison
		JSONAssert.assertEquals(expectedJson, actualJson, false);
		
		
		String expected = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"bird\",\"fish\"]}],pets:[]}";
		String actual = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"cat\",\"fish\"]}],pets:[]}";
		JSONAssert.assertEquals(expected, actual, false);

	}
	
	@Test
	public void jsonCompareUSERAPI() {
		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users/7484388", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		String actualJson = response.getBody().asString();
		String expectedJson = "{\n"
				+ "    \"id\": 7484388,\n"
				+ "    \"name\": \"Jagadish Reddy\",\n"
				+ "    \"email\": \"reddy_jagadish@wyman.test\",\n"
				+ "    \"gender\": \"male\",\n"
				+ "    \"status\": \"active\"\n"
				+ "}";
		
		
		JSONAssert.assertEquals(expectedJson, actualJson, true);

		
	}
	
	@Test
	public void jsonLargeCompareAPI() throws IOException {
		Response response = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		String actualJson = response.getBody().asString();
        String expectedJson = Files.readString(Paths.get("./src/test/resources/jsons/products.json"));		
		JSONAssert.assertEquals(expectedJson, actualJson, true);
	}
	
		

}
