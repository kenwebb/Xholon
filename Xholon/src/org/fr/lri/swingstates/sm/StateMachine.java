/*  
 *   Authors: Caroline Appert (caroline.appert@lri.fr) and Michel Beaudouin-Lafon
 *   Copyright (c) Universite Paris-Sud XI, 2007. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
 */
package org.fr.lri.swingstates.sm;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

//import javax.swing.SwingUtilities;
//import javax.swing.Timer;

//import org.fr.lri.swingstates.debug.StateMachineEventListener;
//import org.fr.lri.swingstates.debug.Watcher; // NOT NEEDED in Xholon GWT
import org.fr.lri.swingstates.events.VirtualEvent;
//import org.fr.lri.swingstates.events.VirtualTimerEvent;

import org.primordion.xholon.base.XholonAnonymous;
import org.primordion.xholon.util.ClassHelper;

/**
 *  
 * <p>A state machine consists of a set of <i>states</i> and a set of <i>transitions</i>.
 * Each transition goes from an input state to an output state (which can be the same),
 * and is labeled by an <i>event</i>, an optional <i>guard</i> and an optional <i>action</i>.
 * At any one time, the machine is one of its states, called the <i>current state</i>. 
 * When the state machine receives an event, it looks for the first outgoing transition 
 * of the current state that matches the event and whose <code>guard</code> method returns True.
 * If it finds such a transition, it fires it, i.e. it calls the current state's <code>leave()</code> method, 
 * then the transition's <code>action()</code> method, finally sets the current state to the transition's output state 
 * and calls the output state's <code>enter()</code> method.
 * If no transition matches, the event is simply ignored.
 * 
 * <p>This version of StateMachine is slightly altered to integrate it with Xholon.</p>
 * 
 * <p>The declaration of a state machine uses Java's anonymous class as follows:
 * <ul>
 * <li> the state machine is the instance of an anonymous subclass of StateMachine, whose body contains one field per state;
 * <li> each state is an instance of an anonymous subclass of State, whose body contains one field per outgoing transition of this state; 
 * <li> each transition is an instance of an anonymous subclass of Transition, whose body contain the optional <code>guard</code> and <code>action</code> methods;
 * </ul>
 * Since we are using anonymous classes, each state machine, state and transition can contain its own fields and methods, if needed.
 * Note also that since we are using the nesting of anonymous classes, transitions have access to 
 * the fields and methods of the enclosing state and state machine, and states have access to the
 * fields and methods of the enclosing state machine.
 * 
 * <p>In summary, the structure of a state machine is as follows:
 * <pre>
 * 	StateMachine sm = new StateMachine () {
 * 		// local fields and methods if needed
 * 		...
 * 		public State s1 = new State () {
 * 			// local fields and methods if needed
 * 			...
 * 			public void enter () { ... do something when entering this state ... } // optional
 * 			public void leave () { ... do something when leaving this state ...} // optional
 *			
 *			// declare a transition to state s2 when receiving an event "anEvent"..
 *			// (see class StateMachine.State.Transition for details).
 * 			Transition t1 = new Event ("anEvent", ">> s2") {
 * 				public boolean guard () { ... return True or False ... }
 * 				public void action () { ... do something ... }
 * 			}
 * 			Transition t2 = ...
 * 		}
 * 		
 * 		public State s2 = new State () {
 * 			...
 * 		}
 * 	}
 * </pre>
 *
 *
 * @author Caroline Appert and Michel Beaudouin-Lafon
 */

public abstract class StateMachine extends XholonAnonymous { // implements ActionListener { //, StateMachineListener {

	/**
	 * The key string of events that triggered <code>AnimationStopped</code> transitions.
	 */
	public static String TIME_OUT   = "TimeOut";

	protected State currentState = null;
	protected State initialState = null;
	protected State stateInBuilt = null;

	private boolean inited = false;
	private boolean active = true;
	private boolean consumes = false;

	private Vector<State> allStates = new Vector<State>();

	//private Timer timer; // NOT NEEDED in Xholon GWT

	//private Watcher                          watcher = null;
	//private LinkedList<StateMachineListener> stateMachineListeners = null;

	/**
	 * Builds a state machine. 
	 */
	public StateMachine(){
//		I don't understand why fields are not initialized at this stage...
//		reset();
		init();
	}

