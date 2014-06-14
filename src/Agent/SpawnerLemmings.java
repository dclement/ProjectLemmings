package Agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Agent.DecisionTree.DecisionNode;
import environment.Environment;
import launcher.FrameworkLauncher;

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
	private DecisionNode graph; 
	public int posx;
	public int posy;
	public Environment env;
	private List<LemmingMind> Lemmings = new ArrayList<LemmingMind>();
	private DecisionNode[][] worldmodel; //TODO we shouldn't create this world here but in an upper kindof thingy should be 

	public SpawnerLemmings(int lemmingsCount, Environment env, int posx, int posy) {
		this.numberLemmingsLeft = lemmingsCount;
		this.env = env;
		this.posx = posx;
		this.posy = posy;
		this.worldmodel = new DecisionNode[env.getWidth()][];
		for(int i =0; i<env.getWidth();i++){
			this.worldmodel[i] = new DecisionNode[env.getHeight()];
			for(int j=0; j<env.getHeight();j++){
				this.worldmodel[i][j]=null;
			}
		}
		graph = new DecisionNode(worldmodel,new Point(this.posx, this.posy));
		
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
			LemmingMind lem = new LemmingMind(graph);
			FrameworkLauncher.launchAgent(lem,new Point(this.posx, this.posy));
			Lemmings.add(lem);
			this.numberLemmingsLeft--;
		}
	}
	
	public List<LemmingMind> getLemmingList()
	{
		return this.Lemmings;
	}
	
	
}
