package framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Ma Yi on 2017/4/27.
 */
public class ConfigUtil {
    public static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
    private static String udid;
    private static String port;
    private static String wdaPort;
    private static Map<String,Object> configItems;
    private static Map<String, Object> ymlMap;
    private static String rootDir;
    private static String screenshotDir;

    public static final String IOS_BUNDLE_ID = "IOS_BUNDLE_ID";
    public static final String ADDITION_CONIG = "ADDITION_CONIG";
    public static final String IOS_WDA_PORT = "IOS_WDA_PORT";
    public static final String MINI_PROGRAM_NAME = "MINI_PROGRAM_NAME";
    public static final String DEFAULT_DEVICE_ENABLE = "DEFAULT_DEVICE_ENABLE";
    public static final String UDID = "UDID";
    public static final String APPIUM_PORT = "APPIUM_PORT";
    private static ConfigUtil configUtil;
    private static int DEFAULT_WAIT_SEC = 0;
    private static int DEFAULT_POLLING_INTERVAL_SEC = 0;

    public static final String WC_USER = "WC_USER";
    public static final String WC_PASSWORD = "WC_PASSWORD";

    public static int getDefaultWaitSec(){
        return DEFAULT_WAIT_SEC;
    }

    public static int getDefaultPollingIntervalSec(){
        return DEFAULT_POLLING_INTERVAL_SEC;
    }

    public static String getScreenshotDir() {
        return screenshotDir;
    }


    public static ConfigUtil initialize(String file, String deviceUdid, String appiumPort,String wda){

        if(null != configUtil){
            return configUtil;
        }

        configUtil = new ConfigUtil();

        try {
            log.info("Reading config file " + file);
            port = appiumPort;

            InputStream input = new FileInputStream(new File(file));
            ymlMap = new Yaml().load(input);
            configItems = new HashMap<>();

            //初始化的顺序很重要
            List<String> keyList = new ArrayList(Arrays.asList("GENERAL","DEFAULT_DEVICE","RUN_TEST_CONIFG"));

            addConfigItem(keyList);

            boolean isDefaultDeviceEnabled = getBooleanValue(DEFAULT_DEVICE_ENABLE);

            if(isDefaultDeviceEnabled){
                deviceUdid = getStringValue(UDID);
                appiumPort = getStringValue(APPIUM_PORT);
                wda = getStringValue(IOS_WDA_PORT);
                log.info("Default device " + deviceUdid + " is enabled.");
            }

            setUdid(deviceUdid);
            port = appiumPort;
            wdaPort = wda;

            //Initialize data such as device name etc by its uuid
            keyList = new ArrayList(Arrays.asList(udid));
            List<String> addiontalList = getListValue(ADDITION_CONIG);

            if(addiontalList != null){
                for(String key : addiontalList){
                    keyList.add(key);
                    log.info("Additional item " + key + "is added");
                }
            }

            addConfigItem(keyList);

            DEFAULT_WAIT_SEC = (int)getLongValue("DEFAULT_WAIT_SEC");
            DEFAULT_POLLING_INTERVAL_SEC = (int)getLongValue("DEFAULT_POLLING_INTERVAL_SEC");

            //Create Root dir
            rootDir = System.getProperty("user.dir") + File.separator + udid + "-" + Util.getCurrentTimeFormat();
            Util.createDir(rootDir);

            screenshotDir = rootDir + File.separator + "screenshot" + File.separator;
            Util.createDir(screenshotDir);

            log.info("rootDir is " + rootDir);

        }catch (Exception e){
            log.error("!!!!!!Fail to read config file");
            e.printStackTrace();
        }

        return configUtil;
    }

    protected static void addConfigItem(List<String> keyList){
        for(String key : keyList){
            Map<String,Object> tempMap = (Map<String,Object>)ymlMap.get(key);

            for(String itemKey : tempMap.keySet()){
                configItems.put(itemKey,tempMap.get(itemKey));
            }
        }
    }

    public static void setUdid(String uuid){
        udid = uuid;
    }

    public static String getWdaPort() {
        return wdaPort;
    }

    public static String getPort() {
        return port;
    }

    public static String getRootDir(){
        return rootDir;
    }


    public static String getActivityName() {
        return getStringValue("ANDROID_MAIN_ACTIVITY");
    }

    public static String getBundleId() {
        return getStringValue(IOS_BUNDLE_ID);
    }

    public static String getServerIP(){
        return getStringValue("APPIUM_SERVER_IP");
    }

    public static long getRetryCount() {
        return getLongValue("RETRY_COUNT");
    }

    public static String getPackageName() {
        return getStringValue("ANDROID_PACKAGE");
    }


    public static String getUdid(){
        return udid;
    }

    public static String getDeviceName(){
        String deviceName = getStringValue("DEVICE_NAME");

        return deviceName == null? udid:deviceName;
    }

    public static String getStringValue(String key) {
        String value = String.valueOf(configItems.get(key));
        log.info("Config : " + key + " = " + value);

        return value == null || value.equals("null")? null:value.trim();
    }
    public static void setStringValue(String key,String value){
        configItems.put(key,value);
    }

    public static ArrayList<String> getListValue(String key) {
        ArrayList<String> list =(ArrayList) configItems.get(key);
        log.info("Config : " + key + " = " + list);

        return list == null? new ArrayList<>():list;
    }

    public static void setLongValue(String key, String val){
        configItems.put(key,Integer.parseInt(val));
    }

    public static long getLongValue(String key) {
        Integer value =(Integer) configItems.get(key);
        log.info("Config : " + key + " = " + value);

        return value == null? -100:value.longValue();
    }

    public static void setBooleanValue(String key, boolean val){
        configItems.put(key,val);
    }

    public static boolean getBooleanValue(String key, boolean defaultValue) {
        Boolean value = (Boolean) configItems.get(key);

        log.info("Config : " + key + " = " + value);

        return value == null? defaultValue : value;
    }

    public static boolean getBooleanValue(String key) {
        return getBooleanValue(key,false);
    }

    public static Map<String,Object> getMapValue(String key){
        Map<String,Object> map = (Map)configItems.get(key);
        log.info("Config : " + key + " = " + map);
        return map;
    }
}
