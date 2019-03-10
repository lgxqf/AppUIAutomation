package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WeiXinOfficialAccountsPage extends BasePage {
    private MobileElement searchBtn;

    public static WeiXinOfficialAccountsPage verify(){
        return new WeiXinOfficialAccountsPage();
    }

    protected WeiXinOfficialAccountsPage(){
        Driver.findElementByText(getRes("OFFICIAL_ACCOUNTS_TEXT"));
        searchBtn = Driver.findElementById(getRes("OFFICIAL_ACCOUNTS_PAGE_SEARCH_BUTTON_ID"));
    }

    public void clickSearchButton(){
        searchBtn.click();
    }
}
