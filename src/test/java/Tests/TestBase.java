package Tests;

import Pages.PageBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import data.LoadProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.Helper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class TestBase  {
    protected static ExtentReports extentReports;
    protected static ExtentSparkReporter extentSparkReporter;
    protected static ExtentTest extentTest;
    protected static ExtentTest logger;
    public static WebDriver driver;
    static String currentTestCaseName;

    private static Logger log = Logger.getLogger(PageBase.class.getName());
    String Instance_URL = LoadProperties.properties.getProperty("NTGapps.Instance_URL");

    @BeforeSuite
    @Parameters({"browser"})
    public WebDriver start(@Optional("chrome") String browserName) throws IOException {
        if (browserName.equalsIgnoreCase("chrome")) {
            // System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            ChromeOptions ops = new ChromeOptions();
            HashMap<String, Object> chromePref = new HashMap<>();
            ops.setExperimentalOption("prefs", chromePref);
            ops.addArguments("--remote-allow-origins=*");
            ops.addArguments("--disable-notifications");
            ops.addArguments("--disable-popup-blocking");
            driver = new ChromeDriver(ops);
            driver.manage().window().maximize();
            driver.navigate().to(Instance_URL);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        return driver;
    }

    /* @AfterSuite
     public void tearDown() throws IOException {
         log.info("Starting after suite");
         // Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "./Reports/" + "Final_Report.html"));
         driver.quit();
         extent.flush();
     }*/
    //Take Screenshot when test case fail and add it in the screen folder
    @AfterMethod
    public void getResult(Method method, ITestResult result) throws IOException {
        String testName = method.getName(); // Use test method name
        String description = "Test Case " + testName;
        extentTest = extentReports.createTest(testName, description);
        //ExtentTest logger = null;

        if (result.getStatus() == ITestResult.FAILURE) {
            Helper Help = new Helper();
            Help.captureScreenshots(driver, testName);
            extentTest.log(Status.FAIL, result.getThrowable());
            log.info("=== fail Test ");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, result.getTestName());
            log.info("=== PASSED ");
        } else {
            extentTest.log(Status.SKIP, result.getTestName() + " is SKIPPED");
            log.info("Skipped");
        }
    }

    @AfterTest
    public void TearDown() throws IOException {
        //to write or update test information to the reporter
        log.info("End of after suite");
        // driver.quit();
        if (driver != null) {
            driver.quit();
        }
        extentReports.flush();
    }

    @BeforeTest
    public void startReport() {
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/extentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentSparkReporter.config().setDocumentTitle("Simple Automation Report");
        extentSparkReporter.config().setReportName("NTGApps Automation Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss a");
        extentSparkReporter.config().getOfflineMode();
    }

    public static void reporter(String status, String stepDetail) {
        //ExtentTest logger = null;
        String path;
        try {
            path = Helper.capture(driver, currentTestCaseName + "_" + Helper.getRandomDateTime() + ".jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (status.equalsIgnoreCase("PASS")) {
            logger.pass(stepDetail, MediaEntityBuilder.createScreenCaptureFromPath(path).build());

        } else if (status.equalsIgnoreCase("fail")) {
            logger.fail(stepDetail, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } else if (status.equalsIgnoreCase("info")) {
            logger.info(stepDetail);
        } else if (status.equalsIgnoreCase("Warning")) {
            logger.warning(stepDetail);
        }
    }

    public void captureScreenshot() {
        try {
            System.out.println("Taking screenshot for failed assert");
            String screenshotPath = System.getProperty("user.dir") + "/Reports/Screenshots";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            System.out.println("Adding screenshot to extent report");
            String screenshotName = "screeshot_" + new Random().nextInt(999) + ".png";
            screenshotPath = screenshotPath + File.separator + screenshotName;
            Files.copy(screenshot, new File(screenshotPath));
            extentTest.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



