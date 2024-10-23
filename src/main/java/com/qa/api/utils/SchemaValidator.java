package com.qa.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static org.testng.Assert.assertTrue;

public class SchemaValidator {
    public static void validateSchema(Response response, String schemaFileName) {
        try {
            response.then().assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
            System.out.println("Schema validation passed.");
        } catch (AssertionError e) {
            System.err.println("Schema validation failed: " + e.getMessage());
            assertTrue(false, "Schema validation failed: " + e.getMessage());
        }
    }
}
