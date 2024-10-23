package com.qa.api.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.contants.AuthType;
import com.qa.api.utils.SchemaValidator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SchemaProductAPITest extends BaseTest{
	
	    @Test
	    public void validateProductSchema() {
//	        RestAssured.given()
//	            .baseUri("https://fakestoreapi.com")
//	            .when()
//	            .get("/products")
//	            .then()
//	            .assertThat()
//	            .statusCode(200)
//	            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("product-schema.json"));
	        
	        
	     Response response = 
	    		 restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
	     
	     SchemaValidator.validateSchema(response, "schema/product-schema.json");
	      
	        
	    }
	}



