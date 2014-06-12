package environment;

import org.janusproject.kernel.address.AgentAddress;

public class ActionInfluence extends Influence {

	Action action;
	
	public ActionInfluence(){
		super(null);
	}
	
	public ActionInfluence(AgentAddress emiter, Action action){
		super(emiter);
		this.action = action;
	}
	
}
