package page.wechat;

import framework.BasePage;
import framework.Driver;

public class WeiXinADPage extends BasePage {
    public static WeiXinADPage verify(){
        return new WeiXinADPage();
    }

    protected WeiXinADPage(){
        Driver.findElementByTextWithoutScroll(getRes("ARTICLE_PAGE_AD_TEXT"));
    }
}
