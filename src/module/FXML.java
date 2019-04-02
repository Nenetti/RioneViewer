package module;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;



public class FXML<T> {

    private Node node;

    private T controller;


    public FXML (String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            this.node = loader.load();
            this.controller=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getNode () {
        return node;
    }

    public T getController () {
        return controller;
    }
}
