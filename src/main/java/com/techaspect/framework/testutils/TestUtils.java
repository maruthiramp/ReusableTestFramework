/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.testutils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.techaspect.framework.setup.TestSetUp;

public class TestUtils extends TestSetUp {

	public static String screenshotPath;
	public static String screenshotName;
	public String testSheetName;
	
	/* Use this dataprovider when we have one excel sheet for one test case. */
	@DataProvider(name = "dp")
	public Object[][] getData(Method method) {
		/*
		 * 1. Sheet name same as the test case name 2. Returns the row count in
		 * the sheet 3. Returns the column count in the sheet
		 */
		String sheetName = method.getName();
		int rowCount = excel.getRowCount(sheetName);
		int columnCount = excel.getColumnCount(sheetName);

		/*
		 * We are using rowCount-1 as the first row in the excel will be header.
		 * We are using number of columns as 1 because we will be using
		 * Hashtable.
		 */
		Object[][] data = new Object[rowCount - 1][1];
		Hashtable<String, String> table = null;

		/* Creating one hashtable for each data row */
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			table = new Hashtable<>();
			for (int cellNum = 0; cellNum < columnCount; cellNum++) {
				table.put(excel.getCellData(sheetName, cellNum, 1), excel.getCellData(sheetName, cellNum, rowNum));
				appLogs.debug("Hashtable-->" + table);
				data[rowNum - 2][0] = table;
			}
		}

		appLogs.debug("Data-->" + data);
		return data;
	}

	/*
	 * Use this dataprovider when we have all test cases and test data in one
	 * excel sheet.
	 */
	@DataProvider(name = "dpone")
	public Object[][] getDatas(Method m) {

		/* Finding the sheet based on environment*/
		
		String env = configProperty.getProperty("server").toUpperCase();
		switch (env) {
		case "PROD":
			testSheetName = configProperty.getProperty("prodTestSheetName");
			break;
		case "STAGE":
			testSheetName = configProperty.getProperty("stageTestSheetName");
			break;
		case "UAT":
			testSheetName = configProperty.getProperty("uatTestSheetName");
			break;
		case "QA":
			testSheetName = configProperty.getProperty("qaTestSheetName");
			break;
		default:
			break;
		}
		
		/* Number of rows in the excel sheet */
		int rows = excel.getRowCount(testSheetName);
		appLogs.debug("Total rows are : " + rows);

		/* Name of test case */
		String testName = m.getName();

		/* Find the test case start row */
		int testCaseRowNum = 1;
		for (testCaseRowNum = 1; testCaseRowNum <= rows; testCaseRowNum++) {
			String testCaseName = excel.getCellData(testSheetName, 0, testCaseRowNum);
			if (testCaseName.equalsIgnoreCase(testName))
				break;
		}
		appLogs.debug("Test case starts from row num: " + testCaseRowNum);

		/* Checking total rows in test case */
		int dataStartRowNum = testCaseRowNum + 2;
		int testRows = 0;
		while (!excel.getCellData(testSheetName, 0, dataStartRowNum + testRows)
				.equals("")) {
			testRows++;
		}
		appLogs.debug("Total rows of data are : " + testRows);

		/* Checking total columns in test case */
		int colStartColNum = testCaseRowNum + 1;
		int testCols = 0;
		while (!excel.getCellData(testSheetName, testCols, colStartColNum).equals("")) {
			testCols++;
		}
		appLogs.debug("Total cols are : " + testCols);

		/* Returning data */
		Object[][] data = new Object[testRows][1];
		int i = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {
			Hashtable<String, String> table = new Hashtable<>();
			for (int cNum = 0; cNum < testCols; cNum++) {
				String colName = excel.getCellData(testSheetName, cNum, colStartColNum);
				String testData = excel.getCellData(testSheetName, cNum, rNum);
				table.put(colName, testData);
			}
			data[i][0] = table;
			i++;
		}
		return data;
	}

	/* Capture Screenshot */
	public static final void captureScreenshot() throws IOException {
		screenshotPath = "./Screenshots/";
		File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		/*This is used for Extent report */
		FileUtils.copyFile(scrFile, new File(screenshotPath + screenshotName));
	}

}