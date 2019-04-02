package rione;


import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.EntityID;
import rione.Category.Type;
import traffic3.manager.TrafficManager;
import traffic3.objects.TrafficAgent;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Collection;
import java.util.List;



/**
 * TrafficSimulatorで使用するログファイル出力クラス.<br>
 * <br>
 * TrafficSimulatorでしかエージェントのTimeStepを取得できない.<br>
 * <br>
 * 別クラスでは中身が未更新または空のデータしか取得できない.
 */
public class AgentExporter {

    private static String STATIC_ROAD_PATH = "Human/Human.bin";
    private static String CYCLE_PATH = "Human/hu%d.bin";
    private static String TIMESTEP_PATH = "Human/ts%d.bin";

    public AgentExporter () {
        Utility.init();
    }

    /**
     * エージェント情報を外部出力する.<br>
     * ただしログの軽量化のため非更新データは初期時刻でのみ出力する.<br>
     * <br>
     * TrafficSimulatorはサイクル(1)からしか呼ばれないため、初期時刻はサイクル(1)
     *
     * @param manager
     * @param time
     */
    public void exportAgentLog (TrafficManager manager, int time) {
        try {
            if ( time == 1 ) {
                exportStaticLog(manager);
            } else {
                exportCycleLog(manager, time);
                exportTimeStepLog(manager, time);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * エージェント情報の全てを外部出力する.
     *
     * @param manager
     */
    private void exportStaticLog (TrafficManager manager) throws Exception {
        Collection<TrafficAgent> humans = manager.getAgents();
        if ( humans != null && !humans.isEmpty() ) {
            File file = Utility.createNewFile(STATIC_ROAD_PATH);
            BinaryWriter writer = new BinaryWriter(file);
            for (TrafficAgent trafficAgent : humans) {
                Human human = trafficAgent.getHuman();
                if ( human != null && human.isHPDefined() && human.isXDefined() && human.isYDefined() ) {
                    writer.write(getHumanID(human));
                    writer.write(toID(human.getID()));
                    writer.write(human.getX());
                    writer.write(human.getY());
                    writer.write(human.getHP());
                    writer.write(human.getDamage());
                    writer.write(human.getBuriedness());
                    writer.write(toID(human.getPosition()));
                    writer.write(human.getStamina());
                    writer.write(getWater(human));
                }
            }
            writer.close();
        }
    }

    /**
     * エージェント情報の中でも毎サイクル変化する情報のみを外部出力する.
     *
     * @param manager
     * @param time
     */
    private void exportCycleLog (TrafficManager manager, int time) {
        try {
            Collection<TrafficAgent> humans = manager.getAgents();
            if ( humans != null ) {
                File file = Utility.createNewFile(String.format(CYCLE_PATH, time));
                BinaryWriter writer = new BinaryWriter(file);
                for (TrafficAgent agent : humans) {
                    Human human = agent.getHuman();
                    if ( human != null
                            && human.isXDefined()
                            && human.isYDefined()
                            && human.isHPDefined()
                            && human.isDamageDefined()
                            && human.isBuriednessDefined()
                            && human.isPositionDefined()
                            && human.isStaminaDefined()
                    ) {
                        writer.write(toID(human.getID()));
                        writer.write(human.getX());
                        writer.write(human.getY());
                        writer.write(human.getHP());
                        writer.write(human.getDamage());
                        writer.write(human.getBuriedness());
                        writer.write(toID(human.getPosition()));
                        writer.write(human.getStamina());
                        writer.write(getWater(human));
                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * エージェントの移動経路を毎サイクル出力する.
     *
     * @param manager
     * @param time
     */
    private void exportTimeStepLog (TrafficManager manager, int time) {
        try {
            Collection<TrafficAgent> humans = manager.getAgents();
            if ( humans != null ) {
                File file = Utility.createNewFile(String.format(TIMESTEP_PATH, time));
                BinaryWriter writer = new BinaryWriter(file);
                for (TrafficAgent agent : humans) {
                    Human human = agent.getHuman();
                    if ( human != null ) {
                        writer.write(toID(human.getID()));
                        writer.write(toDouble(agent.getTimeStep().getStepPoint()));
                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Point2Dのリストをdoubleの配列に変換する.
     *
     * @param list
     * @return
     */
    private double[] toDouble (List<Point2D.Double> list) {
        if ( list != null && !list.isEmpty() ) {
            double[] array = new double[list.size() * 2];
            for (int i = 0; i < array.length; i += 2) {
                array[i] = list.get(i / 2).getX();
                array[i + 1] = list.get(i / 2).getY();
            }
            return array;
        }
        return new double[0];
    }

    /**
     * 外部出力のためにHumanのクラス分類を行う.
     *
     * @param human
     * @return
     */
    private int getHumanID (Human human) {
        if ( human instanceof Civilian ) {
            return Type.Civilian.getID();
        } else if ( human instanceof AmbulanceTeam ) {
            return Type.AmbulanceTeam.getID();
        } else if ( human instanceof FireBrigade ) {
            return Type.FireBrigade.getID();
        } else if ( human instanceof PoliceForce ) {
            return Type.PoliceForce.getID();
        }
        return 0;
    }

    /**
     * エージェントの保水量を取得する
     *
     * @param human
     * @return
     */
    private int getWater (Human human) {
        if ( human instanceof FireBrigade ) {
            return ((FireBrigade) human).getWater();
        }
        return 0;
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
    private int toID (EntityID id) {
        if ( id != null ) {
            return id.getValue();
        } else {
            return -1;
        }
    }
}