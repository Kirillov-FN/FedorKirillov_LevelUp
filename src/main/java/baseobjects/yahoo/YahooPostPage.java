package baseobjects.yahoo;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YahooPostPage extends YahooBase {

    private final By.ByXPath path = new ByXPath("//span[contains(@data-test-id,\"message-subject\")]");
    private final By.ByXPath checkBoxPath = new ByXPath("//button[contains(@data-test-id,\"icon-btn-checkbox\")]");
    private WebElement currentElement = null;
    @FindBy(linkText = "Написать")
    private WebElement newMessage;
    @FindBy(id = "message-to-field")
    private WebElement messageTo;
    @FindBy(css = "input[data-test-id=\"compose-subject\"]")
    private WebElement messageSubject;
    @FindBy(css = "div[data-test-id=\"rte\"]>div")
    private WebElement messageText;
    @FindBy(css = "button[data-test-id=\"compose-send-button\"]")
    private WebElement sendButton;
    @FindBy(css = "div[data-test-id=\"notifications\"] span")
    private WebElement alert;
    @FindBy(xpath = "//div[contains(@data-test-id,\"virtual-list\")]/ul/li"
        + "//span[contains(@data-test-id,\"message-subject\")]")
    private WebElement webList;
    @FindBy(xpath = "//div[contains(@data-test-id,\"virtual-list\")]/ul/li")
    private List<WebElement> webElementList;
    @FindBy(css = "button[data-test-id=\"toolbar-delete\"]")
    private WebElement deleteButton;
    @FindBy(id = "profile-signout-link")
    private WebElement logoff;
    @FindBy(id = "ybarAccountMenuOpener")
    private WebElement accountMenu;
    @FindBy(css = "a[data-test-folder-name]")
    private List<WebElement> folderList;
    @FindBy(css = "div[data-test-folder-container=\"Draft\"] span[data-test-id=\"displayed-count\"]")
    private WebElement countOfDraftItems;
    @FindBy(css = "button[data-test-id=\"icon-btn-close\"]")
    private WebElement closeDraftButton;
    @FindBy(css = "button[data-test-id]")
    private List<WebElement> buttonList;

    public YahooPostPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void createNewMessage(String email, String subject, String text) {
        wait.until(ExpectedConditions.visibilityOf(newMessage)).click();
        wait.until(ExpectedConditions.visibilityOf(messageTo)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOf(messageSubject)).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOf(messageText)).sendKeys(text);
    }

    public void sendMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(sendButton)).click();
    }

    public boolean isNotifyValid(String text) {
        var isNotifyValid = wait.until(ExpectedConditions
            .textToBePresentInElement(alert, text));
        return isNotifyValid;
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public WebElement findWebElementBySubj(String subj) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(webList, subj));
        } catch (Exception ignore) {
            refresh();
        }
        List<WebElement> elements = webElementList;
        for (WebElement element : elements) {
            if (element.findElement(path)
                       .getText().equals(subj)) {
                this.currentElement = element;
                return element;
            }
        }
        return null;
    }

    public Map<String, String> getElementValue(String mailSubject) {
        findWebElementBySubj(mailSubject);
        Map<String, String> map = new HashMap<>();
        if (currentElement != null) {
            map.put("mailTo", currentElement.findElement(By.xpath("//div[contains(@data-test-id,\"senders\")]/span"))
                                            .getAttribute("title").toLowerCase(Locale.ROOT));
            map.put("mailSubject", currentElement.findElement(By
                .xpath("//span[contains(@data-test-id,\"message-subject\")]")).getText());
            map.put("text", currentElement.findElement(By
                .xpath("//div[contains(@data-test-id,\"snippet\")]")).getText());
        }
        return map;
    }

    public void deleteCurrentElement() {
        currentElement.findElement(checkBoxPath).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

    public void clickCurrentElement(String subj) {
        currentElement.findElement(By.xpath("//span[text()=\"" + subj + "\"]")).click();
    }

    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

    public void logoff() {
        wait.until(ExpectedConditions.elementToBeClickable(accountMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoff)).click();
    }

    public void clickFolder(String folderName) {
        for (WebElement element : folderList) {
            if (element.getAttribute("data-test-folder-name") != null
                && element.getAttribute("data-test-folder-name").equals(folderName)) {
                element.click();
                return;
            }
        }
    }

    public int getCountOfDraft() {
        int count;
        try {
            count = Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(countOfDraftItems)).getText());
        } catch (Exception ignore) {
            count = 0;
        }
        return count;
    }

    public void closeDraft() {
        wait.until(ExpectedConditions.visibilityOf(closeDraftButton)).click();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void clickButtonById(String id) {
        for (WebElement element : buttonList) {
            if (element.getAttribute("data-test-id") != null) {
                if (element.getAttribute("data-test-id").equals(id)) {
                    element.click();
                    return;
                }
            }
        }
    }
}
