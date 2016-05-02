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

package org.primordion.xholon.service.xholonhelper;

import org.primordion.xholon.base.IXholon;

/**
 * Interface for cut, copy, paste actions.
 * This includes a declaration of signals.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 19, 2009)
 */
public interface ICutCopyPaste extends IXholon {
	
	// cut/copy/paste to and from XML Strings
	
	/**
	 * Do the actual work of copying the node to an XML String.
	 * @param node 
	 * @return 
	 */
	public String copySelf(IXholon node);
	
	/**
	 * Do the actual work of cutting the node to an XML String.
	 * @param node 
	 * @return 
	 */
	public String cutSelf(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteLastChild(IXholon node, String xmlString);
	
	/**
	 * Paste a new node or subtree from an XML String.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteFirstChild(IXholon node, String xmlString);
	
	/**
	 * Paste a new node or subtree from an XML String.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteAfter(IXholon node,String xmlString);
	
	
	/**
	 * Paste a new node or subtree from an XML String.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteBefore(IXholon node,String xmlString);
	
	/**
	 * Paste a new node or subtree from an XML String.
	 */
	public void pasteReplacement(IXholon node,String xmlString);
	
	/**
	 * Paste a new node or subtree from an XML String,
	 * by merging it with this node.
	 * Existing structure is retained.
	 * New nodes are pasted as the last child of an existing node.
	 * Attributes are replaced with attributes in the XML.
	 * <p>As an example, if the Cellontro Cell model is currently loaded,
	 * you could copy the following XML into the clipboard,
	 * and then right click on the InheritanceHierarchy --> XholonClass node
	 * and select Paste Merge.
	 * This would create a new subtree of XholonClass nodes under the existing SmallMolecule node.</p>
	 * <pre>
	&lt;SmallMolecule>
	  &lt;SmZero id='9000' xhType='XhtypePurePassiveObject' implName='org.primordion.xholon.base.Annotation'>
	    &lt;SmOne xhType='XhtypePurePassiveObject' implName='org.primordion.xholon.base.Annotation'/>
	    &lt;SmTwo xhType='XhtypePurePassiveObject' implName='org.primordion.xholon.base.Annotation'/>
	    &lt;SmThree xhType='XhtypePurePassiveObject' implName='org.primordion.xholon.base.Annotation'/>
	  &lt;/SmZero>
	&lt;/SmallMolecule>
	 * </pre>
	 * <p>As another example, merging the following XML to XholonClass will create a new port (port[1]).</p>
	 * <pre>
	&lt;xholonClassDetails>
	  &lt;World xhType="XhtypePureActiveObject">
	    &lt;port name="port" index="P_PARTNER" connector="#xpointer(ancestor::HelloWorldSystem/Hello)"/>
	    &lt;port name="port" index="1" connector="#xpointer(ancestor::HelloWorldSystem/Hello)"/>
	  &lt;/World>
	&lt;/xholonClassDetails>
	 * </pre>
	 * @param xmlString An XML String.
	 */
	public void pasteMerge(IXholon node, String xmlString);
	
	
	// cut/copy/paste to and from the Xholon clipboard
	
	/**
	 * Copy the node to the clipboard as an XML String.
	 * @param node 
	 */
	public void copyToClipboard(IXholon node);
	
	/**
	 * Cut the node to the clipboard as an XML String.
	 * @param node 
	 */
	public void cutToClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard, as the node's last child.
	 * @param node 
	 */
	public void pasteLastChildFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard, as the node's first child.
	 * @param node 
	 */
	public void pasteFirstChildFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard, as the node's next sibling
	 * @param node 
	 */
	public void pasteAfterFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard, as the node's previous sibling.
	 * @param node 
	 */
	public void pasteBeforeFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard,
	 * as a complete replacement for the node.
	 * @param node 
	 */
	public void pasteReplacementFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from an XML String in the clipboard, by merging it with the node.
	 * @param node 
	 */
	public void pasteMergeFromClipboard(IXholon node);
	
	/**
	 * Paste a new node or subtree from a dropped (drag-n-drop) XML String, as the node's last child.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteLastChildFromDrop(IXholon node, String xmlString);
	
	/**
	 * Paste a new node or subtree from a dropped (drag-n-drop) XML String, as the node's first child.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteFirstChildFromDrop(IXholon node, String xmlString);
	
	/**
	 * Paste a new node or subtree from a dropped (drag-n-drop) XML String, as the node's next sibling.
	 * @param node 
	 * @param xmlString 
	 */
	public void pasteAfterFromDrop(IXholon node, String xmlString);
	
	// clone
	
	/**
	 * Clone an existing node, and make the clone that node's last child.
	 * @param node 
	 */
	public void cloneLastChild(IXholon node);
	
	/**
	 * Clone an existing node, and make the clone that node's first child.
	 * @param node 
	 */
	public void cloneFirstChild(IXholon node);
	
	/**
	 * Clone an existing node, and position the clone after that node.
	 * @param node 
	 */
	public void cloneAfter(IXholon node);
	
	/**
	 * Clone an existing node, and position the clone before that node.
	 * @param node 
	 */
	public void cloneBefore(IXholon node);
	
}
