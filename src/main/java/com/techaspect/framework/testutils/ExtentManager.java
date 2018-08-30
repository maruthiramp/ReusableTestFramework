/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.testutils;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	private ExtentManager(){
	}

	private static ExtentReports extent;
	private static String filePath = "./extentreport.html";


	public static ExtentReports getExtent() {
		if (extent != null) {
			return extent;
		} else {
			extent = new ExtentReports();
			extent.attachReporter(getHtmlReporter());
			extent.setSystemInfo("Host Name", "Maruthi Ram");
			extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
			return extent;
		}
	}

	public static ExtentHtmlReporter getHtmlReporter() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.setAppendExisting(false);
		htmlReporter.loadXMLConfig(System.getProperty(Constants.ROOT_DIR)+"\\src\\test\\resources\\extentConfig\\ReportsConfig.xml");
		return htmlReporter;
	}


}
