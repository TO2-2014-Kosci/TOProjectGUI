package to2.dice.GUI.views;

import javax.swing.JButton;
import javax.swing.JTable;

import to2.dice.GUI.model.Model;
import to2.dice.common.GameInfo;

public class GameListView extends View {
	private JButton refreshButton;
	private JButton createGameButton;
	private JButton joinGameButton;
	private JTable gameListTable;
	public GameListView(Model model) {
		super(model);
	}
	//TODO
	public GameInfo getSelectedGame(){
		return null;
	}

}
