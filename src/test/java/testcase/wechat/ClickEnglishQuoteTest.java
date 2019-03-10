package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.*;

public class ClickEnglishQuoteTest  extends BaseTest {
    @Test
    public class ShowMyMomentTest extends BaseTest {
        //打开我的朋友圈
        public void traverseEnglishQuote(){
            WeiXinMainPage.verify()
                    .clickContactButton();

            WeiXinContactPage.verify()
                    .clickOfficialAccountButton();

            OfficialAccountsPage.verify()
                    .clickAddButton();

            WeiXinSearchPage.verify()
                    .clickSearchText()
                    .inputText("英语短句每日分享");

            Driver.sleep(10);
        }
    }
}
