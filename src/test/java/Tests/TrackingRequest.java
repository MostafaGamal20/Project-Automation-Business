package Tests;
import Pages.*;
import data.LoadProperties;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.logging.Logger;


public class TrackingRequest extends TestBase {
    private static Logger log = Logger.getLogger(PageBase.class.getName());
    TrackingRequest_Page requestObject;
    dashboard_Page FTTHDashBoardPage;
    Technician_Page technicianPage;
    Login_Page Login;
    int Num = 100;
    Random rand = new Random();
    String RequestId;
    String nameISOWDow = "ISOW-DWO" + rand.nextInt(Num);
    String nameFDTCLLI = "FDTCLLI" + rand.nextInt(Num);
    protected static String usernameReq = LoadProperties.properties.getProperty("Username");
    protected static String passwordReq = LoadProperties.properties.getProperty("Password");
    protected static String usernameTC = LoadProperties.properties.getProperty("UsernameTC");
    protected static String PasswordTC = LoadProperties.properties.getProperty("PasswordTC");
    protected static String usernameNo = LoadProperties.properties.getProperty("usernameNo");
    protected static String PasswordNO = LoadProperties.properties.getProperty("PasswordNO");
    protected static String usernameDM = LoadProperties.properties.getProperty("UsernameDM");
    protected static String passwordDM = LoadProperties.properties.getProperty("PasswordDM");

