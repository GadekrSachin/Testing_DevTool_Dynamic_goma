package Step_def;

import static io.restassured.RestAssured.given;

import java.util.Properties;
import com.factory.Base_driver;
import com.pages.ConfigManager;
import io.restassured.RestAssured; 
import io.restassured.response.Response;

public class Api {

	Base_driver basedriver = new Base_driver();
	Properties props = ConfigManager.getProperties();

	String Form_ID = "";

	public String baseurl() {
		return RestAssured.baseURI = (props.getProperty("ApiBase_url"));
	}

	public void Get(String url) {

		Response response =
				given()
				.when()
				.get(baseurl() + url)
				.then()
				.extract()
				.response();

		System.out.print("All response in APi :" + response.asPrettyString());

		System.out.printf("data statuss ", response.getStatusLine().contains("200") ? "true " : "false");

	}
}
