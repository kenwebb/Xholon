/*  
 *   Authors: Caroline Appert (caroline.appert@lri.fr)
 *   Copyright (c) Universite Paris-Sud XI, 2007. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
*/
package org.fr.lri.swingstates.events;

import java.util.EventObject;
import java.util.LinkedList;

import org.fr.lri.swingstates.sm.State;
import org.fr.lri.swingstates.sm.Transition;

/**
 * A virtual event to send to a state machine.
 * <p>
 * For instance, to send a virtual event "copy" to a state machine <code>sm</code>:
 * <pre>
 * //Build a virtual event
 * VirtualEvent copyEvent =  new VirtualEvent("copy");
 * //Send it to a state machine sm
 * sm.processEvent(copyEvent);
 * </pre>
 * The virtual event can thus be used to fire a transition in sm:
 * <pre>
 * StateMachine sm = new StateMachine() {
 * 		public State s = new State () {
 * 			Transition t = new Transition("copy") { ... };
 * 			...
 * 		};
 * 		...
 * };
 * </pre>		
 * </p>
 * @author Caroline Appert
 *
 */
public class VirtualEvent extends EventObject {

	String nameEvent;
	
	/**
	 * Builds a virtual event. 
	 * @param n The name of the virtual event.
	 */
	public VirtualEvent(String n) {
		super(n);
		nameEvent = n;
	}
	
	/**
	 * Builds a virtual event. 
	 * @param n The name of the virtual event.
	 */
	public VirtualEvent(String n, Object source) {
		super(source);
		nameEvent = n;
	}

	/**
	 * Returns the name of the virtual event.
	 * @return The name of the virtual event.
	 */
	public String getNameEvent() {
		return nameEvent;
	}

	/**
	 * Sets the name of a virtual event. 
	 * @param n The name of the virtual event to set.
	 */
	public void setNameEvent(String n) {
		nameEvent = n;
	}
	
	/**
	 * Retrieves the transitions that match the name of this event on a given state.
	 * @param state The state.
	 * @return The list of the matching transitions.
	 */
	public LinkedList<Transition> getMatchingTransitions(State state){
		return state.getTransitions();
	}

	/**
	 * Sets the source of this <code>VirtualEvent</code>.
	 * @param source The new source.
	 */
	public void setSource(Object source) {
		super.source = source;
	}

}
