import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;


public class GameView extends JPanel{

	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT= 500;
	private static final long serialVersionUID = -4185624294656844410L;
	private static final String[] columnNames = {
			"Gracz",
			"Koúci",
			"Punkty"
	};
	private Object[][] rowData={
			{"Gracz 1", new ImageIcon("kosciTabela.png"), "11"},
	};
	
	private ImageIcon playerDices = new ImageIcon("kosciTabela.png");
	
	public GameView() {
		setBackground(new Color(70, 155, 30));
		setLayout(new MigLayout(
				"",
				"[][grow][]",
				"[grow][]"));
		JTable playerTable = new JTable(new AbstractTableModel(){

			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return columnNames.length;
			}
			
			public Class<?> getColumnClass(int columnCount) {
				if (columnCount == 1) {
					return ImageIcon.class;
				} else {
					return String.class;
				}
			}
	        public String getColumnName(int col) {
	            return columnNames[col];
	        }
			public int getRowCount() {
				return rowData.length;
			}
			
			public Object getValueAt(int arg0, int arg1) {
				return rowData[arg0][arg1];
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
		
		add(playerScrollTable, "cell 0 0 1 2, w 30%!, growy");
		JPanel dices = new JPanel();
		dices.setBackground(new Color(70, 155, 30));
		add(dices, "cell 1 0 2 1, grow");
		add(new JLabel(playerDices), "cell 1 1");
		add(new JButton("Wyjdü"), "cell 2 1, grow");
		
		
		
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
}
