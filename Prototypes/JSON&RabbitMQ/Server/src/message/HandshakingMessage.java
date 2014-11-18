package message;

import org.json.JSONObject;

public class HandshakingMessage extends Message {
	private String queueName;

	public HandshakingMessage(String queueName) {
		super("handshaking");
		this.queueName = queueName;
	}

	public HandshakingMessage(JSONObject object) {
		super("handshaking");
		this.queueName = object.getString("queueName");
	}
	
	@Override
	public String toString() {
		JSONObject object = super.toJSONObject();
		object.put("queueName", this.queueName);
		return object.toString();
	}
	
	public String getQueueName() {
		return this.queueName;
	}
	
}
