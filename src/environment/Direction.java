
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
	},
	/** SouthWest. */
	SOUTHWEST(-1,+1) {
		@Override
		public Direction opposite() { return SOUTHEAST; }
	},
	/** NorthEast. */
	SOUTHEAST(+1,+1) {
		@Override
		public Direction opposite() { return SOUTHWEST; }
	},
	ANY(0,0){
		public Direction opposite(){return ANY;}
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

	public static Direction createDirection(int x , int y ){
		if(x==-1 && y==-1) return NORTHWEST;
		if(x==0 && y==-1) return NORTH; 
		if(x==1 && y ==-1) return NORTHEAST;
		if(x==-1 && y==0) return WEST;
		if(x==0 && y ==0) return ANY;
		if(x==1 && y==0) return EAST; 
		if(x==-1 && y==1) return SOUTHWEST;
		if(x==0 && y==1) return SOUTH;
		if(x==1 && y==1) return SOUTHEAST;
		return ANY;
	}
	
	public static boolean isAlike(Direction dir , Direction dir2){
		if(dir==ANY || dir2 == ANY){
			return true;
		}
		if(dir.dx !=0){
			if(dir2.dx==dir.dx)
				return true;
		}
		if(dir.dy !=0){
			if(dir2.dy == dir.dy)
				return true;
		}
		return false;
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
