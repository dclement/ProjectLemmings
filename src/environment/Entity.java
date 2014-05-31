
package environment;

public interface Entity<BT extends Body> {
	
	/** Run the living behavior of the entity for one simulation step.
	 */
	public void live();

	/** Replies the body attached to this living entity.
	 * 
	 * @return the body, or <code>null</code> if the living entity
	 * has not body yet.
	 */
	public BT getBody();
	
}
