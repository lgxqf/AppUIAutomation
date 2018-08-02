package page.weclass;

import framework.BasePage;
import framework.Driver;
import framework.Log;
import framework.ResourceUtil;
import io.appium.java_client.MobileElement;


public class WCLoginPage extends BasePage {

    private MobileElement phoneText;
    private MobileElement passwordText;
    private MobileElement loginBtn;

    public static WCLoginPage verify(){
        return new WCLoginPage();
    }

    protected WCLoginPage(){
        phoneText = Driver.findElementByXpath(getRes("WC_LOGIN_PAGE_PHONE_TEXT_XPATH"));
        passwordText = Driver.findElementByXpath(getRes("WC_LOGIN_PAGE_PASSWORD_TEXT_XPATH"));
        loginBtn = Driver.findElementByXpath(getRes("WC_LOGIN_PAGE_LOGIN_BUTTON_XPATH"));
    }

    public WCLoginPage inputPhoneNo(String phoneNo){
        Log.info(Log.getMethodName() + " " +  phoneNo);
        Driver.setText(phoneText,phoneNo);
        return  this;
    }


    public WCLoginPage inputPassword(String password){
        Log.info(Log.getMethodName() + " " + password);
        Driver.setText(passwordText,password);
        return  this;
    }

    public WCLoginPage clickLoginButton(){
        Log.info(Log.getMethodName());
        loginBtn.click();
        return this;
    }
}

