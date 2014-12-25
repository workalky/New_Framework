package com.linkedIn.loginPage.Verification;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.linkedIn.init.Common;

public class LoginPageVerification extends AbstractPage {

	public LoginPageVerification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	Common common = new Common(driver);

	static String test_result = "";

	public void verificationMethod(WebElement element,
			String VerificationMessage) {

		if (element.isDisplayed()) {
			common.logverification(VerificationMessage);
			common.logStatus(PASSED);
		} else {
			test_result = "failed";
			common.logverification(VerificationMessage);
			common.logStatus(FAILED);
		}

		/*if (!test_result.equals("failed")) {
			common.log("<h2> <font color=238E23>--<img src=\"passed.png\"/></font> </h2></br></br>");
			//common.log("<h2 style=color:red> Test failed </h2>");
			test_result = "";
		} else {
			common.log("<h2><font color=#FF0000>--<img src=\"failed.png\"/></font> </h2></br></br>");
			//common.log("<h2 style=color:green> Test Passed </h2>");
			test_result = "";
		}*/

	}

	public void verificationMethodEmail(WebElement element,
			String VerificationMessage) {

		if (common.isElementDisplayed(element)) {
			common.logverification(VerificationMessage);
			common.logStatus(PASSED);
		} else {
			/* test_result = "failed"; */
			common.logverification(VerificationMessage);
			common.logStatus(FAILED);
		}

		/*
		 * if (test_result.equals("failed")) {
		 * 
		 * common.log("<h2 style=color:red> Test failed </h2>"); test_result =
		 * ""; } else {
		 * 
		 * common.log("<h2 style=color:green> Test Passed </h2>"); test_result =
		 * ""; }
		 */

	}

	public void VerificationMethod_List_Last(List<WebElement> elements,
			String VerificationMessage) {

		if (elements.size() >= 0) {

			for (WebElement element : elements) {

				common.log("<b>Validation Message:- </b>  " + element.getText()
						+ "</br>");

				common.log("=========================================================================================</br></br>");

			}
			common.logverification(VerificationMessage);
			common.logStatus(PASSED);
		} else {
			/* test_result = "failed"; */
			common.logverification(VerificationMessage);
			common.logStatus(FAILED);
		}

		/*
		 * if (test_result.equals("failed")) {
		 * 
		 * common.log("<h2 style=color:red> Test failed </h2>"); test_result =
		 * ""; } else {
		 * 
		 * common.log("<h2 style=color:green> Test Passed </h2>"); test_result =
		 * ""; }
		 */
	}

	public void verificationNotNull(WebElement element, String Tabname) {
		common.pause(1);
		if (element.getText().equals(null)) {
			common.log("data not selected for " + Tabname);
			common.logStatus(FAILED);
		} else {
			common.log("data selected Successfully  for " + Tabname);
			common.logStatus(PASSED);
		}
	}

	public void verifytextbox(WebElement element, String tocompare, String msg) {
		if (tocompare.equals(element.getAttribute("value"))) {
			common.logverification(msg);
			common.logStatus(PASSED);
		} else {
			// System.out.println(tocompare+" == "+element.getAttribute("value"));
			common.logverification(msg);
			common.logStatus(FAILED);
		}
	}

	public void verifyselecteddata(String selected, String toverify,
			String tabname) {
		System.out.println(selected + " == " + toverify);
		if (selected.equals(toverify)) {
			common.logverification("Selected Data is present on " + tabname
					+ " tab");
			common.logStatus(PASSED);
		} else {
			common.logverification("Selected Data is not present on " + tabname
					+ " tab");
			common.logStatus(FAILED);
		}

	}

	/*--------------------------VERIFICATION METHOD------------------------------------------------------------------*/

	public void verificationMethod(WebElement element, String element_name,
			String element_type, String page_name) {

		/*
		 * Actions move_to_element=new Actions(driver);
		 * move_to_element.moveToElement(element).build().perform();
		 * common.pause(3);
		 */

		if (common.isElementDisplayed(element)) {

			common.log("'" + element_name + "' " + element_type
					+ " verification on " + page_name
					+ " page. <h3 style=color:green>PASSED</h3>");
			Assert.assertTrue(true);
		} else {

			common.log("'" + element_name + "' " + element_type
					+ " verification on " + page_name
					+ " page. <h3 style=color:red>FAILED</h3>");

			Assert.assertTrue(false);
		}

	}

	/*------------------------------------------------------------------------------------------------------------------------*/

}
