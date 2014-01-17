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

package org.primordion.xholon.base;

import org.primordion.xholon.util.Misc;

/**
 * Port is one type of concrete "port" in a xholon application.
 * The other alternative is for a xholon to have a direct reference to another xholon.
 * There is more flexibility using the Port class, but there is also more overhead.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.4 (Created on August 21, 2006)
 */
public class Port extends Xholon implements IPort {
	private static final long serialVersionUID = -9158800706174399310L;

	/**
	 * Replications of this port.
	 * ref to remote port(s), or to remote xholon(s)
	 * using its nextSibling as the actual ref
	 */
	public IXholon[] replication = null;
	
	/**
	 * Provided interface.
	 */
	private IPortInterface providedInterface = null;
	
	/**
	 * Required interface.
	 */
	private IPortInterface requiredInterface = null;
	
	/**
	 * Role name.
	 * Has to be public, so reflection can read it in from xml file.
	 * Not used for now.
	 */
	//public String roleName = null;
	
	/**
	 * Whether or not this is a conjugated port.
	 */
	private boolean isConjugated = false;
	
	/**
	 * Specify whether a port should be explicitly set as a child of its owning xholon.
	 * This should only be set to true for debugging purposes.
	 */
	private static boolean shouldBeChild = false;
	
	// range values for use in setLink() and getRanges()
	private static int lowRangeVal[] = null; // low value in a range
	private static int highRangeVal[] = null; // high value in a range
	private static int currentRangeVal[] = null; // current value while iterating through a range
	private static int rangeIx[] = null; // position of the nth range in the xpathExpression string
	
	/*
	 * @see org.primordion.xholon.base.IPort#setProvidedInterface(org.primordion.xholon.base.IPortInterface)
	 */
	public void setProvidedInterface(IPortInterface providedInterface) {
		this.providedInterface = providedInterface;
	}

	/*
	 * @see org.primordion.xholon.base.IPort#getProvidedInterface()
	 */
	public IPortInterface getProvidedInterface() {
		return providedInterface;
	}

