/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.base;

/**
 * A state machine entity is any XholonClass that has to do with state machines.
 * These are identified by being subclasses of StateMachineEntity, and having an
 * xhtype of XhtypeStateMachineEntityActive or XhtypeStateMachineEntity. These
 * include XholonClasses such as State, Transition, Pseudostate, Activity, Trigger, etc.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sep 25, 2005)
 */
public interface IStateMachineEntity extends IXholon {
	
	/**
	 * Execute an instance of a state machine.
	 * @param msg An incoming message that contains a signal, optional data, and that
	 * identifies the sender and receiver of the message.
	 */
	public void doStateMachine(IMessage msg);
	
	/**
	 * Return the Xholon that owns the state machine that this IStateMachineEntity is a part of.
	 * @return An instance of IXholon.
	 */
	public IXholon getOwningXholon();
	
	/**
	 * Return a specified trigger for a transition.
	 * @param trigNum The numeric id of the trigger.
	 * @return The value of the trigger.
	 */
	public int getTrigger(int trigNum);
	
	/**
	 * Return the activity ID of a transition.
	 * @return An activity ID.
	 */
	public int getActivityId();
	/**
	 * Return the entry activity ID of a state.
	 * @return An entry activity ID.
	 */
	public int getEntryActivityId();
	/**
	 * Return the exit activity ID of a state.
	 * @return An exit activity ID.
	 */
	public int getExitActivityId();
	/**
	 * Return the do activity ID of a state.
	 * @return A do activity ID.
	 */
	public int getDoActivityId();
	/**
	 * Return the guard activity ID of a transition.
	 * @return A guard activity ID.
	 */
	public int getGuardActivityId();
	
	/**
	 * Is this a currently active sub state of some other state or of the state machine as a whole?
	 * @return true or false
	 */
	public boolean isActiveSubState();
	
	/**
	 * Notify the existence of all currently active sub states.
	 * This is intended to be called from Interaction, to notify it of all initial lowest level active states,
	 * in which case it will be normally called on a StateMachine node.
	 * It could be called by any external entity to notify it of all currently active states.
	 */
	public void notifyActiveSubStates();
}
