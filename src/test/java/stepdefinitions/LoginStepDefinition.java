package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.MainPage;


import java.time.Duration;

public class LoginStepDefinition {
    @Managed
    private WebDriver driver;
    private static EnvironmentVariables environmentVariables;
    public static String accountPage ;

//@issue:SFXF-2667;



    @And("I am logged in")
    public void iAmLoggedIn() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.id_email));
        String email = driver.findElement(MainPage.id_email).getAttribute("value");
        if (email.contains("ionut.apostu@xyz.com")) {
            System.out.println("Login Successful!");
        } else {
            Assert.fail("Can't see the email! Expecting ... but found --- " + driver.findElement(MainPage.id_email).getText());
        }
    }

    @Given("I am seeing the login form")
    public void iAmSeeingTheLoginForm() {
        accountPage = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("account.page");
        driver.get(accountPage);
        System.out.println("Navigated to ... " + accountPage);
    }

    @And("I enter an existing email into the secure form")
    public void iEnterAnExistingEmailIntoTheSecureForm() {
        WebElement secureLogin = driver.findElement(MainPage.username);
        secureLogin.sendKeys("ionut.apostu@xyz.com");
    }

    @Then("I enter a valid password into the secure form")
    public void iEnterAValidPasswordIntoTheSecureForm() {
        WebElement securePassword = driver.findElement(MainPage.secureUsername);
        securePassword.sendKeys("password12345");
        WebElement secureLoginButton = driver.findElement(MainPage.secureLoginButton);
        secureLoginButton.click();
    }

    @And("I enter a {string}")
    public void iEnterA(String arg0) {
        WebElement securePassword = driver.findElement(MainPage.securePassword);
        securePassword.sendKeys(arg0);
        WebElement secureLoginButton = driver.findElement(MainPage.secureLoginButton);
        secureLoginButton.click();

    }

    @And("I am checking the {string}")
    public void iAmCheckingThe(String arg0) {

        String errorMsg = driver.findElement(MainPage.errorMsg).getText();
        if (errorMsg.contains(arg0)) {
            System.out.println("Success, correct message displayed");

        } else {
            Assert.fail("Errors do not match, expected " + arg0 + " and found " + errorMsg);
        }
    }
}
