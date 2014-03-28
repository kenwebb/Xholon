/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.memcomp.base;

import java.util.Vector;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.util.MiscRandom;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 2, 2006)
 */
public abstract class XhAbstractMemComp extends XholonWithPorts implements IMembraneComputing {

	protected static int currentPriority = 0;
	
	// are rules selected in a deterministic way, or are they selected in a random order?
	protected static boolean isDeterministic = true;
	// are rules maximally parallel?
	protected static boolean isMaximallyParallel = true;
	
	// See Paun (1999) p. 119 for the two conditions that must be met for a computation to be successful
	protected static boolean computationComplete   = false;
	protected static boolean computationSuccessful = false;
	
	public int state = 0;
	public String roleName = null;
	public int priority = PRIORITY_DEFAULT;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = 0;
		roleName = null;
		//priority = PRIORITY_DEFAULT;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/**
	 * Is the computation complete?
	 * @return true or false
	 */
	public static boolean isComputationComplete() {return computationComplete;}
	
	/**
	 * Is the computation successful?
	 * @return true or false
	 */
	public static boolean isComputationSuccessful() {return computationSuccessful;}
	
	/**
	 * Apply a rule, in which all v objects are inside the same membrane as the u objects.
	 * @param uObject One or more u objects.
	 * @param vObject One or more v objects, or null.
	 * @return The number of times the rule matches.
	 */
	protected int applyRule(String uObject, String vObject)
	{
		return applyRule(uObject, vObject, null, false);
	}
	
	/**
	 * Apply a rule with promoters and/or inhibitors,
	 * in which all v objects are inside the same membrane as the u objects.
	 * @param uObject One or more u objects.
	 * @param vObject One or more v objects, or null.
	 * @param promoter Object(s) that promote application of the rule, or null.
	 * @param inhibitor Object(s) that inhibit application of the rule, or null.
	 * @return The number of times the rule matches.
	 */
	protected int applyRule(String uObject, String vObject, String promoter, String inhibitor)
	{
		return applyRule(uObject, vObject, null, false, promoter, inhibitor);
	}
	
	/**
	 * Apply a rule that includes one or more promoters, and/or one or more inhibitors.
	 * @param uObject One or more u objects.
	 * @param vObject One or more v objects, or null.
	 * @param vPortNum One or more v object targets, or null.
	 * @param dissolve Whether or not to dissolve the membrane.
	 * @param promoter Object(s) that promote application of the rule, or null.
	 * @param inhibitor Object(s) that inhibit application of the rule, or null.
	 * @return The number of times the rule matches.
	 */
	protected int applyRule(String uObject, String vObject, int[] vPortNum, boolean dissolve,
			String promoter, String inhibitor)
	{
		IXholon portHere = ((XholonWithPorts)parentNode).getPort(P_HERE);
		char treeObjectChar[] = getChildren(portHere); // objects from the tree
		// don't apply the rule if promoters are required, and there are none in the tree
		if (promoter != null) {
			if (!matchesTreeNonDestruct(promoter.toCharArray(), treeObjectChar)) {
				return 0;
			}
		}
		// don't apply the rule if inhibitors would block it, and if they all exist in the tree
		if (inhibitor != null) {
			if (matchesTreeNonDestruct(inhibitor.toCharArray(), treeObjectChar)) {
				return 0;
			}
		}
		return applyRule(uObject, vObject, vPortNum, dissolve);
	}
	
