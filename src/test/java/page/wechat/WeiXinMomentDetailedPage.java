package page.wechat;

import framework.BasePage;
import framework.Driver;

public class WeiXinMomentDetailedPage extends BasePage {

    protected WeiXinMomentDetailedPage(){
        Driver.findElementByText(getRes("MOMENT_DETAILED_PAGE_PRAISE_TEXT"));
    }

    public static WeiXinMomentDetailedPage verify(){
        return new WeiXinMomentDetailedPage();
    }
}
