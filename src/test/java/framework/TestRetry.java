package framework;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


/**
 * Created by Ma Yi on 2017/5/5.
 */
public class TestRetry implements IRetryAnalyzer {
    private int retryCount = 0;
    private static long maxRetryCount ;

    @Override
    public boolean retry(ITestResult result) {
        maxRetryCount = ConfigUtil.getRetryCount();

        if (retryCount < maxRetryCount) {
            String message = "########################################################################Running retry for  '" + result.getName() + "' on class " +
                    this.getClass().getName() + " Retrying " + retryCount + " times";

            Log.info(message);
            retryCount++;
            return true;
        }

        return false;
    }
}
