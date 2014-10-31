import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class CreateGameView extends JPanel {

	private static final long serialVersionUID = 6952287105002870473L;
	
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT= 300;

	public CreateGameView() {
		super();
		this.setLayout(new MigLayout("", "[grow][100:100:250]", ""));
		this.add(new JLabel("Tworzenie gry"), "cell 0 0 2 1,alignx center");
		this.add(new JLabel("Typ gry"), "cell 0 1,alignx left");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(GameType.values()));
		add(comboBox, "cell 1 1,growx");
		this.add(new JLabel("Iloœæ ¿ywych graczy"), "cell 0 2, left");
		
		JSpinner spinner_3 = new JSpinner();
		add(spinner_3, "cell 1 2,growx,aligny center");
		this.add(new JLabel("Iloœæ s³abych botów"), "cell 0 3, left");
		
		JSpinner spinner_2 = new JSpinner();
		add(spinner_2, "cell 1 3,growx,aligny center");
		this.add(new JLabel("Iloœæ silnych botów"), "cell 0 4, left");
		
		JSpinner spinner_1 = new JSpinner();
		add(spinner_1, "cell 1 4,growx,aligny center");
		this.add(new JLabel("Iloœæ wygranych rund"), "cell 0 5, left");
		
		JSpinner spinner = new JSpinner();
		add(spinner, "cell 1 5,growx,aligny center");
		this.add(new JLabel("Tylko AI (ogl¹daj grê)"), "cell 0 6, left");
		
		JCheckBox tylkoAIcheckButton = new JCheckBox("");
		add(tylkoAIcheckButton, "cell 1 6,alignx center,aligny center");
		this.add(new JButton("Stwórz grê"), "cell 1 10, left, w 100!");
		this.add(new JButton("WyjdŸ"), "cell 0 10, right, w 100!");
		
		
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
}
