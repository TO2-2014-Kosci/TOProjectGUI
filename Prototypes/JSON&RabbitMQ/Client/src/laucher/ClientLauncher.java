package laucher;

import rabbitMQ.Server;

public class ClientLauncher {

	public static void main(String[] args) throws Exception{
		Server server = new Server();
		server.connectToServerCreateChannelAndConsume();
		server.createFanoutChannelAndConsume();
		server.createTopicChannelAndConsume("ala.ma.kota");
		Thread.sleep(10000);
		System.out.println("Nie œpie, czekam");
		System.out.println(server.receiveStringFromNormal());
		System.out.println(server.receiveStringFromFanout());
		System.out.println(server.receiveStringFromTopic());
	}

}
