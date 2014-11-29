package to2.dice.GUI.views;

import javax.swing.JButton;
import javax.swing.JTextField;

import to2.dice.GUI.model.Model;

public class LoginView extends View {
	
	private JButton loginButton;
	private JTextField loginField;
	
	public LoginView(Model model) {
		super(model);
	}

	public String getLogin(){
		return loginField.getText();
	}
}
