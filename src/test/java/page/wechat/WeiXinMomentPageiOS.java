package page.wechat;

import framework.Driver;
import io.appium.java_client.MobileElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class WeiXinMomentPageiOS extends WeiXinMomentPage {

    public static Logger log = LoggerFactory.getLogger(WeiXinMomentPageiOS.class);

    public WeiXinMomentPageiOS clickMyMoment(){
        Driver.getPageSource();

        //找到带"昨天"字的元素
        MobileElement elem = Driver.findElemByIdWithoutException(getRes("MY_POST_PAGE_MOMENT_YESTERDAY_ID"),5);

        if(elem == null){
            //如果没有昨天月字的元素，找"月"元素
            elem = Driver.findElemByIdWithoutException(getRes("MY_POST_PAGE_MOMENT_PIC_ID"));
        }

        Assert.assertNotEquals(elem , null);

        //计算要点击位置的坐标
        int x = elem.getCenter().getX() + 50;
        int y = elem.getCenter().getY();

        Driver.clickByCoordinate(x,y);
        return this;
    }
}
