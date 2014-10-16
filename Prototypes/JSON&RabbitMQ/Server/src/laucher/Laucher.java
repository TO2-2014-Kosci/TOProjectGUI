package laucher;

import rabbitMQ.Server;

public class Laucher {

	public static void main(String[] args) {
		Server server = new Server();
		server.connect();
		server.sendString("Ala ma kota");
		server.disconnect();
	}

}
