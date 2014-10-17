package laucher;

import org.json.JSONObject;

import message.LoginMessage;
import rabbitMQ.Server;

public class ClientLauncher {

	public static void main(String[] args) throws Exception{
		Server server = new Server();
		server.connectToServerCreateChannelAndConsume();
		server.createFanoutChannelAndConsume();
		server.createTopicChannelAndConsume("ala.*.*");
		while (true) {
		//System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromNormal())).getLogin());
		//System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromFanout())).getLogin());
		System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromTopic())).getLogin());
		}
	}
}