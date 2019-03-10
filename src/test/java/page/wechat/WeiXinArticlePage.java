package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;

import java.util.Random;

public class WeiXinArticlePage extends BasePage {

    private MobileElement adText;

    public static WeiXinArticlePage verify(){
        return new WeiXinArticlePage();
    }

    protected WeiXinArticlePage(){
        Driver.findElementByTextWithoutScroll(getRes("ARTICLE_PAGE_AD_TEXT"));
    }

    public void clickAD(){
        Driver.sleep(10);

        int x = Driver.getDeviceWidth() / 2;
        int y = Driver.getDeviceHeight() / 2;

        Random rnd = new Random();
        for(int i = 0 ;i < 2; i++) {
            Driver.scrollUp(x,y,y-10);
            Driver.sleep(2);
            //Driver.sleep(rnd.nextInt()30);
        }

        adText = Driver.findElementByText(getRes("ARTICLE_PAGE_AD_TEXT"));
        Driver.getPageSource();

        y = adText.getLocation().y + adText.getRect().getHeight()/2;
        Driver.clickByCoordinate(x,y);

        //Enter into AD Page, do some scroll
        for(int i = 0 ; i <2 ; i++) {
            Driver.sleep(5);
            Driver.scrollUp(x, y, y);
        }

    }
}
