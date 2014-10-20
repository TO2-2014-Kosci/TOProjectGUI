package launcher;

import rabbitMQ.ClientServer;

public class ClientLauncherPrototype {

	public static void main(String[] args) {
		ClientServer server = new ClientServer();
		server.connectToServerCreateChannelAndConsume();
	}

}
