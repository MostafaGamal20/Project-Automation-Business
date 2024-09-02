package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class TrackingRequest_Page extends PageBase {

    private static Logger log = Logger.getLogger(PageBase.class.getName());
    protected static By ISOW_DWO_Loc = By.id("isowdwo");
    protected static By FDTLLI_Loc = By.id("fdtclli");
    protected static By buttonBulk = By.xpath("//button[@title='Bulk']");
    protected static By Bulk_type_list_loc = By.xpath("//*[@id='NCMB_bulktype']/div/span");
    protected static By attach_file_loc = By.id("attach_file");
    protected static By saveRequest = By.xpath("(//button[@id='save'])[1]");
    protected static By proceed_loc = By.id("proceed");
    protected static By closeBtn = By.xpath("//button[contains(text(),'Close')]");
    protected static By Materials = By.xpath("//span[.='Materials']");
    protected static By quantity = By.id("quantity");
    protected static By Refresh=By.cssSelector("button.p-splitbutton-kanban.p-button.p-component.p-button-icon-only > i.fa.fa-refresh");

    public TrackingRequest_Page(WebDriver driver) {
        super(driver);
    }

    public void ClickOnCloseDialog() {
        if (isElementPresentInDom(closeBtn)) {
            click(waitUntilElementToBeClickableSpecificSec(closeBtn, 30));
        }
    }
    public void selectValueFromDropdown(String selectElement, String selectValue) throws InterruptedException {
        log.info("Starting select_Value ...");
        WebElement dropdown = waitUntilElementToBevisible(By.xpath("//span[contains(text(),'" + selectElement + "')]"));
        dropdown.click();
        Thread.sleep(1000);
        WebElement Element = waitUntilElementToBeClickable(By.xpath("//li[@aria-label='" + selectValue + "']"));
        Element.click();
    }
    public void fillAllMandatoryFieldRequest(String nameISOW_DWO, String nameFDTCLLI) {
        WebElement udaISOW_DWO = waitUntilElementToBevisible(ISOW_DWO_Loc);
        setTextElement(udaISOW_DWO, nameISOW_DWO);

        WebElement uda_FDTCLLI = waitUntilElementToBevisible(FDTLLI_Loc);
        setTextElement(uda_FDTCLLI, nameFDTCLLI);
    }
    // Add File By Bulk ===> Bulk process !
    public void bulkProcess(String bulk_type, String file, boolean save) {
        WebElement Bulk = waitUntilElementToBeClickable(buttonBulk);
        Bulk.click();

        WebElement bulk_type_list = waitUntilElementToBeClickable(Bulk_type_list_loc);
        click_JS(bulk_type_list);

        WebElement bulk_type_element = waitUntilElementToBeClickable(By.xpath("//span[.='" + bulk_type + "']"));
        click_JS(bulk_type_element);

        WebElement attach_file = waitUntilElementToPresent(attach_file_loc);
        setTextElement(attach_file, file);
        // Button Save in Dialog
        if (isElementPresentInDom(By.xpath("//button[@class='btn btn-primary ng-star-inserted']"))) {
            waitUntilElementToBeClickable(By.xpath("//button[@class='btn btn-primary ng-star-inserted']")).click();
        } else {
            log.info("Don't Save");
        }
    }
    public void closeDialog() throws InterruptedException {
        Thread.sleep(1000);
        By elementCloseDialogInstStage = By.cssSelector("body > div.ng-tns-c1729407856-31.p-dialog-mask.p-component-overlay.p-component-overlay-enter.p-dialog-mask-scrollblocker.ng-star-inserted > div > div.p-dialog-header.ng-tns-c1729407856-31.ng-star-inserted > div > button.p-ripple.p-element.ng-tns-c1729407856-31.p-dialog-header-icon.p-dialog-header-close.p-link.ng-star-inserted > timesicon");
        WebElement closeDialog = waitUntilElementToBeClickable(elementCloseDialogInstStage);
        click(closeDialog);
    }
    public void saveRequest() {
        WebElement bulk_type_element = waitUntilElementToBevisible(saveRequest);
        click(bulk_type_element);
    }
    public String getRequestId() {
        String requestId = getValueFromFields("//input[@id='ftthrequestid']");
        return requestId;
    }
    public void proceedBtn() {
        WebElement proceed = waitUntilElementToBevisible(proceed_loc);
        click(proceed);
    }

    public String getMessageText() {
        By MassageSuccess = By.id("swal2-html-container");
        return waitUntilElementToBevisible(MassageSuccess).getText();
    }

    public void clickFTThRequestID() throws InterruptedException {
        Thread.sleep(1000);
        WebElement clickRequest_IDe = waitUntilElementToBevisible(By.xpath("//th[contains(text(), 'FTTH Request ID')]"));
        moveToElement(clickRequest_IDe);
    }

    public boolean checkTaskDisplayInWorkSpace(String number) {
        try {
            By myTasks = By.xpath("//tr[td='" + number + "']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(myTasks));
            if (isElementPresentInDom(myTasks))
                waitUntilElementToBeClickable(By.xpath("(//tr[contains(@class, 'p-selectable-row')]//button[text()=' Open '])[1]")).click();
            System.out.println("The task Request is added Successfully");
            return true;
        } catch (Exception ex) {
            log.info("Request Task not added in Manager Workspace grid ");
            return false;
        }
    }

    public void selectTechnicians(String nameTechnician) throws InterruptedException {
        WebElement selectPanel = waitUntilElementToBevisible(By.xpath("//p-header[.='Technicians ']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectPanel);
        WebElement dropdown = waitUntilElementToBevisible(By.id("NCMB_technician1"));
        dropdown.click();
        WebElement selectElement = waitUntilElementToBevisible(By.xpath("//li[@aria-label='" + nameTechnician + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectElement);

    }

    public void proceedTo_ReturnedTo(String ProceedTo) {
        WebElement Proceed = waitUntilElementToBevisible(By.xpath("//span[contains(text(), '" + ProceedTo + "')]"));
        Proceed.click();

    }

    public void selectStore(String Store) throws InterruptedException {
        WebElement clickStore = waitUntilElementToBevisible(By.id("NCMB_store"));
        clickStore.click();

        WebElement typeStore = waitUntilElementToBevisible(By.xpath("//li[@aria-label='" + Store + "']"));
        typeStore.click();
        Thread.sleep(1000);
    }

    public void addMaterials(String selectCategory, String selectValue, String selectMaterials, String selectValueM, String Quantity, String commentVal, String IssueToVal) throws InterruptedException {
        WebElement Material = waitUntilElementToBeClickable(Materials);
        Material.click();
        selectValueFromDropdown(selectCategory, selectValue);  //SELECT Category
        Thread.sleep(3000);
        selectValueFromDropdown(selectMaterials, selectValueM); //Select Materials
        Thread.sleep(3000);
        WebElement qun = waitUntilElementToBevisible(quantity); // select Quantity
        setTextElement(qun, Quantity);
        By comment = By.id("comment");
        WebElement Comment = waitUntilElementToBevisible(comment); // add Comment
        setTextElement(Comment, commentVal);
        Thread.sleep(1000);
        By issueTo = By.id("issuedto");
        WebElement issue = waitUntilElementToBevisible(issueTo); // add issuedTo
        setTextElement(issue, IssueToVal);
        Thread.sleep(2000);
        By Add = By.id("add");
        WebElement addBtn = waitUntilElementToBevisible(Add); //click Button Add
        addBtn.click();
        Thread.sleep(2000);
    }

    public boolean testRowDisplay(String number) throws InterruptedException {
        int maxAttempts = 10;  // Maximum number of refresh attempts
        int attempt = 0;
        boolean rowFound = false;

        // Loop until the row is found or the maximum attempts are reached
        while (attempt < maxAttempts && !rowFound) {
            try {
                // Attempt to find the row element
                WebElement row = driver.findElement(By.xpath("//tr[td='" + number + "']"));
                if (row != null && row.isDisplayed()) {
                    rowFound = true;
                    System.out.println("Row is displayed.");
                    waitUntilElementToBeClickable(By.xpath("(//tr[contains(@class, 'p-selectable-row')]//button[text()=' Open '])[1]")).click();
                    // Click on the "Open" button in the row
                    // waitUntilElementToBeClickable(By.xpath("(//tr[contains(@class, 'p-selectable-row')]//button[text()=' Open '])[1]")).click();
                    //  System.out.println("The task Request is added successfully.");
                    return true;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Row not found. Attempting to refresh... Attempt: " + (attempt + 1));

                // Click the refresh button
                WebElement refreshButton = driver.findElement(Refresh);
                refreshButton.click();
                attempt++;  // Increment the attempt counter

                // Optional: Add a small delay to wait for the page to refresh before the next attempt
                try {
                    Thread.sleep(2000);  // Adjust the sleep time as needed
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
        // If the loop exits and the row was not found
        if (!rowFound) {
            System.out.println("Test failed: Row was not found after " + maxAttempts + " attempts.");
        }
        return false;
    }

    public boolean CheckRowAndRefresh(String number) throws InterruptedException {
        boolean isRowDisplayed = false;
        while (!isRowDisplayed) {
            try {
                // Attempt to find the row
                WebElement row = waitUntilElementToBevisible(By.xpath("//tr[td='" + number + "']"));
                if (row != null && row.isDisplayed()) {
                    isRowDisplayed = true;
                    System.out.println("Row is displayed.");
                    waitUntilElementToBeClickable(By.xpath("(//tr[contains(., '" + number + "')]//button[text()=' Open '])[1]")).click();
                    //System.out.println("The task Request is added successfully.");
                }
            } catch (Exception e) {
                System.out.println("Row not found, trying to refresh...");
                // Attempt to find and click the refresh button until it appears
                boolean isRefreshButtonDisplayed = false;
                while (!isRefreshButtonDisplayed) {
                    try {
                        WebElement refreshButton = driver.findElement(Refresh);
                        if (refreshButton.isDisplayed()) {
                            isRefreshButtonDisplayed = true;
                            refreshButton.click();
                            System.out.println("Clicked refresh button.");
                        }
                    } catch (Exception ex) {
                        System.out.println("Refresh button not found, retrying...");
                        // Optional: Add a short sleep to avoid hammering the server
                        Thread.sleep(1000); // Adjust sleep time as necessary
                    }
                }
                // Optional: Wait for some time before the next iteration
                Thread.sleep(1000); // Adjust sleep time as necessary
            }
        }
        return isRowDisplayed;
    }
}