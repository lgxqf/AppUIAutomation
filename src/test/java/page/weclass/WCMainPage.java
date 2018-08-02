package page.weclass;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

public class WCMainPage extends BasePage {
    private MobileElement txtMyClass;

    public static WCMainPage verify(){
        return new WCMainPage();
    }

    protected WCMainPage(){
        txtMyClass = Driver.findElementByXpath(getRes("WC_MAIN_PAGE_MYCLASS_TXT_XPATH"));
    }

    public WCMainPage clickMyClass(){
        txtMyClass.click();
        return this;
    }

    public WCMainPage enterClassRoom(){
        Driver.findElementByXpath(getRes("WC_MAIN_PAGE_ENTER_CLASS_BTN_XPATH")).click();
        return this;
    }
}
