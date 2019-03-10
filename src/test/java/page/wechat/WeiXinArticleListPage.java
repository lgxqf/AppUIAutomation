package page.wechat;

import framework.BasePage;
import framework.Driver;

public class WeiXinArticleListPage extends BasePage {

    public static WeiXinArticleListPage verify(){
        return new WeiXinArticleListPage();

    }

    protected WeiXinArticleListPage(){
        Driver.findElementByText(getRes("ARTICLE_LIST_PAGE_HISTORY_ARTICLE_TEXT"));
        Driver.sleep(5);
        //Driver.findElementByText(getRes("ARTICLE_LIST_PAGE_SEARCH_ARTICLE_TEXT"));
    }
}
