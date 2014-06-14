package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Stack;

import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import Agent.DecisionTree.DecisionLink;
import Agent.DecisionTree.DecisionNode;
import environment.Action;
import environment.DeathReason;
import environment.Direction;
import environment.EndArea;
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
	private Stack<Influence> influencePath;
	private Stack<DecisionLink> links;
	private Influence expectedInfluence;
	double distanceToGoal;
	private DecisionLink expectedLink;
	
	/**
	 * @param body
	 *            is the body to attached to this ghost.
	 */

	public boolean isInStack(DecisionNode dn) {
	
		
		List<DecisionNode> dnl= new ArrayList<>();
		dnl.addAll(this.path);
		int i = 0;
		while(i<dnl.size() && dnl.get(i)!=dn ){
			i++;
		}
	
		if(i==dnl.size()){
			return false;
		}
		else
			return true;
		
	}

	public LemmingMind(DecisionNode startingNode) {
		path = new Stack<DecisionNode>();
		path.push(startingNode);
		influencePath = new Stack<Influence>();
		influencePath.push(new Influence());
		
		links = new Stack<>();
		links.push(new DecisionLink(null,startingNode,influencePath.peek(), 0.5f) );
		
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
	public Status activate(Object ... objects){
		this.path.peek().enterNode(this.getPerceivedObjects());
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		// http://lite4.framapad.org/p/F8mul3DbRA
		
		// we retrieve the influence that ended up being applied whether or not
		// it was the expected one
		Influence lastAppliedInfluence = this.getAppliedInfluence();
		if(lastAppliedInfluence!=null){
			System.out.println("last applied: " + lastAppliedInfluence.toString());
		
		}
		DecisionNode newDecisionNode = path.peek().getChildWithInfluence(lastAppliedInfluence);
			
		if(newDecisionNode == null){
			newDecisionNode = path.peek();
		}
		DecisionLink tookLink = newDecisionNode.getLink(path.peek(),lastAppliedInfluence);
		System.out.println("the linking link is: " + tookLink);
		
		if(expectedLink!=null && expectedLink!=tookLink){
			System.out.println("we got the wrong effect applied!");
			expectedLink.affectLink(computeBonus(1, 4), Effect.PENALIZE);
		}
		
		// if influence was Dig or Bridge, The environment was modified, we
		// therefore must
		List<Perception> perception = this.getPerceivedObjects();
		if(lastAppliedInfluence !=null){
			if (lastAppliedInfluence.getAction() == Action.DIG) {
				newDecisionNode.updateParentsOnRemove();
			}
			if (lastAppliedInfluence.getAction() == Action.BRIDGE) {
				Direction dir = lastAppliedInfluence.getDirection() == Direction.EAST ? Direction.SOUTHEAST
						: Direction.SOUTHWEST;
				path.peek().getChildWithInfluence(new Influence(dir, Action.FALL))
						.updateParentsOnAdd();
			}
		}
		
		if (isInStack(newDecisionNode)) { // Looping !
			if(tookLink!=null)
				tookLink.affectLink(computeBonus(2, 7), Effect.PENALIZE);
			affectPath(2, 10, Effect.PENALIZE);
			System.out.println("looped, penilizing");
			// remove loop from path;
			while (path.peek() != newDecisionNode) {
				path.pop();
				influencePath.pop();
				links.pop();
			}			
		}
		else{
			this.path.push(newDecisionNode);
			this.influencePath.push(lastAppliedInfluence);
			this.links.push(tookLink);
		}
	
		newDecisionNode.enterNode(perception);
		Direction desiredDirection = null;

		
		//System.out.println("link : " );
		
		
		if (isDead()) {
			DeathReason dr = this.getDeathReason();
			if(dr== DeathReason.DEATH){
				System.out.println("we died so lets set up the path");
				affectPath(3, 1, Effect.PENALIZE);
				expectedLink.affectLink(computeBonus(1,2),Effect.PENALIZE );
				System.out.println(expectedLink);
				System.out.println(tookLink);
			}
			else{
				System.out.println("we found a solution lets take it!");
				praiseSolution();
			}
			killMe();
		} else {
			// increment path strength because we are still here
			//affectPathElement(4, 10, Effect.PRAISE);

			// considering it
			// TODO Pick next action to execute

			// Point d'arrive
			Perception EndAreaTracking = extractEndArea(perception);

			// Figuring out the desired direction
			if (EndAreaTracking != null) {
				System.out.println("endArea detected");
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
				desiredDirection = getOrientation();
			}
		}

		if (desiredDirection != null) {
			System.out.println(desiredDirection);
			DecisionLink linkToTake=newDecisionNode.getBestChildren();
			DecisionLink linkToTakeDirection = newDecisionNode.getBestChildrenWithCondition(desiredDirection, Action.ANY);
			if(extractEndArea(perception)!=null){
				System.out.println("GOGOGO ! " + linkToTakeDirection + " " + linkToTake);
			}
			if(linkToTakeDirection!=null){
				if(linkToTake.getStrength()-linkToTakeDirection.getStrength()>0.1){
					
				}
				else{
					linkToTake = linkToTakeDirection;
				}
				if(extractEndArea(perception)!=null){
					linkToTake = linkToTakeDirection;
				}
			}
			
			//}
			if(linkToTake==null){//blocked ?
				System.out.println("kill");
				killMe();
			}
			if(linkToTake!=null)
			System.out.println("picked influence: "+ linkToTake.getInfluence().toString());
			this.expectedLink = linkToTake;
			this.setInfluence(linkToTake.getInfluence());
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

	

	protected float computeBonus(int index, double strength) {
	
		return (float) -(Math.log((index + 2f) / 4f) / strength) + 0.1f;
	}

	private void praiseSolution() {
		List<DecisionLink> links = new ArrayList<>();
		links.addAll(this.links);
		Collections.reverse(links);
		
		for(int i =0; i<links.size() ; i++){
			links.get(i).setStrength(1f);
		}
	}
	
	protected void affectPathElement(int index, double strength, Effect effect) {
		if(index>=this.path.size()){
			return;
		}
		
		List<DecisionLink> links = new ArrayList<>();
		links.addAll(this.links);
		Collections.reverse(links);
		float bonus = computeBonus(index, strength);
		links.get(index).affectLink(bonus, effect);
		
		/*
		ListIterator<DecisionNode> dit = this.path.listIterator(index);
		ListIterator<Influence> iit = this.influencePath.listIterator(index);
		float bonus = computeBonus(index, strength);
		if (dit.hasNext()) {
			DecisionNode current = dit.next();
			Influence appliedInf = iit.next();
			if (dit.hasNext()) {
				DecisionNode older = dit.next();
				DecisionLink link = current.getLink(older,appliedInf);
				if(link!=null){
					link.affectLink(bonus, effect);
				}
			}
		}*/
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
		

		List<DecisionLink> links = new ArrayList<>();
		links.addAll(this.links);
		Collections.reverse(links);
		
		for(int i =0; i<depth ; i++){
			if(i>=links.size())
				break;
			float bonus = computeBonus(i, strength);
			links.get(i).affectLink(bonus, effect);	
		}
		
		/*
		List<Influence> ilist=new ArrayList<Influence>();
		ilist.addAll(this.influencePath);
		Collections.reverse(ilist);
		
		List<DecisionNode> list= new ArrayList<DecisionNode>();
		list.addAll(this.path);
		Collections.reverse(list);
		
		DecisionNode older = list.get(0);
		DecisionNode current;
		Influence currenti;
		float bonus;
		for (int i = 1; i < depth; i++) {
			bonus = computeBonus(i, strength);
			current = older;
			if(i<list.size()){
				older=list.get(i);
				currenti = ilist.get(i-1);
				DecisionLink link = current.getLink(older,currenti);
				
				if(link!=null){
				
					link.affectLink(bonus, effect);					
				}
			}
			else{
				break;
			}
				
		}*/
	}
	

	@Override
	protected LemmingsBody createBody(Environment env) {
		return new LemmingsBody(getAddress(), env, 2, Direction.EAST, true); // max
																				// angular
																				// acceleration
																				// (r/s)/s

	}

}
