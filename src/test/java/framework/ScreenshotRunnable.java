package framework;


public class ScreenshotRunnable implements Runnable {
    private static boolean stop = false;
    private final int sleepTime = 3;

    public static void stop(){
        stop = true;
    }

    public void run(){
        while (!stop){
            Driver.takeScreenShot();
            Driver.sleep(sleepTime);
        }
    }
}
