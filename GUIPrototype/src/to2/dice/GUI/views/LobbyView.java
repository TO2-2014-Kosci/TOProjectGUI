package to2.dice.GUI.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;
import to2.dice.GUI.controllers.LobbyController;
import to2.dice.GUI.model.Model;

public class LobbyView extends View {
	private JButton sitDownStandUpButton;
	private JButton leaveButton;
	private JLabel playersCountLabel;
	private JTable playersTable;
	
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGHT= 400;
	
	String[] columnNames={"Name"};
	
	public LobbyView(Model model, LobbyController controller) {
		super(model, controller);
		setLayout(new MigLayout());
		
		playersTable = new JTable(new AbstractTableModel(){

			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return columnNames.length;
			}
	        public String getColumnName(int col) {
	            return columnNames[col];
	        }
	        
	        //TODO possible null pointer 
			public int getRowCount() {
				if(model.gameState==null){
					return 0;
				}
				return model.gameState.getPlayersNumber();
			}
			
			public Object getValueAt(int arg0, int arg1) {
				return model.gameState.getPlayers().get(arg0).getName();
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
		
		//TODO Is it working?
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)playersTable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		
		JScrollPane playersScrollTable= new JScrollPane(playersTable);
		add(playersScrollTable,"push,grow,wrap");
		JButton leaveButton = new JButton("Opuœæ");
		leaveButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedLeaveButton();
			}
		});
		add(leaveButton,"w 100!,split 4,gapright push,pushx");	
		add(new JLabel("Iloœæ graczy:"),"w 100!,gapleft push,pushx");
		
		JLabel playersLabel = new JLabel("Gracze:");
		
		add(playersLabel,"w 50!,gapright push,pushx");

		JButton sitStandButton = new JButton("Usi¹dŸ");
		sitStandButton.addActionListener(new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
				controller.clickedSitDownStandUpButton();
			}
		});
		add(sitStandButton,"w 100!,gapleft push,pushx");
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

}
