package Pages;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;

public class Login_Page extends PageBase {

    private static Logger log = Logger.getLogger(Login_Page.class.getName());
 //  private final By signInLocator = By.xpath("//button[text()=' Log in ']");
 //  private final By non_login_btn_loc = By.xpath("//button[.=' Log in ']");




    @FindBy(id = "username")
    private WebElement unameTxt;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordTxt;

    @FindBy(css = "button.btn.btn-primary.btn-auth")
    private WebElement loginBtn;


    public Login_Page(WebDriver driver) {
        super(driver);
    }


    public void userLogin(String username, String password) {
        // ExpectedConditions.presenceOfElementLocated(signInLocator)
        waitUntilElementToBevisible(unameTxt);
        setTextElement(unameTxt, username);
        waitUntilElementToBevisible(passwordTxt);
        setTextElement(passwordTxt, password);
        waitUntilElementToBevisible(loginBtn);
        click(loginBtn);
    }
    public boolean verifyNavigatedToLogin(String keyWord, int Duration) {
        return verifyURLContains(keyWord, Duration);
    }

    public void logOut() {
        WebElement userImage = driver.findElement(By.id("user_image"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", userImage);

        WebElement log =waitUntilElementToBeClickable(By.id("logout"));
        log.click();
    }
    public boolean verifyNavigatedToLoginOut(String keyWord, int Duration) {
        return verifyURLContains(keyWord, Duration);
    }

    public boolean isLoginElementPresent(String URL) {
        try {
            String currentUrl = driver.getCurrentUrl();
            // Check if the current URL matches the login page URL
            return currentUrl.equals(URL);
        } catch (Exception e) {
            // If the element is not found, return false
            return false;
        }
    }
}

