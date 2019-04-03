package config;


import module.SystemPanel;

import java.awt.*;



public class Config {

    public static int Width = 1280;
    public static int Height = 720;
    public static double Scale = 0.5;
    public static int MapX = 0;
    public static int MapY = 0;
    public static int Time = 1;
    public static int MilliTime = 0;
    public static int LoadableTime = 0;
    public static int FrameRate = 0;
    public static PlayMode MODE = PlayMode.Play;
    public static int MapStartX = 0;
    public static int MapStartY = 0;
    public static int MapEndX = 0;
    public static int MapEndY = 0;


    public static String APPNAME = "RioneViewer";
    public static String VERSION = "4.00";

    public static final int FPS = 60;
    public static final double MaxScale = 2.5;
    public static final double MinScale = 0.1;


    public static final int RESCALE_UNIT = 200;
    public static final double REROTATE_UNIT = 0.1;

    public static Font DEFAULT_FONT = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
    public static Font TIME_FONT = new Font(Font.DIALOG_INPUT, Font.BOLD, 40);

    public static final Color BACKGROUND_COLOR = new Color(100, 100, 100);
    public static final Color ROAD_COLOR = new Color(220, 220, 220);
    public static final Color BUILDING_COLOR = new Color(150, 150, 150);
    public static final Color BLOCKADE_COLOR = Color.BLACK;
    public static final Color BLOCKADE_EDGE_COLOR = Color.RED;
    public static final Color REMOVE_BLOCKADE_COLOR = new Color(180, 180, 180);
    public static final Color REMOVE_BLOCKADE_EDGE_COLOR = new Color(255, 0, 0, 50);
    public static final Color STEP_COLOR = new Color(0, 200, 255);


    public static final Color EDGE_COLOR = Color.BLACK;
    public static final Color AT_COLOR = Color.WHITE;
    public static final Color PF_COLOR = Color.BLUE;
    public static final Color FB_COLOR = Color.RED;
    public static final Color CIV_Color = Color.GREEN;

    public static final Color UNBURNT_COLOR = Color.GRAY;
    public static final Color HEATING_COLOR = Color.YELLOW;
    public static final Color BURNING_COLOR = Color.ORANGE;
    public static final Color INFERNO_COLOR = Color.RED;
    public static final Color WATER_DAMAGE_COLOR = new Color(100, 149, 237);
    public static final Color MINOR_DAMAGE_COLOR = new Color(100, 149, 237);
    public static final Color MODERATE_DAMAGE_COLOR = new Color(100, 149, 237);
    public static final Color SEVERE_DAMAGE_COLOR = new Color(100, 149, 237);
    public static final Color BURNT_OUT_COLOR = new Color(75, 0, 0);


    public enum PlayMode {
        Play,
        Pause
    }

    /**
     * @param t
     */
    public static void addTime (int t) {
        if ( 0 < Config.Time + t && Config.Time + t < Config.LoadableTime ) {
            Config.Time += t;
        }
        SystemPanel.setTime(String.valueOf(Config.Time));
    }

    public static void addScale (double v) {
        if ( Config.Scale - v > Config.MinScale && Config.Scale - v < Config.MaxScale ) {
            Config.Scale -= v;
        }
        SystemPanel.setScale(String.format("%.0f", Config.Scale * 100) + "%");
    }

}