package page.weclass;

import framework.BasePage;
import framework.Driver;
import framework.Util;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WCClassPage extends BasePage {
    public static Logger log = LoggerFactory.getLogger(Driver.class);

    private MobileElement permissionBtn;

    public static WCClassPage verify(){
        return new WCClassPage();
    }

    protected WCClassPage(){
        permissionBtn = Driver.findElementWithoutException(By.xpath(getRes("WC_CLASS_PAGE_ALWAYS_ALLOW_BTN_XPATH")),5);

        if(permissionBtn != null){
            permissionBtn.click();
        }

        Driver.findElementById(getRes("WC_CLASS_PAGE_NOTICE_BAR_TXT_ID"));
    }

    public WCClassPage stay(int seconds){
        String text = Util.getCurrentTimeFormat();

        clickHandUp();
        clickMsgBtn();
        inputText(text);
        sendText();
        Driver.sleep(seconds);

        return this;
    }

    public WCClassPage exit(){
        Driver.pressBack();
        Driver.findElement(By.xpath(getRes("CONFIRM_XPATH"))).click();

        return this;
    }

    public WCClassPage clickHandUp(){
        MobileElement elem = Driver.findElementWithoutException(By.id(getRes("WC_CLASS_PAGE_HAND_UP_BTN_ID")));

        if(elem!=null){
            elem.click();
        }else{
            log.error("Hands up element is not found!");
        }
        return this;
    }

    public WCClassPage clickMsgBtn(){
        Driver.findElementById(getRes("WC_CLASS_PAGE_INPUT_BTN_ID")).click();
        return this;
    }

    public WCClassPage inputText(String str){
        Driver.findElementById(getRes("WC_CLASS_PAGE_MSG_TEXT_ID"))
                .setValue(str);
        return this;
    }

    public WCClassPage sendText(){
        Driver.findElementById(getRes("WC_CLASS_PAGE_MSG_SEND_BTN_ID"))
                .click();
        return this;
    }
}
