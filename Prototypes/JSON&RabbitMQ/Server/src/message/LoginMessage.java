package message;

import org.json.*;

public class LoginMessage{
	private String login;
	
	public LoginMessage(String login) {
		this.login = login;
	}
	
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	@Override
	public String toString() {
		JSONObject object = new JSONObject();
		object.put("login", login);
		return object.toString();
	}

}
