package message;

import org.json.*;

public abstract class Message {
	private String type;
	
	public Message(String type) {
		this.type = type;
	}
	
	public Message(JSONObject object) {
		this.type = object.getString("type");
	}
	
	@Override
	public abstract String toString();
	
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	public final JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("type", type);
		return object;
	}
}
