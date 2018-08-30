/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.customlisteners;

import java.io.IOException;
import java.util.Arrays;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.techaspect.framework.setup.TestSetUp;
import com.techaspect.framework.testutils.Constants;
import com.techaspect.framework.testutils.TestUtils;

public class CustomeListeners extends TestSetUp implements ITestListener, ISuiteListener {

	public void onFinish(ISuite arg0) {
		/* Code related to onFinish goes here*/
	}

	public void onStart(ISuite arg0) {
		/* Code related to onStart goes here*/
	}

	 public void onFinish(ITestContext context) {
			/* Code related to onFinish goes here*/
	 }

	public void onStart(ITestContext arg0) {
		/* Code related to onStart goes here*/
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		/* Code related to onTestFailedButWithinSuccessPercentage goes here*/
	}

	public void onTestFailure(ITestResult arg0) {
		
		try {
			TestUtils.captureScreenshot();
		} catch (IOException e) {
			testCaseLogger.get().info("Unable to capture screenshot");
			appLogs.debug("Unable to capture screenshot. " + e);
		}
		
		String exceptionMessage=Arrays.toString(arg0.getThrowable().getStackTrace());
		try {
			testCaseLogger.get().fail("<details>" + "<summary style='cursor: pointer;'>" + "<b>" + Constants.OR_START_FONT_TAG + "red>" + "Exception Occured: Click to see"
					+ Constants.OR_END_FONT_TAG + "</b >" + "</summary>" +exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
			
			testCaseLogger.get().fail("<b>" + Constants.OR_START_FONT_TAG + "red>" + "ScreenShot of failure" + Constants.OR_END_FONT_TAG + "</b><style>[data-featherlight]{cursor: pointer;}</style>",
					MediaEntityBuilder.createScreenCaptureFromPath(TestUtils.screenshotPath + TestUtils.screenshotName)
							.build());
		} catch (IOException e) {
			testCaseLogger.get().info("Unable to create Screen Capture");
			appLogs.debug("Unable to create Screen Capture. " + e);
		}
		String failureLog="This Test Case got Failed";
		Markup m = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
		testCaseLogger.get().log(Status.FAIL, m);
		
		System.setProperty("org.uncommons.reportng.escape-output","false");
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+System.getProperty(Constants.ROOT_DIR) + Constants.SCREENSHOTS_FOLDER +TestUtils.screenshotName+">Screenshot</a>");	
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+System.getProperty(Constants.ROOT_DIR) + Constants.SCREENSHOTS_FOLDER +TestUtils.screenshotName+"><img src="+System.getProperty(Constants.ROOT_DIR) + Constants.SCREENSHOTS_FOLDER +TestUtils.screenshotName+" height=200 width=200></img></a>");
	}

	public void onTestSkipped(ITestResult arg0) {
		String methodName=arg0.getMethod().getMethodName();
		String logText="<b>"+Constants.OR_START_FONT_TAG + "blue>" +"Test Case:- "+ methodName+ " Skipped"+Constants.OR_END_FONT_TAG+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testCaseLogger.get().skip(m);		
	
	
	}

	public void onTestStart(ITestResult arg0) {
		String methodName=arg0.getMethod().getMethodName();
		ExtentTest child = parentTest.get().createNode(methodName);
		testCaseLogger.set(child);
		testCaseLogger.get().info("<b>"+"Starting execution of Test Case:- "+methodName+ "</b>");
		
	}

	public void onTestSuccess(ITestResult arg0) {
		String methodName=arg0.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Passed"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testCaseLogger.get().pass(m);
	}
	}