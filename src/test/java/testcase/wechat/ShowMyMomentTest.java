package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.WeiXinMainPage;
import page.wechat.WeiXinMePage;
import page.wechat.WeiXinMomentPage;

@Test
public class ShowMyMomentTest extends BaseTest {

    public void showMyMoment(){

        WeiXinMainPage.verify()
                .clickMeButton();

        WeiXinMePage.verify()
                .clickMoment();

        WeiXinMomentPage.verify()
                .scroll()
                .clickMyMoment();

        Driver.sleep(10);
    }
}
