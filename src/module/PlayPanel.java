package module;


import config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import config.Config.PlayMode;



public class PlayPanel {

    @FXML
    public void onActionRewind (ActionEvent e) {
        Config.MODE = PlayMode.Pause;
        Config.addTime(-10);
    }

    @FXML
    public void onActionBack (ActionEvent e) {
        Config.MODE = PlayMode.Pause;
        Config.addTime(-1);
    }

    @FXML
    public void onActionPlay (ActionEvent e) {
        Config.MODE = PlayMode.Play;
    }

    @FXML
    public void onActionPause (ActionEvent e) {
        Config.MODE = PlayMode.Pause;
    }

    @FXML
    public void onActionForward (ActionEvent e) {
        Config.MODE = PlayMode.Pause;
        Config.addTime(1);
    }

    @FXML
    public void onActionSkip (ActionEvent e) {
        Config.MODE = PlayMode.Pause;
        Config.addTime(10);
    }


}
