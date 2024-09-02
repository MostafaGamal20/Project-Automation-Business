package Tests;

import Pages.*;
import data.LoadProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class Technician extends TestBase {
    private static Logger log = Logger.getLogger(PageBase.class.getName());
    dashboard_Page FTTHDashBoardPage;
    Login_Page Login;
    TrackingRequest_Page requestObjectPage;
    TrackingRequest requestObject;
    Technician_Page technicianPage;
    protected static String usernameTC = LoadProperties.properties.getProperty("UsernameTC");
    protected static String PasswordTC = LoadProperties.properties.getProperty("PasswordTC");

    //Test Case 1 Add Materials By The Technician
    //@Test (dependsOnGroups = {"create_Request_FThhReq","verify_Request_Displayed_In_DesignEng"})
    @Test
    public void addMaterials() throws InterruptedException {
        Login = new Login_Page(driver);
        Login.logOut();
        requestObject = new TrackingRequest();
        requestObject.create_Request_FThhReq();
        requestObject.verify_Request_Displayed_In_DM();
        Login.userLogin(usernameTC, PasswordTC);
        FTTHDashBoardPage = new dashboard_Page(driver);
        Thread.sleep(3000);
        try {
            FTTHDashBoardPage.openMenu();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);}
        FTTHDashBoardPage.clickOnMyApp("FTTH Tracking Tool");
        FTTHDashBoardPage.clickOnParentMenu("  Technician Workspace");
        requestObjectPage = new TrackingRequest_Page(driver);
        Thread.sleep(2000);
        // Verify Task Displayed in the WorkSpace (Grid Chart)
        boolean verifyTaskAdded =requestObjectPage.CheckRowAndRefresh("FTTH-2024-0814");
        System.out.println( "Request Id is =  " +  verifyTaskAdded);
        //  Assert.assertTrue(verifyTaskAdded, " Task Request is Added in the Technician Workspace Successfully");
        requestObjectPage.addMaterials("Select Category",
                "TOOLS","Select Materials","Utility Knife",
                "100","comment","issued");
        technicianPage = new Technician_Page(driver);
        boolean  materialIsDisplayed=   technicianPage.verifyAddMaterials("Utility Knife");
        if(materialIsDisplayed)
            Assert.assertTrue(true, "Material is Add successfully");
        else {
            Assert.assertFalse(false, "Material Not Added");
        }
    }
}
