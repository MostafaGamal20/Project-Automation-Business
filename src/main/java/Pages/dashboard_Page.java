package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Logger;


public class dashboard_Page extends PageBase {

    private final By TaskIconLocator = By.xpath("//a[@title='Tasks']");
    private final By MenuBtn = By.cssSelector("a.closeOpenSideMenu");
    private static Logger log = Logger.getLogger(PageBase.class.getName());

    public dashboard_Page(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isSettingsElementExist() {
        try {
            if (isElementPresentInDom(TaskIconLocator)) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return false;

    }

    public boolean verifyNavigatingToDashboard() {

        try {
            verifyURLContains("pages", 5);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    // open Menu
    public void openMenu() throws InterruptedException {
        //driver.navigate().refresh();
        log.info("Starting openMenu");
        try {
            log.info("Try wait for alert");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().getText();
            alert.accept();

        } catch (org.openqa.selenium.TimeoutException ex) {
            log.info("Catch Do Nothing");
        }

        try {
            log.info("Try , wait for (//i[@class='fa fa-light fa-thumbtack'])");

            if (isElementPresentInDom(By.xpath("//i[@class='fa fa-light fa-thumbtack']"))){
                waitUntilElementToBeClickable(By.xpath("//i[@class='fa fa-light fa-thumbtack']"));
            }
        } catch (Exception ex) {
            log.info("Catch The left side menu is not opened, open left menu..");
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(MenuBtn))
                    .click();
        }
    }
    // click On My App
    public void clickOnMyApp(String appName) throws InterruptedException {
        By myAppLocator = By.xpath("//span[text()=' " + appName + "']");
        // By myAppLocator = By.xpath("//li[@title = '" + appName + "']/a/span");
        // Thread.sleep(5000);
        log.info("My App Locator exist? " + isElementPresentInDom(myAppLocator));
        try {
            // log.info("Before Scrolling");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(myAppLocator)).click();
            } catch (Exception ex) {
                Actions action = new Actions(driver);
                action.moveToElement(new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(myAppLocator))).click().build().perform();
            }
        } catch (org.openqa.selenium.TimeoutException ex) {
            try {
                //  log.info("Scrolling");
                //   scroll_div_UntilElementVisibility("div.aside", driver.findElement(myAppLocator));
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(myAppLocator)).click();
            } catch (Exception exc) {
                log.info("it may be the only existing application");
            }
        }
    }
    //click On ParentMenu
    public void clickOnParentMenu(String parentMenu) throws InterruptedException {
        By parentMenuLocator = By.xpath("//span[.='" + parentMenu + "']");
        WebElement ParentMenu = waitUntilElementToBeClickable(parentMenuLocator);
        click(ParentMenu);
    }

    public  void expandSpecificModule(String modName) {
        By addBy = By.xpath("//span[contains(text(),'" + modName + "')]/../parent::div");

        log.info("addBy>>" + addBy.toString());
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!isElementPresentInDom(addBy) == true);

        log.info("isElementPresentInDom(addBy)" + isElementPresentInDom(addBy));
        click_JS(waitUntilElementToBeClickable(addBy));
    }

    public  void openModule(String modName) {
        log.info("openModule >> modName>>" + modName);
        expandSpecificModule(modName);
    }

}

