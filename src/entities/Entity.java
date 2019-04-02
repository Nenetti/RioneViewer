package entities;


import entities.Category.Type;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import module.Utility;



public abstract class Entity {

    private Type classType;

    public Entity (Type classType) {
        this.classType = classType;
    }

    public Type getClassType () {
        return this.classType;
    }

    protected abstract boolean isTimeExist (int time);

}