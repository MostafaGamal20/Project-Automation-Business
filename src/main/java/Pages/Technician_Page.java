package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class Technician_Page extends PageBase{
    public Technician_Page(WebDriver driver) {
        super(driver);
    }
    public boolean verifyAddMaterials(String Material) {
        WebElement row = waitUntilElementToBevisible(By.xpath("//tr[td='" + Material + "']"));
        if (row.isDisplayed()){
            System.out.println("Materials is Displayed ");
            return true;
        }else {
            System.out.println("Materials Not  Displayed ");
            return false;
        }
    }
}
