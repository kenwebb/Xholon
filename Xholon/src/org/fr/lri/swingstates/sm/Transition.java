package org.fr.lri.swingstates.sm;

import java.util.EventObject;
import java.util.LinkedList;

import org.primordion.xholon.base.XholonAnonymous;

/**
 * A transition of a state machine.
 * 
 * <p>This version of Transition is slightly altered to integrate it with Xholon.</p>
 * 
 * <p>
 * This is an inner class of <code>StateMachine.State</code> and is meant to
 * be used as follows:
 * <pre>
 * 	Transition t = new &lt;eventtype&gt; (&lt;parameters&gt;, &lt;output state&gt;) {
 *  		public boolean guard () { ... return True if transitions enabled ... } // optional
 *  		public void action () { ... transition action ... } // optional
 * 	}
 * </pre>
 * 
 * <p>The <code>Transition</code> class has many derived classes corresponding to the various types of events that can be handled by a state machine.
 * <code>&lt;eventtype&gt;</code> represents one of these classes and &lt;parameters&gt; the corresponding parameters.
 * 
 * <p><code>&lt;output state&gt</code> is the specification of the output state of the transition.
 * It is a string containing the name of the output state, which is, in general, the name of the
 * field of the state machine that holds the state (see class StateMachine.State).
 * In order to make it easier to spot the output state in the declaration, the name can be prefixed by
 * any combination of the following characters: -, =, &gt; and space. This makes it possible to 
 * specify a transition to state <code>s2</code> with strings such as <code>"-> s2", "==> s2", ">> s2"</code>, etc.
 * 
 * @see StateMachine
 * @author Caroline Appert
 */
public abstract class Transition extends XholonAnonymous {
	
	/**
	 * The event object that has triggered this transition.
	 */
	protected EventObject triggeringEvent = null;
	
	/**
	 * The output state of this transition.
	 */
	protected State outputState = null;
	
	/**
	 * The input state of this transition.
	 */
	protected State inputState = null;
	
	/**
	 * The name of the output state of this transition.
	 */
	protected String outputStateName;
	
	protected void setInputState(State inputState) {
		this.inputState = inputState;
	}

	/**
	 * Builds a transition that is triggered by any event.
	 * @param outState The name of the output state
	 */
	protected Transition(String outState) {
		super();
		setOutputStateName(outState);
	}
	
	/**
	 * Builds a transition that is triggered by any event and 
	 * that loops on the current state.
	 */
	protected Transition() {
		outputStateName = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		setRoleName(toString());
		super.postConfigure();
	}
	
	LinkedList<String> mustBeBefore() {
		//String ownClassName = this.getClass().getSuperclass().getSimpleName();
		String ownClassName = this.getClass().getSuperclass().getName();
    ownClassName = ownClassName.substring(ownClassName.lastIndexOf(".") + 1);
		
		LinkedList<String> res = null;
		if(ownClassName.endsWith("OnTag")) {
			res = new LinkedList<String>();
			res.add(ownClassName.substring(0, ownClassName.length()-5));
			res.add(ownClassName.substring(0, ownClassName.length()-5)+"OnShape");
			res.add(ownClassName.substring(0, ownClassName.length()-5)+"OnComponent");
		} else {
			if(ownClassName.endsWith("OnComponent")) {
				res = new LinkedList<String>();
				res.add(ownClassName.substring(0, ownClassName.length()-11));
			} else {
				if(ownClassName.endsWith("OnShape")) {
					res = new LinkedList<String>();
					res.add(ownClassName.substring(0, ownClassName.length()-7));
				} else {
					if(ownClassName.startsWith("Tagged")) {
						res = new LinkedList<String>();
						res.add(ownClassName.substring(6, ownClassName.length()));
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * Sets the name of the output state of this transition.
	 * @param outState The name of the output state
	 */
	protected void setOutputStateName(String outState) {
		/* 
		 * trim leading chars: '-=> ' 
		 * so we can write the destination state as
		 * "-> s", "=> s", ">> s", etc.
		 */
		int i = 0;
		int c = outState.charAt(i);
		while (c == '-' || c == '=' || c == '>' || c == ' ') {
			c = outState.charAt(++i);
		}
		
		outputStateName = i > 0 ? outState.substring(i) : outState;
		outputState = null;
	}
	
	/**
	 * @return the output state of this transition.
	 */
	public State getOutputState() {
		if (outputStateName == null) {
			// a null output state means we stay in the same state
			// => we don't call leave/enter
			return null;
		}
		if (outputState == null) {
			try {
				outputState = inputState.getMachine().getState(outputStateName);
			} catch (StateNotFoundException e) {
				System.err.println("Destination state " 
						+ e.getStateName()
						+ " not found in state machine " 
						+ inputState.getMachine()+".");
				return null;
			}
		}
		return outputState;
	}
	
	/**
	 * @return the input state of this transition.
	 */
	public State getInputState() {
		return inputState;
	}
	
	/**
	 * Tests if an event can trigger that transition.
	 * @param eventObject The event to test
	 * @return True if the <code>eventObject</code> 
	 * can trigger this transition. 
	 */
	public boolean matches(EventObject eventObject) {
		return true;
	}
	
	/**
	 * @return The default string that would be returned 
	 * by java.lang.Object.toString().
	 */
	public String oldToString() {
		return super.toString();
	}
	
	/**
	 * Method called when an event matching this transition 
	 * is received to decide whether it can be fired.
	 * This method always returns true. 
	 * It can be redefined in subclasses, e.g.:
	 * <pre>
	 * 	Transition t = new Press (BUTTON1) {
	 * 		public boolean guard() { ... return true or false ... }
	 * 	}
	 * </pre>
	 * @return True if this transition must be fired.
	 */
	public boolean guard() {
		return true;
	}
	
	/**
	 * Method called when this transition is fired.
	 * This method does nothing. 
	 * It can be redefined in subclasses, e.g.:
	 * <pre>
	 * 	Transition t = new Press (BUTTON1) {
	 * 		public void action() { ... do something ... }
	 * 	}
	 * </pre>
	 */
	public void action() { }
	
	/**
	 * Returns the event that has just been received. 
	 * @return the virtual event that has just been received, 
	 * null if this transition is not fired by a virtual event.
	 */
	public EventObject getEvent() {
		return triggeringEvent;
	}

	/**
	 * @param triggeringEvent The event that has just triggered this transition.
	 */
	void setTriggeringEvent(EventObject triggeringEvent) {
		this.triggeringEvent = triggeringEvent;
	}
	
	
}

