package entities;

import entities.Category.Action;
import entities.Category.Message;


public class ActionMessage extends entities.Message {
	
	
	public ActionMessage(Message messageType, Human sender, Human receiver, Human agent, Action action, int buriedness, int damage, int hp, int position, int targetID, boolean isRadio, int size) {
		super(messageType, sender, receiver);
	}
	
	
	
}