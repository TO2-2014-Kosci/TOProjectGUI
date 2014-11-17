
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;


public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1552746400473185110L;

	public GamePanel() {
		setBackground(new Color(70, 155, 30));
		setOpaque(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawDice(g2, 10, 10, 5);
		drawDice(g2, 70, 10, 5);
		drawDice(g2, 10, 70, 5);
		drawDice(g2, 70, 70, 5);
		drawDice(g2, 140, 70, 5);
    }
	
	private void drawDice(Graphics2D g2, int x, int y, int point) {
		g2.setColor(Color.WHITE);
		g2.fillPolygon(new int[]{x, x+50, x+50, x}, new int[]{y, y, y+50, y+50}, 4);
		g2.setColor(Color.black);
		g2.drawString(Integer.toString(point), x+25, y+25);
	}
	
}