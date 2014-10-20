package message;

import org.json.*;

public abstract class Message {
	private String type;
	
	public Message(String type) {
		this.type = type;
	}
	
	@Override
	public abstract String toString();
	
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("type", type);
		return object;
	}
}
