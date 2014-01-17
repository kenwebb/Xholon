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

package org.primordion.user.app.Generic;

//import java.util.Iterator;
import java.util.Vector;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Used for test purposes. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 8, 2005)
 */

public class XhGeneric extends XholonWithPorts {

	// Input parameters.
	/** Number of the test to run. */
	protected static int testNumber = 1; // default is 1 if nothing specified in config file
	
	/**
	 * Constructor.
	 *
	 */
	public XhGeneric() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		if (xhc == null) {
			logger.error("Xholon::configure: xhClass is null");
			return;
		}
		String instructions = xhc.getConfigurationInstructions();
		if (Msg.infoM) System.out.print( getName() + ": " + instructions );
		// carry out the instructions
		int len = instructions.length();
		int instructIx = 0;
		while (instructIx < len) {
			switch( instructions.charAt(instructIx)) {			
			default:
				if (Msg.errorM)
					System.out.println( "Xholon configure: error [" + instructions.charAt(instructIx) + "]\n" );
				instructIx++;
				break;
			} // end switch
		} // end while

		super.configure();
	} // end configure()
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		super.preAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		// test inserting nodes
		if (isRootNode()) {
		//if (getName().equals("node_0")) {
		//if (getName().equals("node_1")) {
		//if (getName().equals("node_9")) {
		//if (getName().equals("node_2")) {
		//if (getName().equals("node_4")) {
		//if (getName().equals("node_11")) {
			IXholon newNode = null;
			try {
				newNode = getFactory().getXholonNode();
			} catch (XholonConfigurationException e) {
				logger.error(e.getMessage(), e.getCause());
				return;
			}
			newNode.setId(getNextId());
			newNode.setXhc(getClassNode( "Node" ));
			
			//newNode.insertBeforeSibling(this);
			//newNode.insertAfterSibling(this);
			newNode.insertFirstChild(this);
			//newNode.insertLastChild(this);
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		super.postAct();
	}
	
	/**
	 * Set which test will be run when user clicks on a node.
	 * @param tNumber The number of the test.
	 */
	public static void setTestNumber(int tNumber)
	{
		testNumber = tNumber;
	}
	
	/**
	 * Get the test number.
	 * @return The test number.
	 */
	public static int getTestNumber()
	{
		return testNumber;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String out = null;
		int i;

		out = getName() + "[";
		
		switch (testNumber) {
		case 1:
			out += " rootNode:" + getRootNode().getName();
			out += " getNumLevels:" + getNumLevels();
			out += " height:" + height();
			out += " treeSize:" + treeSize();
			break;
		case 2:
			out += " hasChildOrSibling:" + hasChildOrSiblingNodes();
			out += " hasFirstChild:" + hasChildNodes();
			out += " hasParentNode:" + hasParentNode();
			out += " hasNextSibling:" + hasNextSibling();
			break;
		case 3:
			out += " isExternal:" + isExternal();
			out += " isInternal:" + isInternal();
			out += " isLeaf:" + isLeaf();
			out += " isRoot:" + isRootNode();
			out += " isTerminal:" + isExternal();
			break;
		case 4: // right sibling
			out += " nextSibling:";
			IXholon rightSibling = getNextSibling();
			if (rightSibling == null) {
				out += null;
			}
			else {
				out += rightSibling.getName();
			}
			break;
		case 5: // first child
			out += " firstChild:";
			IXholon leftChild = getFirstChild();
			if (leftChild == null) {
				out += null;
			}
			else {
				out += leftChild.getName();
			}
			break;
		case 6: // next sibling
			out += " nextSibling:";
			IXholon leftSibling = getPreviousSibling();
			if (leftSibling == null) {
				out += null;
			}
			else {
				out += leftSibling.getName();
			}
			break;
		case 7: // children Iterator
			out += " getNumChildren:" + getNumChildren(false);
			out += " getChildIterator:{";
			out += " Iterator is no longer supported}";
			break;
		case 8: // children Vector
			out += " getNumChildren:" + getNumChildren(false);
			out += " getChildren:{";
			Vector v = getChildNodes(false);
			int vSize = v.size();
			for (i = 0; i < vSize; i++) {
				out += ((IXholon)v.elementAt(i)).getName();
				if (i < vSize-1) {
					out += ",";
				}
			}
			out += "}";
			break;
		case 9: // children Nth
			int numChildren = getNumChildren(false);
			out += " getNumChildren:" + numChildren;
			out += " getNthChild:{";
			for (i = 0; i < numChildren; i++) {
				out += getNthChild(i, false).getName();
				if (i < numChildren-1) {
					out += ",";
				}
			}
			out += "}";
			break;
		case 10: // leftmost sibling
			out += " leftmostSibling:";
			IXholon leftmostSibling = getFirstSibling();
			if (leftmostSibling == null) {
				out += null;
			}
			else {
				out += leftmostSibling.getName();
			}
			break;
		case 11: // rightmost sibling
			out += " rightmostSibling:";
			IXholon rightmostSibling = getLastSibling();
			if (rightmostSibling == null) {
				out += null;
			}
			else {
				out += rightmostSibling.getName();
			}
			break;
		case 12: // rightmost child
			out += " rightmostChild:";
			IXholon rightmostChild = getLastChild();
			if (rightmostChild == null) {
				out += null;
			}
			else {
				out += rightmostChild.getName();
			}
			break;
		case 13: // siblings Iterator
			out += " getSiblingIterator:{";
			out += " Iterator is no longer supported}";
			break;
		default:
			out += " " + testNumber + " is an invalid TestNumber. Edit Generic_xhn.xml.";
			break;
		} // end switch
		
		out += "]";
		return out;
	}
}
