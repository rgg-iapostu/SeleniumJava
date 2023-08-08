Created by Ionut Apostu for Rue Gilt Groupe test assignment

Choices

First of all, let me explain my choices:

Selenium - one of the most popular open source frameworks for testing web apps
Java - very popular in the automation world
Maven - famous dependency manager for Java projects
Serenity - amazing HTML reports (can be triggered by running maven's specific goals)

Warning: If running this with Maven, make sure that the Surefire and Failsafe plugins don't collide (conflicts). Also, this is built on JavaSDK 18.

If running through the Cucumber runner, you won't get the HTML reports of Serenity
Using Java 18 and Maven 3.0.0-M5

Disclaimer: I didn't use the page object model or the page factory model for defining all of the steps because I wanted to show exactly how the steps are done. If I was to use the page object model, that is most traditionally used across the industry, I would have created a separate class, which contained all the elements, located with either with By (POM) or @FindBy (PFM)

Here's an example of the Page Object Model:

<code>
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.ruelala.com/boutique/");
    }

    public void enterEmail(String email) {
        driver.findElement(By.id("landing_email")).sendKeys(email);
        driver.findElement(By.id("registration_continue")).click();
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("register_password")).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(By.id("registration_submit")).click();
    }

    public String getRegistrationSuccessMessage() {
        return driver.findElement(By.className("rue-button")).getText();
    }

    public String getPasswordErrorMessage() {
        return driver.findElement(By.className("except-email-opt-in")).getText();
    }
}
</code>




And here's an example of the Page Factory Model (PFM)

<code>
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
private final WebDriver driver;

    @FindBy(id = "landing_email")
    private WebElement emailInput;

    @FindBy(id = "registration_continue")
    private WebElement continueButton;

    @FindBy(id = "register_password")
    private WebElement passwordInput;

    @FindBy(id = "registration_submit")
    private WebElement registerButton;

    @FindBy(className = "rue-button")
    private WebElement registrationSuccessMessage;

    @FindBy(className = "except-email-opt-in")
    private WebElement passwordErrorMessage;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.ruelala.com/boutique/");
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
        continueButton.click();
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public String getRegistrationSuccessMessage() {
        return registrationSuccessMessage.getText();
    }
    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText();
    }
}
</code>

Here is a comparison between the two:

Pros of POM:

More readable due to encapsulation of page elements within a PageObject class
Offers better maintenance, as changes to elements or functionality are isolated within the respective PageObject class
Inherently modular and can be easily extended, reused, or refactored as necessary
More stable, as elements are encapsulated within the PageObject class and are less prone to changes
Offers better test organization due to the modular structure of PageObject classes

Cons of POM:

Slower, as the PageObject class is initialized each time a page is accessed
Requires explicit declaration and definition of locators and page elements within the PageObject class


Pros of Page Factory Model:

Code can be less cluttered due to the use of annotations to auto-inject locators and page elements
Faster, as elements are initialized only when required
Not as strong as POM, but still more modular than raw Selenium code
May require less code to organize and maintain test cases


Cons of Page Factory Model:

Code can be cluttered due to explicit element initialization using annotations
Maintenance can be more difficult due to scattered elements throughout the codebase
May be less stable, as locators are not always guaranteed to be unique or stable over time



Another very popular technology, although younger than Selenium, is Cypress.

Here's an example of a test that would work with the registration process for the Ruelala.com website:
<code>
describe('Registration process', () => {
beforeEach(() => {
cy.visit('https://www.ruelala.com/boutique/')
})

it('should register with valid email and password', () => {
cy.get('#landing_email').type(faker.internet.email())
cy.get('#registration_continue').click()
cy.get('#register_password').type('ionutapostu')
cy.get('#registration_submit').click()
cy.contains('Shop Now').click()
cy.contains('My Account').trigger('mouseover')
cy.contains('Account Info').click()
cy.get('.rue-button').should('have.text', 'Update Member Info')
})

it('should show password error with short password', () => {
cy.get('#landing_email').type(faker.internet.email())
cy.get('#registration_continue').click()
cy.get('#register_password').type('short')
cy.get('#registration_submit').click()
cy.contains('Password must be at least 10 characters.').should('be.visible')
})
})
</code>

Also, here is an example of the same test working in Appium (mobile):

const {Builder, By, Key, until} = require('selenium-webdriver');
const faker = require('faker');

