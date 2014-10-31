import java.awt.EventQueue;

import javax.swing.JFrame;

public class GUITest {
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				MainFrame mainFrame= new MainFrame();
				mainFrame.setVisible(true);
			}
		});
	}
}
