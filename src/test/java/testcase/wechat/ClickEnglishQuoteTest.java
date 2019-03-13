package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.*;

import java.util.Random;

import static framework.ResourceUtil.getRes;

@Test
public class ClickEnglishQuoteTest  extends BaseTest {

        //运行前需先手动关注公从号 "英语短句每日分享"
        public void traverseEnglishQuote(){

            enterArticleList();

            int x = Driver.getDeviceWidth()/2;
            int y = Driver.getDeviceHeight() - 100;

            int adClickedCount = 0;
            int articleFailureCount = 0;
            int adFailureCount = 0;

            int distance = 75;
            int step = 80;

            Random rnd = new Random();

            while(adClickedCount + adFailureCount < 5){
                log.info("+++===+++=== AD clicked: " + adClickedCount + "    Article Failure clicked:" +articleFailureCount + "  AD Failure clicked:" + adFailureCount);

                try {
                    WeiXinArticleListPage.verify();

                }catch (Exception e){
                    Driver.appRelaunch();
                    enterArticleList();
                }

                //Scroll to show old articles to be clicked
                int loop = rnd.nextInt(10);

                log.info("Scroll count is " + loop);

                for(int i = 0; i< loop ; i++){
                    Driver.scrollUp(x, y, 500);
                }
                //Driver.scrollUp(x, y, 200);
                Driver.sleep(5);

                log.info("Trying to click Article");
                Driver.clickByCoordinate(x, y - distance);

                //Check if article is clicked
                try {
                    WeiXinArticlePage.verify();
                }catch (Exception e ){
                    //If fail to click article,try to click other article
                    Driver.clickByCoordinate(x, y - distance - 200);
                }

                //Again check if article is clicked
                try {
                    WeiXinArticlePage.verify()
                            .clickAD();
                    distance = 75;
                }catch (Exception e){
                    log.info("Fail to click Article!!!");
                    distance = distance + step;
                    articleFailureCount ++;
                    continue;
                }

                boolean containEnglishQuote = Driver.getPageSource().contains(getRes("ENGLISH_QUOTE_NAME_TEXT"));

                //Check if enter into AD Page
                if(containEnglishQuote){
                    log.info("====Fail to click AD");
                    //Driver.takeScreenShot();
                    Driver.pressBack();
                    adFailureCount ++;
                    continue;
                }

                //Enter into AD Page, do some scroll
                Driver.sleep(new Random().nextInt(3) + 3);
                Driver.takeScreenShot();
                Driver.scrollUp(x, y, y);

                log.info("====Succeed in clicking AD. Press back twice");
                Driver.pressBack();
                Driver.pressBack();
                adClickedCount ++;
            }

            log.info("+++===+++=== AD clicked: " + adClickedCount + "    Article Failure clicked:" +articleFailureCount + "  AD Failure clicked:" + adFailureCount);
        }

    protected void enterArticleList(){
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

    }
}
