import config.Config;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import module.*;
import module.Timer;

import javax.swing.*;
import java.awt.*;



/**
 * ViewerのGUIを管理するクラスです.
 * このクラスを通してGUIに組み込まれたコンポ−ネントの操作を行います.
 */
public class RioneViewer4 {


    private JFrame map;

    private MapPanel mapPanel;

    public void setup () {

        map = new JFrame();
        //map.setResizable(false);
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setBounds(0, 0, 1580, 720);
        map.setLayout(null);
        map.setTitle(Config.APPNAME + " Ver" + Config.VERSION);
        map.setBackground(Config.BACKGROUND_COLOR);
        //map.addKeyListener(new inputKey_Map());

        mapPanel = new MapPanel();
        mapPanel.addMouseListener(new Mouse());
        mapPanel.addMouseMotionListener(new Mouse());
        mapPanel.addMouseWheelListener(new Mouse());
        mapPanel.setBackground(new Color(0, 0, 0));
        mapPanel.setBounds(0, 0, 1280, 720);

        FrameRatePanel frameRatePanel = new FrameRatePanel();
        frameRatePanel.setBounds(0, 0, 200, 100);

        map.add(frameRatePanel);
        map.add(mapPanel);

        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBounds(1280, 0, 300, 720);
        VBox console = new VBox();
        jfxPanel.setScene(new Scene(console, 300, 720));
        map.add(jfxPanel);

        FXML playConsole = new FXML("PlayPanel.fxml");
        FXML systemInformation = new FXML("SystemPanel.fxml");
        FXML selectPanel = new FXML("SelectPanel.fxml");

        Platform.runLater(() -> {
            console.getChildren().add(systemInformation.getNode());
            console.getChildren().add(playConsole.getNode());
            console.getChildren().add(selectPanel.getNode());
        });

        GUI.init(
                (SystemPanel) systemInformation.getController(),
                (SelectPanel) selectPanel.getController(),
                (PlayPanel) playConsole.getController()
        );


        Config.MapX = -(int) (((Config.MapEndX - Config.MapStartX) * Config.Scale) / 2);
        Config.MapY = (int) (((Config.MapEndY - Config.MapStartY) * Config.Scale) / 2);


        map.setVisible(true);


        startModeManager();
        start();

    }

    /**
     * Viewerの時間系処理を別スレッドで開始させる.
     */
    private void start () {
        while (true) {
            Timer.start();
            update();
            Timer.stop();
            double processTime = Timer.getTime();
            int mm = (int) (1000.0 / Config.FPS - processTime);
            if ( mm > 0 ) {
                Timer.duration(mm);
                Config.FrameRate = Config.FPS;
            } else {
                Config.FrameRate = (int) (1000.0 / processTime);
            }
        }
    }

    /**
     * Viewer画面の更新を呼び出す.
     * ただし任意のタイミングではなく,できうる限り早く呼び出す.
     */
    private void update () {
        map.repaint();
    }

    /**
     * Viewerの再生状態による時間系処理の計算を別スレッドで行わせる.
     */
    private void startModeManager () {
        new Thread(() -> {
            while (true) {
                if ( Config.MODE == Config.PlayMode.Play ) {
                    Timer.addTime(1);
                }
                Timer.duration(1);
            }
        }).start();
    }


}