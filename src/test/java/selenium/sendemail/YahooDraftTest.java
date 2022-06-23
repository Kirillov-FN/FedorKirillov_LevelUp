package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooDraftTest extends SeleniumTestsResources {

    protected final String sendToAddress = "Kirillov-f@yandex.ru";
    protected String subject = "Тема письма" + System.currentTimeMillis();
    protected final String text = "Текст Письма";

    @Test
    public void  loginDraftLogoff() {
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
        int templatesBeforeTest = 0;
        try {
            WebElement count = webDriver
                .findElement(By
                    .cssSelector(
                        "div[data-test-folder-container=\"Draft\"]>a>span>span[data-test-id=\"displayed-count\"]"));
            if (count != null) {
                templatesBeforeTest = Integer.parseInt(count.getText());
            }
        } catch (Exception ignore) {
            templatesBeforeTest = 0;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Написать"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message-to-field")))
            .sendKeys(sendToAddress);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("input[data-test-id=\"compose-subject\"]")))
            .sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"rte\"]>div")))
            .sendKeys(text);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("button[data-test-id=\"icon-btn-close\"]")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("span[data-test-folder-name=\"Draft\"]")))
            .click();
        // Обновляем страницу для корректного отображения черновиков
        webDriver.navigate().refresh();
        // Принимаем поп-ап (сомнительное решение, может стать причиной падения теста
        // , как вариант options.add_argument("--disable-popup-blocking"))
        webDriver.switchTo().alert().accept();
        WebElement row = getElementInListBySubject("//div[contains(@data-test-id,\"virtual-list\")]/ul/li", subject);
        // Проверки
        assertEquals(templatesBeforeTest + 1,
            webDriver
                .findElements(By.xpath("//div[contains(@data-test-id,\"virtual-list\")]"
                        + "/ul/li//span[contains(@data-test-id,\"draft-indicator\")]")).size());
        assertEquals(sendToAddress, row.findElement(By
            .xpath("//div[contains(@data-test-id,\"senders\")]")).getText());
        assertEquals(subject, row.findElement(By
            .xpath("//span[contains(@data-test-id,\"message-subject\")]")).getText());
        assertEquals(text, row.findElement(By
            .xpath("//div[contains(@data-test-id,\"snippet\")]")).getText());
        // Отправка
        webDriver.findElement(By.xpath("//span[contains(@title,\"" + subject + "\")]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("button[data-test-id=\"compose-send-button\"]"))).click();
        // Verify, что письмо исчезло из черновиков
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By
                .cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span>a")));
        String alert = webDriver.findElement(By
            .cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span")).getText();
        assertEquals("Ваше сообщение отправлено.", alert);
        // Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Sent\"]")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"mail-reader-list-container\"]")));

        WebElement firstRow = getElementInListBySubject("//ul[contains(@aria-label,\"Message list\")]/li", subject)
            .findElement(By.xpath("//span[contains(@data-test-id,\"message-subject\")]"));

        assertTrue(firstRow.getText().contains(subject));
        // Выйти из учётной записи
        webDriver.findElement(By.id("ybarAccountMenuOpener")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-signout-link"))).click();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }


}
