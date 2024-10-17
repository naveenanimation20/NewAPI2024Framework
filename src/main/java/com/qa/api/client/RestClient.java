package com.qa.api.client;


import java.io.File;
import java.util.Base64;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import com.qa.api.contants.AuthType;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;

public class RestClient {
	
	
	// Define a ResponseSpecification for successful responses (200 OK)
    private ResponseSpecification responseSpec200 = expect().log().all().statusCode(200);
    
    private ResponseSpecification responseSpec200Or404 = 
    	    expect().log().all().statusCode(anyOf(equalTo(200), equalTo(404)));
    
    private ResponseSpecification responseSpec200Or201 = 
    	    expect().log().all().statusCode(anyOf(equalTo(200), equalTo(201)));


    // Define a ResponseSpecification for 201 Created
    private ResponseSpecification responseSpec201 = expect().log().all().statusCode(201);
    
    private ResponseSpecification responseSpec204 = expect().log().all().statusCode(204);


    // Define a ResponseSpecification for 400 Bad Request
    private ResponseSpecification responseSpec400 = expect().log().all().statusCode(400);

    // Define a ResponseSpecification for 401 Unauthorized
    private ResponseSpecification responseSpec401 = expect().log().all().statusCode(401);

    // Define a ResponseSpecification for 404 Not Found
    private ResponseSpecification responseSpec404 = expect().log().all().statusCode(404);

    // Define a default ResponseSpecification for other cases (can customize later)
    private ResponseSpecification defaultResponseSpec = expect().log().all();
		

   // private String baseUrl = ConfigManager.get("baseUrl");

    
    // Method to handle different authentication mechanisms
    private RequestSpecification setupRequest(AuthType authType, ContentType contentType) {
        RequestSpecification request = RestAssured.given().log().all()
                .baseUri(ConfigManager.get("baseUrl"))
                .contentType(contentType)  // Set Content-Type based on the input from Rest Assured's ContentType enum
                .accept(contentType);      // Set Accept header for response content type

        switch (authType) {
            case BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
                break;
            case CONTACTS_BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.get("contacts_api_token"));
                break;
            case OAUTH2:
                request.header("Authorization", "Bearer " + generateOAuthToken());
                break;
            case BASIC_AUTH:
                String basicAuthToken = generateBasicAuthToken();
                request.header("Authorization", "Basic " + basicAuthToken);
                break;
            case API_KEY:
                request.header("x-api-key", ConfigManager.get("apiKey"));
                break;
            case NO_AUTH:
                // No authentication header needed
            	System.out.println("no auth is required...");
                break;
        }

        return request;
    }

    // Method to generate OAuth2 token
    private String generateOAuthToken() {
        return RestAssured.given()
                .formParam("client_id", ConfigManager.get("clientId"))
                .formParam("client_secret", ConfigManager.get("clientSecret"))
                .formParam("grant_type", ConfigManager.get("grantType"))
                .post(ConfigManager.get("tokenUrl"))
                .then()
                .extract()
                .path("access_token");
    }

    // Method to generate Basic Auth token
    private String generateBasicAuthToken() {
        String credentials = ConfigManager.get("basicUsername") + ":" + ConfigManager.get("basicPassword");
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    // GET request with query params and path params
    public Response get(String endpoint, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setupRequest(authType, contentType);

        applyParams(request, queryParams, pathParams, endpoint);

        return request.get(endpoint).then().spec(responseSpec200Or404).extract().response();
    }

    // POST request with Lombok-based POJO, query params, and path params
    public <T> Response post(String endpoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setupRequest(authType, contentType);

        applyParams(request, queryParams, pathParams, endpoint);

        return request.body(body).post(endpoint).then().spec(responseSpec200Or201).extract().response();
    }

    // PUT request
    public <T> Response put(String endpoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setupRequest(authType, contentType);

        applyParams(request, queryParams, pathParams, endpoint);

        return request.body(body).put(endpoint).then().spec(responseSpec200).extract().response();
    }

    // PATCH request
    public <T> Response patch(String endpoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setupRequest(authType, contentType);

        applyParams(request, queryParams, pathParams, endpoint);

        return request.body(body).patch(endpoint).then().spec(responseSpec200).extract().response();
    }

    // DELETE request
    public Response delete(String endpoint, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setupRequest(authType, contentType);

        applyParams(request, queryParams, pathParams, endpoint);

        return request.delete(endpoint).then().spec(responseSpec204).extract().response();
    }
    
    
 // Add a method to handle POST requests with a file
    public Response postWithFile(String endpoint, File file, 
                                 Map<String, String> queryParams, 
                                 Map<String, String> pathParams, 
                                 AuthType authType, ContentType contentType) {
    	
    	RequestSpecification request = setupRequest(authType, contentType);

    	applyParams(request, queryParams, pathParams, endpoint);

        return request.body(file).post(endpoint).then().log().all().spec(responseSpec201).extract().response();
    }
    
    
    // Consolidated method for handling query and path parameters
    private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams, String endpoint) {
        if (queryParams != null) {
            request.queryParams(queryParams);
        }
        if (pathParams != null) {
            endpoint = replacePathParams(endpoint, pathParams);
        }
    }
    

    // Helper method to replace path params in the endpoint
    private String replacePathParams(String endpoint, Map<String, String> pathParams) {
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return endpoint;
    }
}

