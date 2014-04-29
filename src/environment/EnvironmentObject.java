package environment;

public interface EnvironmentObject {
	
	/** Replies if this object is occluding the
	 * agent's perceptions.
	 * If the object is occluding the perceptions,
	 * every object behind this object will be
	 * not seen by the agent.
	 * 
	 * @return <code>true</code> if the object is
	 * occluding, <code>false</code> otherwise.
	 */
	public boolean isOccluder();
	
	/** Replies if this object could be picked
	 * by the agents.
	 * 
	 * @return <code>true</code> if an agent
	 * could pick this object; <code>false</code>
	 * otherwise.
	 */
	public boolean isPickable();

}
