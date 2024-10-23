package com.qa.api.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.ConfigManager;
import com.qa.api.contants.AuthType;
import com.qa.api.pojo.Credentials;
import com.qa.api.utils.SchemaValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SchemaUsersAPITest  extends BaseTest{
		
	
	
	@Test
    public void validateUserSchema() {  
        
     Response response = 
    		 restClient.get(BASE_URL_GOREST, "public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
     response.prettyPrint();
     SchemaValidator.validateSchema(response, "schema/user-schema.json");
      
        
    }

	
}
