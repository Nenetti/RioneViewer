package rione;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;



public class TimeStep {

    private List<Point2D.Double> stepPoint;
    private List<Point2D.Double> acceleration;

    public TimeStep () {
        this.stepPoint = new ArrayList<>();
        this.acceleration = new ArrayList<>();
    }

    public void addStepPoint (double x, double y) {
        this.stepPoint.add(new java.awt.geom.Point2D.Double(x, y));
    }

    public void addAcceleration (double x, double y) {
        this.acceleration.add(new java.awt.geom.Point2D.Double(x, y));
    }

    public void clear () {
        this.stepPoint.clear();
        this.acceleration.clear();
    }

    public List<Point2D.Double> getStepPoint () {
        return stepPoint;
    }

    public List<Point2D.Double> getAcceleration () {
        return acceleration;
    }
}
