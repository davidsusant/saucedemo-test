package com.saucedemo.utils;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;

public class TestContext {
    
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }

    public void setInventoryPage(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }
}
