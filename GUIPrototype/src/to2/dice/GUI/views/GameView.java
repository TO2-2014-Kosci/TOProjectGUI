package to2.dice.GUI.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import animation.Test;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.controllers.GameController;

public class GameView extends View {
	private static final long serialVersionUID = -1998878099465780349L;
	private GameAnimation gameAnimation;
	private JButton rerollButton;
	private JButton standUpLeaveButton;
	private JTable playerTable;
	private JLabel timerLabel;
	
	private static final int DEFAULT_WIDTH = 750;
	private static final int DEFAULT_HEIGHT= 520;
	
	public GameView(Model model, GameController controller, GameAnimation gameAnimation){
		super(model, controller);
		this.gameAnimation = gameAnimation;
		setBackground(new Color(64, 0, 0));
		setLayout(new MigLayout("", "[][grow][]", "[][grow][][]"));
		playerTable = new JTable(new AbstractTableModel(){
			private final String[] columnNames = {
				"Gracz",
				"Ko�ci",
				"Punkty"
			};
			
			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return columnNames.length;
			}
			
			public Class<?> getColumnClass(int columnCount) {
				return getValueAt(0, columnCount).getClass();
			}
	        public String getColumnName(int col) {
	            return columnNames[col];
	        }
			public int getRowCount() {
				return model.getGameState().getPlayersNumber();
			}
			
			public Object getValueAt(int arg0, int arg1) {
				switch (arg1) {
					case 0:
						return model.getGameState().getPlayers().get(arg0).getName();
					case 1:
						//TODO change to image, maybe
						int[] dice = model.getGameState().getPlayers().get(arg0).getDice().getDice();
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
		});
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
		
		add(playerScrollTable, "cell 0 0 1 4,width 30%!,growy");
		
		timerLabel = new JLabel();
		timerLabel.setOpaque(true);
		timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(timerLabel, "cell 2 0,alignx right");
		standUpLeaveButton = new JButton();
		String text;
		if (model.isSitting()) {
			text = "Wsta�";
		} else {
			text = "Wyjd�";
		}
		standUpLeaveButton.setText(text);
		standUpLeaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedStandUpLeaveButton();
			}
		});
		
		add(standUpLeaveButton, "cell 1 3,alignx left,aligny bottom");
		
		
		rerollButton = new JButton("Przerzu�");
		rerollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.rerollDice();
			}
		});
		add(rerollButton, "cell 2 3,alignx right,aligny bottom");
		if (model.getGameState().getCurrentPlayer() != null && model.getGameState().getCurrentPlayer().getName().equals(model.getLogin())) {
			rerollButton.setVisible(true);
		} else {
			rerollButton.setVisible(false);
		}
		add(gameAnimation.getCanvas(), "cell 1 0 2 4,grow");
		Timer timer = (new Timer());
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (model.getTimer() > 5) {
					timerLabel.setForeground(Color.BLACK);
					timerLabel.setText(Integer.toString(model.getTimer()));
					model.setTimer(model.getTimer()-1);
				} else if (model.getTimer() >= 0){
					timerLabel.setForeground(Color.RED);
					timerLabel.setText(Integer.toString(model.getTimer()));
					model.setTimer(model.getTimer()-1);
				} else {
					timerLabel.setText("0");
					model.setTimer(0);
				}
			}
		}, 0, 1000);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if (model.getGameState().getCurrentPlayer() != null && model.getGameState().getCurrentPlayer().getName().equals(model.getLogin())) {
			rerollButton.setVisible(true);
		} else {
			rerollButton.setVisible(false);
		}
		((AbstractTableModel)playerTable.getModel()).fireTableDataChanged();
		gameAnimation.refresh();
	}
}
