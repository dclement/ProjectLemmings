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

	public void affectLink(float strength, Effect effect){
		if(effect == Effect.PENALIZE){
			float value = strength*this.strength;
			this.strength-=value;
		}
		else{
			float value = strength*(1-this.strength);
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

	@Override
	public String toString() {
		return "DecisionLink [parent=" + parent + ", influence=" + influence.toString()
				+ ", strength=" + strength + ", child=" + child + ", hash=" + System.identityHashCode(this)+ " ]";
	}

	
	
}
