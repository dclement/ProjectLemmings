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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import Util.XMLParser;
import environment.Environment;
import environment.EnvironmentObject;

/**
 * GUI classe
 * @author Clement
 *
 */
public class StartGUI extends JFrame implements KeyListener, MouseListener {

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
	
	
	private JMenuBar menuBar;
	private JMenu play;
	private JMenu menu;
	private JMenu editeur;
	private JMenu save;
	private JPanel UserInterface;
	private String[][] grid;
	private JButton bWall;
	private JButton bJump;
	private JButton bEndArea;
	private JButton bSpawner;
	private JButton bSupp;
	private boolean StartSimulation = false;
	private String newEnvironmentObject = null;
	private int width = 28;
	private int height = 28;
	/**
	 * 
	 * @param env is the environment to display.
	 */
	public StartGUI() {
		grid = new String[width][height];
		GridPanel gp = new GridPanel();
		gp.addMouseListener(this);
		gp.setPreferredSize(new Dimension(
				CELL_WIDTH*width,
				CELL_HEIGHT*height));
		
		JScrollPane sc = new JScrollPane(gp);
		menuBar = new JMenuBar();
		
		play = new JMenu("Play");
		menuBar.add(play);
		play.addMenuListener(new MenuListener() {
		
		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			StartSimulation = true;
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
		editeur = new JMenu("Modification carte");
		menuBar.add(editeur);
		editeur.addMenuListener(new MenuListener() {
		
		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			
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
		
		save = new JMenu("save");
		menuBar.add(save);
		save.addMenuListener(new MenuListener() {
		
		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			Object[] possibilities = null;
			String selectedValue = (String) JOptionPane.showInputDialog(null,

			        "Name of the file", "Save",

			        JOptionPane.INFORMATION_MESSAGE, null,

			        possibilities, null);

			//If a string was returned, say so.
			if ((selectedValue != null) && (selectedValue.length() > 0)) {
			   System.out.println("sele  " + selectedValue);
			   try {
				   save(selectedValue);
			   } catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			   }
			}
		}
		
		/**
		 * Create XML file
		 * @param selectedValue : name of the xml
		 * @throws ParserConfigurationException 
		 */
		private void save(String selectedValue) throws ParserConfigurationException {
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();
			Document document = constructeur.newDocument();

			// Propriétés du DOM
			document.setXmlVersion("1.0");
			document.setXmlStandalone(true);

			// Création de l'arborescence du DOM
			Element racine = document.createElement("config");
			
			Element env = document.createElement("environment");
			env.setAttribute("height","28");
			env.setAttribute("width","28");
			racine.appendChild(env);

			for(int x=0; x<width; x++) {
				for(int y=0; y<height; y++) {
					if(grid[x][y]!=null)
					{
						if (grid[x][y].equalsIgnoreCase("Wall")) {
							Element wall = document.createElement("Wall");
							wall.setAttribute("x", String.valueOf(x));
							wall.setAttribute("y", String.valueOf(y));
							wall.setAttribute("width", "0");
							wall.setAttribute("height", "0");
							env.appendChild(wall);
						}
						if (grid[x][y].equalsIgnoreCase("EndArea")) {
							Element endarea = document.createElement("Endarea");
							endarea.setAttribute("x", String.valueOf(x));
							endarea.setAttribute("y", String.valueOf(y));
							env.appendChild(endarea);
						}
						if (grid[x][y].equalsIgnoreCase("Jump")) {
							Element Jump = document.createElement("Jump");
							Jump.setAttribute("x", String.valueOf(x));
							Jump.setAttribute("y", String.valueOf(y));
							Jump.setAttribute("width", "1");
							Jump.setAttribute("height", "1");
							env.appendChild(Jump);
						}
						if (grid[x][y].equalsIgnoreCase("Spawner")) {
							Element Spawner = document.createElement("Spawner");
							Spawner.setAttribute("x", String.valueOf(x));
							Spawner.setAttribute("y", String.valueOf(y));
							Spawner.setAttribute("numberLemmings", "5");
							env.appendChild(Spawner);
						}
					}
					
			
				}
			}
			document.appendChild(racine);
			transformerXml(document, selectedValue);
		}

		public void transformerXml(Document document, String fichier) {
			try {
				// Création de la source DOM
				Source source = new DOMSource(document);
				String AbsolutePath = new File("").getAbsolutePath();
				AbsolutePath = AbsolutePath + "\\map\\";
				AbsolutePath = AbsolutePath + fichier;
				AbsolutePath = AbsolutePath + ".xml";
				System.out.println("absol " + AbsolutePath);
				// Création du fichier de sortie
				File file = new File(AbsolutePath);
				Result resultat = new StreamResult(AbsolutePath);
				
				// Configuration du transformer
				TransformerFactory fabrique = TransformerFactory.newInstance();
				Transformer transformer = fabrique.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				
				// Transformation
				transformer.transform(source, resultat);
			}catch(Exception e){
				e.printStackTrace();
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
    		 	newEnvironmentObject = "Wall";
       		}
        });      
 
		bJump = new JButton("Jump");
		bJump.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = "Jump";
       		}
        });
		bEndArea = new JButton("EndArea");
		bEndArea.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = "EndArea";
       		}
        });
		bSpawner = new JButton("Spawner");
		bSpawner.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = "Spawner";
       		}
        });
		bSupp = new JButton("Suppr");
		bSupp.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
    		 	newEnvironmentObject = null;
       		}
        });
		UserInterface.setLayout(new GridLayout(4,1));
		UserInterface.add(bWall);
		UserInterface.add(bJump);
		UserInterface.add(bEndArea);
		UserInterface.add(bSpawner);
		UserInterface.add(bSupp);
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
			System.out.println("paint");
			int px, py;
			Graphics2D g2d = (Graphics2D)g;
			if(grid!=null)
			{
			for(int x=0; x<width; x++) {
				for(int y=0; y<height; y++) {
					px = CELL_WIDTH * x;
					py = CELL_HEIGHT * y;
					if(grid[x][y]!=null)
					{
						if (grid[x][y].equalsIgnoreCase("Wall")) {
							g2d.setColor(Color.BLUE);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
						}
						if (grid[x][y].equalsIgnoreCase("EndArea")) {
							g2d.setColor(Color.RED);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
						}
						if (grid[x][y].equalsIgnoreCase("Jump")) {
							g2d.setColor(Color.GREEN);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
						}
						if (grid[x][y].equalsIgnoreCase("Spawner")) {
							g2d.setColor(Color.YELLOW);
							g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
						}
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
		  grid[x][y] = newEnvironmentObject;
		  System.out.println("element " + newEnvironmentObject + "position " + x  + " " + y);
		  repaint();
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
	
	public boolean getStartSimu()
	{
		return this.StartSimulation;
	}
	
	
}
