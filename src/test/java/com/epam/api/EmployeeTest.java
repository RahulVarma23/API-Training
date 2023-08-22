package com.epam.api;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.epam.pojo.Employee;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

public class EmployeeTest {
	
	@BeforeClass
	public void setUp() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api";
		RestAssured.basePath = "/v1";
		
		RestAssured.requestSpecification = new RequestSpecBuilder().
				                          setContentType("application/json").build();				
	}
	

	@Parameters({"successCode","statusLine","contentType","contentEncoding"})
	@Test
	public void VerifyEmployeeDetails(int successCode, String statusLine, String contentType, String contentEncoding ){
		
		Employee employee = new Employee ("ABC", 1234, 24);
		Employee employee1 = new Employee ("ABC", 4444, 25);
		
		System.out.println(employee.getName());
		
		//step1
		Response response1 = given().when().get("/employees").then().extract().response();
		int statusCode1 = response1.statusCode();
		Assert.assertEquals(statusCode1, successCode);
		String statusLine1 =  response1.statusLine();
		Assert.assertEquals(statusLine1, statusLine);
		String contentType1 = response1.contentType();
		Assert.assertEquals(contentType1, contentType);
		String contenEncoding1 = response1.header("Content-Encoding");
		Assert.assertEquals(contenEncoding1, contentEncoding);
		int countOfEmployees = response1.path("data.size()");
		
		//step2
		Response response2 = given().body(employee).when().post("/create").then().extract().response();
		int statusCode2 = response2.statusCode();
		int id = Integer.parseInt(response2.path("data.id"));
		Assert.assertEquals(statusCode2, 200);
		Response response3 = given().when().get("/employees").then().extract().response();
		int statusCode3 = response3.statusCode();
		Assert.assertEquals(statusCode3, successCode);
		int updatedCountOfEmployees = response3.path("data.size()");
		Assert.assertEquals(updatedCountOfEmployees, countOfEmployees+1);
		
		//step3
		Assert.assertEquals(response3.path("data.name"), employee.getName());
		Assert.assertEquals(Integer.parseInt(response3.path("data.salary")), employee.getSalary());
		Assert.assertEquals(Integer.parseInt(response3.path("data.age")), employee.getAge());
		
		//step4
		Response response4 = given().body(employee1).when().put("/update/"+id).then().extract().response();
		int statusCode4 = response4.statusCode();
		Assert.assertEquals(statusCode4, successCode);
		
		//step5
		Response response5 = given().when().get("/employee"+id).then().extract().response();
		Assert.assertEquals(response5.path("data.name"), employee1.getName());
		int statusCode5 = response4.statusCode();
		Assert.assertEquals(statusCode5, 200);
		Assert.assertEquals(Integer.parseInt(response5.path("data.salary")), employee1.getSalary());
		Assert.assertEquals(Integer.parseInt(response5.path("data.age")), employee1.getAge());
		
		//step6
		Response response6 = given().when().delete("/employee"+id).then().extract().response();
		int statusCode6 = response6.statusCode();
		Assert.assertEquals(statusCode6, successCode);
		String statusLine6 =  response6.statusLine();
		Assert.assertEquals(statusLine6, statusLine);
		int finalCountOfEmployees = response6.path("data.size()");
		Assert.assertEquals(finalCountOfEmployees, countOfEmployees);
		
	}
}
