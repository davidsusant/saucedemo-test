# SauceDemo Test Automation Framework

A Selenium + Cucumber + TestNG + Allure test automation framework for saucedemo.com, built with Java and Gradle.

## Tech Stack

- Java v21
- Selenium WebDriver v4.18.1
- WebDriverManager v5.7.0
- Cucumber v7.15.0
- TestNG v7.9.0
- Allure v2.26.0
- AssertJ v3.25.3 (for assertions)
- Gradle v8.4

## Running Test

```bash
# Run all tests
./gradlew test

# Run specific tags
./gradlew test -Dcucumber.filter.tags="@smoke"
```
