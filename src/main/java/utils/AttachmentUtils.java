package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttachmentUtils {

    public static byte[] makeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static byte[] getPageSource(WebDriver driver) {
        return driver.getPageSource().getBytes();
    }
}
