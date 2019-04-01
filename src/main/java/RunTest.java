import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RunTest {
    public static Logger log = LoggerFactory.getLogger(RunTest.class);

    public static boolean isWin(){
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String exeCmd(List<String> cmdList){
        log.info("System name is :" + System.getProperty("os.name"));

        if(isWin()){
            //TODO:Check this on windows
            cmdList.add(0,"cmd");
            cmdList.add(1,"/c");
        }else{
            cmdList.add(0,"sh");
            cmdList.add(1,"-c");
        }

        String []commandStr = cmdList.toArray(new String[0]);

        return exeCmd(commandStr,true);
    }

    public static String exeCmd(String []commandStr, boolean show) {
        log.info("Method exeCmd : "  + Arrays.asList(commandStr));

        BufferedReader br = null;
        String res = "";

        try {
            Process p ;
            if(commandStr.length ==1 ){
                p = Runtime.getRuntime().exec(commandStr[0]);
            }else{
                p = Runtime.getRuntime().exec(commandStr);
            }

            //br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName(“GBK”)));
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = br.readLine()) != null) {
                output.append(line + "\n");
            }

            res = output.toString().trim();

            if(show) {
                log.info(Arrays.asList(commandStr) + "  ---output is : " + res);
            }

            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            log.info(commandStr + "fail to execute : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    public static String getDatetime(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return date.format(new Date());
    }

    public static String startLogRecord(String udid){
        String logName = udid + "-" + getDatetime() + ".log";

        Runnable newRunnable = () -> {
            ArrayList<String> cmd = new ArrayList<>();

            if(udid.length()< 20){
                cmd.add("adb -s " + udid + " logcat > " + logName);
            }else{
                cmd.add("idevicesyslog -u " + udid + " > " + logName);
            }

            exeCmd(cmd);
        };

        Thread thread = new Thread(newRunnable);
        thread.setDaemon(true);
        thread.start();
        return logName;
    }

    public static String getUdid(String fileName){
        String udid = null;

        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String s = "";

            while((s= br.readLine())!=null && !s.contains("udid")){
            }
            br.close();

            int index = s.lastIndexOf("=") + 3;
            int indexEnd = s.lastIndexOf("\"");
            udid = s.substring(index,indexEnd);
            //log.info(udid);
        }catch(Exception e){
            log.error("Fail to get device udid from test suite xml");
            e.printStackTrace();
        }

        return udid;
    }

    // java -jar target/UIAutomation-1.0-fat-tests.jar  task/demo-android.xml
    public static void main(String args[]){

        TestNG testng = new TestNG();
        String xmlFile = args[0];
        log.info("Test suite file " + xmlFile);

        String udid = getUdid(xmlFile);
        log.info("Device udid " + udid);

        List<String> suites = Lists.newArrayList();
        suites.add(xmlFile);
        testng.setTestSuites(suites);
        //startLogRecord(udid);
        testng.run();
    }
}
