/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

import java.util.ArrayList;
import java.util.List;
//import java.util.Vector;

//import org.primordion.xholon.common.mechanism.CeControl;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.StringTokenizer;

/**
 * A Xholon with ports, so it can set up lateral connections with peers.
 * Ports allow dynamic interactions between Xholons at runtime.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sep 8, 2005)
 */
public abstract class XholonWithPorts extends Xholon {
	private static final long serialVersionUID = -556489515016015371L;
	
	// port array
	public IXholon[] port = null;
	
	/**
	 * Constructor.
	 */
	public XholonWithPorts() {}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		port = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isContainer()
	 */
	public boolean isContainer()
	{
		return xhc.isContainer();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isActiveObject()
	 */
	public boolean isActiveObject()
	{
		return xhc.isActiveObject();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isPassiveObject()
	 */
	public boolean isPassiveObject()
	{
		return xhc.isPassiveObject();
	}
	
	/**
	 * This setter is required by external tools such as Spring.
	 */
	public void setPort(IXholon[] port)
	{
		this.port = port;
	}
	
	/**
	 * This getter may be required by external tools such as Spring.
	 * @return
	 */
	public IXholon[] getPort()
	{
		return port;
	}
	
	public void setPort(int portNum, IXholon portRef)
	{
		if (port != null) {
			if (port.length > portNum) {
				port[portNum] = portRef;
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPort(int)
	 */
	public IXholon getPort(int portNum)
	{
		if (port != null) {
			if (port.length > portNum) {
				return port[portNum];
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#isBound(org.primordion.xholon.base.IXholon)
	 */
	public boolean isBound(IXholon port)
	{
		if (port == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPorts()
	 */
	public void setPorts() {
		// move up the inheritance hierarchy looking for first parent with a xhType
		IXholonClass iXhc = xhc;
		while ((!iXhc.isRootNode()) && (iXhc.getXhType() == IXholonClass.XhtypeNone)) {
			iXhc = (IXholonClass)iXhc.getParentNode();
		}
		// is this an active object?
		if (iXhc.isActiveObject() || (iXhc.getXhType() == IXholonClass.XhtypeConfigContainer)) {
			int maxPorts = getApp().getMaxPorts();
			if (maxPorts > 0) {
				port = new IXholon[maxPorts];
			}
		}
	}
	
	/**
	 * Resize the port array.
	 * This is intended for use by subclasses, such as XhChameleon.
	 * @param newSize The requested size of the new port[] array.
	 */
	protected void resizePortArray(int newSize) {}
	
	/**
	 * Set ports dynamically, for each port owned by this xholon.
	 * Search for an interaction partner that currently resides in the same container as this xholon.
	 * If a port already references a xholon that currently resides in the same container, then leave it as is.
	 * This method stops working as soon as it can't find an interaction partner for a specific port.
	 * It's assumed that each port already references some xholon of the correct type,
	 * but some of these references may be to xholons currently in other containers.
	 */
	protected void setPortsDynamically()
	{
		for (int i = 0; i < getApp().getMaxPorts(); i++) {
			if ((port[i] != null) && (port[i].getParentNode() != getParentNode())) {
				IXholon node = getNextSibling();
				if (node == null) {
					node = getParentNode().getFirstChild();
				}
				while (node != this) {
					if (node.getXhc() == port[i].getXhc()) {
						port[i] = node;
						break;
					}
					else {
						node = node.getNextSibling();
						if (node == null) {
							node = getParentNode().getFirstChild();
						}
					}
				}
				if (node == this) { // can't find an interaction partner
					return;
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		if (xhc == null) {
			logger.error("XholonWithPorts.configure(): xhClass is null\n");
			return;
		}
		IXholonClass ciXhc = xhc;
		String instructions = ciXhc.getConfigurationInstructions();
		
		while (!(ciXhc.isRootNode())) {
			ciXhc = (XholonClass)ciXhc.getParentNode();
			if (ciXhc.hasConfigurationInstructions()) {
				if (instructions.length() > 0) {
					instructions += ContainmentData.CD_SEPARATOR;
				}
				instructions += ciXhc.getConfigurationInstructions();
			}
		}
		
		int len = instructions.length();
		int instructIx = 0;
		while (instructIx < len) {
			switch( instructions.charAt(instructIx)) {
			case 'p': // port
				instructIx = configurePorts(instructions, instructIx);
				break;
			case 'P': // port with a name other than "port"
				instructIx = configurePorts(instructions, instructIx);
				break;
			case ContainmentData.CD_REPLICATION: // replication
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
			case ContainmentData.CD_CONFIG: // 'C' configuration instructions
				instructIx = configure(instructions, instructIx);
				break;
			default:
				if (Msg.errorM)
					println( "XholonWithPorts.configure(): error [" + instructions.charAt(instructIx) + "]\n" );
				instructIx++;
				break;
			} // end switch
		} // end while
		// execute recursively
		if (firstChild != null) {
			firstChild.configure();
		}
		if (nextSibling != null) {
			nextSibling.configure();
		}
	} // end configure()
	
	/**
	 * Configure ports.
	 * Example of a port specification in ClassDetails.xml:
	 * <p>&lt;port name="port" index="P_PARTNER" connector="#xpointer(ancestor::HelloWorldSystem/Hello)"/></p>
	 * @param instructions A String that contains port specifications.
	 * @param instructIx An index into the instructions String.
	 * @return An updated value of instructIx.
	 */
	public int configurePorts(String instructions, int instructIx)
	{
	  if (instructions.charAt(instructIx) == 'P') { // port(s) with another name
			return configurePortsWithOtherNames(instructions, instructIx);
		}
		instructIx += 5; // point at char after opening [
		int startPos = instructIx;
		while (instructions.charAt(instructIx) != ']') {
			instructIx++;
		}
		String portId = instructions.substring(startPos, instructIx);
		int portNum = ReflectionFactory.instance().getAttributeIntVal(this, portId);
		instructIx++; // point to = or {
		if (instructions.charAt(instructIx) == '=') { // direct port from a Xholon
			instructIx++; // point to first char after =
			resizePortArray(portNum+1);
			// handle multiple xpointers to same port; to allow alternatives if first one(s) don't work
			try {
			  if (port[portNum] == null) { // don't replace one that's already been assigned
					port[portNum] = getPortRef(instructions, instructIx);
				}
			} catch (RuntimeException e) {
				// in some circumstances this is OK
				//consoleLog(getName() + ": Unable to set up a port. The port variable may be null." + e.getMessage());
			}
		}
		else if (instructions.charAt(instructIx) == '{') { // Port port
			instructIx++;
			int multiplicity = 1; // default
			if (instructions.charAt(instructIx) != '}') {
				multiplicity = Misc.atoi(instructions, instructIx);
			}
			instructIx = instructions.indexOf('{', instructIx) + 1; // find start of next param
			boolean isConjugated = false; // default
			if (instructions.charAt(instructIx) != '}') {
				if (instructions.charAt(instructIx) == 't') { // true
					isConjugated = true;
				}
			}
			instructIx = instructions.indexOf('{', instructIx) + 1; // find start of next param
			String interfaceNames = instructions.substring(instructIx, instructions.indexOf('}', instructIx));
			// put the provided interface names into a String array
			StringTokenizer st = new StringTokenizer(interfaceNames, ",");
			int maxTokens = st.countTokens();
			String[] providedInterfaceNames = new String[maxTokens];
			int tokenIx;
			for (tokenIx = 0; tokenIx < maxTokens; tokenIx++) {
				providedInterfaceNames[tokenIx] = st.nextToken();
			}
			instructIx = instructions.indexOf('{', instructIx) + 1; // find start of next param
			interfaceNames = instructions.substring(instructIx, instructions.indexOf('}', instructIx));
			// put the required interface names into a String array
			st = new StringTokenizer(interfaceNames, ",");
			maxTokens = st.countTokens();
			String[] requiredInterfaceNames = new String[maxTokens];
			for (tokenIx = 0; tokenIx < maxTokens; tokenIx++) {
				requiredInterfaceNames[tokenIx] = st.nextToken();
			}
			try {
				port[portNum] = Port.createPort(
						this, multiplicity,
						null, providedInterfaceNames,
						null, requiredInterfaceNames,
						isConjugated);
			} catch (RuntimeException e) {
				logger.error("Unable to set up an IPort port. The port variable may be null.", e);
			}
		}
		
		while ((instructIx < instructions.length()) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
			instructIx++;
		}
		instructIx++;
		return instructIx;
	}
	
	/**
	 * Configure ports that have names other than "port".
	 * @param instructions ex: Ppartner[-1]="#pointer(...)"
	 * @param instructIx
	 * @return
	 */
	protected int configurePortsWithOtherNames(String instructions, int instructIx) {
	  //System.out.println("XholonWithPorts configurePortsWithOtherNames( " + this.getName());
		// for now, assume that the port is a scalar rather than an array
		String portName = "";
		instructIx++; // point one past the initial "P"
		while (instructions.charAt(instructIx) != '[') { // point at char after opening [
			portName = portName + instructions.charAt(instructIx);
			instructIx++;
		}
		instructIx++;
		int startPos = instructIx;
		while (instructions.charAt(instructIx) != ']') {
			instructIx++;
		}
		String portId = instructions.substring(startPos, instructIx);
		int portNum = ReflectionFactory.instance().getAttributeIntVal(this, portId);
		instructIx++; // point to = or {
		if (instructions.charAt(instructIx) == '=') { // direct port from a Xholon
		  //System.out.println("    1: " + portId + " " + portNum + " " + portName);
			instructIx++; // point to first char after =
			// locate the port ref
			IXholon portRef = getPortRef(instructions, instructIx);
			// get the port variable from the portName, and assign the port ref to the port
			//List portList = ReflectionFactory.instance().getAllPorts(this, true, portName); // GWT
			if (portNum == IPort.PORTINDEX_NULL) {
			  //System.out.println("    2: "); // + portList.size());
			  // this port is a scalar
				//if ((portList.size() == 0) // getAllPorts() may have failed, but set() methods might succeed
				//		|| ((portList.size() > 0) && (((PortInformation)portList.get(0)).getReffedNode() == null))) { // GWT getAllPorts() returns psudo-ports from the XholonClass
					//System.out.println("    3");
					// this port is currently unbound; don't replace one that's already been assigned
					boolean rc = ReflectionFactory.instance().setAttributeObjectVal_UsingSetter(this, portName, portRef);
					if (!rc) {
					  //System.out.println("    4");
						// setter didn't work, so try to set the field directly
						ReflectionFactory.instance().setAttributeObjectVal(this, portName, portRef);
					}
				//}
			}
			else {
				// this port is an array
				// TODO check portList to see if that port is already bound
				// TODO be able to call a new method r.setAttributeObjectArrayVal_UsingSetter()
				ReflectionFactory.instance().setAttributeObjectArrayVal(this, portName, portRef, portNum);
			}
		}
		
		while ((instructIx < instructions.length()) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
			instructIx++;
		}
		instructIx++;
		return instructIx;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		if (xhc == null) {
			logger.error("XholonWithPorts.postConfigure(): xhClass is null\n");
		}
		IXholonClass ciBec = xhc;
		String instructions = ciBec.getConfigurationInstructions();
		while ((!(ciBec.isRootNode())) && (instructions.equals(""))) {
			ciBec = (XholonClass)ciBec.getParentNode();
			instructions = ciBec.getConfigurationInstructions();
		}
		//if (instructions != null) {println("XholonWithPorts.postConfigure():" + instructions);}
		int len = instructions.length();
		int instructIx = 0;
		int portNum = IPort.PORTINDEX_NULL;
		int startPos;
		while (instructIx < len) {
			switch( instructions.charAt(instructIx)) {
			case 'P': // port with another name
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
			case 'p': // port
				instructIx += 5; // point at char after opening [
				startPos = instructIx;
				while (instructions.charAt(instructIx) != ']') {
					instructIx++;
				}
				String portId = instructions.substring(startPos, instructIx);
				portNum = ReflectionFactory.instance().getAttributeIntVal(this, portId);
				
				// Some direct ports from a Xholon can't be set up during configure()
				// because they point to a replication which didn't exist then.
				instructIx++; // point to = or {
				if (instructions.charAt(instructIx) == '=') { // direct port from a Xholon
					instructIx++; // point to first char after =
					resizePortArray(portNum+1);
					// handle multiple xpointers to same port; to allow alternatives if first one(s) don't work
					try {
					  if (port[portNum] == null) { // don't replace one that's already been assigned
						  port[portNum] = getPortRef(instructions, instructIx);
						}
					} catch (RuntimeException e) {
						// in some circumstances this is OK
						//consoleLog(getName() + ": Unable to set up a port. The port variable may be null.");
					}
				}
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
			case ContainmentData.CD_REPLICATION: // replication
				instructIx += 12; // point at char after opening [
				startPos = instructIx;
				boolean isRange = false; // may be a range of replications (ex: 1..3)
				while (instructions.charAt(instructIx) != ']') {
					if (instructions.charAt(instructIx) == '.') {
						isRange = true;
					}
					instructIx++;
				}
				String replIndexStr = instructions.substring(startPos, instructIx);
				String xpathExpr = instructions.substring(instructions.indexOf('(', instructIx) + 1, instructions.indexOf(')', instructIx));
				int replicationIndex;
				if (isRange) {
					int lowIndex = Misc.atoi(replIndexStr, 0);
					int highIndex = Misc.atoi(replIndexStr, replIndexStr.lastIndexOf('.') + 1);
					for (replicationIndex = lowIndex; replicationIndex <= highIndex; replicationIndex++) {
						((Port)port[portNum]).setLink(replicationIndex, this, xpathExpr);
					}
				}
				else {
					replicationIndex = Integer.parseInt(replIndexStr);
					((Port)port[portNum]).setLink(replicationIndex, this, xpathExpr);
				}
				
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
			case ContainmentData.CD_CONFIG: // 'C' configuration instructions; ignore
				instructIx++;
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
			default:
				if (Msg.errorM)
					println( "XholonWithPorts.postConfigure(): error [" + instructions.charAt(instructIx) + "]\n" );
				instructIx++;
				break;
			} // end switch
		} // end while
		super.postConfigure();
	} // end postConfigure()
	
	/*
	 * TODO XholonWithPorts needs to remove its ports, as well as removing itself.
	 * @see UML 2.1.1 spec, DestroyLinkAction
	 * @see org.primordion.xholon.base.Xholon#terminate()
	 */
	public void terminate()
	{
		super.terminate();
	}
	
	/*@Override
	public List<IXholon> searchForReferencingNodes()
	{
		List<IXholon> reffingNodes = new ArrayList<IXholon>();
		IXholon myRoot = getRootNode();
		if (myRoot.getClass() != Control.class) {
			((XholonWithPorts)myRoot).searchForReferencingNodes(this, reffingNodes);
		}
		return reffingNodes;
	}*/
	
	/**
	 * Search for instances of Xholon with ports that reference this instance.
	 * @param reffedNode The Xholon node that we're looking for references to.
	 * @param reffingNodes A list that is being filled with references.
	 */
	/*@Override
	protected void searchForReferencingNodes(IXholon reffedNode, List<IXholon> reffingNodes)
	{
	  if (port != null) {
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					if (port[i] == reffedNode) {
						reffingNodes.add(this);
					}
				}
			}
		}
		if (firstChild != null) {
			if (ClassHelper.isAssignableFrom(XholonWithPorts.class, firstChild.getClass())) {
				((XholonWithPorts)firstChild).searchForReferencingNodes(reffedNode, reffingNodes);
			}
		}
		if (nextSibling != null) {
			if (ClassHelper.isAssignableFrom(XholonWithPorts.class, nextSibling.getClass())) {
				((XholonWithPorts)nextSibling).searchForReferencingNodes(reffedNode, reffingNodes);
			}
		}
	}*/
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		/* GWT this can cause infinite recursion
		List portList = getAllPorts();
		if (portList.size() > 0) {
			outStr += " [";
			for (int i = 0; i < portList.size(); i++) {
				PortInformation portInfo = (PortInformation)portList.get(i);
				outStr += " " + portInfo;
			}
			outStr += "]";
		}
		*/
		if (getVal() != 0.0) {
			outStr += " val:" + getVal();
		}
		return outStr;
	}
}
