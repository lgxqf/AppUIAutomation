package testcase.weclass;

import framework.BaseTest;
import framework.ConfigUtil;
import framework.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import page.weclass.WCClassPage;
import page.weclass.WCLoginPage;
import page.weclass.WCMainPage;
import page.weclass.WCPageUtil;

@Test
public class WCPerformanceTest extends BaseTest {
    public static Logger log = LoggerFactory.getLogger(WCPerformanceTest.class);

    public void enterAndExitClass(){
        long stayTime = ConfigUtil.getLongValue("WC_PERFORMANCE_CLASSROOM_STAYTIME");

        log.info(Driver.getPageSource(1));

        if(!WCPageUtil.isLogined()) {
            WCLoginPage.verify()
                    .inputPhoneNo(ConfigUtil.getStringValue(ConfigUtil.WC_USER))
                    .inputPassword(ConfigUtil.getStringValue(ConfigUtil.WC_PASSWORD))
                    .clickLoginButton();
        }

        long runningTime = ConfigUtil.getLongValue("WC_PERFORMANCE_TIME");
        //检查运行时间
        long testStartTime = System.currentTimeMillis();

        boolean stop = false;

        while(!stop) {
            long testEndTime = System.currentTimeMillis();

            if((testEndTime - testStartTime) > ( runningTime * 60 * 1000)) {
                log.info("已运行" + (testEndTime - testStartTime)/60/1000 + "分钟，任务即将结束");
                stop = true;
            }

            WCMainPage.verify()
                    .clickMyClass()
                    .enterClassRoom();

            WCClassPage.verify()
                    .stay((int)stayTime)
                    .exit();

            Driver.sleep(2);
        }
    }
}
