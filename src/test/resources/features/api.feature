Feature: API Testing with Rest Assured
# ---------------- GET USER by GET method----------------
  Scenario: Get user by ID
    Given the base API URL is "https://dummyjson.com"
    When I send a GET request to "/users/1"
    Then the response status code should be 200
    And the user's first name should be "Emily"
# ---------------- LOGIN API by POST method ----------------
  Scenario: Successful login with valid credentials
    Given the base API URL is "https://practicetestautomation.com"
    And the login endpoint is "/practice-test-login"
    When I send a POST login request with Username "student" and Password "Password123"
    Then the login response status code should be 200
  # ---------------- UI VALIDATION using Selenium ----------------
  Scenario: UI - Successful login and user validation
    Given the UI base URL is "https://practicetestautomation.com"
    And the login page path is "/practice-test-login"
    When I open the login page and login with Username "student" and Password "Password123"
    Then I should see the user dashboard
    And the displayed username should be "student"
