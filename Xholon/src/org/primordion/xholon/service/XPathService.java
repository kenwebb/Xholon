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

package org.primordion.xholon.service;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;

/**
 * A simple XPath 1.0 service.
 * This service can also be used to support other path languages such as PcsPath.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 29, 2009)
 */
public class XPathService extends AbstractXholonService {
	private static final long serialVersionUID = 4992546629491790961L;

	/**
	 * Name of the default class that implements the service.
	 */
	private static final String DEFAULT_IMPL_NAME = "org.primordion.xholon.base.XPath";
	//private static final String DEFAULT_IMPL_NAME = "org.primordion.xholon.service.xpath.Jaxen";
	
	/**
	 * Name of the class that implements the service.
	 */
	private String implName = null;
	
	/**
	 * The singleton instance.
	 */
	private transient IXholon instance = null;
	
	/**
	 * Get the singleton instance of a service class that implements IXPath.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(IXholonService.XHSRV_XPATH)) {
			return createPathService("XPath");
		}
		else if (serviceName.equals(IXholonService.XHSRV_PCSPATH)) {
			return createPathService("PcsPath");
		}
		else if (serviceName.equals(IXholonService.XHSRV_JXPATH)) {
			// for now, just instantiate it directly
			//return new org.primordion.xholon.service.xpath.JXPath(); // GWT
		}
		else if (serviceName.equals(IXholonService.XHSRV_JAXEN)) {
			// for now, just instantiate it directly
			//return new org.primordion.xholon.service.xpath.Jaxen(); // GWT
		}
		else {
			return null;
		}
		return null; // GWT
	}
	
	/**
	 * Create a path service instance.
	 * @param xholonClassName The name of a specific path language.
	 * ex: "XPath" "PcsPath"
	 * @return
	 */
	protected IXholon createPathService(String xholonClassName)
	{
		if (instance == null) {
			IXholonClass pathXholonClass = null;
			if (implName == null) {
				// try to get the implName from the XPath or PcsPath IXholonClass
				pathXholonClass = getApp().getClassNode(xholonClassName);
				if (pathXholonClass != null) {
					implName = pathXholonClass.getImplName();
				}
			}
			if (implName == null) {
				implName = DEFAULT_IMPL_NAME;
			}
			instance = createInstance(implName);
			instance.setId(getNextId());
			instance.setXhc(pathXholonClass);
			instance.appendChild(this);
		}
		return instance;
	}
	
	// actions
	private static final String showPathFromRoot     = "Show Path from root";
	private static final String showPathFromTo       = "Show Path from/to";
	private static final String showPathFromToAsPort = "Show Path from/to as port";
	private static final String showPathToFrom       = "Show Path to/from";
	private static final String showPathToFromAsPort = "Show Path to/from as port";
	private static final String testPcsPath = "Test PcsPath";
	
	/** action list */
	private String[] actions = {
			showPathFromRoot,
			showPathFromTo,
			showPathFromToAsPort,
			showPathToFrom,
			showPathToFromAsPort,
			testPcsPath};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		String pathExpr = null;
		if (action == null) {return;}
		IXholon[] selectedNodes = getCurrentlySelectedNodes();
		if (selectedNodes == null) {return;}
		if (action.equals(showPathFromRoot)) {
			for (int i = 0; i < selectedNodes.length; i++) {
				pathExpr = ((IXPath)instance).getExpression(selectedNodes[i], getApp().getRoot(), false);
				show(pathExpr);
			}
		}
		else if (action.equals(showPathFromTo)) {
			if (selectedNodes.length != 2) {return;}
			pathExpr = ((IXPath)instance).getExpression(selectedNodes[0], selectedNodes[1]);
			show(pathExpr);
		}
		else if (action.equals(showPathFromToAsPort)) {
			// <port name="port" index="0" connector="#xpointer(ancestor::HelloWorldSystem/World)"/>
			if (selectedNodes.length != 2) {return;}
			pathExpr = ((IXPath)instance).getExpression(selectedNodes[0], selectedNodes[1]);
			StringBuilder sb = new StringBuilder();
			sb.append("<port name=\"port\" index=\"0\" connector=\"#xpointer(");
			sb.append(pathExpr);
			sb.append(")\"/>");
			show(sb.toString());
		}
		else if (action.equals(showPathToFrom)) {
			if (selectedNodes.length != 2) {return;}
			pathExpr = ((IXPath)instance).getExpression(selectedNodes[1], selectedNodes[0]);
			show(pathExpr);
		}
		else if (action.equals(showPathToFromAsPort)) {
			if (selectedNodes.length != 2) {return;}
			pathExpr = ((IXPath)instance).getExpression(selectedNodes[1], selectedNodes[0]);
			StringBuilder sb = new StringBuilder();
			sb.append("<port name=\"port\" index=\"0\" connector=\"#xpointer(");
			sb.append(pathExpr);
			sb.append(")\"/>");
			show(sb.toString());
		}
		else if (action.equals(testPcsPath)) {
			instance = null;
			implName = null;
			createPathService("PcsPath");
		}
		else {}
	}
	
	/**
	 * Show the expression in some way.
	 * @param expr An XPath expression.
	 */
	protected void show(String expr)
	{
		//System.out.println(expr);
		//getLogger().info(expr);
		println(expr);
	}
	
	/**
	 * Get the IXholon nodes that are currently selected.
	 * @return An array of nodes, or null.
	 */
	protected IXholon[] getCurrentlySelectedNodes()
	{
		IXholon nss = getServiceLocatorService().getService(IXholonService.XHSRV_NODE_SELECTION);
		if (nss != null) {
			IMessage nodesMsg = nss.sendSyncMessage(
				NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
			return (IXholon[])nodesMsg.getData();
		}
		return null;
	}
	
	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}
}
