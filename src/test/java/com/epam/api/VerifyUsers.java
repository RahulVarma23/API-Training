package com.epam.api;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyUsers {
	
	private RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com")
			.setContentType("application/json").build();
	
	@Test
	public void testGetRequest() {
		Response response = given().
				                  spec(requestSpecification).
				       
				            when().
				                  get("/users").
				            then().extract().response();
		
		
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);	
		
		List<String> id = response.path("id");
		Assert.assertTrue(id.size()>3);
		
		List<String> names = response.path("name");
		List<String> actual = names.stream().filter(name->name.equals("Ervin Howell")).collect(Collectors.toList());
		Assert.assertTrue(actual.size()>=1);
	}
}
