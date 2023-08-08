package stepdefinitions;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.cucumber.java.en.And;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Screenshots;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.AccountPage;

public class AccountStepDefinition {

    @Managed
    private WebDriver driver;
    private static EnvironmentVariables environmentVariables;
    public static String accountPage ;
    @Screenshots(forEachAction = true)
    @And("I update my {string} and {string}")
    public void iUpdateMyAnd(String firstNameString, String lastNameString) throws InterruptedException {
       WebElement firstName = driver.findElement(AccountPage.firstName);
       WebElement lastName = driver.findElement(AccountPage.lastName);

       firstName.click();
       firstName.clear();
       firstName.sendKeys(firstNameString);
       lastName.click();
       lastName.clear();
       lastName.sendKeys(lastNameString);

       driver.findElement(AccountPage.updateButton).click();
       Thread.sleep(1000);

    }

    @And("I see the Account Updated message")
    public void iSeeTheAccountUpdatedMessage() {
        String accountUpdateSuccess = driver.findElement(AccountPage.accountUpdateSuccess).getText();
        if (accountUpdateSuccess.contains("We've updated your account.")) {
            System.out.println("Account Updated, received success message");
        } else {
            Assert.fail("Account Success Message not found");
        }
    }
}
