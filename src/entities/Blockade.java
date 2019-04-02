package entities;


import java.util.HashMap;
import java.util.HashSet;

import entities.Category.Type;
import javafx.scene.paint.Color;



public class Blockade extends Entity {

    private int entityID;
    private HashMap<Integer, Integer> x = new HashMap<>();
    private HashMap<Integer, Integer> y = new HashMap<>();
    private HashMap<Integer, Apex> apexes = new HashMap<>();
    private int position;
    private HashMap<Integer, Integer> repairCost = new HashMap<>();
    private HashSet<Integer> timeStamp = new HashSet<>();

    private int generateTime;
    private int deleteTime = Integer.MAX_VALUE;


    public Blockade (Type classType, int entityID, int x, int y, int[] apexes, int position, int repairCost, int time) {
        super(classType);
        setEntityID(entityID);
        setX(x, time);
        setY(y, time);
        setApexes(apexes, time);
        setPosition(position);
        setCost(repairCost, time);
        setTimeStamp(time);
        this.generateTime = time;
    }

    public void update (int x, int y, int[] apexes, int position, int repairCost, int time) {
        setX(x, time);
        setY(y, time);
        setApexes(apexes, time);
        setCost(repairCost, time);
        setTimeStamp(time);
    }

    public boolean isGenerated (int time) {
        return this.generateTime <= time;
    }

    public int getGenerateTime () {
        return generateTime;
    }

    @Override
    public boolean isTimeExist (int time) {
        return x.containsKey(time);
    }

    public void setGenerateTime (int generateTime) {
        this.generateTime = generateTime;
    }

    public void setDeleteTime (int deleteTime) {
        this.deleteTime = deleteTime;
    }

    public void setEntityID (int entityID) {
        this.entityID = entityID;
    }

    public void setX (int x, int time) {
        this.x.put(time, x);
    }

    public void setY (int y, int time) {
        this.y.put(time, y);
    }

    public void setApexes (int[] apexes, int time) {
        this.apexes.put(time, new Apex(apexes));
    }

    public void setPosition (int position) {
        this.position = position;
    }

    public void setCost (int cost, int time) {
        this.repairCost.put(time, cost);
    }

    public void setTimeStamp (int time) {
        this.timeStamp.add(time);
    }

    public int getEntityID () {
        return entityID;
    }

    public Apex getApex (int time) {
        return apexes.get(time);
    }
}