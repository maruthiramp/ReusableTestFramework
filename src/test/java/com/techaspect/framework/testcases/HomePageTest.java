/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.techaspect.framework.pageobjects.BasePage;
import com.techaspect.framework.pageobjects.HomePage;
import com.techaspect.framework.setup.TestSetUp;
import com.techaspect.framework.testutils.Constants;
import com.techaspect.framework.testutils.TestUtils;

public class HomePageTest extends TestSetUp {

	@Test(dataProviderClass=TestUtils.class, dataProvider="dpone")
	public void testSocialShareLinks(Hashtable<String, String> data){
		testCaseLogger.get().assignAuthor(Constants.AUTHOR1);
		testCaseLogger.get().assignCategory(Constants.SMOKE_CATEGORY);
		testCaseLogger.get().assignCategory(Constants.REGRESSION_CATEGORY);
		if(!data.get("Runmode").equalsIgnoreCase("Y")){
			appLogs.debug("RunMode is set to NO for testdata");
			throw new SkipException("RunMode is set to No for test data");
		}
		navigateToBaseURL();
		HomePage homePage = new HomePage().open();
		String actualTitle = homePage.getSocialShareTitle(data.get("socialmediatype"));
		homePage.switchBack();
		Assert.assertEquals(actualTitle, data.get("expected title"));
	}
	
	
	@Test(dataProviderClass=TestUtils.class, dataProvider="dpone")
	public void testLogo(Hashtable<String, String> data){
		testCaseLogger.get().assignAuthor(Constants.AUTHOR1);
		testCaseLogger.get().assignCategory(Constants.REGRESSION_CATEGORY);
		if(!data.get("Runmode").equalsIgnoreCase("Y")){
			appLogs.debug("RunMode is set to NO for testdata");
			throw new SkipException("RunMode is set to No for test data");
		}
		navigateToBaseURL();
		HomePage homePage = new HomePage().open();
		String actualUrl = homePage.getLogoUrl();
		System.out.println("actualUrl--> " + actualUrl);
		Assert.assertTrue(actualUrl.equalsIgnoreCase(data.get("expected url")));	
		
	}
	
}
