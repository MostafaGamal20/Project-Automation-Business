package Tests;
import Pages.Login_Page;
import Pages.PageBase;
import Pages.dashboard_Page;
import data.LoadProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.logging.Logger;

public class Login extends TestBase {
    protected static dashboard_Page FtthDashBoardPage;
    private static Logger log = Logger.getLogger(PageBase.class.getName());
    Login_Page Login;
    String username = LoadProperties.properties.getProperty("Username");
    String password = LoadProperties.properties.getProperty("Password");


    @Test
    public void loginUser() throws InterruptedException {
        Login = new Login_Page(driver);
        if (Login.isLoginElementPresent(Instance_URL)) {
            System.out.println("Login page is already open.Skipping logOut().");
        } else {
            // Perform logout if login page is not displayed
            Login.logOut();
        }
        Login.userLogin(username, password);
        Thread.sleep(5000);
        boolean isContainsLogin = Login.verifyNavigatedToLogin("pages", 15);
        if (isContainsLogin) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false, "Failed to Login ");
        }
    }

    @Test(dependsOnMethods = {"loginUser"})
    public void login_functionality() {
        log.info("before calling verifyNavigatingToDashboard");
        FtthDashBoardPage = new dashboard_Page(driver);
        boolean is_Contains_Pages = FtthDashBoardPage.verifyNavigatingToDashboard();
        log.info("In Login_Functionality >>is_Contains_Pages>>>" + is_Contains_Pages);
        if (is_Contains_Pages) {
            Assert.assertTrue(true);
        } else Assert.assertTrue(false, "Failed to Login");
    }
}