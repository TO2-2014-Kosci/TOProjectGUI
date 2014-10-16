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
		System.out.println(server.receiveStringFromNormal() + "dupa1");
		System.out.println(server.receiveStringFromFanout() + "dupa2");
		System.out.println(server.receiveStringFromTopic() + "dupa3");
		System.out.println("Nie œpie, czekam");
	}

}
