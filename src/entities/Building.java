package entities;


import java.util.HashMap;

import entities.Category.BuildingCode;
import entities.Category.Type;
import entities.Category.Fieryness;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



public class Building extends Area {

    private double buildingAreaGround = 0;
    private double burningTemp = 0;
    private int code = 0;
    private int floors = 0;
    private HashMap<Integer, Double> capacity = new HashMap<>();
    private HashMap<Integer, Double> consum = new HashMap<>();
    private HashMap<Integer, Double> energy = new HashMap<>();
    private HashMap<Integer, Fieryness> fieryness = new HashMap<>();
    private HashMap<Integer, Double> fuel = new HashMap<>();
    private HashMap<Integer, Integer> ignition = new HashMap<>();
    private HashMap<Integer, Double> ignitionPoint = new HashMap<>();
    private HashMap<Integer, Double> initialFuel = new HashMap<>();
    private HashMap<Integer, Integer> lastWater = new HashMap<>();
    private HashMap<Integer, Double> prevBurned = new HashMap<>();
    private HashMap<Integer, Double> radiationEnergy = new HashMap<>();
    private HashMap<Integer, Double> temperature = new HashMap<>();
    private HashMap<Integer, Integer> waterQuantity = new HashMap<>();
    private HashMap<Integer, Boolean> lastWatered = new HashMap<>();


    private Polygon polygon;


    public Building (Type classType, int entityID, int x, int y, int[] apexes) {
        super(classType, entityID, x, y, apexes);
    }

    public void setup (double buildingAreaGround, double burningTemp, double capacity, int code, double consum,
                       double energy, Fieryness fieryness, int floors, double fuel, int ignition, double ignitionPoint, double initialFuel, int lastWater,
                       double prevBurned, double radiationEnergy, double temperature, int waterQuantity, boolean lastWatered) {
        setFieryness(1, fieryness);
        setBuildingAreaGround(buildingAreaGround);
        setBurningTemp(burningTemp);
        setCapacity(1, capacity);
        setCode(code);
        setConsum(1, consum);
        setEnergy(1, energy);
        setFieryness(1, fieryness);
        setFloors(floors);
        setFuel(1, fuel);
        setIgnition(1, ignition);
        setIgnitionPoint(1, ignitionPoint);
        setInitialFuel(1, initialFuel);
        setLastWater(1, lastWater);
        setPrevBurned(1, prevBurned);
        setRadiationEnergy(1, radiationEnergy);
        setTemperature(1, temperature);
        setWaterQuantity(1, waterQuantity);
        setLastWatered(1, lastWatered);
    }

    public void update (double consum, double energy, Fieryness fieryness, double fuel, int ignition, double ignitionPoint, double initialFuel, int lastWater,
                        double prevBurned, double radiationEnergy, double temperature, int waterQuantity, boolean lastWatered, int time) {
        setFieryness(time, fieryness);
        setConsum(time, consum);
        setEnergy(time, energy);
        setFieryness(time, fieryness);
        setFuel(time, fuel);
        setIgnition(time, ignition);
        setIgnitionPoint(time, ignitionPoint);
        setInitialFuel(time, initialFuel);
        setLastWater(time, lastWater);
        setPrevBurned(time, prevBurned);
        setRadiationEnergy(time, radiationEnergy);
        setTemperature(time, temperature);
        setWaterQuantity(time, waterQuantity);
        setLastWatered(time, lastWatered);
    }

    public Color setFillColor (int time) {
        if ( isTimeExist(time) ) {
            switch (fieryness.get(time)) {
                case UNBURNT:
                    return Color.GRAY;
                case HEATING:
                    return Color.YELLOW;
                case BURNING:
                    return Color.ORANGE;
                case INFERNO:
                    return Color.RED;
                case WATER_DAMAGE:
                    return Color.CORNFLOWERBLUE;
                case MINOR_DAMAGE:
                    return Color.CORNFLOWERBLUE;
                case MODERATE_DAMAGE:
                    return Color.CORNFLOWERBLUE;
                case SEVERE_DAMAGE:
                    return Color.CORNFLOWERBLUE;
                case BURNT_OUT:
                    return Color.color(0.3, 0, 0);
            }
        }
        return Color.GRAY;
    }

//    public double getTemperatureChange (int time, timeShift shift) {
//        if ( isTimeExist(time) && isTimeExist(time - shift.getValue()) ) {
//            return temperature.get(time) - temperature.get(time - shift.getValue());
//        }
//        return Double.NaN;
//    }

    @Override
    protected boolean isTimeExist (int time) {
        return lastWatered.containsKey(time);
    }

    public void setBuildingAreaGround (double buildingAreaGround) {
        this.buildingAreaGround = buildingAreaGround;
    }

    public void setBurningTemp (double burningTemp) {
        this.burningTemp = burningTemp;
    }

    public void setCapacity (int time, double capacity) {
        this.capacity.put(time, capacity);
    }

    public void setCode (int code) {
        this.code = code;
    }

    public void setConsum (int time, double consum) {
        this.consum.put(time, consum);
    }

    public void setEnergy (int time, double energy) {
        this.energy.put(time, energy);
    }

    public void setFieryness (int time, Fieryness fieryness) {
        this.fieryness.put(time, fieryness);
    }

    public void setFloors (int floors) {
        this.floors = floors;
    }

    public void setFuel (int time, double fuel) {
        this.fuel.put(time, fuel);
    }

    public void setIgnition (int time, int ignition) {
        this.ignition.put(time, ignition);
    }

    public void setIgnitionPoint (int time, double ignitionPoint) {
        this.ignitionPoint.put(time, ignitionPoint);
    }

    public void setInitialFuel (int time, double initialFuel) {
        this.initialFuel.put(time, initialFuel);
    }

    public void setLastWater (int time, int lastWater) {
        this.lastWater.put(time, lastWater);
    }

    public void setPrevBurned (int time, double prevBurned) {
        this.prevBurned.put(time, prevBurned);
    }

    public void setRadiationEnergy (int time, double radiationEnergy) {
        this.radiationEnergy.put(time, radiationEnergy);
    }

    public void setTemperature (int time, double temperature) {
        this.temperature.put(time, temperature);
    }

    public void setWaterQuantity (int time, int waterQuantity) {
        this.waterQuantity.put(time, waterQuantity);
    }

    public void setLastWatered (int time, boolean lastWatered) {
        this.lastWatered.put(time, lastWatered);
    }

    //*********************************************************************************************************************************************************************************

    public double getBuildingAreaGround () {
        return this.buildingAreaGround;
    }

    public double getBurningTemp () {
        return this.burningTemp;
    }

    public BuildingCode getCode () {
        switch (code) {
            case 0:
                return BuildingCode.WOOD;
            case 1:
                return BuildingCode.STEEL;
            case 2:
                return BuildingCode.CONCRETE;
        }
        return null;
    }

    public int getFloors () {
        return this.floors;
    }

    public double getTemperature (int time) {
        return temperature.get(time);
    }

    public Fieryness getFieryness (int time) {
        return fieryness.get(time);
    }


}