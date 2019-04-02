package info;


import entities.*;

import java.util.Collection;
import java.util.HashMap;



public class WorldInfo {

    private static String path;
    private static HashMap<Integer, Road> roadMap;
    private static HashMap<Integer, Building> buildingMap;
    private static HashMap<Integer, Human> humanMap;
    private static HashMap<Integer, Blockade> blockadeMap;
    private static HashMap<Integer, Area> areaMap;



    private static TrafficInfo trafficInfo;
    private static MessageInfo messageInfo;

    public WorldInfo (String path) {
        WorldInfo.path = path;
    }

    public static void init () {
        WorldInfo.blockadeMap = new HashMap<>();
    }

    public static void setTrafficInfo (TrafficInfo trafficInfo) {
        WorldInfo.trafficInfo = trafficInfo;
    }

    public static TrafficInfo getTrafficInfo () {
        return trafficInfo;
    }

    public static void setMessageInfo (MessageInfo messageInfo) {
        WorldInfo.messageInfo = messageInfo;
    }

    public static MessageInfo getMessageInfo () {
        return messageInfo;
    }


    public static void setRoads (HashMap<Integer, Road> roads) {
        roadMap = roads;
    }

    public static void setBuildings (HashMap<Integer, Building> buildings) {
        buildingMap = buildings;
    }

    public static void setHumans (HashMap<Integer, Human> humans) {
        humanMap = humans;
    }

    public static void setBlockadeMap (HashMap<Integer, Blockade> blockadeMap) {
        WorldInfo.blockadeMap = blockadeMap;
    }

    public static void setAreaMap (HashMap<Integer, Area> areaMap) {
        WorldInfo.areaMap = areaMap;
    }

    public static String getPath () {
        return path;
    }

    public static HashMap<Integer, Road> getRoadMap () {
        return roadMap;
    }

    public static HashMap<Integer, Blockade> getBlockadeMap () {
        return blockadeMap;
    }

    public static HashMap<Integer, Building> getBuildingMap () {
        return buildingMap;
    }

    public static HashMap<Integer, Human> getHumanMap () {
        return humanMap;
    }

    public static HashMap<Integer, Area> getAreaMap () {
        return areaMap;
    }



    public static Blockade getBlockade (int entityID) {
        return blockadeMap.get(entityID);
    }

    public static Building getBuilding (int entityID) {
        return buildingMap.get(entityID);
    }

    public static Human getHuman (int entityID) {
        return humanMap.get(entityID);
    }

    public static Road getRoad (int entityID) {
        return roadMap.get(entityID);
    }

    public static Collection<Road> getRoads () {
        return roadMap.values();
    }

    public static Collection<Building> getBuildings () {
        return buildingMap.values();
    }

    public static Collection<Human> getHumans () {
        return humanMap.values();
    }

    public static Collection<Blockade> getBlockades () {
        return blockadeMap.values();
    }

}