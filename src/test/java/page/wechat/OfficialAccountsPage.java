package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class OfficialAccountsPage extends BasePage {
    private MobileElement addBtn;

    public static OfficialAccountsPage verify(){
        return new OfficialAccountsPage();
    }

    protected  OfficialAccountsPage(){
        Driver.findElementById(getRes("OFFICIAL_ACCOUNTS_TEXT"));
        addBtn = Driver.findElementById(getRes("OFFICIAL_ACCOUNTS_PAGE_ADD_BUTTON_ID"));
    }

    public void clickAddButton(){
        addBtn.click();
    }
}
