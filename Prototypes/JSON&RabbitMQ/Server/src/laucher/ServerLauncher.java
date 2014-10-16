package laucher;

import rabbitMQ.Server;

public class ServerLauncher {

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server();
		server.connect();
		server.sendString("Ala ma kota");
		Thread.sleep(10000);
		server.disconnect();
	}

}
