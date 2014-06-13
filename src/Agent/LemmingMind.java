package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Stack;

import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import Agent.DecisionTree.DecisionLink;
import Agent.DecisionTree.DecisionNode;
import environment.Action;
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
	private Influence expectedInfluence;
	double distanceToGoal;

	/**
	 * @param body
	 *            is the body to attached to this ghost.
	 */

	public boolean isInStack(DecisionNode dn) {
		DecisionNode[] dnl = (DecisionNode[]) this.path.toArray();
		int i = 0;
		while (dnl[i] != dn && i < dnl.length) {
			i++;
		}
		if (i != dnl.length) { // TODO this might end up being REALLY slow ! if
								// we're not
			// looping and we've gone a long way
			return true;
		} else
			return false;
	}

	public LemmingMind(DecisionNode startingNode) {
		path = new Stack<DecisionNode>();
		path.push(startingNode);
		this.distanceToGoal = Double.MAX_VALUE;
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
		// http://lite4.framapad.org/p/F8mul3DbRA

		Influence lastAppliedInfluence = this.getAppliedInfluence();
		DecisionNode newDecisionNode = path.peek().getChildrenWithInfluence(
				lastAppliedInfluence);
		if (isInStack(newDecisionNode)) { // Looping !

			affectPath(3, 10, Effect.PENALIZE);
			// remove loop from path;
			while (path.peek() != newDecisionNode) {
				path.pop();
			}
		}
		List<Perception> perception = this.getPerceivedObjects();
		newDecisionNode.enterNode(perception);
		this.path.push(newDecisionNode);
		Direction desiredDirection = null;
		
		if (isDead()) {
			affectPath(6, 2, Effect.PENALIZE);
			killMe();
		} else {
			// increment path strength because we are still here
			affectPathElement(5, 10, Effect.PRAISE);

			// considering it
			// TODO Pick next action to execute

			// Point d'arrive
			Perception EndAreaTracking = extractEndArea(perception);
			
			// Figuring out the desired direction
			if (EndAreaTracking != null) {
				// TODO Compute Distance to goal and increment or decrement path
				int newDistance = EndAreaTracking.getDistance();
				Effect effect = null;
				if (this.distanceToGoal > newDistance) {
					effect = Effect.PRAISE;
				} else {
					if (this.distanceToGoal < newDistance)
						effect = Effect.PENALIZE;
				}
				if (effect != null)
					this.affectPathElement(0, 10, effect);
				this.distanceToGoal = newDistance;
				desiredDirection = EndAreaTracking.getDirection();
			} else {
				List<Direction> freeDirections = extractFreeDirections(perception);
				
				if (freeDirections.contains(getOrientation())) {
					desiredDirection = getOrientation();
				} else {
					desiredDirection = freeDirections.get(this.rnd.nextInt(freeDirections.size()));
					}
				}
			}
			
			if (desiredDirection != null) {
			
				DecisionLink linkToTake = newDecisionNode.getBestChildrenWithCondition(desiredDirection, Action.ANY);
				
				this.setInfluence(linkToTake.getInfluence());
			} else {
				// this.setMotionInfluence(new
				// MotionInfluence(desiredDirection));
				// influeence
				// Update body if body is falling
				System.out.println("Fall " + isFalling());
				if (isFalling()) {
					this.setInfluence(new Influence(desiredDirection));
				} else {
					// Bloquer dans un trou
					System.out.println("kill");
					killMe();
				}
			}
		

		return StatusFactory.ok(this);
	}

	// x is the index of the step starting numbering and the last taken step, a
	// higher x, a lower penalisation
	// y is the Intensity of the penalisation to apply. a higher y, the lower
	// the overall penalisation
	// a really heavy penalization would be 2
	// a really light penalization would be 10
	// x must be lower than 7
	// -ln((x+2)/4)/y+0.1 :

	protected double computeBonus(int index, double strength) {
		return -Math.log((index + 2) / 4) / strength + 0.1;
	}

	protected void affectPathElement(int index, double strength, Effect effect) {
		ListIterator<DecisionNode> dit = this.path.listIterator(index);
		double bonus = computeBonus(index, strength);
		if (dit.hasNext()) {
			DecisionNode current = dit.next();
			if (dit.hasNext()) {
				DecisionNode older = dit.next();
				DecisionLink link = current.getLink(older);
				link.affectLink(bonus, effect);
			}
		}
	}

	/**
	 * 
	 * @param how
	 *            deep do you want to affect your path, max depths is 7
	 * @param How
	 *            heavy do you want the effect to be, a higher value means a
	 *            lighter effect ( 2 < effect < 10 )
	 * @param effect
	 */
	protected void affectPath(int depth, double strength, Effect effect) {
		if (depth < 0)
			depth = 0;
		if (depth > 7)
			depth = 7;
		ListIterator<DecisionNode> dit = this.path.listIterator();
		DecisionNode current = dit.next();
		DecisionNode older = dit.next();
		double bonus;
		for (int i = 0; i < depth; i++) {
			bonus = computeBonus(i, strength);
			if (dit.hasNext()) {
				current = older;
				older = dit.next();
				DecisionLink link = current.getLink(older);
				link.affectLink(bonus, effect);
			} else {
				break;
			}
		}
	}

	@Override
	protected LemmingsBody createBody(Environment env) {
		return new LemmingsBody(getAddress(), env, 2, Direction.EAST, true); // max
																				// angular
																				// acceleration
																				// (r/s)/s

	}

}
