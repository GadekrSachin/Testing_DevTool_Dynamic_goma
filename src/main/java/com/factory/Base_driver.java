package com.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver; 
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base_driver {
	

	public static WebDriver driver ;
	
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

	public WebDriver initializedDriver(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")){		
//			WebDriverManager.chromedriver();
//			WebDriverManager.chromedriver().setup();
 			System.setProperty("webdriver.chrome.driver", "src/test/resources/Driver/chromedriver-121.exe");
			ChromeOptions options = new ChromeOptions();
//			upload chromebrowser version(121) same as driver 121
			options.setBinary("C:\\Users\\SHUBH\\Driver\\Testing browser\\chrome-win64(121)\\chrome-win64\\chrome.exe");
//			options.addArguments("--remote-allow-origins=*" );
			  driver = new ChromeDriver(options);

//	    	 	driver = new ChromeDriver( );
			
		}else if(browser.equalsIgnoreCase("firefox")) {
 
//			System.setProperty("webdriver.gecko.driver", "C:\\Selenium WebDriver\\FirefoxDriver\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();	 
		}
		else if(browser.equalsIgnoreCase("edge")) {
			  driver = new EdgeDriver();
		}
		
		
		
		
		else {
			System.out.println(browser+"unsupported browser");
		}
		return getDriver();
		}
	
	public  static synchronized WebDriver getDriver() {
		return  tdriver.get();
	}
	
	public static void main(String[] args) {
 
	}

	 
}
