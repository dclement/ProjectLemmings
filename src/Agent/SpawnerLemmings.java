package Agent;

import java.awt.Point;

import launcher.FrameworkLauncher;
import environment.Environment;

/**
 * Class servant a creer les agents au meme endroit de la carte a interval de temps r�gulier
 * @author Clement
 *
 */
public class SpawnerLemmings extends Thread{
	/**
	 * Nombre d'agents a spawn
	 */
	public int numberLemmingsLeft;
	/**
	 * Position du spwaner
	 */
	public int posx;
	public int posy;
	public Environment env;
		
	public SpawnerLemmings(int lemmingsCount, Environment env, int posx, int posy) {
		this.numberLemmingsLeft = lemmingsCount;
		this.env = env;
		this.posx = posx;
		this.posy = posy;
	}
	
	/**
	 * Thread de creation des agents toutes les 5 seconds
	 */
	public void run()
	{
		while (true) {
			
			spawn();
			try {
				Thread.sleep(2000);
			}
			catch (InterruptedException e) {
				return;
			}
		}
		
	}
	
	public synchronized void spawn()
	{
		System.out.println("spawn  " + numberLemmingsLeft);
		if(this.numberLemmingsLeft>0)
		{
			//position x, position y, distance perception, direction d�part, lemmings chute d'une case au depart
			LemmingMind lem = new LemmingMind();
			FrameworkLauncher.launchAgent(lem,new Point(this.posx, this.posy));
			this.numberLemmingsLeft--;
		}
	}	
}
