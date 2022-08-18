package selenium.resources;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import utils.AttachmentUtils;

public class ScreenshotWatcher  implements TestWatcher {
    WebDriver driver;
    String path;

    public ScreenshotWatcher(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable throwable) {
        // do something
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        // do something
        //captureScreenshot(driver, context.getDisplayName());
        attachScreenshot(driver);
        attachPageSource(driver);
    }

    @Attachment(type = "image/png", fileExtension = ".png")
    private byte[] attachScreenshot(final WebDriver driver) {
        return AttachmentUtils.makeScreenshot(driver);
    }

    private void attachPageSource(final WebDriver driver) {
        byte[] pageSource = AttachmentUtils.getPageSource(driver);
        Allure.addAttachment("page_source", "text/html", new ByteArrayInputStream(pageSource),
            ".html");
    }

    public void captureScreenshot(WebDriver driver, String fileName) {
        try {
            new File(path).mkdirs();
            try (FileOutputStream out =
                     new FileOutputStream(path + File.separator + "screenshot-" + fileName + ".png")) {
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
