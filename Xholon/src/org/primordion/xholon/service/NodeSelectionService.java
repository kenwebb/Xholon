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
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;

/**
 * Remember which IXholon node(s) are currently selected.
 * This will allow services or other xholons to act on those nodes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 9, 2009)
 */
public class NodeSelectionService extends AbstractXholonService {
	private static final long serialVersionUID = 3612796200967356375L;

	/**
	 * A request to remember one or more selected nodes.
	 * This replaces any previous collection of selected nodes.
	 */
	public static final int SIG_REMEMBER_SELECTED_NODES_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
	
	/**
	 * A request to remember one or more additional selected nodes.
	 * This appends the new node(s) to the end of any nodes that are currently in the collection of selected nodes.
	 */
	public static final int SIG_APPEND_SELECTED_NODES_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102;
	
	/**
	 * A request to clear the collection of selected nodes.
	 */
	public static final int SIG_CLEAR_SELECTED_NODES_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103;
	
	/**
	 * A request to get the current collection of selected nodes.
	 */
	public static final int SIG_GET_SELECTED_NODES_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105;
	
	/**
	 * A response to the request.
	 */
	public static final int SIG_GET_SELECTED_NODES_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 106;
	
	/**
	 * A request to remember the current node that was selected using MouseEvent.BUTTON3 (Right-Click).
	 * This replaces any previous node.
	 */
	public static final int SIG_REMEMBER_BUTTON3_NODE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 107;
	
	/**
	 * A request to get the currently remembered MouseEvent.BUTTON3 (Right-Click) node.
	 */
	public static final int SIG_GET_BUTTON3_NODE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 108;
	
	/**
	 * A response to a REMEMBER or other set request.
	 */
	public static final int SIG_REMEMER_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 109;
	
	/**
	 * A request to remember the current GridPanel that was selected using MouseEvent.BUTTON3 (Right-Click).
	 * This replaces any previous node.
	 */
	public static final int SIG_REMEMBER_BUTTON3_GRIDPANEL_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 110;
	
	/**
	 * A request to get the currently remembered MouseEvent.BUTTON3 (Right-Click) GridPanel.
	 */
	public static final int SIG_GET_BUTTON3_GRIDPANEL_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 111;
	
	/**
	 * This is the value of currentlySelectedNodes when there are no selected nodes,
	 * such as when the service first starts, and when it receives a SIG_CLEAR_SELECTED_NODES_REQ.
	 */
	private static final IXholon[] CLEARED_SELECTED_NODES = new IXholon[0];
	
	/**
	 * IXholons in the tree that are currently selected.
	 */
	private transient IXholon[] currentlySelectedNodes = CLEARED_SELECTED_NODES;
	
	/**
	 * Node in the tree that is currently selected using MouseEvent.BUTTON3 (Right-Click).
	 */
	private transient IXholon currentlySelectedButton3Node = null;
	
	/**
	 * Instance of GridPanel that is currently selected using MouseEvent.BUTTON3 (Right-Click).
	 */
	private transient IXholon currentlySelectedButton3GridPanel = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg)
	{
	  switch (msg.getSignal()) {
		case SIG_REMEMBER_SELECTED_NODES_REQ:
		  currentlySelectedNodes = (IXholon[])msg.getData();
		  break;
		case SIG_APPEND_SELECTED_NODES_REQ:
			IXholon[] newNodes = (IXholon[])msg.getData();
			IXholon[] combinedNodes = new IXholon[currentlySelectedNodes.length + newNodes.length];
			int i = 0;
			for (int j = 0; j < currentlySelectedNodes.length; j++) {
				combinedNodes[i++] = currentlySelectedNodes[j];
			}
			for (int k = 0; k < newNodes.length; k++) {
				combinedNodes[i++] = newNodes[k];
			}
			currentlySelectedNodes = combinedNodes;
			break;
		case SIG_REMEMBER_BUTTON3_NODE_REQ:
			currentlySelectedButton3Node = (IXholon)msg.getData();
			break;
		case SIG_CLEAR_SELECTED_NODES_REQ:
			currentlySelectedNodes = CLEARED_SELECTED_NODES;
			break;
		case SIG_REMEMBER_BUTTON3_GRIDPANEL_REQ:
			currentlySelectedButton3GridPanel = (IXholon)msg.getData();
			break;
		default:
			super.processReceivedMessage(msg);
		}
		
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case SIG_REMEMBER_BUTTON3_NODE_REQ:
			currentlySelectedButton3Node = (IXholon)msg.getData();
			return new Message(SIG_REMEMER_RESP, null, this, msg.getSender());
		case SIG_REMEMBER_BUTTON3_GRIDPANEL_REQ:
			currentlySelectedButton3GridPanel = (IXholon)msg.getData();
			return new Message(SIG_REMEMER_RESP, null, this, msg.getSender());
		case SIG_GET_SELECTED_NODES_REQ:
			return new Message(SIG_GET_SELECTED_NODES_RESP, currentlySelectedNodes, this, msg.getSender());
		case SIG_GET_BUTTON3_NODE_REQ:
			return new Message(SIG_GET_SELECTED_NODES_RESP, currentlySelectedButton3Node, this, msg.getSender());
		case SIG_GET_BUTTON3_GRIDPANEL_REQ:
			return new Message(SIG_GET_SELECTED_NODES_RESP, currentlySelectedButton3GridPanel, this, msg.getSender());
		default:
			return super.processReceivedSyncMessage(msg);
		}
		
	}
	
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
	private static final String showSelectedNodes       = "Show Selected Nodes";
	private static final String showSelectedButton3Node = "Show Context Menu Node";
	
	/** action list */
	private String[] actions = {
			showSelectedNodes, showSelectedButton3Node
			};
	
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
		println("NSS:" + action);
		if (action == null) {return;}
		if (showSelectedNodes.equals(action)) {
			if (currentlySelectedNodes == null) {
				println("NSS: 0");
				return;
			}
			println("NSS:" + currentlySelectedNodes.length);
			for (int i = 0; i < currentlySelectedNodes.length; i++) {
				println(currentlySelectedNodes[i]);
			}
		}
		else if (showSelectedButton3Node.equals(action)) {
				println(currentlySelectedButton3Node);
		}
	}
	
}
