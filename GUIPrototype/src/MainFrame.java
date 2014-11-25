
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT= 200;
	private static final long serialVersionUID = 1L;
	
	public MainFrame(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginPanel loginPanel= new LoginPanel(this);
		setContentPane(loginPanel);
		setMinimumSize(loginPanel.getMinimumSize());
//		GameView gameView= new GameView(this);
//		setContentPane(gameView);
//		setMinimumSize(gameView.getMinimumSize());
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Koœci");
		setIconImage((new ImageIcon("kosc.png")).getImage());
		//pack();
	}
}
