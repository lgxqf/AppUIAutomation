package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.WeiXinMainPage;
import page.wechat.WeiXinMePage;
import page.wechat.WeiXinMomentPage;

@Test
public class ShowMyMomentTest extends BaseTest {

    //打开我的朋友圈
    public void showMyMoment(){
        //打开微信主页面，点击"我"
        WeiXinMainPage.verify()
                .clickMeButton();

        //校验"我"页面，打开"朋友圈"
        WeiXinMePage.verify()
                .clickMoment();

        //校验"朋友圈页面"，下划一段距离，然后打开带图片的朋友圈
        WeiXinMomentPage.verify()
                .scroll()
                .clickMyMoment();

        Driver.sleep(10);
    }
}
