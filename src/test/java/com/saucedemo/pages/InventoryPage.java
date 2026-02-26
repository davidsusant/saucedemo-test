package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class InventoryPage extends BasePage {

    // Locators
    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // Actions
    @Step("Open burger menu")
    public InventoryPage openMenu() {
        click(menuButton);
        return this;
    }

    @Step("Logout")
    public LoginPage logout() {
        openMenu();
        click(logoutLink);
        return new LoginPage();
    }

    // Assertions
    public boolean isInventoryPageDisplayed() {
        return getCurrentUrl().contains("inventory.html") && isDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }
}
