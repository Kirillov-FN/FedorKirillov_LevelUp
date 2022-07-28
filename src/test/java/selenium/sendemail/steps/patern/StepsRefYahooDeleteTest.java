package selenium.sendemail.steps.patern;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.JUnitException;
import properties.TestProperties;
import selenium.resources.ScreenshotWatcher;
import selenium.resources.SeleniumTestsResources;
import selenium.sendemail.steps.patern.steps.YahooSteps;

@TestMethodOrder(OrderAnnotation.class)
public class StepsRefYahooDeleteTest extends SeleniumTestsResources {

    private final String mailTo = TestProperties.getProperty("yahoo.deleteTest.mailTo");
    private final String mailSubject = TestProperties.getProperty("yahoo.deleteTest.mailSubject")
        + System.currentTimeMillis();
    private final String text = TestProperties.getProperty("yahoo.deleteTest.text");

    @Test
    @DisplayName("Удаление письма")
    @Description("Удаление письма")
    public void  loginDeleteLogoff() {
        step = new YahooSteps(webDriver, wait);
        step.openYahoo();
        step.login(yahooLogin + "1", yahooPassword);
        step.assertThatUserLogined(yahooLogin.toLowerCase(Locale.ROOT));
        step.sendMessage(mailTo, mailSubject, text);
        step.checkMessageInFolder("Inbox", mailTo, mailSubject, text);
        step.deleteMessage();
        step.checkMessageInFolder("Trash", mailTo, mailSubject, text);
        step.logOff();
    }
}
