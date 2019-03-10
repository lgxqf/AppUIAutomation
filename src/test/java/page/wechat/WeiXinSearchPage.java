package page.wechat;

import framework.BasePage;
import framework.Driver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;


public class WeiXinSearchPage extends BasePage {

    private MobileElement searchTextBox;

    public static WeiXinSearchPage verify(){
        return new WeiXinSearchPage();
    }

    protected  WeiXinSearchPage(){
        searchTextBox = Driver.findElementByText(getRes("SEARCH_PAGE_SEARCH_TEXT_BOX_TEXT"));
    }

    public WeiXinSearchPage clickSearchText(){
        searchTextBox.click();
        return this;
    }

    public WeiXinSearchPage inputText(String str){
        Driver.sendKeys(searchTextBox, str);
        return this;
    }

    public void clickFirstSearchResult(String cls, String text){
        Driver.findElementByClassAndText(cls, text)
                .click();
    }
}