	/**
	 * Apply a rule that is either cooperative, and/or that has >1 v objects.
	 * A cooperative rule is one in which the radius of u is greater than 1 (>1 u objects).
	 * The radius of u is the length of u in characters.
	 * <p>The generic structure of a rule is u -> v.</p>
	 * <p>ex: a -> (a,in2)b</p>
	 * <p>ex: dd -> (a,in4)</p>
	 * <p>ex: ac -> DISSOLVE</p>
	 * <p>ex: a -> ab</p>
	 * <p>ex: f -> ff</p>
	 * <p>ex: ff -> af</p>
	 * <p>ex: ac -> c</p>
	 * <p>ex: a -> a(b,in2)(c,in2)(c,in2)</p>
	 * <p>ex: aa -> (a,out)(a,out)</p>
	 * <p>ex: a -> abbbbbb</p>
	 * <p>ex: XADE -> XBBDE</p>
	 * @param uObject One or more u objects.
	 * @param vObject One or more v objects, or null.
	 * @param vPortNum One or more v object targets, or null.
	 * @param dissolve Whether or not to dissolve the membrane.
	 * @return The number of times the rule matches.
	 */
	protected int applyRule(String uObject, String vObject, int[] vPortNum, boolean dissolve)
	{
	  int i;
		int matchCount = 0;
		// don't apply rule if a previous rule had a higher priority
		if (currentPriority > priority) {return matchCount;}
		IXholon portDest[] = null;
		IXholon portHere = ((XholonWithPorts)parentNode).getPort(P_HERE);
		if (vPortNum == null) {
			if (vObject != null) {
				int len = vObject.length();
				portDest = new IXholon[len];
				for (i = 0; i < len; i++) {
					portDest[i] = portHere;
				}
			}
		}
		else {
			portDest = new IXholon[vPortNum.length];
			// all vPorts must exist
			for (i = 0; i < vPortNum.length; i++) {
				portDest[i] = ((XholonWithPorts)parentNode).getPort(vPortNum[i]);
				if (!(isNotDissolved(portDest[i]))) {
					// can't apply the rule because one of the required vPorts does not exist
					return matchCount;
				}
			}
		}
		// match uObject with current contents of P_HERE
		char uObjectChar[] = uObject.toCharArray(); // u objects from the rule
		char treeObjectChar[] = getChildren(portHere); // objects from the tree
		// while there's a match
		boolean match = matchesTree(uObjectChar, treeObjectChar);
		if (match) {
			while (match) {
				matchCount++;
				IXholon objHere;
				// remove from P_HERE one instance of each object contained within uObject
				for (i = 0; i < uObjectChar.length; i++) {
					objHere = getXPath().evaluate(uObject.substring(i, i+1), portHere);
					objHere.removeChild();
					objHere.remove();
				}
				// create new vObject instances, each within the correct vPort
				if (vObject != null) {
					createVObjects(vObject, portDest);
				}
				if (isMaximallyParallel) {
					// is there more than one instance of u within the tree?
					treeObjectChar = getChildren(portHere);
					match = matchesTree(uObjectChar, treeObjectChar);
				}
				else {
					match = false;
				}
			}
			currentPriority = priority;
			computationComplete = false;
			// dissolve the membrane ?
			if (dissolve) {
				IXholon memHere = getXPath().evaluate("ancestor::Membrane", portHere);
				memHere.sendMessage(SIG_DISSOLVE, null, this);
			}
		}
		return matchCount;
	}
	
	/**
	 * Apply a symport rule.
	 * @param inoutObject The object(s) being moved in or out.
	 * @param direction The direction of movement, either SD_IN or SD_OUT.
	 * @return The number of times the rule matches.
	 */
	protected int applyRuleSymport(String inoutObject, int direction)
	{
		int i;
		int matchCount = 0;
		// don't apply rule if a previous rule had a higher priority
		if (currentPriority > priority) {return matchCount;}
		// the source of Objects
		IXholon portFrom  = null;
		// the destination of Objects
		IXholon portTo = null;
		switch(direction) {
		case SD_IN:
			// move the inoutObjects inwards, i.e. from the enclosing membrane to here
			portFrom = ((XholonWithPorts)parentNode).getPort(P_OUT);
			portTo   = ((XholonWithPorts)parentNode).getPort(P_HERE);
			break;
		case SD_OUT:
			// move the inoutObjects outwards, i.e. from here to the enclosing membrane
			portFrom = ((XholonWithPorts)parentNode).getPort(P_HERE);
			portTo   = ((XholonWithPorts)parentNode).getPort(P_OUT);
			break;
		default:
			break;
		}
		// match inoutObject with current contents of source
		char uObjectChar[] = inoutObject.toCharArray(); // objects from the rule
		char treeObjectChar[] = getChildren(portFrom); // objects from the tree
		// while there's a match
		boolean match = matchesTree(uObjectChar, treeObjectChar);
		if (match) {
			while (match) {
				matchCount++;
				IXholon objHere;
				// transfer one instance of each object contained within inoutObject
				for (i = 0; i < uObjectChar.length; i++) {
					objHere = getXPath().evaluate(inoutObject.substring(i, i+1), portFrom);
					objHere.removeChild(); // remove it from its current location in the tree
					portTo.sendMessage(SIG_APPEND, objHere, this);
				}
				if (isMaximallyParallel) {
					// is there more than one instance of inout within the tree?
					treeObjectChar = getChildren(portFrom);
					match = matchesTree(uObjectChar, treeObjectChar);
				}
				else {
					match = false;
				}
			}
			currentPriority = priority; // does priority apply to Symport rules?
			computationComplete = false;
		}
		return matchCount;
	}
	
