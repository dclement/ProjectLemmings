package Agent;

import org.janusproject.kernel.status.Status;

import environment.AIBody;
import environment.Body;
import environment.Environment;

public class Dummy extends Animat<AIBody>  {

	public Dummy(){
		
	}
	
	public Status live(){
		return null;
	}

	@Override
	protected AIBody createBody(Environment in) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
