package environment;

public enum Action {
	DIG,
	BRIDGE,
	CLIMB,
	WALK,
	FALLING,
	ANY;
	
	public boolean equals(Action action){
		if (action == ANY){
			return true;
		}
		else return equals((Object)action); 
	}
	
	
	
}
