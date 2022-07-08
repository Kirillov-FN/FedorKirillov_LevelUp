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
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class RefYahooDraftTest extends SeleniumTestsResources {

    protected final String sendToAddress = TestProperties.getProperty("yahoo.draftTest.mailTo");
    protected final String subject = TestProperties.getProperty("yahoo.draftTest.mailSubject")
        + System.currentTimeMillis();
    protected final String text = TestProperties.getProperty("yahoo.draftTest.text");

    @Test
    public void  loginDraftLogoff() {
        YahooMainPage mainPage = new YahooMainPage(webDriver, wait);
        mainPage.open();
        mainPage.clickEmailButton();
        YahooLoginPage loginPage = new YahooLoginPage(webDriver, wait);
        String title = loginPage.login(yahooLogin, yahooPassword);
        assertTrue(title.contains(yahooLogin.toLowerCase(Locale.ROOT) + " — Yahoo Почта"));
        YahooPostPage postPage = new YahooPostPage(webDriver, wait);
        final int templatesBeforeTest = postPage.getCountOfDraft();
        postPage.createNewMessage(sendToAddress, subject, text);
        postPage.closeDraft();
        postPage.clickFolder("Draft");
        postPage.refresh();
        postPage.acceptAlert();
        //postPage.clickFolder("Draft");
        Map<String, String> row = postPage.getElementValue(subject);
        assertEquals(templatesBeforeTest + 1, postPage.getCountOfDraft());
        assertEquals(sendToAddress.toLowerCase(Locale.ROOT), row.get("mailTo"));
        assertEquals(subject, row.get("mailSubject"));
        assertEquals(text, row.get("text"));
        postPage.clickCurrentElement(subject);
        postPage.clickButtonById("compose-send-button");
        boolean isValid = postPage.isNotifyValid("Ваше сообщение отправлено.");
        assertTrue(isValid);
        postPage.clickFolder("Sent");
        postPage.getElementValue(subject);
        row = postPage.getElementValue(subject);
        assertEquals(sendToAddress.toLowerCase(Locale.ROOT), row.get("mailTo"));
        assertEquals(subject, row.get("mailSubject"));
        assertEquals(text, row.get("text"));
        postPage.logoff();

        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }


}
