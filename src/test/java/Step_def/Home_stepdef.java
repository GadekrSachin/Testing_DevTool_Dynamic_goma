package Step_def;
 
import java.util.Properties;
import org.openqa.selenium.By;
import com.factory.Base_driver;
import com.pages.Common;
import com.pages.ConfigManager;
import com.pages.Home;
import com.pages.login;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
 
public class Home_stepdef {

	Base_driver basedriver = new Base_driver();

	Properties props = ConfigManager.getProperties();
	Home Homepage = new Home(Base_driver.getDriver());
	login log = new login(Base_driver.getDriver());
	Api ap = new Api();

	Common common = new Common();

	private By loginbutton = By.xpath("//button[text()='Login']");
	
	

	@Given("user on Home page")
	public void user_on_home_page() {
		log.user_provide_and(props.getProperty("username"), props.getProperty("password"));

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
		Homepage.form_fill();
		ap.Get(props.getProperty("form_get_url")); 
		
		Thread.sleep(3000);
	}

	@When("user create a new form")
	public void user_create_a_new_form() {
		System.out.println("data 2");
	}
}
