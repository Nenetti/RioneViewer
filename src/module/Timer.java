package module;


import config.Config;



public class Timer {

    private static int mm;

    private static long start;
    private static long stop;

    public static void addTime (int t) {
        mm += t;
        if ( Timer.mm >= 1000 ) {
            if ( Config.LoadableTime > Config.Time ) {
                Config.addTime(1);
            }
            Timer.mm = 0;
        }
        Config.MilliTime = mm;
    }

    public static void start () {
        Timer.start = System.nanoTime();
    }

    public static void stop () {
        Timer.stop = System.nanoTime();
    }

    public static double getTime () {
        return (stop - start) / 1000000.0;
    }

    public static void duration (int mm) {
        try {
            Thread.sleep(mm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
