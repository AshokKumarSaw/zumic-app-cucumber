package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	public LoginPage(WebDriver driver) {
		super(driver);
	}

//Elements
	

	@FindBy(name = "login")
	WebElement txtUsername;
	@FindBy(name = "password")
	WebElement txtPassword;
	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnLogin;

	// Methods
	

	public void setLoginUsername(String username) {
		txtUsername.sendKeys(username);
	}

	public void setLoginPassword(String password) {
		txtPassword.sendKeys(password);
	}

	public void clickBtnLogin() {
		btnLogin.click();
	}

}
