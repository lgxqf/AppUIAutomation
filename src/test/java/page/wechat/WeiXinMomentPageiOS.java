package page.wechat;

import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinMomentPageiOS extends WeiXinMomentPage {

    public WeiXinMomentPageiOS clickMyMoment(){
        MobileElement elem = Driver.findElementById(getRes("MY_POST_PAGE_MOMENT_PIC_ID"));

        int x = elem.getCenter().getX();

        x = x + 50;
        int y = elem.getCenter().getY();


        Driver.clickByCoordinate(x,y);
        return this;
    }
}
