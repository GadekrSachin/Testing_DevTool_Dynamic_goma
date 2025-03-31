package api;

import static io.restassured.RestAssured.given;

import com.factory.Base_driver;
import org.testng.Assert;
import allapi.EndPoint; 
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Api {

	Base_driver basedriver = new Base_driver();
//	Properties props = ConfigManager.getProperties();

	String Form_ID = "";

	public String baseurl() {
//		return RestAssured.baseURI = (props.getProperty("ApiBase_url"));

		return RestAssured.baseURI = EndPoint.API_BASEURL;
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
	
	
	public void Post(String url, String payload) {
        Response response = 
                given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(baseurl() + url)
                .then()
                .extract()
                .response();
                
        System.out.println("POST Response:\n" + response.asPrettyString());
         
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 201, "POST request failed with status: " + statusCode);
    }	
	
	
	public void Put(String url, String payload) {
        Response response = 
                given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .put(baseurl() + url)
                .then()
                .extract()
                .response();
                
        System.out.println("PUT Response:\n" + response.asPrettyString());
        // Assert for PUT success, assuming 200 is expected
        Assert.assertTrue(response.getStatusCode() == 200, "PUT request failed with status: " + 	response.getStatusCode());
	}
	
	
	public void Delete(String url) {
        Response response = 
                given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseurl() + url)
                .then()
                .extract()
                .response();
                
        System.out.println("DELETE Response:\n" + response.asPrettyString());
        // Assert for DELETE success (status 200 or 204)
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 204, "DELETE request failed with status: " + statusCode);
 
        String responseBody = response.asString().toLowerCase();
        Assert.assertTrue(responseBody.contains("success") || responseBody.contains("deleted"), "Delete response did not contain expected success indicator. Response Body: " + responseBody);
    }
}
