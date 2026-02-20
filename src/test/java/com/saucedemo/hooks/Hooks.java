package com.saucedemo.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.utils.DriverManager;
import com.saucedemo.utils.ScreenshotUtils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    @Before(order = 1)
    public void setup(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        Allure.description("Scenario: " + scenario.getName());
        DriverManager.initDriver();
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && config.isScreenshotOnFailure()) {
            logger.warn("Scenario FAILED: {} --- capturing screenshot", scenario.getName());
            ScreenshotUtils.attachToAllure("Failure - " + scenario.getName());
            ScreenshotUtils.saveToFile(scenario.getName());
        }

        String status = scenario.getStatus().name();
        logger.info("Scenario '{}' finished with status: {}", scenario.getName(), status);

        DriverManager.quitDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtils.attachToAllure("Step failure screenshot");
        }
    }
}
