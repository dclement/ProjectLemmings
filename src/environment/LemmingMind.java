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
		

		@Override
		public LemmingsBody getBody() {
			return this.body;
		}
		
		private List<Direction> extractFreeDirections(List<Perception> list) {
			List<Direction> freeDirections = new ArrayList<Direction>();
			freeDirections.addAll(Arrays.asList(Direction.values()));
			for(Perception obj : list) {
				if (obj.getDistance()<=1) {
					freeDirections.remove(obj.getDirection());
				}
			}
			return freeDirections;
		}
		
		/** Replies the perception that is corresponding to the EndArea.
		 * 
		 * @param list is the list of all the perceptions to filter.
		 * @return the EndArea or <code>null</code> if not found.
		 */
		private Perception extractEndArea(List<Perception> list) {
			for(Perception obj : list) {
				if (obj.isEndArea()) {
					return obj;
				}
			}
			return null;
		}
				
		@Override
		public void live() {
			// Get the ghost body
			LemmingsBody b = getBody();
		
			// Get the perceptions from the body.
			List<Perception> perception = b.perceive();
			
			Direction desiredDirection = null;
					
			// Point d'arriver
			Perception EndAreaTracking = extractEndArea(perception);
			
			if (EndAreaTracking!=null) {
				// See the EndArea
				desiredDirection = EndAreaTracking.getDirection();	
			}
			else {
					List<Direction> freeDirections = extractFreeDirections(perception);
					if (!freeDirections.isEmpty()) {
						if (freeDirections.contains(b.getOrientation()))
						{
							desiredDirection = b.getOrientation();
						}
						else
						{
							desiredDirection = freeDirections.get(this.rnd.nextInt(freeDirections.size()));
						}
					}
				
			}
		
			// If the Lemmings decided to move, try to move the body accordingly.
			if (desiredDirection!=null)
				b.move(desiredDirection);
			
		}

}
