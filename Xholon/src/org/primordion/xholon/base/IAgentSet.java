package org.primordion.xholon.base;

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
	
}
