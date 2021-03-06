
package environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.janusproject.kernel.address.AgentAddress;

import Agent.LemmingsBody;
import Util.Position;
import Util.XMLParser;

/**
 * Environnement
 * @author Clement
 *
 */
public class Environment {

	private final AtomicBoolean changed = new AtomicBoolean();

	
	
	/** Event listeners.
	 */
	private final List<GameListener> listeners = new ArrayList<GameListener>();

	/** Matrix of the cells.
	 */
	private final EnvironmentObject[][] grid;
	/** Given an indication of time progression: even or odd time.
	 */
	private boolean isEvenTime = true;
	
	/** Width of the world.
	 */
	private final int width;

	/** Height of the world.
	 */
	private final int height;

	private final Map<AgentAddress,Body> bodies = new TreeMap<AgentAddress,Body>();
	
	/**
	 * Ressource contenant les donn�es de l'environnement a creer
	 */
	private final XMLParser parser;
	
	/**
	 * End level
	 */
	private int totalLemmingsFinish;
	/**
	 * @param width is the width of the world.
	 * @param height is the height of the world.
	 * @param walls 
	 * @param parser 
	 */
	public Environment(int width, int height, XMLParser parser) {
		this.width = width;
		this.height = height;
		this.grid = new EnvironmentObject[width][height];
		this.parser = parser;
		//Generation de la carte
		GenerationMap(this.width,this.height);
	}
	
	/**
	 * Generation de la disposition de l'env
	 * @param width2 dimension de l'env
	 * @param height2 dimension de m'env
	 */
	private void GenerationMap(int width2, int height2) {
		// TODO 
		// Generation de carte par fichier externe xml par exemple
		int x,y;
		
		ArrayList<Position> list = parser.Walls;
		for(Position p :list)
		{
			this.grid[p.getX()][p.getY()]=new Wall();
			for(int i=0;i<p.getWidth();i++)
			{
				int currentx = p.getX()+i;
				this.grid[currentx][p.getY()] = new Wall();
				for(int j=0;j<p.getHeight();j++)
				{
					this.grid[currentx][p.getY()+j+1] = new Wall();
				}
			}
		}
		this.grid[parser.endAreax][parser.endAreay] = new EndArea();
		ArrayList<Position> listJump = parser.Jump;
		for(Position p :listJump)
		{
			this.grid[p.getX()][p.getY()]=new Jump();
		}
		ArrayList<Position> listPike = parser.Pike;
		for(Position p :listPike)
		{
			this.grid[p.getX()][p.getY()]=new Pike();
		}
		/**
		 * Bord de la carte
		 */
		for(int i=0;i<width2;i++)
		{
			this.grid[i][0] = new Wall();
			this.grid[i][height2-1] = new Wall();
		
		}
		for(int i=0;i<height2;i++)
		{
			this.grid[0][i] = new Wall();
			this.grid[width2-1][i] = new Wall();
		}
	}

	/** Replies if the environment has an even value for its loop step counter.
	 * 
	 * @return <code>true</code> if the simulation is even, <code>false</code>
	 * if it is odd.
	 */
	public synchronized boolean isEvenTime() {
		return this.isEvenTime;
	}
	
	/** Add listener.
	 * 
	 * @param listener
	 */
	public synchronized void addGameListener(GameListener listener) {
		if (listener!=null) this.listeners.add(listener);
	}

	/** Remove listener.
	 * 
	 * @param listener
	 */
	public synchronized void removeGameListener(GameListener listener) {
		if (listener!=null) this.listeners.remove(listener);
	}

	
	/** Replies the width of the world.
	 * 
	 * @return the width of the world.
	 */
	public int getWidth() {
		return this.width;
	}

	/** Replies the height of the world.
	 * 
	 * @return the height of the world.
	 */
	public int getHeight() {
		return this.width;
	}

