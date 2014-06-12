package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import Agent.DecisionTree.DecisionNode;
import environment.Direction;
import environment.Environment;
import environment.Influence;
import environment.Perception;

/**
 * Lemmings esprit
 * 
 * @author Clement
 * 
 */

public class LemmingMind extends Animat<LemmingsBody> {

	private final Random rnd = new Random();
	private Stack<DecisionNode> path;

	/**
	 * @param body
	 *            is the body to attached to this ghost.
	 */

	public boolean isInStack(DecisionNode dn){
		DecisionNode[] dnl = (DecisionNode[]) this.path.toArray();
		int i =dnl.length-1;
		while(dnl[i]!=dn){
			i--;
		}
		if(i>0){			//TODO this might end up being REALLY slow ! if we're not looping and we've gone a long way
			return true;
		}
		else
			return false; 
	} 
	
	
	public LemmingMind(DecisionNode startingNode) {
		path = new Stack<DecisionNode>();
		path.push(startingNode);
	}

	private List<Direction> extractFreeDirections(List<Perception> list) {
		List<Direction> freeDirections = new ArrayList<Direction>();
		freeDirections.addAll(Arrays.asList(Direction.values()));
		for (Perception obj : list) {
			if (obj.getDistance() <= 1 && obj.getHauteur() == 0) {
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

	/**
	 * Replies the perception that is corresponding to the EndArea.
	 * 
	 * @param list
	 *            is the list of all the perceptions to filter.
	 * @return the EndArea or <code>null</code> if not found.
	 */
	private Perception extractEndArea(List<Perception> list) {
		for (Perception obj : list) {
			if (obj.isEndArea() && obj.getDistance() == 1) {
				return obj;
			}
		}
		return null;
	}

	private boolean jumpAvailable(List<Perception> list) {
		boolean etat = false;
		for (Perception obj : list) {
			if (obj.getDistance() == 0 && obj.getHauteur() == 1 && obj.isJump()) {
				etat = true;
			}
		}
		return etat;
	}

	@Override
	public Status live() {

		// Algo:
		/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */

		if (!isDead()) {
			// Get the perceptions from the body.
			List<Perception> perception = this.getPerceivedObjects();
			Direction desiredDirection = null;

			// Point d'arriver
			Perception EndAreaTracking = extractEndArea(perception);

			if (EndAreaTracking != null) {
				// See the EndArea
				desiredDirection = EndAreaTracking.getDirection();
			} else {
				List<Direction> freeDirections = extractFreeDirections(perception);
				if (!freeDirections.isEmpty()) {
					if (jumpAvailable(perception)) {
						desiredDirection = Direction.NORTHEAST;
					} else {
						if (freeDirections.contains(getOrientation())) {
							desiredDirection = getOrientation();
						} else {
							desiredDirection = freeDirections.get(this.rnd
									.nextInt(freeDirections.size()));
						}
					}
				}
			}

			System.out.println(this.getAddress().toString() + " is Alive");
			// If the Lemmings decided to move, try to move the body
			// accordingly.
			if (desiredDirection != null) {
				this.setMotionInfluence(new Influence(desiredDirection));
			} else {
				// this.setMotionInfluence(new
				// MotionInfluence(desiredDirection));
				// influeence
				// Update body if body is falling
				System.out.println("Fall " + isFalling());
				if (isFalling()) {
					this.setMotionInfluence(new Influence(
							desiredDirection));
				} else {
					// Bloquer dans un trou
					System.out.println("kill");
					killMe();
				}
			}
		} else {
			// corps dead suicide
			killMe();
		}
		return StatusFactory.ok(this);
	}

	@Override
	protected LemmingsBody createBody(Environment env) {
		return new LemmingsBody(getAddress(), env, 2, Direction.EAST, true); // max
																				// angular
																				// acceleration
																				// (r/s)/s

	}

}
