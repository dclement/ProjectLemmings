package environment;

import org.janusproject.kernel.address.AgentAddress;

public abstract class Influence {
	AgentAddress emiter; 
	
	public Influence(AgentAddress emiter){
		this.emiter = emiter;
	}
	
	public Influence(){
		this(null);
	}

	public AgentAddress getEmiter() {
		return emiter;
	}

	public void setEmiter(AgentAddress emiter) {
		this.emiter = emiter;
	}
	
	
	
}
