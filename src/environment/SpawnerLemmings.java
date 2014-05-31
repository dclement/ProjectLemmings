package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class servant a creer les agents au meme endroit de la carte a interval de temps régulier
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
	public Environment e;
	private List<LemmingMind> Lemmings = new ArrayList<LemmingMind>();
			
	public SpawnerLemmings(int lemmingsCount, Environment env, int posx, int posy) {
		this.numberLemmingsLeft = lemmingsCount;
		this.e = env;
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
				Thread.sleep(5000);
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
			//position x, position y, distance perception, direction départ, lemmings chute d'une case au depart
			LemmingsBody body = this.e.addLemmings(this.posx, this.posy, 1, Direction.EAST, true);
			LemmingMind Lem = new LemmingMind(body);
			Lemmings.add(Lem);
			this.numberLemmingsLeft--;
		}
	}
	
	public List<LemmingMind> getLemmingList()
	{
		return this.Lemmings;
	}
	
	
}
