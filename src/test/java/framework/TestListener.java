package framework;

/**
 * Created by Ma Yi on 2017/5/5.
 */

import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class TestListener extends TestListenerAdapter implements IAnnotationTransformer{
    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        IRetryAnalyzer iRetryAnalyzer= iTestAnnotation.getRetryAnalyzer();

        if(iRetryAnalyzer==null){
            iTestAnnotation.setRetryAnalyzer(TestRetry.class);
        }
    }

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        Log.info("\n\n");
        Log.info("------------------------------------------------------------------------");
        Log.info(">>>>>>>>>>>>>>>>>>>>>>Running Test : " + tr.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        Driver.takeScreenShotWithExt("failure");
        Log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Log.info("++++++++++++++++++++++ Test Fail: " + tr.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        Log.info(">>>>>>>>>>>>>>>>>>>>>> Test Pass: " + tr.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);

        int failureCount = context.getFailedTests().size();

        if( 0 != failureCount){
            Log.info("\n\n"+failureCount + " test case failed:");

            for(ITestNGMethod method : context.getFailedTests().getAllMethods()){
                Log.info("     " + method.getMethodName());
            }
        }
    }
}

