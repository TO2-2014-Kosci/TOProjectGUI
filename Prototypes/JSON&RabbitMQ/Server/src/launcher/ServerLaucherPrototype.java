package launcher;

import rabbitMQ.ServerServerPrototype;

public class ServerLaucherPrototype {

	public static void main(String[] args) {
		ServerServerPrototype server = new ServerServerPrototype();
		server.connectNormal();
		System.out.println(server.receiveStringFromHangshake());
		server.connectFanoutbyName("MarekGame");
		server.sendStringClient("OK");
		server.sendStringFanout("Witamy w grze MarekGame");

	}

}
