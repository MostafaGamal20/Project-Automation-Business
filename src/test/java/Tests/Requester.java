package Tests;

import Pages.*;
import data.LoadProperties;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.logging.Logger;

public class Requester extends TestBase {
    private static Logger log = Logger.getLogger(PageBase.class.getName());
    dashboard_Page FTTHDashBoardPage;
    Login_Page Login;
    Requester_Page requester;
    TrackingRequest_Page requestObject;
    protected static String usernameReq = LoadProperties.properties.getProperty("Username");
    protected static String passwordReq = LoadProperties.properties.getProperty("Password");

    // Test Case 1 Add image in the task
    @Test(priority = 1)
    public void verifyAddImageInReq() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        Login.userLogin(usernameReq, passwordReq); //Login By Requester
        Thread.sleep(3000);
        FTTHDashBoardPage = new dashboard_Page(driver);
        FTTHDashBoardPage.openMenu();
        FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
        FTTHDashBoardPage.clickOnParentMenu("Add");
        requester = new Requester_Page(driver);
        String image = "file1.png";
        requester.AddImage(System.getProperty("user.dir") + "/files/" + image,false);
        //verification Uploaded Image
        boolean verifyImageAdded = requester.verifyImageUploaded(" file1.png ");
        Assert.assertTrue(verifyImageAdded, "image Added Successfully");

    }
    // Test Case 2 Add FTG in the Request FTTH Requester
    @Test(priority = 2)
    public void uploadFTGBulkProcess() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        Login.userLogin(usernameReq, passwordReq); //Login By Requester
        Thread.sleep(3000);
        FTTHDashBoardPage = new dashboard_Page(driver);
        FTTHDashBoardPage.openMenu();
        FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
        FTTHDashBoardPage.clickOnParentMenu("Add");
        requestObject = new TrackingRequest_Page(driver);
        String FTG_Sample = "FTG Sample.xls";
        requestObject.bulkProcess("Create", System.getProperty("user.dir") + "/files/" + FTG_Sample, false);
        requestObject.closeDialog();
        Thread.sleep(3000);
        requester = new Requester_Page(driver);
        int rowCount  =  requester.rowCountBulk();
        System.out.println( "row = " +  rowCount);       //verification process Bulk
        Assert.assertEquals(rowCount, 1 , "the bulk process uploaded successfully.");
    }
}