package launcher;

import java.util.Scanner;

import org.json.JSONObject;

import message.LoginMessage;
import rabbitMQ.ClientServer;

public class ClientLauncher {

	public static void main(String[] args) throws Exception{
		Scanner sc= new Scanner(System.in);
		ClientServer server = new ClientServer();
		server.connectToServerCreateChannelAndConsume();
		server.createFanoutChannelAndConsume();
		while(true){
			System.out.println("Wybierz typ klienta 1- normal, 2- fanout, 3-topic");
			int temp=sc.nextInt();
			switch(temp){	
				case 1:
					while (true) {
						System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromNormal())).getLogin());
					}
				case 2:
					while (true) {
						System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromFanout())).getLogin());
					}		
				case 3:
					System.out.println("Podaj klucz");
					sc.nextLine();
					String topic=sc.nextLine();
					server.createTopicChannelAndConsume(topic);
					while (true) {
						System.out.println(new LoginMessage(new JSONObject(server.receiveStringFromTopic())).getLogin());
					}
				default:
					sc.close();
					System.exit(0);
				break;
			}
		}
	}
}