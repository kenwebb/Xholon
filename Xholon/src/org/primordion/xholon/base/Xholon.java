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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.json.client.JSONObject;
//import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TextAreaElement;

//import java.io.IOException;
//import java.io.PrintWriter;
import java.io.Serializable;
//import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.client.HtmlElementCache;
import org.primordion.xholon.app.AbstractApplication;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.common.mechanism.CeAction;
import org.primordion.xholon.common.mechanism.CeAnnotation;
import org.primordion.xholon.common.mechanism.CeControl;
import org.primordion.xholon.common.mechanism.CeRole;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
import org.primordion.xholon.service.creation.ITreeNodeFactory;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc; // only imports java standard classes, and Xholon

// logging
//import org.slf4j.Logger; // use either slf4j or apache.commons.logging
//import org.slf4j.LoggerFactory;
//import org.apache.commons.logging.Log; // Xholon default; use either slf4j or apache.commons.logging
//import org.apache.commons.logging.LogFactory; // Xholon default
//import org.primordion.xholon.gwt.Log; // old GWT

// null logging
import org.primordion.xholon.logging.Log; // for use with GWT 2.5.1
import org.primordion.xholon.logging.LogFactory; // for use with GWT 2.5.1

// GWT 2.5.1 logging
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Xholon is an abstract class that implements a lot of the functionality defined in the IXholon interface.
 * @see IXholon
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */

public abstract class Xholon implements IXholon, IDecoration, Comparable, Serializable {
	private static final long serialVersionUID = 1271821389789389499L;

	/** Parent node, or null if root. */
	protected transient IXholon parentNode = null;
	
	/** First (leftmost) child, or null. */
	protected IXholon firstChild = null;
	
	/** Next (right) sibling, or null. */
	protected IXholon nextSibling = null;
	
	/** Unique ID of this Xholon instance. */
	protected transient int id = XHOLON_ID_DEFAULT;
	
	/** Xholon Class associated with this Xholon. */
	protected IXholonClass xhc = null;
	
	/** IXholon logger. */
	//protected static final Logger logger = LoggerFactory.getLogger("Xholon"); // slf4j
	protected static final Log logger = LogFactory.getLog("Xholon"); // apache.commons.logging, Xholon null log
	//protected static final Log logger = new Log(); // gwt
	//protected static final Logger logger = Logger.getLogger("Xholon"); // GWT 2.5.1
	
	/**
	 * print2Console() will ignore any string that starts with this character.
	 * ASCII \cQ or \cq U+0011 DEVICE CONTROL ONE
	 */
	protected static final String PRINT2CONSOLE_IGNORE_ME = "\u0011";
	
	/** Constructor. */
	public Xholon()	{}
	
	// *******************************************************************************************
	// TreeNode - the following are methods that were implemented in the now deprecated TreeNode interface
	/*
	 * @see org.primordion.xholon.base.IXholon#remove()
	 */
	public void remove()
	{
		if (hasChildNodes()) {
			firstChild.remove();
		}
		if (hasNextSibling()) {
			nextSibling.remove();
		}
		getFactory().returnTreeNode(this);
	}
	
	/**
	 * Get the factory that creates IXholon instances.
	 * @return The factory.
	 */
	public ITreeNodeFactory getFactory() {
		IApplication app = getApp();
		return (app == null) ? null : app.getFactory();
	}
	
	/**
	 * Get the name of the Java class that implements the IQueue interface.
	 * @return The class name.
	 */
	public String getIQueueImplName() {
		IApplication app = getApp();
		return (app == null) ? null : app.getIQueueImplName();
	}
	
	/**
	 * Get the message Q.
	 * @return An instance of IQueue.
	 */
	public IQueue getMsgQ() {
		IApplication app = getApp();
		return (app == null) ? null : app.getMsgQ();
	}
	
	/**
	 * Get the system message Q.
	 * @return An instance of IQueue.
	 */
	public IQueue getSystemMsgQ() {
		IApplication app = getApp();
		return (app == null) ? null : app.getSystemMsgQ();
	}
	
	/**
	 * Get whether or not capture of interactions is enabled. 
	 * @return true or false
	 */
	public boolean getInteractionsEnabled() {
		if (getApp() == null) {
			return false;
		}
		else {
			return getApp().getUseInteractions();
		}
	}
	
