package to2.dice.GUI.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import to2.dice.GUI.controllers.CreateGameController;
import to2.dice.GUI.model.Model;
import to2.dice.game.GameSettings;
import to2.dice.game.GameType;

public class CreateGameView extends View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601683176423998974L;
	private JButton createGameButton;
	private JButton returnButton;
	private JTextField nameField;
	//TODO
	private JComboBox gameTypeComboBox;
	private JSpinner maxHumanPlayersSpinner;
	private JSpinner timeForMoveSpinner;
	private JSpinner maxInactiveTurnsSpinner;
	private JSpinner roundsToWinSpinner;
	private JSpinner[] botsNumberSpinner;
	
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT= 300;

	
	public CreateGameView(Model model, CreateGameController controller) {
		super(model, controller);
		this.setLayout(new MigLayout("", "[grow][100:100:250]", ""));
		this.add(new JLabel("Tworzenie gry"), "cell 0 0 2 1,alignx center");
		
		this.add(new JLabel("Typ gry"), "cell 0 1,alignx left");	
		gameTypeComboBox = new JComboBox();
		gameTypeComboBox.setModel(new DefaultComboBoxModel(GameType.values()));
		add(gameTypeComboBox, "cell 1 1,growx");
		
		this.add(new JLabel("Iloœæ ¿ywych graczy"), "cell 0 2, left");			
		maxHumanPlayersSpinner = new JSpinner(new SpinnerNumberModel(1,0,null,1));
		((DefaultEditor) maxHumanPlayersSpinner.getEditor()).getTextField().setEditable(false);
		add(maxHumanPlayersSpinner, "cell 1 2,growx,aligny center");
		
		this.add(new JLabel("Iloœæ s³abych botów"), "cell 0 3, left");		
		JSpinner spinner_2 = new JSpinner(new SpinnerNumberModel(0,0,null,1));
		((DefaultEditor) spinner_2.getEditor()).getTextField().setEditable(false);
		add(spinner_2, "cell 1 3,growx,aligny center");
		
		this.add(new JLabel("Iloœæ silnych botów"), "cell 0 4, left");		
		JSpinner spinner_1 = new JSpinner(new SpinnerNumberModel(0,0,null,1));
		((DefaultEditor) spinner_1.getEditor()).getTextField().setEditable(false);
		add(spinner_1, "cell 1 4,growx,aligny center");
		
		this.add(new JLabel("Iloœæ wygranych rund"), "cell 0 5, left");		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(3,1,null,1));
		((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
		add(spinner, "cell 1 5,growx,aligny center");
		
		//init value, minimum,maximum,step
		JSpinner spinner_4 = new JSpinner(new SpinnerNumberModel(30,1,null,1));
		((DefaultEditor) spinner_4.getEditor()).getTextField().setEditable(false);
		add(spinner_4, "cell 1 6,growx,aligny center");
		this.add(new JLabel("Czas tury"), "cell 0 6, left");	
				
		JSpinner spinner_5 = new JSpinner();
		((DefaultEditor) spinner_5.getEditor()).getTextField().setEditable(false);
		add(spinner_5, "cell 1 7,growx,aligny center");
		this.add(new JLabel("Max. nieaktywnoœci"), "cell 0 7, left");
		
		JCheckBox ActivityRestrictionBox = new JCheckBox("");
		add(ActivityRestrictionBox, "cell 1 7,alignx center,aligny center");
		ActivityRestrictionBox.setSelected(true);
		ActivityRestrictionBox.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(ActivityRestrictionBox.isSelected()){
					spinner_5.setEnabled(true);
				}
				else{
					spinner_5.setEnabled(false);
				}
				
			}
			
			
		});
		
		
		
		JButton create = new JButton("Stwórz grê");
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomView roomView = new RoomView(mainFrame);
				mainFrame.setContentPane(roomView);
				mainFrame.setMinimumSize(roomView.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				roomView.revalidate();
			}
		});
		this.add(create, "cell 1 10, left, w 100!");
		JButton back = new JButton("WyjdŸ");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameList gameList = new GameList(mainFrame);
				mainFrame.setContentPane(gameList);
				mainFrame.setMinimumSize(gameList.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.revalidate();
			}
		});
		this.add(back, "cell 0 10, right, w 100!");
		
		
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		
	}
	//TODO
	public GameSettings getGameSettings(){
		return null;
	}
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
