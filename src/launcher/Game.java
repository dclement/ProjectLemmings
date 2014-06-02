package launcher;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Agent.LemmingMind;
import Agent.LemmingsBody;
import Agent.SpawnerLemmings;
import Util.XMLParser;
import environment.Direction;
import environment.Environment;
import gui.LemmingsGUI;
import gui.StartGUI;

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
	public static StartGUI FrameStart;
	
	public final int EXECUTION_DELAY = 200;
	private LemmingsGUI GUI;
	
	/**
	 * Constructeur du jeu
	 */
	public Game(String fileIn) {
		parser = new XMLParser();
		try {
			parser.readXML(fileIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		this.environment = new Environment(parser.width,parser.height,parser);
		JFrame window = createGUI();
		FrameworkLauncher.launchEnvironment(environment, this.GUI, EXECUTION_DELAY);
		
		sp = new SpawnerLemmings(parser.numberLemmings, environment, parser.spx, parser.spy);
		LemmingMind lem = new LemmingMind();
		FrameworkLauncher.startSimulation();
		
	}
	
	/** Run the Lemmings game.
	 */
	public void run() {
		
		
		
		JFrame window = this.GUI;
		
		if (window!=null) window.setVisible(true);

		//Lancement du thread de creation des agents
		Thread T = new Thread(sp);
		T.start();		
		
		
		//TODO since we now run a JANUS kernel, we shouldn't need this anymore
		while (!this.stop.get()) {
			//Y a t il des agents ?
			/*
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
			*/
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
		this.GUI = new LemmingsGUI(this.environment); 
		return this.GUI;
		
	}
	
/*
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		/*parser = new XMLParser();
		
		FrameStart = new StartGUI();
		if (FrameStart!=null) FrameStart.setVisible(true);
		while(!FrameStart.StartSimulation)
		{
			System.out.println("Wait launch");
		}
		FrameStart.setVisible(false);*/
		//Recherche du xml de creation du monde par l'utilisateur
	/*
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
		if(fileIn!=null)
		{
			parser.readXML(fileIn);
			Game game = new Game();
			game.run();
		}
		System.exit(0);
		
	}*/
	
}
