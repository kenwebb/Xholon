/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.mech.petrinet;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Queue;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.xholonhelper.IFindChildSibWith;

/**
 * A queue that contains references to all Transition nodes in the Petri net.
 * This may permit faster access to the nodes at runtime.
 * One way to add new transitions at runtime,
 * is to paste them as children of this container node.
 * It will automatically move them to the Petri net,
 * and add them to the queue.
 * <p>An instance of &lt;QueueTransitions> must be the firstChild of an instance of &lt;PetriNet>.</p>
 * <p>Note: default max Q size is 10; will try to increase size if required.</p>
 * <p>Examples:</p>
<pre>
  &lt;QueueTransitions connector="#xpointer(ancestor::ClimateSystem/SolarSystem)"/>
  &lt;QueueTransitions connector="#xpointer(ancestor::ProtocolSystem)"/>
  &lt;QueueTransitions shouldAct="false"/>
  &lt;QueueTransitions/>
</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 22, 2012)
*/
public class QueueTransitions extends Queue implements IQueue {
	private static final long serialVersionUID = 8218198061365304635L;

	/**
	 * Root node of a subtree that contains the Transition nodes for this PetriNet.
	 */
	private IXholon transitionsRoot = null;
	
	/**
	 * The specification of the port reference to a subtree (transitionsRoot) that contains Transition nodes,
	 * formatted as an XPath string.
	 */
	private String connector = null;
	
	/**
	 * Whether or not to do something (invoke act()) each time step.
	 */
	private boolean shouldAct = true;
	
	/**
	 * Whether or not to process only the single next transition each time step.
	 * A value of false means to process all items in the Q each time step.
	 */
	private boolean shouldActNextOnly = false;
	
	/**
	 * Whether or not to shuffle each time step.
	 */
	private boolean shouldShuffle = true;
	
