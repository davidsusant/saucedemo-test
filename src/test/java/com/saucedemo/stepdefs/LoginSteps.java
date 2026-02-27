package com.saucedemo.stepdefs;

import org.assertj.core.api.Assertions;

import com.saucedemo.utils.TestContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        context.getLoginPage().open();
        Assertions.assertThat(context.getLoginPage().isLoginPageDisplayed())
                .as("Login page should be displayed")
                .isTrue();
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        context.getLoginPage().enterUsername(username);
        context.getLoginPage().enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        context.setInventoryPage(context.getLoginPage().clickLoginButton());
    }

    @When("I open the burger menu")
    public void i_open_the_burger_menu() {
        context.getInventoryPage().openMenu();
    }

    @When("I click logout")
    public void i_click_logout() {
        context.setLoginPage(context.getInventoryPage().logout());
    }

    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        Assertions.assertThat(context.getInventoryPage().isInventoryPageDisplayed())
                .as("Should be on inventory page")
                .isTrue();
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        Assertions.assertThat(context.getInventoryPage().getPageTitle())
                .as("Page title should match")
                .isEqualTo(expectedTitle);
    }

    @Then("I should see an error message containing {string}")
    public void i_should_see_an_error_message_containing(String expectedError) {
        Assertions.assertThat(context.getLoginPage().isErrorMessageDisplayed())
            .as("Error message should be displayed")
            .isTrue();

        Assertions.assertThat(context.getLoginPage().getErrorMessage())
            .as("Error message should contain expected text")
            .contains(expectedError);
    }

    @Then("I should be on the login page")
    public void i_should_be_on_the_login_page() {
        Assertions.assertThat(context.getLoginPage().isLoginPageDisplayed())
            .as("Should be back on login page after logout")
            .isTrue();
    }
}
