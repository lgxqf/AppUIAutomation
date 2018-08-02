package framework;


import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class ResourceUtil {

    private static Map<String,String> map;

    static {
        String resFile = "AndroidRES.yml";

        if(!Driver.isAndroid()){
            resFile = "IOSRES.yml";
        }

        String configFileName = System.getProperty("user.dir") + File.separator + "config" + File.separator + resFile;

        try {
            InputStream input = new FileInputStream(new File(configFileName));
            map = new Yaml().load(input);
            Log.info("ResourceUtil file " + configFileName);
        } catch (Exception e) {
            Log.info("Fail to read resource config file");
        }
    }

    public static String getRes(String key){
        String value = map.get(key);

        Log.info("Config : " + key + " = " + value);
        return value;
    }
}