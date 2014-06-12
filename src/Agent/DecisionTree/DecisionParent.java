package Agent.DecisionTree;

import environment.Influence;

public class DecisionParent {

	private final DecisionNode parent;
	private final Influence influence;
	private float strength;

	public DecisionParent(DecisionNode parent, Influence influence,	float strength) {
		super();
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

}
