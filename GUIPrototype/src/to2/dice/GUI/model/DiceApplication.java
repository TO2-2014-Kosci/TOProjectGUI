package to2.dice.GUI.model;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import to2.dice.GUI.views.View;


public class DiceApplication extends JFrame {
	private static final long serialVersionUID = 4487043914138417716L;
	private View view;
	
	public DiceApplication() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Koœci");
		setIconImage((new ImageIcon("kosc.png")).getImage());
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			   @Override
			   public void run() {
			    // TODO Auto-generated method stub
			    
			   }
			   
			  }));
	}
	
	
	public void refresh() {
		this.view.refresh();
		this.revalidate();
		//TODO
	}
	
	public void setView(View view) {
		this.view = view;
		this.setContentPane(view);
		this.setMinimumSize(view.getMinimumSize());
		setLocationRelativeTo(null);
		this.refresh();
		this.view.refresh();
	}
}
