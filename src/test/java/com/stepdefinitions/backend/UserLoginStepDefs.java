package com.stepdefinitions.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserLoginStepDefs {

	private Response response;
	private String endpoint="";
	@Given("the login endpoint is {string}")
	public void the_login_endpoint_is(String endpoint) {
		this.endpoint=endpoint;
		
	}
	@When("I send a POST login request with Username {string} and Password {string}")
	public void i_send_a_post_login_request_with_username_and_password(String string, String string2) {
		response = RestAssured.given().when().get(this.endpoint);
	}
	 
	@Then("the login response status code should be {int}")
	public void the_login_response_status_code_should_be(Integer expectedStatusCode) {
		System.out.println("actual response code : "+response.statusCode());
		
		assertThat(response.statusCode(), equalTo(expectedStatusCode));
		assertNotNull(response.getBody());
	}
	
}
