package laucher;

import rabbitMQ.Server;

public class Laucher {

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server();
		server.connect();
		server.sendString("Ala ma kota");
		Thread.sleep(10000);
		server.disconnect();
	}

}
