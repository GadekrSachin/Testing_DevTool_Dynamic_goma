package com.pages;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.factory.Base_driver;

import allapi.EndPoint;

public class Home {

	WebDriver driver;
	Properties props = ConfigManager.getProperties();
	Common common = new Common();
	Buttons button = new Buttons();

	NetworkInterceptorUtil networkUtil = new NetworkInterceptorUtil(Base_driver.driver);

	private By Add_Form = By.xpath("(//i[@class=\"ri-add-fill\"])[2]");
	private By Add_Form_mobile = By.xpath("(//i[@class=\"ri-add-fill\"])[1]");

	private By Delete_Form = By.xpath("//i[@class=\"ri-delete-bin-line\"]");
	private By All_Form = By.xpath("//i[@class='ri-more-2-fill']");
	private By First_name = By.xpath("//input[@placeholder=\"Name this form\"]");
	private By Last_name = By.xpath("//input[@placeholder=\"Describe this form\"]");
	private By Select_Service = By
			.xpath("//div[contains(@class, 'MuiSelect-select') and contains(@class, 'MuiSelect-multiple')]");
	private By All_catagory = By
			.xpath("//span[@class='MuiAccordionSummary-content MuiAccordionSummary-contentGutters css-1b8uc0m']");
	private By All_service = By
			.xpath("//span[@class='MuiTypography-root MuiTypography-body1 MuiListItemText-primary css-1w22uhs']");
	private By backc = By
			.xpath("//div[@class=\"MuiBackdrop-root MuiBackdrop-invisible MuiModal-backdrop css-1lbe2ow\"]");
	private By frequency = By.xpath("(//div[@aria-haspopup=\"listbox\"])[2]");
	private By month = By.xpath("(//li[@role=\"option\"])[2]");
	private By save = By.xpath("//button[contains(text(), 'Save')]");
	private By add_question = By.xpath("(//span[@class='svc-add-new-item-button__text'])[1]");
	private By save_Q = By.xpath("//button[contains(text(),\"Save\")]");
	private By red_Line = By.xpath("//span[@class=\"MuiTypography-root MuiTypography-body1 css-1w22uhs\"]");
	private By appointmentcard = By.xpath("//i[@class='ri-newspaper-line']");
	private By Date_Point = By.xpath("//div[@class=\"MuiFormControl-root MuiTextField-root css-m925do\"]");
	private By ok_button = By.xpath("//button[contains(text(),'OK')]");

	//	Add_Client
	private By add_external_button = By.xpath("//i[@class=\"ri-add-line\"]");
	private By add_internal_button = By.xpath("//p[@class=\"MuiTypography-root MuiTypography-body1 css-1j5h8oe\"]");
	private By Fname = By.xpath("//input[@placeholder=\"First Name\"]");

	private By Lname = By.xpath("//input[@placeholder=\"First Name\"]");


	private By questionTitle(int index) {
		return By.xpath(
				"(//h5[@class='sd-title sd-element__title sd-question__title']//span[@class='sv-string-editor'])["
						+ index + "]");
	}

	public Home(WebDriver driver) {
		this.driver = driver;
	}

	public void add_client(String FName, String LName, String cCode, String Mnumber, String Email,
			String Gender, String DOB, String HAddress) {
		Base_driver.driver.findElement(add_external_button).click();
		Base_driver.driver.findElement(add_internal_button).click();


	}

	public void verify_appointments_as_per_date() {
		Base_driver.driver.findElement(Date_Point).click();
		List<WebElement> calendarButtons = Base_driver.driver
				.findElements(By.xpath("//button[@aria-selected='false']"));

		if (!calendarButtons.isEmpty()) {

			Random random = new Random();
			int randomIndex = random.nextInt(calendarButtons.size());

			WebElement randomButton = calendarButtons.get(randomIndex);
			String selectedDate = randomButton.getText().trim();
			randomButton.click();
			System.out.println("Clicked on date: " + selectedDate);
			Base_driver.driver.findElement(ok_button).click();
			Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

			WebElement datepick = Base_driver.driver.findElement(By.xpath("//input[@placeholder=\"EEEE DD MMMM\"]"));
			String datePickerValue = datepick.getAttribute("value").trim();

			if (datePickerValue.contains(selectedDate)) {
				System.out.println("Date Picker displays the correct selected date: " + datePickerValue);
			} else {
				System.out.println(
						"Date Picker validation failed! Expected: " + selectedDate + ", Found: " + datePickerValue);
			}
		} else {
			System.out.println("❌ No available dates found to select.");
		}
	}

	public void redline() {
		String time = Base_driver.driver.findElement(red_Line).getText();
		LocalTime now = LocalTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
		String formattedTime = now.format(formatter);

		System.out.println("formattedTime" + formattedTime);
		assert formattedTime.matches("\\d{1,2}:\\d{2} (AM|PM)") : "Time format is incorrect";
	}


	public void delete_form() {
		List<WebElement> elements = Base_driver.driver.findElements(All_Form);
		if (!elements.isEmpty()) {
		    WebElement lastElement = elements.get(elements.size() - 1);
		    lastElement.click();
		} else {
		    System.out.println("No elements found with the given locator.");
		}

		Base_driver.driver.findElement(Delete_Form).click();

		networkUtil.startListening(EndPoint.DELETE_FORM);
		common.delete_pop_up();


		String jsonRequest = networkUtil.getLatestJsonRequest();
		String jsonResponse = networkUtil.getLatestJsonResponse();

		if (jsonRequest != null) {
			System.out.println("📌 Captured API Request Payload: " + jsonRequest);
		} else {
			System.out.println("❌ No API request payload captured!");
		}

		if (jsonResponse != null) {
			System.out.println("📌 API JSON Response: " + jsonResponse);
		} else {
			System.out.println("❌ No API response captured!");
		}

	}

