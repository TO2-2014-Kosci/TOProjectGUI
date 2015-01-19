package to2.dice.GUI.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;
import to2.dice.GUI.controllers.GameListController;
import to2.dice.GUI.model.Model;
import to2.dice.game.GameInfo;

public class GameListView extends View {
	private static final long serialVersionUID = 1571599297606706487L;
	private JButton refreshButton;
	private JButton createGameButton;
	private JButton joinGameButton;
	private JTable gameListTable;

	private static final String[] columnNames = { "Nazwa", "Typ", "Graczy", "Max graczy", "Graczy AI", "Rozpocz�ta" };

	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	public GameListView(Model model, GameListController controller) {
		super(model, controller);
		gameListTable = new JTable(new GameListViewTableModel());

		gameListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					controller.clickedJoinGameButton();
				}
			}
		});
		initGameListTableView();
		gameListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		setLayout(new BorderLayout());
		JScrollPane gamesScrollTable = new JScrollPane(gameListTable);
		add(gamesScrollTable, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new MigLayout("", "", "[][grow, bottom][][]"));
		add(buttonPanel, BorderLayout.EAST);
		joinGameButton = new JButton("Do��cz");
		joinGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedJoinGameButton();
			}
		});
		buttonPanel.add(joinGameButton, "cell 0 1,width 100!");
		createGameButton = new JButton("Stw�rz gr�");
		createGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clickedCreateGameButton();
			}
		});
		buttonPanel.add(createGameButton, "cell 0 2,width 100!");
		refreshButton = new JButton("Od�wie�");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.refreshGameList();
			}
		});
		buttonPanel.add(refreshButton, "cell 0 3,width 100!");
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	private void initGameListTableView() {
		gameListTable.getColumnModel().getColumn(1).setMaxWidth(50);
		gameListTable.getColumnModel().getColumn(1).setMinWidth(50);

		gameListTable.getColumnModel().getColumn(2).setMaxWidth(50);
		gameListTable.getColumnModel().getColumn(2).setMinWidth(50);

		gameListTable.getColumnModel().getColumn(3).setMaxWidth(70);
		gameListTable.getColumnModel().getColumn(3).setMinWidth(70);

		gameListTable.getColumnModel().getColumn(4).setMaxWidth(70);
		gameListTable.getColumnModel().getColumn(4).setMinWidth(70);

		gameListTable.getColumnModel().getColumn(5).setMaxWidth(70);
		gameListTable.getColumnModel().getColumn(5).setMinWidth(70);

		gameListTable.setFillsViewportHeight(true);
		gameListTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		gameListTable.setShowVerticalLines(false);
		gameListTable.setRowHeight(20);
		// TODO Is it supposed to be right aligned?:>
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) gameListTable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		// renderer.setHorizontalTextPosition(JLabel.CENTER);
		// gamesTable.setPreferredScrollableViewportSize(new
		// Dimension(500,300));
	}

	public GameInfo getSelectedGame() {
		int rowNumber = gameListTable.getSelectedRow();
		if (rowNumber == -1) {
			return null;
		} else {
			return model.getRoomList().get(rowNumber);
		}
	}

	@Override
	public void refresh() {
		((AbstractTableModel) gameListTable.getModel()).fireTableDataChanged();
	}

	private class GameListViewTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public int getRowCount() {
			return model.getRoomList().size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return getValueAt(0, columnIndex).getClass();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			GameInfo gameInfo = model.getRoomList().get(arg0);
			switch (arg1) {
			case 0:
				return gameInfo.getSettings().getName();
			case 1:
				return gameInfo.getSettings().getGameType();
			case 2:
				return gameInfo.getPlayersNumber();
			case 3:
				return gameInfo.getSettings().getMaxHumanPlayers();
			case 4:
				int number = 0;
				for (int i : gameInfo.getSettings().getBotsNumbers().values()) {
					number += i;
				}
				return number;
			case 5:
				return gameInfo.isGameStarted();
			default:
				return new Object();

			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
}
