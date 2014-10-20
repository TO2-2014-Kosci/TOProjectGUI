package launcher;

import message.JoinToGameMessage;
import rabbitMQ.ClientServerPrototype;

public class ClientLauncherPrototype {

	public static void main(String[] args) {
		ClientServerPrototype server = new ClientServerPrototype();
		server.connectToServerCreateChannelsAndConsume();
		server.sendMessage(new JoinToGameMessage("MarekGame"));
		if (server.receiveStringFromDirect().equalsIgnoreCase("OK")) {
			server.connectToGameChannelByName("MarekGame");
			System.out.println(server.receiveStringFromGame());
		};
	}

}