	/** Replies the environmental object inside the cell at the given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return the object or <code>null</code> if none.
	 */
	public synchronized EnvironmentObject getObject(int x, int y) {
		if (x>=0 && y>=0 && x<this.width && y<this.height)
			return this.grid[x][y];
		return null;
	}

	/** Replies if the cell at the given coordinates is empty.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if the cell is empty; <code>false</code>
	 * if not empty.
	 */
	public synchronized boolean isFree(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& this.grid[x][y]==null);
	}

	/** Replies if the cell at the given coordinates contains a wall etc.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if the cell contains a wall,
	 * otherwise <code>false</code>.
	 */
	public synchronized boolean isWall(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y] instanceof Wall));
	}

	public synchronized boolean isLemmings(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y] instanceof LemmingsBody));
	}
	
	public synchronized boolean isEndArea(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y] instanceof EndArea));
	}
	
	/** Replies if the cell at the given coordinates contains an occlusing object.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if the cell contains an occluding object,
	 * otherwise <code>false</code>.
	 */
	public synchronized boolean isOccluder(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& this.grid[x][y]!=null && this.grid[x][y].isOccluder());
	}

	/** Replies if the cell at the given coordinates contains a pickable object.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if the cell contains a pickable object,
	 * otherwise <code>false</code>.
	 */
	public synchronized boolean isPickable(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& this.grid[x][y]!=null && this.grid[x][y].isPickable());
	}

	public synchronized void move(Body body, Direction dir) {
		Point position = getPosition(body);
		if (position!=null && dir!=null) {
			int x = position.x + dir.dx;
			int y = position.y + dir.dy;
			if ((x!=position.x || y!=position.y)
					&&
					x>=0 &&
					x<this.width &&
					y>=0 &&
					y<this.height) {
						//Position desir�e libre 
						if(isFree(x, y))
						{
							this.grid[x][y] = body;
							this.grid[position.x][position.y] = null;
							if (dir.name().contains("EAST"))
							{
								body.setOrientation(Direction.EAST);
							}
							else if(dir.name().contains("WEST"))
								{
									body.setOrientation(Direction.WEST);
								}
							
							if(isPike(x, y+1))
							{
								System.out.println("Dead by pick !! ");
								//suppression du body dans la grille
								this.grid[x][y] = null;
								body.setDead(true,DeathReason.DEATH);
							}
						}
						else
						{
							if(isEndArea(x, y))
							{
								System.out.println("Lemmings arriv� !! ");
								//suppression du body dans la grille
								body.setDead(true,DeathReason.ARRIVED);
								this.grid[position.x][position.y] = null;
								setTotalLemmingsFinish(getTotalLemmingsFinish() + 1);
							}
						}	
			}
		}
		else
		{
			//Direction lemmings null mais le corps est peut etre en levitation 
			if(position!=null)
			{
				if(body.isFalling() && isPike(position.x, position.y+1))
				{
					System.out.println("Chute Dead by pick !! ");
					body.setDead(true,DeathReason.DEATH);
					this.grid[position.x][position.y] = null;
				}
			}
		}
	}
	//TODO en vrai je comprend pas � quoi servent ces environments Change et init.getAndSet blablabla
	public void runBehaviour() 
	{
		
		for(Body body : this.bodies.values())
		{
			Influence mi = body.getInfluence();
			
			if(mi!=null)
			{
				switch( mi.getAction())
				{
				case DIG:
					dig(body,mi.getDirection(),mi);
					break;
				case BRIDGE:
					bridge(body,mi.getDirection(),mi);
					break;
				case WALK:
					walk(body,mi.getDirection(),mi);
					break;
				case FALL:
					fall(body,mi.getDirection(),mi);
					break;
				default:
					break;
				}
				//TODO implement jumps!
				//this.move(body, mi.getDirection());
				
			}
		}
		List<Perception> list;
		for(Body body : this.bodies.values()) 
		{
			list = perceive((AIBody) body);
			if (list==null) 
			{
				list = Collections.emptyList();
				body.setPerceptions(list);
			}
		}
	}

	private void fall(Body body, Direction direction, Influence influence) {
		Point position = getPosition(body);
		if(this.grid[position.x][position.y+1]==null){
			body.setAppliedInfluence(influence);
			move(body, influence.getDirection());
		}
			
	}

	private void walk(Body body, Direction direction, Influence influence) {
		//Recupere la position du corps, applique la direction
		Point position = getPosition(body);
		if (position!=null && direction!=null) 
		{
			//Verifie si le block sur lequel on marche n'est pas vide
			if(this.grid[position.x][position.y+1]!=null)
			{
				body.setFalling(false);
				int x = position.x + direction.dx;
				int y = position.y + direction.dy;
				//V�rifie que ce soit ni en bordure ni un block
				if(x>=0 && y>=0 && x<this.width && y<this.height && this.grid[x][y]==null)
				{
					body.setAppliedInfluence(influence);
					move(body,direction);
				}else
				{				
					//On adapte l'influence
					Influence mi = new Influence(influence.getEmiter(), direction.opposite(), Action.WALK);
					body.setAppliedInfluence(mi);
					//Si block vide mais pas en bordure alors on fait un simple Walk
					move(body,direction);
				}
			}else
			{
				body.setFalling(true);
				Influence mi = computeFall(influence,position.x, position.y);
				
				body.setAppliedInfluence(mi);
				move(body,mi.getDirection());
			}
		}
	}

	private void bridge(Body body, Direction direction,Influence influence) {
		//Recupere la position du corps, applique la direction
		Point position = getPosition(body);
		if (position!=null && direction!=null) 
		{
			//Verifie si le block sur lequel on marche n'est pas vide
			if(this.grid[position.x][position.y+1]!=null)
			{
				body.setFalling(false);
				int x = position.x + direction.dx;
				int y = position.y + direction.dy;
				//V�rifie que ce soit ni en bordure ni un block
				if(x>=0 && y>=0 && x<this.width && y<this.height && this.grid[x][y+1]==null)
				{
					this.grid[x][y+1]=new Wall();
					body.setAppliedInfluence(influence);
					move(body,direction);
				}else if(x>=0 && y>=0 && x<this.width && y<this.height)
				{				
					//On adapte l'influence
					Influence mi = new Influence(influence.getEmiter(), direction, Action.WALK);
					body.setAppliedInfluence(mi);
					//Si block vide mais pas en bordure alors on fait un simple Walk
					move(body,direction);
	
				}else
				{
					Influence mi = new Influence(influence.getEmiter(), direction.opposite(), Action.WALK);
					body.setAppliedInfluence(mi);
					move(body,direction.opposite());
				}
			}else
			{
				body.setFalling(true);
				Influence mi = computeFall(influence,position.x, position.y);
				
				body.setAppliedInfluence(mi);
				move(body,mi.getDirection());
			}
		}
	}
	
	private Influence computeFall(Influence inf, int coordx, int coordy){
		Influence mi=new Influence(inf.getEmiter(),Direction.SOUTH,Action.FALL); 
		Direction direction = inf.getDirection();
		/*if(direction.dx>0 && this.grid[coordx+1][coordy+1]==null)
			mi = new Influence(inf.getEmiter(), Direction.SOUTHEAST, Action.FALL);
		else{
			if(direction.dx<0 && this.grid[coordx-1][coordy+1]==null){
				mi = new Influence(inf.getEmiter(), Direction.SOUTHWEST, Action.FALL);	
			}
			else
				mi = new Influence(inf.getEmiter(), Direction.SOUTH, Action.FALL);
		}*/
		return mi;
	}
	
	
	private void dig(Body body, Direction direction,Influence influence) {
		//Recupere la position du corps, applique la direction
		Point position = getPosition(body);
		if (position!=null && direction!=null) 
		{
			//Verifie si le block sur lequel on marche n'est pas vide
			if(this.grid[position.x][position.y+1]!=null)
			{
				body.setFalling(false);
				int x = position.x + direction.dx;
				int y = position.y + direction.dy;
				//V�rifie que ce soit ni un block de bordure ni un block vide
				if(x>=0 && y>=0 && x<this.width && y<this.height && this.grid[x][y]!=null)
				{
					this.grid[x][y]=null;
					move(body,direction);
					body.setAppliedInfluence(influence);					
				}else if(x>=0 && y>=0 && x<this.width && y<this.height)
				{
					//Si block vide mais pas en bordure alors on fait un simple Walk
					move(body,direction);
					//On adapte l'influence
					Influence mi = new Influence(influence.getEmiter(), direction, Action.WALK);
					body.setAppliedInfluence(mi);
				}else
				{
					Influence mi = new Influence(influence.getEmiter(), direction.opposite(), Action.WALK);
					body.setAppliedInfluence(mi);
					move(body,direction.opposite());
				}
			}else
			{
				body.setFalling(true);
				Influence mi = computeFall(influence,position.x, position.y);
				
				
				body.setAppliedInfluence(mi);
				move(body,direction);
			}
		}
	}

	/** Retreive and reply the position of the given body.
	 * 
	 * @param body is the body to search for.
	 * @return the position; or <code>null</code> if the body
	 * was not found.
	 */
	public Point getPosition(Body body) {
		for(int x=0; x<this.width; x++) {
			for(int y=0; y<this.height; y++) {
				if (this.grid[x][y]==body) {
					return new Point(x,y);
				}
			}
		}
		return null;
	}
	
	/** Compute the perceptions for the given AI body.
	 * 
	 * @param body is the body for which the perceptions must be computed.
	 * @return the perceptions, never <code>null</code>.
	 */
	synchronized List<Perception> perceive(AIBody body) {
		List<Perception> list = new LinkedList<Perception>();
		Point position = getPosition(body);
		if (position!=null) {
			
			int distance = body.getPerceptionDistance();
			Direction[] directions = Direction.values();

			BitSet sets = new BitSet(directions.length);
			sets.set(0,directions.length);

			int x, y, j;
			Perception perception;	
			// Perception sur les axes
			for(int i=1; i<=distance; i++) {
				for(Direction direction : directions) {			
					if (sets.get(direction.ordinal())) {
						x = position.x + (direction.dx * i);
						y = position.y + (direction.dy * i);
						if(direction.dx==0)
						{
							j=0;
						}
						else
						{
							j=i;
						}
						if (!isFree(x,y)) {
							perception = new Perception(
									isWall(x,y),
									direction,
									j,
									direction.dy*i,
									isEndArea(x, y),
									isLemmings(x,y),
									isJump(x, y),
									isPike(x,y));
							list.add(perception);
							if (isOccluder(x, y))
								sets.clear(direction.ordinal());
						}
					}
				}
			}
		}
		return list;
	}
			
	public int getTotalLemmingsFinish() {
		return totalLemmingsFinish;
	}

	public void setTotalLemmingsFinish(int totalLemmingsFinish) {
		this.totalLemmingsFinish = totalLemmingsFinish;
	}

	public synchronized boolean isJump(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y] instanceof Jump));
	}
	
	public synchronized boolean isPike(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y] instanceof Pike));
	}

	public synchronized void userEnvironnementChange(EnvironmentObject remplacement, int x, int y)
	{
		this.grid[x][y] = remplacement;
	}

	public void killAgentBody(AgentAddress agent) {
		// TODO Kill Body
		this.bodies.remove(agent);
	}

	public void spawnAgentBody(Agent.Animat<?> animat, Point position) {
		if (animat!=null) {
			Body body = animat.spawnBody(this);
			if (body!=null) {
				
				body.setPosition(position.x,position.y);
				this.grid[position.x][position.y]=body;
				this.bodies.put(animat.getAddress(), body);
			}
		}
		
	}
}
