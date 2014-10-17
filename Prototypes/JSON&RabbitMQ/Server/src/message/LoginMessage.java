package message;

import org.json.*;

public class LoginMessage implements Message {
	private String login;
	
	public LoginMessage(String login) {
		this.login = login;
	}
	
	@Override
	public byte[] getBytes() {
		JSONObject obj; 
		return null;
	}

}
