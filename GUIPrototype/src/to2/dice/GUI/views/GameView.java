package to2.dice.GUI.views;

import javax.swing.JButton;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.controllers.GameController;

public class GameView extends View {
	private static final long serialVersionUID = -1998878099465780349L;
	private GameAnimation gameAnimation;
	private JButton rerollButton;
	private JButton standUpLeaveButton;
	
	public GameView(Model model, GameController controller, GameAnimation gameAnimation){
		super(model, controller);
		this.gameAnimation = gameAnimation;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
