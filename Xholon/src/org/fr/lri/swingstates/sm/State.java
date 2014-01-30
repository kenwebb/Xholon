package org.fr.lri.swingstates.sm;

import java.util.LinkedList;

import org.primordion.xholon.base.XholonAnonymous;

/**
 * A state of a state machine.
 * 
 * <p>This version of State is slightly altered to integrate it with Xholon.</p>
 * 
 * <p>This is an inner class of <code>StateMachine</code> and is meant to be used as follows:
 * 
 * <pre>
 * 	StateMachine sm = new StateMachine ("sm") {
 * 		...
 * 		public State myState = new State() {
 *  	 		... declare transitions ...
 * 		}
 * 		...
 * 	}
 * </pre>
 * 
 *  The string name of the state, which is used to specify output states in transitions,
 *  will be the name of the field (<code>"myState"</code> in this example).
 *  For this to work, the field must be declared public.
 * 
 * @see StateMachine
 * @author Caroline Appert
 */
public abstract class State extends XholonAnonymous {
	
	private StateMachine machine;
	private String name = null;
	private LinkedList<Transition> transitions;
	
	/**
	 * Builds a new state. 
	 * The string name of the state, which is used to specify output states in transitions,
	 * will be the name of the field this object is assigned to in the parent state machine.
	 * For this to work, the field must be declared public.
	 * The first state in a state machine is its initial state, i.e. the current state
	 * when the machine is first created and when it is reset.
	 */
	public State () {
	  println("State() constructor");
		transitions = new LinkedList<Transition>();
	}
	
	/**
	 * Builds a new state with a given name.
	 * THe names of the states must be unique within a state machine. 
	 * The first state in a state machine is its initial state, i.e. the current state
	 * when the machine is first created and when it is reset.
	 * @param n The string name of the state, which is used to specify destination state in transitions.
	 */
	public State (String n) {
		this();
		name = n.intern();
	}
	
	/**
	 * @return The name of this state
	 */
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return The state machine to which this state belongs.
	 */
	public StateMachine getMachine() {
		return machine;
	}
	
	void setMachine(StateMachine machine) {
		this.machine = machine;
	}
	
	/**
	 * @return The result of the <code>toString</code> default method.
	 */
	public String oldToString(){
		return super.toString();
	}
	
	/**
	 * @return The output transitions of this state.
	 */
	public LinkedList<Transition> getTransitions(){
		return transitions;
	}
	
	/**
	 * The method called when the parent state machine enters this state.
	 * This method does nothing. It can be redefined in derived classes
	 * to specify what to do when this state becomes active.
	 * This is done as follows when using an anoynous class:
	 * <pre>
	 * 	State s = new State () {
	 * 		public void enter () { ... do something ...}
	 * 		...
	 * 	}
	 * </pre>
	 */
	public void enter(){ }
	
	/**
	 * The method called when the parent state machine leaves this state.
	 * This method does nothing. It can be redefined in derived classes
	 * to specify what to do when this state becomes active.
	 * This is done as follows when using an anoynous class:
	 * <pre>
	 * 	State s = new State () {
	 * 		public void leave () { ... do something ...}
	 * 		...
	 * 	}
	 * </pre>
	 */
	public void leave(){ }
	
	/**
	 * Adds a transition to this state.
	 * @param t the transition to add.
	 */
	public final void addTransition(Transition t) {
		LinkedList<String> transitionsAfter = t.mustBeBefore();
		if(transitionsAfter == null) transitions.add(t);
		else {
			int i = 0;
			for(; i < transitions.size(); i++) {
			  String simpleName = transitions.get(i).getClass().getSuperclass().getName();
        simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
				//if(transitionsAfter.contains(transitions.get(i).getClass().getSuperclass().getSimpleName()))
				if (transitionsAfter.contains(simpleName)) {
				  break;
				}
			}
			transitions.add(i, t);
		}
		t.setInputState(this);
	}
	
	/**
	 * Removes a transition from this state.
	 * @param t the transition to remove.
	 */
	public final void removeTransition(Transition t) {
		transitions.remove(t);
		t.setInputState(null);
	}
	
}
