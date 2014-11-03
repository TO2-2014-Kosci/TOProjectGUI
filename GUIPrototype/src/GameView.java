import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Component;


public class GameView extends JPanel{

	private static final int DEFAULT_WIDTH = 750;
	private static final int DEFAULT_HEIGHT= 520;
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
	
	public GameView(JFrame mainFrame) {
//		setBackground(new Color(70, 155, 30));
		setBackground(new Color(64, 0, 0));
		setLayout(new MigLayout("", "[][grow][]", "[][grow][][]"));
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
		
		add(playerScrollTable, "cell 0 0 1 4,width 30%!,growy");
		
		JLabel label = new JLabel("59");
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(label, "cell 2 0,alignx right");
		
		JLabel dices = new JLabel(new ImageIcon("kosciEkran2.png"));
		dices.setBackground(new Color(70, 155, 30));
		dices.setOpaque(true);
		add(dices, "cell 1 0 2 2,grow");
		JButton button = new JButton("Wyjdü");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameList gameList = new GameList(mainFrame);
				mainFrame.setContentPane(gameList);
				mainFrame.setMinimumSize(gameList.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.revalidate();
			}
		});
		add(button, "cell 2 3,alignx right,aligny bottom");
		JLabel test = new JLabel(new ImageIcon("kosciGracz.png"));
		test.setBackground(new Color(70, 155, 30));
		test.setOpaque(true);
		add(test, "cell 1 2 2 2,grow, height 140px!");
		
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
}
