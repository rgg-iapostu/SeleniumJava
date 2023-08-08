package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

// page_url = about:blank
public class MainPage {
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static By username = By.xpath("//*[@id=\"secure_login_username\"]");
    public static By id_email = By.xpath("//*[@id=\"id_email\"]");
    public static By secureUsername = By.xpath("//*[@id=\"secure_login_password\"]");
    public static By secureLoginButton = By.xpath("//*[@id=\"secure_login_submit\"]");
    public static By securePassword = By.xpath("//*[@id=\"secure_login_password\"]");
    public static By errorMsg = By.xpath("//*[@id=\"secure_login_form\"]/ul/li");


}