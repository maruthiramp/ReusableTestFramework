/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.testutils;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;


import java.net.URL;

import com.techaspect.framework.setup.TestSetUp;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	private DriverFactory(){
	}

	/**
	 * This method is to create a driver instance for what is configured in configuration file.
	 * @param browserName
	 * @throws MalformedURLException 
	 */
	public static WebDriver createDriverInstance(String browserName) throws MalformedURLException
	{
		WebDriver driver =null;
		if(TestSetUp.configProperty.getProperty(Constants.EXECUTION_ENV).contains("Windows")){
		if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			DriverManager.setDriver(driver);
			DriverManager.maximizeBrowser(driver);
			DriverManager.setImplicitWait(driver);
		}
		else if(browserName.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			DriverManager.setDriver(driver);
			DriverManager.maximizeBrowser(driver);
			DriverManager.setImplicitWait(driver);
		}
		else if(browserName.equalsIgnoreCase("ie"))
		{
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
			DriverManager.setDriver(driver);
			DriverManager.maximizeBrowser(driver);
			DriverManager.pageLoadTimeout(driver);
			DriverManager.setImplicitWait(driver);
		}
		
	}
		else if(TestSetUp.configProperty.getProperty(Constants.EXECUTION_ENV).contains("Saucelabs"))
		{
			DesiredCapabilities caps = null;
			
			String sauceEnv = TestSetUp.configProperty.getProperty(Constants.BROWSER).toUpperCase();
			switch (sauceEnv) {
			case Constants.CHROME:
				caps = DesiredCapabilities.chrome();
				caps.setCapability(Constants.PLATFORM, Constants.CHROME_PLATFORM);
			    caps.setCapability(Constants.VERSION, Constants.CHROME_VERSION);
			    caps.setCapability("name", Constants.CHROME_PLATFORM + "-"+TestSetUp.configProperty.getProperty(Constants.BROWSER)+"-"+Constants.CHROME_VERSION);
			    caps.setCapability("tags", Constants.SAUCE_TAGS);
				break;
			case Constants.FIREFOX:
				caps = DesiredCapabilities.firefox();
				caps.setCapability(Constants.PLATFORM, Constants.FIREFOX_PLATFORM);
				caps.setCapability(Constants.VERSION, Constants.FIREFOX_VERSION);
			    caps.setCapability("name", Constants.FIREFOX_PLATFORM + "-"+TestSetUp.configProperty.getProperty(Constants.BROWSER)+"-"+Constants.FIREFOX_VERSION);
				caps.setCapability("tags", Constants.SAUCE_TAGS);
				break;
			case Constants.IE:
				caps = DesiredCapabilities.edge();
				caps.setCapability(Constants.PLATFORM, Constants.IE_PLATFORM);
				caps.setCapability(Constants.VERSION, Constants.SAUCE_IE_VERSION);
			    caps.setCapability("name", Constants.IE_PLATFORM + "-"+TestSetUp.configProperty.getProperty(Constants.BROWSER)+"-"+Constants.SAUCE_IE_VERSION);
				caps.setCapability("tags", Constants.SAUCE_TAGS);
				break;
			default:
				break;
			}
			driver = new RemoteWebDriver(new URL(Constants.SAUCE_URL), caps);
			DriverManager.maximizeBrowser(driver);
			DriverManager.pageLoadTimeout(driver);
			DriverManager.setImplicitWait(driver);
		}
		else if(TestSetUp.configProperty.getProperty(Constants.EXECUTION_ENV).contains("Browserstack"))
		{
			DesiredCapabilities caps = null;
			
			String browserStackEnv = TestSetUp.configProperty.getProperty(Constants.BROWSER).toUpperCase();
			switch (browserStackEnv) {
			case Constants.CHROME:
				caps = DesiredCapabilities.chrome();
				caps.setCapability(Constants.BROWSERSTACK_NETWORK_LOGS, true);
				caps.setCapability(Constants.BROWSERSTACK_DEBUG, true);
				caps.setCapability("os", Constants.BROWSERSTACK_OS);
			    caps.setCapability(Constants.OS_VERSION, Constants.BROWSERSTACK_OS_VERSION);
			    caps.setCapability(Constants.VERSION, Constants.CHROME_VERSION);
			    caps.setCapability(Constants.BROWSERSTACK_LOCAL, Constants.BROWSERSTACK_LOCAL_VALUE_FALSE);
				break;
			case Constants.FIREFOX:
				caps = DesiredCapabilities.firefox();
				caps.setCapability(Constants.BROWSERSTACK_NETWORK_LOGS, true);
				caps.setCapability(Constants.BROWSERSTACK_DEBUG, true);
				caps.setCapability("os", Constants.BROWSERSTACK_OS);
			    caps.setCapability(Constants.OS_VERSION, Constants.BROWSERSTACK_OS_VERSION);
			    caps.setCapability(Constants.VERSION, Constants.FIREFOX_VERSION);
			    caps.setCapability(Constants.BROWSERSTACK_LOCAL, Constants.BROWSERSTACK_LOCAL_VALUE_FALSE);
				break;
			case Constants.IE:
				caps = DesiredCapabilities.edge();
				caps.setCapability("os", Constants.BROWSERSTACK_OS);
				caps.setCapability(Constants.BROWSERSTACK_NETWORK_LOGS, true);
				caps.setCapability(Constants.BROWSERSTACK_DEBUG, true);
				caps.setCapability(Constants.OS_VERSION, Constants.BROWSERSTACK_OS_VERSION);
			    caps.setCapability(Constants.VERSION, Constants.BROWSERSTACK_IE_VERSION);
			    caps.setCapability(Constants.BROWSERSTACK_LOCAL, Constants.BROWSERSTACK_LOCAL_VALUE_FALSE);
				break;
			default:
				break;
			}
			driver = new RemoteWebDriver(new URL(Constants.BROWSERSTACK_URL), caps);
			DriverManager.setDriver(driver);
			DriverManager.maximizeBrowser(driver);
			DriverManager.pageLoadTimeout(driver);
			DriverManager.setImplicitWait(driver);
		}else if(TestSetUp.configProperty.getProperty(Constants.EXECUTION_ENV).contains("SeeTest"))
		{
			DesiredCapabilities caps = null;
			String seeTestUrl = "https://cloud.seetest.io/wd/hub";
			String seeEnv = TestSetUp.configProperty.getProperty(Constants.BROWSER).toUpperCase();
			switch (seeEnv) {
			case Constants.CHROME:
				caps = DesiredCapabilities.chrome();
			    caps.setCapability(Constants.PLATFORM, Constants.SEETEST_CHROME_PLATFORM);
			    caps.setCapability(Constants.VERSION, Constants.SEETEST_CHROME_VERSION);
			    caps.setCapability("accessKey", Constants.SEETEST_ACCESS_KEY);
			    caps.setCapability("testName", Constants.SEETEST_CHROME_PLATFORM + "-"+TestSetUp.configProperty.getProperty(Constants.BROWSER)+"-"+Constants.SEETEST_CHROME_VERSION);
				break;
			case Constants.FIREFOX:
				caps = DesiredCapabilities.firefox();
				caps.setCapability(Constants.PLATFORM, Constants.SEETEST_FIREFOX_PLATFORM);
			    caps.setCapability(Constants.VERSION, Constants.SEETEST_FIREFOX_VERSION);
			    caps.setCapability("accessKey", Constants.SEETEST_ACCESS_KEY);
			    caps.setCapability("testName", Constants.SEETEST_FIREFOX_PLATFORM + "-"+TestSetUp.configProperty.getProperty(Constants.BROWSER)+"-"+Constants.SEETEST_FIREFOX_VERSION);
				break;
			case "IE":
				throw new SkipException("SeeTest Support for Edge browser is not available as of now..");
			
			default:
				break;
			}
			driver = new RemoteWebDriver(new URL(seeTestUrl), caps);
			DriverManager.setDriver(driver);
			DriverManager.maximizeBrowser(driver);
			DriverManager.pageLoadTimeout(driver);
			DriverManager.setImplicitWait(driver);
		}
		/**
		 * Additional execution environments can be added here.
		 */
		return DriverManager.getDriver();			
	}

	
	/**
	 * This method is to kill the driver.
	 */
	public static void destroyDriver()
	{
		if(DriverManager.getDriver()!=null)
		DriverManager.getDriver().quit();
	}
}
