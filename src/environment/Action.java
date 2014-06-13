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
	CLIMB {
		@Override
		public List<Direction> getPossibleDirections() {
			Direction[] dirs=new Direction[] {Direction.NORTHWEST, Direction.NORTHEAST};
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
			Direction[] dirs=new Direction[] {Direction.WEST, Direction.EAST};
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
			// TODO Auto-generated method stub
			return null;
		}
	};
	

	public Direction[] possibleDirection;
	
	public boolean equals(Action action){
		if (action == ANY){
			return true;
		}
		else return equals((Object)action);
	}
	
	public abstract List<Direction> getPossibleDirections();
	 
}
