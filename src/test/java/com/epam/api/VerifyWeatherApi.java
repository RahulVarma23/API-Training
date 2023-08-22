package com.epam.api;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyWeatherApi {
	
	String apiKey = "ce3d48a055dcc47c4b38038c29ee7241";
	float longitude;
	float latitude;
	
	private RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://api.openweathermap.org/data/2.5")
			.setContentType("application/json").build();
	
	@Test
	public void testWeatherApi() {
		Response response1 = given().
				                  spec(requestSpecification).
				                  queryParam("q", "Hyderabad").
				                  queryParam("apiKey", apiKey).
				                  
				       
				            when().
				                  get("/weather").
				            then().extract().response();
		
		int statusCode = response1.statusCode();
		Assert.assertEquals(statusCode, 200);	
		
		longitude = response1.path("coord.lon");
	    latitude = response1.path("coord.lat");
	    
	    Response response2 = given().
                spec(requestSpecification).
                queryParam("lat", latitude).
                queryParam("lon", longitude).
                queryParam("apiKey", apiKey).
                
     
          when().
                get("/weather").
          then().extract().response();

	   System.out.println(response2.body().asString()); 

        int stCode = response2.statusCode();
        Assert.assertEquals(stCode, 200);
        
        String name = response2.path("name").toString();
        Assert.assertEquals(name, "Hyderabad");
        
        String country = response2.path("sys.country").toString();
        Assert.assertEquals(country, "IN");
        
        float minTemp = response2.path("main.temp_min");   
        Assert.assertTrue(minTemp>0);
        
        float temp = response2.path("main.temp");   
        Assert.assertTrue(temp > 0);        
	}
}
