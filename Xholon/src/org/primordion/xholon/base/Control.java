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

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Control for a Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on December 17, 2005)
 */
public class Control extends Xholon implements IControl {
	private static final long serialVersionUID = 1903764484531724051L;
	
	/**
	 * Name of a Control object.
	 */
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}
	
	public String getName() { return roleName;}
	
	/* 
	 * @see org.primordion.xholon.base.IControl#setControlNode(java.lang.String, org.primordion.xholon.base.Control, org.primordion.xholon.base.IXholonClass)
	 */
	public IControl setControlNode(String nodeName, IControl aParentNode, IXholonClass controlClass) {
		Control node = new Control();
		node.setXhc(controlClass);
		node.setId(getNextId());
		node.setRoleName(nodeName);
		if (aParentNode != null) {
			node.appendChild(aParentNode);
		}
		return node;
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
	 * Most Control nodes do not need to do something every timeStep.
	 * The only purpose of this act() method is to invoke act() on the child nodes.
	 * This currently applies to the View node, which may have children.
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		if (firstChild != null) {
			firstChild.act();
		}
		// DO NOT invoke sibling nodes.
	}
	
	/**
	 * By definition, a Control node is never a root node.
	 * Its root node is the instance of IApplication.
	 * @see org.primordion.xholon.base.Xholon#isRootNode()
	 */
	public boolean isRootNode()
	{
		return false;
	}
	
	/**
	 * By definition, a Control node's root node is the instance of IApplication.
	 * @see org.primordion.xholon.base.IXholon#getApp()
	 */
	public IApplication getApp() {
		return (IApplication)getRootNode();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#handleNodeSelection()
	 */
	public Object handleNodeSelection()
	{
		String event = roleName; // give this role a more meaningful name
		IApplication iapp = getApp();
	  System.out.println("Control.handleNodeSelection() event:" + event
	    + " state:" + stateName[iapp.getControllerState()]);
		if (hasParentNode()) {
			switch (iapp.getControllerState()) {
			case CS_INITIALIZED:
				if (event.equals("Start")) {iapp.setControllerState(CS_RUNNING);}
				else if (event.equals("Step")) {iapp.setControllerState(CS_STEPPING);}
				else if (event.equals("Stop")) {iapp.setControllerState(CS_STOPPED);}
				break;
			case CS_RUNNING:
				if (event.equals("Pause")) {iapp.setControllerState(CS_PAUSED);}
				else if (event.equals("Stop")) {iapp.setControllerState(CS_STOPPED);}
				break;
			case CS_PAUSED:
				if (event.equals("Pause")) {iapp.setControllerState(CS_RUNNING);}
				else if (event.equals("Start")) {iapp.setControllerState(CS_RUNNING);}
				else if (event.equals("Step")) {iapp.setControllerState(CS_STEPPING);}
				else if (event.equals("Stop")) {iapp.setControllerState(CS_STOPPED);}
				break;
			case CS_STEPPING:
				// probably won't happen, but just in case
				iapp.setControllerState(CS_RUNNING);
				break;
			case CS_STOPPED:
				break;
			default:
				break;
			}
		}
		return toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		xmlWriter.writeAttribute("roleName", getRoleName());
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName() + "_" + getId();
		if ("Controller".equals(roleName)) {
			outStr += " state=" + getApp().getControllerStateName();
		}
		return outStr;
	}
}
