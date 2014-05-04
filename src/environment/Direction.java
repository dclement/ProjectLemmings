
package environment;

import java.util.Random;


public enum Direction {
	/** West. */
	WEST(-1,0) {
		@Override
		public Direction opposite() { return EAST; }
	},
	/** East. */
	EAST(+1,0) {
		@Override
		public Direction opposite() { return WEST; }
	},
	/** North. */
	NORTH(0,-1) {
		@Override
		public Direction opposite() { return SOUTH; }
	},
	/** East. */
	SOUTH(0,+1) {
		@Override
		public Direction opposite() { return NORTH; }
	},
	/** NorthWest. */
	NORTHWEST(-1,-1) {
		@Override
		public Direction opposite() { return NORTHEAST; }
	},
	/** NorthEast. */
	NORTHEAST(+1,-1) {
		@Override
		public Direction opposite() { return NORTHWEST; }
	};
	
	/** Relative coordinate of the direction.
	 */
	public final int dx;
	
	/** Relative coordinate of the direction.
	 */
	public final int dy;
	
	/**
	 * @param x
	 * @param y
	 */
	Direction(int x, int y) {
		this.dx = x;
		this.dy = y;
	}

	/** Replies a random direction.
	 * 
	 * @return a random direction.
	 */
	public static Direction random() {
		Random rnd = new Random();
		return values()[rnd.nextInt(values().length)];
	}
	
	/** Replies the opposite direction.
	 * 
	 * @return the opposite direction.
	 */
	public abstract Direction opposite();
	
}