	public void user_drag() throws InterruptedException {
		System.out.println("data");

		// way1
//		WebElement appointmentCards = Base_driver.driver
//				.findElement(By.xpath("(//i[@class=\"ri-newspaper-line\"])[3]"));
//		WebElement dropAreas = Base_driver.driver.findElement(By.xpath(
//				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[2]/table[1]/tbody[1]/tr[83]/td[1]"));
//
//		Common.dragAndDrop(appointmentCards, dropAreas);
//		button.clickFixedButton(Base_driver.driver);

		// way2

		WebElement appointmentCard = Base_driver.driver.findElement(appointmentcard);
//		List<WebElement> appointmentCard = driver.findElements(By.xpath("(//i[@class='ri-newspaper-line'])"));

		common.scrollToElement(Base_driver.driver, appointmentCard);
		Thread.sleep(2000);
		common.dropAppointmentInAvailableSlot(Base_driver.driver, appointmentCard);
		Thread.sleep(2000);
		button.clickFixedButton(Base_driver.driver);
	}

	public void form_fill() throws InterruptedException {

		Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));

		if (props.getProperty("Base_Resolution").equals(props.getProperty("Mobile_resolution"))) {
			Base_driver.driver.findElement(Add_Form_mobile).click();
		} else {
			Base_driver.driver.findElement(Add_Form).click();
		}

		Base_driver.driver.findElement(First_name).sendKeys(props.getProperty("Form_FirstName"));
		Base_driver.driver.findElement(Last_name).sendKeys(props.getProperty("Form_Desc"));
		Base_driver.driver.findElement(Select_Service).click();

		// multiple catagory

		String[] categories = props.getProperty("category").split("\\s*,\\s*");
		String[] services = props.getProperty("service").split("\\s*,\\s*");

		for (String categoryName : categories) {
			categoryName = categoryName.trim();
			boolean categoryFound = false;

			List<WebElement> categoryElements = Base_driver.driver.findElements(All_catagory);

			for (WebElement category : categoryElements) {
				if (category.getText().trim().equalsIgnoreCase(categoryName)) {
					category.click();
					categoryFound = true;

					Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
					List<WebElement> serviceElements = Base_driver.driver.findElements(All_service);
					boolean anyServiceSelected = false;
					for (String serviceName : services) {
						serviceName = serviceName.trim();
						boolean serviceFound = false;

						for (WebElement service : serviceElements) {
							if (service.getText().trim().equalsIgnoreCase(serviceName)) {
								String ariaPressed = service.getAttribute("aria-pressed");
								if (ariaPressed == null || ariaPressed.equals("false")) {
									service.click();
									System.out.println("Service selected: " + serviceName);
									anyServiceSelected = true;

									Base_driver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
								} else {
									System.out.println("Service already selected: " + serviceName);
								}
								serviceFound = true;
								break;
							}
						}

						if (!serviceFound) {
							System.out.println("Service not found in " + categoryName + ": " + serviceName);
						}
					}
					if (anyServiceSelected) {
						category.click();
					}
					break;
				}
			}
			if (!categoryFound) {
				System.out.println("Category not found: " + categoryName);
			}
		}

		//
		Base_driver.driver.findElement(backc).click();
		Base_driver.driver.findElement(frequency).click();
		Base_driver.driver.findElement(month).click();
		Base_driver.driver.findElement(save).click();

		WebElement ss = Base_driver.driver.findElement(By.cssSelector(".svc-tab-designer_content"));
		JavascriptExecutor js = (JavascriptExecutor) Base_driver.driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ss);

		String questionsString = props.getProperty("questions");

		String[] questions = questionsString.split(",");

		for (int i = 0; i < questions.length; i++) {
			Base_driver.driver.findElement(add_question).click();
			By questionLocator = questionTitle(i + 1);
			Thread.sleep(1000);
			Base_driver.driver.findElement(questionLocator).click();
			Base_driver.driver.findElement(questionLocator).sendKeys(questions[i]);
		}

		networkUtil.startListening(EndPoint.ADD_FORM_TARGET_API);
		Base_driver.driver.findElement(save_Q).click();
		Thread.sleep(3000);

		String jsonRequest = networkUtil.getLatestJsonRequest();
		String jsonResponse = networkUtil.getLatestJsonResponse();

		if (jsonRequest != null) {
			System.out.println("📌 Captured API Request Payload: " + jsonRequest);
		} else {
			System.out.println("❌ No API request payload captured!");
		}

		if (jsonResponse != null) {
			System.out.println("📌 API JSON Response: " + jsonResponse);
		} else {
			System.out.println("❌ No API response captured!");
		}

		int formId = new JSONObject(jsonRequest).getInt("formId");
		System.out.println("formId : " + formId);
		if (formId <= 0) {
			System.out.println(" Test Failed: formId is 0 or less!");
			throw new AssertionError("Test Failed: Invalid formId detected");
		} else {
			System.out.println("✅ Test Passed: formId is valid -> " + formId);
		}

	}

}
