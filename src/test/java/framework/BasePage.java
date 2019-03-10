package framework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class BasePage {
    public static Logger log = LoggerFactory.getLogger(BasePage.class);

    protected static AppiumDriver<MobileElement> driver;

    public BasePage() {
        Log.info(this.getClass().getName());

        //设置隐式延时
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(ConfigUtil.getDefaultWaitSec())),
                this);
    }

    public static void setDriver(AppiumDriver appDriver){
        driver = appDriver;
    }

    public String getRes(String str){
        return ResourceUtil.getRes(str);
    }
}
