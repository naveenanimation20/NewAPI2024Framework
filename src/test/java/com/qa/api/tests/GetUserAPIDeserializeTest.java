package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserAPIDeserializeTest extends BaseTest {

	@Test
	public void getUserTest() {

		Response response = restClient.get("/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);

		User[] userRes = JsonUtils.deserialize(response, User[].class);

		for (User user : userRes) {
			System.out.println("ID : " + user.getId());
			System.out.println("NAME : " + user.getName());
			System.out.println("EMAIL : " + user.getEmail());
			System.out.println("STATUS : " + user.getStatus());
			System.out.println("GENDER : " + user.getGender());

			System.out.println("----------------------");

		}
	}

	@Test
	public void createUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";

		// 1. create a user : POST
		User user = User.builder().name("Naveen").status("active").email("naveentestapii12@gmail.com").gender("male")
				.build();
		Response response = restClient.post("/public/v2/users", user, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);

		Integer userId = response.jsonPath().getInt("id");
		System.out.println("user id ==>" + userId);

		// GET:
		Response responseGet = restClient.get("/public/v2/users/"+ userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);

		// De-Serialization: JSON to POJO

		User userRes = JsonUtils.deserialize(responseGet, User.class);

		System.out.println(userRes.getId() + " " + userRes.getName() + " " + userRes.getGender() + " "+ userRes.getStatus() + " " + userRes.getEmail());
		Assert.assertEquals(userRes.getId(), userId);
		Assert.assertEquals(userRes.getEmail(), user.getEmail());
		Assert.assertEquals(userRes.getGender(), user.getGender());
		Assert.assertEquals(userRes.getStatus(), user.getStatus());
		Assert.assertEquals(userRes.getName(), user.getName());

	}

}
