/**
 ******************************************************************************
 * 							  REUSABLE FRAMEWORK
 *  							CONFIDENTIAL
 *							
 * *****************************************************************************
 */

package com.techaspect.framework.customlisteners;

import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.techaspect.framework.setup.TestSetUp;

public class RetryListeners implements IRetryAnalyzer {

	private int count = 0;
	private int maxCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			Throwable ex = result.getThrowable();
			if (ex != null && ex.getClass() == UnreachableBrowserException.class) {
				TestSetUp.appLogs.debug("UnreachableBrowserException");
			}
			if (count < maxCount) {
				count++;
				result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
				String message = Thread.currentThread().getName() + " Error in "
						+ result.getName() + " with status "
						+ result.getStatus() + " Retry attempt # " + count;
				TestSetUp.appLogs.debug(message);
				return true;
			}

		}
		return false;
	}	
}