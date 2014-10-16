package laucher;

import rabbitMQ.Server;

public class Laucher {

	public static void main(String[] args) {
		Server server = new Server();
		server.connect();
		String msg = server.receiveString();
		System.out.println(msg);
		server.disconnect();
	}

}
