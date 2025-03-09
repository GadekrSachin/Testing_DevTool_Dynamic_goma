package runner;
  
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
 
  
 
@CucumberOptions(
		
		features = {"./src/test/resources/features/Homepage.feature"},
		
		glue={"Step_def" ,"Hooks"},
		monochrome = true,
		dryRun = false, 
		tags = "@Form",   
		plugin = {
						"pretty",
						"html: test-report"
						}
		)

	public class loginrunner extends AbstractTestNGCucumberTests{
	
}
