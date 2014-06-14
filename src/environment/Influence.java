package environment;

import org.janusproject.kernel.address.AgentAddress;

public class Influence {
	private AgentAddress emiter; 
	
	private Direction direction;
	private Action action; 
	
	public Influence(AgentAddress emiter){
		this.emiter = emiter;
	}
	
	public Influence(){
		this((AgentAddress) null);
	}

	public Influence(Direction dir){
		this(null, dir);
	}
	
	public Influence(Direction dir, Action act){
		this(null, dir,act);
	}
	
	public Influence(AgentAddress emiter, Direction dir){
		this(emiter, dir, Action.WALK);
	}
	
	public Influence(AgentAddress emiter, Direction dir, Action act){
		this(emiter);
		this.direction = dir;
		this.action = act;
	}
		
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public AgentAddress getEmiter() {
		return emiter;
	}

	public void setEmiter(AgentAddress emiter) {
		this.emiter = emiter;
	}
	
	
	@Override
	public String toString() {
		return "Influence [direction=" + direction + ", action=" + action + ", hash=" + System.identityHashCode(this) +  "]";
	}

	@Override
	public boolean equals(Object info){ // We don't want it only to be equal when the object is the same but also when the content is the same
		Influence inf = (Influence) info;
		if(inf==null)
			return false; 
		if(Action.equal(inf.getAction(), this.action) && inf.getDirection()==this.direction)
			return true;
		else 
			return false;
	}
	
}
