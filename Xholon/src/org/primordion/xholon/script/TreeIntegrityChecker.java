/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * Check the integrity of a Xholon tree or subtree. The script performs the following checks:
 * <ul>
 * <li>Siblings must all have the same parent node.</li>
 * <li>A first child and its parent must reference each other.</li>
 * <li>A script node must be a last child of its parent.</li>
 * </ul>
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app (Paste last child) simply as:
 * <pre>&lt;TreeIntegrityChecker/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 8, 2009)
 */
public class TreeIntegrityChecker extends XholonScript {
	
	private static final boolean IS_TESTING = false;
	
	/**
	 * The number of problems that were found.
	 */
	int problemCount = 0;
	
	public void postConfigure()
	{
		println("Checking the integrity of tree/subtree " + getParentNode() + "...");
		getParentNode().visit(this);
        print("Finished integrity check. ");
        if (problemCount == 0) {
        	println("No problems were found.");
        }
        else if (problemCount == 1) {
        	println("1 problem was found.");
        }
        else {
        	println("" + problemCount + " problems were found.");
        }
        removeChild();
	}
	
    public boolean visit(IXholon visitee)
    {
    	if ((visitee.hasNextSibling()) && (visitee.getNextSibling() != this)) {
        	// check that visitee and visitee's nextSibling both have the same parentNode
    		if (visitee.getParentNode() != visitee.getNextSibling().getParentNode()) {
    			problemCount++;
    			System.out.println("(" + problemCount + ") Two siblings have different parents: "
    					+ visitee + " " + visitee.getNextSibling());
    		}
    		// 
    		if ((!visitee.isRootNode()) && (visitee != visitee.getNextSibling().getPreviousSibling())) {
    			problemCount++;
    			System.out.println("(" + problemCount
    					+ ") Node not equal to getNextSibling().getPreviousSibling(): "
    					+ visitee + " " + visitee.getNextSibling());
    		}
    	}
    	
    	// if visitee is a first sibling, check that the visitee and its parent reference each other
    	if (visitee.hasParentNode()) {
    		if (visitee.equals(visitee.getParentNode().getFirstChild())) {
    			// visitee is a first sibling
    			// I'm not sure what I can check here
    		}
    	}
    	
    	// if visitee is a XholonScript, it should be the lastChild of its parent
    	//System.out.println("checking " + visitee);
    	if (ClassHelper.isAssignableFrom(XholonScript.class, visitee.getClass())) {
    		IXholon nextSibling = visitee.getNextSibling();
    		if ((nextSibling != null)
    				&& (!ClassHelper.isAssignableFrom(XholonScript.class, nextSibling.getClass()))) {
    			problemCount++;
    			System.out.println("(" + problemCount
    					+ ") Script node is not last child of its parent: "
    					+ visitee);
    		}
    	}
    	
    	// testing
    	if (IS_TESTING) {
	    	if (visitee.getXhcName().charAt(0) == 'I') {
	    		problemCount++;
				System.out.println("(" + problemCount
						+ ") It's strange that the name of a node should start with 'I': "
						+ visitee + " .");
	    	}
    	}
    	
    	return true;
    }
    
}
