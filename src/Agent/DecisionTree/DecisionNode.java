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
		
	private List<DecisionNode> children;
	
	//ParentNode
	private List<DecisionParent> parents;
	
	public DecisionNode(){
		//parentNode
		this.entered=false; 
		this.parents = new ArrayList<DecisionParent>();
	}
	
	public DecisionNode(DecisionParent parent){
		this();
		this.parents.add(parent); 
		
	}
	
	public DecisionNode(List<DecisionParent> parents){
		this.entered = false; 
		this.parents = parents; 
	}
	
	public void enterNode(){
		if(!entered){
			children = new ArrayList<DecisionNode>();		
			for(Direction dir:Direction.values()){
				children.add(new DecisionNode(new DecisionParent(this,new MotionInfluence(dir),INSERTION_STRENGTH)));
			}
			//TODO add other kinds of influences ( actions, bridge, dig ... etc )
		}
	}

	public float getStrenghWithParent(DecisionNode parent){
		float retval = -1;
		int i=0;
		while(i<parents.size() && parents.get(i).getParent()!=parent){
			i++;
		}
		
		if(i<parents.size()){
			retval = parents.get(i).getStrength();
		}
		return retval;
	}
	
	public DecisionNode getBestChildren(){
		if(children.size() ==0){
			return null;
		}
		float bestStrength=0;
		DecisionNode bestDecisionNode = children.get(0);
		for( DecisionNode child: children){
			float str = child.getStrenghWithParent(this);
			if(str>bestStrength)
				bestStrength = str; 
				bestDecisionNode = child;
		}
		
		return bestDecisionNode;
	}
	
	public List<DecisionNode> getChildren() {
		return children;
	}

	public void setChildren(List<DecisionNode> children) {
		this.children = children;
	}

	
	public List<DecisionParent> getParents() {
		return parents;
	}

	public void setParents(List<DecisionParent> parents) {
		this.parents = parents;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
