
package environment;

import java.awt.Point;
import java.util.List;

import org.janusproject.kernel.address.AgentAddress;

public abstract class Body implements EnvironmentObject {

	private final Environment environment;
	private Direction orientation;
	private boolean falling;
	private MotionInfluence motionInfluence; 

	private List<Perception> perceptions;
	
	private AgentAddress agentaddress; 
	
	/**
	 * @param environment is the environment in which the body is located.
	 * @param direction is the initial direction of the body.
	 * @param fall is the position of the lemmings in the env falling or not.
	 */
	public Body(AgentAddress address, Environment environment, Direction direction, boolean fall) {
		this.environment = environment;
		this.orientation = direction;
		this.agentaddress = address;
		this.setFalling(fall);
	}
	
	public AgentAddress getAgentaddress() {
		return agentaddress;
	}

	public void setAgentaddress(AgentAddress agentaddress) {
		this.agentaddress = agentaddress;
	}

	/**
	 * Change the orientation of the body.
	 * 
	 * @param direction is the new orientation.
	 */
	public void setOrientation(Direction direction) {
		this.orientation = direction;
	}
	
	/** Replies the orientation of the body.
	 * 
	 * @return the orientation of the body.
	 */
	public Direction getOrientation() {
		return this.orientation;
	}
	
	/** Replies the environment in which the body is located.
	 * 
	 * @return the environment.
	 */
	Environment getEnvironment() {
		return this.environment;
	}

	/** Move the body with an influence.
	 * 
	 * @param dir is the desired direction.
	 */
	public void moveDirection(Direction dir) {
		Environment e = getEnvironment();
		e.move(this,dir);
	}


	@Override
	public boolean isOccluder() {
		return false;
	}
	
	@Override
	public boolean isPickable() {
		return false;
	}
	//TODO return proper value
	public int getX(){
		return environment.getPosition(this).x;
	}

	//TODO return proper value	
	public int getY(){
		return environment.getPosition(this).y;
	}

	public Point getPosition(){
		return environment.getPosition(this);
	}
	
	public void setPosition(int i, int j) {
				
	}
	/**
	 * Give the pickable object to the body.
	 * 
	 * @param pickable is the picked object to give to the body. 
	 */
	void setPickable(EnvironmentObject pickable) {
		// Must be overridden in child classes
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean fall) {
		this.falling = fall;
	}

	public void setPerceptions(List<Perception> list) {
		this.perceptions = list; 
	}
	
	public List<Perception> getPerceptions(){
		return this.perceptions;
	}

	public MotionInfluence getMotionInfluence() {
		return motionInfluence;
	}

	public void setMotionInfluence(MotionInfluence  motionInfluence) {
		this.motionInfluence = motionInfluence;
	}
	
	public MotionInfluence consumeInfluence(){
		if(this.motionInfluence!=null){
			MotionInfluence inf = this.motionInfluence;
			this.motionInfluence = null;
			if(this.getAgentaddress()!= null){
				inf.setEmiter(this.getAgentaddress());
			}
			return inf;
		}
		return null;
	}
}
