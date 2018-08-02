package page.weclass;

import framework.Driver;
import framework.ResourceUtil;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class WCPageUtil {
    public static boolean isLogined(){
        boolean ret = true;
        MobileElement elem = Driver.findElementWithoutException(By.xpath(ResourceUtil.getRes("WC_MAIN_PAGE_MYCLASS_TXT_XPATH")));

        if(elem == null){
            ret = false;
        }

        return ret;
    }

}
