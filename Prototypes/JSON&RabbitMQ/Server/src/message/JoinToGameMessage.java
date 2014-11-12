package message;

import org.json.JSONObject;

public class JoinToGameMessage extends Message {
	private String gameName;
	
	public JoinToGameMessage(String gameName) {
		super("joinToGame");
		this.gameName = gameName;
		// TODO Auto-generated constructor stub
	}
	
	public JoinToGameMessage(JSONObject object) {
		super("joinToGame");
		this.gameName = object.getString("gameName");
	}

	@Override
	public String toString() {
		JSONObject object = super.toJSONObject();
		object.put("gameName", gameName);
		return object.toString();
	}

	public String getGameName() {
		return gameName;
	}
}