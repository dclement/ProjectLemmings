package Agent.DecisionTree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import environment.Direction;
import environment.Influence;

public class DecisionNode implements Comparable {
	
	//may be useless
	private Point worldCoordinates;
	private Point startingPoint;
	private DecisionNode[][] nodeMap;
	//Default insertion strength
	public static float INSERTION_STRENGTH = 0.70f;
	
	
	private boolean entered=false;
		
	private List<DecisionLink> children;
	
	//ParentNode
	private List<DecisionLink> parents;
	
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
	
	public void enterNode(){
		if(!entered){
			children = new ArrayList<DecisionLink>();		
			for(Direction dir:Direction.values()){
				DecisionNode child;
				if(this.nodeMap[worldCoordinates.x][worldCoordinates.y]!=null){
					child = this.nodeMap[worldCoordinates.x][worldCoordinates.y];
				}
				else{
					child = new DecisionNode(this.nodeMap,new Point(this.worldCoordinates.x + dir.dx,this.worldCoordinates.y+dir.dy));
				}
				DecisionLink childlink = new DecisionLink(this,child,new Influence(dir),INSERTION_STRENGTH);
				child.addParent(childlink);;
				children.add(childlink);
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
