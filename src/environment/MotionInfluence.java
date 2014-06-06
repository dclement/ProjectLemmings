package environment;

import org.janusproject.kernel.address.AgentAddress;

public class MotionInfluence extends Influence{
	Direction direction;
	
	public MotionInfluence(Direction dir){
		this(dir, null);
	}
	
	public MotionInfluence(Direction dir, AgentAddress emiter){
		super(emiter);
		this.direction = dir;
		
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

		
}
