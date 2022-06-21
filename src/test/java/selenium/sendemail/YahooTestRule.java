package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Locale;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooTestRule extends SeleniumTestsResources {

    private final String mailTo = yahooLogin;
    private final String mailSubject = subject + System.currentTimeMillis() + " Тест";
    private final String text = "Mail Text";

    @Test
    @Order(1)
    public void  login() {
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
    }

    @Test
    @Order(2)
    public void createNewMessage() {
        // Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
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
        String alert;
        var implicitWaitTimeout = webDriver.manage().timeouts().getImplicitWaitTimeout();
        webDriver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                    .cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span>a")));
        } finally {
            webDriver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
        alert = webDriver.findElement(By.cssSelector("div[data-test-id=\"notifications\"]>div>div>div>span")).getText();
        assertEquals("Ваше сообщение отправлено.", alert);
        // Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Sent\"]")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"mail-reader-list-container\"]")));
        WebElement firstRowSubject = null;
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions
                .textToBePresentInElementLocated(By
                    .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                        + "//span[contains(@data-test-id,\"message-subject\")]"), mailSubject));
            firstRowSubject = webDriver.findElement(By
                .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                + "//span[contains(@data-test-id,\"message-subject\")]"));
        } catch (Exception e) {
            System.out.printf("Не дождались в отправленных письма с темой: " + mailSubject);
        } finally {
            webDriver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
        assertTrue(firstRowSubject.getText().contains(mailSubject), "Expected: " + mailSubject + " Actual: "
            + firstRowSubject.getText());
        // Verify, что письмо появилось в папке «Тест»
        // Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-test-folder-name=\"Тест\"]")))
            .click();
        WebElement firstRowFrom = null;
        WebElement firstRowText = null;
        firstRowSubject = null;
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            webDriver.navigate().refresh();
            wait.until(ExpectedConditions
                .textToBePresentInElementLocated(By
                    .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                        + "//span[contains(@data-test-id,\"message-subject\")]"), mailSubject));
            firstRowSubject = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                    .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                        + "//span[contains(@data-test-id,\"message-subject\")]")));
            firstRowFrom = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//ul[contains(@aria-label,\"Message list\")]"
                + "/li[position()=2]"
                + "//div[contains(@data-test-id,\"senders\")]/span")));
            firstRowText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//ul[contains(@aria-label,\"Message list\")]"
                + "/li[position()=2]"
                + "//div[contains(@data-test-id,\"snippet\")]/div")));
        } catch (Exception e) {
            webDriver.navigate().refresh();
        } finally {
            webDriver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
        // Если Письмо не подгрузилось
        if (firstRowSubject == null) {
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            try {
                wait.until(ExpectedConditions
                    .textToBePresentInElementLocated(By
                        .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                            + "//span[contains(@data-test-id,\"message-subject\")]"), mailSubject));
                firstRowSubject = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By
                        .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                            + "//span[contains(@data-test-id,\"message-subject\")]")));
                firstRowFrom = webDriver.findElement(By.xpath("//ul[contains(@aria-label,\"Message list\")]"
                    + "/li[position()=2]"
                    + "//div[contains(@data-test-id,\"senders\")]/span"));
                firstRowText = webDriver.findElement(By.xpath("//ul[contains(@aria-label,\"Message list\")]"
                    + "/li[position()=2]"
                    + "//div[contains(@data-test-id,\"snippet\")]/div"));
            } catch (Exception e) {
                System.out.printf("Не дождались письма с темой: " + mailSubject);
            } finally {
                webDriver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
            }
        }
        assertEquals(yahooLogin.toLowerCase(Locale.ROOT), firstRowFrom.getAttribute("title").toLowerCase(Locale.ROOT));
        assertTrue(firstRowSubject.getText().contains(mailSubject), "Expected: " + mailSubject + " Actual: "
            + firstRowSubject.getText());
        assertEquals(text, firstRowText.getText());
    }

    @Test
    @Order(3)
    public void exit() {
        // Выйти из учётной записи
        webDriver.findElement(By.id("ybarAccountMenuOpener")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-signout-link"))).click();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }
}
