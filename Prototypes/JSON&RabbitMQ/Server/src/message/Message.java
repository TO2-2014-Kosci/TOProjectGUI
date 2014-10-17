package message;

import org.json.*;

public abstract class Message {
	private String type;
	private Message message;
	
	public Message(String type, Message message) {
		this.type = type;
		this.message = message;
	}
	
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	@Override
	public String toString() {
		JSONObject object = new JSONObject();
		object.put("type", type);
		object.put("message", message.toString());
		System.out.println(object);
		return object.toString();
	}
}
