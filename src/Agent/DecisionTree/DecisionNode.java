package Agent.DecisionTree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import environment.Action;
import environment.Direction;
import environment.Environment;
import environment.Influence;

public class DecisionNode implements Comparable {
	
	//may be useless
	private Point worldCoordinates;
	private Point startingPoint;
	private DecisionNode[][] nodeMap;
	//Default insertion strength
	public static float INSERTION_STRENGTH = 0.70f;
	//used to build children nodes taking into account the environment 
	private Environment env; 
	
	private boolean entered=false;
		
	private List<DecisionLink> children;
	
	//ParentNode
	private List<DecisionLink> parents;
	

	public DecisionLink getLink(DecisionNode parent) {
		int i=0; 
		while (i < parents.size() && parents.get(i).getParent()!=parent)
			i++;
		
		if(i==parents.size())
			return null;
		else 
			return parents.get(i);
	}
	
	
	public DecisionNode(DecisionNode[][] nodeMap,Point wc, Environment env){
		//parentNode
		this.env = env;
		this.worldCoordinates = wc;
		this.entered=false; 
		this.parents = new ArrayList<DecisionLink>();
		this.nodeMap[wc.x][wc.y] = this;
	}
	
	public DecisionNode(DecisionLink parent,DecisionNode[][] nodeMap,Point wc, Environment env){
		this(nodeMap,wc, env);
		this.parents.add(parent); 
	}
	
	public void enterNode(){ //TODO add a way to build the node taking the environment surroundings into account
		if(!entered){
			children = new ArrayList<DecisionLink>();		
			for(Direction dir:Direction.values()){
				DecisionNode child;
				if(this.nodeMap[worldCoordinates.x][worldCoordinates.y]!=null){
					child = this.nodeMap[worldCoordinates.x][worldCoordinates.y];
				}
				else{
					child = new DecisionNode(this.nodeMap,new Point(this.worldCoordinates.x + dir.dx,this.worldCoordinates.y+dir.dy),env);
				}
				DecisionLink childlink = new DecisionLink(this,child,new Influence(dir),INSERTION_STRENGTH);
				child.addParent(childlink);;
				children.add(childlink);
			}
			//TODO add other kinds of influences ( actions, bridge, dig ... etc )
		}
	}

	public DecisionNode getChildrenWithInfluence(Influence in){
		int i =0; 
		while(i<this.children.size() && !this.children.get(i).getInfluence().equals(in))
			i++;
		if(i == this.children.size()){
			return null;
		}
		else return this.children.get(i).getChild();
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
		DecisionNode bestDecisionNode = children.get(0).getChild();
		for( DecisionLink child: children){
			float str = child.getStrength();
			if(str>bestStrength){
				bestStrength = str; 
				bestDecisionNode = child.getChild();
			}
		}
		return bestDecisionNode;
	}
	
	public DecisionLink getBestChildrenWithCondition(Direction dir, Action action){
		if(children.size() ==0){
			return null;
		}
		float bestStrength=0;
		DecisionLink bestDecisionLink = null;
		for( DecisionLink child: children){
			float str = child.getStrength();
			if(str>bestStrength && child.getInfluence().getAction().equals(action)&& Direction.isAlike(child.getInfluence().getDirection(),dir)){
				bestStrength = str; 
				bestDecisionLink = child;
			}
		}
		return bestDecisionLink;
	}
	
	public List<DecisionLink> getChildren() {
		return children;
	}

	public void setChildren(List<DecisionLink> children) {
		this.children = children;
	}

	public void addParent(DecisionLink parent){
		this.parents.add(parent);
	}
	
	
	public List<DecisionLink> getParents() {
		return this.parents;
	}

	public void setParents(List<DecisionLink> parents) {
		this.parents = parents;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
