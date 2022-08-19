package com.revature.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wdw;

    @FindBy(id="email-login-input")
    private WebElement emailLogin;

    @FindBy(id="password-login-input")
    private WebElement passwordLogin;

    @FindBy(id="login-btn-2")
    private WebElement loginButton2;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wdw = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void typeEmail(String email) {
        emailLogin.sendKeys(email);
    }

    public void typePassword(String password) {
        passwordLogin.sendKeys(password);
    }

    public void clickLoginButton2() {
        loginButton2.click();
    }
}
