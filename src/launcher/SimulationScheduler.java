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
package launcher;

import java.util.Iterator;
import java.util.logging.Logger;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.status.Status;

import environment.Environment;


/**
 * Schedule the simulation.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SimulationScheduler extends AgentActivator {

	private final Environment environment;
	private final long waitDuration;
	private final Logger logger = Logger.getLogger(SimulationScheduler.class.getCanonicalName());
	
	/**
	 * @param environment is the environment to schedule
	 * @param waitDuration is the wait duration atthe end of each simulation step (in milliseconds).
	 */
	public SimulationScheduler(Environment environment, long waitDuration) {
		this.environment = environment;
		this.waitDuration = waitDuration;
	}

	@Override
	protected Status executeBehaviour(Iterator<? extends Agent> agents) {
		this.environment.runBehaviour();
		Status s = super.executeBehaviour(agents);
		if (this.waitDuration>0) {
			try {
				Thread.sleep(this.waitDuration);
			}
			catch (InterruptedException e) {
				this.logger.throwing(getClass().getCanonicalName(), "activateBehaviour", e); //$NON-NLS-1$
			}
		}
		return s;
	}
	
}