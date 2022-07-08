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
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class RefYahooDeleteTest extends SeleniumTestsResources {

    private final String mailTo = TestProperties.getProperty("yahoo.deleteTest.mailTo");
    private final String mailSubject = TestProperties.getProperty("yahoo.deleteTest.mailSubject")
        + System.currentTimeMillis();
    private final String text = TestProperties.getProperty("yahoo.deleteTest.text");

    @Test
    public void  loginDeleteLogoff() {
        YahooMainPage mainPage = new YahooMainPage(webDriver, wait);
        mainPage.open();
        mainPage.clickEmailButton();
        YahooLoginPage loginPage = new YahooLoginPage(webDriver, wait);
        loginPage.inputLogin(yahooLogin);
        loginPage.inputPassword(yahooPassword);
        var title = loginPage.toMailPage();
        assertTrue(title.contains(yahooLogin.toLowerCase(Locale.ROOT) + " — Yahoo Почта"));
        YahooPostPage postPage = new YahooPostPage(webDriver, wait);
        postPage.createNewMessage(mailTo, mailSubject, text);
        postPage.sendMessage();
        var isAlert = postPage.isNotifyValid("Ваше сообщение отправлено.");
        assertTrue(isAlert);
        postPage.clickFolder("Inbox");
        Map<String, String> row = postPage.getElementValue(mailSubject);
        assertEquals(mailTo.toLowerCase(Locale.ROOT), row.get("mailTo"));
        assertEquals(mailSubject, row.get("mailSubject"));
        assertEquals(text, row.get("text"));
        postPage.deleteCurrentElement();
        postPage.clickDeleteButton();
        isAlert = postPage.isNotifyValid("Сообщение удалено.");
        assertTrue(isAlert);
        postPage.clickFolder("Trash");
        assertTrue(postPage.findWebElementBySubj(mailSubject) != null);
        postPage.logoff();
        assertTrue(webDriver.getCurrentUrl().equals("https://www.yahoo.com/"));
    }


}