	/**
	 * Makes this state machine consume an event.
	 * Any state machine having a lower priority than this state machines 
	 * will not receive the event that this state machine is being processing.
	 * @param c True if this state machine must consume this event, false otherwise.
	 * @return this state machine.
	 */
	public StateMachine consumes(boolean c) {
		this.consumes = c;
		return this;
	}

	/**
	 * Tests if this state machine has consumed the last event it processed.
	 * @return true if this state machine has consumed the last event it processed, false otherwise.
	 */
	public boolean hasConsumed() {
		return this.consumes;
	}

	/**
	 * Returns this state machine's current state.
	 * @return the current state.
	 */
	public State getCurrentState(){
		return this.currentState;
	}

	/**
	 * Returns this state machine's initial state.
	 * @return the initial state.
	 */
	public State getInitialState(){
		return this.initialState;
	}

	/**
	 * Returns the vector containing all this state machine's states.
	 * @return the vector containing all the states.
	 */
	public Vector<State> getAllStates(){
		return this.allStates;
	}

	/**
	 * Adds the specified state machine event listener to receive state machine events from this state machine. 
	 * State machine events occur when a this state machine is attached, detached, resumed, reset, 
	 * suspended, goes to another state or loops on the current state. 
	 * If l is null, no exception is thrown and no action is performed.
	 * @param l The state machine event listener to add.
	 */
	//public synchronized void addStateMachineListener(StateMachineEventListener l) {
		//if(l == null) return;
		//if(this.watcher == null)
		//	this.watcher = new Watcher(this);
		//this.watcher.addSMEventListener(l);
	//}  

	/**
	 * Removes the specified state machine event listener so that it no longer receives state machine events from this state machine. 
	 * State machine events occur when a this state machine is attached, detached, resumed, reset, 
	 * suspended, goes to another state or loops on the current state. 
	 * If l is null, no exception is thrown and no action is performed.
	 * @param l The state machine event listener to remove.
	 */
	//public synchronized void removeStateMachineListener(StateMachineEventListener l) {
		//if(l == null) return;
		//if(this.watcher != null)
		//	this.watcher.removeSMEventListener(l);
		//this.watcher = null;
	//}


	/**
	 * Method called when this state machine is reset.
	 * This method does nothing. It can be redefined in derived classes.
	 * @see StateMachine#reset() 
	 */
	public void doReset() { }

	/**
	 * Sets the state of this state machine to the initial state.
	 * The initial state is the first state in the order of the declarations.
	 * @return this state machine.
	 */
	public StateMachine reset(){
		//if (this.watcher != null) this.watcher.fireSmReset(this.getCurrentState());
		this.currentState = this.initialState;
		//this.disarmTimer(); // NOT NEEDED in Xholon GWT
		this.doReset();
		return this;
	}

	/**
	 * Returns the active state of this state machine. The machine is active unless <code>suspend()</code> has been called.
	 * @return the active state of this state machine.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Method called when this state machine is suspended.
	 * This method does nothing. It can be redefined in derived classes.
	 * @see StateMachine#suspend() 
	 */
	public void doSuspend() { }

	/**
	 * Makes this state machine inactive.
	 * When a state machine is inactive, it does not process events.
	 */
	public void suspend() {
		if(this.active) this.doSuspend();
		this.active = false;
		//if(this.watcher != null) this.watcher.fireSmSuspended();
	}

	/**
	 * Makes this state machine be active or inactive (calls <code>resume</code> or <code>suspend</code>).
	 * @param active True to makes this state machine be active, false to makes this state machine be inactive.
	 * @see StateMachine#resume()
	 * @see StateMachine#suspend()
	 */
	public void setActive(boolean active) {
		if(active) this.resume();
		else this.suspend();
	}

	/**
	 * Method called when this state machine is resumed.
	 * This method does nothing. It can be redefined in derived classes.
	 * @see StateMachine#resume() 
	 */
	public void doResume() { }

	/**
	 * Makes this state machine active.
	 * When a state machine is active, it processes events.
	 */
	public void resume() {
		if(!inited) initStatesAndTransitions();
		if(!this.active) this.doResume();
		this.active = true;
		//if(this.watcher != null) this.watcher.fireSmResumed();
	}

	/**
	 * This method is called by the constructor of a state machine.
	 * By default, it does nothing.
	 * Override it to specify required variables initializations for a specific machine.
	 */
	public void init() {
    consoleLog("StateMachine init()");
	}

