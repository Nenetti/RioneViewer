package rione.viewer;


import adf.agent.action.ambulance.ActionLoad;
import adf.agent.action.ambulance.ActionRescue;
import adf.agent.action.ambulance.ActionUnload;
import adf.agent.action.common.ActionMove;
import adf.agent.action.common.ActionRest;
import adf.agent.action.fire.ActionExtinguish;
import adf.agent.action.fire.ActionRefill;
import adf.agent.action.police.ActionClear;
import adf.agent.communication.MessageManager;
import adf.agent.communication.standard.bundle.centralized.CommandAmbulance;
import adf.agent.communication.standard.bundle.centralized.CommandFire;
import adf.agent.communication.standard.bundle.centralized.CommandPolice;
import adf.agent.communication.standard.bundle.information.*;
import adf.agent.info.AgentInfo;
import adf.component.communication.CommunicationMessage;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.EntityID;
import rione.viewer.Category.Action;
import rione.viewer.Category.Message;
import rione.viewer.Category.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



/**
 * Tacticsからのみ呼び出すViewerの外部出力クラス
 */
public class TacticsExporter {

    private int time = 0;
    private int id;
    private Type type;
    private static String ACTION_PATH = "Source/%d/ac/%d.bin";
    private static String MESSAGE_PATH = "Source/%d/me/%d.bin";

    public TacticsExporter (AgentInfo agentInfo) {
        Utility.init();
        this.id = toID(agentInfo.getID());
        this.type = toType(agentInfo.me());
    }

    private void updateTime (AgentInfo agentInfo) {
        this.time = agentInfo.getTime();
    }

