package to2.dice.GUI.views;

import javax.swing.JButton;
import javax.swing.JTable;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.controllers.GameListController;
import to2.dice.GUI.model.Model;
import to2.dice.game.GameInfo;

public class GameListView extends View {
	private JButton refreshButton;
	private JButton createGameButton;
	private JButton joinGameButton;
	private JTable gameListTable;
	public GameListView(Model model, GameListController controller) {
		super(model, controller);
	}
	//TODO
	public GameInfo getSelectedGame(){
		return null;
	}

}
