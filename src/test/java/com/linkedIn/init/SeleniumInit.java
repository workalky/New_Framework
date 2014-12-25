package com.linkedIn.init;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.linkedIn.loginPage.Index.LoginPageIndex;
import com.linkedIn.loginPage.IndexPage.LogInPageIndexPage;
import com.linkedIn.loginPage.Verification.LoginPageVerification;

/**
 * Selenium class Initialization
 * 
 * 
 */

// @Listeners(ATUReportsListener.class)
public class SeleniumInit implements ILoggerStatus {

	/* Minimum requirement for test configuration */

	public static String testUrl; // Test url
	protected String seleniumHub; // Selenium hub IP
	protected String seleniumHubPort; // Selenium hub port
	public static String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;

	protected static String workbook_name; // excel workbook path
	protected static String sheet_name; // excel sheet name

	protected static long start_date; // test start date
	protected static Date start_ondate;

	protected static String browserName; // testing browser name
	protected static String browserVersion; // testing browser version
	protected static String osName; // testing os environment

	// screen-shot folder
	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test

	protected static Logger logger = Logger.getLogger("testing");

	protected WebDriver driver;

	Common common = new Common(driver);

	/* Page's declaration */
	/*
	 * protected PleySignupIndexPage pleySignup; protected PleyHomePageIndexpage
	 * homepage; protected PleyLoginIndexPage loginpage; protected
	 * PleyFooterIndexpage pleyfooter; protected PleyGiveAsGiftIndexPage
	 * pleygiveasgift;
	 */

	protected LogInPageIndexPage loginPageIndexPage;
	protected LoginPageVerification loginPageVerfication;

	// protected DashboardPage dashboardPage;
	// protected TestData testdata;

	// protected RegisteredPage registeredPage;
	// And many more ...

	@BeforeSuite
	public void getTestInfo() {

	}

