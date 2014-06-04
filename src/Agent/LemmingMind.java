package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import environment.Body;
import environment.Direction;
import environment.Entity;
import environment.Environment;
import environment.MotionInfluence;
import environment.Perception;


/**
 * Lemmings esprit
 * @author Clement
 *
 */


public class LemmingMind  extends Animat<LemmingsBody> {

		private final Random rnd = new Random();

		/**
		 * @param body is the body to attached to this ghost.
		 */
				
		public LemmingMind() {
			// TODO Auto-generated constructor stub
		}

		private List<Direction> extractFreeDirections(List<Perception> list) {
			List<Direction> freeDirections = new ArrayList<Direction>();
			freeDirections.addAll(Arrays.asList(Direction.values()));
			for(Perception obj : list) {
				if (obj.getDistance()<=1 && obj.getHauteur()==0) {
					freeDirections.remove(obj.getDirection());
				}
			}
			freeDirections.remove(Direction.NORTHEAST);
			freeDirections.remove(Direction.NORTHWEST);
			freeDirections.remove(Direction.SOUTH);
			freeDirections.remove(Direction.NORTH);
			freeDirections.remove(Direction.SOUTHEAST);
			freeDirections.remove(Direction.SOUTHWEST);
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
				
		private boolean jumpAvailable(List<Perception> list){
			boolean etat = false;
			for(Perception obj : list) {
				if (obj.getDistance()==0 && obj.getHauteur()==1 && obj.isJump()) {
					etat = true;
				}
			}
			return etat;
		}
		
		@Override
		public Status live() {
			
			if(!isDead())
			{
				// Get the perceptions from the body.
				List<Perception> perception = this.getPerceivedObjects();
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
							if(jumpAvailable(perception))
							{
								desiredDirection = Direction.NORTHEAST;
							}
							else
							{
							if (freeDirections.contains(getOrientation()))
							{
								desiredDirection = getOrientation();
							}
							else
							{
								desiredDirection = freeDirections.get(this.rnd.nextInt(freeDirections.size()));
							}
							}
						}
				}
	
				System.out.println(this.getAddress().toString() + " is Alive");
				System.out.println("DesiredDirection " + desiredDirection);
				// If the Lemmings decided to move, try to move the body accordingly.
				if (desiredDirection!=null)
				{
					this.setMotionInfluence(new MotionInfluence(desiredDirection));
				}
				else
				{ // la je comprend pas tout =°
					//this.setMotionInfluence(new MotionInfluence(desiredDirection));
					//influeence
					//Update body if body is falling
					System.out.println("Fall " + isFalling());
					if(isFalling())
					{
						this.setMotionInfluence(new MotionInfluence(desiredDirection));
					}
					else
					{
						//Bloquer dans un trou
						System.out.println("kill");
						killMe();
					}
				}
			}
			else
			{
				// corps dead suicide
				killMe();
			}
			return StatusFactory.ok(this);
		}


		@Override
		protected LemmingsBody createBody(Environment env) {
			return new LemmingsBody(getAddress(),env, 2, Direction.EAST,true);			// max angular acceleration (r/s)/s
			
		}

}
