package Agent;

import java.util.List;

import org.janusproject.kernel.address.AgentAddress;

import environment.AIBody;
import environment.Direction;
import environment.Environment;

/**
 * Corps des lemmings
 * @author Clement
 *
 */
public class LemmingsBody extends AIBody {

		/**
		 * @param env is the environment in which the body is located.
		 * @param distance is the maximal distance of perception of the artificial intelligence.
		 * @param direction is the initial direction of the body.
		 */
		public LemmingsBody(AgentAddress address, Environment env, int distance, Direction direction, boolean fall) {
			super(address, env, distance, direction, fall);
		}
		
				
		/** Change the state of the body: the direction and
		 * the state of the ghost.
		 * 
		 * @param dir is the direction to which the body should be moved.
		 * @param state is the ghost state to be displayed.
		 */
		public void move(Direction dir) {
			moveDirection(dir);
		}
}
