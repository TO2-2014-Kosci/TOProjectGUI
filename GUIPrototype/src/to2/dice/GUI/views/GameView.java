package to2.dice.GUI.views;

import javax.swing.JButton;

import to2.dice.GUI.model.Model;

public class GameView extends View {
	private GameAnimation gameAnimation;
	private JButton rerollButton;
	private JButton standUpLeaveButton;
	
	public GameView(Model model, GameAnimation gameAnimation){
		super(model);
		this.gameAnimation = gameAnimation;
	}
}