	/**
	 * Fetches suite-configuration from XML suite file.
	 * 
	 * @param testContext
	 */
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {

		System.out
				.println("\n----------------*********  In Configuration Class  *********----------------\n");

		testUrl = testContext.getCurrentXmlTest().getParameter("selenium.url");

		System.out.println("\n==========**********  " + testUrl
				+ "  **********==========\n");

		seleniumHub = testContext.getCurrentXmlTest().getParameter(
				"selenium.host");

		seleniumHubPort = testContext.getCurrentXmlTest().getParameter(
				"selenium.port");

		targetBrowser = testContext.getCurrentXmlTest().getParameter(
				"selenium.browser");

		workbook_name = testContext.getCurrentXmlTest().getParameter(
				"workbook.path");

		sheet_name = testContext.getCurrentXmlTest().getParameter(
				"testsheet.name");

	}

	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) throws IOException, InterruptedException,
			AWTException {

		currentTest = method.getName(); // get Name of current test.

		URL remote_grid = new URL("http://" + seleniumHub + ":"
				+ seleniumHubPort + "/wd/hub");

		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME)
				.getAbsolutePath();

		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME)
				.getAbsolutePath();

		DesiredCapabilities capability = null;
		if (targetBrowser == null || targetBrowser.contains("firefox")) {

			FirefoxProfile profile = new FirefoxProfile();

			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.download.manager.showWhenStarting",
					false);
			profile.setEnableNativeEvents(true);
			profile.setPreference("network.http.use-cache", false);

			capability = DesiredCapabilities.firefox();
			capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
			
			start_date = System.currentTimeMillis();
			start_ondate = new Date();

			driver = new RemoteWebDriver(remote_grid, capability);

			System.out
					.println("\n================********** Running Browser : FireFox **********================\n");

			

		} else if (targetBrowser.contains("ie8")) {

			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.ANY);
			capability.setBrowserName("internet explorer");

			capability
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			capability.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
					true);
			capability.setJavascriptEnabled(true);
			
			start_date = System.currentTimeMillis();
			start_ondate = new Date();

			System.out
					.println("\n================********** Running Browser : Internet Explorer **********================\n");

			

		} else if (targetBrowser.contains("chrome")) {

			System.out
					.println("\n================********** Running Browser : Google Chrome **********================\n");

			File chromeDriver = new File("lib/chromedriver.exe");
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			/*
			 * capability .setCapability("webdriver.chrome.driver",
			 * "E:\\Projects\\credible\\credible\\credible\\lib\\chromedriver.exe"
			 * ); System.setProperty("webdriver.chrome.driver",
			 * "E:\\Projects\\credible\\credible\\credible\\lib\\chromedriver.exe"
			 * );
			 */

			capability.setCapability("webdriver.chrome.driver",
					chromeDriver.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver",
					chromeDriver.getAbsolutePath());
			capability.setPlatform(Platform.ANY);

			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);


			start_date = System.currentTimeMillis();
			start_ondate = new Date();

			driver = new RemoteWebDriver(remote_grid, capability);
		} else if (targetBrowser.contains("ie11")) {
			capability = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver", "lib/IEDriverServer.exe");

			capability.setBrowserName("internet explorer");

			capability
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			capability.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
					true);
			capability.setJavascriptEnabled(true);
			
			start_date = System.currentTimeMillis();
			start_ondate = new Date();
			
			driver = new RemoteWebDriver(remote_grid, capability);

			System.out
					.println("\n================********** Running Browser : Internet Explorer **********================\n");

			

		} else if (targetBrowser.contains("safari")) {

			File safariplugin = new File("lib/WebDriver-2.safariextz");
			System.out.println("safari driver : "
					+ safariplugin.getAbsolutePath());
			// System.setProperty("webdriver.safari.driver",
			// safariplugin.getAbsolutePath());
			System.setProperty("webdriver.safari.driver",
					safariplugin.getAbsolutePath());

			capability = DesiredCapabilities.safari();
			/*
			 * capability.setCapability("safari.options.dataDir",
			 * "/Users/vipulbhai/Library/Safari");
			 */

			capability.setJavascriptEnabled(true);

			// driver = new RemoteWebDriver(remote_grid, capability);
			this.driver = new SafariDriver(capability);

			System.out
					.println("\n================********** Running Browser : Safari Browser **********================\n");

			start_date = System.currentTimeMillis();
			start_ondate = new Date();

		} else if (targetBrowser.contains("saucelab")) {

			capability = DesiredCapabilities.internetExplorer();

			capability.setCapability("platform", Platform.WIN8);

			capability.setCapability("version", "10");

			capability.setCapability("name", "LinkedIn");
			
			start_date = System.currentTimeMillis();
			start_ondate = new Date();

			this.driver = new RemoteWebDriver(
					new URL("http://rahul_kiwiqa:9624945a-603b-449f-848d-b2f2ef3faac4@ondemand.saucelabs.com:80/wd/hub"),
					capability);

			start_date = System.currentTimeMillis();
			start_ondate = new Date();

		} else if (targetBrowser.contains("Android")) {

			System.out.println("Android Device------");
			DesiredCapabilities caps = DesiredCapabilities.android();
			caps.setCapability("platform", Platform.ANY);
			caps.setCapability("version", "4.4");
			// caps.setCapability("device-orientation", "portrait");
			caps.setCapability("public", "public");
			caps.setCapability("name", "pley Automatin in mac");
			this.driver = new RemoteWebDriver(
					new URL(
							"http://rahul_kiwiqa:9624945a-603b-449f-848d-b2f2ef3faac4@ondemand.saucelabs.com:80/wd/hub"),
					caps);

			start_date = System.currentTimeMillis();
			start_ondate = new Date();
		}
		// driver = new RemoteWebDriver(remote_grid, capability);

		if (!targetBrowser.contains("Android")) {
			driver.manage().window().maximize();

			Capabilities capabilities = ((RemoteWebDriver) driver)
					.getCapabilities();
			browserName = capabilities.getBrowserName().toLowerCase();
			browserVersion = capabilities.getVersion().toString();
			osName = System.getProperty("os.name");

		}
		driver.get(testUrl);

		/*
		 * pleySignup = new PleySignupIndexPage(driver); homepage = new
		 * PleyHomePageIndexpage(driver); loginpage = new
		 * PleyLoginIndexPage(driver); pleyfooter = new
		 * PleyFooterIndexpage(driver); pleygiveasgift = new
		 * PleyGiveAsGiftIndexPage(driver);
		 */

		loginPageIndexPage = new LogInPageIndexPage(driver);
		loginPageVerfication = new LoginPageVerification(driver);

	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		try {

			System.out
					.println("\n================********** Tear Down **********================\n");

			String testName = testResult.getName();

			if (!testResult.isSuccess()) {

				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: "
						+ testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);

				/* Make a screenshot for test that failed */
				String screenshotName = common.getCurrentTimeStampString()
						+ testName;
				Reporter.log("<br>" + ""
						+ " <b>Please look to the screenshot - </b>");

				Reporter.log("<img src=\"failed.png\"/>--</font></br>");

				common.makeScreenshot(driver, screenshotName);

				common.setTestDetails(LoginPageIndex.row_num, 5, "Fail");

			} else {

				Reporter.log("<h3> <font color=#00FF00> Passed--<img src=\"passed.png\"/>--</font> </h2></br></br>");

				System.out.println("TEST PASSED - " + testName + "\n");

				common.logStatus(PASSED);

				common.setTestDetails(LoginPageIndex.row_num, 5, "Pass");

			}

			driver.manage().deleteAllCookies();
			driver.quit();

		} catch (Throwable throwable) {
			System.out.println("message from tear down"
					+ throwable.getMessage());

			throwable.getStackTrace();

			common.logStatus(FAILED);

		}

		/*
		 * ZipFiles zipresult = new ZipFiles(); zipresult.zipResult();
		 */
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void logMessage(String msg) {
		Reporter.log(msg + "<br/>");
	}

	public static void log(String msg, final int logger_status) {

		switch (logger_status) {

		case ILoggerStatus.NORMAL:
			Reporter.log("<br>" + msg + "</br>");
			break;

		case ILoggerStatus.ITALIC:
			log("<i>" + msg + "</i>");
			break;

		case ILoggerStatus.STRONG:
			Reporter.log("<strong>" + msg + "</br>");
			break;
		}
	}

	public static void logStatus(final int test_status) {

		switch (test_status) {

		case ITestStatus.PASSED:
			log("<font color=238E23>--Passed</font>");
			break;

		case ITestStatus.FAILED:
			log("<font color=#FF0000>--Failed</font>");
			break;

		case ITestStatus.SKIPPED:
			log("<font color=#FFFF00>--Skipped</font>");
			break;

		default:
			break;
		}

	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {
		System.out
				.println("-----------------------------------------------typed in log"
						+ msg);
		Reporter.log(msg);
	}

}
