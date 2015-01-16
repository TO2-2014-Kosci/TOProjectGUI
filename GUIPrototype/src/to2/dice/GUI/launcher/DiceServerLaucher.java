package to2.dice.GUI.launcher;

import to2.dice.server.Server;

public class DiceServerLaucher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
