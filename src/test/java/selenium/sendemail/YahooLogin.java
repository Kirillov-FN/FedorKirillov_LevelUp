package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.resources.SeleniumTestsResources;

@TestMethodOrder(OrderAnnotation.class)
public class YahooLogin extends SeleniumTestsResources {

    @Test
    @Order(1)
    public void  login() {
        // Открываем стартовую страницу
        webDriver.navigate().to(yahooMainPage);
        String originalWindow = webDriver.getWindowHandle();
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
        assertEquals("levelup.homework3@yahoo.com — Yahoo Почта", webDriver.getTitle());
    }

    @Test
    @Order(2)
    public void createNewMessage() {
        int templatesBeforeTest = 0;
        try {
            WebElement count = webDriver.findElement(By.cssSelector("span[data-test-id=\"displayed-count\"]"));
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
            .sendKeys(subject + (templatesBeforeTest + 1));
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
        assertEquals(subject + (templatesBeforeTest + 1), draftList.get(1).findElement(By
            .cssSelector("span[data-test-id=\"message-subject\"]")).getText());
        assertEquals(text, draftList.get(1).findElement(By
            .cssSelector("div[data-test-id=\"snippet\"]>div")).getText());
    }

    @Test
    @Order(3)
    public void sendEmail() {
        WebElement count = webDriver.findElement(By.cssSelector("span[data-test-id=\"displayed-count\"]"));
        final int templatesBeforeTest = Integer.parseInt(count.getText());
        //
        WebElement draftUl = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div[data-test-id=\"virtual-list\"]>ul")));
        List<WebElement> draftList = draftUl.findElements(By.tagName("li"));
        draftList.get(1).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("button[data-test-id=\"compose-send-button\"]"))).click();
        //
        webDriver.navigate().refresh();
        // TODO: Тут нужно ожидание
        int currentCount;
        try {
            currentCount = Integer.parseInt(wait
                .until(ExpectedConditions
                    .visibilityOfElementLocated(By
                    .cssSelector("span[data-test-id=\"displayed-count\"]")))
                .getText());
        } catch (Exception ignore) {
            currentCount = 0;
        }

        //
        assertEquals(
            templatesBeforeTest - 1, currentCount);
    }
}
