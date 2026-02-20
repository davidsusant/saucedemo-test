package com.saucedemo.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.config.ConfigManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ConfigManager config = ConfigManager.getInstance();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initDriver();
        }

        return driverThreadLocal.get();
    }

    public static void initDriver() {
        String browser = config.getBrowser().toLowerCase();
        logger.info("Initializing {} driver (headless={})", browser, config.isHeadless());

        WebDriver driver = switch (browser) {
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            default -> createChromeDriver();
        };

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
        logger.info("Driver initialized successfully");
    }

    private static ChromeDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--disable-notifications",
                "--disable-popup-blocking");

        return new ChromeDriver(options);
    }

    private static FirefoxDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (config.isHeadless()) {
            options.addArguments("-headless");
        }

        return new FirefoxDriver(options);
    }

    private static EdgeDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        return new EdgeDriver(options);
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();

        if (driver != null) {
            try {
                driver.quit();
                logger.info("Driver quit successfully");

            } catch (Exception e) {
                logger.warn("Error quitting driver: {}", e.getMessage());

            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}
