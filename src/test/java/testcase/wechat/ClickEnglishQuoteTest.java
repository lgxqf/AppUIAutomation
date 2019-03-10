package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.*;
import static framework.ResourceUtil.getRes;

public class ClickEnglishQuoteTest  extends BaseTest {
    @Test
    public class ShowMyMomentTest extends BaseTest {

        public void traverseEnglishQuote(){

            String name = "英语短句每日分享";

            WeiXinMainPage.verify()
                    .clickContactButton();

            WeiXinContactPage.verify()
                    .clickOfficialAccountButton();

            WeiXinOfficialAccountsPage.verify()
                    .clickSearchButton();

            WeiXinSearchPage.verify()
                    .clickSearchText()
                    .inputText(name)
                    .clickFirstSearchResult(getRes("SEARCH_PAGE_SEARCH_RESULT_CLASS"), name);

            WeiXinSubscriptionPage.verify()
                    .clickHistoryButton();

            WeiXinArticleListPage.verify();

            Driver.getPageSource();
            Driver.sleep(10);
        }
    }
}
