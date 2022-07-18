package baseobjects.yahoo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YahooLoginPage extends YahooBase {

    @FindBy(id = "login-username")
    private WebElement loginInput;
    @FindBy(id = "login-signin")
    private WebElement nextButton;
    @FindBy(id = "login-passwd")
    private WebElement passwordInput;
    @FindBy(id = "ybarMailLink")
    private WebElement mailLink;
    @FindBy(id = "ybarAccountMenuOpener")
    private WebElement dropDownMenu;

    public YahooLoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void inputLogin(String login) {
        wait.until(ExpectedConditions.visibilityOf(loginInput)).sendKeys(login);
        wait.until(ExpectedConditions.visibilityOf(nextButton)).click();
    }

    public void inputPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(nextButton)).click();
    }

    public String toMailPage() {
        wait.until(ExpectedConditions.visibilityOf(mailLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(dropDownMenu));
        return driver.getTitle();
    }

    public String login(String username, String password) {
        inputLogin(username);
        inputPassword(password);
        return toMailPage();
    }
}
