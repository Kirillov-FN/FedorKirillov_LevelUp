package selenium.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.resources.SeleniumTestsResources;
import selenium.resources.SleepUtils;

public class RamblerLogin extends SeleniumTestsResources {

    @Test
    public void  login() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        // Открываем стартовую страницу
        webDriver.navigate().to(ramblerMainPage);
        String originalWindow = webDriver.getWindowHandle();
        // Переходим на страницу входа в почту
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Войти в почту"))).click();
        // Ждем пока переключимся на новую вуладку
        for (String windowHandle : webDriver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }
        // Ищем фрейм для логина
        WebElement applicationFrame = webDriver.findElement(By.cssSelector("div > iframe"));
        webDriver.switchTo().frame(applicationFrame);
        // Ищем инпуты для Логина, Пароля
        WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        loginInput.sendKeys(ramblerLogin);
        passwordInput.sendKeys(ramblerPassword);
        //  Ищем кнопку Войти
        var loginButton = webDriver.findElement(By.cssSelector("button.rui-Button-button>span"));
        loginButton.submit(); // С Click() не работало((
        // Ждем новую страницу (Проверяем по наличию выпадающего меню)
        //SleepUtils.sleep(30000); // Капча((
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
            "button[data-cerber=\"topline::mail::user::menu_open\"]")
        ));
        assertEquals("Входящие — Рамблер/почта", webDriver.getTitle());




    }
}
