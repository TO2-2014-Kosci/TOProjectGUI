package laucher;

import rabbitMQ.ClientServer;

public class ClientLaucherPrototype {

	public static void main(String[] args) {
		ClientServer server = new ClientServer();
		server.connectToServerCreateChannelAndConsume();
	}

}
