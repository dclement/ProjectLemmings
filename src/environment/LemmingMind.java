package environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Lemmings esprit
 * @author Clement
 *
 */
public class LemmingMind implements Entity<LemmingsBody> {
		
		private final LemmingsBody body;
		private final Random rnd = new Random();
		
		/**
		 * @param body is the body to attached to this ghost.
		 */
		public LemmingMind(LemmingsBody body) {
			this.body = body;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public LemmingsBody getBody() {
			return this.body;
		}
		
		private List<Direction> extractFreeDirections(List<Perception> list) {
			List<Direction> freeDirections = new ArrayList<Direction>();
			freeDirections.addAll(Arrays.asList(Direction.values()));
			/*for(Perception obj : list) {
				if (obj.getDistance()<=1 &&) {
					freeDirections.remove(obj.getDirection());
				}
			}*/
			return freeDirections;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void live() {
			// Get the ghost body
			LemmingsBody b = getBody();

			// Get the perceptions from the body.
			List<Perception> perception = b.perceive();
			
			Direction desiredDirection = null;
			
			// Random direction when reaching an intersection
			List<Direction> freeDirections = extractFreeDirections(perception);
			
			if (!freeDirections.isEmpty()) {
				desiredDirection = freeDirections.get(this.rnd.nextInt(freeDirections.size()));
			}	
			
			
			// If the ghost decided to move, try to move the body accordingly.
			if (desiredDirection!=null)
				b.move(desiredDirection);
		}

}