	/**
	 * Internal initialization of the state machine: resolve the state names into their corresponding objects.
	 * If not called explicitly, this is called automatically the first time a transition is fired.
	 * The only reason to call it explicitly is to avoid a delay when it is called automatically.
	 */
	public void initStatesAndTransitions() {

    consoleLog("StateMachine initStatesAndTransitions() 1");
    println("StateMachine initStatesAndTransitions() 1");
		if (this.inited)
			return;
		consoleLog("StateMachine initStatesAndTransitions() 2");
    println("StateMachine initStatesAndTransitions() 2");

		/* 
		 * use the reflection interface to get the list of state names
		 * use the allStates vector to map them to state objects
		 * NOTE : this makes the assumption that the order of 
		 * the fields as enumerated by the reflection API is the same 
		 * as the order in which the fields are constructed.
		 */
		Class smClass = this.getClass();
//		System.out.println("build state machine "+smClass);

/*
		// The states of a state machine is the union of:
		// - all its public State fields (including those of its ancestors) and
		// - all its declared State fields (a declared field overwrite a public existing one of the same name)
		ArrayList<Field> stateFields = new ArrayList<Field>();
		Field[] publicFields = smClass.getFields();
		for (int i = 0; i < publicFields.length; i++) {
			Class<?> fieldType = publicFields[i].getType();
			//if (State.class.isAssignableFrom(fieldType)) {
			if (ClassHelper.isAssignableFrom(State.class, fieldType)) {
				stateFields.add(publicFields[i]);
			}
		}
		Field[] declaredFields = smClass.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Class<?> fieldType = declaredFields[i].getType();
			//if (State.class.isAssignableFrom(fieldType)) {
			if (ClassHelper.isAssignableFrom(State.class, fieldType)) {
				int index = stateFields.indexOf(declaredFields[i]);
				if(index != -1) {
					stateFields.set(index, declaredFields[i]);
				} else {
					stateFields.add(declaredFields[i]);
				}
			}
		}

		for (int i = 0; i < stateFields.size(); i++) {
			String fieldName = stateFields.get(i).getName();
			try {
				stateFields.get(i).setAccessible(true);
				State s = (State) stateFields.get(i).get(this);
				// it can happen when an animation is running
				if(s == null) {
					return;
				}
				if (s.getName() == null) 
					s.setName(fieldName.intern());
				allStates.add(s);

				if(initialState == null){
					currentState = s;
					initialState = s;
				}
				s.setMachine(this);

				// *** Begin initialization transitions for state s *** //
				Class<?> stateClass = s.getClass();
				LinkedList<Field> allFields = new LinkedList<Field>();
				Class<?> tmp = stateClass;
				// collect all the fields of super classes between this class 
				// and the State class to collect all the declared transitions.
				// Example:
				// class SelectionState extends State {
				//		Transition t1;
				// }
				// [...]
				// State s = new SelectionState() {
				//		Transition t2;
				// }
				// => s must contain the transitions t1 and t2
				//while(State.class.isAssignableFrom(tmp.getSuperclass())) {
				while(ClassHelper.isAssignableFrom(State.class, tmp.getSuperclass())) {
					Field[] fields = tmp.getDeclaredFields();
					for(int cpt = 0; cpt < fields.length; cpt++) {
						allFields.add(fields[cpt]);
					}
					tmp = tmp.getSuperclass();
				}
				for (int j = 0; j < allFields.size(); j++) {
					Class<?> fieldStateType = allFields.get(j).getType();
					//if (Transition.class.isAssignableFrom(fieldStateType)) {
					if (ClassHelper.isAssignableFrom(Transition.class, fieldStateType)) {
						allFields.get(j).setAccessible(true);
						Transition t = (Transition) allFields.get(j).get(s);
						// it can happen when an animation is running
						if(t == null) return;
						s.addTransition(t);
//						System.out.println("\t\tadd transition "+t);
						t.setInputState(s);
					}
				}
				// End initialization transitions for state s
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			// System.out.print((stateIndex > 1 ? ", ":"") + state.name);
//			}
		}
		// System.out.println(".");
		inited = true;
		if(currentState == null) {
			System.err.println("Failed in initializing the state machine "+this);
		}
		//if(watcher != null) watcher.fireSMInited();
		*/
	}

