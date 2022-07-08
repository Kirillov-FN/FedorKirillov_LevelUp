package baseobjects.yahoo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.TestProperties;

public class YahooMainPage {
    private WebDriver driver;
    private WebDriverWait wait;
    @FindBy(linkText = "Sign in")
    private WebElement emailButton;

    public YahooMainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.navigate().to(TestProperties.getProperty("yahoo.MainPage"));
    }

    public void clickEmailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(emailButton)).click();
    }
}
