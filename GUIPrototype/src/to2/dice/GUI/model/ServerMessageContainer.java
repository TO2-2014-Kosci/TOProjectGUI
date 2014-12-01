package to2.dice.GUI.model;

import to2.dice.game.GameState;
import to2.dice.server.ServerMessageListener;

public class ServerMessageContainer implements ServerMessageListener {
	private ServerMessageListener serverMessageListener;
	
	
	@Override
	public void onGameStateChange(GameState arg0) {
		if (this.serverMessageListener != null) {
			this.serverMessageListener.onGameStateChange(arg0);
		}
	}
	
	public void setServerMessageListener(ServerMessageListener sml) {
		this.serverMessageListener = sml;
	}
	
	public void removeServerMessageListener() {
		this.serverMessageListener = null;
	}
}
