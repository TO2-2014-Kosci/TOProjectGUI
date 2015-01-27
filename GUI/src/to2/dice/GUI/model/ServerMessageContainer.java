package to2.dice.GUI.model;

import to2.dice.game.GameState;
import to2.dice.server.ServerMessageListener;

public class ServerMessageContainer implements ServerMessageListener {
	private ServerMessageListener serverMessageListener;

	@Override
	public void onGameStateChange(GameState arg0) {
		// try {
		if (serverMessageListener != null) {
			serverMessageListener.onGameStateChange(arg0);
		}
		// } catch (NullPointerException e) {
		// e.printStackTrace();
		// }
	}

	public void setServerMessageListener(ServerMessageListener sml) {
		serverMessageListener = sml;
	}

	public void removeServerMessageListener() {
		serverMessageListener = null;
	}
}
