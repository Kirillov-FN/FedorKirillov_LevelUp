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
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooDeleteTest extends SeleniumTestsResources {

    private final String mailTo = yahooLogin;
    private final String mailSubject = "Тема письма" + System.currentTimeMillis();
    private final String text = "Mail Text";

    @Test
    public void  loginDeleteLogoff() {
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
        assertTrue(webDriver.getTitle().contains("levelup.homework3@yahoo.com — Yahoo Почта"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Написать"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message-to-field")))
            .sendKeys(mailTo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("input[data-test-id=\"compose-subject\"]")))
            .sendKeys(mailSubject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"rte\"]>div")))
            .sendKeys(text);
        // Отправить письмо
        webDriver.findElement(By.cssSelector("button[data-test-id=\"compose-send-button\"]")).click();
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span>a")));
        String alert = webDriver.findElement(By
            .cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span")).getText();
        assertEquals("Ваше сообщение отправлено.", alert);
        // Verify, что письмо появилось в папке Входящие
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("span[data-test-folder-name=\"Inbox\"]"))).click();
        WebElement row = getElementInListBySubject(
            "//div[contains(@data-test-id,\"virtual-list\")]/ul/li",
            mailSubject);
        // Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertEquals(mailTo.toLowerCase(Locale.ROOT), row.findElement(By
            .xpath("//div[contains(@data-test-id,\"senders\")]/span")).getAttribute("title").toLowerCase(Locale.ROOT));
        assertEquals(mailSubject, row.findElement(By
            .xpath("//span[contains(@data-test-id,\"message-subject\")]")).getText());
        assertEquals(text, row.findElement(By
            .xpath("//div[contains(@data-test-id,\"snippet\")]")).getText());
        // Удалить письмо
        row.findElement(By.xpath("//button[contains(@data-test-id,\"icon-btn-checkbox\")]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector("button[data-test-id=\"toolbar-delete\"]"))).click();
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By
                .xpath("//div[@data-test-id=\"notifications\"]//span")));
        alert = webDriver.findElement(By
            .xpath("//div[@data-test-id=\"notifications\"]//span")).getText();
        assertEquals("Сообщение удалено.", alert);
        // Verify что письмо появилось в папке Корзина
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("span[data-test-folder-name=\"Trash\"]"))).click();
        assertTrue(isElementInList("//div[contains(@data-test-id,\"virtual-list\")]/ul/li", mailSubject));
        // Выйти из учётной записи
        webDriver.findElement(By.id("ybarAccountMenuOpener")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-signout-link"))).click();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }


}
