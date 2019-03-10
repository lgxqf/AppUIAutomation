package framework;


public class ScreenshotRunnable implements Runnable {
    private static boolean stop = false;

    public static void stop(){
        stop = true;
    }

    public void run(){
        int sleepTime = ConfigUtil.getScreenShotInterval();
        while (!stop){
            Driver.takeScreenShot(true);
            Driver.sleep(sleepTime);
        }
    }
}
