package Tests;
import java.io.IOException;

public class FeatureThread extends Thread {

    public String browserName;
    TrackingRequest TrackingRequest;


    public FeatureThread(String threadName, String browserName) {
        super(threadName);
        this.browserName = browserName;
        TrackingRequest = new TrackingRequest();

    }

    @Override
    public void run() {
        System.out.println("Thread--started"+ Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            TrackingRequest.start(this.browserName);
            TrackingRequest.create_Request_FThhReq();
            TrackingRequest.verify_Request_Displayed_In_DM();
            TrackingRequest.verify_Request_Displayed_In_Technician();
            TrackingRequest.verify_Request_Displayed_In_No();
            // -- > Name the Test Case
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                TrackingRequest.TearDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread--ended " +Thread.currentThread().getName());
        }
    }
}