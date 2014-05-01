

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Util.XMLParser;
import environment.Direction;
import environment.Environment;
import environment.LemmingMind;
import environment.LemmingsBody;
import environment.SpawnerLemmings;
import gui.LemmingsGUI;

/**
 * Classe principale
 * @author Clement
 *
 */
public class Game {
	
	private final Environment environment;
	private final AtomicBoolean stop = new AtomicBoolean(false);
	private SpawnerLemmings sp;
	private static XMLParser parser;
	/**
	 * Constructeur du jeu
	 */
	public Game() {
		this.environment = new Environment(parser.width,parser.height,parser);
		sp = new SpawnerLemmings(parser.numberLemmings, environment, parser.spx, parser.spy);
	}
	
	/** Run the Lemmings game.
	 */
	public void run() {
		
		JFrame window = createGUI();
		
		if (window!=null) window.setVisible(true);
		//Lancement du thread de creation des agents
		Thread T = new Thread(sp);
		T.start();		
		
		
		
		while (!this.stop.get()) {
			//Y a t il des agents ?
			if(!sp.getLemmingList().isEmpty())
			{
				//Live des agents
				for(LemmingMind Lem : sp.getLemmingList()) {
					Lem.live();
				}
				
			}
			
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e) {
				return;
			}
			
			if (window!=null) window.repaint();
		}
		

		//JOptionPane.showMessageDialog(window,"Game Over!"); //$NON-NLS-1$
		if (window!=null) window.setVisible(false);
	}
	
	/** Create the UI for the game.
	 * 
	 * @return the UI object.
	 */
	protected JFrame createGUI() {
		return new LemmingsGUI(this.environment);
	}
	
	/**
	 * @param args are the command line arguments
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		parser = new XMLParser();
		
		//Recherche du xml de creation du monde par l'utilisateur
		InputStream is;
		JFrame guiFrame = new JFrame();
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(guiFrame);
		String fileIn = null;
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			fileIn = fc.getSelectedFile().getAbsolutePath();
			
		}
		System.out.println("file " + fileIn);
		//Parse le fichier xml
		parser.readXML(fileIn);
		Game game = new Game();
		game.run();
		System.exit(0);
	}
	
}
