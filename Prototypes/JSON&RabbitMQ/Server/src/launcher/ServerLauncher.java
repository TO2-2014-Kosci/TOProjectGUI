package launcher;

import java.util.Scanner;
import message.LoginMessage;
import rabbitMQ.ServerServer;

public class ServerLauncher {

	public static void main(String[] args) throws InterruptedException {
		ServerServer server = new ServerServer();
		server.connectNormal();
		server.connectFanout();
		server.connectTopic();
		Scanner sc= new Scanner(System.in);
		String message = new LoginMessage("Ala ma kota").toString();
		String topic;
		while(true){
			System.out.println("Wybierz typ wiadomosci,0-koniec 1- normal, 2- fanout, 3-topic");
			int temp=sc.nextInt();
			if(temp==0){
				server.disconnect();
				System.exit(0);
				sc.close();
			}			switch(temp){	
			case 1:
				server.sendString(message);
				//System.out.println(server.receiveString());
			break;
			case 2:
				server.sendStringFanout(message);		
			break;
			case 3:
				System.out.println("Podaj klucz");
				sc.nextLine();
				topic=sc.nextLine();
				System.out.println(topic);
				server.sendStringTopic(message,topic);
			break;
			default:
				server.disconnect();
				System.exit(0);
				sc.close();
			}
		}
	}

}
