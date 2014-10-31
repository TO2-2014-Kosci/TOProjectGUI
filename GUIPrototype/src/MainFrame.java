import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT= 200;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainFrame(){
		setLayout(new BorderLayout());
		//setSize(600, 600);
		//setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		
		//LoginPanel loginPanel= new LoginPanel();
		//add(loginPanel,BorderLayout.CENTER);
		//setMinimumSize(loginPanel.getMinimumSize());

		GameList gameList= new GameList();
		add(gameList,BorderLayout.CENTER);
		setMinimumSize(gameList.getMinimumSize());

		
		//RoomView roomView= new RoomView();
		//add(roomView,BorderLayout.CENTER);
		//setMinimumSize(roomView.getMinimumSize());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Koœci");
		setIconImage((new ImageIcon("kosc.png")).getImage());
		//pack();
	}
}
