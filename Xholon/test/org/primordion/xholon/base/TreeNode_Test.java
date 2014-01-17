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

import java.util.Vector;

//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

import org.primordion.xholon.common.mechanism.CeControl;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class TreeNode_Test extends GWTTestCase {

	private IXholon node[] = null;
	private static final int NUM_NODES = 20; //must be >= 10
	
	public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
		node = new TreeNode_Concrete[NUM_NODES];
		for (int i = 0; i < NUM_NODES; i++) {
			node[i] = new TreeNode_Concrete();
		}
	}

	public void gwtTearDown() throws Exception {
		node = null;
	}

	/*
	public void testSetFactory() {
		ITreeNodeFactory fact = new TreeNodeFactoryDynamic(
				TreeNode_Concrete.class,
				XholonClass.class,
				Activity.class);
		Xholon.setFactory(fact);
		assertEquals("[setFactory]", fact, Xholon.factory);
	}*/
	
	public void testGetParentNode() {
		assertNull("[getParentNode null]", node[0].getParentNode());
		makeDeepTree();
		assertEquals("[getParentNode 01]", node[0], node[1].getParentNode());
		assertEquals("[getParentNode 12]", node[1], node[2].getParentNode());
	}
	
	public void testGetFirstChild() {
		assertNull("[getFirstChild null]", node[0].getFirstChild());
		makeDeepTree();
		assertEquals("[getFirstChild 10]", node[1], node[0].getFirstChild());
		assertEquals("[getFirstChild 21]", node[2], node[1].getFirstChild());
	}
	
	public void testGetNextSibling() {
		assertNull("[getNextSibling null1]", node[0].getNextSibling());
		assertNull("[getNextSibling null2]", node[1].getNextSibling());
		makeSiblingTree();
		assertEquals("[getNextSibling 21]", node[2], node[1].getNextSibling());
		assertEquals("[getNextSibling 98]", node[9], node[8].getNextSibling());
		assertNull("[getNextSibling null3]", node[NUM_NODES-1].getNextSibling());
	}
	
	public void testGetPreviousSibling() {
		assertNull("[getPreviousSibling null1]", node[0].getPreviousSibling());
		assertNull("[getPreviousSibling null2]", node[1].getPreviousSibling());
		makeSiblingTree();
		assertEquals("[getPreviousSibling 21]", node[1], node[2].getPreviousSibling());
		assertEquals("[getPreviousSibling 98]", node[8], node[9].getPreviousSibling());
		assertNull("[getPreviousSibling null3]", node[1].getPreviousSibling());
	}
	
	public void testSetParentNode() {
		node[1].setParentNode(node[0]);
		node[2].setParentNode(node[1]);
		assertEquals("[setParentNode 01]", node[0], node[1].getParentNode());
		assertEquals("[setParentNode 12]", node[1], node[2].getParentNode());
	}
	
	public void testSetFirstChild() {
		node[0].setFirstChild(node[1]);
		node[1].setFirstChild(node[2]);
		assertEquals("[setFirstChild 10]", node[1], node[0].getFirstChild());
		assertEquals("[setFirstChild 21]", node[2], node[1].getFirstChild());
	}
	
	public void testSetNextSibling() {
		node[0].setNextSibling(node[1]);
		node[1].setNextSibling(node[2]);
		assertEquals("[setNextSibling 10]", node[1], node[0].getNextSibling());
		assertEquals("[setNextSibling 21]", node[2], node[1].getNextSibling());
	}
	
	public void testGetRootNode() {
		assertEquals("[getRootNode is this node]", node[0], node[0].getRootNode());
		makeDeepTree();
		assertEquals("[getRootNode is parent node]", node[0], node[1].getRootNode());
		assertEquals("[getRootNode is grandparent node]", node[0], node[2].getRootNode());
	}
	
	public void testSetParentChildLinks() {
		makeDeepTree();
		assertNull("[setParentChildLinks null]", node[0].getParentNode());
		for (int i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[setParentChildLinks]", node[i], node[i+1].getParentNode());
		}
	}
	
	public void testSetParentSiblingLinks() {
		makeSiblingTree();
		assertNull("[setParentChildLinks null 1]", node[1].getPreviousSibling());
		assertNull("[setParentChildLinks null 2]", node[NUM_NODES-1].getNextSibling());
		for (int i = 1; i < NUM_NODES; i++) {
			assertEquals("[setParentChildLinks parent]", node[0], node[i].getParentNode());
		}
		for (int i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[setParentChildLinks sibling]", node[i+1], node[i].getNextSibling());
		}
	}
	
	// should do some sibling tests too
	public void testRemoveChild() {
		makeDeepTree();
		for (int i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[removeChild before]", node[i], node[i+1].getParentNode());
		}
		node[5].removeChild();
		for (int i = 0; i < 4; i++) {
			assertEquals("[removeChild after]", node[i], node[i+1].getParentNode());
		}
		assertNull("[removeChild after null 4]", node[4].getFirstChild());
		assertNull("[removeChild after null 5]", node[5].getParentNode());
		assertEquals("[removeChild after 56]", node[5], node[6].getParentNode());
	}
	
	public void testInsertAfter() {
		makeSiblingTree();
		assertEquals("[insertAfter size before]", NUM_NODES-1, node[0].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode.insertAfter(node[5]);
		assertEquals("[insertAfter size after]", NUM_NODES, node[0].getNumChildren(false));
		for (int i = 1; i < 4; i++) {
			assertEquals("[insertAfter after 1]", node[i+1], node[i].getNextSibling());
		}
		assertEquals("[insertAfter after 2a]", newNode, node[5].getNextSibling());
		assertEquals("[insertAfter after 2b]", node[6], newNode.getNextSibling());
		for (int i = 6; i < NUM_NODES-1; i++) {
			assertEquals("[insertAfter after 3]", node[i+1], node[i].getNextSibling());
		}
	}
	
	public void testInsertBefore() {
		makeSiblingTree();
		assertEquals("[insertBefore size before]", NUM_NODES-1, node[0].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode.insertBefore(node[6]);
		assertEquals("[insertBefore size after]", NUM_NODES, node[0].getNumChildren(false));
		for (int i = 1; i < 4; i++) {
			assertEquals("[insertBefore after 1]", node[i+1], node[i].getNextSibling());
		}
		assertEquals("[insertBefore after 2a]", newNode, node[5].getNextSibling());
		assertEquals("[insertBefore after 2b]", node[6], newNode.getNextSibling());
		for (int i = 6; i < NUM_NODES-1; i++) {
			assertEquals("[insertBefore after 3]", node[i+1], node[i].getNextSibling());
		}
	}
	
	public void testInsertFirstChild_Deep1() {
		makeDeepTree();
		assertEquals("[insertFirstChild size before 1]", NUM_NODES-1, node[0].getNumChildren(true));
		assertEquals("[insertFirstChild size before 2]", 1, node[5].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode.insertFirstChild(node[5]);
		assertEquals("[insertFirstChild size after 1]", NUM_NODES, node[0].getNumChildren(true));
		assertEquals("[insertFirstChild size after 2]", 2, node[5].getNumChildren(false));
	}
	
	public void testInsertFirstChild_Sibling() {
		makeSiblingTree();
		assertEquals("[insertFirstChild size before sib 1]", NUM_NODES-1, node[0].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode = new TreeNode_Concrete();
		newNode.insertFirstChild(node[0]);
		assertEquals("[insertFirstChild size after sib 2]", NUM_NODES, node[0].getNumChildren(false));
	}
	
	public void testInsertFirstChild_Deep2() {
		makeDeepTree();
		assertEquals("[insertFirstChild size after sib 3]", 0, node[NUM_NODES-1].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode = new TreeNode_Concrete();
		newNode.insertFirstChild(node[NUM_NODES-1]);
		assertEquals("[insertFirstChild size after sib 4]", 1, node[NUM_NODES-1].getNumChildren(false));
	}
	
	public void testAppendChild() {
		makeDeepTree();
		assertEquals("[appendChild size before 1]", NUM_NODES-1, node[0].getNumChildren(true));
		assertEquals("[appendChild size before 2]", 1, node[5].getNumChildren(false));
		IXholon newNode = new TreeNode_Concrete();
		newNode.appendChild(node[5]);
		assertEquals("[appendChild size after 1]", NUM_NODES, node[0].getNumChildren(true));
		assertEquals("[appendChild size after 2]", 2, node[5].getNumChildren(false));
		
		makeSiblingTree();
		assertEquals("[appendChild size before sib 1]", NUM_NODES-1, node[0].getNumChildren(false));
		newNode = new TreeNode_Concrete();
		newNode.appendChild(node[0]);
		assertEquals("[appendChild size after sib 2]", NUM_NODES, node[0].getNumChildren(false));
		assertEquals("[appendChild after 1]", newNode, node[NUM_NODES-1].getNextSibling());
		assertEquals("[appendChild after 2]", node[NUM_NODES-1], newNode.getPreviousSibling());
		
		makeDeepTree();
		assertEquals("[appendChild size after sib 3]", 0, node[NUM_NODES-1].getNumChildren(false));
		newNode = new TreeNode_Concrete();
		newNode.appendChild(node[NUM_NODES-1]);
		assertEquals("[appendChild size after sib 4]", 1, node[NUM_NODES-1].getNumChildren(false));
	}
	
	public void testSwapNode() {
		makeSiblingTree();
		assertEquals("[swapNode size before]", NUM_NODES-1, node[0].getNumChildren(false));
		node[1].swapNode(node[NUM_NODES-1]);
		node[2].swapNode(node[NUM_NODES-2]);
		assertEquals("[swapNode size after]", NUM_NODES-1, node[0].getNumChildren(false));
	}
	
	public void testDepth_Deep() {
		makeDeepTree();
		assertEquals("[depth root node 1]", 0, node[0].depth());
		assertEquals("[depth deep node 1]", NUM_NODES-1, node[NUM_NODES-1].depth());
	}
	
	public void testDepth_Sibling() {
		makeSiblingTree();
		assertEquals("[depth root node 2]", 0, node[0].depth());
		for (int i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[depth sib node 2]", 1, node[i].depth());
		}
	}
	
	public void testHeight_Deep() {
		makeDeepTree();
		assertEquals("[height root node 1]", NUM_NODES-1, node[0].height());
		assertEquals("[height deep node 1]", 0, node[NUM_NODES-1].height());
	}
	
	public void testHeight_Sibling() {
		makeSiblingTree();
		assertEquals("[height root node 2]", NUM_NODES-1, node[0].height());
		for (int i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[height sib node 2]", NUM_NODES-1-i, node[i].height());
		}
	}
	
	public void testTreeSize_Deep() {
		makeDeepTree();
		assertEquals("[treeSize 1]", NUM_NODES, node[0].treeSize());
		assertEquals("[treeSize 2]", 1, node[NUM_NODES-1].treeSize());
	}
	
	public void testTreeSize_Sibling() {
		makeSiblingTree();
		assertEquals("[treeSize 3]", NUM_NODES, node[0].treeSize());
		assertEquals("[treeSize 4]", 1, node[NUM_NODES-1].treeSize());
	}
	
	// this tests a wierd configuration, that may actually be illegal ?
	// some of the firstChild-parent links are not symmetrical
	public void testTreeSize_DeepSibling() {
		makeDeepTree();
		makeSiblingTree();
		assertEquals("[treeSize]", (int)Math.pow(2, NUM_NODES-1), node[0].treeSize());
	}
	
	public void testIsRootNode() {
		IXholon node = new XholonNull();
		// a node is a root node if its parent is null
		assertTrue("[isRootNode parent is null]", node.isRootNode());
		
		IControl controlNode = new Control();
		IXholonClass controlXholonClass = new XholonClass();
		controlXholonClass.setId(CeControl.ControlCE);
		controlNode.setXhc(controlXholonClass);
		node.setParentNode(controlNode);
		// a node is a root node if its parent's XhcId is CeControl.ControlCE
		assertTrue("[isRootNode parent is CeControl.ControlCE]", node.isRootNode());
		
		IXholon pNode = new XholonNull();
		node.setParentNode(pNode);
		// otherwise a node is not a root node
		assertFalse("[isRootNode]", node.isRootNode());
		
		// a control node is never a root node
		assertFalse("[isRootNode control node]", controlNode.isRootNode());
	}
	
	public void testIsRootNode_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertTrue("[isRootNode before]", node[i].isRootNode());
		}
		makeDeepTree();
		assertTrue("[isRootNode after root]", node[0].isRootNode());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertFalse("[isRootNode after not root]", node[i].isRootNode());
		}
	}
	
	public void testIsRootNode_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertTrue("[isRootNode before]", node[i].isRootNode());
		}
		makeSiblingTree();
		assertTrue("[isRootNode after root]", node[0].isRootNode());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertFalse("[isRootNode after not root]", node[i].isRootNode());
		}
	}
	
	public void testIsExternal_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertTrue("[isExternal before]", node[i].isExternal());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertFalse("[isExternal after 1]", node[i].isExternal());
		}
		assertTrue("[isExternal after 2]", node[NUM_NODES-1].isExternal());
	}
	
	public void testIsExternal_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertTrue("[isExternal before]", node[i].isExternal());
		}
		makeSiblingTree();
		for (i = 1; i < NUM_NODES-2; i++) {
			assertFalse("[isExternal after 1]", node[i].isExternal());
		}
		assertTrue("[isExternal after 2]", node[NUM_NODES-1].isExternal());
	}
	
	public void testIsInternal_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[isInternal before]", node[i].isInternal());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertTrue("[isInternal after 1]", node[i].isInternal());
		}
		assertFalse("[isInternal after 2]", node[NUM_NODES-1].isInternal());
	}
	
	public void testIsInternal_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[isInternal before]", node[i].isInternal());
		}
		makeSiblingTree();
		for (i = 1; i < NUM_NODES-2; i++) {
			assertTrue("[isInternal after 1]", node[i].isInternal());
		}
		assertFalse("[isInternal after 2]", node[NUM_NODES-1].isInternal());
	}
	
	public void testIsLeaf_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertTrue("[isLeaf before]", node[i].isLeaf());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertFalse("[isLeaf after 1]", node[i].isLeaf());
		}
		assertTrue("[isLeaf after 2]", node[NUM_NODES-1].isLeaf());
	}
	
	public void testIsLeaf_Sibling() {
		int i;
		makeSiblingTree();
		for (i = 1; i < NUM_NODES-2; i++) {
			assertTrue("[isLeaf after 1]", node[i].isLeaf());
		}
		assertTrue("[isLeaf after 2]", node[NUM_NODES-1].isLeaf());
	}
	
	public void testGetChildNodes_Deep() {
		Vector childNodes = node[0].getChildNodes(false);
		assertEquals("[getChildNodes before 1]", 0, childNodes.size());
		childNodes = node[0].getChildNodes(true);
		assertEquals("[getChildNodes before 2]", 0, childNodes.size());
		
		makeDeepTree();
		childNodes = node[0].getChildNodes(false);
		assertEquals("[getChildNodes after 1]", 1, childNodes.size());
		childNodes = node[0].getChildNodes(true);
		assertEquals("[getChildNodes after 2]", NUM_NODES-1, childNodes.size());
	}
	
	public void testGetChildNodes_Sibling() {
		makeSiblingTree();
		Vector childNodes = node[0].getChildNodes(false);
		assertEquals("[getChildNodes after 1]", NUM_NODES-1, childNodes.size());
		childNodes = node[0].getChildNodes(true);
		assertEquals("[getChildNodes after 2]", NUM_NODES-1, childNodes.size());
	}
	
	public void testGetNthChild_Deep() {
		makeDeepTree();
		IXholon nthNode = node[0].getNthChild(0, false);
		assertEquals("[getNthChild deep 1]", node[1], nthNode);
		nthNode = node[0].getNthChild(5, true);
		assertEquals("[getNthChild deep 2]", node[6], nthNode);
		nthNode = node[0].getNthChild(1, false);
		assertNull("[getNthChild deep null]", nthNode);
	}
	
	public void testGetNthChild_Sibling() {
		makeSiblingTree();
		IXholon nthNode = node[0].getNthChild(5, false);
		assertEquals("[getNthChild sibling 1]", node[6], nthNode);
		nthNode = node[0].getNthChild(5, true);
		assertEquals("[getNthChild sibling 2]", node[6], nthNode);
	}
	
	public void testHasParentNode_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasParentNode before]", node[i].hasParentNode());
		}
		makeDeepTree();
		assertFalse("[hasParentNode after root]", node[0].hasParentNode());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertTrue("[hasParentNode after not root]", node[i].hasParentNode());
		}
	}
	
	public void testHasParentNode_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasParentNode before]", node[i].hasParentNode());
		}
		makeSiblingTree();
		assertFalse("[hasParentNode after root]", node[0].hasParentNode());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertTrue("[hasParentNode after not root]", node[i].hasParentNode());
		}
	}
	
	public void testHasChildNodes_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasChildNodes before]", node[i].hasChildNodes());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertTrue("[hasChildNodes after 1]", node[i].hasChildNodes());
		}
		assertFalse("[hasChildNodes after 2]", node[NUM_NODES-1].hasChildNodes());
	}
	
	public void testHasChildNodes_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasChildNodes before]", node[i].hasChildNodes());
		}
		makeSiblingTree();
		assertTrue("[hasChildNodes after root]", node[0].hasChildNodes());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertFalse("[hasChildNodes after not root]", node[i].hasChildNodes());
		}
	}
	
	public void testHasNextSibling_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasNextSibling before]", node[i].hasNextSibling());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertFalse("[hasNextSibling after 1]", node[i].hasNextSibling());
		}
		assertFalse("[hasNextSibling after 2]", node[NUM_NODES-1].hasNextSibling());
	}
	
	public void testHasNextSibling_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasNextSibling before]", node[i].hasNextSibling());
		}
		makeSiblingTree();
		assertFalse("[hasNextSibling after root]", node[0].hasNextSibling());
		for (i = 1; i < NUM_NODES-2; i++) {
			assertTrue("[hasNextSibling after not root]", node[i].hasNextSibling());
		}
		assertFalse("[hasNextSibling after not root last]", node[NUM_NODES-1].hasNextSibling());
	}
	
	public void testHasChildOrSiblingNodes_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasChildOrSiblingNodes before]", node[i].hasChildOrSiblingNodes());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertTrue("[hasChildOrSiblingNodes after 1]", node[i].hasChildOrSiblingNodes());
		}
		assertFalse("[hasChildOrSiblingNodes after 2]", node[NUM_NODES-1].hasChildOrSiblingNodes());
	}
	
	public void testHasChildOrSiblingNodes_Sibling() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertFalse("[hasChildOrSiblingNodes before]", node[i].hasChildOrSiblingNodes());
		}
		makeSiblingTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertTrue("[hasChildOrSiblingNodes after]", node[i].hasChildOrSiblingNodes());
		}
		assertFalse("[hasChildOrSiblingNodes after last]", node[NUM_NODES-1].hasChildOrSiblingNodes());
	}
	
	// getNumChildren() already included as part of some previous tests
	public void testGetNumChildren_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getNumChildren before 1]", 0, node[i].getNumChildren(false));
			assertEquals("[getNumChildren before 2]", 0, node[i].getNumChildren(true));
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getNumChildren after 1]", 1, node[i].getNumChildren(false));
			assertEquals("[getNumChildren after 2]", NUM_NODES-(i+1), node[i].getNumChildren(true));
		}
	}

	public void testGetNumChildren_Sibling() {
		int i;
		makeSiblingTree();
		assertEquals("[getNumChildren 1]", NUM_NODES-1, node[0].getNumChildren(false));
		assertEquals("[getNumChildren 2]", NUM_NODES-1, node[0].getNumChildren(true));
		for (i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[getNumChildren 3]", 0, node[i].getNumChildren(false));
			assertEquals("[getNumChildren 4]", 0, node[i].getNumChildren(true));
		}
	}
	
	public void testGetLastChild_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getLastChild before]", node[i].getLastChild());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-2; i++) {
			assertNotNull("[getLastChild after 1]", node[i].getLastChild());
		}
		assertNull("[getLastChild after 2]", node[NUM_NODES-1].getLastChild());
	}

	public void testGetLastChild_Sibling() {
		int i;
		makeSiblingTree();
		assertEquals("[getLastChild 1]", node[NUM_NODES-1], node[0].getLastChild());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertNull("[getLastChild 2]", node[i].getLastChild());
		}
	}

	public void testGetLastSibling_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getLastSibling before]", node[i].getLastSibling());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getLastSibling after]", node[i].getLastSibling());
		}
	}

	public void testGetLastSibling_Sibling() {
		int i;
		makeSiblingTree();
		for (i = 1; i < NUM_NODES-2; i++) {
			assertEquals("[getLastSibling 1]", node[NUM_NODES-1], node[i].getLastSibling());
		}
		assertNull("[getLastSibling 2]", node[NUM_NODES-1].getLastSibling());
	}
	
	public void testGetFirstSibling_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getFirstSibling before]", node[i].getFirstSibling());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getFirstSibling after]", node[i].getFirstSibling());
		}
	}
	
	public void testGetFirstSibling_Sibling() {
		int i;
		makeSiblingTree();
		assertNull("[getFirstSibling 1]", node[0].getFirstSibling());
		assertNull("[getFirstSibling 2]", node[1].getFirstSibling());
		for (i = 2; i < NUM_NODES-1; i++) {
			assertEquals("[getFirstSibling 2]", node[1], node[i].getFirstSibling());
		}
	}

	public void testGetSiblings_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getSiblings 1]", 0, node[i].getSiblings().size());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getSiblings 2]", 0, node[i].getSiblings().size());
		}
	}
	
	public void testGetSiblings_Sibling() {
		int i;
		makeSiblingTree();
		assertEquals("[getSiblings 1]", 0, node[0].getSiblings().size());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[getSiblings 2]", NUM_NODES-2, node[i].getSiblings().size());
		}
	}
	
	public void testGetNthSibling_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getNthSibling before]", node[i].getNthSibling(0));
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertNull("[getNthSibling after 1]", node[i].getNthSibling(0));
			assertNull("[getNthSibling after 2]", node[i].getNthSibling(1));
		}
	}
	
	public void testGetNthSibling_Sibling() {
		int i;
		makeSiblingTree();
		assertNull("[getNthSibling 1]", node[0].getNthSibling(0));
		for (i = 1; i < NUM_NODES-1; i++) {
			assertNotNull("[getNthSibling 2]", node[i].getNthSibling(0));
			assertNotNull("[getNthSibling 3]", node[i].getNthSibling(1));
		}
		assertEquals("[getNthSibling 4a]", node[1], node[5].getNthSibling(0));
		assertEquals("[getNthSibling 4b]", node[2], node[5].getNthSibling(1));
		assertEquals("[getNthSibling 4c]", node[3], node[5].getNthSibling(2));
		assertEquals("[getNthSibling 4d]", node[4], node[5].getNthSibling(3));
		assertEquals("[getNthSibling 4e]", node[6], node[5].getNthSibling(4));
		assertEquals("[getNthSibling 4f]", node[NUM_NODES-1], node[5].getNthSibling(NUM_NODES-3));
	}
	
	public void testGetNumSiblings_Deep() {
		int i;
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getNumSiblings 1]", 0, node[i].getNumSiblings());
		}
		makeDeepTree();
		for (i = 0; i < NUM_NODES-1; i++) {
			assertEquals("[getNumSiblings 2]", 0, node[i].getNumSiblings());
		}
	}
	
	public void testGetNumSiblings_Sibling() {
		int i;
		makeSiblingTree();
		assertEquals("[getNumSiblings 1]", 0, node[0].getNumSiblings());
		for (i = 1; i < NUM_NODES-1; i++) {
			assertEquals("[getNumSiblings 2]", NUM_NODES-2, node[i].getNumSiblings());
		}
	}
	
	public void testGetNeighbors_Deep() {
		int i;
		Vector v;
		for (i = 0; i < NUM_NODES-1; i++) {
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_xSC, null);
			assertEquals("[getNeighbors before 1]", 0, v.size());
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
			assertEquals("[getNeighbors before 2]", 0, v.size());
		}
		makeDeepTree();
		v = node[0].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors after 1]", 1, v.size());
		v = node[0].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors after 2]", 1, v.size());
		for (i = 1; i < NUM_NODES-2; i++) {
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
			assertEquals("[getNeighbors after 3]", 1, v.size());
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
			assertEquals("[getNeighbors after 4]", 2, v.size());
		}
		v = node[NUM_NODES-1].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors after 5]", 0, v.size());
		v = node[NUM_NODES-1].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors after 6]", 1, v.size());
		
		// node 5 - all possible values for "include"
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xxx, null);
		assertEquals("[getNeighbors xxx]", 0, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors xxC]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xSx, null);
		assertEquals("[getNeighbors xSx]", 0, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xSC, null);
		assertEquals("[getNeighbors xSC]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_Pxx, null);
		assertEquals("[getNeighbors Pxx]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PxC, null);
		assertEquals("[getNeighbors PxC]", 2, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PSx, null);
		assertEquals("[getNeighbors PSx]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC]", 2, v.size());
		
		// node 5 - some other possible values for "distance"
		v = node[5].getNeighbors(2, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 4, v.size());
		v = node[5].getNeighbors(3, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 6, v.size());
		v = node[5].getNeighbors(4, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 8, v.size());
		v = node[5].getNeighbors(5, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 10, v.size());
		v = node[5].getNeighbors(6, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 11, v.size());
		v = node[5].getNeighbors(7, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 12, v.size());
	}
	
	public void testGetNeighbors_Sibling() {
		int i;
		Vector v;
		makeSiblingTree();
		v = node[0].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors after 1]", NUM_NODES-1, v.size());
		v = node[0].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors after 2]", NUM_NODES-1, v.size());
		for (i = 1; i < NUM_NODES-2; i++) {
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
			assertEquals("[getNeighbors after 3]", 0, v.size());
			v = node[i].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
			assertEquals("[getNeighbors after 4]", NUM_NODES-1, v.size());
		}
		v = node[NUM_NODES-1].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors after 5]", 0, v.size());
		v = node[NUM_NODES-1].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors after 6]", NUM_NODES-1, v.size());
		
		// node 5 - all possible values for "include"
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xxx, null);
		assertEquals("[getNeighbors xxx]", 0, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xxC, null);
		assertEquals("[getNeighbors xxC]", 0, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xSx, null);
		assertEquals("[getNeighbors xSx]", NUM_NODES-2, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_xSC, null);
		assertEquals("[getNeighbors xSC]", NUM_NODES-2, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_Pxx, null);
		assertEquals("[getNeighbors Pxx]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PxC, null);
		assertEquals("[getNeighbors PxC]", 1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PSx, null);
		assertEquals("[getNeighbors PSx]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC]", NUM_NODES-1, v.size());
		
		// node 5 - some other possible values for "distance"
		v = node[5].getNeighbors(2, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 1]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(3, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 2]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(4, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 3]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(5, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 4]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(6, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 5]", NUM_NODES-1, v.size());
		v = node[5].getNeighbors(7, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 6]", NUM_NODES-1, v.size());
	}
	
	//// bug
	/*public void testGetNeighbors_Distance0() {
		Vector v;
		makeDeepTree();
		v = node[5].getNeighbors(0, IXholon.NINCLUDE_PSC, null);
		assertEquals("[getNeighbors PSC 0]", 0, v.size()); // bug - should be 0 rather than 2
	}*/
	
	public void testGetNeighbors_Exclude() {
		Vector v;
		makeDeepTree();
		v = node[5].getNeighbors(1, IXholon.NINCLUDE_PSC, "Dummy");
		assertEquals("[getNeighbors PSC 0]", 2, v.size());
	}
	
	/**
	 * Make a deep tree.
	 */
	protected void makeDeepTree() {
		for (int i = 0; i < NUM_NODES-1; i++) {
			node[i+1].setParentChildLinks(node[i]);
		}
	}
	
	/**
	 * Make a shallow sibling-only tree.
	 */
	protected void makeSiblingTree() {
		node[1].setParentChildLinks(node[0]);
		for (int i = 2; i < NUM_NODES; i++) {
			node[i].setParentSiblingLinks(node[i-1]);
		}
	}

}
