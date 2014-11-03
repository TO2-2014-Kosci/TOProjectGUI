import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public LoginPanel(){
		JTextField loginField= new JTextField();
		JLabel loginLabel= new JLabel("Podaj login");
		JButton loginButton= new JButton("Zaloguj");
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//System.out.println(loginField.getText());
				JOptionPane.showMessageDialog(loginField,"Nick zajêty lub niepoprawny","B³¹d logowania",JOptionPane.ERROR_MESSAGE);
				loginField.setText("");
				loginField.transferFocusDownCycle();
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
		//loginFieldConstraints.fill= GridBagConstraints.BOTH;
		loginField.setPreferredSize(new Dimension(200,25));
		//loginFieldConstraints.ipadx=200;
		add(loginField,loginFieldConstraints);
		GridBagConstraints loginButtonConstraints = new GridBagConstraints();
		
		loginButtonConstraints.weightx = 0;
		loginButtonConstraints.weighty = 0;
		loginButtonConstraints.gridx=1;
		loginButtonConstraints.gridy=2;
		loginButtonConstraints.gridwidth=1;
		loginButtonConstraints.gridheight=1;
		//loginButtonConstraints.fill= GridBagConstraints.BOTH;
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

		setMinimumSize(new Dimension(250, 120));
		
		
		//add(loginField, BorderLayout.CENTER);
		//add(loginLabel,BorderLayout.NORTH);
		//add(loginButton,BorderLayout.SOUTH);

		
		//loginField.setMaximumSize(new Dimension(100, 30));
		//setMaximumSize(new Dimension(200, 60));
	}

}
