package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.TestProperties;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooRuleTest extends SeleniumTestsResources {


    private final String mailTo = TestProperties.getProperty("yahoo.ruleTest.mailTo");
    private final String mailSubject = TestProperties.getProperty("yahoo.ruleTest.mailSubject")
        + System.currentTimeMillis();
    private final String mailText = TestProperties.getProperty("yahoo.ruleTest.text") + System.currentTimeMillis();

    @Test
    public void  loginSendLogoff() {
        // Войти в почту
        // Открываем стартовую страницу
        webDriver.navigate().to(yahooMainPage);
        // Переходим на страницу входа в почту
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Sign in"))).click();
        // Вводим логин
        WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-username")));
        loginInput.sendKeys(yahooLogin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-signin"))).click();
        // Вводим пароль
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-passwd")));
        passwordInput.sendKeys(yahooPassword);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-signin"))).click();
        // Переходим на страницу почты
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ybarMailLink"))).click();
        // Ждем новую страницу (Проверяем по наличию выпадающего меню)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ybarAccountMenuOpener")));
        // Assert, что вход выполнен успешно
        assertTrue(webDriver.getTitle().contains("levelup.homework3@yahoo.com — Yahoo Почта"));

        // Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Написать"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message-to-field")))
            .sendKeys(mailTo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("input[data-test-id=\"compose-subject\"]")))
            .sendKeys(mailSubject);
        String text = "Mail Text";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"rte\"]>div")))
            .sendKeys(text);
        // Отправить письмо
        webDriver.findElement(By.cssSelector("button[data-test-id=\"compose-send-button\"]")).click();
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"notifications\"] a")));
        String alert = webDriver.findElement(By
            .cssSelector("div[data-test-id=\"notifications\"] span")).getText();
        assertEquals("Ваше сообщение отправлено.", alert);
        // Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Sent\"]")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"mail-reader-list-container\"]")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Sent\"]")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"mail-reader-list-container\"]")));
        assertTrue(isElementInList("//ul[contains(@aria-label,\"Message list\")]/li", mailSubject));
        // Verify, что письмо появилось в папке «Тест»
        // Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Тест\"]")))
            .click();

        WebElement row = getElementInListBySubject("//ul[contains(@aria-label,\"Message list\")]/li",
            mailSubject);
        WebElement rowSubject = row.findElement(By
            .xpath("//span[contains(@data-test-id,\"message-subject\")]"));
        WebElement rowFrom = row.findElement(By
            .xpath("//div[contains(@data-test-id,\"senders\")]/span"));
        WebElement rowText = row.findElement(By
            .xpath("//div[contains(@data-test-id,\"snippet\")]/div"));

        assertEquals(yahooLogin.toLowerCase(Locale.ROOT), rowFrom.getAttribute("title").toLowerCase(Locale.ROOT));
        assertTrue(rowSubject.getText().contains(mailSubject), "Expected: " + mailSubject + " Actual: "
            + rowSubject.getText());
        assertEquals(text, rowText.getText());
        // Выйти из учётной записи
        webDriver.findElement(By.id("ybarAccountMenuOpener")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-signout-link"))).click();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }
}
