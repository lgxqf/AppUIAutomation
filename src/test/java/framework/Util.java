package framework;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ma Yi on 2017/5/27.
 */
public final class Util {
    public static Logger log = LoggerFactory.getLogger(Util.class);

    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();

        return "===== Method : " + methodName + "   ";
    }

    public static String multiplyStringValue(String a, String b) {
        double quantity = Double.valueOf(a);
        double price = Double.valueOf(b);

        return getStringDoubleFormat(quantity * price);
    }

    public static String getStringDoubleFormat(String str) {
        return getStringDoubleFormat(Double.valueOf(str));
    }

    public static String getStringDoubleFormat(Double str) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(str);
    }

    public static boolean isAndroid(String udid) {
        return udid.length() < 20;
    }

    public static boolean isAndroid() {
        String udid = ConfigUtil.getUdid();
        return udid.length() < 20;
    }

    public static String getCurrentTimeString(){
        long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间

        return sd;
    }

    public static String getDatetime(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return date.format(new Date());
    }

    public static String getTimeString(Date time){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return date.format(time);
    }

    public static String exeCmd(String []commandStr) {
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

    public static String exeCmd(String commandStr){
        return exeCmd(commandStr,true);
    }

    public static String exeCmd(String commandStr, boolean show) {
        String [] cmd = new String[1];
        cmd[0] = commandStr;

        return exeCmd(cmd,show);
    }

    public static String exeCmd(List<String> cmdList){
        log.info("System name is :" + System.getProperty("os.name"));

        if(Util.isWin()){
            //TODO:Check this on windows
            cmdList.add(0,"cmd");
            cmdList.add(1,"/c");
        }else{
            cmdList.add(0,"sh");
            cmdList.add(1,"-c");
        }

        String []commandStr = cmdList.toArray(new String[0]);

        return exeCmd(commandStr);
    }



    public static void renameFile(String fileName, String newName){
        File file = new File(fileName);
        file.renameTo(new File(newName));
        log.info("Crash screenshot: " + newName);
    }



    public static boolean createDir(String dir){
        File file = new File(dir);

        if(file.exists()){
            return false;
        }

        return file.mkdir();
    }


    public static void copyFile(File src, File dest){
        try {
            FileUtils.copyFile(src,dest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeFile(File file,String str){
        FileOutputStream fop = null;
        OutputStreamWriter osw ;

        String charsetName = "UTF-8";

        if(Util.isWin()){
            charsetName = "GBK";
        }

        try {
            fop = new FileOutputStream(file);
            osw = new OutputStreamWriter(fop, charsetName);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = str.getBytes(charsetName);

            osw.write(new String(contentInBytes));
//            fop.flush();
//            fop.close();
            osw.flush();
            osw.close();
            log.info("Write to file " + file.getName());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MyFilter implements FilenameFilter{
        private String type;
        public MyFilter(String type){
            this.type = type;
        }
        public boolean accept(File dir,String name){
            return name.endsWith(type);
        }
    }

    public static List<String> getFileList(String dir){
        return getFileList(dir,null,false);
    }

    public static List<String> getFileList(String dir,boolean fileNameWithPath){
        return getFileList(dir,null,fileNameWithPath);
    }

    public static List<String> getFileList(String dir,String filterName, boolean fileNameWithPath){
        List<String> list = new ArrayList<>();
        File file = new File(dir);
        FilenameFilter filter = null;

        if(filterName != null){
            filter = new MyFilter(filterName);
        }

        if(file.exists() && file.isDirectory()){
            String []name = file.list(filter);
            if(fileNameWithPath){
                for(String str : name){
                    list.add(dir +File.separator + str);
                }
            }else {
                list = Arrays.asList(name);
            }
        }

        Collections.sort(list);
        return list;
    }

    public static String timeDifference(long start, long end) {

        long between = end - start;
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        //long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        String timeDifference = hour + "小时" + min + "分" + s + "秒";// + ms + "毫秒";
        return timeDifference;
    }


    public static String getCurrentTimeFormat(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
        return format.format(date.getTime());//这个就是把时间戳经过处理得到期望格式的时间
    }

    /**
     * The form of nextInt used by IntStream Spliterators.
     * For the unbounded case: uses nextInt().
     * For the bounded case with representable range: uses nextInt(int bound)
     * For the bounded case with unrepresentable range: uses nextInt()
     *
     * @param origin the least value, unless greater than bound
     * @param bound the upper bound (exclusive), must not equal origin
     * @return a pseudorandom value
     */
    public static final int internalNextInt(int origin, int bound) {
        Random random = new Random();

        if (origin < bound) {
            int n = bound - origin;
            if (n > 0) {
                return random.nextInt(n) + origin;
            }
            else {  // range not representable as int
                int r;
                do {
                    r = random.nextInt();
                } while (r < origin || r > bound);
                return r;
            }
        }
        else {
            //return random.nextInt();
            log.info("!!!!origin>=bound");
            return bound - origin;
        }
    }

    public static boolean isWin(){
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String getGrep(){
        String grep = "grep";

        if(isWin()){
            grep = "findstr";
        }

        return grep;
    }
}

