package to2.dice.GUI.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
import to2.dice.game.BotLevel;
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
	private JComboBox<GameType> gameTypeComboBox;
	private JSpinner maxHumanPlayersSpinner;
	private JSpinner timeForMoveSpinner;
	private JSpinner maxInactiveTurnsSpinner;
	private JSpinner roundsToWinSpinner;
	private JSpinner[] botsNumberSpinner;

	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 310;

	public CreateGameView(Model model, CreateGameController controller) {
		super(model, controller);
		this.setLayout(new MigLayout("", "[][grow][100:100:250]", "[][][][][][][][][][][][]"));
		this.add(new JLabel("Tworzenie gry"), "cell 0 0 3 1,alignx center");

		this.add(new JLabel("Nazwa gry"), "cell 0 1,alignx left");
		nameField = new JTextField(model.getLogin());
		nameField.setHorizontalAlignment(JTextField.RIGHT);
		add(nameField, "cell 1 1 2 1,growx");

		this.add(new JLabel("Typ gry"), "cell 0 2,alignx left");
		gameTypeComboBox = new JComboBox<>();
		gameTypeComboBox.setModel(new DefaultComboBoxModel<>(GameType.values()));
		add(gameTypeComboBox, "cell 2 2,growx");

		this.add(new JLabel("Iloœæ wygranych rund"), "cell 0 3,alignx left");
		roundsToWinSpinner = new JSpinner(new SpinnerNumberModel(3, 1, null, 1));
		((DefaultEditor) roundsToWinSpinner.getEditor()).getTextField().setEditable(false);
		add(roundsToWinSpinner, "cell 2 3,growx,aligny center");

		timeForMoveSpinner = new JSpinner(new SpinnerNumberModel(30, 1, null, 1));
		((DefaultEditor) timeForMoveSpinner.getEditor()).getTextField().setEditable(false);
		add(timeForMoveSpinner, "cell 2 4,growx,aligny center");
		this.add(new JLabel("Czas tury"), "cell 0 4,alignx left");

		maxInactiveTurnsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, null, 1));
		((DefaultEditor) maxInactiveTurnsSpinner.getEditor()).getTextField().setEditable(false);
		add(maxInactiveTurnsSpinner, "cell 2 5,growx,aligny center");
		this.add(new JLabel("Max. nieaktywnoœci"), "cell 0 5,alignx left");
		JCheckBox ActivityRestrictionBox = new JCheckBox("");
		add(ActivityRestrictionBox, "cell 2 5,alignx center,aligny center");
		ActivityRestrictionBox.setSelected(true);
		ActivityRestrictionBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (ActivityRestrictionBox.isSelected()) {
					maxInactiveTurnsSpinner.setEnabled(true);
				} else {
					maxInactiveTurnsSpinner.setEnabled(false);
				}
			}
		});

		this.add(new JLabel("Iloœæ ¿ywych graczy"), "cell 0 6,alignx left");
		maxHumanPlayersSpinner = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		((DefaultEditor) maxHumanPlayersSpinner.getEditor()).getTextField().setEditable(false);
		add(maxHumanPlayersSpinner, "cell 2 6,growx,aligny center");

		this.add(new JLabel("Liczba botów"), "cell 0 7 3 1,alignx center");
		int i = 0;
		botsNumberSpinner = new JSpinner[BotLevel.values().length];
		for (BotLevel level : BotLevel.values()) {
			this.add(new JLabel(level.toString()), "cell 0 " + (i + 8) + ",alignx left");
			botsNumberSpinner[i] = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
			((DefaultEditor) botsNumberSpinner[i].getEditor()).getTextField().setEditable(false);
			add(botsNumberSpinner[i], "cell 2 " + (i + 8) + ",growx,aligny center");
			i++;
		}

		createGameButton = new JButton("Stwórz grê");
		createGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedCreateGameButton();
			}
		});
		returnButton = new JButton("WyjdŸ");
		returnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedReturnButton();
			}
		});
		this.add(returnButton, "newline push, skip 1, width 100!,alignx right");
		this.add(createGameButton, "width 100!,alignx left");
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

	}

	// TODO
	public GameSettings getGameSettings() {
		Map<BotLevel, Integer> botsNumber = new HashMap<BotLevel, Integer>();
		int i = 0;
		for (BotLevel level : BotLevel.values()) {
			botsNumber.put(level, (int) botsNumberSpinner[i].getValue());
			i++;
		}
		int diceNumber = 5;
		int maxInactiveTime = 0;
		if ((int) timeForMoveSpinner.getValue() < 0) {
			maxInactiveTime = -1;
		} else {
			maxInactiveTime = (int) timeForMoveSpinner.getValue();
		}
		return new GameSettings((GameType) gameTypeComboBox.getSelectedItem(), diceNumber, nameField.getText(),
				(int) maxHumanPlayersSpinner.getValue(), (int) timeForMoveSpinner.getValue(), maxInactiveTime,
				(int) roundsToWinSpinner.getValue(), botsNumber);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}
}