    //....?? Test Case 1  Requester Create task and attach file and proceed this task the District manager.
    @Test(priority = 1)
    public void create_Request_FThhReq() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        Login.userLogin(usernameReq,passwordReq);
        Thread.sleep(2000);
        boolean isContainsLogin = Login.verifyNavigatedToLogin("pages", 15);
        if (isContainsLogin) {
            Assert.assertTrue(true);
            FTTHDashBoardPage = new dashboard_Page(driver);
            FTTHDashBoardPage.openMenu();
            FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
            FTTHDashBoardPage.clickOnParentMenu("Add");
            requestObject = new TrackingRequest_Page(driver);
            requestObject.selectValueFromDropdown("Select Site","704-00-000");
            requestObject.fillAllMandatoryFieldRequest(nameISOWDow, nameFDTCLLI);
            String FTG_Sample = "FTG Sample.xls";
            requestObject.bulkProcess("Create", System.getProperty("user.dir") + "/files/" + FTG_Sample, false);
            requestObject.closeDialog();
            requestObject.saveRequest();
            RequestId = requestObject.getRequestId();
            System.out.println("Request ID is : " + RequestId);
            requestObject.proceedBtn();
            String actualMessage = requestObject.getMessageText();
            Assert.assertEquals(actualMessage, "Your request has been submitted successfully");
            requestObject.ClickOnCloseDialog();
            if(actualMessage.equalsIgnoreCase(actualMessage)){
                System.out.println("The Request Task is added Successfully.");
            }else {
                System.out.println("The Request Task is not Added.");
            }
        } else {
            Assert.assertTrue(false, "Failed to Login ");
        }
    }

    //....?? Test Case 2  District manager Assign the Technician and add notes or add image and fill fields and proceed this task to District manager.

    @Test(priority = 2)
    public void verify_Request_Displayed_In_DM() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        Login.userLogin(usernameDM, passwordDM);
        boolean isContainsLogin = Login.verifyNavigatedToLogin("pages", 15);
        if (isContainsLogin) {
            Assert.assertTrue(true);
            FTTHDashBoardPage = new dashboard_Page(driver);
            Thread.sleep(1000);
            FTTHDashBoardPage.openMenu();
            FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
            FTTHDashBoardPage.clickOnParentMenu("  Manager Workspace");
            requestObject = new TrackingRequest_Page(driver);
            // Verify Task Displayed in the WorkSpace (Grid Chart)
            requestObject.clickFTThRequestID();
            boolean result = requestObject.CheckRowAndRefresh(RequestId);  // Replace with the actual row number or identifier
            if (result) {
                System.out.println("The Request Task is added Successfully.");
            } else {
                System.out.println("The Request Task is Not added Successfully.");
            }
            //Assert.assertTrue(verifyTaskAdded, "The Request Task is added Successfully in the Design Engineer Workspace");
            requestObject.selectStore("AL-medina");
            requestObject.selectTechnicians("Card EX Technician");
            // proceed to (" Proceed To Technician ") or returned (" Return To Requester ")
            requestObject.proceedTo_ReturnedTo(" Proceed To Technician ");
            //verify Request Forwarded To The Technician successfully, or verify Request Returned To requester successfully.
            String actualMassage = requestObject.getMessageText();
            Assert.assertEquals(actualMassage, "Request has been forwarded to the technician successfully.");
            // Assert.assertEquals(actualMassage,"Request has been returned to requester successfully");
            requestObject.ClickOnCloseDialog();
        } else {
            Assert.assertTrue(false, "Failed to Login ");
        }
    }

    //....?? Test Case 3 Technician verify the task added materials and fill fields and proceed this task to NO(Network Operation) or returned task to (.....).

    @Test (priority=3)
    public void verify_Request_Displayed_In_Technician() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        //   Login.verifyNavigatedToLoginOut("navigateToLogin=true", 15);
        Login.userLogin(usernameTC, PasswordTC);
        boolean isContainsLogin = Login.verifyNavigatedToLogin("pages", 15);
        if (isContainsLogin) {
            Assert.assertTrue(true);
            FTTHDashBoardPage = new dashboard_Page(driver);
            Thread.sleep(3000);
            try {
                FTTHDashBoardPage.openMenu();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
            FTTHDashBoardPage.clickOnParentMenu("  Technician Workspace");
            requestObject = new TrackingRequest_Page(driver);
            Thread.sleep(1000);
            // Verify Task Displayed in the WorkSpace (Grid Chart)
            requestObject.clickFTThRequestID();
            boolean result =requestObject.CheckRowAndRefresh(RequestId);  // Replace with the actual row number or identifier
            if (result) {
                System.out.println("The Request Task is added in Workspace Successfully.");
            } else {
                System.out.println("The Request Task is Not added Workspace.");
            }
            requestObject.addMaterials("Select Category",
                    "TOOLS","Select Materials","Utility Knife",
                    "100","comment","issued");
            technicianPage = new Technician_Page(driver);
            boolean materialIsDisplayed = technicianPage.verifyAddMaterials("Utility Knife");
            if(materialIsDisplayed)
                Assert.assertTrue(true, "Material is Add successfully");
            else {
                Assert.assertFalse(false, "Material Not Added");
            }
            requestObject.proceedTo_ReturnedTo(" Proceed To NO "); // proceed to Network Operation
            String actualMassage = requestObject.getMessageText();
            Assert.assertEquals(actualMassage, "Request has been forwarded to NO Successfully");
            requestObject.ClickOnCloseDialog(); // CLOSE DIALOG

        } else {
            Assert.assertTrue(false, "Failed to Login ");
        }
    }

    //....?? Test Case 4 Network Operation verify the task added and verify materials and accept the task or Reject.
    @Test (priority=4)
    public void verify_Request_Displayed_In_No() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        //   Login.verifyNavigatedToLoginOut("navigateToLogin=true", 15);
        Login.userLogin(usernameNo,PasswordNO);
        FTTHDashBoardPage = new dashboard_Page(driver);
        Thread.sleep(3000);
        FTTHDashBoardPage.openMenu();
        FTTHDashBoardPage.clickOnParentMenu("Network Operations workspace");
        FTTHDashBoardPage.clickOnParentMenu("  Network Operations workspace");
        requestObject = new TrackingRequest_Page(driver);
        // Verify Task Displayed in the WorkSpace (Grid Chart)
        boolean result =requestObject.CheckRowAndRefresh1("RequestId");// Replace with the actual row number or identifier
        if (result) {
            System.out.println("The Request Task is added Successfully.");
        } else {
            System.out.println("The Request Task is Not added.");
        }
        requestObject.proceedTo_ReturnedTo(" Accept "); // " Reject "
        // proceed to Network Operation
        Thread.sleep(4000);
        String actualMassage = requestObject.getMessageText();
        Assert.assertEquals(actualMassage, "Request has been accepted successfully");
        requestObject.ClickOnCloseDialog();
        if(actualMassage.equalsIgnoreCase(actualMassage)){
            System.out.println("The Request Task is Accept Successfully.");
        }else {
            System.out.println("The Request Task is Reject.");
        }
    }
}



// allure serve target/allure-results
