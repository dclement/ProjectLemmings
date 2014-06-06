package Agent.DecisionTree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import environment.Direction;
import environment.Influence;
import environment.MotionInfluence;

public class DecisionNode implements Comparable {
	
	//may be useless
	private  Point worldCoordinates;
	private Point startingPoint;
	
	//Default insertion strength
	public static float INSERTION_STRENGTH = 0.70f;
	
	private boolean entered=false;
	private Influence Influence;
	
	private Set<DecisionNode> children;
	
	//ParentNode
	private DecisionNode parent;
	
	//Strength to parent
	private float strength;
	
	static public int compare(DecisionNode dn1, DecisionNode dn2){
		return dn1.compareTo(dn2);
	}
	
	public int compareTo(DecisionNode dn) throws ClassCastException{
		return Float.compare(this.strength, dn.getStrengh());		
	}
	
	public DecisionNode(){
		//parentNode
		this.parent = null;
		this.strength = 1;
	}
	
	public DecisionNode(DecisionNode parent, Influence influence, float initialStrengh){
		this.strength = initialStrengh;
		this.parent = parent; 
		this.Influence = influence;
	}
	
	public void enterNode(){
		if(!entered){
			children = new TreeSet<DecisionNode>();		
			for(Direction dir:Direction.values()){
				children.add(new DecisionNode(this,new MotionInfluence(dir),INSERTION_STRENGTH));
			}
			//TODO add other kinds of influences ( actions, bridge, dig ... etc )
		}
	}

	public DecisionNode getBestChildren(){
		Iterator<DecisionNode> it = this.children.iterator();
		if(it.hasNext()){
			return it.next();
		}
		else{
			return null;
		}
	}
	
	public Influence getInfluence() {
		return Influence;
	}

	public void setInfluence(Influence influence) {
		Influence = influence;
	}

	public Set<DecisionNode> getChildren() {
		return children;
	}

	public void setChildren(Set<DecisionNode> children) {
		this.children = children;
	}

	public DecisionNode getParent() {
		return parent;
	}

	public void setParent(DecisionNode parent) {
		this.parent = parent;
	}

	public float getStrengh() {
		return strength;
	}

	public void setStrengh(float strengh) {
		this.strength = strengh;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
