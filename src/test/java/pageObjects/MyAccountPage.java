package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	// Element

	@FindBy(xpath = "//i[@class='caret']")
	WebElement drpdwnMyAccount;

	@FindBy(xpath = "//a[normalize-space()='Logout']")
	WebElement lnkLogout;

	public boolean isMyAccountVisible() {

		try {
			drpdwnMyAccount.click();
			return lnkLogout.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void clickLogout() {
	//	drpdwnMyAccount.click();
		lnkLogout.click();
	}
}
