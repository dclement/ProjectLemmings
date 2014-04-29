
package environment;

public abstract class Body implements EnvironmentObject {

	private final Environment environment;
	private Direction orientation;
	
	/**
	 * @param environment is the environment in which the body is located.
	 * @param direction is the initial direction of the body.
	 */
	public Body(Environment environment, Direction direction) {
		this.environment = environment;
		this.orientation = direction;
	}
	
	/**
	 * Change the orientation of the body.
	 * 
	 * @param direction is the new orientation.
	 */
	void setOrientation(Direction direction) {
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

	/**
	 * Give the pickable object to the body.
	 * 
	 * @param pickable is the picked object to give to the body. 
	 */
	void setPickable(EnvironmentObject pickable) {
		// Must be overridden in child classes
	}
	
}