	/**
	 * Apply an antiport rule.
	 * @param outObject
	 * @param inObject
	 * @return The number of times the rule matches.
	 */
	protected int applyRuleAntiport(String outObject, String inObject)
	{
		int i;
		int matchCount = 0;
		// don't apply rule if a previous rule had a higher priority
		if (currentPriority > priority) {return matchCount;}
		// the location of Objects outside the membrane
		IXholon portOut = ((XholonWithPorts)parentNode).getPort(P_OUT);
		// any object in the Environment is available in unlimited quantities
		boolean isOutEnvironment = false;
		if (portOut.getParentNode().getXhcName().equals("Environment")) {
			isOutEnvironment = true;
		}
		// the location of Objects inside the membrane
		IXholon portIn = ((XholonWithPorts)parentNode).getPort(P_HERE);
		// match outObject with current contents of what's inside
		char outObjectChar[] = outObject.toCharArray(); // out objects from the rule
		char treeObjectChar[] = getChildren(portIn); // objects from the tree
		boolean match = matchesTree(outObjectChar, treeObjectChar);
		if (!match) {
			// no match on outObject
			return matchCount;
		}
		// match inObject with current contents of what's outside
		char inObjectChar[] = inObject.toCharArray(); // in objects from the rule
		treeObjectChar = getChildren(portOut); // objects from the tree
		if (isOutEnvironment) {
			match = matchesTreeNonDestruct(inObjectChar, treeObjectChar);
		}
		else {
			match = matchesTree(inObjectChar, treeObjectChar);
		}
		if (match) {
			// while there's a match
			while (match) {
				matchCount++;
				IXholon obj;
				// transfer one instance of each object contained within outObject
				for (i = 0; i < outObjectChar.length; i++) {
					obj = getXPath().evaluate(outObject.substring(i, i+1), portIn);
					obj.removeChild(); // remove it from its current location in the tree
					portOut.sendMessage(SIG_APPEND, obj, this);
				}
				// transfer one instance of each object contained within inObject
				for (i = 0; i < inObjectChar.length; i++) {
					if (isOutEnvironment) {
						// create new instance of object that exists in the environment
						try {
							obj = getFactory().getXholonNode();
						} catch (XholonConfigurationException e) {
							logger.error(e.getMessage(), e.getCause());
							return matchCount;
						}
						obj.setId(getNextId());
						obj.setXhc(getClassNode(inObject.substring(i, i+1)));
					}
					else {
						// get instance of object that was in outside region
						obj = getXPath().evaluate(inObject.substring(i, i+1), portOut);
						obj.removeChild(); // remove it from its current location in the tree
					}
					portIn.sendMessage(SIG_APPEND, obj, this);
				}
				if (isMaximallyParallel) {
					// is there more than one instance of outObject within the tree?
					treeObjectChar = getChildren(portIn);
					match = matchesTree(outObjectChar, treeObjectChar);
					if (isOutEnvironment) {
						// don't need to recheck; anything previously found in Environment will always be there
					}
					else {
						match = matchesTree(inObjectChar, treeObjectChar);
					}
				}
				else {
					match = false;
				}
			}
			currentPriority = priority; // does priority apply to Symport rules?
			computationComplete = false;
		}
		return matchCount;
	}
	
	/**
	 * Do the objects in the u part of the rule match what's currently in the Objects subtree ?
	 * @param u The u part of a rule.
	 * @param tree The current contents of an Objects subtree.
	 * @return true or false
	 */
	protected boolean matchesTree(char u[], char tree[])
	{
		int i,j;
		boolean charFound;
		for (i = 0; i < u.length; i++) {
			charFound = false;
			for (j = 0; j < tree.length; j++) {
				if (u[i] == tree[j]) {
					charFound = true;
					tree[j] = ' '; // blank out the found character so can't find that one again
					break; // exit the inner for loop
				}
			}
			if (!charFound) {
				return false; // a required character in u was not found
			}
		}
		return true; // all characters in u were found within tree
	}
	
