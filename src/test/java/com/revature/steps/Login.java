package com.revature.steps;

import com.revature.pages.LoginPage;
import com.revature.runner.TestRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static com.revature.runner.TestRunner.driver;
import static java.lang.Thread.sleep;

public class Login {

    public LoginPage loginPage;

    @Given("I am at the  login page")
    public void iAmAtTheLoginPage() throws InterruptedException {
        driver.get("http://127.0.0.1:5501/login.html");
        loginPage = new LoginPage(driver);
        sleep(4000);
    }

    @When("I type in a email of {string}")
    public void iTypeInAEmailOf(String email) throws InterruptedException {
        loginPage.typeEmail(email);
        sleep(4000);
    }

    @And("I type in a password of {string}")
    public void iTypeInAPasswordOf(String password) throws InterruptedException {
        loginPage.typePassword(password);
        sleep(4000);
    }

    @And("I click the login button")
    public void iClickTheLoginButton() throws InterruptedException {
        loginPage.clickLoginButton2();
        sleep(4000);
    }

    @Then("I should be redirected to the student homepage")
    public void iShouldBeRedirectedToTheStudentHomepage() throws InterruptedException {
        WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(10));
        wdw.until(ExpectedConditions.urlToBe("http://127.0.0.1:5501/user-page.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://127.0.0.1:5501/user-page.html");
        sleep(2000);
    }
}