	/*
	 * @see org.primordion.xholon.base.IPort#setRequiredInterface(org.primordion.xholon.base.IPortInterface)
	 */
	public void setRequiredInterface(IPortInterface requiredInterface) {
		this.requiredInterface = requiredInterface;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#getRequiredInterface()
	 */
	public IPortInterface getRequiredInterface() {
		return requiredInterface;
	}

	/*
	 * @see org.primordion.xholon.base.IPort#setIsConjugated(boolean)
	 */
	public void setIsConjugated(boolean isConjugated) {
		this.isConjugated = isConjugated;
	}

	/*
	 * @see org.primordion.xholon.base.IPort#getIsConjugated()
	 */
	public boolean getIsConjugated() {
		return isConjugated;
	}
	
	/*
	 * A Port should not call postConfigure recursively.
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName() {
		String myName = "_";
		if (parentNode.getClass() == Port.class) { // this is a replication
			// port_<xholonID>_<portID>_<replicationID>
			myName += getParentNode().getParentNode().getId() + "_" + getParentNode().getId() + "_" + getId();
		}
		else { // this is a first level port whose parent is a xholon
			// port_<xholonID>_<portID>
			myName += getParentNode().getId() + "_" + getId();
		}
		return myName;
	}
	
	/**
	 * Create an instance of the Port class.
	 * @param portOwner The xholon that owns this port.
	 * @param multiplicity The number of replications of the port to create.
	 * @param provIfSigs Provided interface signal IDs.
	 * @param provIfNames Provided interface signal names.
	 * @param reqIfSigs Required interface signal IDs.
	 * @param reqIfNames Required interface signal names.
	 * @param isConjugated Whether or not the port instance is conjugated.
	 * @return A newly created instance of Port.
	 */
	public static IPort createPort(
			IXholon portOwner, int multiplicity,
			int[] provIfSigs, String[] provIfNames,
			int[] reqIfSigs, String[] reqIfNames,
			boolean isConjugated)
	{
		// create port and set its basic properties
		IPort iport = new Port();
		IXholonClass portClass = portOwner.getClassNode("Port");
		if (portClass != null) {
			iport.setXhc(portClass);
		}
		iport.setId(((Port)iport).getNextId());
		if (shouldBeChild) {
			iport.appendChild(portOwner);
		}
		else {
			iport.setParentNode(portOwner);
		}
		// set up the provided interface
		IPortInterface providedInterface = new PortInterface();
		if (provIfSigs == null && provIfNames != null) {
			provIfSigs = createSignals(provIfNames, portOwner);
		}
		providedInterface.setInterface(provIfSigs);
		providedInterface.setInterfaceNames(provIfNames);
		iport.setProvidedInterface(providedInterface);
		// set up the required interface
		IPortInterface requiredInterface = new PortInterface();
		if (reqIfSigs == null && reqIfNames != null) {
			reqIfSigs = createSignals(reqIfNames, portOwner);
		}
		requiredInterface.setInterface(reqIfSigs);
		requiredInterface.setInterfaceNames(reqIfNames);
		iport.setRequiredInterface(requiredInterface);
		// set isConjugated
		iport.setIsConjugated(isConjugated);
		// set replications
		iport.setReplications(multiplicity);
		
		return iport;
	}
	
	/**
	 * Create an array of signal IDs given an array of signal names.
	 * This method uses reflection to determine the IDs.
	 * @param signalName An array of signal names.
	 * @param portOwner Xholon that owns this port.
	 * @return An array of signal IDs.
	 */
	protected static int[] createSignals(String[] signalName, IXholon portOwner)
	{
		if (signalName.length == 0) {return null;}
		if (signalName[0].equals("")) {return null;}
		int[] signalId = new int[signalName.length];
		for (int i = 0; i < signalName.length; i++) {
			signalId[i] = ReflectionFactory.instance().getAttributeIntVal(portOwner, signalName[i]);
		}
		return signalId;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#setReplications(int)
	 */
	public void setReplications(int multiplicity) {
		if (multiplicity > 0) {
			replication = new Port[multiplicity];
		}
		for (int i = 0; i < multiplicity; i++) {
			replication[i] = new Port();
			replication[i].setId(i); // the replication's ID is its replication index
			replication[i].setParentNode(this);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#setLink(java.lang.String, int, int, int)
	 */
	public boolean setLink(int index, IXholon context, String xpathExprTemplate, int xholonIx, int portIx, int replicationIx)
	{
		int startIx = 0;
		int endIx = startIx;
		String xpathExpression = "";
		// get xholon part of the expression
		if (xholonIx != XPATH_EXPR_NO_INDEX) {
			endIx = xpathExprTemplate.indexOf(XPATH_EXPR_WILDCARD, startIx);
			xpathExpression = xpathExpression + xpathExprTemplate.substring(startIx, endIx) + xholonIx;
			startIx = endIx + 1;
		}
		// get port part of the expression
		if (portIx != XPATH_EXPR_NO_INDEX) {
			endIx = xpathExprTemplate.indexOf(XPATH_EXPR_WILDCARD, startIx);
			xpathExpression = xpathExpression + xpathExprTemplate.substring(startIx, endIx) + portIx;
			startIx = endIx + 1;
		}
		// get replication part of the expression
		if (replicationIx != XPATH_EXPR_NO_INDEX) {
			endIx = xpathExprTemplate.indexOf(XPATH_EXPR_WILDCARD, startIx);
			xpathExpression = xpathExpression + xpathExprTemplate.substring(startIx, endIx) + replicationIx;
			startIx = endIx + 1;
		}
		xpathExpression = xpathExpression + xpathExprTemplate.substring(startIx); // get rest of template
		// set the link
		return setLink(index, context, xpathExpression);
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#setLink(int, org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public boolean setLink(int index, IXholon context, final String xpathExpressionIn)
	{
			if (isConjugated) { // this must a "client" setting up the link(s)
				int multiplicity = replication.length;
				// get xholon range info contained in the xpathExpression (ex: A[1..2]/B/C[1..8])
				int numRanges = getRanges(xpathExpressionIn);
				
				IXholon remoteXholon;
				for (int m = index; m < multiplicity; m++) {
					String xpathExpressionDeranged = replaceRange(xpathExpressionIn, numRanges);
					int attrReplPos = xpathExpressionDeranged.indexOf("attribute::replication[");
					if (attrReplPos == -1) {
						// there is no "attribute::replication["
						remoteXholon = getXPath().evaluate(xpathExpressionDeranged, context);
					}
					else {
						// xpath.evalutate should ignore "/attribute::replication["
						remoteXholon = getXPath().evaluate(xpathExpressionDeranged.substring(0, attrReplPos-1), context);
						// replace a range in replication part of the xpathExpression
						remoteXholon = getReplication(xpathExpressionDeranged, remoteXholon);
					}
					if (remoteXholon == null) {continue;}

					// possibly set up the link to the server
					if (providedInterface.getSize() > 0) { // this is actually its requiredIF because it's conjugated
						replication[m].setNextSibling(remoteXholon);
					}
					else {
						// mark this replication as being in use if remoteXholon can send messages here
						// mark it as in use by putting itself as nextSibling
						if (requiredInterface.getSize() > 0) {
							replication[m].setNextSibling(replication[m]);
						}
					}
					// possibly set up reverse link as well
					if (requiredInterface.getSize() > 0) { // this is actually its providedIF
						if (remoteXholon.getClass() == Port.class) {
							if (remoteXholon.getNextSibling() == null) { // don't replace an existing connection
								remoteXholon.setNextSibling(replication[m]);
							}
						}
						else {
							// TODO how to figure out which remote port to use ?
							//      the remote xholon should be a port
							((XholonWithPorts)remoteXholon).port[0] = replication[m];
						}
					}
					else {
						// mark this replication as being in use if remoteXholon can send messages here
						// mark it as in use by putting itself as nextSibling
						if (remoteXholon.getClass() == Port.class) {
							if (remoteXholon.getNextSibling() == null) { // don't replace an existing connection
								remoteXholon.setNextSibling(remoteXholon);
							}
						}
						else {
							// TODO how to figure out which remote port to use ?
							//      the remote xholon should be a port
							((XholonWithPorts)remoteXholon).port[0] = ((XholonWithPorts)remoteXholon).port[0];
						}
					}
				}
			}
		return true;
	}
		
	/*
	 * @see org.primordion.xholon.base.IPort#getLink(int)
	 */
	public IXholon getLink(int index)
	{
		return ((IPort)replication[index]).getLink(); //getNextSibling();
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#getLink()
	 */
	public IXholon getLink()
	{
		return getNextSibling();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getPort(int)
	 */
	public IXholon getPort(int portNum)
	{
		if (replication != null) {
			if (replication.length > portNum) {
				return replication[portNum];
			}
		}
		return null;
	}
	
	/*
	 * Is at least one replication bound to something at the remote end?
	 * @see org.primordion.xholon.base.Xholon#isBound(org.primordion.xholon.base.IXholon)
	 */
	public boolean isBound(IXholon port)
	{
		for (int i = 0; i < replication.length; i++) {
			if (getLink(i) != null) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
	 */
	public void sendMessage(int signal, Object data, IXholon sender)
	{
		int index;
		if (sender == parentNode) { // this is a local port, possibly with a connector(s) to remote port/xholon(s)
			if (replication != null) {
				for (index = 0; index < replication.length; index++) { // possibly replicated = broadcast
					if (replication[index] != null) {
						IXholon remotePort = getLink(index);
						if (remotePort != null) {
							if (remotePort != replication[index]) {
								remotePort.sendMessage(signal, data, this);
								if (getInteractionsEnabled()) {
									getInteraction().addMessage(signal, data, sender, this, index);
								}
							}
							else {
								// this replication references itself
								// this means that no messages are allowed to flow in this direction
								// TODO log: no OUT signals defined, unable to send message
							}
						}
						else {
							// TODO log: no connection to remote port, unable to send message
						}
					}
					else {
						// TODO log: no connection to remote port, replication[index] is null, unable to send message
					}
				}
			}
			else { // this is a local port with no connector(s) to remote port/xholon(s)
				// don't send the message
				// TODO log: no connection to remote port, replication is null, unable to send message
			}
		}
		else {
			// this is a remote port that should deliver an instance of remote[] to its parent
			parentNode.sendMessage(signal, data, this, id);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IPort#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon, int)
	 */
	public void sendMessage(int signal, Object data, IXholon sender, int index)
	{
		if (sender == parentNode) { // this is a local port, possibly with a connector(s) to remote port/xholon(s)
			if (replication != null) {
				if (index < replication.length) {
					if (replication[index] != null) {
						IXholon remotePort = getLink(index);
						if (remotePort != null) {
							if (remotePort != replication[index]) {
								remotePort.sendMessage(signal, data, this);
								if (getInteractionsEnabled()) {
									getInteraction().addMessage(signal, data, sender, this, index);
								}
							}
							else {
								// this replication references itself
								// this means that no messages are allowed to flow in this direction
								// TODO log: no OUT signals defined, unable to send message
							}
						}
						else {
							// TODO log: no connection to remote port, unable to send message
						}
					}
					else {
						// TODO log: no connection to remote port, replication[index] is null, unable to send message
					}
				}
				else { // this is a local replicated port instance with no connector to remote port/xholon
					// don't send the message
					// TODO log: no connection to remote port, replication index exceeds max size, unable to send message
				}
			}
			else { // this is a local replicated port instance with no connector to remote port/xholon
				// don't send the message
				// TODO log: no connection to remote port, replication is null, unable to send message
			}
		}
		else {
			// this is a remote port that should deliver the message to its parent xholon
			parentNode.sendMessage(signal, data, this, index);
		}
	}
	
	/**
	 * If this XPath expression contains a range specification for a replication,
	 * replace the range with a specific valid value.
	 * Example ranges: [1..*] [1..3] [5..*] [2..4]
	 * @param xpathExpressionIn An XPath expression that may contain a range specification for replication.
	 * @param remoteXholon A remote xholon.
	 * @return A remote xholon (port) found using the attribute::replication part of the XPath expression.
	 */
	protected IXholon getReplication(String xpathExpressionIn, IXholon remoteXholon)
	{
		// at least for now, only replace a range for "attribute::replication"
		if (remoteXholon == null) {return remoteXholon;}
		int attrReplPos = xpathExpressionIn.indexOf("attribute::replication[");
		if (attrReplPos == -1) {return remoteXholon;} // doesn't use a replication range
		int dotDotPos = xpathExpressionIn.indexOf("..", attrReplPos);
		if (dotDotPos == -1) {return remoteXholon;} // doesn't use a range
		int bracketStartPos = dotDotPos;
		while (--bracketStartPos >= 0) {
			if (xpathExpressionIn.charAt(bracketStartPos) == '[') {
				break;
			}
		}
		//int bracketEndPos   = xpathExpressionIn.indexOf(']', bracketStartPos);
		int lowIndex = Misc.atoi(xpathExpressionIn, bracketStartPos+1) - 1; // make it zero-indexed
		int highIndex;
		if (xpathExpressionIn.charAt(dotDotPos+2) == '*') {
			highIndex = Integer.MAX_VALUE;
		}
		else {
			highIndex = Misc.atoi(xpathExpressionIn, dotDotPos+1);
		}
		highIndex--; // make it zero-indexed
		if (((Port)remoteXholon).replication != null) {
			for (int i = 0; i < ((Port)remoteXholon).replication.length; i++) {
				// ignore if it's outside the requested range
				if (lowIndex > i) {continue;}
				if (highIndex < i) {break;}
				if (((Port)remoteXholon).replication[i] == null) {continue;}
				if (((Port)remoteXholon).replication[i].getNextSibling() == null) {
					// this is the first available index in the remoteXholon's replication array
					return ((Port)remoteXholon).replication[i];
				}
			}
		}
		return null;
	}
		
	/**
	 * Replace a range specification such as [1..8] with an actual value such as [2].
	 * @param xpathExpressionIn XPath expression that contains a range specification.
	 * @param numRanges The number of ranges contained within the XPath expression.
	 * @return The "deranged" XPath expression.
	 */
	protected String replaceRange(String xpathExpressionIn, int numRanges)
	{
		String xpathExpressionOut = "";
		int pos = 0; // position in xpathExpressionIn
		for (int i = 0; i < numRanges; i++) {
			
			if (currentRangeVal[i] > highRangeVal[i]) { // has highVal limit been reached ?
				if (i == 0) { // if this is the leftmost range (outermost xholon in the tree)
					break; // final limit has been reached
				}
				else {
					currentRangeVal[i] = lowRangeVal[i];
				}
			}
			xpathExpressionOut += xpathExpressionIn.substring(pos, rangeIx[i]+1);
			xpathExpressionOut += currentRangeVal[i];
			pos = xpathExpressionIn.indexOf(']', rangeIx[i]); // point to the next ']'
			if (numRanges > i+1) { // are there more ranges to the right of me in the string
				if (currentRangeVal[i+1] == highRangeVal[i+1]) {
					currentRangeVal[i]++;
				}
			}
			else {
				currentRangeVal[i]++;
			}
		}
		xpathExpressionOut += xpathExpressionIn.substring(pos); // get rest of string
		return xpathExpressionOut;
	}
	
	/**
	 * Gather information about ranges contained within the XPath expression.
	 * A range is a pair of numbers separated by a "..".
	 * ex: [1..8]
	 * A range contained as part of "attribute::replication[]" is ignored (ex: attribute::replication[1..*]).
	 * @param xpathExpression An XPath expression.
	 * @return The number of ranges found.
	 */
	protected int getRanges(String xpathExpression)
	{
		int tempStore[] = new int[30]; // temporarily store parsed info here - rangeIx, low, high
		int tempStoreIx = 0;
		int numRanges = 0;
		// get number of ranges
		int dotDotPos = 0; // char index into xpathExpression while searching for ".."
		int attrReplPos = xpathExpression.indexOf("attribute::replication[");
		int ixTemp = 0; // 
		while (true) {
			dotDotPos = xpathExpression.indexOf("..", dotDotPos);
			if ((attrReplPos > ixTemp) && (attrReplPos < dotDotPos)) {break;} // ignore attribute::replication range
			if (dotDotPos == -1) {break;} // there are no more ranges
			numRanges++;
			ixTemp = dotDotPos;
			while (ixTemp >= 0) {
				if (xpathExpression.charAt(ixTemp) == '[') {
					tempStore[tempStoreIx++] = ixTemp;
					break;
				}
				ixTemp--;
			}
			tempStore[tempStoreIx++] = Misc.atoi(xpathExpression, ixTemp+1); // store a low value
			ixTemp = dotDotPos+1;
			tempStore[tempStoreIx++] = Misc.atoi(xpathExpression, ixTemp+1); // store a high value
			dotDotPos++;
		}
		if (numRanges > 0) {
			rangeIx = new int[numRanges];
			lowRangeVal = new int[numRanges];
			highRangeVal = new int[numRanges];
			currentRangeVal = new int[numRanges];
			tempStoreIx = 0;
			for (int i = 0; i < numRanges; i++) {
				rangeIx[i]         = tempStore[tempStoreIx++];
				lowRangeVal[i]     = tempStore[tempStoreIx++];
				highRangeVal[i]    = tempStore[tempStoreIx++];
				currentRangeVal[i] = 1;
			}
		}
		
		return numRanges;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((replication != null) && (replication.length > 0)) {
			outStr += " [";
			for (int i = 0; i < replication.length; i++) {
				if (replication[i] != null) {
					IXholon remotePort = getLink(i);
					if (remotePort != null) {
						if (remotePort.getClass() == Port.class) {
							outStr += " repl:" + remotePort.getParentNode().getParentNode().getName();
						}
						else { // this is a xholon rather than a port
							outStr += " repl:" + remotePort.getName();
						}
					}
				}
			}
			outStr += "]";
		}
		return outStr;
	}
}
