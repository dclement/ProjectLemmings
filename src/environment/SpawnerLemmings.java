package environment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class servant a creer les agents au meme endroit de la carte a interval de temps régulier
 * @author Clement
 *
 */
public class SpawnerLemmings{
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
	public int second;

	public SpawnerLemmings(int lemmingsCount, Environment env, int second, int posx, int posy) {
		this.numberLemmingsLeft = lemmingsCount;
		this.e = env;
		this.posx = posx;
		this.posy = posy;
		this.second = second;
	
	}
}
