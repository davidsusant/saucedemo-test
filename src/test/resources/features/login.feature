Feature: User Authentication
    As a SauceDemo user
    I want to be able to log in and log out
    So that I can access the product catalog

    Background:
        Given I am on the login page

    Scenario: Successful login with standard user
        When I enter username "standard_user" and password "secret_sauce"
        And I click the login button
        Then I should be redirected to the inventory page
        And the page title should be "Products"