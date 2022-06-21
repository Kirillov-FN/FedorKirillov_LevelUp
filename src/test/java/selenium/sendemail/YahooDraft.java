package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooDraft extends SeleniumTestsResources {

    @Test
    @Order(1)
    public void  login() {
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
    }

    @Test
    @Order(2)
    public void createNewMessage() {
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

        WebElement draftUl = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"virtual-list\"]>ul")));
        List<WebElement> draftList = draftUl.findElements(By.tagName("li"));
        // Проверки
        assertEquals(templatesBeforeTest + 1, draftList.size() - 1);
        assertEquals(sendToAddress, draftList.get(1).findElement(By
            .cssSelector("div[data-test-id=\"senders\"]>span>span")).getText());
        assertEquals(subject, draftList.get(1).findElement(By
            .cssSelector("span[data-test-id=\"message-subject\"]")).getText());
        assertEquals(text, draftList.get(1).findElement(By
            .cssSelector("div[data-test-id=\"snippet\"]>div")).getText());
    }

    @Test
    @Order(3)
    public void sendEmail() {
        WebElement draftUl = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"virtual-list\"]>ul")));
        List<WebElement> draftList = draftUl.findElements(By.tagName("li"));
        draftList.get(1).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("button[data-test-id=\"compose-send-button\"]"))).click();
        // Verify, что письмо исчезло из черновиков
        String alert = null;
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
        WebElement firstRow = null;
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            firstRow = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                    .xpath("//ul[contains(@aria-label,\"Message list\")]/li[position()=2]"
                        + "//span[contains(@data-test-id,\"message-subject\")]")));
        } finally {
            webDriver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
        assertTrue(firstRow.getText().contains(subject));
        // Выйти из учётной записи
        webDriver.findElement(By.id("ybarAccountMenuOpener")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-signout-link"))).click();
        assertTrue(wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/")));
    }
}
