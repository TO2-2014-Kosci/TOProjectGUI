

import to2.dice.server.Server;

public class DiceServerLaucher {

	public static void main(String[] args) {
		new Server();
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
