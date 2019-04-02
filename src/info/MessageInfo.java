package info;


import entities.Entity;
import entities.Human;
import entities.Message;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashSet;

public class MessageInfo {
	
	
//	private WorldInfo worldInfo;
//	private HashSet<MessageLine> messageLines;
//
//
//	public MessageInfo(WorldInfo worldInfo) {
//		this.worldInfo=worldInfo;
//		worldInfo.setMessageInfo(this);
//		this.messageLines=new HashSet<>();
//	}
//
//	public void updateTimeStep(int time) {
//		messageLines.clear();
//		for(Human human:worldInfo.getHumanMap().values()) {
//			Message[] messages=human.getReceivedMessage(time);
//			if(messages!=null) {
//				for(Message message:messages) {
//					if(message.getReceiver()!=null&&message.getSender()!=null) {
//						MessageLine line=new MessageLine(message.getSender(), message.getReceiver(), null, time);
//						messageLines.add(line);
//					}
//				}
//			}
//		}
//	}
//
//	public HashSet<MessageLine> getMessageLines() {
//		return messageLines;
//	}

	
	
	
	
//	public class MessageLine {
//
//		private Human sender;
//		private Human receiver;
//		private Entity target;
//		private Group group;
//
//		public MessageLine(Human sender, Human receiver, Entity target, int time) {
//			this.sender=sender;
//			this.receiver=receiver;
//			group=new Group();
//			Line line=new Line(sender.getMapX(time), sender.getMapY(time), receiver.getMapX(time), receiver.getMapY(time));
//			line.setStroke(Color.AQUA);
//			group.getChildren().add(line);
//		}
//
//		public Node getLine() {
//			return group;
//		}
//
//	}
	
	
	
	
} 