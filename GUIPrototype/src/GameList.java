import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
public class GameList extends JPanel {
	
	private static final
	String[] columnNames={"Name","Type", "Rounds"};
	Object[][] rowData={
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 1","N+",3},
			{"Gra 2","N*",4}
	};
	public GameList(){
		JButton joinButton= new JButton("Do³¹cz");
		JButton refreshButton= new JButton("Odœwie¿");
		JButton createButton= new JButton("Stwórz grê");
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
			
		}
				);		
		gamesTable.setFillsViewportHeight(true);
		gamesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		gamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTable.setShowVerticalLines(false);
		gamesTable.setRowHeight(20);
        //gamesTable.setPreferredScrollableViewportSize(new Dimension(500,300));
		
		setLayout(new BorderLayout());
		JScrollPane gamesScrollTable= new JScrollPane(gamesTable);
		add(gamesScrollTable,BorderLayout.CENTER);
		
		JPanel buttonPanel= new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(joinButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanel.add(createButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanel.add(refreshButton);
		
		add(buttonPanel,BorderLayout.EAST);
	}
}
