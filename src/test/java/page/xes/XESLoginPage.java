package page.xes;

import framework.*;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;

public class XESLoginPage extends BasePage {

    private MobileElement phoneText;
    private MobileElement passwordText;
    private MobileElement loginBtn;
    private final String MY = "我的";

    public static XESLoginPage verify(){
        return new XESLoginPage();
    }

    protected XESLoginPage(){
        Driver.sleep(5);
        Driver.findElementByText(MY).click();
        Driver.findElementById(ResourceUtil.getRes("TAB_MY_PAGE_LOGIN_BUTTON_ID"))
                .click();

        //通过检查页面上的指定元素来确认页面是否正常
        phoneText = Driver.findElementById(ResourceUtil.getRes("LOGIN_PAGE_PHONE_TEXT_ID"));
        passwordText = Driver.findElementById(ResourceUtil.getRes("LOGIN_PAGE_PASSWORD_TEXT_ID"));
    }

    public XESLoginPage inputPhoneNo(String phoneNo){
        Log.info(Log.getMethodName() + " " +  phoneNo);

        if(Driver.isAndroid()){
            Driver.setText(phoneText,phoneNo);
        }else{
            phoneText.click();

            //获取删除button位置
            MobileElement elem = Driver.findElementById("type == 'XCUIElementTypeButton' AND name == '9'");

            Dimension dim = elem.getSize();
            int width = dim.getHeight();
            int y = elem.getCenter().getY() + width;
            int x = elem.getCenter().getX() ;

            //删除手机号 重新输入
            for(int i = 0 ; i<=10 ; i++){
                Driver.clickByCoordinate(x,y);
            }

            //输入手机号
            char [] phoneNoAry = phoneNo.toCharArray();

            for(char c : phoneNoAry){
                Driver.findElementById("type == 'XCUIElementTypeButton' AND name == '" + c + "'"  )
                        .click();
            }
        }

        return  this;
    }


    public XESLoginPage inputPassword(String password){
        Log.info(Log.getMethodName() + " " + password);
        Driver.setText(passwordText,password);
        return  this;
    }

    public XESLoginPage clickLoginButton(){
        Log.info(Log.getMethodName());

        loginBtn = Driver.findElementById(ResourceUtil.getRes("LOGIN_PAGE_LOGIN_BUTTON_ID"));
        loginBtn.click();
        return this;
    }
}