	/**
	 * {@inheritDoc}
	 */
	//public void actionPerformed(ActionEvent arg0) {
	  // NOT NEEDED in Xholon GWT
		/*if(arg0.getSource() instanceof Timer){
			processEvent(new VirtualTimerEvent((Timer)arg0.getSource()));
		}*/
	//}

	/**
	 * NOT USED in Xholon GWT; commented out
	 * Arms the default timer.
	 * When the timer expires, a <code>TimeOut</code> event is sent to the state machine.
	 * Each state machine has a single timer. 
	 * Calling <code>armTimer</code> before it has expired effectively rearms it.
	 * @param d the delay of the timer.
	 * @param repeat If false, only one <code>TimeOut</code> event is fired. If true, a <code>TimeOut</code> event is fired every <code>d</code> milliseconds. 
	 */
	/*public void armTimer(int d, boolean repeat) {
		if (this.timer != null) this.timer.stop();
		else this.timer = new Timer(d, this);
		this.timer.setDelay(d);
		this.timer.setInitialDelay(d);
		this.timer.setRepeats(repeat);
		this.timer.restart();
	}*/

	/**
	 * NOT USED in Xholon GWT; commented out
	 * Arms a tagged timer.
	 * When the timer expires, a <code>TimeOut</code> event is sent to the state machine.
	 * Calling <code>armTimer</code> before it has expired effectively rearms it.
	 * @param tag the tag.
	 * @param d the delay of the timer.
	 * @param repeat If false, only one <code>TimeOut</code> event is fired. If true, a <code>TimeOut</code> event is fired every <code>d</code> milliseconds. 
	 */
	/*public void armTimer(String tag, int d, boolean repeat) {
		TaggedTimer t = TaggedTimer.getTimer(tag);
		if (t != null) t.stop();
		else t = new TaggedTimer(tag, d, this);
		t.setDelay(d);
		t.setInitialDelay(d);
		t.setRepeats(repeat);
		t.start();
	}*/

	/**
	 * NOT USED in Xholon GWT; commented out
	 * Disarms the timer.
	 */
	/*public void disarmTimer(){
		if(this.timer != null) this.timer.stop();
	}*/

	/**
	 * NOT USED in Xholon GWT; commented out
	 * Disarms a tagged timer.
	 * @param tag the tag.
	 */
	/*public void disarmTimer(String tag){
		TaggedTimer t = TaggedTimer.getTimer(tag);
		if (t != null) t.stop();
	}*/

	/**
	 * Adds the specified state machine listener to receive events fired by this state machine. 
	 * Use the method <code>sendEvent</code> to make a state machine fire an event. 
	 * @param listener The state machine listener to add.
	 * @see StateMachine#fireEvent(EventObject)
	 * @see StateMachine#removeStateMachineListener(StateMachineEventListener)
	 */
	//public synchronized void addStateMachineListener(StateMachineListener listener) {
	//	if(stateMachineListeners == null) stateMachineListeners = new LinkedList<StateMachineListener>();
	//	stateMachineListeners.add(listener);
	//}

	/**
	 * Removes the specified state machine listener. 
	 * @param listener The state machine listener to remove.
	 */
	//public synchronized void removeStateMachineListener(StateMachineListener listener) {
	//	if(stateMachineListeners == null) return;
	//	stateMachineListeners.remove(listener);
	//}

	/**
	 * Makes this state machine fire a virtual event having a 
	 * given name that will be heard by all
	 * its <code>StateMachineListener</code>.
	 * @param nameEvent The name of the <code>VirtualEvent</code> to fire.
	 */
	public void fireEvent(String nameEvent) {
		//if(stateMachineListeners == null) return;
		VirtualEvent event = new VirtualEvent(nameEvent);
		event.setSource(this);
		//for(Iterator<StateMachineListener> i = stateMachineListeners.iterator(); i.hasNext(); ) {
		//	i.next().eventOccured(event);
		//}
	}

	/**
	 * Makes this state machine fire an event that will be heard by all
	 * its <code>StateMachineListener</code>.
	 * @param event The event to fire.
	 * @see StateMachine#addStateMachineListener(StateMachineEventListener)
	 */
	public void fireEvent(EventObject event) {
		//if(stateMachineListeners == null) return;
		//for(Iterator<StateMachineListener> i = stateMachineListeners.iterator(); i.hasNext(); ) {
		//	StateMachineListener next = i.next();
		//	next.eventOccured(event);
		//}
	}

