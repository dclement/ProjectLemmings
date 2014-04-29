
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

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
	private final int LemmingsCount = 5;
	private final List<LemmingMind> Lemmings = new ArrayList<LemmingMind>();
	private Timer timer;
	private SpawnerLemmings sp;
	private int temps = 2;          
	/**
	 * Constructeur du jeu
	 * @param width dimension de l'env
	 * @param height dimension de l'env
	 */
	public Game(int width, int height) {
		this.environment = new Environment(width,height);
		Random rnd = new Random();
		timer = new Timer();
		sp = new SpawnerLemmings(LemmingsCount, environment, temps, 20, 20);
		for(int i=0; i<LemmingsCount; i++) {
			int x,y;
			do {
				x = rnd.nextInt(width);
				y = rnd.nextInt(height);
			}
			while (!this.environment.isFree(x,y));
			LemmingsBody body = this.environment.addLemmings(x, y, width/2, Direction.NORTH);
			LemmingMind Lem = new LemmingMind(body);
			this.Lemmings.add(Lem);
		}
	}
	
	/** Run the Lemmings game.
	 */
	public void run() {
		
		JFrame window = createGUI();
		
		if (window!=null) window.setVisible(true);
				
		while (!this.stop.get()) {
			
			
			for(LemmingMind Lem : this.Lemmings) {
				Lem.live();
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
	 */
	public static void main(String[] args) {
		Game game = new Game(28,28);
		game.run();
		System.exit(0);
	}
	
}
