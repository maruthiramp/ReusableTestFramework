/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.setup;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.apache.log4j.Logger;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.techaspect.framework.testutils.Constants;
import com.techaspect.framework.testutils.DriverFactory;
import com.techaspect.framework.testutils.DriverManager;
import com.techaspect.framework.testutils.ExcelReader;
import com.techaspect.framework.testutils.ExtentManager;

public class TestSetUp {

	public static Properties configProperty;
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
	public static ThreadLocal<ExtentTest> testCaseLogger = new ThreadLocal<>();
	public static Logger appLogs=Logger.getLogger("devpinoyLogger");
	
	public static final ExcelReader excel = new ExcelReader(
			System.getProperty(Constants.ROOT_DIR) + "\\src\\test\\resources\\testData\\simple.xlsx");

	@BeforeSuite
	public synchronized void beforeSuite() throws IOException {
		FileInputStream fi=null;
		try {
			fi = new FileInputStream(new File(
					System.getProperty(Constants.ROOT_DIR) + "\\src\\test\\resources\\PropertyFiles\\config.properties"));

			configProperty = new Properties();
				configProperty.load(fi);
			
		} catch (FileNotFoundException e) {
			appLogs.debug("config.properties file is not found. "+e);
			
		}
		finally {
		    if (fi!=null) {
		        fi.close();
		        extent = ExtentManager.getExtent();

		    }
		}
		
	}

	@BeforeTest
	public void beforeTest() {
		/* Before Test code comes here. */
	}

	@BeforeClass
	public void beforeClass() {
		/* Extent Reporting */
		ExtentTest parent = extent.createTest(getClass().getSimpleName());
		parentTest.set(parent);
	}

	@BeforeMethod
	public synchronized void beforeMethod(Method method) throws MalformedURLException {
		appLogs.debug("Starting exection of test case: " + method.getName());
		WebDriver driver = null;
		
		if (driver == null) {
			DriverFactory.createDriverInstance(configProperty.getProperty("browser"));
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		extent.flush();
		DriverFactory.destroyDriver();
		}

	@AfterClass
	public void afterClass() {
		/* After Class code comes here. */
	}

	@AfterTest
	public void afterTest() {
		/* After Test code comes here. */
	}

	@AfterSuite
	public void afterSuite() {
		/* After Suite code comes here. */
	}
	
	public void assignAuthor(String authorName) {
		testCaseLogger.get().assignAuthor(authorName);
	}
	
	public void assignCategory(String category) {
		testCaseLogger.get().assignCategory(category);
	}
		
	public void navigateToBaseURL(){
		DriverManager.getDriver().navigate().to(configProperty.getProperty("url"));
		appLogs.debug("Navigating to BaseURL");
	 }
}
