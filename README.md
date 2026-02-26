# SauceDemo Test Automation Framework

A Selenium + Cucumber + TestNG + Allure test automation framework for saucedemo.com, built with Java and Gradle.

## Running Test

```bash
# Run all tests
./gradlew test

# Run specific tags
./gradlew test -Dcucumber.filter.tags="@smoke"
```
