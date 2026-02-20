package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.utils.DriverManager;
import com.saucedemo.utils.WaitUtils;

public class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    private final ConfigManager config;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.config = ConfigManager.getInstance();

        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String path) {
        driver.get(config.getBaseUrl() + path);

        WaitUtils.waitForPageLoad();
    }

    protected void type(WebElement element, String text) {
        WaitUtils.waitForVisible(element).clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return WaitUtils.waitForVisible(element).getText();
    }

    protected void click(WebElement element) {
        WaitUtils.waitForClickable(element).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}
