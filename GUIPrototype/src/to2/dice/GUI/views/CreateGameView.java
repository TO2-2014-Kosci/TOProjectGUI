package to2.dice.GUI.views;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import to2.dice.GUI.model.Model;
import to2.dice.game.GameSettings;

public class CreateGameView extends View {
	private JButton createGameButton;
	private JButton returnButton;
	private JTextField nameField;
	//TODO
	private JComboBox gameTypeComboBox;
	private JSpinner maxHumanPlayerSpinner;
	private JSpinner timeForMoveSpinner;
	private JSpinner maxInactiveTurnsSpinner;
	private JSpinner roundsToWinSpinner;
	private JSpinner[] botsNumberSpinner;
	
	
	
	public CreateGameView(Model model) {
		super(model);
	}
	//TODO
	public GameSettings getGameSettings(){
		return null;
	}
}
