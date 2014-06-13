package Agent.DecisionTree;

import Agent.Effect;
import environment.Influence;

public class DecisionLink {

	private final DecisionNode parent;
	private final Influence influence;
	private float strength;
	private final DecisionNode child;
	
	public DecisionLink(DecisionNode parent,DecisionNode child, Influence influence,	float strength) {
		super();
		this.child = child; 
		this.strength = strength;
		this.parent = parent;
		this.influence = influence;
	}

	public void affectLink(double strength, Effect effect){
		if(effect == Effect.PENALIZE){
			double value = strength*this.strength;
			this.strength-=value;
		}
		else{
			double value = strength*(1-this.strength);
			this.strength += value;
		}
	}
	
	public float getStrength() {
		return strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

	public DecisionNode getParent() {
		return parent;
	}

	public Influence getInfluence() {
		return influence;
	}

	public DecisionNode getChild() {
		return child;
	}

}
