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

import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;

/**
 * The Wiring Service provides a range of services related to the connection or wiring
 * of xholons to each other. It will be able to do the following types of things:
 * <ul>
 * <li>Autowire xholons, with override capability for optional domain/mechanism-specific autowiring.</li>
 * <li>Show and protect unbound ports.</li>
 * <li>Do dynamic rewiring.</li>
 * <li>Insert new intermediate nodes that could do anything such as:
 * security, or other policies;
 * log, monitor;
 * etc.
 * </li>
 * <li></li>
 * </ul>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 22, 2009)
 */
public class WiringService extends AbstractXholonService {
	private static final long serialVersionUID = 3891233638793276409L;
	
	/**
	 * This instance of the Wiring Service.
	 * This is primarily for use by inner classes to access the WiringService instance.
	 */
	private transient IXholon wiringServiceInstance = this; 
	
	/*
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getXhcName())) {
			return this;
		}
		else {
			return null;
		}
	}
	
	// actions
	private static final String showUnboundPorts    = "Show unbound ports";
	private static final String protectUnboundPorts = "Protect unbound ports";
	
	/** action list */
	private String[] actions = {showUnboundPorts, protectUnboundPorts};
	
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
		if (action == null) {return;}
		if (action.equals(showUnboundPorts)) {
			showUnboundPorts();
		}
		else if (action.equals(protectUnboundPorts)) {
			protectUnboundPorts();
		}
		else {}
	}
	
	/**
	 * Show unbound ports.
	 */
	public void showUnboundPorts()
	{
		/**
		 * A method-local inner class that does the work of showing unbound ports.
		 */
		class UnboundPortShower extends Xholon {
			private static final long serialVersionUID = 9014185434821594621L;

			public void show() {
				IXholon[] selectedNodes = getCurrentlySelectedNodes();
				if (selectedNodes == null) {return;}
				for (int i = 0; i < selectedNodes.length; i++) {
					IXholon aRoot = selectedNodes[i];
					// go through the entire subtree of the selected node
					aRoot.visit(this);
				}
			}
			
			/**
			 * @see org.primordion.xholon.base.IXholon#visit(org.primordion.xholon.base.IXholon)
			 * @param visitee The node in the tree that is currently being visited.
			 */
			@SuppressWarnings("rawtypes")
			public boolean visit(IXholon visitee)
			{
				List portList = ReflectionFactory.instance().getAllPorts(visitee, true);
				for (int j = 0; j < portList.size(); j++) {
					PortInformation portInfo = (PortInformation)portList.get(j);
					if (!isBound(portInfo.getReffedNode())) {
						StringBuilder sb = new StringBuilder()
							.append(visitee.getName())
							.append(": ")
							.append(portInfo.getFieldName());
						if (portInfo.getFieldNameIndex() != PortInformation.PORTINFO_NOTANARRAY) {
							sb.append("[")
							.append(portInfo.getFieldNameIndex())
							.append("]");
						}
						if (portInfo.getReffedNode() == wiringServiceInstance) {
							sb.append(" protected");
						}
						System.out.println(sb);
					}
				}
				return true;
			}
			
		} // end of UnboundPortShower inner class
		
		new UnboundPortShower().show();
	}
	
	/**
	 * Protect unbound ports, by connecting them to the Wiring Service.
	 * This protects the ports, by preventing runtime NullPointerExceptions,
	 * if standard IXholon methods are called on the port.
	 */
	public void protectUnboundPorts()
	{
		/**
		 * A method-local inner class that does the work of protecting unbound ports.
		 */
		class UnboundPortProtector extends Xholon {
			private static final long serialVersionUID = 1687650292282068547L;

			public void protect() {
				IXholon[] selectedNodes = getCurrentlySelectedNodes();
				if (selectedNodes == null) {return;}
				for (int i = 0; i < selectedNodes.length; i++) {
					IXholon aRoot = selectedNodes[i];
					// go through the entire subtree of the selected node
					aRoot.visit(this);
				}
			}
			
			/**
			 * @see org.primordion.xholon.base.IXholon#visit(org.primordion.xholon.base.IXholon)
			 * @param visitee The node in the tree that is currently being visited.
			 */
			@SuppressWarnings("rawtypes")
			public boolean visit(IXholon visitee)
			{
				List portList = ReflectionFactory.instance().getAllPorts(visitee, true);
				for (int j = 0; j < portList.size(); j++) {
					PortInformation portInfo = (PortInformation)portList.get(j);
					if (portInfo.getReffedNode() == null) {
						if ("port".equals(portInfo.getFieldName())) {
							visitee.setPort(portInfo.getFieldNameIndex(), wiringServiceInstance);
						}
						// TODO handle non-port ports
					}
				}
				return true;
			}
			
		} // end of UnboundPortProtector inner class
		
		new UnboundPortProtector().protect();
	}
	
	/**
	 * Autowire an IXholon node to other nodes.
	 * @param node An IXholon node whose IXholon attributes will be autowired to other nodes.
	 * @param params A comma-separated sequence of parameters.
	 * If this is null or a blank String, then the default autowiring, if any, will be done.
	 */
	public void autowire(IXholon node, String params)
	{
		
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
	
	/**
	 * This method may be called by any IXholon whose port(s) is bound to the Wiring Service.
	 * @return false The port is bound to the Wiring Service only for protection.
	 * @see org.primordion.xholon.base.Xholon#isBound(org.primordion.xholon.base.IXholon)
	 */
	public boolean isBound(IXholon port)
	{
		return false;
	}
	
}