	/**
	 * Do the in objects match what's currently in the Environment Objects subtree ?
	 * In the Environment, there are an infinite number of copies of each object that exists there.
	 * This function is also used by applyRule() with promoters and inhibitors.
	 * @param in The in part of an Antiport rule, or promoters/inhibitors.
	 * @param tree The current contents of the Environment Objects subtree.
	 * @return true or false
	 */
	protected boolean matchesTreeNonDestruct(char in[], char tree[])
	{
		int i,j;
		boolean charFound;
		for (i = 0; i < in.length; i++) {
			charFound = false;
			for (j = 0; j < tree.length; j++) {
				if (in[i] == tree[j]) {
					charFound = true;
					// allow the found character to be found any number of times
					break; // exit the inner for loop
				}
			}
			if (!charFound) {
				return false; // a required character in u was not found
			}
		}
		return true; // all characters in u were found within tree
	}
	
	/**
	 * Get the children of parent as a character array.
	 * This method is intended to return the children of an instance of Objects.
	 * Each child of Objects has a one-character XholonClass name.
	 * @return An array in which each character is the class name of an object.
	 */
	protected char[] getChildren(IXholon parent)
	{
		Vector objectsV = parent.getChildNodes(false);
		int numObjects = objectsV.size();
		char objects[] = new char[numObjects];
		for (int i = 0; i < numObjects; i++) {
			objects[i] = ((IXholon)objectsV.elementAt(i)).getXhcName().charAt(0);
		}
		return objects;
	}
	
	/**
	 * Create new v objects.
	 * @param vObjects One or more v objects; ex: "ab"
	 * @param vPort Objects instances within which the new v objects should be created.
	 * There must be one vPort item for each character in vObjects.
	 */
	protected void createVObjects(String vObjects, IXholon vPort[])
	{
		//IXholon vNode;
		String className;
		for (int i = 0; i < vObjects.length(); i++) {
			//vNode = factory.getXholonNode();
			//vNode.setId(getNextId());
			className = vObjects.substring(i, i+1);
			//vNode.setXhc(XholonClass.getClassNode(className));
			//vPort[i].sendMessage(SIG_APPEND,vNode,this);
			createObject(className, vPort[i]);
		}
	}
	
	/**
	 * Create a new object and send it to its destination.
	 * @param className 
	 * @param destPort 
	 */
	protected void createObject(String className, IXholon destPort)
	{
		IXholon vNode = null;
		try {
			vNode = getFactory().getXholonNode();
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return;
		}
		vNode.setId(getNextId());
		vNode.setXhc(getClassNode(className));
		destPort.sendMessage(SIG_APPEND,vNode,this);
	}
	
	/**
	 * Is the destination within a dissolved membrane?
	 * @param portDest
	 * @return true or false
	 */
	protected boolean isNotDissolved(IXholon portDest)
	{
		// special case of Objects within Environment
		if (portDest.getParentNode().getXhcName().equals("Environment")) {
			return true;
		}
		// typical case of Objects within MRegion within Membrane; this Membrane must have a parent
		else if ((portDest.getParentNode().hasParentNode())
				&& (portDest.getParentNode().getParentNode().hasParentNode())) {
			return true;
		}
		return false;
	}
	
	/**
	 * This membrane dissolves itself.
	 */
	protected void dissolveMembrane()
	{
		// move all objects to outer membrane 
		IXholon portHere = getXPath().evaluate("MRegion/Objects", this);
		IXholon obj = portHere.getFirstChild();
		IXholon portOut = getXPath().evaluate("../MRegion/Objects", this);
		while (obj != null) {
			obj.removeChild();
			obj.appendChild(portOut);
			obj = portHere.getFirstChild();
		}
		// move any sub-membranes
		IXholon memSub = getXPath().evaluate("Membrane", this);
		while (memSub != null) {
			memSub.removeChild();
			memSub.appendChild(parentNode);
			memSub = getXPath().evaluate("Membrane", this);
		}
		// TODO optionally move rules
		//
		// the Membrane will now dissolve itself
		removeChild(); // remove from the tree
		remove(); // return to the factory
	}
	
