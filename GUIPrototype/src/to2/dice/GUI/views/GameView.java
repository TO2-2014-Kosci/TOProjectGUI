package to2.dice.GUI.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidAlgorithmParameterException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.miginfocom.swing.MigLayout;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.controllers.GameController;
import to2.dice.game.GameType;
import to2.dice.game.NGameState;
import to2.dice.game.Player;

public class GameView extends View {
	private static final long serialVersionUID = -1998878099465780349L;
	private GameAnimation gameAnimation;
	private JButton rerollButton;
	private JButton standUpLeaveButton;
	private JTable playerTable;
	private JLabel timerLabel;
	private JLabel roundLabel;
	private JLabel nGoalLabel;
	private JLabel targetLabel;
	private Font labelFont = new Font("Tahoma", Font.PLAIN, 25);
	
	private Player lastPlayer;
	
	private static final int DEFAULT_WIDTH = 750;
	private static final int DEFAULT_HEIGHT= 520;
	private Timer timer;
	
	public GameView(Model model, GameController controller, GameAnimation gameAnimation){
		super(model, controller);
		this.gameAnimation = gameAnimation;
		setBackground(new Color(6, 35, 0));
		setLayout(new MigLayout("", "[][][grow][]", "[][grow][][]"));
		playerTable = new JTable(new AbstractTableModel(){
			private final String[] columnNames = {
				"Gracz",
				"Koœci",
				"Punkty"
			};
			
			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return columnNames.length;
			}
			
	        public String getColumnName(int col) {
	            return columnNames[col];
	        }
			public int getRowCount() {
				return model.getGameState().getPlayersNumber();
			}
			
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0, columnIndex).getClass();
			}
			
			public Object getValueAt(int arg0, int arg1) {
				switch (arg1) {
					case 0:
						return model.getGameState().getPlayers().get(arg0).getName();
					case 1:
						//TODO change to image, maybe
						int[] dice = model.getGameState().getPlayers().get(arg0).getDice().getDiceArray();
						String result = "";
						for (int i: dice) {
							result += Integer.toString(i) + " "; 
						}
						return result;
					case 2:
						return model.getGameState().getPlayers().get(arg0).getScore();
					default:
						return new Object();
				}
			}
		    public boolean isCellEditable(int row, int col) {
		    	return false;
		    }
		    
		}) {
			private static final long serialVersionUID = 1768548772080336158L;

			@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                Color color = Color.WHITE;
                if (model.getGameState().getPlayers().get(row).equals(model.getGameState().getCurrentPlayer())) {
		    		color = Color.LIGHT_GRAY;
		    	} else {
		    		color = Color.WHITE;
		    	}
                comp.setBackground(color);
                return comp;
            }
		};
		playerTable.setEnabled(false);
		playerTable.setFillsViewportHeight(true);
		playerTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerTable.setShowVerticalLines(false);
		playerTable.setRowHeight(19);
		playerTable.getColumnModel().getColumn(1).setMinWidth(99);
		playerTable.getColumnModel().getColumn(1).setMaxWidth(99);
		playerTable.getColumnModel().getColumn(1).setPreferredWidth(99);
		playerTable.getColumnModel().getColumn(2).setMinWidth(45);
		playerTable.getColumnModel().getColumn(2).setMaxWidth(45);
		playerTable.getColumnModel().getColumn(2).setPreferredWidth(45);
		JScrollPane playerScrollTable= new JScrollPane(playerTable);
		
		add(playerScrollTable, "cell 0 1 1 3, width 30%!, growy");
		
		targetLabel = new JLabel("Do " + model.getGameSettings().getRoundsToWin() + " punktów");
		targetLabel.setFont(labelFont);
		add(targetLabel, "cell 0 0, center");
		
		timerLabel = new JLabel();
		timerLabel.setFont(labelFont);
		add(timerLabel, "cell 3 0,alignx right");
		standUpLeaveButton = new JButton();
		String text;
		if (model.isSitting()) {
			text = "Wstañ";
		} else {
			text = "WyjdŸ";
		}
		standUpLeaveButton.setText(text);
		standUpLeaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedStandUpLeaveButton();
			}
		});
		
		add(standUpLeaveButton, "cell 3 3, alignx left, aligny bottom");
		
		
		rerollButton = new JButton("Przerzuæ");
		rerollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.rerollDice();
			}
		});
		add(rerollButton, "cell 2 3, growx, alignx center, aligny bottom");
		if (model.getGameState().getCurrentPlayer() != null && model.getGameState().getCurrentPlayer().getName().equals(model.getLogin())) {
			rerollButton.setVisible(true);
		} else {
			rerollButton.setVisible(false);
		}
		add(gameAnimation.getCanvas(), "cell 1 1 4 2,grow");
		timer = (new Timer());
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (model.getTimer() > 5) {
					timerLabel.setForeground(Color.BLACK);
					timerLabel.setText(Integer.toString(model.getTimer()));
					model.setTimer(model.getTimer() - 1);
				} else if (model.getTimer() >= 0){
					timerLabel.setForeground(Color.RED);
					timerLabel.setText(Integer.toString(model.getTimer()));
					model.setTimer(model.getTimer() - 1);
				} else {
					timerLabel.setText("0");
					model.setTimer(0);
				}
			}
		}, 0, 1000);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		roundLabel = new JLabel("Runda " + model.getGameState().getCurrentRound());
		roundLabel.setFont(labelFont);
		add(roundLabel, "cell 1 0, alignx left");
		
		if (model.getGameSettings().getGameType() != GameType.POKER) {
			nGoalLabel = new JLabel("Cel: " + ((NGameState)model.getGameState()).getWinningNumber());
		} else {
			nGoalLabel = new JLabel("dsa");
		}
		nGoalLabel.setFont(labelFont);
		add(nGoalLabel, "cell 2 0, alignx center");
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if (model.isSitting()) {
			standUpLeaveButton.setText("Wstañ");
		} else {
			standUpLeaveButton.setText("WyjdŸ");
		}
		if (model.getGameState().getCurrentPlayer() != null && model.getGameState().getCurrentPlayer().getName().equals(model.getLogin())) {
			rerollButton.setVisible(true);
		} else {
			rerollButton.setVisible(false);
		}
		((AbstractTableModel)playerTable.getModel()).fireTableDataChanged();
		roundLabel.setText("Runda " + model.getGameState().getCurrentRound());
		if (model.getGameSettings().getGameType() != GameType.POKER) {
			nGoalLabel.setText("Cel: " + ((NGameState)model.getGameState()).getWinningNumber());
		} else {
			nGoalLabel.setText("dsa");
		}
		if (lastPlayer != null && !lastPlayer.equals(model.getGameState().getCurrentPlayer())) {
			timerLabel.setText(Integer.toString(model.getGameSettings().getTimeForMove()));
			model.setTimer(model.getGameSettings().getTimeForMove());
			lastPlayer = model.getGameState().getCurrentPlayer();
		}
		gameAnimation.refresh();
	}
}
