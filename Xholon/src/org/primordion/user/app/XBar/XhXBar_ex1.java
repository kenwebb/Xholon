/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.user.app.XBar;

import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.common.mechanism.CeXBar;

/**
	XBar application - Xholon Java
	<p>Xholon 0.7 http://www.primordion.com/Xholon</p>
*/
public class XhXBar_ex1 extends XholonWithPorts implements CeXBar_ex1, CeXBar {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;
private String val;
private float fval;

// Constructor
public XhXBar_ex1() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(String val) {this.val = val;}
public String getVal_String() {return val;}

public void setVal(float val) {fval = val;}
public float getVal_float() {return fval;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case VCE: // verb
		// handle agreement with subject
		// look for the subject, as a noun in the NP
		IXholon subject = getXPath().evaluate("ancestor::VP/../descendant::NP/descendant:N", this);
		if (subject == null) {
			// look for the subject, as a pronoun in the NP
			subject = getXPath().evaluate("ancestor::VP/../descendant::NP/descendant:Pron", this);
		}
		System.out.println("The subject of " + getVal_String() + " is " + subject);
		if (isSingular(subject)) {
			// fix the verb so it agrees
			makeVerbSingular();
		}
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void preAct()
{
	switch(xhc.getId()) {
	default:
		break;
	}
	super.preAct();
}

public void act()
{
	switch(xhc.getId()) {
	case XBarSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

public void postAct()
{
	switch(xhc.getId()) {
	default:
		break;
	}
	super.postAct();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			System.out.println("XhXBar: message with no receiver " + msg);
		}
		break;
	}
}

public String toString() {
	String outStr = "";
	if (getXhc().hasAncestor("XBarEntity")) {
		if (isSpecifier()) {outStr = "[specifier]";}
		else if (isAdjunct()) {outStr = "[adjunct]";}
		else if (isComplement()) {outStr = "[complement]";}
		return getName("^:C^^^") + ": " + toStringXBar() + outStr;
	}
	else {
		switch (this.getXhcId()) {
		case LexiconCE:
			return "Number of lexemes (words) in this lexicon: " + getNumChildren(false);
		case LexemeCE:
		{
			IXholon first = getFirstChild();
			if (first != null) {
				IXholon second = first.getNextSibling();
				if (second == null) {
					return first.getVal_String();
				}
				else {
					IXholon third = second.getNextSibling();
					if (third == null) {
						return first.getVal_String() + " " + second.getVal_String();
					}
					else {
						return first.getVal_String() + " " + second.getVal_String() + " " + third.getVal_float();
					}
				}
			}
			break;
		}
		case LemmaCE:
			if (parentNode.getParentNode().getRoleName().equals("TibetanLexicon")) {
				System.out.println("Tibetan: " + getVal_String() + " \u0F44\u00F6\u0915\u0416");
			}
			return getVal_String();
		case PosCE:
			return getVal_String();
		case FrqCE:
			return "" + getVal_float();
		default:
			break;
		}
	}
	
	outStr = getName();
	if ((port != null) && (port.length > 0)) {
		outStr += " [";
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				outStr += " port:" + port[i].getName();
			}
		}
		outStr += "]";
	}
	if (getVal_String() != null) {
		outStr += " val:" + getVal_String();
	}
	return outStr;
}

public String toStringXBar() {
	String outStr = "";
	if (isLeaf()) {
		return getVal_String() + " ";
	}
	else {
		// return a concatenation of all leaf nodes
		XhXBar_ex1 node = (XhXBar_ex1)getFirstChild();
		while (node != null) {
			outStr += node.toStringXBar();
			node = (XhXBar_ex1)node.getNextSibling();
		}
		return outStr;
	}
}

/**
 * Is this a Specifier node.
 * In English, a Specifier node has a parent that's an XP,
 * and has a next sibling that's an X_ .
 * @return true or false
 */
protected boolean isSpecifier()
{
	if ((parentNode.getXhc().hasAncestor("XP")) && hasNextSibling() && (nextSibling.getXhc().hasAncestor("X_"))) {
		return true;
	}
	return false;
}

/**
 * Is this an Adjunct node.
 * In English, an Adjunct node has a parent that's an X_,
 * and has a next or previous sibling that's an X_ .
 * @return true or false
 */
protected boolean isAdjunct()
{
	if ((parentNode.getXhc().hasAncestor("X_"))) {
		if (hasNextSibling() && (nextSibling.getXhc().hasAncestor("X_"))) {
			return true;
		}
		IXholon previousSibling = getPreviousSibling();
		if ((previousSibling != null) && (previousSibling.getXhc().hasAncestor("X_"))) {
			return true;
		}
	}
	return false;
}

/**
 * Is this a Complement node.
 * In English, a Complement node has a parent that's an X_,
 * and has a next sibling that's an X .
 * @return true or false
 */
protected boolean isComplement()
{
	IXholon previousSibling = getPreviousSibling();
	if ((parentNode.getXhc().hasAncestor("X_")) && (previousSibling != null) && (previousSibling.getXhc().hasAncestor("X"))) {
		return true;
	}
	return false;
}

/**
 * Is this English noun or pronoun singular.
 * TODO the implementation is currently very simplistic
 * @return true or false
 */
protected boolean isSingular(IXholon nounOrPronoun)
{
	if (nounOrPronoun == null) {return false;}
	String s = nounOrPronoun.getVal_String();
	if (s == null) {return false;}
	if (s.endsWith("s")) {
		return false;
	}
	else {
		return true;
	}
}

/**
 * If the current node is a verb, make it singular.
 * TODO this is much too simplistic
 */
protected void makeVerbSingular()
{
	setVal(getVal_String() + "s");
}

}
