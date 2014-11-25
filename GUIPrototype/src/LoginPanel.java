import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public LoginPanel(JFrame mainFrame){
		JTextField loginField= new JTextField();
//		Document doc = new PlainDocument();
//		doc.addDocumentListener(new DocumentListener() {
//			
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				try {
//					System.out.println(e.getDocument().getText(0, e.getDocument().getLength()));
//				} catch (BadLocationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//			
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				try {
//					System.out.println(e.getDocument().getText(0, e.getDocument().getLength()));
//				} catch (BadLocationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//			
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				try {
//					System.out.println(e.getDocument().getText(0, e.getDocument().getLength()));
//				} catch (BadLocationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		JTextField loginField= new JTextField(doc, "", 1);
		JLabel loginLabel= new JLabel("Podaj login");
		JButton loginButton= new JButton("Zaloguj");
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (loginField.getText().equals("")) {
					JOptionPane.showMessageDialog(loginField,"Nick zajêty lub niepoprawny","B³¹d logowania",JOptionPane.ERROR_MESSAGE);
					loginField.setText("");
					loginField.transferFocusDownCycle();
				} else {
					GameList gameList = new GameList(mainFrame);
					mainFrame.setContentPane(gameList);
					mainFrame.setMinimumSize(gameList.getMinimumSize());
					mainFrame.setLocationRelativeTo(null);
				}
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
