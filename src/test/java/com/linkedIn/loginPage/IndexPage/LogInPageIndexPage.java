package com.linkedIn.loginPage.IndexPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.linkedIn.init.Common;
import com.linkedIn.loginPage.Verification.AbstractPage;
import com.linkedIn.loginPage.Verification.LoginPageVerification;

public class LogInPageIndexPage extends AbstractPage {

	public LogInPageIndexPage(WebDriver driver) {
		super(driver);

	}

	Common common = new Common(driver);

	LoginPageVerification loginPageVerfication = new LoginPageVerification(
			driver);

	static int logstep = 1;

	/*----------------------- List of web elements/xpaths of page -----------------------*/

	@FindBy(xpath = ".//*[@id='btn-submit']")
	private WebElement joinNow_button;

	@FindBy(xpath = ".//*[@id='firstName-coldRegistrationForm']")
	private WebElement firstName_text;

	@FindBy(xpath = ".//*[@id='lastName-coldRegistrationForm']")
	private WebElement lastName_text;

	@FindBy(xpath = ".//*[@id='control_gen_1']/li[1]/label")
	private WebElement emailId_label;

	@FindBy(xpath = ".//*[@id='control_gen_1']/li[2]/label")
	private WebElement passwd_label;

	@FindBy(xpath = ".//*[@id='email-coldRegistrationForm']")
	private WebElement emailId_text;

	@FindBy(xpath = ".//*[@id='password-coldRegistrationForm']")
	private WebElement passwd_text;

	@FindBy(xpath = ".//*[@id='global-error']/div")
	private WebElement validation_message;

	List<WebElement> textfields = new ArrayList<WebElement>();
	
	
	
	/*----------------------- Test excution methods  -----------------------*/
	

	public void verify_Register_Page() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + "  <a href=\"http://www.linkedin.com\"> Open URL </a>");

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Observe page");

		common.pause(2);

		loginPageVerfication.verificationMethod(firstName_text,
				"Verify EmaiId field");

		common.pause(1);

		loginPageVerfication.verificationMethod(lastName_text,
				"Verify Password field");

		common.pause(1);

		loginPageVerfication.verificationMethod(joinNow_button,
				"Verify \"Sign In\" button");

		common.pause(1);

	}

	public void verify_Label_in_Page() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + " Open URL");

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Observe labels of page");

		common.pause(2);

		loginPageVerfication.verificationMethod(emailId_label,
				"Verify EmaiId field");

		common.pause(1);

		loginPageVerfication.verificationMethod(passwd_label,
				"Verify Password field");

		common.pause(1);
	}

	public void verify_Mendatory_Fields() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + " Open URL");

		common.pause(1);

		common.logstep("Step: " + (logstep++)
				+ " Observe mendatory fields for registration");

		common.pause(2);

		loginPageVerfication.verificationMethod(firstName_text,
				"Verify first name field");

		common.pause(1);

		loginPageVerfication.verificationMethod(lastName_text,
				"Verify last name field");

		common.pause(1);

		loginPageVerfication.verificationMethod(emailId_text,
				"Verify emailid field");

		common.pause(1);

		loginPageVerfication.verificationMethod(passwd_text,
				"Verify password field");

		common.pause(1);

	}

	public void verify_JoinNow_Button() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + " Open URL");

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Verify \"Join Now\" button");

		common.pause(2);

		loginPageVerfication.verificationMethod(joinNow_button,
				"Verify \"Join Now\" button");

		common.pause(1);

	}

	public void click_JoinNow_Button() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + " Open URL");

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Click \"Join Now\" button");

		common.pause(2);

		joinNow_button.click();

		common.pause(2);

		loginPageVerfication.verificationMethod(validation_message,
				"Verification message is verify.");

		common.pause(1);

	}

	public void textbox_Edit_Functionality() {

		logstep = 1;

		common.logstep("Step: " + (logstep++) + " Open URL");

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Enter values in text fields");

		common.pause(1);

		sendValuesTextfields();

		common.pause(1);

		common.logstep("Step: " + (logstep++) + " Observe value in text fields");

		common.pause(1);

		if (getValuesTextfields().equals("Editable")) {

			common.log("All textfields are editable");
		}

		common.pause(1);

	}
	
	/*----------------------- MISC methods of test execution -----------------------*/

	public void sendValuesTextfields() {

		textfields.add(firstName_text);
		textfields.add(lastName_text);
		textfields.add(emailId_text);
		textfields.add(passwd_text);

		for (WebElement textfield : textfields) {

			textfield.sendKeys("Editable");

			common.pause(1);

		}
	}

	public String getValuesTextfields() {

		String value = null;

		textfields.add(firstName_text);
		textfields.add(lastName_text);
		textfields.add(emailId_text);
		textfields.add(passwd_text);

		for (WebElement textfield : textfields) {

			value = textfield.getText();

			common.pause(1);

		}

		return value;
	}

}
