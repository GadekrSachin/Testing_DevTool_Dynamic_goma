package com.pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.factory.Base_driver;

public class Common {

	Base_driver basedriver = new Base_driver();
	private WebDriver driver;
	Properties props = ConfigManager.getProperties();

	
	 
	// Moduleelement
	private By Sale_Module = By.xpath("//i[@class=\"ri-price-tag-3-fill\"]");
	private By Appointment_Module = By.xpath("//i[@class=\"ri-calendar-fill\"]");
	private By Client_Module = By.xpath("//i[@class=\"ri-user-fill\"]");
	private By Catalog_Module = By.xpath("//i[@class=\"ri-book-open-fill\"]");
	private By Catalog_Module_mobile = By.xpath("(//i[@class=\"ri-book-open-fill\"])[2]");
	private By Marketing_Module = By.xpath("//i[@class=\"ri-focus-fill\"]");
	private By Team_Module = By.xpath("//i[@class=\"ri-team-fill\"]");
	private By Report_Module = By.xpath("//i[@class=\"ri-line-chart-line\"]");
	private By Notification_Module = By.xpath("//i[@class=\"ri-notification-fill\"]");
	private By Setting_Module = By.xpath("//i[@class=\"ri-settings-2-fill\"]");
	private By Logout_Module = By.xpath("//i[@class=\"ri-logout-box-r-line\"]");
	private By Profile_Module = By.xpath("(//div[@class=\"MuiAvatar-root MuiAvatar-circular MuiAvatar-colorDefault user-avatar css-el22pw\"])[1]");

//	catalog module 
	private By Service_Menu = By.xpath("//button[@id=\"simple-tab-0\"]");
	private By Product = By.xpath("//button[@id=\"simple-tab-1\"]");
	private By Consulting_Module = By.xpath("//button[@id=\"simple-tab-2\"]");
	private By Gift_Menu = By.xpath("//button[@id=\"simple-tab-3\"]");
	
	
	public void Upto_AllModule(String moduleName) throws InterruptedException  {
		 
		Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8000));
		
		if (moduleName.equalsIgnoreCase("appointment")) {
			Base_driver.driver.findElement(Appointment_Module).click();
		} else if (moduleName.equalsIgnoreCase("sales")) {
			Base_driver.driver.findElement(Sale_Module).click();
		} else if (moduleName.equalsIgnoreCase("client")) {
			Base_driver.driver.findElement(Client_Module).click();
		} else if (moduleName.equalsIgnoreCase("catalog")) {
		
			if(props.getProperty("Base_Resolution").equals(props.getProperty("Mobile_resolution")  )  ) {
				Base_driver.driver.findElement(Catalog_Module_mobile).click();
			}else {		 
				Thread.sleep(3000);
				Base_driver.driver.findElement(Catalog_Module).click();
			}
			
		} else if (moduleName.equalsIgnoreCase("marketing")) {
			Base_driver.driver.findElement(Marketing_Module).click();
		} else if (moduleName.equalsIgnoreCase("team")) {
			Base_driver.driver.findElement(Team_Module).click();
		} else if (moduleName.equalsIgnoreCase("reports")) {
			Base_driver.driver.findElement(Report_Module).click();
		} else if (moduleName.equalsIgnoreCase("notification")) {
			Base_driver.driver.findElement(Notification_Module).click();
		} else if (moduleName.equalsIgnoreCase("setting")) {
			Base_driver.driver.findElement(Setting_Module).click();
		} else if (moduleName.equalsIgnoreCase("logout")) {
			Base_driver.driver.findElement(Logout_Module).click();
		} else if (moduleName.equalsIgnoreCase("profile")) {
			Base_driver.driver.findElement(Profile_Module).click();
		} else {
			System.out.println("Invalid module name: " + moduleName);
		}
	}
	
 
		public void Catalog_submodule(String Submodule)  {

			Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
			
			if (Submodule.equalsIgnoreCase("service menu")) {
				Base_driver.driver.findElement(Service_Menu).click();
			} else if (Submodule.equalsIgnoreCase("product")) {
				Base_driver.driver.findElement(Product).click();
			}else if (Submodule.equalsIgnoreCase("consulting form")) {
				Base_driver.driver.findElement(Consulting_Module).click();
			}else if (Submodule.equalsIgnoreCase("giftmenu")) {
				Base_driver.driver.findElement(Gift_Menu).click();
			}
			
			
			else {
				System.out.println("Invalid module name: " + Submodule);
			}
			}

		public static void dragAndDrop(WebElement source, WebElement target) {
	        Actions actions = new Actions(Base_driver.driver);
	        actions.clickAndHold(source).moveToElement(target).release().perform();
	        System.out.println("Dragged element to target.");
	    }	
		
		public static void scrollToBottom() throws InterruptedException {
	        JavascriptExecutor js = (JavascriptExecutor) Base_driver.driver;
	        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

	        while (true) {
	            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	            Thread.sleep(2000);  

	            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
	            if (newHeight == lastHeight) {
	                break;
	            }
	            lastHeight = newHeight;
	        }
	    }	
		
		public void scrollToElement(WebDriver driver, WebElement element) {
	        try {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
	            System.out.println("Scrolled to the element successfully.");
	        } catch (Exception e) {
	            System.out.println("Unable to scroll to the element: " + e.getMessage());
	        }
	    }
		public void dropAppointmentInAvailableSlot(WebDriver driver, WebElement appointmentCard) {
		    try {
		    	
		    	String time= props.getProperty("timeSlots");
 		        String[] timeSlots =time.split(",");  
		        
		        Actions actions = new Actions(Base_driver. driver);
		        boolean isDropped = false;
 
		        for (String timeSlot : timeSlots) {
		            List<WebElement> dropAreas =Base_driver. driver.findElements(By.xpath("//td[@data-time-slot='" + timeSlot + "']"));
		            
		            for (WebElement dropArea : dropAreas) {
		                if (isDropAreaEmpty(dropArea)) {  
		                    System.out.println("Dropping appointment at: " + timeSlot);
 
		                    actions.clickAndHold(appointmentCard)
		                           .moveToElement(dropArea)
		                           .release()
		                           .build()
		                           .perform();

		                    System.out.println("Appointment dropped successfully at " + timeSlot);
		                    isDropped = true;
		                    
		                    
		                    break; 
		                }
		            }
		            if (isDropped) break;  
		        }

		        if (!isDropped) {
		            System.out.println("No available drop slot found.");
		        }

		    } catch (NoSuchElementException e) {
		        System.out.println("Appointment or drop areas not found.");
		    }
		}
 
		public boolean isDropAreaEmpty(WebElement dropArea) {
		    String classAttribute = dropArea.getAttribute("class");
		    return !classAttribute.contains("occupied"); 
		}

	
		 
}



