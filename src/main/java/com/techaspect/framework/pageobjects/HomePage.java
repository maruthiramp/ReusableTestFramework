/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.techaspect.framework.testutils.Constants;
import com.techaspect.framework.testutils.DriverManager;

public class HomePage extends BasePage{

		
	@Override
	public ExpectedCondition getPageLoadCondition() {
		//return ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.className(Constants.OR_FOOTER_DIV)));
		return ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.className(Constants.OR_FOOTER_DIV2)));
	}
	
	public String getHomePageTitle() {
		appLogs.debug("Executing getHomePageTitle");
		return DriverManager.getDriver().getTitle();
	}
	
	public HomePage open() {
		return (HomePage) openPage(HomePage.class);
	}
	public String getSocialShareTitle(String social){
		//WebElement footerDiv= DriverManager.getDriver().findElement(By.id(Constants.OR_FOOTER_DIV)); 
		WebElement footerDiv= DriverManager.getDriver().findElement(By.cssSelector(Constants.OR_FOOTER_DIV2)); 
		switch (social) {
		case "Facebook":
			//footerDiv.findElement(By.xpath(Constants.OR_ICON_FACEBOOK)).click();
			footerDiv.findElement(By.xpath(Constants.OR_ICON_FACEBOOK2)).click();
			appLogs.debug("Clicked on Facebook icon");
			switchHandle();
			socialPageloadWait(80);
			break;
		case "Twitter":
			//footerDiv.findElement(By.xpath(Constants.OR_ICON_TWITTER)).click();
			footerDiv.findElement(By.xpath(Constants.OR_ICON_TWITTER2)).click();
			appLogs.debug("Clicked on Twitter icon");
			switchHandle();
			socialPageloadWait(80);
			break;
		case "LinkedIn":
			footerDiv.findElement(By.xpath(Constants.OR_ICON_LINKEDIN)).click();
			appLogs.debug("Clicked on LinkedIn icon");
			switchHandle();
			socialPageloadWait(80);
			break;
		default:
			break;
		}
		return DriverManager.getDriver().getTitle();
	}
	
	public void switchBack(){
		switchBackFromHandle();
	}
	
	public void socialPageloadWait(long secs) {
		pageloadWait(secs);
	}
	
	public String getLogoUrl(){
		//click(findElement(By.xpath(Constants.OR_LOGO)), "LOGO");
		click(findElement(By.xpath(Constants.OR_LOGO2)), "LOGO");
		pageloadWait(20);
		return DriverManager.getDriver().getCurrentUrl();
	}
	
}
