package selenium.sendemail.refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import baseobjects.yahoo.YahooLoginPage;
import baseobjects.yahoo.YahooMainPage;
import baseobjects.yahoo.YahooPostPage;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class RefYahooRuleTest extends SeleniumTestsResources {


    private final String mailTo = TestProperties.getProperty("yahoo.ruleTest.mailTo");
    private final String subject = TestProperties.getProperty("yahoo.ruleTest.mailSubject")
        + System.currentTimeMillis();
    private final String mailText = TestProperties.getProperty("yahoo.ruleTest.text") + System.currentTimeMillis();

    @Test
    public void  loginSendLogoff() {
        YahooMainPage mainPage = new YahooMainPage(webDriver, wait);
        mainPage.open();
        mainPage.clickEmailButton();
        YahooLoginPage loginPage = new YahooLoginPage(webDriver, wait);
        String title = loginPage.login(yahooLogin, yahooPassword);
        assertTrue(title.contains(yahooLogin.toLowerCase(Locale.ROOT) + " — Yahoo Почта"));
        YahooPostPage postPage = new YahooPostPage(webDriver, wait);
        postPage.createNewMessage(mailTo, subject, mailText);
        postPage.sendMessage();
        boolean isValid = postPage.isNotifyValid("Ваше сообщение отправлено.");
        assertTrue(isValid);
        postPage.clickFolder("Sent");
        Map<String, String> row = postPage.getElementValue(subject);
        assertEquals(mailTo.toLowerCase(Locale.ROOT), row.get("mailTo"));
        assertEquals(subject, row.get("mailSubject"));
        assertEquals(mailText, row.get("text"));
        postPage.clickFolder("Тест");
        row = postPage.getElementValue(subject);
        assertEquals(mailTo.toLowerCase(Locale.ROOT), row.get("mailTo"));
        assertEquals(subject, row.get("mailSubject"));
        assertEquals(mailText, row.get("text"));
        postPage.logoff();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }
}
