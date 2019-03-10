package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinContactPage extends BasePage {

    private MobileElement officialAccountBtn;

    public static WeiXinContactPage verify(){
        return new WeiXinContactPage();
    }

    protected  WeiXinContactPage(){
        officialAccountBtn = Driver.findElementById(getRes("OFFICIAL_ACCOUNTS_TEXT"));
    }

    public void clickOfficialAccountButton(){
        officialAccountBtn.click();
    }
}
