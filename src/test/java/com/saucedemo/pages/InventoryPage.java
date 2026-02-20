package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage {

    // Locators

    @FindBy(css = ".title")
    private WebElement pageTitle;

    // Assertions

    public boolean isInventoryPageDisplayed() {
        return getCurrentUrl().contains("inventory.hrml") && isDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }
}
