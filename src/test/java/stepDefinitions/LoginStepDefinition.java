package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class LoginStepDefinition {
	WebDriver driver;
	LoginPage loginPage;
	MyAccountPage myAccountPage;

	List<HashMap<String, String>> datamap; // Data driven

	public Logger logger; // for logging
	public ResourceBundle rb; // for reading properties file
	public String br; // to store browser name

	@Before
	public void setup() throws IOException {
		// for log
		logger = LogManager.getLogger(this.getClass());

		/*
		 * ResourceBundle.getBundle("config"); br = rb.getString("browser");
		 */

		File file = new File(".\\src\\test\\resources\\config.properties");
		FileInputStream fileInputStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		br = properties.getProperty("browser");

	}

	@After
	public void tearDown(Scenario scenario) {
		System.out.println("Scenario status ======>" + scenario.getStatus());
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}
		driver.quit();
	}

	@Given("User Launch browser")
	public void user_launch_browser() {
		if (br.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (br.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (br.equals("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@Given("opens URL {string}")
	public void opens_url(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	@When("User enters username as {string} and Password as {string}")
	public void user_enters_username_as_and_password_as(String username, String password) {
		loginPage = new LoginPage(driver);

		loginPage.setLoginUsername(username);
		logger.info("Provided username ");
		loginPage.setLoginPassword(password);
		logger.info("Provided Password ");
	}

	@When("Click on Login button")
	public void click_on_login_button() {
		loginPage.clickBtnLogin();
		logger.info("Clicked on Login button");
	}

	@Then("User navigates to MyAccount Page")
	public void user_navigates_to_my_account_page() {
		myAccountPage = new MyAccountPage(driver);
		boolean isLogoutLinkVisible = myAccountPage.isMyAccountVisible();
		Assert.assertEquals(isLogoutLinkVisible, true);
		if (isLogoutLinkVisible) {
			logger.info("Login Success ");
			Assert.assertTrue(true);
		} else {
			logger.error("Login Failed ");
			Assert.assertTrue(false);
		}
	}

	// ******* Data Driven test method **************
	@Then("check that user navigates to MyAccount Page by passing username and password with excel row {string}")
	public void check_that_user_navigates_to_my_account_page_by_passing_username_and_password_with_excel_row(
			String rows) throws InterruptedException {
		datamap = DataReader.data(System.getProperty("user.dir") + "\\testData\\zumicLoginData.xlsx", "Sheet1");

		int index = Integer.parseInt(rows) - 1;
		String username = datamap.get(index).get("username");
		String password = datamap.get(index).get("password");
		String exp_res = datamap.get(index).get("type");

		loginPage = new LoginPage(driver);
		loginPage.setLoginUsername(username);
		loginPage.setLoginPassword(password);
		loginPage.clickBtnLogin();
		myAccountPage = new MyAccountPage(driver);

		try {
			boolean logoutLnk = myAccountPage.isMyAccountVisible();
			System.out.println(logoutLnk + " ..........");
			if (exp_res.equals("Valid")) {
				if (logoutLnk == true) {
					myAccountPage.clickLogout();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}

			if (exp_res.equals("Invalid")) {
				if (logoutLnk == true) {
					myAccountPage.clickLogout();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {

			Assert.assertTrue(false);
		}
		driver.close();
	}

	// ******* Account Registration Methods **************
}
