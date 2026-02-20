package com.saucedemo.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Allure;

public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "build/screenshots";

    private ScreenshotUtils() {
    }

    public static byte[] takeScreenshot() {
        WebDriver driver = DriverManager.getDriver();

        if (driver instanceof TakesScreenshot ts) {
            return ts.getScreenshotAs(OutputType.BYTES);
        }

        return new byte[0];
    }

    public static void attachToAllure(String name) {
        try {
            byte[] screenshot = takeScreenshot();

            if (screenshot.length > 0) {
                Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
                logger.info("Screenshot '{}' attached to Allure report", name);
            }

        } catch (Exception e) {
            logger.warn("Failed to attach screenshot to Allure: {}", e.getMessage());
        }
    }

    public static String saveToFile(String scenarioName) {
        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9_]", "_");
            String filename = safeName + "_" + timestamp + ".png";
            Path filePath = dir.resolve(filename);

            byte[] screenshot = takeScreenshot();

            if (screenshot.length > 0) {
                Files.write(filePath, screenshot);
                logger.info("Screenshot saved: {}", filePath);

                return filePath.toString();
            }

        } catch (IOException e) {
            logger.warn("Failed to save screenshot: {}", e.getMessage());
        }

        return null;
    }
}
