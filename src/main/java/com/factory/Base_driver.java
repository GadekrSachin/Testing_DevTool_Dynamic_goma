package com.factory;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base_driver {

	public static WebDriver driver; 

	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

	public WebDriver initializedDriver(String browser)  {

		if (browser.equalsIgnoreCase("chrome")) {
 	System.setProperty("webdriver.chrome.driver", "src/test/resources/Driver/chromedriver-121.exe");
			ChromeOptions options = new ChromeOptions();
//			upload chromebrowser version(121) same as driver 121
			options.setBinary("C:\\Users\\SHUBH\\Driver\\Testing browser\\chrome-win64(121)\\chrome-win64\\chrome.exe");
//			options.addArguments("--remote-allow-origins=*" );
			driver = new ChromeDriver(options);
			 return driver;
//	    	 	driver = new ChromeDriver( );

		} else if (browser.equalsIgnoreCase("firefox")) {

//			System.setProperty("webdriver.gecko.driver", "C:\\Users\\SHUBH\\Driver\\chromedriver-122\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();	
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("mobileweb")) {

			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", "Android");
			caps.setCapability("deviceName", "emulator-5554");
			caps.setCapability("browserName", "Chrome");
			caps.setCapability("automationName", "UiAutomator2");
			try {
				driver = new AndroidDriver(new URL("http://localhost:4723"), caps);
			} catch (MalformedURLException e) { 
				e.printStackTrace();
			}
			 
			tdriver.set(driver);
		}

		else {
			throw new IllegalArgumentException(browser + " is an unsupported browser!");
		}
		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}

	public static void main(String[] args) {

	}

}
