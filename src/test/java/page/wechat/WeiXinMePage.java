package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinMePage extends BasePage {

    MobileElement mypost;

    public static WeiXinMePage verify(){
        return new WeiXinMePage();
    }

    protected WeiXinMePage(){
            mypost = Driver.findElementByText(getRes("ME_PAGE_MY_POST_TEXT"));
    }

    public WeiXinMePage clickMoment(){
        mypost.click();
        return this;
    }
}
