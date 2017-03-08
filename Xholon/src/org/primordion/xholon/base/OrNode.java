/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.xholon.base;

/**
 * An OrNode can have multiple children, but at a given point in time,
 * only one of these children can be active.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on April 27, 2008)
 */
public class OrNode extends Xholon implements IOrNode {
	private static final long serialVersionUID = 2647975409088204329L;

	/**
	 * The current only child of this Or node.
	 */
	protected IXholon onlyChild = null;
	
	/**
	 * The role name of the default only child.
	 */
	protected String val = null;
	
	/*
	 * @see org.primordion.xholon.base.IOrNode#getOnlyChild()
	 */
	public IXholon getOnlyChild() {
		return onlyChild;
	}

	/*
	 * @see org.primordion.xholon.base.IOrNode#setOnlyChild(org.primordion.xholon.base.IXholon)
	 */
	public void setOnlyChild(IXholon onlyChild) {
		this.onlyChild = onlyChild;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val)
	{
		this.val = val;
		// The following allows apps to initialize the onlyChild in a Xholon-standard way.
		// This is especially important for JavaScript apps that can't access setOnlyChild().
		initOnlyChild(val);
	}
	
	@Override
	public void setVal_String(String val) {
	  setVal(val);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String()
	{
		return val;
	}
	
	@Override
	public void setVal_Object(Object obj) {
	  setOnlyChild((IXholon)obj);
	}
	
	@Override
	public Object getVal_Object() {
	  return getOnlyChild();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection(org.primordion.xholon.base.IXholon)
	 */
	public Object handleNodeSelection(IXholon otherNode) {
		setOnlyChild(otherNode);
		return "";
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		// by default, onlyChild is the firstChild if nothing else has been specified
		if (onlyChild == null) {
			onlyChild = getFirstChild();
			initOnlyChild(val);
		}
		super.postConfigure();
	}
	
	/**
	 * Try to initialize the value of the only child.
	 * @param onlyChildStr The roleName of the onlyChild node.
	 */
	protected void initOnlyChild(String onlyChildStr) {
	  if (onlyChildStr == null) {return;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (onlyChildStr.equals(node.getRoleName())) {
				onlyChild = node;
				break;
			}
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#preAct()
	 */
	public void preAct() {
		if (onlyChild != null) {
      updateOnlyChild(0);
			// set onlyChild's nextSibling to null, to prevent its siblings from being invoked
			IXholon nextSib = onlyChild.getNextSibling();
			onlyChild.setNextSibling(null);
			onlyChild.preAct();
			onlyChild.setNextSibling(nextSib);
		}
		if (nextSibling != null) {
			nextSibling.preAct();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		if (onlyChild != null) {
      updateOnlyChild(1);
			// set onlyChild's nextSibling to null, to prevent its siblings from being invoked
			IXholon nextSib = onlyChild.getNextSibling();
			onlyChild.setNextSibling(null);
			onlyChild.act();
			onlyChild.setNextSibling(nextSib);
		}
		if (nextSibling != null) {
			nextSibling.act();
		}
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#postAct()
	 */
	public void postAct() {
		if (onlyChild != null) {
      updateOnlyChild(2);
			// set onlyChild's nextSibling to null, to prevent its siblings from being invoked
			IXholon nextSib = onlyChild.getNextSibling();
			onlyChild.setNextSibling(null);
			onlyChild.postAct();
			onlyChild.setNextSibling(nextSib);
		}
		if (nextSibling != null) {
			nextSibling.postAct();
		}
	}
	
	/**
	 * Subclasses of OrNode (such as OrNodeNext) may need to override this method.
	 */
	protected void updateOnlyChild(int ranActMethodIx) {}

	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg) {
		if (msg.getSignal() == ISignal.SIGNAL_ORNODE) {
			// this message is intended to be processed by the OrNode rather than by its only child
			onlyChild = (IXholon)msg.getData();
		}
		if (onlyChild != null) {
			// pass the message to the only child for processing
			onlyChild.processReceivedMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return "onlyChild: " + getOnlyChild();
	}
}
