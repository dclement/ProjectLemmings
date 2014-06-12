package Agent.DecisionTree;

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
