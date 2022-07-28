package baseobjects.yahoo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.TestProperties;

public class YahooMainPage extends YahooBase {

    @FindBy(linkText = "Sign in")
    private WebElement emailButton;

    public YahooMainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.navigate().to(TestProperties.getProperty("yahoo.MainPage"));
    }

    public void clickEmailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(emailButton)).click();
    }
}
