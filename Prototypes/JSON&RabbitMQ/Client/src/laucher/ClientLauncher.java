package laucher;

import rabbitMQ.Server;

public class ClientLauncher {

	public static void main(String[] args) {
		Server server = new Server();
		server.connectToServerCreateChannelAndConsume();
		server.createChannelForGameAndConsume();
		
		System.out.println();
		server.disconnect();
	}

}
