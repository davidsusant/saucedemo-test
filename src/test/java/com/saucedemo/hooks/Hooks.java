package com.saucedemo.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.utils.DriverManager;
import com.saucedemo.utils.ScreenshotUtils;
import com.saucedemo.utils.TestContext;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setup(Scenario scenario) {
        DriverManager.initDriver();
        
        logger.info("Starting scenario: {}", scenario.getName());
    }

    @After(order = 0)
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
