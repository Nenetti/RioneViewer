package rione;


import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.EntityID;
import rione.Category.Type;
import traffic3.manager.TrafficManager;
import traffic3.objects.TrafficArea;
import traffic3.objects.TrafficBlockade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * TrafficSimulatorで使用するログファイル出力クラス.<br>
 * <br>
 * TrafficSimulatorでしか道路情報を取得できない.<br>
 * <br>
 * 別クラスでは中身が未更新または空のデータしか取得できない.
 */
public class TrafficExporter {


    private static String STATIC_AREA_PATH = "Area/Area.bin";
    private static String CYCLE_ROAD_PATH = "Area/ro%d.bin";
    private static String CYCLE_BLOCKADE_PATH = "Area/bl%d.bin";

    /**
     * 道路情報を外部出力する.<br>
     * ただしログの軽量化のため非更新データは初期時刻でのみ出力する.<br>
     * <br>
     * FireSimulatorはサイクル(1)からしか呼ばれないため、初期時刻はサイクル(1)
     *
     * @param manager
     * @param time
     */
    public void exportTrafficLog (TrafficManager manager, int time) {
        try {
            if ( time == 1 ) {
                exportStaticLog(manager);
            } else {
                exportCycleLog(manager, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 道路情報の全てを外部出力する.
     *
     * @param manager
     */
    private void exportStaticLog (TrafficManager manager) throws Exception {
        Collection<TrafficArea> areas = manager.getAreas();
        if ( areas != null && !areas.isEmpty() ) {
            File file = Utility.createNewFile(STATIC_AREA_PATH);
            BinaryWriter writer = new BinaryWriter(file);
            for (TrafficArea trafficArea : areas) {
                Area area = trafficArea.getArea();
                if ( area != null && area.isXDefined() && area.isYDefined() ) {

                    List<EntityID> blockades = area.getBlockades();
                    List<EntityID> neighbours = area.getNeighbours();

                    writer.write(getAreaID(area));
                    writer.write(getID(area.getID()));
                    writer.write(area.getX());
                    writer.write(area.getY());
                    writer.write(area.getApexList());
                    writer.write(toInt(neighbours));
                    writer.write(toEdge(area.getEdges()));
                    writer.write(toInt(blockades));

                }
            }
            writer.close();
        }
    }


    /**
     * 建物情報の中でも毎サイクル変化する情報のみを外部出力する.
     *
     * @param manager
     * @param time
     */
    private void exportCycleLog (TrafficManager manager, int time) throws Exception {
        try {
            Collection<TrafficArea> areas = manager.getAreas();
            if ( areas != null && !areas.isEmpty() ) {
                File file = Utility.createNewFile(String.format(CYCLE_ROAD_PATH, time));
                BinaryWriter writer = new BinaryWriter(file);
                for (TrafficArea trafficArea : areas) {
                    Area area = trafficArea.getArea();
                    if ( area != null && area.isXDefined() && area.isYDefined() && area instanceof Road ) {

                        List<EntityID> blockades = area.getBlockades();
                        writer.write(getID(area.getID()));
                        writer.write(toInt(blockades));

                    }
                }
                writer.close();
            }
            Collection<TrafficBlockade> blockades = manager.getBlockades();
            if ( blockades != null && !blockades.isEmpty() ) {
                File file = Utility.createNewFile(String.format(CYCLE_BLOCKADE_PATH, time));
                BinaryWriter writer = new BinaryWriter(file);
                for (TrafficBlockade trafficBlockade : blockades) {
                    Blockade blockade = trafficBlockade.getBlockade();
                    if ( blockade != null && blockade.isXDefined() && blockade.isYDefined() && blockade.isApexesDefined() && blockade.isPositionDefined() ) {
                        writer.write(getID(blockade.getID()));
                        writer.write(blockade.getX());
                        writer.write(blockade.getY());
                        writer.write(getID(blockade.getPosition()));
                        writer.write(blockade.getRepairCost());
                        writer.write(blockade.getApexes());
                    }
                }
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * EntityIDのリストをintの配列に変換する.
     *
     * @param list
     * @return
     */
    private int[] toInt (List<EntityID> list) {
        if ( list != null && !list.isEmpty() ) {
            int[] array = new int[list.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = getID(list.get(i));
            }
            return array;
        }
        return new int[0];
    }

    /**
     * EdgeのListをx,y座標を交互にいれたintの配列に変換する.<br>
     * よって返される配列のサイズは Edgeリストのサイズ*2である.
     *
     * @param list
     * @return
     */
    private int[] toEdge (List<Edge> list) {
        if ( list != null && !list.isEmpty() ) {
            List<Edge> neighbours = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                if ( edge.getNeighbour() != null ) {
                    neighbours.add(edge);
                }
            }
            if ( !neighbours.isEmpty() ) {
                int[] array = new int[neighbours.size() * 5];
                for (int i = 0; i < array.length; i += 5) {
                    Edge edge = neighbours.get(i / 5);
                    array[i] = edge.getStartX();
                    array[i + 1] = edge.getStartY();
                    array[i + 2] = edge.getEndX();
                    array[i + 3] = edge.getEndY();
                    array[i + 4] = getID(edge.getNeighbour());
                }
                return array;
            }
            return null;
        }
        return null;
    }


    /**
     * 外部出力のために道路のクラス分類を行う.
     *
     * @param area
     * @return
     */
    private int getAreaID (Area area) {
        if ( area instanceof Road ) {
            if ( area instanceof Hydrant ) {
                return Type.Hydrant.getID();
            }
            return Type.Road.getID();
        } else {
            if ( area instanceof Refuge ) {
                return Type.Refuge.getID();
            } else if ( area instanceof AmbulanceCentre ) {
                return Type.AmbulanceCenter.getID();
            } else if ( area instanceof FireStation ) {
                return Type.FireStation.getID();
            } else if ( area instanceof PoliceOffice ) {
                return Type.PoliceOffice.getID();
            }
            return Type.Building.getID();
        }
    }

    /**
     * EntityIDをintに変換する.<br>
     * <br>
     * サーバーのバグかEntityIDが稀にnullであることがある.<br>
     * そのため.getValue()をそのまま使用するとNullPointerExceptionが発生することがある.<br>
     * よってこの関数を間に1度挟む必要がある.<br>
     *
     * @param id
     * @return
     */
    private int getID (EntityID id) {
        if ( id != null ) {
            return id.getValue();
        } else {
            return -1;
        }
    }
}