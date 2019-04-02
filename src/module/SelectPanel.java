package module;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;



public class SelectPanel {

    @FXML
    private ToggleButton normalBuilding;
    @FXML
    private ToggleButton burningBuilding;
    @FXML
    private ToggleButton road;
    @FXML
    private ToggleButton blockade;
    @FXML
    private ToggleButton ambulanceTeam;
    @FXML
    private ToggleButton fireBrigade;
    @FXML
    private ToggleButton policeForce;
    @FXML
    private ToggleButton civilian;

    @FXML
    public void onPushed (ActionEvent e) {
        change((ToggleButton) e.getSource());
    }

    private void change (ToggleButton button) {
        if ( button.isSelected() ) {
            button.setOpacity(0.5);
        } else {
            button.setOpacity(1.0);
        }
    }

    public ToggleButton getNormalBuilding () {
        return normalBuilding;
    }

    public ToggleButton getBurningBuilding () {
        return burningBuilding;
    }

    public ToggleButton getRoad () {
        return road;
    }

    public ToggleButton getBlockade () {
        return blockade;
    }

    public ToggleButton getAmbulanceTeam () {
        return ambulanceTeam;
    }

    public ToggleButton getFireBrigade () {
        return fireBrigade;
    }

    public ToggleButton getPoliceForce () {
        return policeForce;
    }

    public ToggleButton getCivilian () {
        return civilian;
    }
}
