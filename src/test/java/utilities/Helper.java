package utilities;

import Pages.PageBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.logging.Logger;

public class Helper {
    private static Logger log = Logger.getLogger(PageBase.class.getName());


    // Method to take screenshot when the test cases fail
    public static void captureScreenshots(WebDriver driver, String screenshotName) {
        Path dest = Paths.get("./Reports/Screenshots/", screenshotName + ".png");
        try {
            Files.createDirectories(dest.getParent());
            FileOutputStream out = new FileOutputStream(dest.toString());
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close(); // close stream
        } catch (IOException e) {
            log.info("Expectation while taking screenshot" + e.getMessage());
        }
    }

    public static long getRandomDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHmss");
        LocalDateTime now = LocalDateTime.now();
        long genNum;
        genNum = Long.parseLong(dtf.format(now).replaceAll(":", "").replaceAll("/", ""));
        //   log.info(genNum);
        return genNum;
    }
    public static String capture(WebDriver driver, String screenShotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String dest = "./Reports/Screenshots/" + screenShotName + ".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);

        //return dest;
        return destination.getAbsolutePath();
    }

}


