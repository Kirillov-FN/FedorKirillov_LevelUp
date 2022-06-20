package selenium.resources;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class SeleniumTestsResources {
    protected WebDriver webDriver;
    protected final String ramblerMainPage = "https://www.rambler.ru/";
    protected final String yahooMainPage = "https://www.yahoo.com/";
    protected final String ramblerLogin = "LevelUp.homework3@rambler.ru";
    protected final String yahooLogin = "LevelUp.homework3@yahoo.com";
    protected final String ramblerPassword = "LevelUp_homework3";
    protected final String yahooPassword = "LevelUp_homework3";
    protected final String sendToAddress = "Kirillov-f@yandex.ru";
    protected final String subject = "Тема письма";
    protected final String text = "Текст Письма";
    public WebDriverWait wait;


    @BeforeAll
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        setUp();
    }

    //@BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //@AfterEach
    public void tearDown() {
        webDriver.quit();
    }

    @AfterAll
    public void down() {
        tearDown();
    }
}
