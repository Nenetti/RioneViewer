package entities;


public class BuildingMessage extends entities.Message {
	
	
	public BuildingMessage(Category.Message messageType, Human sender, Human receiver, Building building, int brokeness, int fieryness, int temperature, boolean isRadio, int size) {
		super(messageType, sender, receiver);
	}
	
	
	
}