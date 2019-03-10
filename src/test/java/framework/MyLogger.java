package framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    public static Logger log = LoggerFactory.getLogger(MyLogger.class);

    public static void info(String str){
        if(null != str){
            log.info(str);
        }
    }

    public static void info(Object obj){
        log.info(String.valueOf(obj));
    }

    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        return "===== Method : " + methodName + "   ";
    }

}
