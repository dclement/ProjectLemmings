
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import environment.Environment;
import environment.EnvironmentObject;

/**
 * GUI classe
 * @author Clement
 *
 */
public class LemmingsGUI extends JFrame implements KeyListener, MouseListener {

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
	private JMenuBar menuBar;
	private JMenu menu;
	private JPanel UserInterface;
	private String newEnvironmentObject = null;
	private JButton bWall;
	private JButton bJump;
	
	private BufferedImage Wall_img = null;
	private BufferedImage Spawner_img = null;
	private BufferedImage Lemming_img = null;
	private BufferedImage Jump_img = null;
	private BufferedImage EndArea_img = null;
	
	/**
	 * 
	 * @param env is the environment to display.
	 */
	public LemmingsGUI(Environment env) {
		this.environment = env;
				
		try {
			BufferedImage Wall_img_tmp = ImageIO.read(new File(getClass().getResource("mur.png").toURI()));
		  	Wall_img= resizeImage(Wall_img_tmp, CELL_WIDTH, CELL_HEIGHT);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedImage Spawner_img_tmp = ImageIO.read(new File(getClass().getResource("porte.png").toURI()));
			Spawner_img= resizeImage(Spawner_img_tmp, CELL_WIDTH, CELL_HEIGHT);
	    } catch (IOException e) {
	    	System.err.println(e.getMessage());
	    } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedImage Jump_img_tmp = ImageIO.read(new File(getClass().getResource("jump.png").toURI()));
	    	Jump_img= resizeImage(Jump_img_tmp, CELL_WIDTH, CELL_HEIGHT);
	    } catch (IOException e) {
	    	System.err.println(e.getMessage());
	    } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedImage EndArea_img_tmp = ImageIO.read(new File(getClass().getResource("porte.png").toURI()));
	    	EndArea_img= resizeImage(EndArea_img_tmp, CELL_WIDTH, CELL_HEIGHT);
	    } catch (IOException e) {
	    	System.err.println(e.getMessage());
	    } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedImage Lemming_img_tmp = ImageIO.read(new File(getClass().getResource("Lemming.png").toURI()));
	    	Lemming_img= resizeImage(Lemming_img_tmp, CELL_WIDTH, CELL_HEIGHT);
	    } catch (IOException e) {
	    	System.err.println(e.getMessage());
	    } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		GridPanel gp = new GridPanel();
		gp.addMouseListener(this);
		gp.setPreferredSize(new Dimension(
				CELL_WIDTH*this.environment.getWidth(),
				CELL_HEIGHT*this.environment.getHeight()));
		
		JScrollPane sc = new JScrollPane(gp);
		menuBar = new JMenuBar();
				
		menu = new JMenu("User Interface");
		menuBar.add(menu);
		menu.addMenuListener(new MenuListener() {
		
		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			if(UserInterface.isVisible())
			{
				UserInterface.setVisible(false);
			}
			else
			{	
				UserInterface.setVisible(true);
			}
			
		}
		
		@Override
		public void menuDeselected(MenuEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void menuCanceled(MenuEvent e) {
			// TODO Auto-generated method stub
			
		}
		});
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, sc);
		setPreferredSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lemmings Game");
		pack();
		this.setJMenuBar(menuBar);
		UserInterface = new JPanel();
		bWall = new JButton("Wall");
		bWall.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = "environment.Wall";
       		}
        });      
 
		bJump = new JButton("Jump");
		bJump.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = "environment.Jump";
       		}
        });      
		UserInterface.setLayout(new GridLayout(4,1));
		UserInterface.add(bWall);
		UserInterface.add(bJump);
		getContentPane().add(BorderLayout.EAST, UserInterface);
		UserInterface.setVisible(false);
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
							//g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
							g2d.drawImage(Wall_img, px, py, CELL_WIDTH, CELL_HEIGHT, this);
						}
						if (LemmingsGUI.this.environment.isLemmings(x, y)) {
							g2d.setColor(Color.YELLOW);
							g2d.drawImage(Lemming_img, px, py, CELL_WIDTH, CELL_HEIGHT, this);
							//g2d.fillOval(px, py, 10, 10);
						}
						if (LemmingsGUI.this.environment.isEndArea(x,y)) {
							g2d.setColor(Color.RED);
							//g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
							g2d.drawImage(EndArea_img, px, py, CELL_WIDTH, CELL_HEIGHT, this);
						}
						if (LemmingsGUI.this.environment.isJump(x,y)) {
							g2d.setColor(Color.GREEN);
							//g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
							g2d.drawImage(Jump_img, px, py, CELL_WIDTH, CELL_HEIGHT, this);
						}
					}
				}
			}
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		  //Relative coordonnate
		  int x=e.getX();
		  int y=e.getY();
		  //Coordonnate of the grid
		  x=x/CELL_WIDTH;
		  y=y/CELL_HEIGHT;
		  //Remove object
		  if(LemmingsGUI.this.environment.isWall(x, y) || LemmingsGUI.this.environment.isJump(x, y))
		  {
			  EnvironmentObject remplacement = null;
			  LemmingsGUI.this.environment.userEnvironnementChange(remplacement,x,y);
		  }
		  else
		  {
			  if(LemmingsGUI.this.environment.isFree(x, y) && newEnvironmentObject!=null)
			  {
				  Class cls = null;
				try {
					cls = Class.forName(newEnvironmentObject);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Object clsInstance = null;
				  try {
					  clsInstance = (Object) cls.newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				EnvironmentObject remplacement = (EnvironmentObject)clsInstance;
				LemmingsGUI.this.environment.userEnvironnementChange(remplacement,x,y);
			  }
		  }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public BufferedImage resizeImage(BufferedImage image, int width, int height) {
	       int type=0;
	       type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
	       BufferedImage resizedImage = new BufferedImage(width, height,type);
	       Graphics2D g = resizedImage.createGraphics();
	       g.drawImage(image, 0, 0, width, height, null);
	       g.dispose();
	       return resizedImage;
	}
}
