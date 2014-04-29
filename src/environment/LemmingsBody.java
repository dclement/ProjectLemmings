package environment;

import java.util.List;

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
		public LemmingsBody(Environment env, int distance, Direction direction) {
			super(env, distance, direction);
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


		public List<Perception> perceive() {
			// TODO Auto-generated method stub
			return null;
		}
}
