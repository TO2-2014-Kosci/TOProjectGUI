package to2.dice.GUI.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.Model;

public class LoginView extends View {
	private static final long serialVersionUID = 5539852546355993063L;
	
	private JButton loginButton;
	private JTextField loginField;
	private JLabel loginLabel;
	//loginLabel ?
	
	//
	public LoginView(Model model, LoginController controller) {
		super(model, controller);
		MigLayout mig = new MigLayout(
				"al center center",
				"",
				"");
		this.setLayout(mig);
		loginLabel= new JLabel("Podaj login");
		loginButton = new JButton("Zaloguj");
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.clickedLoginButton();
			}
		});
		loginField = new JTextField();
		loginField.setPreferredSize(new Dimension(200,25));
		
		add(loginField,"cell 0 1, center, growy");
		add(loginButton,"cell 0 2, right");
		add(loginLabel,"cell 0 0, left");
		setMinimumSize(new Dimension(250, 120));
	}

	public String getLogin(){
		return loginField.getText();
	}
	
	public void eraseLogin(){
		loginField.setText("");
	}
}
