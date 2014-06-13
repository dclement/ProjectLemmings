package Agent.DecisionTree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
	public static float INSERTION_STRENGTH = 0.60f;
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
	
	public void updateChildren(List<Perception> percepts){
		
	}

	public void updateParentsOnRemove() {
		
		//removing now wrong links
		Stack<DecisionLink> parentToRemove= new Stack();
		for(DecisionLink parent:this.getParents()){
			Action act = parent.getInfluence().getAction();
			if(act==Action.DIG){ //The action Ain't valid noO more NoOo
				parent.getParent().removeChildren(parent);
				parentToRemove.push(parent);
			}
		}
		while(!parentToRemove.isEmpty()){
			this.parents.remove(parentToRemove.pop());
		}

		//Adding new possible Links 
		for(int i=-1; i<=1;i++){
			for(int j =-1 ; j<=1; j++){
				DecisionNode node= nodeMap[this.worldCoordinates.x+i][this.worldCoordinates.y+j];
				if(node!=null){
					Direction dir=Direction.createDirection(i, j);
					List<Influence> newPossibleInfluence = new ArrayList<Influence>();
					switch(dir){
					case NORTHWEST:
						newPossibleInfluence.add(new Influence(Direction.SOUTHEAST,Action.FALL));
						newPossibleInfluence.add(new Influence(Direction.SOUTHEAST,Action.BRIDGE));
						break;
					case NORTH:
						newPossibleInfluence.add(new Influence(Direction.SOUTHEAST,Action.FALL));
						break;
					case NORTHEAST:
						newPossibleInfluence.add(new Influence(Direction.SOUTHWEST,Action.FALL));
						newPossibleInfluence.add(new Influence(Direction.SOUTHWEST,Action.BRIDGE));
						break;
					case EAST:
						newPossibleInfluence.add(new Influence(Direction.WEST,Action.WALK));
						break;
					case WEST:
						newPossibleInfluence.add(new Influence(Direction.EAST,Action.WALK));
						break;
					case SOUTHEAST:
						newPossibleInfluence.add(new Influence(Direction.NORTHWEST,Action.JUMP));
						break;
					case SOUTHWEST:
						newPossibleInfluence.add(new Influence(Direction.NORTHEAST,Action.JUMP));
						break;
					default:
						break;
					}
					for(Influence influ: newPossibleInfluence){
						float mod = (influ.getAction() == Action.WALK)? 0.1f : -0.1f;
						mod = (influ.getAction()==Action.FALL) ? mod : 0f; 
						DecisionLink childlink = new DecisionLink(node,this,influ,INSERTION_STRENGTH+mod);
						this.addParent(childlink);
						node.getChildren().add(childlink);
					}
				
				}				
			}
		}
	}
	
	public void updateParentsOnAdd(){
		//removing now wrong links
		Stack<DecisionLink> parentToRemove= new Stack<DecisionLink>();
		for(DecisionLink parent:this.getParents()){
			Action act = parent.getInfluence().getAction();
			if(act==Action.WALK||act==Action.FALL||act==Action.BRIDGE){ //These actions Ain't valid noO more NoOo
				parent.getParent().removeChildren(parent);
				parentToRemove.push(parent);
			}
		}
		while(!parentToRemove.isEmpty()){
			this.parents.remove(parentToRemove.pop());
		}
		
		for(int i=-1; i<=1;i++){
			for(int j =-1 ; j<=1; j++){
				DecisionNode node= nodeMap[this.worldCoordinates.x+i][this.worldCoordinates.y+j];
				if(node!=null){
					Direction dir=Direction.createDirection(i, j);
					List<Influence> newPossibleInfluence = new ArrayList<Influence>();
					switch(dir){
					case EAST:
						newPossibleInfluence.add(new Influence(Direction.WEST,Action.DIG));
						break;
					case WEST:
						newPossibleInfluence.add(new Influence(Direction.EAST,Action.DIG));
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	
	
	private void removeChildren(DecisionLink parent) {
		this.parents.remove(parent);		
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
				if((perceptionMap[dir.dx+2][dir.dy+2].isFree()&& action != Action.DIG && action!=Action.JUMP) || (action == Action.DIG && (perceptionMap[dir.dx+2][dir.dy+2].isWall()||perceptionMap[dir.dx+2][dir.dy+2].isJump()) ) || (action==Action.JUMP && perceptionMap[dir.dx+2][dir.dy+2].isFree() && perceptionMap[2][3].isJump())){
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
				
				//Walking is the best decision EVARRR
				float mod = (inf.getAction() == Action.WALK)? 0.1f : -0.1f;
				mod = (inf.getAction()==Action.FALL) ? mod : 0f; 
				
				DecisionLink childlink = new DecisionLink(this,child,inf,INSERTION_STRENGTH+mod);
				child.addParent(childlink);
				children.add(childlink);
			}
			//TODO add other kinds of influences ( actions, bridge, dig ... etc )
		}
	}

	public DecisionNode getChildWithInfluence(Influence in){
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
