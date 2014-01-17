package org.primordion.xholon.base;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collections;

/**
 * An agent set is a collection whose elements are always in random order.
 * @author ken
 *
 */
public class AgentSet extends ArrayList implements IAgentSet {
	private static final long serialVersionUID = 2712924385668946213L;

	public AgentSet() {
		super();
	}
	
	public AgentSet(int initialCapacity) {
		super(initialCapacity);
	}
	
	public AgentSet(Collection c) {
		super(c);
	}
	
	/*
	 * TODO use a faster random number generator, such as MersenneTwisterFast
	 * @see org.primordion.xholon.base.IAgentSet#shuffle()
	 */
	public IAgentSet shuffle() {
  	// Collections.shuffle not available in GWT
		//Collections.shuffle(this);
		return this;
	}
	
}
