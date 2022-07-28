package selenium.sendemail.steps.patern;

import io.qameta.allure.Description;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;
import selenium.sendemail.steps.patern.steps.YahooSteps;

@TestMethodOrder(OrderAnnotation.class)
public class StepsRefYahooDraftTest extends SeleniumTestsResources {

    protected final String sendToAddress = TestProperties.getProperty("yahoo.draftTest.mailTo");
    protected final String subject = TestProperties.getProperty("yahoo.draftTest.mailSubject")
        + System.currentTimeMillis();
    protected final String text = TestProperties.getProperty("yahoo.draftTest.text");

    @Test
    @DisplayName("Отправка из черновиков")
    @Description("Отправка из черновиков")
    public void  loginDraftLogoff() {
        step = new YahooSteps(webDriver, wait);
        step.openYahoo();
        step.login(yahooLogin, yahooPassword);
        step.assertThatUserLogined(yahooLogin.toLowerCase(Locale.ROOT));
        step.saveDraft(sendToAddress, subject, text);
        step.checkMessageInFolder("Draft", sendToAddress, subject, text);
        step.sendMessageFromDraft(subject);
        step.checkMessageInFolder("Sent", sendToAddress, subject, text);
        step.logOff();
    }


}
