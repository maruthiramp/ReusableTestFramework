/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.testutils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	
	private DriverManager(){
	}

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/**
	 * This method is for getDriver.
	 */
	public static WebDriver getDriver() {
		return DriverManager.driver.get();
	}
	
	/**
	 * This method is for setDriver.
	 * @param driver
	 */
	public static void setDriver(WebDriver driver) {
		DriverManager.driver.set(driver);
	}


	/**
	 * This method is for maximizing the browser.
	 * @param driver
	 */
	public static void maximizeBrowser(WebDriver driver) {
		driver.manage().window().maximize();
	}

	/**
	 * This method is for setting implicit wait.
	 * @param driver
	 */
	public static void setImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}
	public static void pageLoadTimeout(WebDriver driver) {
	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	}
}
