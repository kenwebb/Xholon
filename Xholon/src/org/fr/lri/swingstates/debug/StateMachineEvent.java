/*  
 *   Authors: Caroline Appert (caroline.appert@lri.fr)
 *   Copyright (c) Universite Paris-Sud XI, 2007. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
*/
package org.fr.lri.swingstates.debug;

import java.util.EventObject;

import org.fr.lri.swingstates.sm.State;
import org.fr.lri.swingstates.sm.StateMachine;
import org.fr.lri.swingstates.sm.Transition;


/**
 * <p>An event which indicates that an action occurred in a state machine. 
 * This event is used both for the following state machine events:
 * <ul>
 * <li>the state machine is reset,
 * <li>the state machine is resumed,
 * <li>the state machine is suspended,
 * <li>the state machine fires a transition changing the current state,
 * <li>the state machine fires a transition making it loop on the current state.
 * </ul>
 * </p>
 * <p> A StateMachineEvent object is fired to every StateMachineEventListener or StateMachineEventAdapter object which 
 * is registered to receive the "interesting" mouse events using the state machine's addSMListener method. 
 * (StateMachineEventAdapter objects implement the StateMachineEventListener interface.) Each such listener object gets a StateMachineEvent containing the state machine event.
 * </p>
 * <p>The current state of this state machine can be retrieved using <code>StateMachineEvent.getCurrentState()</code>.</p>
 * <p>The <code>getPreviousState()</code> and <code>getTransition()</code> retrieve the previous state and the transition.</p>
 * @author Caroline Appert
 **/
public class StateMachineEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	State previousState;
	State currentState;
	Transition transition; 
	
	/**
	 * Builds a StateMachineEvent.
	 * @param stateMachine The state machine that originated this event.
	 * @param t The transition of the state machine that has just been fired. 
	 * @param pState The state of the state machine before t has been fired.
	 * @param cState The current state of the state machine.
	 */
	public StateMachineEvent(StateMachine stateMachine, Transition t, State pState, State cState) {
		super(stateMachine);
		previousState = pState;
		currentState = cState;
		transition = t;
	}
	
	/**
	 * Builds a StateMachineEvent originated by a state machine that has just fired a transition that makes it loop on the current state. The <code>previousState</code> field is equal to the <code>currentState</code> field.
	 * @param stateMachine The state machine that originated this event.
	 * @param t The transition of the state machine that has just been fired. 
	 * @param cState The current state of the state machine.
	 */
	public StateMachineEvent(StateMachine stateMachine, Transition t, State cState) {
		super(stateMachine);
		previousState = cState;
		currentState = cState;
		transition = t;
	}
	
	/**
	 * Builds a StateMachineEvent originated by a state machine that has just been inited.
	 * @param stateMachine The state machine that originated this event.
	 */
	public StateMachineEvent(StateMachine stateMachine) {
		super(stateMachine);
		previousState = stateMachine.getCurrentState();
		currentState = previousState;
		transition = null;
	}

	/**
	 * Returns the current state of the state machine that originated this StateMachineEvent.
	 * @return the current state of the state machine that originated this StateMachineEvent. 
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * Returns the previous state of the state machine that originated this StateMachineEvent.
	 * @return the previous state of the state machine that originated this StateMachineEvent. 
	 * <code>null</code> when this StateMachineEvent has been originated by a state machine when it 
	 * has been attached to a SMControlAttachable object, detached from a SMControlAttachable object, resumed, reset or suspended.
	 */
	public State getPreviousState() {
		return previousState;
	}
	/**
	 * Returns the transition that has just been fired by this state machine.
	 * @return the transition that has just been fired by this state machine. The state machine originated this event because this transition has been fired. <code>null</code> when this StateMachineEvent has been originated by a state machine when it has been attached to a canvas, detached from a canvas, resumed, reset or suspended.
	 */
	public Transition getTransition() {
		return transition;
	}
	
	/**
	 * Returns the state machine that originated this event.
	 * @return the state machine that originated this event.
	 */
	public StateMachine getSmSource() {
		return (StateMachine)getSource();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString(){
		String res = "["+getSmSource();
		if(previousState != null) res+="\n\tprevious state : "+previousState.getName();
		if(currentState != null) res+="\n\tcurrent state : "+currentState.getName();
		if(transition != null) res+="\n\ttransition : "+transition;
		return res+"]";
	}
}
