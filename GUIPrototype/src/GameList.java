import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;
public class GameList extends JPanel {
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT= 500;
	
	private static final String[] columnNames={
		"Nazwa",
		"Typ",
		"Graczy",
		"Max graczy",
		"Graczy AI"
		};
	Object[][] rowData={
			{"Gra 1", "Poker", 3 , 4, 10},
	};
	public GameList(JFrame mainFrame){
		JTable gamesTable = new JTable(new AbstractTableModel(){

			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return columnNames.length;
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
		gamesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		gamesTable.getColumnModel().getColumn(1).setMaxWidth(50);
		gamesTable.getColumnModel().getColumn(1).setMinWidth(50);
		
		gamesTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		gamesTable.getColumnModel().getColumn(2).setMaxWidth(50);
		gamesTable.getColumnModel().getColumn(2).setMinWidth(50);
		
		gamesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		gamesTable.getColumnModel().getColumn(3).setMaxWidth(70);
		gamesTable.getColumnModel().getColumn(3).setMinWidth(70);
		
		gamesTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		gamesTable.getColumnModel().getColumn(4).setMaxWidth(70);
		gamesTable.getColumnModel().getColumn(4).setMinWidth(70);
		
		gamesTable.setFillsViewportHeight(true);
		gamesTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		gamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTable.setShowVerticalLines(false);
		gamesTable.setRowHeight(20);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)gamesTable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(JLabel.CENTER);
        //gamesTable.setPreferredScrollableViewportSize(new Dimension(500,300));
		
		setLayout(new BorderLayout());
		JScrollPane gamesScrollTable= new JScrollPane(gamesTable);
		add(gamesScrollTable,BorderLayout.CENTER);
		JPanel buttonPanel= new JPanel();
		buttonPanel.setLayout(new MigLayout(
				"",
				"",
				"[][grow, bottom][][]"));		
		add(buttonPanel,BorderLayout.EAST);
		String witaj = "Witaj NICK";  
		JLabel lblWitaj = new JLabel(witaj);
		buttonPanel.add(lblWitaj, "cell 0 0,alignx center,aligny center");
		JButton joinButton= new JButton("Do³¹cz");
		joinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomView roomView = new RoomView(mainFrame);
				mainFrame.setContentPane(roomView);
				mainFrame.setMinimumSize(roomView.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				roomView.revalidate();
			}
		});
		buttonPanel.add(joinButton, "cell 0 1,width 100!");
		JButton createButton= new JButton("Stwórz grê");
		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGameView createGameView = new CreateGameView(mainFrame);
				mainFrame.setContentPane(createGameView);
				mainFrame.setMinimumSize(createGameView.getMinimumSize());
				createGameView.revalidate();
			}
		});
		buttonPanel.add(createButton, "cell 0 2,width 100!");
		JButton refreshButton= new JButton("Odœwie¿");
		buttonPanel.add(refreshButton, "cell 0 3,width 100!");
		setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
	}
}
