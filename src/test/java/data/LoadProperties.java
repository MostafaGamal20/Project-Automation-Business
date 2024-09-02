package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

    //Load the properties file From the folder
    public static Properties properties =
            loadProperties(System.getProperty("user.dir")+"\\src\\main\\java\\Properties\\userdata");



    private static Properties loadProperties(String path){

        Properties pro = new Properties();
        // stream for reading file
        try {
            FileInputStream stream = new FileInputStream(path);
            pro.load(stream);
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred : " +e.getMessage());
        } catch (IOException e) {
            System.out.println("Error occurred : " +e.getMessage());
        }
        return pro;
    }
}