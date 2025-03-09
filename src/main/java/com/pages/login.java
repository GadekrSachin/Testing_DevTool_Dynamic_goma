package com.pages;

import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.factory.Base_driver;

import dev.failsafe.internal.util.Assert;

public class login {

	Base_driver basedriver = new Base_driver();
	private WebDriver driver;
	Properties props = ConfigManager.getProperties();

//login elements 	
	private By loginbutton = By.xpath("//button[text()='Login']");
	private By email_id = By.xpath("//input[@inputmode=\"email\"]");
	private By Sign_In_Button = By.xpath("(//button[@type=\"submit\"])[1]");
	private By password_input = By.xpath("//input[@name=\"password\"]");
	private By next_button = By.xpath("//input[@value=\"Next\"]");
	private By invalid_email_error = By.xpath("//div[@id=\"usernameError\"]");
	private By invalid_pass_error = By.xpath("//div[@id=\"passwordError\"]");

	public login(WebDriver driver) {
		this.driver = driver;
	}

	public void user_on_login_page() {

//       String browserName = props.getProperty("browsername");
//		basedriver.initializedDriver(browserName);

		Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String url = props.getProperty("WebUrl");
		Base_driver.driver.get(url);
		Base_driver.driver.manage().window().maximize();
		Base_driver.driver.findElement(loginbutton).click();
	}

	public void user_provide_and(String username, String password) {
		Base_driver.driver.findElement(email_id).sendKeys(username);
//		Base_driver.driver.findElement(next_button).click();
		Base_driver.driver.findElement(password_input).sendKeys(password);
		System.out.println("you are on password input page ---------------");
		Base_driver.driver.findElement(Sign_In_Button).click();
	}

	public void user_provide_invalid(String username) {
		Base_driver.driver.findElement(email_id).sendKeys(username);
		Base_driver.driver.findElement(next_button).click();
	}

	public void user_should_see_an_error_message(String errorMessage) {
		Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		String errormsg = Base_driver.driver.findElement(invalid_email_error).getText();
		Assert.isTrue(true, errormsg, errorMessage);
		System.out.println("validation confirm -----");
	}

	public void user_getting_error_message(String errorMessage) {
		String Password_Error_Text = Base_driver.driver.findElement(invalid_pass_error).getText();
		Assert.isTrue(true, Password_Error_Text, errorMessage);
		System.out.println("validation confirm -----");
	}

}
