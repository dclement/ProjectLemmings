
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import environment.Environment;

/**
 * GUI classe
 * @author Clement
 *
 */
public class LemmingsGUI extends JFrame implements KeyListener {

	private static final long serialVersionUID = -3354644983761730596L;
	
	/** Width in pixels of a cell.
	 */
	public static final int CELL_WIDTH = 20;
	/** Height in pixels of a cell.
	 */
	public static final int CELL_HEIGHT = 20;
	/** Demi-width in pixels of a cell.
	 */
	public static final int DEMI_CELL_WIDTH = CELL_WIDTH/2;
	/** Demi-height in pixels of a cell.
	 */
	public static final int DEMI_CELL_HEIGHT = CELL_HEIGHT/2;
	
	private final Environment environment;
	
	/**
	 * 
	 * @param env is the environment to display.
	 */
	public LemmingsGUI(Environment env) {
		this.environment = env;
				
		GridPanel gp = new GridPanel();
		gp.setPreferredSize(new Dimension(
				CELL_WIDTH*this.environment.getWidth(),
				CELL_HEIGHT*this.environment.getHeight()));
		
		JScrollPane sc = new JScrollPane(gp);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, sc);
		setPreferredSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lemmings Game");
		pack();

		addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			
			break;
		case KeyEvent.VK_RIGHT:
			
			break;
		case KeyEvent.VK_UP:
			
			break;
		case KeyEvent.VK_DOWN:
		
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//this.player.setGUIMove(null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//
	}

	
	private class GridPanel extends JPanel {
		
		private static final long serialVersionUID = -7163684947280907441L;
		
		public GridPanel() {
			setBackground(Color.BLACK);
		}

		@SuppressWarnings("synthetic-access")
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			int px, py;
			Graphics2D g2d = (Graphics2D)g;
			
			synchronized(LemmingsGUI.this.environment) {
				for(int x=0; x<LemmingsGUI.this.environment.getWidth(); x++) {
					for(int y=0; y<LemmingsGUI.this.environment.getHeight(); y++) {
						px = CELL_WIDTH * x;
						py = CELL_HEIGHT * y;
						if (LemmingsGUI.this.environment.isWall(x,y)) {
							g2d.setColor(Color.BLUE);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
						}
						if (LemmingsGUI.this.environment.isLemmings(x, y)) {
							g2d.setColor(Color.YELLOW);
							g2d.fillOval(px, py, 10, 10);
						}
						if (LemmingsGUI.this.environment.isEndArea(x,y)) {
							g2d.setColor(Color.RED);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT/2);
						}
					}
				}
			}
		}
		
	}
	
}
