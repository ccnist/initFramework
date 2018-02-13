package com.team.stepdefinitions;

import com.team.framework.DriverConfig;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginSteps {


    @Given("^I navigated to home$")
    public void i_navigated_to_home() throws Throwable {
        WebDriver driver = DriverConfig.getDriver();
        driver.get(System.getenv("website"));
    }

    @When("^I enter login id$")
    public void i_enter_login_id() throws Throwable {
        throw new PendingException();
    }

    @Then("^I should be navigated to home page$")
    public void i_should_be_navigated_to_home_page() throws Throwable {
        throw new PendingException();
    }

    @And("^I enter user name$")
    public void i_enter_user_name() throws Throwable {
        throw new PendingException();
    }

    @And("^I tap on login button$")
    public void i_tap_on_login_button() throws Throwable {
        throw new PendingException();
    }

}
