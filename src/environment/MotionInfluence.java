package environment;

import org.janusproject.kernel.address.AgentAddress;

public class MotionInfluence {
	Direction direction;
	AgentAddress emiter; 
	
	public MotionInfluence(Direction dir){
		this(dir, null);
	}
	
	public MotionInfluence(Direction dir, AgentAddress emiter){
		this.direction = dir;
		this.emiter = emiter;
	}

	public MotionInfluence(){
		this(Direction.EAST);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public AgentAddress getEmiter() {
		return emiter;
	}

	public void setEmiter(AgentAddress emiter) {
		this.emiter = emiter;
	}
	
}
