package com.qa.api.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIWithDeserializeTest extends BaseTest {

	@Test
	public void getProductsTest_With_POJO_Desierlization() {

		// RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);

		Product[] product = JsonUtils.deserialize(response, Product[].class);

		for (Product p : product) {
			System.out.println("ID : " + p.getId());
			System.out.println("Title: " + p.getTitle());
			System.out.println("Price: " + p.getPrice());
			System.out.println("Rate: " + p.getRating().getRate());
			System.out.println("Count: " + p.getRating().getCount());

			System.out.println("------------");

		}

	}

}
