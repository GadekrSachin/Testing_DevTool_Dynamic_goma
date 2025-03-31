package Step_def;

import java.util.Properties;

import org.openqa.selenium.By;

import com.factory.Base_driver;
import com.pages.Common;
import com.pages.ConfigManager;
import com.pages.Home;
import com.pages.login;

import api.Api;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Home_stepdef {

	Base_driver basedriver = new Base_driver();

	Properties props = ConfigManager.getProperties();
	Home Homepage = new Home(Base_driver.getDriver());
	login log = new login(Base_driver.getDriver());
	Api ap = new Api();

	Common common = new Common();

	private By loginbutton = By.xpath("//button[text()='Login']");

	@When("add {string} and {string} and {string} and {string} and {string} and {string} and {string} and {string}")
	public void add_and_and_and_and_and_and_and(String FName, String LName, String cCode, String Mnumber, String Email, String Gender, String DOB, String HAddress) {
	    Homepage.add_client(FName, LName, cCode, Mnumber, Email, Gender, DOB, HAddress);
	}

	@Then("verify create a new client")
	public void verify_create_a_new_client() {

	}

	@Given("user on Home page")
	public void user_on_home_page()  {
		log.user_provide_and(props.getProperty("username"), props.getProperty("password"));
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@When("redline on current time")
	public void redline_on_current_time() {
		Homepage. redline();
	}
	@When("user drag and drop")
	public void user_drag_and_drop() throws InterruptedException {
		Homepage.user_drag();
	}

	@When("user on form list page")
	public void user_on_form_list_page() throws InterruptedException {
		common.Upto_AllModule("catalog");
		common.Catalog_submodule("consulting form");

	}

	@Then("validate delete form")
	public void validate_delete_form() throws InterruptedException {
		Homepage.delete_form() ;

	}

	@When("Verify appointments as per date")
	public void verify_appointments_as_per_date() {
		Homepage.verify_appointments_as_per_date();
	}

	@When("user create a new form")
	public void user_create_a_new_form() throws InterruptedException {
		Homepage.form_fill();

		Thread.sleep(3000);
	}
}
