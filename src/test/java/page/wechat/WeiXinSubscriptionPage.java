package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinSubscriptionPage extends BasePage {
    private MobileElement historyBtn;

    public static WeiXinSubscriptionPage verify(){
        return new WeiXinSubscriptionPage();
    }

    protected  WeiXinSubscriptionPage(){
        historyBtn = Driver.findElementByText(getRes("SUBSCRIPTION_PAGE_HISTORY_TEXT"));
    }

    public void clickHistoryButton(){
        historyBtn.click();
    }
}
