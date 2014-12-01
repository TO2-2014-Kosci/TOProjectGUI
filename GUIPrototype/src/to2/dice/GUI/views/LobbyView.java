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
	private static final long serialVersionUID = -3735508074042553963L;
	private JButton sitDownStandUpButton;
	private JButton leaveButton;
	private JLabel playersCountLabel;
	private JTable playersTable;
	
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGHT= 400;
	
	String[] columnNames={"Name"};
	
	public LobbyView(Model model, LobbyController controller) {
		super(model, controller);
		setLayout(new MigLayout("", "[grow][][]", "[][][][][][][][][][grow][]")
		);
		
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
				if(model.getGameState()==null){
					return 0;
				}
				return model.getGameState().getPlayersNumber();
			}
			
			public Object getValueAt(int arg0, int arg1) {
				return model.getGameState().getPlayers().get(arg0).getName();
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
		add(playersScrollTable,"cell 0 0 1 10, push, grow");
		sitDownStandUpButton = new JButton("Usi¹dŸ");
		sitDownStandUpButton.addActionListener(new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
				controller.clickedSitDownStandUpButton();
			}
		});
		add(sitDownStandUpButton,"cell 1 9 2 1,growx,aligny bottom");
		
		
		add(new JLabel("Typ gry:"), "cell 1 0, right");
		add(new JLabel("Czas na ruch:"), "cell 1 1, right");
		add(new JLabel("Cel:"), "cell 1 2, right");
		add(new JLabel(model.getGameSettings().getGameType().toString()), "cell 2 0, right");
		add(new JLabel(Integer.toString(model.getGameSettings().getTimeForMove())), "cell 2 1, right");
		add(new JLabel(Integer.toString(model.getGameSettings().getRoundsToWin())), "cell 2 2, right");
		playersCountLabel = new JLabel("Iloœæ graczy: " + Integer.toString(model.getGameState().getPlayersNumber()) + "/" + Integer.toString(model.getGameSettings().getMaxPlayers()));
		add(playersCountLabel,"cell 0 10,alignx center");


		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		
		leaveButton = new JButton("Opuœæ");
		leaveButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedLeaveButton();
			}
		});
		add(leaveButton,"cell 1 10 2 1,grow");	
	}

	@Override
	public void refresh() {
		playersTable.repaint();
		if (model.isSitting()) {
			sitDownStandUpButton.setText("Wstañ");
		} else {
			sitDownStandUpButton.setText("Usi¹dŸ");
		}
		playersCountLabel.setText("Iloœæ graczy: " + Integer.toString(model.getGameState().getPlayersNumber()) + "/" + Integer.toString(model.getGameSettings().getMaxPlayers()));
	}

}
