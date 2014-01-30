package org.fr.lri.swingstates.sm.transitions;

import java.util.EventObject;

import org.fr.lri.swingstates.events.VirtualEvent;
import org.fr.lri.swingstates.sm.Transition;

import org.primordion.xholon.util.ClassHelper;

/**
 * A transition triggered in a high-level event.
 * The <code>Event</code> class can be directly used or extended to define your own events.
 * The above example shows how a state machine, <code>sm1</code>, can receive "longPress" events provided
 * by another state machine, <code>sm2</code>.
 * <pre>
 * StateMachine sm1, sm2;
 * ...
 * sm1 = new StateMachine() {
 *
 *			public State start = new State("start") {
 *				public Transition event = new Event("longPress") {
 *					public void action() {
 *						System.out.println("a long press event");
 *				}
 *	        };
 *		};
 * };
 *
 * sm2 = new StateMachine() {
 * 	
 * 	public State start = new State("start") {
 *		public Transition press = new Press(BUTTON1, ">> wait") {
 *			public void action() {
 *				armTimer(1000);
 *			}
 *		};
 *	};
 *	
 *	public State wait = new State("wait") {
 *		public Transition release = new Release(BUTTON1, ">> start") {
 *			public void action() {
 *				disarmTimer();
 *			}
 *		};
 *		
 *		public Transition longPress = new TimeOut(">> start") {
 *			public void action() {
 *				sm1.processEvent(new VirtualEvent("longPress"));
 *			}
 *		};
 *	};
 * };
 *
 * </pre>
 * 		
 * @author Caroline Appert
 */

public class Event extends Transition {
	
	protected Class<?> classEvent = null;
	
	/**
	 * The string describing the event that triggers this transition.
	 */
	protected String event;
	
	/**
	 * Builds a transition with no modifier.
	 * @param keyEvent The event that triggers this transition
	 * @param outputState The name of the output state
	 */
	public Event(String keyEvent, String outputState) {
		super(outputState);
		consoleLog("Event constructor 1 " + keyEvent + " " + outputState);
		event = keyEvent;
	}
	
	/**
	 * Builds a transition on a position with no modifier 
	 * that loops on current state.
	 * @param keyEvent The event that triggers this transition
	 */
	public Event(String keyEvent) {
		super();
		consoleLog("Event constructor 2 " + keyEvent);
		event = keyEvent;
	}
	
	/**
	 * Builds a transition with no modifier that 
	 * is triggered by any virtual events
	 * whose type is a subclass of <code>eventClass</code>.
	 * @param eventClass The class of events
	 * @param outputState The name of the output state
	 */
	public Event(Class eventClass, String outputState) {
		super(outputState);
		classEvent = eventClass;
	}
	
	/**
	 * Builds a transition with no modifier that 
	 * is triggered by any virtual events
	 * whose type is a subclass of <code>eventClass</code>.
	 * @param eventClass The class of events
	 */
	public Event(Class eventClass) {
		super();
		classEvent = eventClass;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public boolean matches(EventObject eventObject) {
		if(classEvent != null) {
			//return classEvent.isAssignableFrom(eventObject.getClass());
			return ClassHelper.isAssignableFrom(classEvent, eventObject.getClass());
		} else {
			if (eventObject instanceof VirtualEvent) {
				String nameEvent = ((VirtualEvent) eventObject).getNameEvent();
				return (event == null && nameEvent == null)
				|| (event != null && nameEvent != null && event.compareTo(nameEvent) == 0);
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		if(classEvent != null) {
		  //return "Event(" + classEvent.getSimpleName() + ".class)";
  	  String simpleName = classEvent.getName();
      simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
		  return "Event(" + simpleName + ".class)";
		}
		else {
		  return event;
		}
	}

}
