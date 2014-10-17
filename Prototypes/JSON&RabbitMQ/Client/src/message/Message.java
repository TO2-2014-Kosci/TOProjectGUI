package message;

import org.json.*;

public class Message {
	private String type;
	
	public Message(String type) {
		this.type = type;
	}
	
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("type", type);
		return object;
	}
}
