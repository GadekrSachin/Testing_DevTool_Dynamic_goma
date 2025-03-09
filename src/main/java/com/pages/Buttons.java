package com.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.factory.Base_driver;

public class Buttons {

	
	
	private By Popup_OkButton = By.xpath("//button[contains(text(),'Ok')]");
	
	
	public void clickFixedButton(WebDriver driver) {
	    try {
	        WebElement button = Base_driver. driver.findElement(Popup_OkButton);  
	        button.click();  
	        System.out.println("Fixed button clicked successfully.");
	    } catch (NoSuchElementException e) {
	        System.out.println("Fixed button not found.");
	    } catch (ElementClickInterceptedException e) {
	        System.out.println("Fixed button not clickable.");
	    }
	}
	
	
	
}
