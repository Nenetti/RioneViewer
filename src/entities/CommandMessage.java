package entities;

import entities.Category.Message;


public class CommandMessage extends entities.Message {
	
	
	public CommandMessage(Message messageType, Human sender, Human receiver, int targetID, Human toHuman, Category.Action action, boolean isRadio, int size) {
		super(messageType, sender, receiver);
	}
	
	
	
}