package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinMainPage extends BasePage {

    public static WeiXinMainPage verify(){
        return new WeiXinMainPage();

    }

    protected  WeiXinMainPage(){
        Driver.findElementByText(getRes("MAIN_PAGE_WEIXIN_TEXT"));
        Driver.findElementByText(getRes("MAIN_PAGE_CONTACT_TEXT"));
        Driver.findElementByText(getRes("MAIN_PAGE_DISCOVER_TEXT"));
    }

    public WeiXinMainPage clickMeButton(){
        Driver.findElementByText(getRes("MAIN_PAGE_ME_TEXT"))
                .click();
        return this;
    }

    public WeiXinMainPage clickContactButton(){
        Driver.findElementByText(getRes("MAIN_PAGE_CONTACT_TEXT"))
                .click();
        return this;
    }
}
