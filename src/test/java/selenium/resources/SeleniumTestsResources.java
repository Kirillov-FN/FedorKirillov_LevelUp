package selenium.resources;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class SeleniumTestsResources {
    protected WebDriver webDriver;
    protected final String yahooMainPage = "https://www.yahoo.com/";
    protected final String yahooLogin = "LevelUp.homework3@yahoo.com";
    protected final String yahooPassword = "LevelUp_homework3";

    public WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
    }

    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }

    public WebElement getElementInListBySubject(String listLocator, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By
                .xpath(listLocator + "//span[contains(@data-test-id,\"message-subject\")]"), text));
        } catch (Exception ignore) {
            webDriver.navigate().refresh();
        }
        List<WebElement> elements = webDriver.findElements(By.xpath(listLocator));
        for (WebElement element : elements) {
            if (element.findElement(By.xpath("//span[contains(@data-test-id,\"message-subject\")]"))
                       .getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public boolean isElementInList(String locator, String text) {
        return getElementInListBySubject(locator, text) != null;
    }

}
