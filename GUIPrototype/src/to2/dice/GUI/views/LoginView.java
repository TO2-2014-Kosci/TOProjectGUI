package to2.dice.GUI.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.Model;

public class LoginView extends View {
	
	private JButton loginButton;
	private JTextField loginField;
	private JLabel loginLabel;
	//loginLabel ?
	
	//
	public LoginView(Model model) {
		super(model);
		LoginController lc = (LoginController) controller;
		
		loginLabel= new JLabel("Podaj login");
		loginButton = new JButton("Zaloguj");
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				lc.clickedLoginButton();
			}
		});
		
		setLayout(new GridBagLayout());
		GridBagConstraints loginFieldConstraints = new GridBagConstraints();
		loginFieldConstraints.weightx = 0;
		loginFieldConstraints.weighty = 0;
		loginFieldConstraints.gridx=0;
		loginFieldConstraints.gridy=1;
		loginFieldConstraints.gridwidth=2;
		loginFieldConstraints.gridheight=1;
		
		loginField.setPreferredSize(new Dimension(200,25));
		
		add(loginField,loginFieldConstraints);
		GridBagConstraints loginButtonConstraints = new GridBagConstraints();
		
		loginButtonConstraints.weightx = 0;
		loginButtonConstraints.weighty = 0;
		loginButtonConstraints.gridx=1;
		loginButtonConstraints.gridy=2;
		loginButtonConstraints.gridwidth=1;
		loginButtonConstraints.gridheight=1;
		
		loginButtonConstraints.anchor= GridBagConstraints.NORTHEAST;

		add(loginButton,loginButtonConstraints);
		
		GridBagConstraints loginLabelConstraints = new GridBagConstraints();
		loginLabelConstraints.weightx = 0;
		loginLabelConstraints.weighty = 0;
		loginLabelConstraints.gridx=0;
		loginLabelConstraints.gridy=0;
		loginLabelConstraints.gridwidth=1;
		loginLabelConstraints.gridheight=1;
		add(loginLabel,loginLabelConstraints);
	}

	public String getLogin(){
		return loginField.getText();
	}
	
	public void eraseLogin(){
		loginField.setText("");
	}
}
