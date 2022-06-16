package selenium.resources;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class SeleniumTestsResources {
    protected final String ramblerMainPage = "https://www.rambler.ru/";
    protected WebDriver webDriver;
    protected final String login = "LevelUp.homework3@rambler.ru";
    protected final String password = "LevelUp_homework3";


    @BeforeAll
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }
}
