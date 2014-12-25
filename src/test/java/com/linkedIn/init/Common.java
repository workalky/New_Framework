package com.linkedIn.init;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.linkedIn.loginPage.Index.LoginPageIndex;

/**
 * Define Common Webdriver
 */

public class Common {

	Date date = new Date();

	protected Wait<WebDriver> wait;
	protected WebDriver driver;

	protected static long end_date;
	protected static Date end_ondate;
	protected static int diff_date;

	protected static String string_start_date;
	protected static String string_end_date;
	protected static String string_diff_time;

	public Common(WebDriver driver) {

		this.driver = driver;
	}

	/* "findElement" method finds element by it is identifier */
	/**
	 * Find web-element for given locator.
	 * 
	 * @param elementName
	 * @return
	 */

	public WebElement findElement(String elementName) {

		String locator;

		locator = elementName;

		int count = 0;
		while (count < 4) {
			try {
				if (locator.startsWith("link=") || locator.startsWith("LINK=")) {
					locator = locator.substring(5); // remove "link=" from
													// locator
					try {
						if (locator.contains(" "))
							return driver.findElement(By
									.partialLinkText(locator));

						return driver.findElement(By.linkText(locator));
					} catch (Exception e) {
						return null;
					}
				}

				if (locator.startsWith("id=")) {
					locator = locator.substring(3); // remove "id=" from locator
					try {
						return driver.findElement(By.id(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("//")) {
					try {
						return driver.findElement(By.xpath(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("css=")) {

					locator = locator.substring(4); // remove "css=" from
													// locator
					try {
						return driver.findElement(By.cssSelector(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("name=")) {

					locator = locator.substring(5); // remove "name=" from
													// locator
					try {
						return driver.findElement(By.name(locator));
					} catch (Exception e) {
						return null;
					}
				} else {
					try {
						return driver.findElement(By.id(locator));
					} catch (Exception e) {
						return null;
					}

				}
			} catch (StaleElementReferenceException e) {
				e.toString();

				count = count + 1;
				// System.out.println("Trying["+
				// count+"] to recover from a stale element :" +
				// e.getMessage());
			}
			count = count + 4;
		}
		return null;

	}

	/**
	 * Checks checkbox or toggle element.
	 * 
	 * @param element
	 *            Checkbox element.
	 */

	public void scrollTo(WebElement element) {

		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = element;

		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 50;
		for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) {
			try {
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
						.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown)
						.release().perform();
				Thread.sleep(1000L);
			} catch (Exception e1) {
			}
		}

	}

	public void checkChkBox(WebElement element) {

		boolean isCheckBoxSet;

		isCheckBoxSet = element.isSelected();

		if (!isCheckBoxSet) {

			element.click();

		}

	}

	public void openMailinator(String emailAddress) {
		pause(3);
		String emailParsed[] = emailAddress.split("@");
		String url = "http://" + emailParsed[0] + ".mailinator.com";
		open(url);
	}

	/**
	 * Gets current time in the following format Month, Date, Hours, Minutes,
	 * Seconds, Millisecond.
	 * 
	 * @return Current date.
	 */
	public String getCurrentTimeStampString() {

		java.util.Date date = new java.util.Date();

		SimpleDateFormat sd = new SimpleDateFormat("MMddHHmmssSS");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone
				.getOffset(date.getTime()), "GMT"));
		sd.setCalendar(cal);
		return sd.format(date);
	}

	/**
	 * Takes screenshot and adds it to TestNG report.
	 * 
	 * @param driver
	 *            WebDriver instance.
	 */
	public void makeScreenshot(WebDriver driver, String screenshotName) {

		WebDriver augmentedDriver = new Augmenter().augment(driver);

		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";

		/* Copy screenshot to specific folder */
		try {
			/*
			 * String reportFolder = "target" + File.separator +
			 * "failsafe-reports" + File.separator + "firefox" + File.separator;
			 */
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) {
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot, new File(screenshotFolder
					+ File.separator + nameWithExtention).getAbsoluteFile());

		} catch (IOException e) {
			this.log("Failed to capture screenshot: " + e.getMessage());
		}

		log(getScreenshotLink(nameWithExtention, nameWithExtention)); // add
																		// screenshot
																		// link
																		// to
																		// the
																		// report
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public void log(String msg) {

		Reporter.log(msg);

	}

	/**
	 * Generates link for TestNG report.
	 * 
	 * @param screenshot_name
	 *            Screenshot name.
	 * @param link_text
	 *            Link text.
	 * @return Formatted link for TestNG report.
	 */
	public String getScreenshotLink(String screenshot_name, String link_text) {

		log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='../test-output/screenshots/" + screenshot_name + "'>"
				+ link_text + "</a>";
	}

	/**
	 * Checks whether the needed WebElement is displayed or not.
	 * 
	 * @param element
	 *            Needed element
	 * 
	 * @return true or false.
	 */
	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementNotDisplayed(WebElement element) {
		try {
			return !element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Assertion to check that given element is not being displayed.
	 * 
	 * @param locator
	 *            Locator of element.
	 */
	public void assertElementIsNotDisplayed(String locator) {

		Assert.assertFalse(isElementDisplayed(locator));
	}

	/**
	 * Wait(max. 1 minute) till given element does not disappear from page.
	 * 
	 * @param by
	 *            Locator of element.
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	public boolean waitForElementIsNotDisplayed(By by)
			throws InterruptedException {

		for (int second = 0;; second++) {
			if (second >= 60) {

				break;
			}
			try {
				if (!isElementDisplayed(by))
					break;
			} catch (Exception e) {
			}
			pause(1000);
		}
		return false;
	}

	/**
	 * Wait(max. 1 minute) till given element does not disappear from page.
	 * 
	 * @param by
	 *            Locator of element.
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	public boolean waitForElementIsDisplayed(By by) throws InterruptedException {

		for (int second = 0;; second++) {
			if (second >= 60) {

				break;
			}
			try {
				if (isElementDisplayed(by))
					break;
			} catch (Exception e) {
			}
			pause(1000);
		}
		return false;
	}

	public void waitForElement(WebElement webelement) {
		(new WebDriverWait(driver, 180)).until(ExpectedConditions
				.visibilityOf(webelement));

	}

	/**
	 * Checks if given elements is checked.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @return true if checked else false.
	 */
	public boolean isChecked(String locator) {

		return this.findElement(locator).isSelected();

	}

	/**
	 * Checks whether the needed WebElement is displayed or not using Object of
	 * By
	 * 
	 * @param elementLocator
	 * @return
	 */
	public boolean isElementDisplayed(By elementLocator) {
		try {
			return driver.findElement(elementLocator).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	/**
	 * Checks whether the visibility of Element Located
	 * 
	 * @param by
	 * @return
	 */
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
		return new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				return element.isDisplayed() ? element : null;
			}
		};
	}

	/**
	 * Wait up to String locator present
	 * 
	 * @param selector
	 */
	public void waitForElement(String xpath) {
		wait = new WebDriverWait(driver, 50);
		try {
			wait.until(visibilityOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
		}
	}

	/**
	 * Finds handle to second window other than given handle to current window
	 * and switches to as well.
	 * 
	 * @param handleCurrentWindow
	 * @return handleSecondWindow
	 */
	public String findAndSwitchToSecondWindow(String handleCurrentWindow) {

		pause(1000);
		Set<String> windows = driver.getWindowHandles();
		String handleSecondWindow = null;
		for (String window : windows) {
			if (!window.contains(handleCurrentWindow)) {
				handleSecondWindow = window;
			}
		}

		// Switch to the second window.
		try {

			pause(2000);

			driver.switchTo().window(handleSecondWindow);

		} catch (Throwable failure) { // If there is problem in switching
										// window, then re-try.

			pause(1000);

			driver.switchTo().window(handleSecondWindow);

		}

		return handleSecondWindow;

	}

	/**
	 * Wait up to By element present
	 * 
	 * @param element
	 */
	public void waitForElement(By element) {

		try {
			wait = new WebDriverWait(driver, 60);
			wait.until(visibilityOfElementLocated(element));
		} catch (Exception e) {
		}
	}

	/**
	 * Clicks on visible or not visible element.
	 * 
	 * @param element
	 *            Web element.
	 */
	public void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"return arguments[0].click();", element);
		// this.waitForAjax("0");
	}

	/**
	 * Generates random Characters;
	 * 
	 * @param length
	 *            Length of the generated symbols.
	 * 
	 * @return StringBuffer object.
	 */
	public static String generateRandomChars(int length) {
		String random = RandomStringUtils.random(length);
		return random;
	}

	/**
	 * Mouse Hover in Web element
	 * 
	 * @param element
	 */
	public void mouseOver(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();

	}

	/**
	 * Get text in a given element.
	 * 
	 * @param elementName
	 *            Locator of element.
	 * 
	 * @return text in given element.
	 */
	public String getText(String elementName) {

		String text;

		try {
			text = this.findElement(elementName).getText();

		} catch (Exception e) {

			text = "Element was not found";
		}

		return text;
	}

	public void open(String url) {

		driver.get(url);
	}

	/**
	 * Get value of given element dynamically.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @return Dynamic value.
	 */
	public String getValue(String locator) {

		return this.findElement(locator).getAttribute("value");
	}

	/**
	 * Assertion to check that two values are equal.
	 * 
	 * @param value1
	 *            Value-1.
	 * @param value2
	 *            Value-2.
	 */
	public void assertTwoValuesAreEqual(Object value1, Object value2) {

		Assert.assertEquals(value1, value2);
	}

	/**
	 * Checks if given element is being displayed on page sending string of By.
	 * 
	 * @param elementName
	 *            Locator of element.
	 * 
	 * @return true if displayed else false.
	 */
	public boolean isElementDisplayed(String elementName) {

		WebElement webElement;
		try {
			webElement = this.findElement(elementName);
			return webElement.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait till given element is present.
	 * 
	 * @param locator
	 *            Locator of element.
	 */
	public void waitForConditionIsElementPresent(String locator) {

		for (int second = 0;; second++) {
			if (second >= 10) {
				break;
			}

			try {
				if (isElementPresent(locator))
					break;
			} catch (Throwable failure) {
			}

			pause(1000);
		}

	}

	/**
	 * Checks if element loaded in browser memory.
	 * 
	 * @param locator
	 *            Locator of element.
	 * @return true if loaded else false.
	 */
	public boolean isElementPresent(String locator) {

		WebElement webElement = this.findElement(locator);
		if (webElement != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Assertion to check that given element is not present.
	 * 
	 * @param locator
	 *            Locator of element.
	 */
	public void assertElementNotPresent(String locator) {

		Assert.assertFalse(isElementPresent(locator));
	}

	/**
	 * Assertion to check that given element ispresent.
	 * 
	 * @param locator
	 *            Locator of element.
	 */
	public void assertElementPresent(String locator) {

		Assert.assertTrue(isElementPresent(locator));
	}

	/**
	 * Pauses for given seconds.
	 * 
	 * @param secs
	 */
	public void pause(int secs) {
		try {
			Thread.sleep((secs) * 1000);
		} catch (InterruptedException interruptedException) {

		}
	}

	/**
	 * Get random numeric of given length.
	 * 
	 * @param length
	 *            desired length.
	 * @return
	 */
	public int randomNumericValueGenerate(int length) {

		Random randomGenerator = new Random();

		int randomInt = randomGenerator.nextInt(length);
		return randomInt;
	}

	/**
	 * Clears and type new value into given text-box.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @param value
	 *            New text/value.
	 */
	public void type(String locator, String value) {

		this.findElement(locator).clear();
		this.findElement(locator).sendKeys(value);

	}

	/**
	 * Wait till all ajax calls finish.
	 * 
	 * @param num
	 *            Number of ajax calls to finish.
	 */
	public void waitForAjax(String num) {

		String ajax;

		ajax = this.ajaxFinised(num);

		for (int second = 0;; second++) {
			if (second >= 20) {
				break;
			} else if (ajax.equals("true")) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Select By Value
	 * 
	 * @param element
	 * @param value
	 */
	public void selectFromCombo_by_value(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Select By Visible Value
	 * 
	 * @param element
	 * @param value
	 */
	public void selectFromComboByVisibleText(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByVisibleText(value);
		;
	}

	/**
	 * Wait till ajax call finish.
	 * 
	 * @throws InterruptedException
	 */
	public void WaitForAjax() throws InterruptedException {

		String ajax;
		ajax = this.ajaxFinised("1");

		for (int second = 0;; second++) {
			if (second >= 15) {
				break;
			} else if (ajax.equals("true")) {
				break;
			}
			Thread.sleep(1000);
		}

	}

	/**
	 * Checks that all ajax calls are completed on page.
	 * 
	 * @param num
	 *            Number of ajax calls to wait for completion.
	 * 
	 * @return "true" if completed else "false".
	 */
	public String ajaxFinised(String num) {

		Object isAjaxFinished;

		JavascriptExecutor js = (JavascriptExecutor) driver;

		isAjaxFinished = js.executeScript("return jQuery.active == " + num);

		return isAjaxFinished.toString();

	}

	/**
	 * Clears and type new value into given text-box.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @param value
	 *            New text/value.
	 */
	public void typeNew(String locator, String newValue) {

		this.findElement(locator).clear();
		this.findElement(locator).sendKeys(newValue);

	}

	public void drag_and_drop(WebElement element, int xoffset, int yoffset) {
		Actions action = new Actions(driver);
		action.dragAndDropBy(element, xoffset, yoffset).build().perform();
		;
	}

	/*
	 * 
	 * 
	 * Move to any element
	 * 
	 * @param element name
	 */
	public void move_to_element(WebElement element) {

		pause(1);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();

	}

	public void doubleclick(WebElement element)

	{

		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().build().perform();

	}

	public void log(String msg, final int logger_status) {

		switch (logger_status) {

		case ILoggerStatus.NORMAL:
			Reporter.log(msg + "</br>");
			break;

		case ILoggerStatus.ITALIC:
			Reporter.log("<i>" + msg + "</i></br>");
			break;

		case ILoggerStatus.STRONG:
			Reporter.log("<strong>" + msg + "</strong></br>");
			break;
		}
	}

	public void logStatus(final int test_status) {

		switch (test_status) {

		case ITestStatus.PASSED:
			Reporter.log("<font color=238E23>--<img src=\"passed.png\"/></font></br></br>");
			break;

		case ITestStatus.FAILED:
			Reporter.log("<font color=#FF0000>--<img src=\"failed.png\"/></font></br></br>");
			break;

		case ITestStatus.SKIPPED:
			Reporter.log("<font color=#FFFF00>--Skipped</font></br>");
			break;

		default:
			break;
		}

	}

	public void logcase(String msg) {

		Reporter.log("<strong> <h3 style=\"color:DarkViolet\"> " + msg
				+ "</h3> </strong></br>");

		Reporter.log(" <table width=\"100%\" border = \"1\" cellspacing=\"0\"> <tr> "
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> OS Environment </font></th>"
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> Browser Name </font></th> "
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> Browser Version </font></th> </tr>"
				+ "<tr> <td align=\"center\" valign=\"middle\"><i>  "
				+ SeleniumInit.osName
				+ "</i></td><td align=\"center\" valign=\"middle\"><i>"
				+ SeleniumInit.browserName
				+ "</i> </td><td align=\"center\" valign=\"middle\"><i>"
				+ SeleniumInit.browserVersion + "</i></td></tr></table><br>");

	}

	public void logstep(String msg) {

		Reporter.log("<strong><u>" + msg + "</u></strong></br>");

	}

	public void logverification(String msg) {

		Reporter.log(msg + "</br>");

	}

	public void Drag_And_drop_JS(WebElement element, String x, String y) {

		String xto = x;
		String yto = y;
		((JavascriptExecutor) driver)
				.executeScript(
						"function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b]."
								+ "test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),"
								+ "a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent"
								+ "(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):"
								+ "(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,"
								+ "a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} "
								+ "var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|"
								+ "resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; "
								+ "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],"
								+ "arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
						element, xto, yto);
	}

	public void EmbedSaucelabVideo(WebDriver driver) {
		if (SeleniumInit.targetBrowser.contains("saucelab")) {

			System.out.println("in Saucelab");
			SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
			System.out
					.println("Session : " + sessionId
							+ " :  https://saucelabs.com/tests/"
							+ sessionId.toString());
			log("Sauce Lab Video : <a href='https://saucelabs.com/tests/"
					+ sessionId.toString() + "'>Click here</a>");
			log("<script src='https://saucelabs.com/video-embed/"
					+ sessionId.toString() + ".js'> </script>");
		}
	}

	/**
	 * 
	 * get excel sheet details from this method , pass parameters of Row number
	 * and Column number of cell
	 * 
	 * @param row_Num
	 * @param col_Num
	 * @return
	 */

	public String getTestDetails(int row_Num, int col_Num) {

		String workbookPath = SeleniumInit.workbook_name;

		String sheetName = SeleniumInit.sheet_name;

		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(workbookPath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String test_name = null;
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheet(sheetName);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				if (row.getRowNum() == row_Num) {

					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						if (cell.getColumnIndex() == col_Num)

							if (cell.getColumnIndex() == col_Num)

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_NUMERIC:
									test_name = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_STRING:
									test_name = cell.getStringCellValue();
									break;
								}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return test_name;
	}

	/**
	 * 
	 * Set details in excel sheet in cell pass row number and column number of
	 * cell as parameters
	 * 
	 * @param row_Num
	 * @param col_Num
	 * @param value
	 */

	public void setTestDetails(int row_Num, int col_Num, String value) {

		String workbookPath = SeleniumInit.workbook_name;

		String sheetName = SeleniumInit.sheet_name;

		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(workbookPath));
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheet(sheetName);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				if (row.getRowNum() == row_Num) {

					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						if (cell.getColumnIndex() == col_Num) {

							switch (cell.getCellType()) {

							case Cell.CELL_TYPE_BLANK:
								cell.setCellValue(value);
								break;
							case Cell.CELL_TYPE_STRING:
								cell.setCellValue(value);
								break;

							}
						}

					}
					try {
						FileOutputStream out = new FileOutputStream(new File(
								workbookPath));
						workbook.write(out);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public String secToMin(int sec) {

		int min = 0;

		if (sec > 59) {

			min = (sec / 60);
			sec = sec % 60;

		}

		return min + " minutes " + sec + " seconds";
	}

	/**
	 * 
	 * call this method will show test start time , end time and total test
	 * execution time in test report in HTML table
	 * 
	 */

	public void testExcutionDetails() {

		end_date = System.currentTimeMillis();
		end_ondate = new Date();

		diff_date = (int) ((end_date - SeleniumInit.start_date) / 1000);

		string_start_date = SeleniumInit.start_ondate + "";
		string_end_date = end_ondate + "";
		string_diff_time = secToMin(diff_date);

		setTestDetails(LoginPageIndex.row_num, 8, string_start_date);
		setTestDetails(LoginPageIndex.row_num, 9, string_end_date);
		setTestDetails(LoginPageIndex.row_num, 10, string_diff_time);

		Reporter.log(" <table width=\"100%\" border = \"1\" cellspacing=\"0\"> <tr> "
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> Start Time</font> </th> "
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> End Time</font> </th> "
				+ "<th bgcolor=\"Thistle\"><font color=\"white\"> Test Excution Time</font> </th> </tr>"
				+ "<tr> <td align=\"center\" valign=\"middle\"><i>  "
				+ string_start_date
				+ "</i></td><td align=\"center\" valign=\"middle\"><i>"
				+ string_end_date
				+ "</i> </td><td align=\"center\" valign=\"middle\"><i>"
				+ string_diff_time + "</i></td></tr></table><br>");

	}

}