	/**
	 * {@inheritDoc}
	 */
	public void eventOccured(EventObject eventObject) {
		processEvent(eventObject);
	}

	/**
	 * Processes in the state machine a virtual event having a given name. 
	 * @param event The name of the virtual event to process
	 */
	public void processEvent(String event) {
		processEvent(new VirtualEvent(event));
	}

	protected Transition fireTransition(EventObject event) {
		if(!inited) initStatesAndTransitions();
		LinkedList<Transition> trans = currentState.getTransitions();
		Transition hasFired = null;
		if(trans!=null){
			for(Iterator<Transition> i = trans.iterator(); i.hasNext(); ){
				if(hasFired != null) break;
				Transition t = i.next();
				if(t.matches(event)) {
					t.setTriggeringEvent(event);
					if (fireTransition(t)) {
						hasFired = t;
						return hasFired;
					}
				}
			}
		}
		return hasFired;
	}

	/**
	 * SwingUtilities NOT NEEDED by Xholon GWT (single-threaded)
	 * Processes in the state machine the virtual event received. 
	 * @param event The virtual event to process
	 */	
	public void processEvent(final EventObject event) {
		if(!isActive()) return;
		fireTransition(event);
		/*if(SwingUtilities.isEventDispatchThread()) {
			fireTransition(event);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					fireTransition(event);
				}
			});
		}*/
	}

	/**
	 * Attempt to fire transition <code>t</code>.
	 * If the transition's guard evaluates to true, leave the current state,
	 * execute the transition's action and enter the destination state.
	 * If the destination state is not specified, stay in the current state
	 * and do not execute the leave/enter actions.
	 * @param t the transition to fire
	 * @return true if the transition was fired, false otherwise
	 */
	protected boolean fireTransition (Transition t) {
		final State outputState = t.getOutputState();
		if(!inited) initStatesAndTransitions();
		if (! t.guard()) {
			return false;
		}
		if (outputState != null && outputState != currentState) currentState.leave();
		t.action();
		if (outputState != null && outputState != currentState) {
			//if(watcher != null) {
			//	watcher.fireStateChanged(t, currentState, outputState);
			//}
			currentState = outputState;
			outputState.enter();
		} else {
			//if(watcher != null) watcher.fireStateLooped(t, currentState);
		}
		return true;
	}

	/**
	 * Look up a state by its name. The state's name can be set explicitly when creating it (<code>new State("myState")</code>),
	 * otherwise the state name is the name of the field where it is stored in the state machine (<code>public State myState = new State()</code>).
	 * Note that for this to work, the field must be declared public.
	 * @param s the name of the state to look up
	 * @return the state object
	 * @throws StateNotFoundException if the state is not found in this machine.
	 */
	public State getState(String s) throws StateNotFoundException {
		if (! inited)
			initStatesAndTransitions();
		State state = null;
		for(Iterator<State> iterator = allStates.iterator(); iterator.hasNext(); ) {
			State next = iterator.next();
			if(next.getName().compareTo(s) == 0)
				return next;
		}
		if (state == null) 
			throw new StateNotFoundException(s);
		return state;
		/*
		 * for reasons not clear to me, the code below always raises an exception
		 * saying that we don't have access to the value of the field :-(
		 * That's why we use the workaround in StateMachine.init() and State()
		 * to map a field name to a state
		 Class c = StateMachine.this.getClass();
		 Field[] publicFields = c.getFields();
		 for (int i = 0; i < publicFields.length; i++) {
		 String fieldName = publicFields[i].getName();
		 Class fieldType = publicFields[i].getType();
		 System.out.println("Name: " + c.getName() + ":" + fieldName + 
		 ", Type: " + fieldType.getName());
		 try {
		 Object fieldValue = publicFields[i].get(StateMachine.this);
		 if (fieldValue instanceof StateMachine.State && s.equals(fieldName)) {
		 return (State) fieldValue;
		 }
		 } catch (Exception e) {
		 System.out.println(e);
		 }

		 }
		 System.err.println("StateMachine error : The state "+s+" does not exist");
		 return null;
		 */
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return super.toString() + " currentState:" + currentState;
	}

}
