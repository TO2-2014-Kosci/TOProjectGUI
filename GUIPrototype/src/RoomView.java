import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


public class RoomView extends JPanel {
	String[] columnNames={"Name"};
	Object[][] rowData={
			{"Gracz 1"},
			{"Gracz 2"}
	};
	private static final long serialVersionUID = 1L;
	
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGHT= 400;
	
	public RoomView(JFrame mainFrame){
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
		playersTable.setRowHeight(19);
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)playersTable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		
		JScrollPane playersScrollTable= new JScrollPane(playersTable);
		add(playersScrollTable,"push,grow,wrap");
		JButton opusc = new JButton("Opuœæ");
		opusc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameList gameList = new GameList(mainFrame);
				mainFrame.setContentPane(gameList);
				mainFrame.setMinimumSize(gameList.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.revalidate();
			}
		});
		add(opusc,"w 100!,split 4,gapright push,pushx");	
		add(new JLabel("Iloœæ graczy:"),"w 100!,gapleft push,pushx");
		add(new JLabel("2/9"),"w 50!,gapright push,pushx");
		JButton start = new JButton("Rozpocznij");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameView gameView= new GameView(mainFrame);
				mainFrame.setContentPane(gameView);
				mainFrame.setMinimumSize(gameView.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.revalidate();
			}
		});
		add(start,"w 100!,gapleft push,pushx");
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

}
