package com.runner;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.stepdefinitions.backend","com.stepdefinitions.frontend"},
    plugin = {"pretty", "html:target/cucumber-report.html"},
    monochrome = false
    
)
public class TestRunner {
}
