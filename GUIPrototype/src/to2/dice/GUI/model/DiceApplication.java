package to2.dice.GUI.model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import to2.dice.GUI.views.View;

public class DiceApplication extends JFrame {
	private static final long serialVersionUID = 4487043914138417716L;
	private View view;

	public DiceApplication() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame) e.getSource();

				int result = JOptionPane.showConfirmDialog(frame, "Czy na pewno chcesz zamkn¹æ aplikacje?",
						"Zamykanie aplikacji", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					System.exit(0);
				}
			}
		});

		setTitle("Koœci");
		setIconImage((new ImageIcon("kosc.png")).getImage());

	}

	public void refresh() {
		this.view.refresh();
		this.revalidate();
		// TODO
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
