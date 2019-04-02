package entities;

import entities.Category.Message;


public class RoadMessage extends entities.Message {
	
	
	public RoadMessage(Message messageType, Human sender, Human receiver, Blockade blockade, int blockadeX, int blockadeY, Road road, int repairCost, boolean isRadio, int size) {
		super(messageType, sender ,receiver);
	}
	
	
	
}