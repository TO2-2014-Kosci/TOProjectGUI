package message;

import org.json.*;


public class LoginMessage extends Message {
	private String login;
	
	public LoginMessage(String login) {
		super("login");
		this.login = login;
	}
	
	public LoginMessage(JSONObject object) {
		super("login");
		this.login = object.getString("login");
	}
	
	@Override
	public String toString() {
		JSONObject object = super.toJSONObject();
		object.put("login", login);
		return object.toString();
	}

	public String getLogin() {
		return login;
	}
}