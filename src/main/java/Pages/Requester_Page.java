package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class Requester_Page extends PageBase {
    protected static TrackingRequest_Page requestPage;
    private static Logger log = Logger.getLogger(PageBase.class.getName());

    By Image_Patching_Loc = By.xpath("(//span[.='Image Patching'])");
    By Browse_Loc = By.xpath("//input[@type='file']");
    By save_btn_loc = By.xpath("(//button[@id='save'])[2]");

    public Requester_Page(WebDriver driver) {
        super(driver);

    }

    public void bulkProcess(String bulk_type, String file, boolean save) {
        requestPage = new TrackingRequest_Page(driver);
        requestPage.bulkProcess(bulk_type, file, save);
    }

    public void AddImage(String Upload_image, boolean Save) throws InterruptedException {
        Thread.sleep(3000);
        WebElement click_panel = waitUntilElementToBeClickable(Image_Patching_Loc);
        click_panel.click();

        WebElement attach_image = waitUntilElementToPresent(Browse_Loc);
        setTextElement(attach_image, Upload_image);

        WebElement save = waitUntilElementToBeClickable(save_btn_loc);
        save.click();
    }

    public boolean verifyImageUploaded(String image) {
        try {
            By myTasks = By.xpath("//tr[td='" + image + "']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(myTasks));
            if (isElementPresentInDom(myTasks))
                System.out.println("The image uploaded Successfully");
            return true;
        } catch (Exception ex) {
            log.info(" Image not loaded ");
            return false;
        }
    }

    public int rowCountBulk() {
        // Locate the element that contains the row count
        WebElement rowElement = driver.findElement(By.cssSelector("div.recordsNumber"));

        // Get the text from the element
        String rowText = rowElement.getText();

        // Extract the number from the text (assuming the format "# Rows : 1")
        String[] parts = rowText.split(":");
        int rowCount = Integer.parseInt(parts[1].trim());

        // Verify that the row count is 1
        return rowCount;
    }

    public boolean verifyAddRow() {
        WebElement row = waitUntilElementToBevisible(By.xpath("//tfoot[div='# Rows : 1 ']"));
        if (row.isDisplayed()) {
            log.info("row ='visible'");
            return true;
        } else {
            log.info("row ='It's not visible'");
            return false;
        }
    }
}
