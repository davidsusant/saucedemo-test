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

    Scenario: Login fails with locked out user
        When I enter username "locked_out_user" and password "secret_sauce"
        And I click the login button
        Then I should see an error message containing "Sorry, this user has been locked out"

    Scenario: Login fails with invalid credentials
        When I enter username "invalid_user" and password "wrong_password"
        And I click the login button
        Then I should see an error message containing "Username and password do not match"

    Scenario Outline: Login fails with missing credentials
        When I enter username "<username>" and password "<password>"
        And I click the login button
        Then I should see an error message containing "<error>"

        Examples:
            | username      | password     | error                |
            |               | secret_sauce | Username is required |
            | standard_user |              | Password is required |

    @logout
    Scenario: User can log out successfully
        When I enter username "standard_user" and password "secret_sauce"
        And I click the login button
        Then I should be redirected to the inventory page
        When I open the burger menu
        And I click logout
        Then I should be on the login page