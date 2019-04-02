package rione;


import firesimulator.world.*;
import rione.Category.Type;

import java.io.File;
import java.util.Collection;



/**
 * FireSimulatorで使用するログファイル出力クラス.<br>
 * <br>
 * FireSimulatorでしか建物情報を取得できない.<br>
 * <br>
 * 別クラスでは中身が未更新または空のデータしか取得できない.
 */
public class FireExporter {

    private static String STATIC_BUILDING_PATH = "Area/Building.bin";
    private static String CYCLE_BUILDING_PATH = "Area/bu%d.bin";

    public FireExporter () {
        Utility.init();
    }

    /**
     * 建物情報を外部出力する.<br>
     * ただしログの軽量化のため非更新データは初期時刻でのみ出力する.<br>
     * <br>
     * FireSimulatorはサイクル(1)からしか呼ばれないため、初期時刻はサイクル(1)
     *
     * @param world
     * @param time
     */
    public void exportBuildingLog (World world, int time) {
        try {
            if ( time == 1 ) {
                exportStaticLog(world);
            } else {
                exportCycleLog(world, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建物情報の全てを外部出力する.
     *
     * @param world
     */
    private void exportStaticLog (World world) {
        try {
            Collection<Building> buildings = world.getBuildings();
            if ( buildings != null && !buildings.isEmpty() ) {
                File file = Utility.createNewFile(STATIC_BUILDING_PATH);
                BinaryWriter writer = new BinaryWriter(file);
                for (Building building : buildings) {
                    if ( building != null ) {
                        writer.write(getClassID(building));
                        writer.write(building.getID());
                        writer.write(building.getX());
                        writer.write(building.getY());
                        writer.write(building.getBuildingAreaGround());
                        writer.write(building.getBurningTemp());
                        writer.write(building.getCapacity());
                        writer.write(building.getCode());
                        writer.write(building.getConsum());
                        writer.write(building.getEnergy());
                        writer.write(building.getFieryness());
                        writer.write(building.getFloors());
                        writer.write(building.getFuel());
                        writer.write(building.getIgnition());
                        writer.write(building.getIgnitionPoint());
                        writer.write(building.getInitialFuel());
                        writer.write(building.getLastWater());
                        writer.write(building.getPrevBurned());
                        writer.write(building.getRadiationEnergy());
                        writer.write(building.getTemperature());
                        writer.write(building.getWaterQuantity());
                        writer.write(building.getLastWatered());
                        writer.write(building.getApexes());
                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建物情報の中でも毎サイクル変化する情報のみを外部出力する.
     *
     * @param world
     * @param time
     */
    private void exportCycleLog (World world, int time) {
        try {
            Collection<Building> buildings = world.getBuildings();
            if ( buildings != null && !buildings.isEmpty() ) {
                File file = Utility.createNewFile(String.format(CYCLE_BUILDING_PATH, time));
                BinaryWriter writer = new BinaryWriter(file);
                for (Building building : buildings) {
                    if ( building != null ) {
                        writer.write(building.getID());
                        writer.write(building.getConsum());
                        writer.write(building.getEnergy());
                        writer.write(building.getFieryness());
                        writer.write(building.getFuel());
                        writer.write(building.getIgnition());
                        writer.write(building.getIgnitionPoint());
                        writer.write(building.getInitialFuel());
                        writer.write(building.getLastWater());
                        writer.write(building.getPrevBurned());
                        writer.write(building.getRadiationEnergy());
                        writer.write(building.getTemperature());
                        writer.write(building.getWaterQuantity());
                        writer.write(building.getLastWatered());
                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 外部出力のために建物のクラス分類を行う.
     *
     * @param building
     * @return
     */
    private int getClassID (Building building) {
        if ( building instanceof Refuge ) {
            return Type.Refuge.getID();
        } else if ( building instanceof AmbulanceCenter ) {
            return Type.AmbulanceCenter.getID();
        } else if ( building instanceof FireStation ) {
            return Type.FireStation.getID();
        } else if ( building instanceof PoliceOffice ) {
            return Type.PoliceOffice.getID();
        }
        return Type.Building.getID();
    }

}