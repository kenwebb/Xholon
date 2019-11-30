package org.primordion.xholon.base;

import com.google.gwt.core.client.JsArray;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collections;
import java.util.Iterator;

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
	
	@Override
	public IAgentSet shuffle() {
  	// Collections.shuffle not available in GWT
		//Collections.shuffle(this);
		return this;
	}
	
	@Override
	public JsArray<?> asJsArray() {
		JsArray<?> jsarr = this.makeNativeArray();
		Iterator<?> it = this.iterator();
		while (it.hasNext()) {
			pushNativeArray(jsarr, it.next());
		}
		return jsarr;
	}
	
	protected native JsArray<?> makeNativeArray() /*-{return [];}-*/;
	
	protected native void pushNativeArray(JsArray<?> jsarr, Object node) /*-{jsarr.push(node);}-*/;
	
}
