package com.stepdefinitions.frontend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class UIValidationStepDefs {

    private WebDriver driver;
    private String baseUrl;
    private String loginPath;

    @Before
    public void setUp() {
        // Setup WebDriver (Chrome)
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // headless for CI; remove for local debugging
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Given("the UI base URL is {string}")
    public void the_ui_base_url_is(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Given("the login page path is {string}")
    public void the_login_page_path_is(String path) {
        this.loginPath = path;
    }

    @When("I open the login page and login with Username {string} and Password {string}")
    public void i_open_the_login_page_and_login_with_username_and_password(String username, String password) {
        String url = this.baseUrl + this.loginPath;
        driver.get(url);

        // The selectors below are based on the typical practicetestautomation page structure.
        // Adjust selectors if the real page differs.
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        submitBtn.click();
    }

    @Then("I should see the user dashboard")
    public void i_should_see_the_user_dashboard() {
        // Wait for dashboard presence - simple sleep for brevity; replace with WebDriverWait in real code
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore
        }
        // Check for a dashboard indicator element
        boolean dashboardPresent = driver.findElements(By.cssSelector(".post-login,message, .dashboard, #dashboard")).size() > 0;
        // Fallback: check that current URL changed or body contains known text
        if (!dashboardPresent) {
            dashboardPresent = !driver.getCurrentUrl().contains("practice-test-login");
        }
        assertTrue("Dashboard should be visible after login", dashboardPresent);
    }

    @Then("the displayed username should be {string}")
    public void the_displayed_username_should_be(String expectedUsername) {
        // Try common selectors where username might appear
        String displayed = "";
        if (driver.findElements(By.cssSelector(".username, #username-display, .user-name")).size() > 0) {
            WebElement el = driver.findElement(By.cssSelector(".username, #username-display, .user-name"));
            displayed = el.getText().trim();
        } else if (driver.findElements(By.tagName("body")).size() > 0) {
            displayed = driver.findElement(By.tagName("body")).getText();
        }
        assertTrue("Displayed username should contain expected username", displayed.contains(expectedUsername));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
