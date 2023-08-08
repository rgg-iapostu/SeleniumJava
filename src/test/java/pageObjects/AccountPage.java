package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = https://perf.ruelala.com/boutique/
public class AccountPage {
    public AccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public static By firstName = By.xpath("//*[@id=\"id_first_name\"]");
    public static By lastName = By.xpath("//*[@id=\"id_last_name\"]");

    public static By updateButton = By.xpath("//*[@id=\"member_info_form\"]/header/button");

    public static By accountUpdateSuccess = By.xpath("/html/body/div[2]/div/div/section[1]/div");

}