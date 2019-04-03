package reader;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import config.Config;
import entities.*;
import info.WorldInfo;
import module.BinaryReader;
import module.Utility;



public class LogReader {


    public LogReader () {
        setup();
    }

    public void setup () {
        try {
            setupArea("Data/Area/Area.bin");
            setupBuilding("Data/Area/Building.bin");
            setupHuman("Data/Human/Human.bin");
            //new TrafficInfo(worldInfo);
            //new MessageInfo(worldInfo);
            calcMapSize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calcMapSize () {
        int sx = Integer.MAX_VALUE, sy = Integer.MAX_VALUE, ex = 0, ey = 0;
        Collection<Area> areas = WorldInfo.getAreaMap().values();
        for (Area area : areas) {
            sx = Math.min(sx, area.getX());
            sy = Math.min(sy, area.getY());
            ex = Math.max(ex, area.getX());
            ey = Math.max(ey, area.getY());
        }
        Config.MapStartX = sx;
        Config.MapStartY = sy;
        Config.MapEndX = ex;
        Config.MapEndY = ey;
    }

    public void read () {
        try {
            int time = 2;
            for (int i = 0; i < 1000; i++) {
                int loadableTime = readTime("Data/Time.bin");
                Config.LoadableTime = loadableTime;
                if ( time <= loadableTime ) {
                    updateBlockade(String.format("Data/Area/bl%d.bin", time), time);
                    updateHuman(String.format("Data/Human/hu%d.bin", time), time);
                    updateRoad(String.format("Data/Area/ro%d.bin", time), time);
                    updateBuilding(String.format("Data/Area/bu%d.bin", time), time);
                    updateTimeStep(String.format("Data/Human/ts%d.bin", time), time);
                    try {
                        updateSourceAgentInfo(time);
                        updateMessageInfo(time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("Loading Time[%d]", time));
                    time++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初期の時刻に依存しないAreaログデータの読み込みを行う.
     *
     * @param path
     */
    private void setupArea (String path) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Area> areaMap = new HashMap<>();
        HashMap<Integer, Road> roadMap = new HashMap<>();

        List<Area> areaList = new ArrayList<>();
        List<int[]> neighbourList = new ArrayList<>();
        List<int[]> edgeList = new ArrayList<>();

        while (reader.isReadable()) {
            int classID = reader.getInt();
            int entityID = reader.getInt();
            int x = reader.getInt();
            int y = reader.getInt();
            int[] apexes = reader.getIntArray();
            int[] neighbours = reader.getIntArray();
            int[] edges = reader.getIntArray();
            int[] blockades = reader.getIntArray();
            Category.Type classType = Category.Type.getURN(classID);

            x = rescaleX(x);
            y = rescaleY(y);
            apexes = rescale(apexes);

            Area area;
            if ( classType == Category.Type.Road || classType == Category.Type.Hydrant ) {
                Road road = new Road(classType, entityID, x, y, apexes, toBlockade(blockades));
                roadMap.put(entityID, road);
                areaMap.put(entityID, road);
                area = road;
            } else {
                Building building = new Building(classType, entityID, x, y, apexes);
                areaMap.put(entityID, building);
                area = building;
            }
            areaList.add(area);
            neighbourList.add(neighbours);
            edgeList.add(edges);
        }

        for (int i = 0; i < areaList.size(); i++) {
            Area area = areaList.get(i);
            int[] neighbourIDs = neighbourList.get(i);
            Area[] neighbours = new Area[neighbourIDs.length];
            for (int k = 0; k < neighbours.length; k++) {
                neighbours[k] = areaMap.get(neighbourIDs[k]);
            }
            int[] edgeIDs = edgeList.get(i);
            Edge[] edges = new Edge[edgeIDs.length / 5];
            for (int k = 0; k < edgeIDs.length; k += 5) {
                edges[k / 5] = new Edge(edgeIDs[k], edgeIDs[k + 1], edgeIDs[k + 2], edgeIDs[k + 3],
                        areaMap.get(edgeIDs[k + 4]));
            }
            area.update(neighbours, edges);
        }

        WorldInfo.setRoads(roadMap);
        WorldInfo.setAreaMap(areaMap);
    }

    /**
     * 指定時刻の道路ログデータを読み込む.
     *
     * @param path
     * @param time
     */
    private void updateRoad (String path, int time) {
        File file = new File(path);

        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Road> roadMap = WorldInfo.getRoadMap();

        while (reader.isReadable()) {
            int entityID = reader.getInt();
            int[] blockades = reader.getIntArray();
            Road road = roadMap.get(entityID);
            if ( road != null ) {
                road.update(time, toBlockade(blockades));
            }
        }
    }

    /**
     * 初期の時刻に依存しないBuildingログデータの読み込みを行う.<br>
     * ただし座標などのRoadとの共通部分など一部パラメータはAreaを利用している
     *
     * @param path
     */
    private void setupBuilding (String path) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Area> areaMap = WorldInfo.getAreaMap();
        HashMap<Integer, Building> buildingMap = new HashMap<>();

        while (reader.isReadable()) {
            int classID = reader.getInt();
            int entityID = reader.getInt();
            int x = reader.getInt();
            int y = reader.getInt();
            double buildingAreaGround = reader.getDouble();
            double burningTemp = reader.getDouble();
            double capacity = reader.getDouble();
            int code = reader.getInt();
            double consum = reader.getDouble();
            double energy = reader.getDouble();
            int fieryness = reader.getInt();
            int floors = reader.getInt();
            double fuel = reader.getDouble();
            int ignition = reader.getInt();
            double ignitionPoint = reader.getDouble();
            double initialFuel = reader.getDouble();
            int lastWater = reader.getInt();
            double prevBurned = reader.getDouble();
            double radiationEnergy = reader.getDouble();
            double temperature = reader.getDouble();
            int waterQuantity = reader.getInt();
            boolean lastWatered = reader.getBoolean();
            int[] apexes = reader.getIntArray();
            Category.Type classType = Category.Type.getURN(classID);
            Category.Fieryness fierynessType = Category.Fieryness.getURN(fieryness);
            Building building = (Building) areaMap.get(entityID);
            if ( building != null ) {
                building.setup(buildingAreaGround, burningTemp, capacity, code, consum, energy, fierynessType, floors,
                        fuel, ignition, ignitionPoint, initialFuel, lastWater, prevBurned, radiationEnergy, temperature,
                        waterQuantity, lastWatered);
                buildingMap.put(entityID, building);
            }
        }
        WorldInfo.setBuildings(buildingMap);
    }

    /**
     * 指定時刻の建物ログデータを読み込む.
     *
     * @param path
     * @param time
     */
    private void updateBuilding (String path, int time) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Building> buildingMap = WorldInfo.getBuildingMap();

        while (reader.isReadable()) {
            int entityID = reader.getInt();
            double consum = reader.getDouble();
            double energy = reader.getDouble();
            int fieryness = reader.getInt();
            double fuel = reader.getDouble();
            int ignition = reader.getInt();
            double ignitionPoint = reader.getDouble();
            double initialFuel = reader.getDouble();
            int lastWater = reader.getInt();
            double prevBurned = reader.getDouble();
            double radiationEnergy = reader.getDouble();
            double temperature = reader.getDouble();
            int waterQuantity = reader.getInt();
            boolean lastWatered = reader.getBoolean();
            Building building = buildingMap.get(entityID);
            Category.Fieryness fierynessType = Category.Fieryness.getURN(fieryness);
            if ( building != null ) {
                building.update(consum, energy, fierynessType, fuel, ignition, ignitionPoint, initialFuel, lastWater,
                        prevBurned, radiationEnergy, temperature, waterQuantity, lastWatered, time);
            }
        }
    }

    /**
     * 初期の時刻に依存しないHumanログデータの読み込みを行う.<br>
     *
     * @param path
     */
    private void setupHuman (String path) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Human> humanMap = new HashMap<>();

        while (reader.isReadable()) {
            int classID = reader.getInt();
            int entityID = reader.getInt();
            int x = reader.getInt();
            int y = reader.getInt();
            int hp = reader.getInt();
            int damage = reader.getInt();
            int buriedness = reader.getInt();
            int position = reader.getInt();
            int stamina = reader.getInt();
            int water = reader.getInt();

            x = rescaleX(x);
            y = rescaleY(y);

            Category.Type classType = Category.Type.getURN(classID);
            if ( classType != null ) {
                Human human = new Human(classType, entityID, x, y, hp, damage, buriedness, position, stamina, water);
                //human.setupImage(worldInfo.getDataStorage());
                humanMap.put(entityID, human);
            }
        }
        WorldInfo.setHumans(humanMap);
    }

    /**
     * 指定時刻のHumanログデータを読み込む.
     *
     * @param path
     * @param time
     */
    private void updateHuman (String path, int time) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Human> humanMap = WorldInfo.getHumanMap();
        while (reader.isReadable()) {
            int entityID = reader.getInt();
            int x = reader.getInt();
            int y = reader.getInt();
            int hp = reader.getInt();
            int damage = reader.getInt();
            int buriedness = reader.getInt();
            int position = reader.getInt();
            int stamina = reader.getInt();
            int water = reader.getInt();
            Human human = humanMap.get(entityID);

            x = rescaleX(x);
            y = rescaleY(y);

            if ( human != null ) {
                human.update(x, y, hp, damage, buriedness, position, stamina, water, time);
            }
        }
    }

    /**
     * 指定時刻のTimeStepログデータを読み込む.
     *
     * @param path
     * @param time
     */
    public void updateTimeStep (String path, int time) {
        File file = new File(path);
        BinaryReader reader = new BinaryReader(file);
        HashMap<Integer, Human> humanMap = WorldInfo.getHumanMap();
        while (reader.isReadable()) {
            int entityID = reader.getInt();
            double[] step = reader.getDoubleArray();
            int[] restep = rescale(step);
            if ( restep.length > 0 ) {
                Human human = humanMap.get(entityID);
                if ( human != null ) {
                    human.updateTimeStep(restep, time + 1);
                }
            }
        }
    }

    /**
     * クライント側(Agent側)から送られてきた指定時刻のエージェント全般ログデータを読み込む.
     *
     * @param time
     */
    public void updateSourceAgentInfo (int time) {
        File file = Utility.toFile(String.format("Source/%d/ac", time));
        if ( !file.exists() ) return;
        File[] files = file.listFiles();
        if ( files == null ) return;
        HashMap<Integer, Area> areaMap = WorldInfo.getAreaMap();
        for (File f : files) {
            BinaryReader reader = new BinaryReader(f);
            int entityID = reader.getInt();
            int action = reader.getInt();
            int detectorTarget = reader.getInt();
            int someoneOnBoard = reader.getInt();
            long thinkTime = reader.getLong();
            int[] changedEntities = reader.getIntArray();
            int clear_Target = 0;
            boolean clear_UseOldFunction = false;
            int clear_X = 0;
            int clear_Y = 0;
            int extinguish_Target = 0;
            int extinguish_power = 0;
            int load_Target = 0;
            int[] move_path = null;
            boolean move_usePosition = false;
            int move_X = 0;
            int move_Y = 0;
            int rescue_Target = 0;
            Category.Action actionType = Category.Action.getURN(action);
            switch (actionType) {
                case ActionClear:
                    clear_Target = reader.getInt();
                    clear_UseOldFunction = reader.getBoolean();
                    clear_X = reader.getInt();
                    clear_Y = reader.getInt();
                    break;
                case ActionExtinguish:
                    extinguish_Target = reader.getInt();
                    extinguish_power = reader.getInt();
                    break;
                case ActionLoad:
                    load_Target = reader.getInt();
                    break;
                case ActionMove:
                    move_path = reader.getIntArray();
                    move_usePosition = reader.getBoolean();
                    move_X = reader.getInt();
                    move_Y = reader.getInt();
                    break;
                case ActionRefill:
                    break;
                case ActionRescue:
                    rescue_Target = reader.getInt();
                    break;
                case ActionRest:
                    break;
                case ActionSearch:
                    break;
                case ActionUnload:
                    break;
            }
            Human human = WorldInfo.getHuman(entityID);
            if ( human != null ) {
                Area[] path = null;
                if ( actionType == Category.Action.ActionMove ) {
                    List<Area> list = new ArrayList<>();
                    for (int id : move_path) {
                        list.add(areaMap.get(id));
                    }
                    path = list.toArray(new Area[0]);
                }
                // 1フレームずれる
                // これは実際に行動を起こすのは次のフレームのため
                human.updateSource(actionType, detectorTarget, someoneOnBoard, thinkTime, changedEntities, clear_Target,
                        clear_UseOldFunction, clear_X, clear_Y, extinguish_Target, extinguish_power, load_Target,
                        path, move_usePosition, move_X, move_Y, rescue_Target, time + 1);
            }
        }
    }

    /**
     * クライント側(Agent側)から送られてきた指定時刻のメッセージログデータを読み込む.
     *
     * @param time
     */
    public void updateMessageInfo (int time) {
        File file = Utility.toFile(String.format("Source/%d/me", time));
        if ( !file.exists() ) return;
        File[] files = file.listFiles();
        if ( files == null ) return;
        for (File f : files) {
            BinaryReader reader = new BinaryReader(f);
            int entityID = reader.getInt();
            Human human = WorldInfo.getHuman(entityID);
            int receiveSize = reader.getInt();
            Message[] receivedMessages = new Message[receiveSize];
            for (int i = 0; i < receiveSize; i++) {
                receivedMessages[i] = toMessage(reader, human);
            }
            int sendSize = reader.getInt();
            Message[] sendMessages = new Message[sendSize];
            for (int i = 0; i < sendSize; i++) {
                sendMessages[i] = toMessage(reader, human);
            }
            human.updateMessage(receivedMessages, sendMessages, time);
        }
    }

    /**
     * 指定時刻のBlockadeログデータを読み込む.
     *
     * @param fileName
     * @param time
     */
    public void updateBlockade (String fileName, int time) {
        File file = new File(fileName);
        System.out.println(file);
        BinaryReader reader = new BinaryReader(file);
        while (reader.isReadable()) {
            int entityID = reader.getInt();
            int x = reader.getInt();
            int y = reader.getInt();
            int position = reader.getInt();
            int repairCost = reader.getInt();
            int[] apexes = reader.getIntArray();
            Blockade blockade = WorldInfo.getBlockade(entityID);

            x = rescaleX(x);
            y = rescaleY(y);
            apexes = rescale(apexes);

            if ( blockade != null ) {
                blockade.update(x, y, apexes, position, repairCost, time);
            } else {
                blockade = new Blockade(Category.Type.Blockade, entityID, x, y, apexes, position, repairCost, time);
                WorldInfo.getBlockadeMap().put(entityID, blockade);
            }
        }
    }


//    public void setupUserData (String fileName, int time)  {
//        File file = new File(fileName);
//        BinaryReader reader = new BinaryReader(file);
//        HashMap<Integer, Human> humanMap = worldInfo.getHumanMap();
//        while (reader.isReadable()) {
//            Category classType = Category.getURN(reader.getInt());
//            int entityID = reader.getInt();
//            double r = reader.getDouble();
//            double g = reader.getDouble();
//            double b = reader.getDouble();
//        }
//    }
//
//    public void updateUserData (String fileName, int time)  {
//        File file = new File(fileName);
//        BinaryReader reader = new BinaryReader(file);
//        HashMap<Integer, Human> humanMap = worldInfo.getHumanMap();
//        while (reader.isReadable()) {
//            Category classType = Category.getURN(reader.getInt());
//            int entityID = reader.getInt();
//            double r = reader.getDouble();
//            double g = reader.getDouble();
//            double b = reader.getDouble();
//        }
//    }


    //
    private Message toMessage (BinaryReader reader, Human receiver) {
        int message = reader.getInt();
        Category.Message messageType = Category.Message.getURN(message);
        int senderID = 0;
        int agentID = 0;
        int buildingID = 0;
        int brokeness = 0;
        int fieryness = 0;
        int temperature = 0;
        int blockadeID = 0;
        int blockadeX = 0;
        int blockadeY = 0;
        int roadID = 0;
        int repairCost = 0;
        int buriedness = 0;
        int damage = 0;
        int hp = 0;
        int position = 0;
        int targetID = 0;
        int toID = 0;
        boolean isRadio = false;
        int messageSize = 0;
        Category.Action actionType = null;
        switch (messageType) {
            case CommandAmbulance:
                senderID = reader.getInt();
                targetID = reader.getInt();
                toID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case CommandFire:
                senderID = reader.getInt();
                targetID = reader.getInt();
                toID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case CommandPolice:
                senderID = reader.getInt();
                targetID = reader.getInt();
                toID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessageAmbulanceTeam:
                senderID = reader.getInt();
                agentID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                buriedness = reader.getInt();
                damage = reader.getInt();
                hp = reader.getInt();
                position = reader.getInt();
                targetID = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessageBuilding:
                senderID = reader.getInt();
                buildingID = reader.getInt();
                brokeness = reader.getInt();
                fieryness = reader.getInt();
                temperature = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessageCivilian:
                senderID = reader.getInt();
                agentID = reader.getInt();
                buriedness = reader.getInt();
                damage = reader.getInt();
                hp = reader.getInt();
                position = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessageFireBrigade:
                senderID = reader.getInt();
                agentID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                buriedness = reader.getInt();
                damage = reader.getInt();
                hp = reader.getInt();
                position = reader.getInt();
                targetID = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessagePoliceForce:
                senderID = reader.getInt();
                agentID = reader.getInt();
                actionType = Category.Action.getURN(reader.getInt());
                buriedness = reader.getInt();
                damage = reader.getInt();
                hp = reader.getInt();
                position = reader.getInt();
                targetID = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
            case MessageRoad:
                senderID = reader.getInt();
                blockadeID = reader.getInt();
                if ( blockadeID != -1 ) {
                    blockadeX = reader.getInt();
                    blockadeY = reader.getInt();
                }
                roadID = reader.getInt();
                repairCost = reader.getInt();
                isRadio = reader.getBoolean();
                messageSize = reader.getInt();
                break;
        }
        Human sender = WorldInfo.getHuman(senderID);
        Human agent = WorldInfo.getHuman(agentID);
        Human toHuman = WorldInfo.getHuman(toID);
        Road road = WorldInfo.getRoad(roadID);
        Blockade blockade = WorldInfo.getBlockade(blockadeID);
        Building building = WorldInfo.getBuilding(buildingID);
        switch (messageType) {
            case CommandAmbulance:
            case CommandFire:
            case CommandPolice:
                return new CommandMessage(messageType, sender, receiver, targetID, toHuman, actionType, isRadio,
                        messageSize);
            case MessageAmbulanceTeam:
            case MessageFireBrigade:
            case MessagePoliceForce:
            case MessageCivilian:
                return new ActionMessage(messageType, sender, receiver, agent, actionType, buriedness, damage, hp,
                        position, targetID, isRadio, messageSize);
            case MessageBuilding:
                return new BuildingMessage(messageType, sender, receiver, building, brokeness, fieryness, temperature,
                        isRadio, messageSize);
            case MessageRoad:
                return new RoadMessage(messageType, sender, receiver, blockade, blockadeX, blockadeY, road, repairCost,
                        isRadio, messageSize);
        }
        return null;
    }

    private Blockade[] toBlockade (int[] array) {
        List<Blockade> blockades = new ArrayList<>();
        for (int id : array) {
            Blockade b = WorldInfo.getBlockade(id);
            if ( b != null ) {
                blockades.add(b);
            }
        }
        return blockades.toArray(new Blockade[0]);
    }

    private int readTime (String path) {
        BinaryReader reader = new BinaryReader(new File(path));
        if ( reader.isReadable() ) {
            return reader.getInt();
        }
        return 0;
    }


    private int rescaleX (int value) {
        return value / Config.RESCALE_UNIT;
    }

    private int rescaleY (int value) {
        return value / -Config.RESCALE_UNIT;
    }

    private int[] rescale (double[] value) {
        if ( value != null ) {
            int[] re = new int[value.length];
            for (int i = 0; i < value.length; i++) {
                if ( i % 2 == 0 ) {
                    re[i] = (int) (value[i] / Config.RESCALE_UNIT);
                } else {
                    re[i] = (int) (value[i] / -Config.RESCALE_UNIT);
                }
            }
            return re;
        }
        return null;
    }

    private int[] rescale (int[] value) {
        if ( value != null ) {
            for (int i = 0; i < value.length; i++) {
                if ( i % 2 == 0 ) {
                    value[i] /= Config.RESCALE_UNIT;
                } else {
                    value[i] /= -Config.RESCALE_UNIT;
                }
            }
        }
        return value;
    }


}