	/**
	 * This is the code that RuleSet instances should execute in their act() function.
	 */
	protected void actRuleSet()
	{
	  currentPriority = PRIORITY_LOWEST;
		// optionally randomly sort the order of the rules to make rule selection nondeterministic
		// do this by swapping the type and id of up to one pair of rules each timeStep
		if (!isDeterministic) {
			Vector rules = getChildNodes(false);
			int numRules = rules.size();
			if (numRules > 1) {
				int rNum1 = MiscRandom.getRandomInt(0, numRules);
				int rNum2 = MiscRandom.getRandomInt(0, numRules);
				// are these two random numbers different?
				if (rNum1 != rNum2) {
					IXholon xholon1 = (IXholon)rules.elementAt(rNum1);
					IXholon xholon2 = (IXholon)rules.elementAt(rNum2);
					// are the priorities the same? (higher priority rules must preceed lower priority ones)
					if (((XhAbstractMemComp)xholon1).priority == ((XhAbstractMemComp)xholon2).priority) {
						// swap the two rules by swapping their type and id
						IXholonClass tempXhc = xholon1.getXhc();
						xholon1.setXhc(xholon2.getXhc());
						xholon2.setXhc(tempXhc);
						int tempId = xholon1.getId();
						xholon1.setId(xholon2.getId());
						xholon2.setId(tempId);
					}
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		if (xhc.getName().equals("CompletionDetector")) {
			if (computationComplete) {
				// confirm that the output membrane exists
				if (port[P_OUTPUT].getParentNode() == null) {
					// it no longer exists; computation complete but unsuccessful
				}
				else {
					// computation complete and successful
					computationSuccessful = true;
				}
			}
		}
		else if (xhc.getName().equals("SuperCellSystem")) {
			// Process ALL messages in the Q.
			// This includes messages added to Q during this timestep.
			processMessageQ();
		}
		
		super.postAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processMessageQ()
	 */
	public void processMessageQ()
	{
		// Process ALL messages in the Q.
		// This includes messages added to Q during this timestep.
		IMessage rxMsg = (IMessage)getMsgQ().dequeue();
		while (rxMsg != null) {
		  if (getInteractionsEnabled()) {
				getInteraction().processReceivedMessage(rxMsg);
			}
			try {
			  rxMsg.getReceiver().processReceivedMessage(rxMsg);
			} catch (RuntimeException e1) {
			  logger.error("", e1);
				//e1.printStackTrace();
			}
			rxMsg = (IMessage)getMsgQ().dequeue();
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		int event = msg.getSignal();
		IXholon msgData;
		
		if (xhc.getName().equals("Objects")) {
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIG_APPEND:
					msgData = (IXholon)msg.getData();
					if (msgData != null) {
						msgData.appendChild(this);
					}
					else {
						// something went wrong
					}
				}
			}
		}
		else if (xhc.getName().equals("Membrane")) {
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIG_APPEND:
					msgData = (IXholon)msg.getData();
					if (msgData != null) {
						msgData.appendChild(this);
					}
					else {
						// something went wrong
					}
					break;
				case SIG_DISSOLVE:
					if (msg.getSender() == this) {
						// this is a delayed message from the same Membrane; dissolve now
						dissolveMembrane();
					}
					else {
						// send a message to self, so will dissolve only after all other internal actions are taken
						this.sendMessage(SIG_DISSOLVE, null, this);
					}
					break;
				}
			}
		}
		else if (xhc.getName().equals("SuperCellSystem")) {
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIG_APPEND:
					msgData = (IXholon)msg.getData();
					if (msgData != null) {
						msgData.appendChild(this);
					}
					else {
						// something went wrong
					}
				}
			}
		}
		else {
			println("processReceivedMessage: unhandled msg " + msg + " received by " + getName());
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if (xhc.getName().equals("RuleSet")) {
			if ((port != null) && (port.length > 0)) {
				outStr += " [";
				for (int i = 0; i < port.length; i++) {
					if (port[i] != null) {
						outStr += " port:" + port[i].getName();
					}
				}
				outStr += "]";
			}
		}
		else if (xhc.hasAncestor("Rule")) {
			outStr += " priority=" + priority;
		}
		else if (xhc.getName().equals("Objects")
				|| xhc.getName().equals("SuperCellSystem")) {
			outStr += "  " + String.valueOf(getChildren(this));
		}
		return outStr;
	}
}
