package com.stepdefinitions.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserValidationStepDefs {

	private Response response;

	@Given("the base API URL is {string}")
	public void the_base_api_url_is(String baseUrl) {
		RestAssured.baseURI = baseUrl;
	}

	@When("I send a GET request to {string}")
	public void i_send_a_get_request_to(String endpoint) {
		response = RestAssured.given().when().get(endpoint);
		System.out.println(response.getBody());
	}

	@Then("the response status code should be {int}")
	public void the_response_status_code_should_be(Integer expectedStatusCode) {
		assertThat(response.statusCode(), equalTo(expectedStatusCode));
	}

	@Then("the user's first name should be {string}")
	public void the_user_s_first_name_should_be(String expectedName) {
		String firstName = response.jsonPath().getString("firstName");
		System.out.println("first name : "+firstName);
		assertThat(firstName, equalTo(expectedName));
	}
}
