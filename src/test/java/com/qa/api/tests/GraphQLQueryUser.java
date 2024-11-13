package com.qa.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GraphQLQueryUser {
	
    private static final String GRAPHQL_ENDPOINT = "https://gorest.co.in/public/v2/graphql";


    @Test
    public void testGetUserQueryWithVariables() {
        String query = "query User($userId:ID!) {\n"
        		+ "    user(id: $userId) {\n"
        		+ "        email\n"
        		+ "        gender\n"
        		+ "        id\n"
        		+ "        name\n"
        		+ "        status\n"
        		+ "    }\n"
        		+ "}";

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", "7516106");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
            .body(requestBody)
        .when()
            .post(GRAPHQL_ENDPOINT)
        .then().log().all()
            .statusCode(200)
            .body("data.user.id", equalTo(7516106))
            .body("data.user.email", not(emptyString()))
            .body("data.user.name", not(emptyString()))
            .body("data.user.gender", not(emptyString()))
            .body("data.user.status", not(emptyString()));
    }
    
    
    private String readGraphQLQuery(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    @Test
    public void testGetUserQueryWithVariablesWithGraphqlFile() throws IOException {
        String query = readGraphQLQuery("src/test/resources/graphql/getUserQuery.graphql");

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", 7516106);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
            .body(requestBody)
        .when()
            .post(GRAPHQL_ENDPOINT)
        .then().log().all()
            .statusCode(200)
            .body("data.user.id", equalTo(7516106))
            .body("data.user.email", not(emptyString()))
            .body("data.user.name", not(emptyString()))
            .body("data.user.gender", not(emptyString()))
            .body("data.user.status", not(emptyString()));
    }

}
