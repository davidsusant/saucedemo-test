package com.saucedemo.runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features", 
    glue = {
        "com.saucedemo.stepdefs",
        "com.saucedemo.hooks"
    }, 
    plugin = {
        "pretty",
        "html:build/reports/cucumber-report.html",
        "json:build/reports/cucumber-report.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }, 
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
