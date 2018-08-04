package page.wechat;

import framework.BasePage;
import framework.Driver;
import framework.Util;
import io.appium.java_client.MobileElement;

public class WeiXinMomentPage extends BasePage {

    public static WeiXinMomentPage verify(){

        if( !Util.isAndroid() ) {
            return new WeiXinMomentPageiOS();
        }

        return new WeiXinMomentPage();
    }

    protected WeiXinMomentPage(){
        Driver.findElementByText(getRes("MOMENT_PAGE_ME_TEXT"));
    }

    public WeiXinMomentPage clickMyMoment(){
        MobileElement elem = Driver.findElemByIdWithoutException (getRes("MY_POST_PAGE_MOMENT_PIC_ID"));

        if(elem == null){
            elem = Driver.findElementById(getRes("MY_POST_PAGE_MOMENT_ARTICLE_ID"));
        }

        elem.click();

        return this;
    }

    public WeiXinMomentPage scroll(){
        Driver.scrollUp();

        return this;
    }
}
