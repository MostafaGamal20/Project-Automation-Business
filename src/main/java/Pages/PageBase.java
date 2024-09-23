package Pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PageBase {

    protected static WebDriver driver;
    WebDriverWait webDriverWait;
    private final long timeoutInSeconds = 200;
    private static Logger log = Logger.getLogger(PageBase.class.getName());
    protected By continueEditingBtn = By.id("popup-trigger");
    protected static dashboard_Page dashboard_page;
    protected Actions Action;
    private WebDriverWait wait;


    // Constructor
    public PageBase(WebDriver driver) {
        //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        PageBase.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        PageFactory.initElements(driver, this);
    }

    //sendKeys
    protected void setTextElement(WebElement TextBox, String value) {
        TextBox.sendKeys(value);
        //TextBox.sendKeys(Keys.TAB);
    }

    // wait Until ElementTo Be Visible
    protected WebElement waitUntilElementToBevisible(WebElement webElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        return webElement;
    }

    protected WebElement waitUntilElementToBevisible(By locator) {
        // return
        // webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

    protected void waitUntilElementToBeClickable(WebElement webElement) {

        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));

    }

    protected WebElement waitUntilElementToBeClickable(By locator) {


        // return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));

    }

    protected WebElement waitUntilElementToPresent(By locator) {

        return new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    //click Java Script
    protected void click_JS(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
    }

    //click
    protected void click(WebElement webElement) {

        for (int i = 0; i < 10; i++) {

            try {
                webElement.click();
                break;
            } catch (ElementClickInterceptedException e) {
                log.info("ElementClickInterceptedException>>" + webElement.getText());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    //verify_URL_Contains
    protected static boolean verifyURLContains(String keyword, int duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        try {
            wait.until(ExpectedConditions.urlContains(keyword));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    protected Boolean isElementPresentInDom(By by) {
        // long driverTimeOut=0;
        // driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean isElementPresent;
        log.info("In isElementPresentInDom>>by>" + by.toString());
        int elemSize = 0;
        int dupLicateElementCounter = 0;

        for (int i = 0; i < 10; i++) {
            elemSize = driver.findElements(by).size();

            log.info("elemSize>>" + elemSize);
            if (elemSize == 1) {
                break;
            }
            if (elemSize > 1) {
                dupLicateElementCounter = dupLicateElementCounter + 1;

                if (dupLicateElementCounter > 5) {
                    break;
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (elemSize == 0) {
            log.info("in if>> elemSize==0");
            Assert.fail("Element not exist");
            System.exit(0);

            return false;
        } else if (elemSize == 1) {
            log.info("in if>> elemSize==1 >> return true");
            return true;
        } else if (elemSize > 1) {
            log.info("in if>> elemSize>1");
            Assert.fail("Element exists more than once");
            log.info("Element exists more than once>>" + elemSize + ">>" + by.toString());
            System.exit(0);

            return false;
        }
        return null;
    }

    public void handleDiscardedPopup() throws InterruptedException {

        log.info("Starting handleDiscardedPopup ...");
        Thread.sleep(4000);
        if (isElementPresentInDom(continueEditingBtn)) {
            log.info("handleDiscardedPopup >>Inside if");
            waitUntilElementToBeClickable(continueEditingBtn).click();
        }

        log.info("End of handleDiscardedPopup ...");
    }

    public void deleteAllCookies() {
        PageBase.driver.manage().deleteAllCookies();
    }

    public void notifications() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

    }

    protected boolean isElementDisplayed(By element) {

        try {
            waitUntilElementToBevisible(element);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }

    protected int isElementPresentInDomWithoutInterruption(By by) {
        // long driverTimeOut=0;
        // driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean isElementPresent;
        log.info("In isElementPresentInDomWithoutInterruption>>by>" + by.toString());
        int elemSize = 0;
        int dupLicateElementCounter = 0;

        for (int i = 0; i < 10; i++) {
            elemSize = driver.findElements(by).size();

            log.info("elemSize>>" + elemSize);
            if (elemSize > 0) {
                return elemSize;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return elemSize;
    }

    public String getValueFromField(String id) {
        String value;
        value = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.getElementById('" + id + "').value");
        return value;
    }
    public String getValueFromFields(String id) {
        try {
            // Wait for the element to be present and visible
            WebElement element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(id)));

            // Check if the element is found
            if (element != null) {
                // Use JavaScript to get the value
                return (String) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].value;", element);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching the value from field: " + e.getMessage());
        }
        return null; // Return null if the element is not found or there's an error
    }

    public String useRequestId(String id) {
        System.out.println("Using Request ID: " + id);
        return id;
    }
    public void moveToElement(WebElement elementMove) {
        Action = new Actions(driver);
        WebElement Move = waitUntilElementToBevisible(elementMove);
        Action.moveToElement(Move).doubleClick().perform();
    }

    protected WebElement waitUntilElementToBeClickableSpecificSec(By locator, int seconds) {

        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(locator));

    }

    protected WebElement waitUntilElementToBeClickableSpecificSec(WebElement element, int seconds) {

        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(element));

    }
}






