package Agent.DecisionTree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import environment.Action;
import environment.Direction;
import environment.Environment;
import environment.Influence;
import environment.Perception;

public class DecisionNode implements Comparable {
	
	//may be useless
	private Point worldCoordinates;
	private Point startingPoint;
	private DecisionNode[][] nodeMap;
	//Default insertion strength
	public static float INSERTION_STRENGTH = 0.70f;
	//used to build children nodes taking into account the environment 
		
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
	
	
	public DecisionNode(DecisionNode[][] nodeMap,Point wc){
		//parentNode
		this.worldCoordinates = wc;
		this.entered=false; 
		this.parents = new ArrayList<DecisionLink>();
		this.nodeMap[wc.x][wc.y] = this;
	}
	
	public DecisionNode(DecisionLink parent,DecisionNode[][] nodeMap,Point wc){
		this(nodeMap,wc);
		this.parents.add(parent); 
	}
	
	
	
	public List<Influence> getPossibleInfluences(List<Perception> percepts){
		List<Influence> retList = new ArrayList<Influence>();
		
		Perception[][] perceptionMap = new Perception[5][];
		for(int i =0 ; i < perceptionMap.length;i++){
			perceptionMap[i] = new Perception[5];
		}
		
		for(int i=0; i< percepts.size();i++){
			Perception percept= percepts.get(i);
			perceptionMap[percept.getDirection().dx*percept.getDistance()][percept.getDirection().dy*percept.getDistance()] = percept; 
		}
		
		for(Action action : Action.values()){
			List<Direction> pdir= action.getPossibleDirections();
			for(Direction dir: pdir){
				if((perceptionMap[dir.dx][dir.dy].isFree()&& action != Action.DIG) || (action == Action.DIG && (perceptionMap[dir.dx][dir.dy].isWall()||perceptionMap[dir.dx][dir.dy].isJump()) )){
					retList.add(new Influence(dir,action));
				}
			}
		}
		return retList;
	}
	
	
	public void enterNode(List<Perception> percepts){ //TODO add a way to build the node taking the environment surroundings into account
		if(!entered){
			children = new ArrayList<DecisionLink>();	
			
			List<Influence> possibleInfluences = getPossibleInfluences(percepts);
			for(Influence inf:possibleInfluences){
				DecisionNode child;
				if(this.nodeMap[worldCoordinates.x][worldCoordinates.y]!=null){
					child = this.nodeMap[worldCoordinates.x][worldCoordinates.y];
				}
				else{
					child = new DecisionNode(this.nodeMap,new Point(this.worldCoordinates.x + inf.getDirection().dx,this.worldCoordinates.y+inf.getDirection().dy));
				}
				DecisionLink childlink = new DecisionLink(this,child,inf,INSERTION_STRENGTH);
				child.addParent(childlink);
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
