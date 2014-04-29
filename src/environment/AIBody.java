
package environment;

import java.util.List;

public abstract class AIBody extends Body {

	/** Is the perception distance.
	 */
	private final int distance;

	/**
	 * @param env is the environment in which the body is located.
	 * @param distance is the maximal distance of perception of the artificial intelligence.
	 * @param direction is the initial direction of the body.
	 */
	public AIBody(Environment env, int distance, Direction direction) {
		super(env, direction);
		this.distance = distance;
	}	

	/** Replies the maximal distance of perception.
	 * 
	 * @return the maximal distance of perception.
	 */
	public int getPerceptionDistance() {
		return this.distance;
	}
	
	/** Replies the objects around the body.
	 * 
	 * @return the list of the perceived objects.
	 */
	public List<Perception> perceive() {
		Environment e = getEnvironment();
		return e.perceive(this);
	}
	
}
