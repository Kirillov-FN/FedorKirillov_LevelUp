package selenium.sendemail.steps.patern;

import java.util.Locale;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;
import selenium.sendemail.steps.patern.steps.YahooSteps;

@TestMethodOrder(OrderAnnotation.class)
public class StepsRefYahooDeleteTest extends SeleniumTestsResources {

    private final String mailTo = TestProperties.getProperty("yahoo.deleteTest.mailTo");
    private final String mailSubject = TestProperties.getProperty("yahoo.deleteTest.mailSubject")
        + System.currentTimeMillis();
    private final String text = TestProperties.getProperty("yahoo.deleteTest.text");

    @Test
    public void  loginDeleteLogoff() {
        step = new YahooSteps(webDriver, wait);
        step.openYahoo();
        step.login(yahooLogin, yahooPassword);
        step.assertThatUserLogined(yahooLogin.toLowerCase(Locale.ROOT));
        step.sendMessage(mailTo, mailSubject, text);
        step.checkMessageInFolder("Inbox", mailTo, mailSubject, text);
        step.deleteMessage();
        step.checkMessageInFolder("Trash", mailTo, mailSubject, text);
        step.logOff();
    }
}
