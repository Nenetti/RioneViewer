package entities;


public abstract class Message{
	
	private Category.Message messageType;
	private Human sender;
	private Human receiver;
	
	public Message(Category.Message messageType, Human sender, Human receiver) {
		this.messageType=messageType;
		this.sender=sender;
		this.receiver=receiver;
	}
	
	public Category.Message getMessageType() {
		return messageType;
	}
	public Human getSender() {
		return sender;
	}
	public Human getReceiver() {
		return receiver;
	}
	
}