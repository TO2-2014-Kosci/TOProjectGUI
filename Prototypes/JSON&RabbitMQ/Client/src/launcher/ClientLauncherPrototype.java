package launcher;

import rabbitMQ.ClientServerPrototype;

public class ClientLauncherPrototype {

	public static void main(String[] args) {
		ClientServerPrototype server = new ClientServerPrototype();
		server.connectToServerCreateChannelsAndConsume();
	}

}
