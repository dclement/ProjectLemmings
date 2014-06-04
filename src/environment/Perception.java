
package environment;

public class Perception {

	private final boolean isWall;
	private final boolean isEndArea;
	private final Direction direction;
	private final int distance;
	private final boolean isLemmings;
	private final int hauteur;
	private final boolean isJump;
	private final boolean isPike;
	
	public Perception(boolean wall, Direction direction, int distance, int hauteur, boolean endArea, boolean Lemmings, boolean Jump, boolean Pike) {
		
		
		PerceptionType type;
		this.isWall = wall;
		this.direction = direction;
		this.distance = distance;
		this.isEndArea = endArea;
		this.isLemmings = Lemmings;
		this.hauteur = hauteur;
		this.isJump = Jump;
		this.isPike = Pike;
	}
	
	/** Replies if the perceived object is a wall.
	 * 
	 * @return <code>true</code> if wall.
	 */
	public boolean isWall() {
		return this.isWall;
	}
	
	/** Replies if the perceived object is a EndArea.
	 * 
	 * @return <code>true</code> if wall.
	 */
	public boolean isEndArea() {
		return this.isEndArea;
	}
	
	/** Replies if this object is a free cell.
	 * 
	 * @return <code>true</code> if free cell.
	 */
	public boolean isFree() {
		return !this.isWall && !this.isEndArea;
	}
	
	/** Replies if direction at which the object was perceived.
	 * 
	 * @return the direction of the perceived object.
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/** Replies the distance to the perceived object.
	 * 
	 * @return the distance to the object.
	 */
	public int getDistance() {
		return this.distance;
	}
	
	/** Replies if the perceived object is a Lemmings.
	 * 
	 * @return <code>true</code> if lemmings.
	 */
	public boolean isLemmings() {
		return this.isLemmings;
	}
	
	/** Replies the distance to the perceived object.
	 * 
	 * @return the distance to the object.
	 */
	public int getHauteur() {
		return this.hauteur;
	}
	
	/** Replies if the perceived object is a Jump.
	 * 
	 * @return <code>true</code> if jump.
	 */
	public boolean isJump() {
		return this.isJump;
	}
	
	/** Replies if the perceived object is a Pike.
	 * 
	 * @return <code>true</code> if pike.
	 */
	public boolean isPike() {
		return this.isPike;
	}
}