    public void export (AgentInfo agentInfo, MessageManager messageManager, adf.agent.action.Action action, EntityID targetEntity) {
        try {
            updateTime(agentInfo);
            exportActionInfo(agentInfo, action, targetEntity);
            exportMessageInfo(agentInfo, messageManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportActionInfo (AgentInfo agentInfo, adf.agent.action.Action action, EntityID detectorTarget) throws Exception {
        File file = Utility.createNewFile(String.format(ACTION_PATH, this.time, toID(agentInfo.getID())));
        BinaryWriter writer = new BinaryWriter(file, true);
        Action actionType = toAction(this.type, action);

        writer.write(this.id);
        writer.write(actionType.getID());
        writer.write(toID(detectorTarget));
        writer.write(toID(getSomeoneOnBoard(agentInfo)));
        writer.write(agentInfo.getThinkTimeMillis());
        writer.write(toArray(agentInfo.getChanged().getChangedEntities()));
        switch (actionType) {
            case ActionClear:
                ActionClear clear = (ActionClear) action;
                writer.write(toID(clear.getTarget()));
                writer.write(clear.getUseOldFunction());
                writer.write(clear.getPosX());
                writer.write(clear.getPosY());
                break;
            case ActionExtinguish:
                ActionExtinguish extinguish = (ActionExtinguish) action;
                writer.write(toID(extinguish.getTarget()));
                writer.write(extinguish.getPower());
                break;
            case ActionLoad:
                ActionLoad load = (ActionLoad) action;
                writer.write(toID(load.getTarget()));
                break;
            case ActionMove:
                ActionMove move = (ActionMove) action;
                writer.write(toArray(move.getPath()));
                writer.write(move.getUsePosition());
                writer.write(move.getPosX());
                writer.write(move.getPosY());
                break;
            case ActionRefill:
                break;
            case ActionRescue:
                ActionRescue rescue = (ActionRescue) action;
                writer.write(toID(rescue.getTarget()));
                break;
            case ActionRest:
                break;
            case ActionSearch:
                break;
            case ActionUnload:
                break;
        }
        writer.close();
    }

    public void exportMessageInfo (AgentInfo agentInfo, MessageManager messageManager) throws Exception {
        File file = Utility.createNewFile(String.format(MESSAGE_PATH, this.time, toID(agentInfo.getID())));
        BinaryWriter writer = new BinaryWriter(file);
        writer.write(this.id);
        writeMessage(messageManager.getReceivedMessageList(), writer);
        //writeMessage(messageManager.getSendMessageList(), writer);
        writer.close();
    }

    private void writeMessage (List<CommunicationMessage> list, BinaryWriter writer) throws Exception {
        if ( list != null && !list.isEmpty() ) {
            writer.write(list.size());
            for (CommunicationMessage communicationMessage : list) {
                Message messageType = toMessage(communicationMessage);
                if ( messageType != null ) {
                    writer.write(messageType.getID());
                    switch (messageType) {
                        case CommandAmbulance:
                            CommandAmbulance messageCA = (CommandAmbulance) communicationMessage;
                            writer.write(toID(messageCA.getSenderID()));
                            writer.write(toID(messageCA.getTargetID()));
                            writer.write(toID(messageCA.getToID()));
                            writer.write(messageCA.getAction());
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageCA.toByteArray()));
                            break;
                        case CommandFire:
                            CommandFire messageCF = (CommandFire) communicationMessage;
                            writer.write(toID(messageCF.getSenderID()));
                            writer.write(toID(messageCF.getTargetID()));
                            writer.write(toID(messageCF.getToID()));
                            writer.write(messageCF.getAction());
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageCF.toByteArray()));
                            break;
                        case CommandPolice:
                            CommandPolice messageCP = (CommandPolice) communicationMessage;
                            writer.write(toID(messageCP.getSenderID()));
                            writer.write(toID(messageCP.getTargetID()));
                            writer.write(toID(messageCP.getToID()));
                            writer.write(messageCP.getAction());
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageCP.toByteArray()));
                            break;
                        case MessageAmbulanceTeam:
                            MessageAmbulanceTeam messageAT = (MessageAmbulanceTeam) communicationMessage;
                            writer.write(toID(messageAT.getSenderID()));
                            writer.write(toID(messageAT.getAgentID()));
                            writer.write(messageAT.getAction());
                            writer.write(messageAT.getBuriedness());
                            writer.write(messageAT.getDamage());
                            writer.write(messageAT.getHP());
                            writer.write(toID(messageAT.getPosition()));
                            writer.write(toID(messageAT.getTargetID()));
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageAT.toByteArray()));
                            break;
                        case MessageBuilding:
                            MessageBuilding messageBu = (MessageBuilding) communicationMessage;
                            writer.write(toID(messageBu.getSenderID()));
                            writer.write(toID(messageBu.getBuildingID()));
                            writer.write(messageBu.getBrokenness());
                            writer.write(messageBu.getFieryness());
                            writer.write(messageBu.getTemperature());
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageBu.toByteArray()));
                            break;
                        case MessageCivilian:
                            MessageCivilian messageCiv = (MessageCivilian) communicationMessage;
                            writer.write(toID(messageCiv.getSenderID()));
                            writer.write(toID(messageCiv.getAgentID()));
                            writer.write(messageCiv.getBuriedness());
                            writer.write(messageCiv.getDamage());
                            writer.write(messageCiv.getHP());
                            writer.write(toID(messageCiv.getPosition()));
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageCiv.toByteArray()));
                            break;
                        case MessageFireBrigade:
                            MessageFireBrigade messageFB = (MessageFireBrigade) communicationMessage;
                            writer.write(toID(messageFB.getSenderID()));
                            writer.write(toID(messageFB.getAgentID()));
                            writer.write(messageFB.getAction());
                            writer.write(messageFB.getBuriedness());
                            writer.write(messageFB.getDamage());
                            writer.write(messageFB.getHP());
                            writer.write(toID(messageFB.getPosition()));
                            writer.write(toID(messageFB.getTargetID()));
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageFB.toByteArray()));
                            break;
                        case MessagePoliceForce:
                            MessagePoliceForce messagePF = (MessagePoliceForce) communicationMessage;
                            writer.write(toID(messagePF.getSenderID()));
                            writer.write(toID(messagePF.getAgentID()));
                            writer.write(messagePF.getAction());
                            writer.write(messagePF.getBuriedness());
                            writer.write(messagePF.getDamage());
                            writer.write(messagePF.getHP());
                            writer.write(toID(messagePF.getPosition()));
                            writer.write(toID(messagePF.getTargetID()));
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messagePF.toByteArray()));
                            break;
                        case MessageRoad:
                            MessageRoad messageRo = (MessageRoad) communicationMessage;
                            writer.write(toID(messageRo.getSenderID()));
                            writer.write(toID(messageRo.getBlockadeID()));
                            if ( toID(messageRo.getBlockadeID()) != -1 ) {
                                writer.write(messageRo.getBlockadeX());
                                writer.write(messageRo.getBlockadeY());
                            }
                            writer.write(toID(messageRo.getRoadID()));
                            writer.write(messageRo.getRepairCost());
                            writer.write(communicationMessage.isRadio());
                            writer.write(calcMessageSize(messageRo.toByteArray()));
                            break;
                    }
                }
                writer.flush();
            }
        } else {
            writer.write(0);
        }
    }

    private Message toMessage (CommunicationMessage communicationMessage) {
        if ( communicationMessage instanceof MessageAmbulanceTeam ) {
            return Message.MessageAmbulanceTeam;
        } else if ( communicationMessage instanceof MessageFireBrigade ) {
            return Message.MessageFireBrigade;
        } else if ( communicationMessage instanceof MessagePoliceForce ) {
            return Message.MessagePoliceForce;
        } else if ( communicationMessage instanceof MessageCivilian ) {
            return Message.MessageCivilian;
        } else if ( communicationMessage instanceof MessageBuilding ) {
            return Message.MessageBuilding;
        } else if ( communicationMessage instanceof MessageRoad ) {
            return Message.MessageRoad;
        } else if ( communicationMessage instanceof CommandAmbulance ) {
            return Message.CommandAmbulance;
        } else if ( communicationMessage instanceof CommandFire ) {
            return Message.CommandFire;
        } else if ( communicationMessage instanceof CommandPolice ) {
            return Message.CommandPolice;
        }
        return null;
    }

    private int calcMessageSize (byte[] array) {
        if ( array != null ) {
            return array.length;
        }
        return 0;
    }

    private Action toAction (Type type, adf.agent.action.Action action) {
        if ( action == null ) {
            return Action.ActionSearch;
        } else if ( action instanceof ActionMove ) {
            return Action.ActionMove;
        } else if ( action instanceof ActionRest ) {
            return Action.ActionRest;
        }
        switch (type) {
            case AmbulanceTeam:
                if ( action instanceof ActionRescue ) {
                    return Action.ActionRescue;
                } else if ( action instanceof ActionLoad ) {
                    return Action.ActionLoad;
                } else if ( action instanceof ActionUnload ) {
                    return Action.ActionUnload;
                }
                break;
            case FireBrigade:
                if ( action instanceof ActionExtinguish ) {
                    return Action.ActionExtinguish;
                } else if ( action instanceof ActionRefill ) {
                    return Action.ActionRefill;
                }
                break;
            case PoliceForce:
                if ( action instanceof ActionClear ) {
                    return Action.ActionClear;
                }
                break;
        }
        return Action.ActionRest;
    }

    private Type toType (StandardEntity entity) {
        if ( entity instanceof AmbulanceTeam ) {
            return Type.AmbulanceTeam;
        } else if ( entity instanceof FireBrigade ) {
            return Type.FireStation;
        } else if ( entity instanceof PoliceForce ) {
            return Type.PoliceForce;
        }
        return null;
    }

    private int[] toArray (List<EntityID> list) {
        List<EntityID> result = new ArrayList<>();
        if ( list != null && !list.isEmpty() ) {
            for (EntityID id : list) {
                if ( id != null ) {
                    result.add(id);
                }
            }
            int[] array = new int[result.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = toID(result.get(i));
            }
            if ( list.size() != result.size() ) {
                System.err.println("パスプラのリストに null が入っている");
            }
            return array;
        } else {
            return new int[0];
        }
    }

    private int[] toArray (Set<EntityID> list) {
        if ( list != null && !list.isEmpty() ) {
            int[] array = new int[list.size()];
            int i = 0;
            for (EntityID id : list) {
                array[i] = toID(id);
                i++;
            }
            return array;
        }
        return null;
    }

    private EntityID getSomeoneOnBoard (AgentInfo agentInfo) {
        Human human = agentInfo.someoneOnBoard();
        if ( human != null ) {
            return human.getID();
        } else {
            return null;
        }
    }

    private int toID (EntityID id) {
        if ( id != null ) {
            return id.getValue();
        } else {
            return -1;
        }
    }

}