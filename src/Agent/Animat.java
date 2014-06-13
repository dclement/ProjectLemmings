/* 
 * $Id$
 * 
 * Copyright (c) 2007-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package Agent;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivationPrototype;
import org.janusproject.kernel.status.Status;

import environment.AIBody;
import environment.Body;
import environment.Direction;
import environment.Environment;
import environment.Influence;
import environment.Perception;



/**
 * Abstract implementation of an animat.
 * 
 * @param <ABT> is the type of the body supported by this agent.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@AgentActivationPrototype
public abstract class Animat<ABT extends AIBody> extends Agent {
	
	private static final long serialVersionUID = -3753143150725086116L;
	
	private ABT body = null;

	/**
	 */
	public Animat() {
		//
	}
	
	/** Invoked by the simulator to create the body for the agent.
	 * 
	 * @param in is the environment to create the body for.
	 * @return the agent body.
	 */
	protected abstract ABT createBody(Environment in);

	/** Set the body.
	 * 
	 * @param in is the environment to create the body for.
	 * @return the agent body.
	 */
	public ABT spawnBody(Environment in) {
		if (this.body==null) {
			this.body = createBody(in);
		}
		return this.body;
	}
	
	/** Replies the body of this animat.
	 * 
	 * @return the body of this animat.
	 */
	private ABT getBody() {
		if (this.body==null) throw new IllegalStateException();
		return this.body;
	}

		
	/** Replies the position of the animat.
	 * 
	 * @return the x-coordinate of the position of this animat.
	 */
	protected final int getX() {
		return getBody().getX();
	}

	/** Replies the position of the animat.
	 * 
	 * @return the y-coordinate of the position of this animat.
	 */
	protected final int getY() {
		return getBody().getY();
	}

	protected final Direction getOrientation(){
		return this.getBody().getOrientation();
	}
	
	protected final void setOrientation(Direction dir){
		this.getBody().setOrientation(dir);
	}
	
	protected final void setInfluence(Influence inf){
		this.getBody().setInfluence(inf);
	}
	
	protected final boolean isFalling(){
		return this.getBody().isFalling();
	}
	
	protected final Influence getAppliedInfluence(){
		return this.getBody().getAppliedInfluence();
	}
	
	protected final boolean isDead(){
		return this.getBody().isDead();
	}
		
	/** Replies all the perceived objects.
	 * 
	 * @return the perceived objects.
	 */
	protected final List<Perception> getPerceivedObjects() {
		return getBody().perceive();
	}

	/** Run the behaviour of the animat
	 * 
	 * @return the status of the execution, usually: StatusFactory.ok(this).
	 */
	@Override
	public abstract Status live();
	
}