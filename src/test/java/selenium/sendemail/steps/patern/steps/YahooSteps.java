package selenium.sendemail.steps.patern.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import baseobjects.yahoo.YahooLoginPage;
import baseobjects.yahoo.YahooMainPage;
import baseobjects.yahoo.YahooPostPage;
import io.qameta.allure.Step;
import java.util.Locale;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YahooSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private YahooMainPage yahooMainPage;
    private YahooLoginPage yahooLoginPage;
    private YahooPostPage yahooPostPage;

    public YahooSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.yahooMainPage = new YahooMainPage(driver, wait);
        this.yahooLoginPage = new YahooLoginPage(driver, wait);
        this.yahooPostPage = new YahooPostPage(driver, wait);
    }

    @Step("Открытие страницы")
    public void openYahoo() {
        yahooMainPage.open();
        yahooMainPage.clickEmailButton();
    }

    @Step("Логин")
    public void login(String username, String password) {
        try {
            yahooLoginPage.inputLogin(username);
            yahooLoginPage.inputPassword(password);
            yahooLoginPage.toMailPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step("Проверка входа")
    public void assertThatUserLogined(String expected) {
        assertTrue(yahooLoginPage.getUserFromTitle().contains(expected));
    }

    @Step("Отправка сообщения {0} {1} {2}")
    public void sendMessage(String email, String subject, String text) {
        yahooPostPage.createNewMessage(email, subject, text);
        yahooPostPage.sendMessage();
        assertTrue(yahooPostPage.isNotifyValid("Ваше сообщение отправлено."));
    }

    @Step("Сохранение черновика")
    public void saveDraft(String email, String subject, String text) {
        yahooPostPage.createNewMessage(email, subject, text);
        yahooPostPage.closeDraft();
        yahooPostPage.clickFolder("Draft");
        yahooPostPage.refresh();
        yahooPostPage.acceptAlert();
    }

    @Step("Проверка сообщения в Каталоге {0}")
    public void checkMessageInFolder(String folderName, String email, String subject, String text) {
        yahooPostPage.clickFolder(folderName);
        Map<String, String> row = yahooPostPage.getElementValue(subject);
        assertEquals(email.toLowerCase(Locale.ROOT), row.get("mailTo").toLowerCase(Locale.ROOT));
        assertEquals(subject, row.get("mailSubject"));
        assertEquals(text, row.get("text"));
    }

    @Step("Удаление письма")
    public void deleteMessage() {
        yahooPostPage.deleteCurrentElement();
        yahooPostPage.clickDeleteButton();
        assertTrue(yahooPostPage.isNotifyValid("Сообщение удалено."));
    }

    @Step("Выход")
    public void logOff() {
        yahooPostPage.logoff();
        assertTrue(driver.getCurrentUrl().equals("https://www.yahoo.com/"));
    }

    @Step("Отправка сообщения из черновиков")
    public void sendMessageFromDraft(String subject) {
        yahooPostPage.clickCurrentElement(subject);
        yahooPostPage.clickButtonById("compose-send-button");
        assertTrue(yahooPostPage.isNotifyValid("Ваше сообщение отправлено."));
    }
}
