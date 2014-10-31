import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;


public class RoomView extends JPanel {
	String[] columnNames={"Name"};
	Object[][] rowData={
			{"Gracz 1"},
			{"Gracz 2"}
	};
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT= 400;
	
	public RoomView(){
		setLayout(new MigLayout());
		
		JTable playersTable = new JTable(new AbstractTableModel(){

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
		playersTable.setFillsViewportHeight(true);
		playersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		playersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersTable.setShowVerticalLines(false);
		playersTable.setRowHeight(20);
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)playersTable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		//playersTable.setPreferredScrollableViewportSize(new Dimension(500,300));
		
		
		JScrollPane playersScrollTable= new JScrollPane(playersTable);
		add(playersScrollTable,"push,grow,wrap");
		add(new JButton("Opuœæ"),"w 100!,split 4,gapright push,pushx");	
		add(new JLabel("Iloœæ graczy:"),"w 100!,gapleft push,pushx");
		add(new JLabel("4/9"),"w 50!,gapright push,pushx");
		add(new JButton("Rozpocznij"),"w 100!,gapleft push,pushx");
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

}
