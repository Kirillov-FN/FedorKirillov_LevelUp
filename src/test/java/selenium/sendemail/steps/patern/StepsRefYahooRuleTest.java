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
public class StepsRefYahooRuleTest extends SeleniumTestsResources {


    private final String mailTo = TestProperties.getProperty("yahoo.ruleTest.mailTo");
    private final String subject = TestProperties.getProperty("yahoo.ruleTest.mailSubject")
        + System.currentTimeMillis();
    private final String mailText = TestProperties.getProperty("yahoo.ruleTest.text") + System.currentTimeMillis();

    @Test
    @DisplayName("Проверка правила")
    @Description("Проверка правила")
    public void  loginSendLogoff() {
        step = new YahooSteps(webDriver, wait);
        step.openYahoo();
        step.login(yahooLogin, yahooPassword);
        step.assertThatUserLogined(yahooLogin.toLowerCase(Locale.ROOT));
        step.sendMessage(mailTo, subject, mailText);
        step.checkMessageInFolder("Sent", mailTo, subject, mailText);
        step.checkMessageInFolder("Тест", mailTo, subject, mailText);
        step.logOff();
    }
}
