package Tests;

public class TestRunner {
    public static void main(String or[]) {
        //Thread th1 =new FeatureThread("chrome thread","chrome");
        //System.out.println("thread started......");
        //th1.start();
        int ChromeCount = 5;  // Number of browsers to launch
        int waitTimeInMillis = 20000;
        // Wait time in milliseconds (e.g., 2000ms = 2 seconds)
        for (int i = 0; i < ChromeCount; i++) {
            System.out.println("Starting thread for browser instance " + (i + 1));
            // Start a new thread for each browser session
            new FeatureThread("chrome thread " + (i + 1), "chrome").start();
            // Wait for specified time before opening the next browser
            try {
                Thread.sleep(waitTimeInMillis);  // Wait for 2 seconds before starting the next thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}