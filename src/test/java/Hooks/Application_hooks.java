package Hooks;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.factory.Base_driver;
import com.pages.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before; 

public class Application_hooks {
	
	private Base_driver basedriver;
	  WebDriver driver;
	Properties props = ConfigManager.getProperties();
	 
//	Properties prop;
	
	public Properties getBrowserName() {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("./src/test/resources/driver_confi/confii.properties");  
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop ;
    }	
 
 
	@Before(order = 0)
	public  void launch_browser() {
		 
		String browserName = props.getProperty("browsername");	
		basedriver=new Base_driver();
		driver=basedriver.initializedDriver(browserName);
		
		String resolution= props.getProperty("Base_Resolution");		

		if (resolution.equalsIgnoreCase(props.getProperty("Mobile_resolution"))) {
            Base_driver. driver.manage().window().setSize(new org.openqa.selenium.Dimension(400, 750));
        } else {
        	Base_driver.driver.manage().window().maximize();
        }
				  
		 Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
		 Base_driver.driver.get(props.getProperty("WebUrl"));
	}
	
	@After 
	public void quitebrowser() {
//		 Base_driver.driver.close();		 
	}
	
}