	/**
	 * Get the object that handles the capture and subsequent display of interactions.
	 * @return An instance of IInteraction.
	 */
	public IInteraction getInteraction() {
		IApplication app = getApp();
		return (app == null) ? null : app.getInteraction();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getApp()
	 */
	public IApplication getApp() {
		if (xhc != null) {
			// this is a shortcut to the Application
			return xhc.getApp();
		}
		else if (getParentNode() != null) {
			return getParentNode().getApp();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setApp(org.primordion.xholon.app.IApplication)
	 */
	public void setApp(IApplication app)
	{
		logger.warn("To implement Xholon setApp(), override it in a subclass.");
	}
	
	/**
	 * Get the singleton instance of XPath.
	 * @return The singleton instance of XPath.
	 */
	public final IXPath getXPath()
	{
		IXPath xp = (IXPath)getService(AbstractXholonService.XHSRV_XPATH);
		if (xp == null) {
			// use the static factory if the XPathService is not yet available
			String unavailStr = "XPathService is unavailable. Will try to use XPathFactory: ";
			xp = XPathFactory.getInstance();
			if (xp == null) {
				logger.debug(unavailStr + "failed");
			}
			else {
				logger.debug(unavailStr + "succeeded");
			}
		}
		return xp;
	}
	
	/**
	 * Get the singleton instance of org.slf4j.Logger.
	 * Note: the two versions of getLogger() cannot both be active at the same time.
	 * @return An instance of org.slf4j.Logger
	 */
	//public static final Logger getLogger() {return logger;}
	/**
	 * Get the singleton instance of org.apache.commons.logging.Log.
	 * Note: the two versions of getLogger() cannot both be active at the same time.
	 * @return An instance of org.apache.commons.logging.Log
	 */
	public static final Log getLogger() {return logger;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getParentNode()
	 */
	public IXholon getParentNode()       { return parentNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getFirstChild()
	 */
	public IXholon getFirstChild()    { return firstChild; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNextSibling()
	 */
	public IXholon getNextSibling() { return nextSibling; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPreviousSibling()
	 */
	public IXholon getPreviousSibling() {
		if (isRootNode()) {
			return null; // this is the root node
		}
		IXholon node = getParentNode().getFirstChild();
		if (node == this) {
			return null; // this node is already the first sibling
		}
		IXholon leftNode = node;
		while (leftNode.getNextSibling() != null) {
			node = leftNode.getNextSibling();
			if (node == this) {
				return leftNode;
			}
			leftNode = node; // should never get here
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setParentNode(org.primordion.xholon.base.TreeNode)
	 */
	public void setParentNode( IXholon treeNode )       { parentNode = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setFirstChild(org.primordion.xholon.base.TreeNode)
	 */
	public void setFirstChild( IXholon treeNode )    { firstChild = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setNextSibling(org.primordion.xholon.base.TreeNode)
	 */
	public void setNextSibling( IXholon treeNode ) { nextSibling = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRootNode()
	 */
	public IXholon getRootNode() {
		IXholon node = this;
		while (!node.isRootNode()) {
			node = node.getParentNode();
		}
		return node;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#findFirstChildWithXhClass(int)
	 * This method has been duplicated in IFindChildSibWith.
	 */
	public IXholon findFirstChildWithXhClass(int childXhClassId)
	{
		IXholon node = getFirstChild();
		while (node != null) {
			int foundXhcId = node.getXhcId();
			if (foundXhcId == childXhClassId) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#findFirstChildWithXhClass(java.lang.String)
	 */
	public IXholon findFirstChildWithXhClass(String childXhClassName)
	{
		if (childXhClassName == null) {return null;}
		IXholon node = getFirstChild();
		while (node != null) {
			String foundXhcName = node.getXhcName();
			if (childXhClassName.equals(foundXhcName)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setParentChildLinks()
	 */
	public void setParentChildLinks( IXholon parent )
	{
		setParentNode( parent );
		parent.setFirstChild( this );
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setParentSiblingLinks(org.primordion.xholon.base.TreeNode)
	 */
	public void setParentSiblingLinks( IXholon previousSibling )
	{
		setParentNode( previousSibling.getParentNode() ); // previousSibling already has parent
		previousSibling.setNextSibling( this );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#removeChild()
	 */
	public void removeChild()
	{
		if (!isRootNode()) {
			IXholon lNode = getPreviousSibling();
			IXholon rNode = getNextSibling();
			if (lNode == null) { // this is the first (leftmost) sibling
				if (rNode == null) {
					getParentNode().setFirstChild(null);
				}
				else {
					getParentNode().setFirstChild(rNode); // nextSibling is new firstChild of parent
				}
			}
			else {
				if (rNode == null) {
					lNode.setNextSibling(null);
				}
				else {
					lNode.setNextSibling(rNode);
				}
			}
			setParentNode(null);
			setNextSibling(null);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertAfter(org.primordion.xholon.base.IXholon)
	 */
	public void insertAfter(IXholon newLeftSibling)
	{
		if (newLeftSibling.hasNextSibling()) {
			parentNode = newLeftSibling.getParentNode();
			nextSibling = newLeftSibling.getNextSibling();
			newLeftSibling.setNextSibling(this);
		}
		else { // last sibling
			setParentSiblingLinks(newLeftSibling);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertAfter(java.lang.String, java.lang.String)
	 */
	public IXholon insertAfter(String xhClassName, String roleName)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode();
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(xhClassName));
		newNode.setRoleName(roleName);
		newNode.insertAfter(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertAfter(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IXholon insertAfter(String xhClassName, String roleName, String implName)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode(implName);
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(xhClassName));
		newNode.setRoleName(roleName);
		newNode.insertAfter(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertBefore(org.primordion.xholon.base.IXholon)
	 */
	public void insertBefore(IXholon newNextSibling)
	{
		parentNode = newNextSibling.getParentNode();
		if (parentNode.getFirstChild() == newNextSibling) {
			parentNode.setFirstChild(this);
		}
		else {
			IXholon lSibling = newNextSibling.getPreviousSibling();
			lSibling.setNextSibling(this);
		}
		setNextSibling(newNextSibling);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertBefore(java.lang.String, java.lang.String)
	 */
	public IXholon insertBefore(String xhClassName, String roleName)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode();
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(xhClassName));
		newNode.setRoleName(roleName);
		newNode.insertBefore(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertBefore(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IXholon insertBefore(String xhClassName, String roleName, String implName)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode(implName);
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(xhClassName));
		newNode.setRoleName(roleName);
		newNode.insertBefore(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertFirstChild(org.primordion.xholon.base.IXholon)
	 */
	public void insertFirstChild(IXholon newParentNode)
	{
		if (newParentNode.getFirstChild() == null) {
			setParentChildLinks(newParentNode);
		}
		else {
			IXholon newSibling = newParentNode.getFirstChild();
			insertBefore(newSibling);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#appendChild(org.primordion.xholon.base.IXholon)
	 */
	public void appendChild(IXholon newParentNode)
	{
		if (newParentNode.getFirstChild() == null) {
			setParentChildLinks(newParentNode);
		}
		else {
			setParentSiblingLinks(newParentNode.getLastChild());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#appendChild(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.String)
	 */
	public IXholon appendChild(String xhClassName, String roleName)
	{
		IXholon newNode = null;
		IXholonClass xhcNode = getClassNode(xhClassName);
		// get the name of the Java implementation class, if known
		String implName = null;
		if (xhcNode != null) {
			implName = xhcNode.getImplName();
		}
		try {
			if (implName != null) {
				newNode = getFactory().getXholonNode(implName);
			}
			else {
				newNode = getFactory().getXholonNode();
			}
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(xhcNode);
		if (roleName != null) {
			newNode.setRoleName(roleName);
		}
		newNode.appendChild(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#appendChild(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IXholon appendChild(String xhClassName, String roleName, String implName)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode(implName);
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return newNode; // TODO not sure what should be returned in this case
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(xhClassName));
		if (roleName != null) {
			newNode.setRoleName(roleName);
		}
		newNode.appendChild(this);
		return newNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#appendsOwnChildren()
	 */
	public boolean appendsOwnChildren() {
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#replaceNode(org.primordion.xholon.base.IXholon)
	 */
	public void replaceNode(IXholon replacementNode)
	{
		if (!isRootNode()) {
			// insert the replacement node into the tree as a sibling of the current tree
			replacementNode.insertAfter(this);
		}
		// move first child of the current node, to become child of the replacement node
		// it will automatically bring any of its siblings along with it
		IXholon aChild = getFirstChild();
		if (aChild != null) {
			aChild.appendChild(replacementNode);
			aChild = aChild.getNextSibling();
		}
		// correct the parent node of each child
		while (aChild != null) {
			aChild.setParentNode(replacementNode);
			aChild = aChild.getNextSibling();
		}
		// remove the current node from the tree and from the model
		removeChild();
		remove();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#swapNode(org.primordion.xholon.base.IXholon)
	 */
	public void swapNode(IXholon otherNode)
	{
		IXholon thisParent = parentNode;
		IXholon otherParent = otherNode.getParentNode();
		IXholon thisNextSibling = nextSibling;
		IXholon otherNextSibling = otherNode.getNextSibling();
		if (thisParent != otherParent) { // no change if both have same parent
			removeChild();
			otherNode.removeChild();
			// insert this node
			if (otherNextSibling == null) {
				appendChild(otherParent);
			}
			else {
				insertBefore(otherNextSibling);
			}
			// insert other node
			if (thisNextSibling == null) {
				otherNode.appendChild(thisParent);
			}
			else {
				otherNode.insertBefore(thisNextSibling);
			}
		}
	}
	
	//											standard tree utility functions
	
	/*
	 * @see org.primordion.xholon.base.IXholon#depth()
	 */
	public int depth()
	{
		if (isRootNode()) {
			return 0;
		}
		else {
			return 1 + getParentNode().depth();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#height()
	 */
	public int height()
	{
		int hL = 0;
		int hR = 0;
		if (isExternal()) {
			return 0;
		}
		else {
			if (hasChildNodes()) {
				hL = firstChild.height();
			}
			if (hasNextSibling()) {
				hR = nextSibling.height();
			}
			return hL > hR ? hL + 1 : hR + 1;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNumLevels()
	 */
	public int getNumLevels()
	{
		if (hasChildNodes()) {
			return ((Xholon)firstChild).getNumLevelsR() + 1;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Recursively search all subtrees for the maximum number of levels.
	 * @return The maximum number of levels.
	 */
	protected int getNumLevelsR()
	{
		int hL = 0;
		int hR = 0;
		if (isExternal()) {
			return 0;
		}
		else {
			if (hasChildNodes()) {
				hL = ((Xholon)firstChild).getNumLevelsR() + 1;
			}
			if (hasNextSibling()) {
				hR = ((Xholon)nextSibling).getNumLevelsR();
			}
			return hL > hR ? hL : hR;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#treeSize()
	 */
	public int treeSize()
	{
		int lSize = 0;
		int rSize = 0;
		IXholon node = getFirstChild();
		if ( node != null ) {
			lSize = node.treeSize();
		}
		node = getNextSibling();
		if ( node != null ) {
			rSize = node.treeSize();
		}
		return ( 1 + lSize + rSize );
	}
	
	/**
	 * This node is the root node of the tree or subtree, if either:
	 * <p>(1) its parent is null, or</p>
	 * <p>(2) its parent's XhcId == CeControl.ControlCE .</p>
	 * TODO but children of View node should not be root nodes
	 * @see org.primordion.xholon.base.IXholon#isRootNode()
	 */
	public boolean isRootNode()
	{
		if (parentNode == null) {
			return true;
		}
		else {
			return parentNode.getXhcId() == CeControl.ControlCE;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isExternal()
	 */
	public boolean isExternal()
	{
		return ( (firstChild == null) && (nextSibling == null) );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isInternal()
	 */
	public boolean isInternal()
	{
		return ( (firstChild != null) || (nextSibling != null) );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isLeaf()
	 */
	public boolean isLeaf()
	{
		return (firstChild == null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isAttributeHandler()
	 */
	public boolean isAttributeHandler()
	{
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getChildNodes(boolean)
	 */
	public Vector getChildNodes(boolean deep)
	{
		Vector v = new Vector();
		return getChildNodes(v, deep);
	}
	
	/**
	 * Helper function to recursively get all children.
	 * @param v Vector that gets filled with children.
	 * @param deep If true then return entire nested subtree, if false return only immediate children.
	 * @return Vector containing children.
	 */
	protected Vector getChildNodes( Vector v, boolean deep) {
		IXholon nextChild = getFirstChild();
		while (nextChild != null) {
			v.addElement(nextChild);
			if (deep) {
				((Xholon)nextChild).getChildNodes(v, deep);
			}
			nextChild = nextChild.getNextSibling();
		}
		return v;
	}
	
	@Override
	public String getChildrenAsCsv(String nameTemplate, String separator) {
		if (nameTemplate == null) {nameTemplate = GETNAME_DEFAULT;}
		if (separator == null) {separator = ",";}
		StringBuilder sb = new StringBuilder();
		IXholon nextChild = getFirstChild();
		while (nextChild != null) {
			sb.append(nextChild.getName(nameTemplate));
			nextChild = nextChild.getNextSibling();
			if (nextChild != null) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNthChild(int, boolean)
	 */
	public IXholon getNthChild(int n, boolean deep)
	{
		Vector v = getChildNodes(deep);
		if (v.size() > n) {
			return (IXholon)v.elementAt(n);
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasParentNode()
	 */
	public boolean hasParentNode()
	{
		if (parentNode == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasChildNodes()
	 */
	public boolean hasChildNodes()
	{
		return (firstChild != null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasNextSibling()
	 */
	public boolean hasNextSibling()
	{
		return (nextSibling != null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasChildOrSiblingNodes()
	 */
	public boolean hasChildOrSiblingNodes()
	{
		if (hasChildNodes()) {return true;}
		else {return hasNextSibling();}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasSiblingNodes()
	 */
	public boolean hasSiblingNodes()
	{
		if (hasNextSibling()) {
			return true;
		}
		if (getFirstSibling() != null) {
			return true;
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNumChildren(boolean)
	 */
	public int getNumChildren(boolean doFullSubtree)
	{
		// not very efficient
		return getChildNodes(doFullSubtree).size();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getLastChild()
	 */
	public IXholon getLastChild()
	{
		if (hasChildNodes()) {
			if (firstChild.hasNextSibling()) {
				return firstChild.getLastSibling();
			}
			else {
				return firstChild;
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getLastSibling()
	 */
	public IXholon getLastSibling()
	{
		IXholon node = this;
		while (node.hasNextSibling()) {
			node = node.getNextSibling();
		}
		if (node == this) {
			return null;
		}
		else {
			return node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getFirstSibling()
	 */
	public IXholon getFirstSibling()
	{
		IXholon node = null;
		if (!isRootNode()) {
			node = parentNode.getFirstChild();
			if (node == this) {
				return null;
			}
			else {
				return node;
			}
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getSelfAndSiblings(boolean)
	 */
	public Vector getSelfAndSiblings(boolean includeSameClassOnly)
	{
		if (!isRootNode()) {
			Vector v = parentNode.getChildNodes(false);
			if (includeSameClassOnly) {
				Vector scV = new Vector();
				for (int i = 0; i < v.size(); i++) {
					IXholon node = (IXholon)v.get(i);
					if (node.getXhcId() == this.getXhcId()) {
						scV.add(node);
					}
				}
				return scV;
			}
			else {
				return v;
			}
		}
		else {
			return new Vector();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getSiblings()
	 */
	public Vector getSiblings()
	{
		if (!isRootNode()) {
			Vector v = parentNode.getChildNodes(false);
			v.removeElement(this);
			return v;
		}
		else {
			return new Vector();
		}
	}
	
	@Override
	public String getSiblingsAsCsv(String nameTemplate, String separator) {
		StringBuilder sb = new StringBuilder();
		if (!isRootNode()) {
			if (nameTemplate == null) {nameTemplate = GETNAME_DEFAULT;}
			if (separator == null) {separator = ",";}
			IXholon nextSibling = getParentNode().getFirstChild(); //getFirstSibling();
			while (nextSibling != null) {
				if (this == nextSibling) {
					// do not include this node
					nextSibling = nextSibling.getNextSibling();
				}
				else {
					sb.append(nextSibling.getName(nameTemplate));
					nextSibling = nextSibling.getNextSibling();
					if (nextSibling != null) {
						sb.append(separator);
					}
				}
			}
		}
		return sb.toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNthSibling(int)
	 */
	public IXholon getNthSibling(int n)
	{
		Vector v = getSiblings();
		if (v.size() > n) {
			return (IXholon)v.elementAt(n);
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNumSiblings()
	 */
	public int getNumSiblings()
	{
		// not very efficient
		return getSiblings().size();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getSelfAndSiblingsIndex(boolean)
	 */
	public int getSelfAndSiblingsIndex(boolean includeSameClassOnly)
	{
		return getSelfAndSiblings(includeSameClassOnly).indexOf(this);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isUniqueSibling()
	 */
	public boolean isUniqueSibling()
	{
		if (!hasSiblingNodes()) {return true;}
		List siblings = getSiblings();
		for (int i = 0; i < siblings.size(); i++) {
			if (this.getXhcId() == ((IXholon)siblings.get(i)).getXhcId()) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isUniqueSiblingRoleName()
	 */
	public boolean isUniqueSiblingRoleName()
	{
		String roleName = this.getRoleName();
		if (roleName == null) {roleName = "";}
		if (!hasSiblingNodes()) {return true;}
		List siblings = getSiblings();
		for (int i = 0; i < siblings.size(); i++) {
			IXholon sibling = (IXholon)siblings.get(i);
			if (this.getXhcId() == sibling.getXhcId()) {
				String siblingRoleName = sibling.getRoleName();
				if (siblingRoleName == null) {siblingRoleName = "";}
				if (roleName.equals(siblingRoleName)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public native Object getNeighbors() /*-{
		var links = this.links(false, true);
		var neighbors = [];
		links.forEach(function(link) {
		  neighbors.push(link.reffedNode);
		});
		return neighbors;
	}-*/;
	
	@Override
	public native Object getNeighbors(boolean placeGraph, boolean linkGraph) /*-{
		var links = this.links(placeGraph, linkGraph);
		var neighbors = [];
		links.forEach(function(link) {
		  neighbors.push(link.reffedNode);
		});
		return neighbors;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNeighbors(int, int, java.lang.String)
	 */
	public Vector getNeighbors(int distance, int include, String excludeBecName)
	{
		Vector v = new Vector();
		return getNeighbors(v, distance, include, excludeBecName);
	}

	/**
	 * Get neighbor nodes. These include optionally parent, siblings and children.
	 * @param v Vector containing the returned nodes
	 * @param distance How far within the tree to search for neighbors.
	 * @param include whether to include (P)arent and/or (S)iblings and/or (C)hildren
	 * @param excludeBecName name of a XholonClass, used to limit the scope of the returned Vector.
	 *        TODO this uses an IXholon method
	 * @return Vector containing all neighbors (instances of TreeNode).
	 */
	protected Vector getNeighbors(Vector v, int distance, int include, String excludeBecName)
	{
		int i;
		IXholon node;
		// PSC Parent Sibling Children
		if ((include & NINCLUDE_Pxx) == NINCLUDE_Pxx) { // parent
			IXholon myParent = getParentNode();
			if (myParent != null) {
				if ((excludeBecName == null) || !(myParent.getXhcName().equals(excludeBecName))) {
					v.addElement(myParent);
					if (distance > 1) { // recurse
						((Xholon)myParent).getNeighbors(v, distance - 1, NINCLUDE_PSx, excludeBecName);
					}
				}
			}
		}
		if ((include & NINCLUDE_xSx) == NINCLUDE_xSx) { // siblings
			Vector mySibV = getSiblings();
			for (i = 0; i < mySibV.size(); i++) {
				node = (Xholon)mySibV.elementAt(i);
				v.addElement(node);
				if (distance > 1) { // recurse
					((Xholon)node).getNeighbors(v, distance - 1, NINCLUDE_xxC, excludeBecName);
				}
			}
		}
		if ((include & NINCLUDE_xxC) == NINCLUDE_xxC) { // children
			Vector myChildV = getChildNodes(false);
			for (i = 0; i < myChildV.size(); i++) {
				node = (Xholon)myChildV.elementAt(i);
				v.addElement(node);
				if (distance > 1) { // recurse
					((Xholon)node).getNeighbors(v, distance - 1, NINCLUDE_xxC, excludeBecName);
				}
			}
		}
		return v;
	}
		

	/*
	 * @see org.primordion.xholon.base.IXholon#initStatics()
	 */
	public void initStatics() {}

	/*
	 * @see org.primordion.xholon.base.IXholon#preConfigure()
	 */
	public void preConfigure()
	{ println( "TreeNode: preConfigure()\n" ); }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure(java.lang.String, int)
	 */
	public int configure(String instructions, int instructIx)
	{
		println( "Xholon: configure(String instructions) " + instructions + " " + instructIx + "\n" );
		return instructIx;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#print(java.lang.Object)
	 */
	public void print(Object obj) {
		if (Msg.appM) { // && obj instanceof String) {
		  if (obj == null) {
		    print2Console("null", false);
		  }
		  else {
		    print2Console(obj.toString(), true);
		  }
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#println(java.lang.Object)
	 */
	public void println(Object obj) {
		if (Msg.appM) { // && obj instanceof String) {
		  if (obj == null) {
		    print2Console("null\n", true);
		  }
		  else {
		    print2Console(obj.toString() + "\n", true);
		  }
		}
	}
	
	/**
	 * Print to a console.
	 * This can be very slow, so limit the number of calls,
	 * for example by consolidating text using a StringBuilder before calling.
	 * @param str A String to append to the end of the console.
	 * @param scroll Whether or not to scroll to the bottom of the text area.
	 */
	protected void print2Console(String str, boolean scroll) {
	  if (str.startsWith(PRINT2CONSOLE_IGNORE_ME)) {return;}
	  Element element = HtmlElementCache.xhout;
	  if (element != null) {
      element = element.getFirstChildElement();
    }
	  if (element != null) {
      TextAreaElement textfield = element.cast();
      textfield.setValue(textfield.getValue() + str);
      // optionally scroll to the bottom of the text area
      if (scroll) {
        textfield.setScrollTop(textfield.getScrollHeight());
      }
    }
	}
	
	
	// *******************************************************************************************
	// Xholon
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		// initialize all instance variables to initial safe values
		parentNode  = null;
		firstChild  = null;
		nextSibling = null;
		id = 0;
		xhc = null;
	}
	
	/**
	 * Get the next available id for assignment to an instance of Xholon.
	 * @return A unique ID.
	 */
	protected int getNextId()
	{
		return getApp().getNextId();
	}
	
	/**
	 * Set the next available id back to 0.
	 * This should only be called when loading a new model in, to replace an existing one.
	 */
	protected void resetNextId()
	{
		getApp().resetNextId();
	}
		
	/*
	 * @see org.primordion.xholon.base.IXholon#getId()
	 */
	public int getId()
	{ return id; }

	/*
	 * @see org.primordion.xholon.base.IXholon#getIdentity()
	 */
	public IXholon getIdentity()
	{ return this; }

	/*
	 * @see org.primordion.xholon.base.IXholon#getName()
	 */
	public String getName()
	{
		String rn = getRoleName();
		if (rn == null) {
			rn = "";
		}
		else {
			rn += ":";
		}
		if (xhc == null) {
			return rn + this.getClass().getName() + "_" + id;
		}
		else {
			String cName = xhc.getName();
			if (cName == null) {
				return rn + "_" + id;
			}
			return rn + cName.substring(0,1).toLowerCase() + cName.substring(1) + "_" + id;
			// Convention: first letter of enities are lower case, classes upper case
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getName(java.lang.String)
	 */
	public String getName(String template)
	{
		if (template.equals(GETNAME_DEFAULT)) {return getName();}
		if (template.length() != GETNAME_SIZE_TEMPLATE) {return getName();}
		String nameBuffer = "";
		String rn = getRoleName();
		for (int pos = 0; pos < GETNAME_SIZE_TEMPLATE; pos++) {
			char ch = template.charAt(pos);
			switch (pos) {
			case 0: // roleName
				switch (ch) {
				case 'r': if (rn != null) {nameBuffer += getRoleName();} break;
				case 'R': // roleName or className, but not both
					if ((rn == null) || (rn.length() == 0)) {
						if (xhc == null) {
							nameBuffer += this.getClass().getName() + "_";
						}
						else {
							nameBuffer += xhc.getName();
						}
					}
					else {
						nameBuffer += getRoleName();
					}
					break;
				case 'S': // toString()
					nameBuffer += toString();
					break;
				case '^': break;
				default: rn = "" + ch; nameBuffer += ch; break;
				}
				break;
			case 1: // ":"
				switch (ch) {
				case ':': if (rn != null) {nameBuffer += ch;} break;
				case '^': break;
				default: if (rn != null) {nameBuffer += ch;} break;
				}
				break;
			case 2: // Xholon className
				switch (ch) {
				case 'c': // first letter of className should be lowercase
					if (xhc == null) {
						nameBuffer += this.getClass().getName() + "_"; //"unknownClassName_";
					}
					else {
						String cName = xhc.getName();
						nameBuffer += cName.substring(0,1).toLowerCase() + cName.substring(1);
					}
					break;
				case 'C':
					if (xhc == null) {
						nameBuffer += this.getClass().getName() + "_"; //"UnknownClassName_";
					}
					else {
						nameBuffer += xhc.getName();
					}
					break;
				case 'l': // local part  - first letter of className should be lowercase
					if (xhc == null) {
						nameBuffer += this.getClass().getName() + "_"; //"unknownClassName_";
					}
					else {
						String cName = xhc.getLocalPart();
						nameBuffer += cName.substring(0,1).toLowerCase() + cName.substring(1);
					}
					break;
				case 'L': // local part
					if (xhc == null) {
						nameBuffer += this.getClass().getName() + "_"; //"UnknownClassName_";
					}
					else {
						nameBuffer += xhc.getLocalPart();
					}
					break;
				case 'D':
				case 'd':
					if (xhc == null) {
						nameBuffer += this.getClass().getName() + "_"; //"UnknownClassName_";
					}
					else {
						nameBuffer += xhc.getName(String.valueOf(ch));
					}
					break;
				case '^': break;
				default: nameBuffer += ch; break;
				}
				break;
			case 3: // "_"
				switch (ch) {
				case '_': nameBuffer += ch; break;
				case '^': break;
				default: nameBuffer += ch; break;
				}
				break;
			case 4: // id
				switch (ch) {
				case 'i': nameBuffer += getId(); break;
				case '^': break;
				default: nameBuffer += ch; break;
				}
				break;
			case 5: // optional directive
				switch (ch) {
				case 'V': // XholonJsApi val()
				  double dval = getVal();
				  if (dval != 0.0) {
				    nameBuffer += " " + dval;
				  }
				  break;
				case 'W': // XholonJsApi val()
				  double dvalw = getVal();
				  if (dvalw != 0.0) {
				    nameBuffer = "" + dvalw; // replace what's already in the nameBuffer; for example, use this in MathML so Cn will return just its value
				  }
				  break;
				case 'T': // XholonJsApi text()
				  String sval = getVal_String();
				  if ((sval != null) && (sval.length() > 0)) {
				    nameBuffer += sval;
				  }
				  break;
				case 'B': // XholonJsApi bool()
					nameBuffer += " " + getVal_boolean();
					break;
				case 'O': // XholonJsApi obj()
				  Object oval = getVal_Object();
				  if (oval != null) {
				    nameBuffer += " " + oval.toString();
				  }
				  break;
				case 'P': // binary tree path (P) from some root as a hex string (ex: "9abc")
					nameBuffer += this.getBinaryTreePath(16); // ex: "9abc"
					break;
				case 'p': // binary tree path (p) from some root as a bit string (ex: "101011")
					nameBuffer += this.getBinaryTreePath(2); // ex: "101011"
					break;
				case '^': break;
				default: break;
				}
				break;
			default: // should never happen
				break;
			}
		}
		return nameBuffer;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setId(int)
	 */
	public void setId( int entityId )
	{ id = entityId; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setName(java.lang.String)
	 */
	public void setName(String name) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{ return 0.0; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(double)
	 */
	public void setVal(double val)
	{}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_double(double)
	 */
	public void setVal_double(double val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(double)
	 */
	public void incVal(double incAmount)
	{}
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(int)
	 * conflicts with incVal(double) in JS
	 */
	public void incVal(int incAmount)
	{}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(double)
	 */
	public void decVal(double decAmount)
	{}
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(int)
	 * conflicts with decVal(double) in JS
	 */
	public void decVal(int decAmount)
	{}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_boolean()
	 */
	public boolean getVal_boolean() {
		return false;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_byte()
	 */
	public byte getVal_byte() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_char()
	 */
	public char getVal_char() {
		return '\u0000';
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_double()
	 */
	public double getVal_double() {
		return 0.0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_float()
	 */
	public float getVal_float() {
		return 0.0f;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_int()
	 */
	public int getVal_int() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_long()
	 */
	public long getVal_long() {
		return 0L;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_Object()
	 */
	public Object getVal_Object() {
		return null;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_short()
	 */
	public short getVal_short() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_String()
	 */
	public String getVal_String() {
		return null;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(boolean)
	 */
	public void setVal(boolean val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_boolean(boolean)
	 */
	public void setVal_boolean(boolean val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(byte)
	 * conflicts with incVal(double) in JS
	 */
	public void setVal(byte val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_byte(byte)
	 */
	public void setVal_byte(byte val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(char)
	 * conflicts with setVal(double) in JS
	 */
	public void setVal(char val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_char(char)
	 */
	public void setVal_char(char val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(float)
	 * conflicts with setVal(double) in JS
	 */
	public void setVal(float val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_float(float)
	 */
	public void setVal_float(float val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(int)
	 * conflicts with setVal(double) in JS
	 */
	public void setVal(int val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_int(int)
	 */
	public void setVal_int(int val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(long)
	 * conflicts with setVal(double) in JS
	 */
  public void setVal(long val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_long(long)
	 */
	public void setVal_long(long val) {setVal(val);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(java.lang.Object)
	 */
	public void setVal(Object val) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_Object(java.lang.Object)
	 */
	public void setVal_Object(Object val) {setVal(val);}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(short)
	 * conflicts with setVal(double) in JS
	 */
	public void setVal(short val) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_short(short)
	 */
	public void setVal_short(short val) {setVal(val);}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal_String(java.lang.String)
	 */
	public void setVal_String(String val) {setVal(val);}

	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal)
	{
		int classType = IJavaTypes.JAVACLASS_UNKNOWN;
		if (attrName.equals("val")) {
			classType = Misc.getJavaDataType(attrVal);
			switch(classType) {
			case IJavaTypes.JAVACLASS_boolean:
				setVal(Misc.atob(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_double:
				setVal(Misc.atod(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_float:
				setVal(Misc.atof(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_int:
				setVal(Misc.atoi(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_long:
				setVal(Misc.atol(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_String:
				setVal(attrVal);
				break;
			default:
				break;
			}
		}
		else if (attrName.equals("id")) {
		  classType = Misc.getJavaDataType(attrVal);
			switch(classType) {
			case IJavaTypes.JAVACLASS_double:
				setVal(Misc.atod(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_float:
				setVal(Misc.atof(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_int:
				setId(Misc.atoi(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_long:
				setVal(Misc.atol(attrVal, 0));
				break;
			case IJavaTypes.JAVACLASS_String:
				setVal(attrVal);
				break;
			default:
				break;
			}
		}
		else {
		  // TODO don't handle namespaced attrName (ex: "xmlns:xi")
	    setAttributeValNative(attrName, attrVal);
	  }
		return classType;
	}
	
	/**
	 * Set an attribute and it's value using JavaScript.
	 * This is primarily intended to support the use of XML attributes in Xholon Workbooks.
	 * Example:
	 *  &lt;Words roleName="English" lang="en">tree, leaf, cat, car, bird (crow), sun, cloud, house&lt;/Words>
	 *  where  lang="en"  would cause this method to be invoked by the XML parser.
	 */
	protected native void setAttributeValNative(String attrName, String attrVal) /*-{
	  this[attrName] = attrVal;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getClassNode(java.lang.String)
	 */
	public IXholonClass getClassNode( String cName ) {
		IApplication app = getApp();
		return (app == null) ? null : app.getClassNode(cName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getClassNode(int)
	 */
	public IXholonClass getClassNode( int id ) {
		IApplication app = getApp();
		return (app == null) ? null : app.getClassNode(id);
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getXhc()
	 */
	public IXholonClass getXhc()
	{ return xhc; }

	/*
	 * @see org.primordion.xholon.base.IXholon#getXhcId()
	 */
	public int getXhcId()
	{
		if (xhc == null) {
			return -1;
		}
		else {
			return xhc.getId();
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getXhcName()
	 */
	public String getXhcName()
	{
		if (xhc == null) {
			return "UnknownClassName";
		}
		else {
			return xhc.getName();
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setXhc(org.primordion.xholon.base.XholonClass)
	 */
	public void setXhc( IXholonClass xhcNode )
	{
		if (xhcNode == null) {
			logger.error("Xholon setXhc(IXholonClass xhcNode) input arg " + getName() + " is null");
		}
		xhc = xhcNode;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getXhType()
	 */
	public int getXhType()
	{
		if (xhc == null) {
			return IXholonClass.XhtypeNone;
		}
		else {
			return xhc.getXhType();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isContainer()
	 */
	public boolean isContainer()
	{
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isActiveObject()
	 */
	public boolean isActiveObject()
	{
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isPassiveObject()
	 */
	public boolean isPassiveObject()
	{
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasAncestor(java.lang.String)
	 */
	public boolean hasAncestor(String tnName) {
		IXholon node = this;
		while (node != null) {
			if (node.getName().equals(tnName)) {
				return true;
			}
			node = node.getParentNode();
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPorts()
	 */
	public void setPorts() {}
	
	/**
	 * Bind port names to referenced nodes.
	 * This is intended for use in a GWT environment, with nodes that have ad-hoc ports
	 * where the port name (fieldName) is not yet defined.
	 * It calls a JavaScript native method to create new ports.
	 * The application might be a Chameleon app (XhChameleon) or a Bestiary app, or something similar.
	 * The app might be defined in a XholonWorkbook, with or without Chameleon/Bestiary.
	 * The ports must be defined in the XholonClass.
	 * The ports are not typically available by calling app.getAppSpecificObjectVals(),
	 * which only knows about Java GWT compile-time ports.
	 * One way to use this method is to override the configure() method,
	 * with just the single line "bindPorts();" , for example in XhChameleon.
	 *
	 * TODO don't replace a port that's already bound ?
	 */
	public void bindPorts() {
	  List<PortInformation> xhcPortList = this.getXhc().getPortInformation();
		// eval the XPath expressions to determine the reffed nodes
		Iterator<PortInformation> portIt = xhcPortList.iterator();
		while (portIt.hasNext()) {
			PortInformation pi = (PortInformation)portIt.next();
			String xpathExpression = pi.getXpathExpression();
			if (xpathExpression == null) {
			  // this is probably an IPort with portReplication
			  // see XholonWithPorts configurePorts(String instructions, int instructIx)
				int index = pi.getFieldNameIndex();
				String fieldName = pi.getFieldName();
			  int multiplicity = pi.getIportMultiplicity(); // = 0; // default
			  boolean isConjugated = pi.isIportIsConjugated(); // = false; // default
			  String[] providedInterfaceNames = pi.getIportProvidedInterfaceNames().split(","); // = new String[0];
			  String[] requiredInterfaceNames = pi.getIportRequiredInterfaceNames().split(","); // = new String[0];
			  try {
			    IPort iport = Port.createPort(
						  this, multiplicity,
						  null, providedInterfaceNames,
						  null, requiredInterfaceNames,
						  isConjugated);
					if ("port".equals(fieldName)) {
					  // port IPort array
					  setPort(index, iport);
					}
					else {
					  // non-"port" IPort scalar
					  bindPort(this, fieldName, iport);
					}
			  } catch (RuntimeException e) {
				  consoleLog("Unable to set up an IPort port. The port variable may be null." + e);
			  }
			}
			else {
			  IXholon reffedNode = null;
			  String fieldName = pi.getFieldName();
			  if (xpathExpression.startsWith("RemoteNodeService")) {
			    reffedNode = this.bindRemotePort(fieldName, xpathExpression);
			  }
			  else {
			    reffedNode = this.getXPath().evaluate(xpathExpression.trim(), this);
			  }
			  if (reffedNode != null) {
			    int index = pi.getFieldNameIndex();
			    if (fieldName != null) {
			      if (index == PortInformation.PORTINFO_NOTANARRAY) { // -1
			        //if ("port".equals(fieldName)) { // TODO ?
			        //  setPort(0, reffedNode);
			        //}
			        if ("trop".equals(fieldName)) {
			          setPort(0, reffedNode);
			        }
			        else {
			          bindPort(this, fieldName, reffedNode);
			        }
			      }
			      else {
			        // this is an array, possibly a "port" port
			        if ("port".equals(fieldName)) {
			          setPort(index, reffedNode);
			        }
			        else if ("trop".equals(fieldName)) {
			          setPort(index, reffedNode);
			        }
			        //else if ("replication".equals(fieldName)) {
			        //  consoleLog("Xholon bindPorts() fieldName == replication");
			        //  // TODO handle this portReplication within a an IPort port
			        //  // OK the system already handles the portReplication
			        //}
			        else {
			          bindPort(this, fieldName, index, reffedNode);
			        }
			      }
			    }
			  }
			}
		}
	}
	
	public native void consoleLog(Object obj) /*-{
	  if ($wnd.console && $wnd.console.log) {
	    $wnd.console.log(obj);
	  }
	}-*/;
	
	protected native void bindPort(IXholon node, String fieldName, IXholon reffedNode) /*-{
	  if ($wnd.console && $wnd.console.log) {
	    $wnd.console.log("bindPort");
	    $wnd.console.log(node.toString());
	    $wnd.console.log(fieldName);
	    $wnd.console.log(reffedNode.toString());
	  }
	  node[fieldName] = reffedNode;
	}-*/;
	
	protected native void bindPort(IXholon node, String fieldName, int index, IXholon reffedNode) /*-{
	  if (!node[fieldName]) {
      node[fieldName] = [];
    }
    node[fieldName][index] = reffedNode;
	}-*/;
	
	/**
	 * Bind to a remote port using the RemoteNodeService.
	 * Return a new instance of PeerJS.java or other implementation of IRemoteNode.java .
	 * Handle both port name="port" and name="trop".
	 * <p>"RemoteNodeService-PeerJS,Alligator,OPTIONAL_KEY,OPTIONAL_DEBUG"</p>
	 * @param remoteNodeExpr An expression that specifies the remote node (ex: ).
	 * @return A proxy node, or null.
	 */
	protected IXholon bindRemotePort(String portName, String remoteNodeExpr) {
	  boolean isTrop = false;
	  if ("trop".equals(portName)) {
	    isTrop = true;
	  }
	  String[] rnsParams = remoteNodeExpr.split(",", 2);
	  //consoleLog(rnsParams);
    IXholon remoteNode = this.getService(rnsParams[0]);
    if (remoteNode != null) {
      //consoleLog(remoteNode.getName());
      IMessage respMsg = null;
      if (isTrop) {
        // have remoteNode listen for a remote connection (where -3898 (101) = SIG_LISTEN_REQ)
        respMsg = remoteNode.sendSyncMessage(-3898, rnsParams[1], this);
      }
      else {
        // have node initiate a remote connection (where -3897 (102) = SIG_CONNECT_REQ)
        respMsg = remoteNode.sendSyncMessage(-3897, rnsParams[1], this);
      }
      //consoleLog(respMsg);
    }
    else {
      this.println("Unable to create a remoteNode instance from " + rnsParams[0]);
      this.println("If you are using PeerJS, be sure you are using XholonWebRTC.html which includes peer.js, rather than Xholon.html .");
    }
    return remoteNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.configure();
		}
		if (nextSibling != null) {
			nextSibling.configure();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postConfigure()
	 */
	public void postConfigure()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.postConfigure();
		}
		if (nextSibling != null) {
			nextSibling.postConfigure();
		}
	}
	
	/**
	 * Get port reference.
	 * This is based on the XML XPath and XPointer standards.
	 * Examples:
	 * <p>"#xpointer(ancestor::AnElevatorSystem/Floor[1]/CallButton[1])"</p>
	 * <p>"nameOrIdOfOtherApplication#xpointer(/HelloWorldSystem/Hello)"</p>
	 * <p>"ancestor::AnElevatorSystem/Floor[1]/CallButton[1]"</p>
	 * @param instructions A string that may contain a port reference with an XPath location path.
	 * @param instructIx Index into the string where the port reference starts.
	 * @return The Xholon instance identified by the XPath location path,
	 * or the current node if there was no match.
	 */
	protected IXholon getPortRef(String instructions, int instructIx)
	{
	  IXholon node = this;
		int xpointerInc = 11; // increment past '"#xpointer(' string
		int endIxInc = 2; // increment past xpointer ')' end string
		while (instructIx < instructions.length()) {
			switch( instructions.charAt(instructIx)) {
			case '"': // opening "
				IXholon contextNode = this;
				if (instructions.charAt(instructIx+1) != '#') {
					// this references an IXholon in another application or at a remote location,
					// or this app isn't using #xpointer(...)
					instructIx++;
					int endIndex = instructions.indexOf('#', instructIx);
					if (endIndex == -1) {
					  // this app isn't using #xpointer(...),
					  // so just return the entire contents between the opening and closing "
					  xpointerInc = 0; //1; // increment past opening '"'
					  endIxInc = 1; // increment past closing '"'
					}
					else {
					  String otherAppName = instructions.substring(instructIx, endIndex);
					  IApplication otherApp = getApp().getApplicationOther(otherAppName);
					  if (otherApp != null) {
						  contextNode = otherApp.getRoot();
					  }
					  instructIx = endIndex - 1; // point just before the #
					}
				}
				
				// TODO do a proper parsing of the "#xpointer(
				instructIx += xpointerInc; // point to first char after "#xpointer(
				// look for final ) within this port ref; may be other ( and )
				int endIx = instructions.indexOf(IInheritanceHierarchy.NAVINFO_SEPARATOR, instructIx);
				if (endIx == -1) { // this is the last one
					endIx = instructions.length() - endIxInc;
				}
				else {
					endIx -= endIxInc;
				}
				String nodePath = instructions.substring(instructIx, endIx).trim();
				node = getXPath().evaluate(nodePath, contextNode);
				instructIx = endIx + endIxInc; // point beyond closing )"
				break;

			case IInheritanceHierarchy.NAVINFO_SEPARATOR: // end of a port reference
				return node;

			default:
				break;
			}
		}
		return node;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preReconfigure()
	 */
	public void preReconfigure()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.preReconfigure();
		}
		if (nextSibling != null) {
			nextSibling.preReconfigure();
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#reconfigure()
	 */
	public void reconfigure()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.reconfigure();
		}
		if (nextSibling != null) {
			nextSibling.reconfigure();
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#postReconfigure()
	 */
	public void postReconfigure()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.postReconfigure();
		}
		if (nextSibling != null) {
			nextSibling.postReconfigure();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.preAct();
		}
		if (nextSibling != null) {
			nextSibling.preAct();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.act();
		}
		if (nextSibling != null) {
			nextSibling.act();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#actNr()
	 */
	public void actNr() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.postAct();
		}
		if (nextSibling != null) {
			nextSibling.postAct();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#cleanup()
	 */
	public void cleanup()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.cleanup();
		}
		if (nextSibling != null) {
			nextSibling.cleanup();
		}
	}
	
	//	 tree traversal print functions that MAY be overridden in concrete subclasses

	/*
	 * @see org.primordion.xholon.base.IXholon#preOrderPrint(int)
	 */
	public void preOrderPrint( int level )
	{
		printNode( level );
		if (firstChild != null) {
			firstChild.preOrderPrint( level + 1);
		}
		if (nextSibling != null) {
			nextSibling.preOrderPrint( level );
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#inOrderPrint(int)
	 */
	public void inOrderPrint( int level )
	{
		if (firstChild != null) {
			firstChild.inOrderPrint( level + 1 );
		}
		printNode( level );
		if (nextSibling != null) {
			nextSibling.inOrderPrint( level );
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#postOrderPrint(int)
	 */
	public void postOrderPrint( int level )
	{
		if (firstChild != null) {
			firstChild.postOrderPrint( level );
		}
		if (nextSibling != null) {
			nextSibling.postOrderPrint( level + 1 );
		}
		printNode( level );
	}

	/**
	 * Called by the various xOrderPrint() routines.
	 * @param level Level in the tree, where the root node is level 0.
	 */
	protected void printNode( int level )
	{
	  // Xholon classic approach
		/*while (level > 0) {
			print( "." );
			level--;
		}
		if (xhc == null) {
			println( "UnknownClassName" );
		}
		else {
			println( getName() );
		}*/
		
		if (xhc == null) {
			print( "UnknownClassName " );
		}
		else {
			print( getName() + " " );
		}
	}
	
	/**
	 * @see org.primordion.xholon.base.IXholon#visit(org.primordion.xholon.base.IXholon)
	 * @param visitor A visitor.
	 */
	public boolean visit(IXholon visitor)
	{
		if (!visitor.visit(this)) {
			// the visitor has completed its task and doesn't need to visit any more nodes
			return false;
		}
		// visit the children of this visitee node
		IXholon visitee = getFirstChild();
		while (visitee != null) {
			// don't allow visiting the visitor
			if (visitee != visitor) {
				// TODO return false if visitee.visit(visitor); returns false
				//visitee.visit(visitor); // OLD
				if(!visitee.visit(visitor)) {return false;}; // NEW
			}
			visitee = visitee.getNextSibling();
		}
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#writeXml(int, java.io.Writer)
	 */
	/*public void writeXml( int level, Writer fw )
	{
		StringBuffer indent = new StringBuffer(level);
		for (int i = 0; i < level; i++) {
			indent.append('\t');
		}
		try {
			IXholon node = getFirstChild();
			if (node != null) {
				fw.write(indent + "<" + getXhcName() + ">\n");
				node.writeXml(level+1, fw);
				fw.write(indent + "</" + getXhcName() + ">\n");
			}
			else {
				fw.write(indent + "<" + getXhcName() + "/>\n");
			}
			node = getNextSibling();
			if (node != null) {
				node.writeXml(level, fw);
			}
		} catch (IOException e) {
			logger.error("", e);
			//e.printStackTrace();
		}
	}*/

	/*
	 * @see org.primordion.xholon.base.IXholon#handleNodeSelection()
	 */
	public Object handleNodeSelection()
	{
		String parentStr = "";
		if (hasParentNode()) {
			parentStr = (String)parentNode.handleNodeSelection(this);
		}
		return parentStr + toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#handleNodeSelection(org.primordion.xholon.base.IXholon)
	 */
	public Object handleNodeSelection(IXholon otherNode)
	{
		return "";
	}
	
	//               UML2
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
	 * TODO This should probably be in XholonWithPorts because only a xholon with ports can send a message.
	 *      However, that would restrict its flexibility.
	 *      Possibly a xholon without ports might want to send messages to its parent, siblings, children, etc.
	 *      So leave it here for now.
	 */
	public void sendMessage(int signal, Object data, IXholon sender)
	{
		sendMessage(new Message(signal, data, sender, this));
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon, int)
	 */
	public void sendMessage(int signal, Object data, IXholon sender, int index)
	{
		sendMessage(new Message(signal, data, sender, this, index));
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendMessage(org.primordion.xholon.base.IMessage)
	 */
	public void sendMessage(IMessage msg)
	{
		if (getInteractionsEnabled()) {
			getInteraction().addMessage(msg);
		}
		int rc = getMsgQ().enqueue(msg);
		if (rc == IQueue.Q_FULL) {
			println("Xholon sendMessage() Q_FULL " + msg);
		}
		// TODO handle Q_FULL
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		if ((msg.getSignal() == ISignal.SIGNAL_XHOLON_CONSOLE_REQ) && (this.getActionList() != null)) {
			this.doAction((String)msg.getData());
		}
		else if (msg.getSignal() == ISignal.SIGNAL_BPLEX) {
		  // ex (using Dev Tools): var bait = temp1; bait["FishingRodbplex"].msg(-12, ["FishingRodbplex"], bait);
		  // ex: var bait = temp1; bait["FishingRodbplex"].msg(-12, {bplexName:"FishingRodbplex"}, bait);
			Object data = msg.getData();
			if (data == null) {return;}
			String clogStr = "bplex msg sender:" + msg.getSender() + " this:" + this.getName();
			String bplexName = null;
			if (data instanceof JavaScriptObject) {
				clogStr += " datatype:JavaScriptObject";
				JavaScriptObject jso = (JavaScriptObject)data;
				bplexName = (String)getJsoPropertyValue(jso, "bplexName");
				if (bplexName == null) {
					// this may be a JavaScript Array
					JsArrayMixed marr = jso.cast();
					if (marr.length() == 0) {return;}
					bplexName = marr.getString(0);
					if (marr.length() > 1) {
						// TODO the msg might contain a second nested message, or some other IXholon(s) or Object(s)
						//anode = (Object)marr.getObject(1);
					}
					clogStr += " Array";
				}
				else {
					clogStr += " Object";
				}
			}
			else if (data instanceof Object[]) {
				clogStr += " datatype:Java Object[]";
				Object[] oarr = (Object[])data;
				if (oarr.length == 0) {return;}
				bplexName = (String)oarr[0];
			}
			else {
				return;
			}
			consoleLog(clogStr);
			//this.setColor("indigo"); // this works
			if (msg.getSender() != this) {
				// send to next node in the bplex
				if (bplexName != null) {
					IXholon nextBplexNode = getPortNative(this, bplexName);
					if (nextBplexNode != null) {
						// send the same message contents to the next node in the bplex
						nextBplexNode.sendMessage(msg.getSignal(), data, msg.getSender());
					}
				}
			}
		}
		else {
			forwardMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#registerMessageForwardee(org.primordion.xholon.base.IXholon, int[])
	 */
	public void registerMessageForwardee(IXholon forwardee, int[] signal) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#forwardMessage(org.primordion.xholon.base.Message)
	 */
	public void forwardMessage(IMessage msg) {
	  logger.warn("Unexpected async message received : " + msg);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processMessageQ()
	 * TODO same issue as in sendMessage() re whether would be better in XholonWithPorts
	 */
	public void processMessageQ()
	{
		// println("Xholon: processMessageQ()");
		// Only process the messages that are currently in the Q.
		// Don't process messages added to Q during this timestep.
		int numMsgs = getMsgQ().getSize();
		for (int qq = 0; qq < numMsgs; qq++) {
			IMessage rxMsg = (IMessage)getMsgQ().dequeue();
			if (getInteractionsEnabled()) {
				getInteraction().processReceivedMessage(rxMsg);
			}
			if (isSystemMessage(rxMsg)) {
				// this might be a system message
				processSystemMessage(rxMsg);
			}
			else {
				try {
					rxMsg.getReceiver().processReceivedMessage(rxMsg);
				} catch (RuntimeException e) {
					logger.error("Xholon processMessageQ()", e);
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendSystemMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
	 */
	public void sendSystemMessage(int signal, Object data, IXholon sender)
	{
		sendSystemMessage(new Message(signal, data, sender, this));
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendSystemMessage(org.primordion.xholon.base.Message)
	 */
	public void sendSystemMessage(IMessage msg)
	{
		int rc = getSystemMsgQ().enqueue(msg);
		if (rc == IQueue.Q_FULL) {
			logger.debug("Xholon sendSystemMessage() Q_FULL " + msg);
		}
		// TODO handle Q_FULL
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processSystemMessageQ()
	 */
	public void processSystemMessageQ()
	{
		// println("Xholon: processMessageQ()");
		// Only process the messages that are currently in the Q.
		// Don't process messages added to Q during this timestep.
		int numMsgs = getSystemMsgQ().getSize();
		for (int qq = 0; qq < numMsgs; qq++) {
			IMessage rxMsg = (IMessage)getSystemMsgQ().dequeue();
			if (isSystemMessage(rxMsg)) {
				// this might be a system message
				processSystemMessage(rxMsg);
			}
			else {
				try {
					rxMsg.getReceiver().processReceivedMessage(rxMsg);
				} catch (RuntimeException e) {
					logger.error("Xholon processSystemMessageQ()", e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	protected boolean isSystemMessage(IMessage msg)
	{
		if (msg.getSignal() < ISignal.SIGNAL_SYSTEM_MESSAGE) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Process a system message.
	 * @param msg A message received through the system queue.
	 */
	protected void processSystemMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		default:
			try {
				msg.getReceiver().processReceivedMessage(msg);
			} catch (RuntimeException e) {
				logger.error("Xholon processSystemMessage(msg)", e);
			}
			break;
		}
	}
	
	// Synchronous Messaging
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendSyncMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
	 */
	public IMessage sendSyncMessage(int signal, Object data, IXholon sender)
	{
		return sendSyncMessage(new Message(signal, data, sender, this));
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendSyncMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon, int)
	 */
	public IMessage sendSyncMessage(int signal, Object data, IXholon sender, int index)
	{
		return sendSyncMessage(new Message(signal, data, sender, this, index));
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#sendSyncMessage(org.primordion.xholon.base.Message)
	 */
	public IMessage sendSyncMessage(IMessage msg)
	{
		if (getInteractionsEnabled()) {
			IInteraction interaction = getInteraction();
			interaction.addSyncMessage(msg);
			IMessage responseMsg = (IMessage)processReceivedSyncMessage(msg);
			interaction.addSyncMessage(responseMsg);
			return responseMsg;
		}
		else {
			return processReceivedSyncMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedSyncMessage(org.primordion.xholon.base.Message)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		//logger.warn("Unexpected sync message received : " + msg);
		//return new Message(ISignal.SIGNAL_UNKNOWN, null, this, msg.getSender());
		return forwardSyncMessage(msg);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#forwardSyncMessage(org.primordion.xholon.base.Message)
	 */
	public IMessage forwardSyncMessage(IMessage msg) {
	  logger.warn("Unexpected sync message received : " + msg);
	  return new Message(ISignal.SIGNAL_UNKNOWN, null, this, msg.getSender());
	}
	
	/*
	 * The default action for any Xholon, whether or not it's a state machine, is to remove itself from the
	 * xholon composite structure tree, and return itself and its children (recursively) to the factory for
	 * possible recycling.
	 * @see org.primordion.xholon.base.IXholon#terminate()
	 */
	public void terminate()
	{
		removeChild();
		remove();
	}
	
	@Override
	/**
	 * TODO include non-port ports; see 
	 * but don't call getAllPorts() which is too slow when used in D3 Circle Pack
	 */
	public List<IXholon> searchForReferencingNodes()
	{
	  List<IXholon> reffingNodes = new ArrayList<IXholon>();
		IXholon myRoot = getRootNode();
		if (myRoot.getClass() != Control.class) {
			((Xholon)myRoot).searchForReferencingNodesRecurse(this, reffingNodes);
		}
		return reffingNodes;
	}
	
	/**
	 * Search for instances of Xholon with ports that reference this instance.
	 * See enhancements in Xholon2Etrice.java
	 * @param reffedNode The Xholon node that we're looking for references to.
	 * @param reffingNodes A list that is being filled with references.
	 */
	public void searchForReferencingNodesRecurse(Xholon reffedNode, List<IXholon> reffingNodes)
	{
	  IXholon[] port = getPort();
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
			((Xholon)firstChild).searchForReferencingNodesRecurse(reffedNode, reffingNodes);
		}
		// don't search nextSibling if this is xhRoot and nextSibling is srvRoot
		if ((nextSibling != null) && (this.id != 0)) {
			((Xholon)nextSibling).searchForReferencingNodesRecurse(reffedNode, reffingNodes);
		}
	}
	
	@Override
	public List<IXholon> searchForLinkingNodes()
	{
	  List<IXholon> reffingNodes = new ArrayList<IXholon>();
		IXholon myRoot = getRootNode();
		if (myRoot.getClass() != Control.class) {
			((Xholon)myRoot).searchForLinkingNodesRecurse(this, reffingNodes);
		}
		return reffingNodes;
	}
	
	/**
	 * Search for instances of Xholon with ports that reference this instance.
	 * @param reffingNodes A list that is being filled with references.
	 */
	public void searchForLinkingNodesRecurse(Xholon reffedNode, List<IXholon> reffingNodes)
	{
	  /**IXholon[] port = getPort();
	  if (port != null) {
	    for (int i = 0; i < port.length; i++) {
			  if (port[i] != null) {
				  if (port[i] == reffedNode) {
					  reffingNodes.add(this);
					}
				}
			}
		}*/
		
		JsArray<JavaScriptObject> arr = this.getLinksNative(false, true);
	  for (int i = 0; i < arr.length(); i++) {
	    JavaScriptObject obj = arr.get(i);
	    if ((IXholon)getJsoPropertyValue(obj, "reffedNode") == reffedNode) {
	      reffingNodes.add(this);
	    }
	  }
		
		if (firstChild != null) {
			((Xholon)firstChild).searchForLinkingNodesRecurse(reffedNode, reffingNodes);
		}
		// don't search nextSibling if this is xhRoot and nextSibling is srvRoot
		if ((nextSibling != null) && (this.id != 0)) {
			((Xholon)nextSibling).searchForLinkingNodesRecurse(reffedNode, reffingNodes);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performActivity(int, org.primordion.xholon.base.Message)
	 */
	public void performActivity(int activityId, IMessage msg)
	{
		println("Xholon: performActivity(activityId) " + activityId + " " + msg);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performBooleanActivity(int)
	 */
	public boolean performBooleanActivity(int activityId)
	{
		println("Xholon: performBooleanActivity(activityId) " + activityId);
		return false;
	}
		
	/*
	 * @see org.primordion.xholon.base.IXholon#performGuard(int, org.primordion.xholon.base.Message)
	 */
	public boolean performGuard(int activityId, IMessage msg)
	{
		println("Xholon: performGuard(activityId) " + activityId);
		return false;
	}
	
	// Turtle
	
	public void performActivity(int activityId)
	{
		println("Xholon: performActivity(activityId) " + activityId);
	}
	
	//  GP, and other evolutionary computation, ECJ, Behavior encoded as an Activity subtree

	/*
	 * @see org.primordion.xholon.base.IXholon#performVoidActivity(org.primordion.xholon.base.IXholon)
	 */
	public void performVoidActivity(IXholon activity)
	{
		println("Xholon: performVoidActivity(activity) " + activity.getId());
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#performDoubleActivity(org.primordion.xholon.base.IXholon)
	 */
	public double performDoubleActivity(IXholon activity)
	{
		println("Xholon: performDoubleActivity(activity) " + activity.getId());
		return 1.0;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performBooleanActivity(org.primordion.xholon.base.IXholon)
	 */
	public boolean performBooleanActivity(IXholon activity)
	{
		println("Xholon: performBooleanActivity(activity) " + activity.getId());
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPort(org.primordion.xholon.base.IXholon[])
	 */
	public void setPort(IXholon[] port) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPort()
	 */
	public IXholon[] getPort() {return null;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPort(int, org.primordion.xholon.base.IXholon)
	 */
	public void setPort(int portNum, IXholon portRef) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPort(int)
	 * Subclasses that use ports must override this function.
	 */
	public IXholon getPort(int portNum)
	{
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getAllPorts(org.primordion.xholon.base.IXholon)
	 */
	public List getAllPorts()
	{
	  // There are three ways to do this:
	  
	  // (1) specification: uses Reflection, which calls node.getXhc().getPortInformation()
		//return ReflectionFactory.instance().getAllPorts(this, false);
		
		// (2) actual: uses AppGenerator-generated code in Application subclasses
		// this works for Java GWT ports that are bound by GWT at compile-time
		//return node.getApp().getAppSpecificObjectVals(node, (Class<IXholon>)node.getClass()).toArray();
		
		// (3) actual: ports that are bound by JavaScript using bindPorts()
		
		IXholon node = this;
		Class<IXholon> clazz = (Class<IXholon>)node.getClass();
		return getApp().getAppSpecificObjectVals(this, clazz);
	}
	
	@Override
	public List getLinks(boolean placeGraph, boolean linkGraph) {
	  List list = new ArrayList();
	  JsArray<JavaScriptObject> arr = this.getLinksNative(placeGraph, linkGraph);
	  for (int i = 0; i < arr.length(); i++) {
	    JavaScriptObject obj = arr.get(i);
	    String fieldName = (String)getJsoPropertyValue(obj, "fieldName");
	    int fieldNameIndex = getJsoPropertyValueInt(obj, "fieldNameIndex");
	    IXholon reffedNode = (IXholon)getJsoPropertyValue(obj, "reffedNode");
	    String xpathExpression = (String)getJsoPropertyValue(obj, "xpathExpression");
	    list.add(new PortInformation(fieldName, fieldNameIndex, reffedNode, xpathExpression));
	  }
	  return list;
	}
	
	protected native JsArray<JavaScriptObject> getLinksNative(boolean placeGraph, boolean linkGraph) /*-{
	  return this.links(placeGraph, linkGraph);
	}-*/;
	
	protected final native Object getJsoPropertyValue(JavaScriptObject obj, String jsoPropertyName) /*-{
		return obj[jsoPropertyName];
	}-*/;
	
	protected final native int getJsoPropertyValueInt(JavaScriptObject obj, String jsoPropertyName) /*-{
		return obj[jsoPropertyName];
	}-*/;
	
	protected final native IXholon getPortNative(IXholon node, String portName) /*-{
	  return node[portName];
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isBound(org.primordion.xholon.base.IXholon)
	 */
	public boolean isBound(IXholon port)
	{
		if (port == null) {
			return false;
		}
		else {
			//return port.isBound(port);
			return true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		IXholon root = getRootNode();
		if (root == null) {return null;}
		//if (root.getXhc() == null && IApplication.class.isAssignableFrom(root.getClass())) {
		if (root.getXhc() == null && ClassHelper.isAssignableFrom(AbstractApplication.class, root.getClass())) {
			// this must be the Application node
			return root.getService(serviceName);
		}
		else {
			// this might be the model root
			IXholon srvRoot = root.getNextSibling();
			if (srvRoot == null) {
				// try to locate srvRoot through root's xhclass
				if (root.getXhc() == null) {return null;}
				IApplication app = getApp();
				if (app == null) {return null;}
				return app.getService(serviceName);
			}
			IXholon serviceLocator = srvRoot.getFirstChild();
			if (serviceLocator == null) {return null;}
			return serviceLocator.getService(serviceName);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 * Subclasses that use roles might override this method, for better performance.
	 * TODO not all node types can use the service to find role children
	 */
	public void setRoleName(String roleName)
	{
		IXholon role = findFirstChildWithXhClass(CeRole.RoleCE);
		if (role == null) {
			// create a new Role node
			role = appendChild("Role", roleName, Role.getImplName());
		}
		else {
			role.setRoleName(roleName);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 * Subclasses that use roles might override this method, for better performance.
	 * TODO not all node types can use the service to find role children
	 */
	public String getRoleName()
	{
		IXholon role = findFirstChildWithXhClass(CeRole.RoleCE);
		if (role != null) {
			return role.getRoleName();
		}
		else {
			return null;
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setUid(java.lang.String)
	 */
	public void setUid(String uid) {this.setUidNative(uid);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getUid()
	 */
	public String getUid() {return this.getUidNative();}
	
	protected native void setUidNative(String uidArg) /*-{
		this["uuid"] = uidArg;
	}-*/;
	
	protected native String getUidNative() /*-{
		return this["uuid"] ? this["uuid"] : null;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setUri(java.lang.String)
	 */
	public void setUri(String uri) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getUri()
	 */
	public String getUri() {
		if (xhc == null) {return null;}
		IMechanism mech = xhc.getMechanism();
		if (mech == null) {return null;}
		StringBuffer uri = new StringBuffer()
		.append(mech.getNamespaceUri())
		.append("/")
		.append(this.getName(GETNAME_LOCALPART_ID));
		return uri.toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setAnnotation(java.lang.String)
	 */
	public void setAnnotation(String annotation)
	{
	  IXholon ann = findFirstChildWithXhClass(CeAnnotation.AnnotationCE);
		if (ann != null) {
		  ann.setVal(annotation);
		}
		else {
		  // create a new annotation, or update an existing one
			XholonDirectoryService xds = (XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
			if (xds == null) {return;}
			ann = (IXholon)new Annotation(annotation);
			ann.setParentNode(this);
			xds.put(Annotation.makeUniqueKey(this), ann);
		}
	}
	public void setAnno(String annotation) {this.setAnnotation(annotation);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getAnnotation()
	 */
	public String getAnnotation()
	{
		XholonDirectoryService xds = (XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
		if (xds == null) {return null;}
		IXholon ann = (IXholon)xds.get(Annotation.makeUniqueKey(this));
		if (ann != null) {
			return ann.getVal_String();
		}
		ann = findFirstChildWithXhClass(CeAnnotation.AnnotationCE);
		if (ann != null) {
			return ann.getVal_String();
		}
		//return null;
		if ("true".equals(this.getApp().getParam("AnnoUseXholonClassValue"))) {
			return this.xhc.getAnnotation();
		}
		else {
			return null;
		}
	}
	public String getAnno() {return this.getAnnotation();}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasAnnotation()
	 */
	public boolean hasAnnotation()
	{
		IXholon service = getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
		if (service == null) {return false;}
		if (((XholonDirectoryService)service).get(Annotation.makeUniqueKey(this)) != null) {
			return true;
		}
		if (findFirstChildWithXhClass(CeAnnotation.AnnotationCE) != null) {
			//return false;
			return true;
		}
		//return true;
		if ("true".equals(this.getApp().getParam("AnnoUseXholonClassValue"))) {
			return this.xhc.hasAnnotation();
		}
		else {
			return false;
		}
	}
	public boolean hasAnno() {return this.hasAnnotation();}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#showAnnotation()
	 */
	public void showAnnotation()
	{
		XholonDirectoryService xds = (XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
		if (xds == null) {return;}
		IXholon ann = (IXholon)xds.get(Annotation.makeUniqueKey(this));
		if (ann == null) {
			ann = findFirstChildWithXhClass(CeAnnotation.AnnotationCE);
		}
		if (ann != null) {
			ann.showAnnotation();
		}
		else if ("true".equals(this.getApp().getParam("AnnoUseXholonClassValue"))) {
			this.xhc.showAnnotation();
		}
	}
	public void showAnno() {this.showAnnotation();}
	
	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o)
	{
		String nameTemplate = "^:c_i^";
		String thisName = getName(nameTemplate);
		String otherName = ((IXholon)o).getName(nameTemplate);
		return thisName.compareTo(otherName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		String[] al = getActionListNative();
		if (al != null) {
		  return al;
		}
		
	  /* example:
		<ActionList>
			<ActionOne/> <!-- a sub-xholonclass of Action -->
			<ActionTwo/>
			<ActionThree/>
		</ActionList>
		*/
		IXholon actionList = findFirstChildWithXhClass(CeAction.ActionListCE);
		if (actionList != null) {
			return actionList.getActionList();
		}
		else {
			return null;
		}
	}
	
	/*
	 * 
	 */
	protected native String[] getActionListNative() /*-{
	  var alNames = null;
	  if (this.actionList) {
	    alNames = [];
      for (var prop in this.actionList) {
        var pname = prop;
        alNames.push(pname);
	    }
	  }
	  return alNames;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		IXholon aList = findFirstChildWithXhClass(CeAction.ActionListCE);
		if (aList == null) {
			// create a new ActionList node
			aList = appendChild("ActionList", null);
		}
		if (aList != null) {
			aList.setActionList(actionList);
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
	  if (!doActionNative(action)) {
		  IXholon actionList = findFirstChildWithXhClass(CeAction.ActionListCE);
		  if (actionList != null) {
			  actionList.doAction(action);
		  }
		}
	}
	
	/*
	 * 
	 */
	protected native boolean doActionNative(String action) /*-{
	  if (this.actionList) {
	    var a = this.actionList[action];
	    if (a) {
	      a();
	      return true;
	    }
	  }
	  return false;
	}-*/;
	
	/**
	 * Get a configured instance of Xml2Xholon.
	 * @return
	 */
	public IXml2Xholon getXml2Xholon()
	{
		//String className = "org.primordion.xholon.io.xml.Xml2Xholon";
		IXml2Xholon xml2Xholon = new org.primordion.xholon.io.xml.Xml2Xholon();
		/*
		//xml2Xholon = new Xml2Xholon();
		try {
			xml2Xholon = (IXml2Xholon)Class.forName(className).newInstance();
			
		} catch (InstantiationException e) {
			logger.error("Unable to instatiate optional Xml2Xholon class {}", e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to load optional Xml2Xholon class {}", e);
		} catch (ClassNotFoundException e) {
			logger.error("Unable to load optional Xml2Xholon class " + className);
		} catch (NoClassDefFoundError e) {
			logger.error("Unable to load optional Xml2Xholon class {}", e);
		}*/
		IApplication app = getApp();
		if (app == null) {return null;}
		if (xml2Xholon != null) {
			xml2Xholon.setInheritanceHierarchy(app.getInherHier());
			xml2Xholon.setTreeNodeFactory(app.getFactory());
			xml2Xholon.setApp(app);
		}
		return xml2Xholon;
	}
	
	/**
	 * Get a new instance of Xholon2Xml.
	 * @return
	 */
	public IXholon2Xml getXholon2Xml()
	{
		//String className = "org.primordion.xholon.io.xml.Xholon2Xml";
		IXholon2Xml xholon2Xml = new org.primordion.xholon.io.xml.Xholon2Xml();
		/*try {
			xholon2Xml = (IXholon2Xml)Class.forName(className).newInstance();
			
		} catch (InstantiationException e) {
			logger.error("Unable to instatiate optional Xholon2Xml class {}", e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to load optional Xholon2Xml class {}", e);
		} catch (ClassNotFoundException e) {
			logger.error("Unable to load optional Xholon2Xml class " + className);
		} catch (NoClassDefFoundError e) {
			logger.error("Unable to load optional Xholon2Xml class {}", e);
		}*/
		return xholon2Xml;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		String localName = getXhcName();
		String prefix = null;
		String namespaceUri = null;
		String nameTemplate = xholon2xml.getNameTemplate();
		boolean isPrefixed = (this.getXhc() != null) && (this.getXhc().isPrefixed());
		// write start element
		if (isPrefixed) {
			prefix = xhc.getPrefix();
			// remove the prefix from the name
			int beginIndex = localName.indexOf(':');
			if (beginIndex != -1) {
				localName = localName.substring(beginIndex + 1);
			}
			namespaceUri = xhc.getMechanism().getNamespaceUri();
			xmlWriter.writeStartElement(prefix, localName, namespaceUri);
		} else {
			if (nameTemplate == null) {
				xmlWriter.writeStartElement(localName);
			} else {
				xmlWriter.writeStartElement(this.getName(nameTemplate));
			}
		}
		// write attributes
		if (xholon2xml.isWriteAttributes()) {
			toXmlAttributes(xholon2xml, xmlWriter);
		}
		xholon2xml.writeSpecial(this);
		toXmlText(xholon2xml, xmlWriter);
		// write annotation
		if (xholon2xml.isWriteAnnotations()) {
			String ann = xholon2xml.findAnnotation(this);
			if (ann != null) {
				xmlWriter.writeStartElement("Annotation");
				xmlWriter.writeText(ann);
				xmlWriter.writeEndElement("Annotation");
			}
		}
		// write children
		IXholon childNode = getFirstChild();
		while (childNode != null) {
			childNode.toXml(xholon2xml, xmlWriter);
			childNode = childNode.getNextSibling();
		}
		// write end element
		if (isPrefixed) {
			xmlWriter.writeEndElement(localName, namespaceUri);
		} else {
			if (nameTemplate == null) {
				xmlWriter.writeEndElement(localName);
			} else {
				xmlWriter.writeEndElement(this.getName(nameTemplate));
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		if (getXhc() == null) {
			// if the xhNode is itself a XholonClass node, then the XholonClass = null
			return;
		}
		if (xholon2xml.isWriteStandardAttributes()) {
			toXmlAttributes_standard(xholon2xml, xmlWriter);
		}
		if (xholon2xml.getXhAttrStyle() == IXholon2Xml.XHATTR_TO_NULL) {
			return;
		}
		// application specific attributes
		IReflection ir = ReflectionFactory.instance();
		// get attributes that belong directly to the concrete Java class, excluding statics
		Object attribute[][] = ir.getAppSpecificAttributes(this, xholon2xml.getXhAttrReturnAll(), false, false);
		for (int i = 0; i < attribute.length; i++) {
			Class clazz = (Class)attribute[i][2];
			if (clazz.isArray()) {
				// for now, ignore arrays
				continue;
			}
			// TODO for now, should ignore collections and anything else that is not a primitive
			String name = (String)attribute[i][0];
			if (name == null) {continue;}
			if ("roleName".equalsIgnoreCase(name)) {continue;}
			else if ("uid".equalsIgnoreCase(name)) {continue;}
			else if ("uri".equalsIgnoreCase(name)) {continue;}
			Object value = attribute[i][1];
			if (value == null) {continue;}
			// don't write attributes of type IXholon
			//if (IXholon.class.isAssignableFrom(value.getClass())) {
			if (ClassHelper.isAssignableFrom(Xholon.class, value.getClass())) {
				continue;
			}
			toXmlAttribute(xholon2xml, xmlWriter, name, value.toString(), clazz);
		}
		// handle other attributes, which were probably created as JavaScript properties
		if (xholon2xml.isWriteJavaScriptAttributes()) {
		  IXholon xcs = getService(IXholonService.XHSRV_XHOLON_CREATION);
		  if (xcs != null) {
		    // exclude JavaScript variables that contain "$"
		    IMessage msg = xcs.sendSyncMessage(ITreeNodeFactory.SIG_REPORT_EXTRA_ATTRS_IN_XHOLON_NODE_NO_DOLLAR_REQ, this, this);
		    JavaScriptObject obj = (JavaScriptObject)msg.getData();
		    JSONObject jsonObj = new JSONObject(obj);
		    if (jsonObj.size() > 0) {
		      Set<String> keySet = jsonObj.keySet();
		      Iterator<String> jsIt = keySet.iterator();
		      while (jsIt.hasNext()) {
		        String propName = jsIt.next();
		        String propValue = jsonObj.get(propName).toString();
		        if (propValue.startsWith("\"")) {
		          // remove start and end double quote
		          propValue = propValue.substring(1, propValue.length()-1);
		        }
		        toXmlAttribute(xholon2xml, xmlWriter, propName, propValue, null);
		      }
		    }
		  }
		}
	}
	
	/**
	 * <p>Write the standard Xholon attributes to XML.
	 * The standard attributes are:</p>
	 * <ul>
	 * <li>id (optional)</li>
	 * <li>roleName (if a value exists)</li>
	 * <li>uid (if a value exists)</li>
	 * <li>ports (optional)</li>
	 * </ul>
	 * @param xholon2xml
	 * @param xmlWriter The XML writer.
	 */
	protected void toXmlAttributes_standard(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		// standard Xholon attributes
		if (xholon2xml.isWriteXholonId()) {
			xmlWriter.writeAttribute("id", Integer.toString(getId()));
		}
		if (xholon2xml.isWriteXholonRoleName()) {
			String rnStr = getRoleName();
			if (rnStr != null) {
				xmlWriter.writeAttribute("roleName", rnStr);
			}
		}
		String uidStr = getUid();
		if (uidStr != null) {
			xmlWriter.writeAttribute("uid", uidStr);
		}
		if (xholon2xml.isWritePorts()) {
			List portList = getAllPorts();
			for (int i = 0; i < portList.size(); i++) {
				PortInformation portInfo = (PortInformation)portList.get(i);
				xmlWriter.writeStartElement("port");
				xmlWriter.writeAttribute("name", portInfo.getFieldName());
				xmlWriter.writeAttribute("index", Integer.toString(portInfo.getFieldNameIndex()));
				String pathExpr = "";
				IApplication thisApp = getApp(); //Application.getApplication(this);
				IApplication reffedApp = getApp().getApplicationOther(portInfo.getReffedNode());
				if (thisApp != reffedApp) {
					// the port references a node in another IApplication
					pathExpr += reffedApp.getModelName();
				}
				pathExpr += "#xpointer(//";
				pathExpr += portInfo.getReffedNode().getXhcName();
				pathExpr += "[@id='";
				pathExpr += portInfo.getReffedNode().getId();
				pathExpr += "'])";
				xmlWriter.writeAttribute("connector", pathExpr);
				xmlWriter.writeEndElement("port");
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttribute(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public void toXmlAttribute(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, String name, String value, Class clazz)
	{
		switch (xholon2xml.getXhAttrStyle()) {
		case IXholon2Xml.XHATTR_TO_XMLATTR:
			xmlWriter.writeAttribute(name, value);
			break;
		case IXholon2Xml.XHATTR_TO_XMLELEMENT:
			String attributeTag = getAttributeTag(clazz);
			xmlWriter.writeStartElement(attributeTag);
			xmlWriter.writeAttribute("roleName", name);
			xmlWriter.writeText(value);
			xmlWriter.writeEndElement(attributeTag);
			break;
		default:
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlText(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlText(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		// do nothing by default
	}
	
	@Override
	public String makeTextXmlEmbeddable(String str) {
	  if (str == null) {return "";}
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '<':
			case '&':
				return "<![CDATA[" + str + "]]>";
			default: break;
			}
		}
		return str;
	}
	
	/**
	 * Get the type of XML Attribute tag that corresponds to a particular Java class.
	 * TODO This method may return an invalid Xholon class name.
	 * @param clazz A Java class.
	 * @return A name of a subclass of the Xholon Attribute class.
	 */
	protected String getAttributeTag(Class clazz)
	{
		String thisClassName = clazz.getName();
		thisClassName = thisClassName.substring(thisClassName.lastIndexOf('.')+1);
		String tag = "Attribute_" + thisClassName;
		return tag;
	}
	
	public Object getAttributeXh(String name) {
		return ((XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY))
				.get(XholonDirectoryService.makeUniqueKey(this, name));
	}
	
	public void setAttributeXh(String name, Object value) {
		((XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY))
				.put(XholonDirectoryService.makeUniqueKey(this, name), value);
	}
	
	public void removeAttributeXh(String name) {
		((XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY))
				.remove(XholonDirectoryService.makeUniqueKey(this, name));
	}
	
	public boolean hasAttributeXh(String name) {
		return ((XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY))
				.containsKey(XholonDirectoryService.makeUniqueKey(this, name));
	}
	
	public IAttribute getAttributeNodeXh(String name) {
		//Object obj = getAttributeXh(name);
		// TODO complete this; obj may be an Object, an IXholon, or an IAttribute
		// if possible use getXhc().hasAncestor()
		return null;
	}
	
	public void setAttributeNodeXh(IAttribute newAttr) {
		
	}
	
	public IAttribute removeAttributeNodeXh(IAttribute oldAttr) {
		return null;
	}
	
	/**
	 * Two xholons are equal only if they are the same object.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		return (this == obj);
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	//public int hashCode()
	//{
	//	return super.hashCode();
	//}
	
	/**
	 * Hashify this Xholon subtree.
	 * This is intended to be used as a hash, to differentiate two trees, typically at two points in time.
	 * The string is unique, while being as short as possible.
	 * Note that there are many possibilities for what a stringify/hashify method might return.
	 * TODO possibly have one method that returns a short hash-like value (hashify),
	 *   and another that returns a more human-readable value (stringify).
	 * TODO for more hash possibilities, see:
	 *   http://stackoverflow.com/questions/1988665/hashing-a-tree-structure
	 * @param type "sxpres" (default) or "newick" or null (for default)
	 * @return a simple Newick-like string.
	 *   ((Height)Block,Brick,Brick)BlocksAndBricks;  NO - can't tell the difference between the 2 Brick nodes
	 *   ((Height_3)Block_2,Brick_4,Brick_5)BlocksAndBricks_1;  NO - too long, but OK for stringify
	 *   ((3)2,4,5)1;
	 *   ((),,);  NO - need to differentiate between trees where siblings have moved
	 *  OR S-expression
	 *   (BlocksAndBricks(Block(Height) Brick Brick))
	 *   (1 (2 (3) 4 5))
	 */
	public String hashify(String type) {
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case "newick":
			hashifyNewick(this, sb);
			sb.append(";");
			break;
		case "sxpres":
		default:
			sb.append("(");
			hashifySXpres(this, sb);
			sb.append(")");
			break;
		}
		return sb.toString();
	}
	
	/**
	 * Recursively hashify a Xholon subtree, with output in a simple Newick format.
	 * @param node A node in the Xholon hierarchy.
	 * @param sb A StringBuilder instance.
	 */
	protected void hashifyNewick(IXholon node, StringBuilder sb) {
		if (node.hasChildNodes()) {
			sb.append("(");
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				hashifyNewick(childNode, sb);
				childNode = childNode.getNextSibling();
				if (childNode != null) {
					sb.append(",");
				}
			}
			sb.append(")");
		}
		sb.append(node.getId());
	}
	
	/**
	 * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
	 * @param node A node in the Xholon hierarchy.
	 * @param sb A StringBuilder instance.
	 */
	protected void hashifySXpres(IXholon node, StringBuilder sb) {
		sb.append(node.getId());
    if (node.hasChildNodes()) {
      sb.append(" (");
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        hashifySXpres(childNode, sb);
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          sb.append(" ");
        }
      }
      sb.append(")");
    }
	}
	
	/**
	 * Process new items from a Meteor collection.
	 * The Meteor collection acts like a queue.
	 * @param collName
	 */
	@Override
	public void processMeteorQ(String collName) {
	  IXholon meteorService = getService(IXholonService.XHSRV_METEOR_PLATFORM);
	  if (meteorService != null) {
      //consoleLog("Xholon: processMeteorQ( " + collName);
      meteorService.processMeteorQ(collName);
    }
	}
	
	/**
   * Has the Meteor platform been loaded into the DOM?
   */
  @Override
  public native boolean isExistsMeteor() /*-{
    if ($wnd.Meteor) {
      var ready = $wnd.xh.param("ReadyForMeteor");
      //$wnd.console.log(ready);
      if (ready == null) { // param("ReadyForMeteor") is not in use
        //$wnd.console.log("ready is null");
        return true;
      }
      else if (ready == "true") {
        //$wnd.console.log("ready is true");
        return true;
      }
      else {
        //$wnd.console.log("ready is else");
        return false;
      }
    }
    else {
      return false;
    }
  }-*/;
  
  @Override
  public native String getColor() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.color;
	}-*/;
	
  @Override
	public native void setColor(String color) /*-{
	  if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
	  this.decoration.color = color;
	  if (color == null) {return;}
	  // handle rgba(red,green,blue,alpha)  example: "rgba(255,255,255,1.0)"
	  if ((color.substring(0,5) == "rgba(") && (color.charAt(color.length-1) == ")")) {
	    var arr = color.substring(5,color.length-1).split(",");
	    if (arr.length == 4) {
	      this.decoration.opacity = arr[3];
	    }
	  }
	  // handle #rrggbbaa  example: "#FFFFFFFF"
	  else if ((color.charAt(0) == "#") && (color.length == 9)) {
	    var opacity = Number("0x" + color.substring(7,9)) / 255;
	    this.decoration.opacity = opacity;
	  }
	}-*/;

  @Override
  public native String getOpacity() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.opacity;
	}-*/;
	
  @Override
	public native void setOpacity(String opacity) /*-{
	  if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
	  this.decoration.opacity = opacity;
	}-*/;

  @Override
	public native String getFont() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.font;
	}-*/;

  @Override
	public native void setFont(String font) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.font = font;
	}-*/;

  @Override
	public native String getIcon() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.icon;
	}-*/;

  @Override
	public native void setIcon(String icon) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.icon = icon;
	}-*/;
	
  @Override
	public native String getToolTip() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.toolTip;
	}-*/;

  @Override
	public native void setToolTip(String toolTip) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.toolTip = toolTip;
	}-*/;
	
  @Override
	public native String getSymbol() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.symbol;
	}-*/;
	
  @Override
	public native void setSymbol(String symbol) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.symbol = symbol;
	}-*/;
	
  @Override
	public native String getFormat() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.format;
	}-*/;
	
  @Override
	public native void setFormat(String format) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.format = format;
	}-*/;
  
  @Override
	public native void setGeo(String geo) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.geo = geo;
	}-*/;
  
  @Override
	public native String getGeo() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.geo;
	}-*/;
	
  @Override
	public native void setSound(String geo) /*-{
		if (typeof this.decoration == "undefined") {
			this.decoration = {};
		}
		this.decoration.sound = sound;
	}-*/;
  
  @Override
	public native String getSound() /*-{
		if (typeof this.decoration == "undefined") {return null;}
		return this.decoration.sound;
	}-*/;
	
	@Override
	public native Object subtrees(String stNames) /*-{
		if (stNames) {
			if (!this["subtrees"]) {
				this["subtrees"] = {};
			}
			var arr = stNames.split(",");
			for (var i = 0; i < arr.length; i++) {
				var subtreeName = arr[i];
				var existingNode = this.xpath(subtreeName);
				if (existingNode) {
					this["subtrees"][subtreeName] = existingNode.remove();
				}
				else {
					//var node = this.action("build " + subtreeName).parent().last().remove(); // Avatar
					this.append("<" + subtreeName + "/>");
					if (this.last()) {
						var node = this.last().remove();
						this["subtrees"][subtreeName] = node;
					}
				}
			}
		}
		return this["subtrees"];
	}-*/;
	
	@Override
	public native IXholon subtree(String stName) /*-{
		if (stName && this["subtrees"] && this["subtrees"][stName]) {
			return this["subtrees"][stName];
		}
		return null;
	}-*/;
	
	@Override
	public void buildBinaryTreePaths(String str) {
		this.setBinaryTreePath(str);
		IXholon leftNode = this.getFirstChild();
		if (leftNode != null) {
			leftNode.buildBinaryTreePaths(str + "1");
		}
		IXholon rightNode = this.getNextSibling();
		if (rightNode != null) {
			rightNode.buildBinaryTreePaths(str + "0");
		}
	}
	
	@Override
	public native void setBinaryTreePath(String str) /*-{
		this["btpathbinary"] = str;
	}-*/;
	
	@Override
	public native String getBinaryTreePath(int base) /*-{
		var btpathstr = this["btpathbinary"];
		if (typeof btpathstr == "undefined") {return null;}
		if (btpathstr == "") {return "";}
		// var hexa = parseInt(number, 2).toString(16).toUpperCase();
		switch (base) {
		case 2:  return btpathstr;
		case 10: return Number("0b" + btpathstr).toString(10); // might return "NaN"
		case 16: return Number("0b" + btpathstr).toString(16); // might return "NaN"
		default: return null;
		}
	}-*/;
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}
}
