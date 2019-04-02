package module;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;



public class SystemPanel implements Initializable {

    @FXML
    private Label scale;

    @FXML
    private Label time;

    private static Label scaleLabel;
    private static Label timeLabel;


    public static void setScale (String text) {
        Platform.runLater(() -> scaleLabel.setText(text));
    }

    public static void setTime (String text) {
        Platform.runLater(() -> timeLabel.setText(text));
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        scaleLabel = this.scale;
        timeLabel = this.time;
    }
}
