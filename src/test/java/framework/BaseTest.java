package framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

public class BaseTest {
    private final String configFile = System.getProperty("user.dir") +  File.separator + "config/Config.yml";
    public static Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected void subTearDown(){
    }

    @BeforeMethod
    @Parameters({"port","udid","wdaPort"})
    //public void setUp(@Optional("d6744554")String port,@Optional ("4723")String udid,Method method) throws Exception {
    public void setUp(@Optional("") String port, @Optional("") String udid, @Optional("") String wdaPort,Method method) throws Exception {
        ConfigUtil.initialize(configFile,udid,port,wdaPort);

        if(Driver.isAndroid()){
            Driver.driver = Driver.prepareForAppiumAndroid(ConfigUtil.getPackageName(),ConfigUtil.getActivityName(),ConfigUtil.getUdid(),ConfigUtil.getPort());
        }else{
            Driver.driver = Driver.prepareForAppiumIOS(ConfigUtil.getBundleId(),udid,port,wdaPort);
        }

        BasePage.setDriver(Driver.driver);

        new Thread(new ScreenshotRunnable()).start();
    }

    @AfterMethod
    public void tearDown(Method method) throws Exception {
        subTearDown();

        ScreenshotRunnable.stop();

        Driver.driver.quit();
        Driver.driver = null;
        log.info("Completed Test : " + method.getName() + "    ");
    }

}
