package Step_def;
  
import com.factory.Base_driver;
import com.pages.login;
 
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class login_stepdef {

	Base_driver basedriver = new Base_driver();
	 
	
	login loginuser = new login(Base_driver.getDriver());
	
  
	@Given("user on login page")
	public void user_on_login_page() throws InterruptedException { 
	     loginuser.user_on_login_page();
	     
	}
	
	@When("user provide {string} and {string}")
	public void user_provide_and(String username, String password) {
	    loginuser.user_provide_and(username, password);
	}
	
	@When("user provide invalid {string}")
	public void user_provide_invalid(String username) {
	    loginuser.user_provide_invalid(username);
	}
	
	@Then("user should see an error message {string}")
	public void user_should_see_an_error_message(String errorMessage) {
		loginuser.user_should_see_an_error_message(errorMessage);
	}
	
	@Then("user getting error message {string}")
	public void user_getting_error_message(String errorMessage) {
		loginuser.user_getting_error_message(  errorMessage);
	}
	 
	
 	
//	@After 
//	public void quitebrowser() { 
//			Base_driver.driver.close();
//	}
}
	