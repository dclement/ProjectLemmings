package environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Action {
	DIG{
		@Override
		public List<Direction> getPossibleDirections(){
			Direction[] dirs=new Direction[] {Direction.WEST, Direction.EAST, Direction.SOUTH};
			return  Arrays.asList(dirs); 	
		}
	},
	BRIDGE {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.WEST, Direction.EAST};
			return  Arrays.asList(dirs);
		}
	},
	JUMP {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.NORTHWEST, Direction.NORTHEAST};
			return  Arrays.asList(dirs);
		}
	},
	WALK {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.EAST,Direction.WEST};
			return  Arrays.asList(dirs);
		}
	},
	FALL {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHWEST};
			return  Arrays.asList(dirs);
		}
	},
	ANY {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.ANY};
			return Arrays.asList(dirs);
		}
	};
	

	public Direction[] possibleDirection;
	
	public static boolean equal(Action act1, Action act2){
		if (act1 == ANY || act2==ANY){
			return true;
		}
		else {
			boolean retval = (act1==act2);
			return 	retval;
		}
	}
	
	public abstract List<Direction> getPossibleDirections();
	 
}
