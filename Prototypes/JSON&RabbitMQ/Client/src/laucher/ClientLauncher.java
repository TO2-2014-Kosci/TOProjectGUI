package laucher;

import rabbitMQ.Server;

public class ClientLauncher {

	public static void main(String[] args) {
		Server server = new Server();
		server.connect();
		String msg = server.receiveString();
		System.out.println(msg);
		server.disconnect();
	}

}
