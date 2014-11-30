package to2.dice.GUI.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private static final String[] columnNames = {
		"Gracz",
		"Koúci",
		"Punkty"
	};
	
	private static final int DEFAULT_WIDTH = 750;
	private static final int DEFAULT_HEIGHT= 520;
	
	public GameView(Model model, GameController controller, GameAnimation gameAnimation){
		super(model, controller);
		this.gameAnimation = gameAnimation;
		
		setBackground(new Color(64, 0, 0));
		setLayout(new MigLayout("", "[][grow][]", "[][grow][][]"));
		playerTable = new JTable(new AbstractTableModel(){

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
				return model.gameState.getPlayersNumber();
			}
			
			public Object getValueAt(int arg0, int arg1) {
				switch (arg0) {
					case 0:
						return model.gameState.getPlayers().get(arg1).getName();
					case 1:
						return model.gameState.getPlayers().get(arg1).getDice();
					case 2:
						return model.gameState.getPlayers().get(arg1).getScore();
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
		
		JLabel label = new JLabel("59");
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(label, "cell 2 0,alignx right");
		
//		JLabel dices = new JLabel(new ImageIcon("kosciEkran2.png"));
//		dices.setBackground(new Color(70, 155, 30));
//		dices.setOpaque(true);
//		add(dices, "cell 1 0 2 2,grow");
		JButton button = new JButton("Wyjdü");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rowData[0][0] = "alal";
				((AbstractTableModel)playerTable.getModel()).fireTableDataChanged();
				System.out.println("dd");
			}
		});
		add(button, "cell 2 3,alignx right,aligny bottom");
		Test test2 = new Test();
		add(test2.getCanvas(), "cell 1 0 2 4,grow");
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