describe('RueLaLa Mobile App', function() {
let driver;

    before(async function() {
        let capabilities = {
            'platformName': 'iOS',
            'platformVersion': '12.4',
            'deviceName': 'iPhone 8',
            'app': '/path/to/RueLaLa.app',
            'automationName': 'XCUITest',
            'noReset': true
        }
        driver = await new Builder().withCapabilities(capabilities).build();
        await driver.manage().setTimeouts({ implicit: 5000 });
    });

    it('should register a new user', async function() {
        await driver.findElement(By.id('landing_email')).sendKeys(faker.internet.emailAddress());
        await driver.findElement(By.id('registration_continue')).click();
        await driver.findElement(By.id('register_password')).sendKeys('ionutapostu');
        await driver.findElement(By.id('registration_submit')).click();

        let successPage = await driver.findElement(By.linkText('Shop Now')).isDisplayed();
        assert.isTrue(successPage, 'Registration was not successful');
    });

    it('should display password error message', async function() {
        await driver.findElement(By.id('landing_email')).sendKeys(faker.internet.emailAddress());
        await driver.findElement(By.id('registration_continue')).click();
        await driver.findElement(By.id('register_password')).sendKeys('short');
        await driver.findElement(By.id('registration_submit')).click();

        let passwordError = await driver.findElement(By.className('except-email-opt-in')).getText();
        assert.equal(passwordError, 'Password must be at least 10 characters.', 'Password error message was not displayed');
    });

    after(async function() {
        await driver.quit();
    });
});

(here I had to assume some values)

Additional testcases:

Navigating to the registration page from the home page: navigating to the home page of the website and then clicking on the "Register" button to ensure that it takes the user to the registration page.

Registering with an existing email address: entering an email address that has already been registered on the website and checking that the appropriate error message is displayed.

Registering with a password that includes special characters: entering a password that includes special characters (such as !@#$%^&*) and checking that the registration process is successful.

Registering with a password that includes spaces: entering a password that includes spaces and checking that the appropriate error message is displayed.

Registering with a password that is longer than the maximum allowed length: entering a password that is longer than the maximum allowed length (if present) and checking that the appropriate error message is displayed. Usually, the passwords are stored in the database on a varchar 255, so breaking that limit would be an option.

I could go into more details with the testcases, but I will limit myself to the only few I have written above.

Thought process:

When it comes to testing an application, it's crucial to have a clear understanding of the goals and features of the application and the potential risks involved. This is especially important when it comes to the registration process, email, and password fields, as they are integral to the user experience and security of the application.

To get started, it's important to understand the functionality of the registration process and how information is stored and processed by the application. This can be done by analyzing design documents and requirements, as well as through interviews with the development team.

Once you have a clear understanding of the registration process, you'll want to identify the key areas that need to be tested, in this case, the email and password fields are the scope of this exercise. For the email field, you'll want to test for things like input validation, formatting, and handling of error messages, and ensure that the email address is unique and hasn't been used by another user.

For the password field, you'll want to test for things like password strength, validation of the password input, and the handling of password resets. It's important to ensure that the password field is secure and that users can create strong passwords that meet the application's requirements.

In addition to testing the email and password fields, it's important to consider the overall user experience of the registration process. This may include things like the design and layout of the registration form, the ease of use of the form, and the handling of error messages and other feedback to the user.

I’ve also created a sample test plan for your reference (attached in the git repo)

The technical documentation will include the test plan, code explanations as well as reasoning for the test cases.


Scalability:

To test this on a large scale, you need infrastructure that supports running the test suite. My preference would be Jenkins CI/CD, with Kubernetes and Docker containers that scale and downscale, based on request.

Maintenance:

It can be achieved quite easily, but first we need to define the scope of testing. The scope can be defined if you have enough manual test cases. Then I’d determine the coverage and start translating them based on importance. I would get a regression suite running fast, then adding to the entire coverage, in time. The regression is composed of critical and high value tests that make sure that key components / features / APIs of the application are stable and reliable.
For the given example (the Selenium testcase), using the POM (Page Object Model) allows us to change the xpaths / locators in one place, thus minimising the effort of finding the same id / xpath ten times, scattered over the code and over multiple classes and changing it manually

Test failures:

That’s why I have chosen Serenity. It produces beautiful HTML reports, so that everyone is on the same page with the test case management. It can be easily read by the business and the technical people, and can easily integrate with defect management tools, like JIRA.

Also, early reporting of an issue / bug is essential. I would use the defect management tool to indicate a defect and track it throughout it’s life, until resolution.

Challenges:

One of them was that if I ran my testcases multiple times in a short interval, I would get blocked (the blocker actually comes from rue.common.js). It identifies some sort of automated or rapid input of data and blocks the Continue button for a specific time.

Tried to overcome that by implementing a rotating proxy function, but I found that the proxies used were not sufficient.

Another significant challenge was compatibility. I have a MacBook Air with the M1 processor. This causes some issues between it and Intellij IDE, because of the Rosetta translator. Also had some compatibility issues with the dependencies in the POM file (Maven), but those were solved.

Blockers:

I’d say there are many roadblocks during testing. One of the main ones was not having access to the code. This is very helpful in trying to see how that feature works, and finding edge cases that I can exploit and break the application. For educational and security purposes, of course. The main proposition here would be to get hired and get access to the code and the requirements :) 






