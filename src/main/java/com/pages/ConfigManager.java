package com.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
 
    private static Properties properties;
   
    private ConfigManager() { }
 
    public static Properties getProperties() {
   System.out.println("data");
    	if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream("./src/test/resources/driver_confi/confii.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