	/**
	 * time step multiplier
	 * <p>Each Petri net can have its own value.</p>
	 * <p>This is a convenience copy of the value in PetriNet.</p>
	 */
	private int timeStepMultiplier = IIntegration.M_1;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		super.postConfigure();
		// set value of transitionsRoot, from connector String, using XPath
		if (connector != null) {
			if (connector.startsWith("#xpointer(")) {
				// ex: #xpointer(ancestor::PetriNet/Places/Place[@roleName='p1'])
				String expression = connector.substring(10, connector.length() - 1);
				setTransitionsRoot(this.getXPath().evaluate(expression, this));
			}
			else {
				// allow non-xpointer strings
				setTransitionsRoot(this.getXPath().evaluate(connector, this));
			}
			if (transitionsRoot == null) {
				// a connector was specified, but is an invalid path
				logger.error("QueueTransitions " + connector + " is invalid.");
				return;
			}
		}
		if (transitionsRoot == null) {
			// assume the default location, a child of the PetriNet node
			setTransitionsRoot(((IFindChildSibWith)getService(
				    IXholonService.XHSRV_XHOLON_HELPER))
				    .findFirstChildWithAncestorXhClass(this.getParentNode(), "Transitions"));
			if (transitionsRoot == null) {
				// a connector was specified, but is an invalid path
				logger.error("QueueTransitions " + this.getParentNode() + " must contain a Transitions child.");
				return;
			}
			addTransitionsToQueue(transitionsRoot);
		}
		else {
			addExternalTransitionsToQueue(transitionsRoot);
		}
		if (this.hasChildNodes()) {
			addTransitionsToQueue(this);
			moveTransitionsToPN();
		}
		timeStepMultiplier = ((PetriNet)this.getParentNode()).getTimeStepMultiplier();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		if (this.getSize() == 0) {
			// this can happen after deserializing a model
			if (this.item == null) {
				if (this.maxSize == 0) {
					this.maxSize = 50;
				}
				this.item = new Object[maxSize];
			}
			addExternalTransitionsToQueue(this.getRootNode());
		}
		if (shouldAct && (this.getSize() > 0)) {
			if (this.hasChildNodes()) {
				// at runtime, a user can paste new transitions into this container
				addTransitionsToQueue(this);
				moveTransitionsToPN();
			}
			if (shouldShuffle) {
				shuffle(); // randomize the order of the transitions in the Q
			}
			for (int j = 0; j < timeStepMultiplier; j++) {
				int nxt = nextOff; // point to head of queue
				if (shouldActNextOnly) {
					IXholon nextTrans = (IXholon)this.dequeue();
					nextTrans.actNr();
					this.enqueue(nextTrans);
				}
				else {
					for (int i = 0; i < currentSize; i++) {
						((IXholon)item[nxt]).actNr();
						nxt++;
						nxt %= maxSize;
					}
				}
			}
		}
		super.act();
	}
	
	/**
	 * Add all transitions to the Q.
	 * @param node The parent of the transitions to add to the Q.
	 */
	protected void addTransitionsToQueue(IXholon node)
	{
		// Put transitions into a scheduling Queue, so the order in which they act can be shuffled.
		setMaxSize(this.getSize() + node.getNumChildren(false));
		IXholon transition = node.getFirstChild();
		while (transition != null) {
			if (enqueue(transition) == IQueue.Q_FULL) {
				System.out.println("QueueTransitions postConfigure() Q_FULL");
			}
			((PetriNet)this.getParentNode()).initTransition(transition);
			transition = transition.getNextSibling();
		}
	}
	
	/**
	 * Add all transitions in an external subtree to the Q.
	 * @param node The root of the subtree of the transitions to add to the Q.
	 */
	protected void addExternalTransitionsToQueue(IXholon node)
	{
		node.visit(this);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#visit(org.primordion.xholon.base.IXholon)
	 */
	public boolean visit(IXholon visitee) {
		if (visitee == null || visitee.getXhc() == null) {
			return true;
		}
		if (visitee.getXhc().hasAncestor("TransitionPN")) {
			if (enqueue(visitee) == IQueue.Q_FULL) {
				System.out.println("QueueTransitions visit() Q_FULL");
				setMaxSize(this.getSize() * 2);
				enqueue(visitee);
			}
			((PetriNet)this.getParentNode()).initTransition(visitee);
		}
		return true;
	}
	
	/**
	 * Move all transitions to the Petri net.
	 */
	protected void moveTransitionsToPN()
	{
		IXholon node = this.getFirstChild();
		while (node != null) {
			node.appendChild(transitionsRoot);
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if (attrName.equals("shouldAct")) {
			setShouldAct(Boolean.parseBoolean(attrVal));
		}
		else if (attrName.equals("shouldShuffle")) {
			setShouldShuffle(Boolean.parseBoolean(attrVal));
		}
		else if (attrName.equals("connector")) {
			setConnector(attrVal);
		}
		if (attrName.equals("shouldActNextOnly")) {
			setShouldActNextOnly(Boolean.parseBoolean(attrVal));
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		String outStr = getName()
		  + " qSize:" + getSize();
		if (getSize() > 0) {
			outStr += " firstTransition:[" + item[nextOff] + "]";
		}
		return outStr;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (connector != null) {xmlWriter.writeAttribute("connector", connector);}
		xmlWriter.writeAttribute("shouldAct", Boolean.toString(shouldAct));
		xmlWriter.writeAttribute("shouldActNextOnly", Boolean.toString(shouldActNextOnly));
		xmlWriter.writeAttribute("shouldShuffle", Boolean.toString(shouldShuffle));
	}
	
	public boolean isShouldShuffle() {
		return shouldShuffle;
	}

	public void setShouldShuffle(boolean shouldShuffle) {
		this.shouldShuffle = shouldShuffle;
	}

	public boolean isShouldAct() {
		return shouldAct;
	}

	public void setShouldAct(boolean shouldAct) {
		this.shouldAct = shouldAct;
	}

	public IXholon getTransitionsRoot() {
		return transitionsRoot;
	}

	public void setTransitionsRoot(IXholon transitionsRoot) {
		this.transitionsRoot = transitionsRoot;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public int getTimeStepMultiplier() {
		return timeStepMultiplier;
	}

	public boolean isShouldActNextOnly() {
		return shouldActNextOnly;
	}

	public void setShouldActNextOnly(boolean shouldActNextOnly) {
		this.shouldActNextOnly = shouldActNextOnly;
	}
}
