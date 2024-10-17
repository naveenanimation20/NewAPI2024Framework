package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.api.exceptions.APIFrameworkException;

import io.restassured.response.Response;

public class JsonPathValidator {
	
	private static String getJsonResponseAsString(Response response) {
        return response.getBody().asString();
	}
	
	
	public static <T> T read(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIFrameworkException(jsonPath + "is not found...");
        }
	}
	
	public static <T> List<T> readList(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIFrameworkException(jsonPath + "is not found...");
        }
	}
	
	
	public static <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIFrameworkException(jsonPath + "is not found...");
        }
	}
		

}
