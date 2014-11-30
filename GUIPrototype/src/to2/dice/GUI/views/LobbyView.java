package to2.dice.GUI.views;

import javax.swing.JButton;
import javax.swing.JLabel;

import to2.dice.GUI.controllers.LobbyController;
import to2.dice.GUI.model.Model;

public class LobbyView extends View {
	private JButton sitDownStandUpButton;
	private JButton leaveButton;
	private JLabel playersCountLabel;
	
	public LobbyView(Model model, LobbyController controller) {
		super(model, controller);
	}

}
