package entities;


import entities.Category.Type;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Human extends Entity {


    private int entityID;
    private HashMap<Integer, Integer> x = new HashMap<>();
    private HashMap<Integer, Integer> y = new HashMap<>();
    private HashMap<Integer, Integer> hp = new HashMap<>();
    private HashMap<Integer, Integer> damage = new HashMap<>();
    private HashMap<Integer, Integer> buriedness = new HashMap<>();
    private HashMap<Integer, Integer> position = new HashMap<>();
    private HashMap<Integer, Integer> stamina = new HashMap<>();
    private HashMap<Integer, Integer> water = new HashMap<>();
    private HashMap<Integer, Action> action = new HashMap<>();
    private HashMap<Integer, Step> step = new HashMap<>();
    private HashMap<Integer, Message[]> receivedMessage = new HashMap<>();
    private HashMap<Integer, Message[]> sendMessage = new HashMap<>();


    public enum statusURN {
        bleed,
        buried,
        water_deficit,
        carry,
        carried,
    }


    public Human (Type classType, int entityID, int x, int y, int hp, int damage, int buriedness, int position, int stamina, int water) {
        super(classType);
        setEntityID(entityID);
        setX(1, x);
        setY(1, y);
        setHp(1, hp);
        setDamage(1, damage);
        setBuriedness(1, buriedness);
        setPosition(1, position);
        setStamina(1, stamina);
        setWater(1, water);
    }

    public void update (int x, int y, int hp, int damage, int buriedness, int position, int stamina, int water, int time) {
        setX(time, x);
        setY(time, y);
        setHp(time, hp);
        setDamage(time, damage);
        setBuriedness(time, buriedness);
        setPosition(time, position);
        setStamina(time, stamina);
        setWater(time, water);
    }

    public void updateTimeStep (int[] step, int time) {
        setTimeStep(time, step);
    }

    public void updateMessage (Message[] receivedMessage, Message[] sendMessage, int time) {
        setReceivedMessage(time, receivedMessage);
        setSendMessage(time, sendMessage);
    }

    public void updateSource (Category.Action actionType, int detectorTarget, int someoneOnBoard, long thinkTime,
                              int[] changedEntities, int clearTarget, boolean clearUseOldFunction, int clearX,
                              int clearY, int extinguishTarget, int extinguishPower, int loadTarget, Area[] movePath,
                              boolean moveUsePosition, int moveX, int moveY, int rescueTarget, int time) {
        Action action = new Action(actionType, detectorTarget, someoneOnBoard, thinkTime, changedEntities,
                clearTarget, clearUseOldFunction, clearX, clearY,
                extinguishTarget, extinguishPower,
                loadTarget,
                movePath, moveUsePosition, moveX, moveY, rescueTarget);
        this.action.put(time, action);
//         setAction(time, actionType);
//         setDetectorTarget(time, detectorTarget);
//         setSomeoneOnBoard(time, someoneOnBoard);
//         setThinkTime(time, thinkTime);
//         setChangedEntities(time, changedEntities);
//         switch (actionType) {
//             case ActionClear:
//                 setClear_Target(time, clear_Target);
//                 setClear_UseOldFunction(time, clear_UseOldFunction);
//                 setClear_X(time, clear_X);
//                 setClear_Y(time, clear_Y);
//                 break;
//             case ActionExtinguish:
//                 setExtinguish_Target(time, extinguish_Target);
//                 setExtinguish_power(time, extinguish_power);
//                 break;
//             case ActionLoad:
//                 setLoad_Target(time, load_Target);
//                 break;
//             case ActionMove:
//                 setMove_path(time, move_path);
//                 setMove_usePosition(time, move_usePosition);
//                 setMove_X(time, move_X);
//                 setMove_Y(time, move_Y);
//                 break;
//             case ActionRefill:
//                 break;
//             case ActionRescue:
//                 setRescue_Target(time, rescue_Target);
//                 break;
//             case ActionRest:
//                 break;
//             case ActionSearch:
//                 break;
//             case ActionUnload:
//                 break;
//         }
    }


//    public void setupStatus (DataStorage storage, int time) {
//        if ( getClassType() == Type.Civilian ) {
//            this.statusImage.setImage(storage.getImage_action_move());
//        } else {
//            switch (action.get(time)) {
//                case ActionClear:
//                    this.statusImage.setImage(storage.getImage_action_clear());
//                    break;
//                case ActionExtinguish:
//                    this.statusImage.setImage(storage.getImage_action_extinguish());
//                    break;
//                case ActionLoad:
//                    this.statusImage.setImage(storage.getImage_action_load());
//                    break;
//                case ActionMove:
//                    this.statusImage.setImage(storage.getImage_action_move());
//                    break;
//                case ActionRefill:
//                    this.statusImage.setImage(storage.getImage_action_refill());
//                    break;
//                case ActionRescue:
//                    this.statusImage.setImage(storage.getImage_action_rescue());
//                    break;
//                case ActionRest:
//                    this.statusImage.setImage(storage.getImage_action_rest());
//                    break;
//                case ActionSearch:
//                    this.statusImage.setImage(storage.getImage_action_search());
//                    break;
//                case ActionUnload:
//                    this.statusImage.setImage(storage.getImage_action_unload());
//                    break;
//            }
//        }
//    }

    @Override
    protected boolean isTimeExist (int time) {
        if ( getClassType() == Type.Civilian ) {
            return this.hp.containsKey(time);
        } else {
            return this.action.containsKey(time) && this.hp.containsKey(time);
        }
    }

    public void setEntityID (int entityID) {
        this.entityID = entityID;
    }

    public void setX (int time, int x) {
        this.x.put(time, x);
    }

    public void setY (int time, int y) {
        this.y.put(time, y);
    }

    public void setHp (int time, int hp) {
        this.hp.put(time, hp);
    }

    public void setDamage (int time, int damage) {
        this.damage.put(time, damage);
    }

    public void setBuriedness (int time, int buriedness) {
        this.buriedness.put(time, buriedness);
    }

    public void setPosition (int time, int position) {
        this.position.put(time, position);
    }

    public void setStamina (int time, int stamina) {
        this.stamina.put(time, stamina);
    }

    public void setWater (int time, int water) {
        this.water.put(time, water);
    }

    public void setTimeStep (int time, int[] step) {
        this.step.put(time, new Step(step));
    }

    public void setReceivedMessage (int time, Message[] messages) {
        this.receivedMessage.put(time, messages);
    }

    public void setSendMessage (int time, Message[] messages) {
        this.sendMessage.put(time, messages);
    }


    public int getEntityID () {
        return entityID;
    }

    public int getDamage (int time) {
        return damage.get(time);
    }

    public int getBuriedness (int time) {
        return buriedness.get(time);
    }

    public int getHP (int time) {
        return hp.get(time);
    }

    public double getHP_Per (int time) {
        return (double) hp.get(time) / hp.get(0);
    }

    public int getWater (int time) {
        return water.get(time);
    }

    public double getWater_Per (int time) {
        return (double) water.get(time) / water.get(0);
    }

    public Message[] getReceivedMessage (int time) {
        return receivedMessage.get(time);
    }


    public int getX (int time) {
        return x.get(time);
    }

    public int getY (int time) {
        return y.get(time);
    }

    public statusURN[] getStatuses (int time) {
        List<statusURN> list = new ArrayList<>();
        if ( isTimeExist(time) ) {
            if ( getDamage(time) > 0 ) {
                list.add(statusURN.bleed);
            }
            if ( getBuriedness(time) > 0 ) {
                list.add(statusURN.buried);
            }
            if ( getClassType() == Type.FireBrigade && getWater(time) <= 500 ) {
                list.add(statusURN.water_deficit);
            }
			/*if(getSomeoneOnBoard(time)>=0) {
				list.add(statusURN.carry);
			}*/
        }
        return list.toArray(new statusURN[list.size()]);
    }

    public Step getStep (int time) {
        return step.get(time);
    }
}