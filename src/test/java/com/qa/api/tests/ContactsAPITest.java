package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.ConfigManager;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.Credentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest{
	
	private String tokenId;
	
	@BeforeMethod
	public void getToken() {
		
		//https://thinking-tester-contact-list.herokuapp.com/users/login
		
		Credentials creds = Credentials.builder()
					.email("naveenanimation20@gmail.com")
					.password("test@123")
					.build();
		Response response = restClient.post(BASE_URL_CONTACTS, "/users/login", creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
		tokenId = response.jsonPath().getString("token");
		System.out.println("token id===> " + tokenId);
		ConfigManager.set("contacts_api_token", tokenId);
	
	}
	
	@Test
	public void getUserTest() {
		Response response = restClient.get(BASE_URL_CONTACTS, "/contacts", null, null, AuthType.CONTACTS_BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	
	
	
	
	
	
	

}
