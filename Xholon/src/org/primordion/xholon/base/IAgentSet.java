package org.primordion.xholon.base;

import com.google.gwt.core.client.JsArray;

import java.util.List;

/**
 * An agent set is a collection whose elements are always in random order.
 * @author ken
 *
 */
public interface IAgentSet extends List {
	
	/**
	 * Shuffle the agent set into a random order.
	 * @return The shuffled agent set.
	 */
	public abstract IAgentSet shuffle();
	
	/**
	 * Return this agent set as a native JavaScript array.
	 * @return The agent set as a native JavaScript array.
	 */
	public abstract JsArray<?> asJsArray();
}
