package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    // Locators
    @FindBy(css = ".login_logo")
    private WebElement loginLogo;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    // Actions
    @Step("Navigate to login page")
    public LoginPage open() {
        navigateTo("/");
        logger.info("Navigated to login page");
        return this;
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    @Step("Click login button")
    public InventoryPage clickLoginButton() {
        click(loginButton);
        return new InventoryPage();
    }

    // Assertions
    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginLogo) && isDisplayed(loginButton);
    }
}
