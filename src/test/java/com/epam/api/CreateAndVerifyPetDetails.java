package com.epam.api;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.pojo.Category;
import com.epam.pojo.Pet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateAndVerifyPetDetails {

	private RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://petstore.swagger.io/v2")
			.setContentType("application/json").build();
	
	Category category = new Category (1, "dog");
	Pet pet = new Pet(12345,category, "snoopie", "pending");
	

	@Test
	public void testPostReuest() {
		Response response = given().
				                  spec(requestSpecification).
				                  body(pet).
				            when().
				                  post("/pet").
				            then().extract().response();
		
		
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);			        
	}
	
	@Test
	public void testGetRequest() {
		Response response = given().
				                  spec(requestSpecification).
				       
				            when().
				                  get("/pet/12345").
				            then().extract().response();
		
		
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);	
		Assert.assertEquals(response.contentType(), "application/json");
	
		String pet = response.path("category.name").toString();
		Assert.assertEquals(pet, "dog");	
		
		String name = response.path("name").toString();
		Assert.assertEquals(name, "snoopie");	
		
		String status = response.path("status").toString();
		Assert.assertEquals(status, "pending");	
	}
}
