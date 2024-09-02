package Tests;

import Pages.dashboard_Page;
import org.apache.log4j.Logger;

public class DashBoard extends TestBase {


    private static Logger log = Logger.getLogger(DashBoard.class.getName());
    dashboard_Page dashBoard;


    public DashBoard (){
        super();
    }

    public void isElementExist() {
        dashBoard = new dashboard_Page(driver);
        dashBoard.isSettingsElementExist();
    }
}
