package entities;


import entities.Category.Action;
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
    private HashMap<Integer, Integer> detectorTarget = new HashMap<>();
    private HashMap<Integer, Integer> someoneOnBoard = new HashMap<>();
    private HashMap<Integer, Long> thinkTime = new HashMap<>();
    private HashMap<Integer, int[]> changedEntities = new HashMap<>();
    private HashMap<Integer, Integer> clear_Target = new HashMap<>();
    private HashMap<Integer, Boolean> clear_UseOldFunction = new HashMap<>();
    private HashMap<Integer, Integer> clear_X = new HashMap<>();
    private HashMap<Integer, Integer> clear_Y = new HashMap<>();
    private HashMap<Integer, Integer> extinguish_Target = new HashMap<>();
    private HashMap<Integer, Integer> extinguish_power = new HashMap<>();
    private HashMap<Integer, Integer> load_Target = new HashMap<>();
    private HashMap<Integer, Area[]> move_path = new HashMap<>();
    private HashMap<Integer, Boolean> move_usePosition = new HashMap<>();
    private HashMap<Integer, Integer> move_X = new HashMap<>();
    private HashMap<Integer, Integer> move_Y = new HashMap<>();
    private HashMap<Integer, Integer> rescue_Target = new HashMap<>();

    private HashMap<Integer, Message[]> receivedMessage = new HashMap<>();
    private HashMap<Integer, Message[]> sendMessage = new HashMap<>();
    private HashMap<Integer, Step> step = new HashMap<>();


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

    public void updateSource (Category.Action actionType, int detectorTarget, int someoneOnBoard, long thinkTime, int[] changedEntities, int clear_Target,
                              boolean clear_UseOldFunction, int clear_X, int clear_Y, int extinguish_Target, int extinguish_power, int load_Target, Area[] move_path,
                              boolean move_usePosition, int move_X, int move_Y, int rescue_Target, int time) {

        setAction(time, actionType);
        setDetectorTarget(time, detectorTarget);
        setSomeoneOnBoard(time, someoneOnBoard);
        setThinkTime(time, thinkTime);
        setChangedEntities(time, changedEntities);
        switch (actionType) {
            case ActionClear:
                setClear_Target(time, clear_Target);
                setClear_UseOldFunction(time, clear_UseOldFunction);
                setClear_X(time, clear_X);
                setClear_Y(time, clear_Y);
                break;
            case ActionExtinguish:
                setExtinguish_Target(time, extinguish_Target);
                setExtinguish_power(time, extinguish_power);
                break;
            case ActionLoad:
                setLoad_Target(time, load_Target);
                break;
            case ActionMove:
                setMove_path(time, move_path);
                setMove_usePosition(time, move_usePosition);
                setMove_X(time, move_X);
                setMove_Y(time, move_Y);
                break;
            case ActionRefill:
                break;
            case ActionRescue:
                setRescue_Target(time, rescue_Target);
                break;
            case ActionRest:
                break;
            case ActionSearch:
                break;
            case ActionUnload:
                break;
        }
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

    public void setAction (int time, Category.Action action) {
        this.action.put(time, action);
    }

    public void setDetectorTarget (int time, int detectorTarget) {
        this.detectorTarget.put(time, detectorTarget);
    }

    public void setSomeoneOnBoard (int time, int someoneOnBoard) {
        this.someoneOnBoard.put(time, someoneOnBoard);
    }

    public void setThinkTime (int time, long thinkTime) {
        this.thinkTime.put(time, thinkTime);
    }

    public void setChangedEntities (int time, int[] changedEntities) {
        this.changedEntities.put(time, changedEntities);
    }

    public void setClear_Target (int time, int clear_Target) {
        this.clear_Target.put(time, clear_Target);
    }

    public void setClear_UseOldFunction (int time, boolean clear_UseOldFunction) {
        this.clear_UseOldFunction.put(time, clear_UseOldFunction);
    }

    public void setClear_X (int time, int clear_X) {
        this.clear_X.put(time, clear_X);
    }

    public void setClear_Y (int time, int clear_Y) {
        this.clear_Y.put(time, clear_Y);
    }

    public void setExtinguish_Target (int time, int extinguish_Target) {
        this.extinguish_Target.put(time, extinguish_Target);
    }

    public void setExtinguish_power (int time, int extinguish_power) {
        this.extinguish_power.put(time, extinguish_power);
    }

    public void setLoad_Target (int time, int load_Target) {
        this.load_Target.put(time, load_Target);
    }

    public void setMove_path (int time, Area[] move_path) {
        this.move_path.put(time, move_path);
    }

    public void setMove_usePosition (int time, boolean move_usePosition) {
        this.move_usePosition.put(time, move_usePosition);
    }

    public void setMove_X (int time, int move_X) {
        this.move_X.put(time, move_X);
    }

    public void setMove_Y (int time, int move_Y) {
        this.move_Y.put(time, move_Y);
    }

    public void setRescue_Target (int time, int rescue_Target) {
        this.rescue_Target.put(time, rescue_Target);
    }


    /*****************************************************************************/


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

    public int getSomeoneOnBoard (int time) {
        return someoneOnBoard.get(time);
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

    public Category.Action getAction (int time) {
        return action.get(time);
    }

    public Area[] getMove_path (int time) {
        return move_path.get(time);
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