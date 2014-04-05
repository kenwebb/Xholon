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

//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.net.Socket;
//import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;

import org.primordion.xholon.base.transferobject.MessageSenderReceiver;
import org.primordion.xholon.common.viewer.CeInteractions;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
//import org.primordion.xholon.io.ISwingEntity;
//import org.primordion.xholon.io.SwingEntity;
//import org.primordion.xholon.util.MiscIo;

/**
 * UML 2.0 Interaction.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on Nov 24, 2005)
 * TODO lifeline has been converted from a Set to a Vector (Dec. 4, 2006).
 * It now inefficiently has to call Vector.contains() to prevent duplicate entries in lifeline.
 * It should probably make use of Hashtable.
 */
public class Interaction extends Xholon implements IInteraction {
	private static final long serialVersionUID = -6943462433347730264L;
	
	protected Vector<IXholon> lifeline; // contains unique instances of IXholon
	protected Vector<IXholon> messageV; // contains instances of Message
	protected IQueue sentMsgQ; // contains messages that have been sent but not yet received
	protected static final int MAX_QSIZE = MAX_MESSAGES_STORED;  // maximum size of the sentMsgQ
	
	//protected int outputFormat = FORMAT_UML_GRAPH;
	//protected int outputFormat = FORMAT_SDEDIT; // default
	protected int outputFormat = FORMAT_SDEDIT_SOCKET;
	//protected int outputFormat = FORMAT_UML_GRAPH + FORMAT_SDEDIT + FORMAT_SDEDIT_SOCKET;
	
	protected String socketHost = "localhost";
	protected int socketPort = 60001;
	protected boolean showStates = false;
	protected boolean excludeSystemMessages = true;
	protected boolean excludeAdditionalSystemMessages = true;
	protected int maxNameLen = MAX_NAME_LEN;
	protected int maxDataLen = MAX_DATA_LEN;
	
	/**
	 * Provided/Required interface, for use with non-IPort ports/messages.
	 */
	protected IPortInterface providedRequiredInterface = null;
	
	protected static String outputDir = "./interaction/";
	
	/**
	 * Constructor.
	 */
	public Interaction()
	{
		lifeline = new Vector<IXholon>();
		messageV = new Vector<IXholon>();
		sentMsgQ = new QueueSynchronized(MAX_QSIZE);
		setId(CeInteractions.InteractionsCE);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhc()
	 */
	public IXholonClass getXhc()
	{
		if ((xhc == null) && (this.hasParentNode())) {
			// parent is View, whose parent is Application
			xhc = getParentNode().getParentNode().getClassNode(getId());
		}
		return xhc;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getXhcName()
	 */
	public String getXhcName()
	{
		//return getXhc().getName();
		return "Interaction_";
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		//getXhc(); // make sure the XholonClass has been set
		//return super.getName();
		return "Interaction_" + getId();
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setOutputFormat(int)
	 */
	public void setOutputFormat(int outputFormat)
	{
		this.outputFormat = outputFormat;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setSocketHost(java.lang.String)
	 */
	public void setSocketHost(String socketHost)
	{
		this.socketHost = socketHost;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setSocketPort(int)
	 */
	public void setSocketPort(int socketPort)
	{
		this.socketPort = socketPort;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setShowStates(boolean)
	 */
	public void setShowStates(boolean showStates)
	{
		this.showStates = showStates;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setExcludeSystemMessages(boolean)
	 */
	public void setExcludeSystemMessages(boolean excludeSystemMessages)
	{
		this.excludeSystemMessages = excludeSystemMessages;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setExcludeAdditionalSystemMessages(boolean)
	 */
	public void setExcludeAdditionalSystemMessages(boolean excludeAdditionalSystemMessages)
	{
		this.excludeAdditionalSystemMessages = excludeAdditionalSystemMessages;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setMaxNameLen(int)
	 */
	public void setMaxNameLen(int maxNameLen)
	{
		this.maxNameLen = maxNameLen;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setMaxDataLen(int)
	 */
	public void setMaxDataLen(int maxDataLen)
	{
		this.maxDataLen = maxDataLen;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#setProvidedRequiredInterface(org.primordion.xholon.base.IPortInterface)
	 */
	public void setProvidedRequiredInterface(IPortInterface providedRequiredInterface)
	{
		this.providedRequiredInterface = providedRequiredInterface;
	}

	/*
	 * @see org.primordion.xholon.base.IInteraction#getOutputFormat()
	 */
	public int getOutputFormat()
	{
		return outputFormat;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getSocketHost()
	 */
	public String getSocketHost()
	{
		return socketHost;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getSocketPort()
	 */
	public int getSocketPort()
	{
		return socketPort;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getShowStates()
	 */
	public boolean getShowStates()
	{
		return showStates;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getExcludeSystemMessages()
	 */
	public boolean getExcludeSystemMessages()
	{
		return excludeSystemMessages;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getExcludeAdditionalSystemMessages()
	 */
	public boolean getExcludeAdditionalSystemMessages()
	{
		return excludeAdditionalSystemMessages;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getMaxNameLen()
	 */
	public int getMaxNameLen()
	{
		return maxNameLen;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getMaxDataLen()
	 */
	public int getMaxDataLen()
	{
		return maxDataLen;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#getProvidedRequiredInterface()
	 */
	public IPortInterface getProvidedRequiredInterface()
	{
		return providedRequiredInterface;
	}

	/*
	 *
	 * @see org.primordion.xholon.base.IInteraction#addMessage(org.primordion.xholon.base.Message)
	 */
	public void addMessage(IMessage msg)
	{
		if (msg == null) {return;}
		if (excludeSystemMessages && isSystemMessage(msg.getSignal())) {return;}
		if (messageV.size() <= MAX_MESSAGES_STORED) {
			if (lifeline.size() >= MAX_LIFELINES_STORED) {
				if ((lifeline.contains(msg.getSender())) && (lifeline.contains(msg.getReceiver()))) {
					saveMessage(msg);
				}
			}
			else {
				saveMessage(msg);
				if ((msg.getSender().getClass() != Port.class)
						&& (!lifeline.contains(msg.getSender()))) {
					lifeline.addElement(msg.getSender());
					addInitialState(msg.getSender());
				}
				if ((msg.getReceiver().getClass() != Port.class)
						&& (!lifeline.contains(msg.getReceiver()))) {
					lifeline.addElement(msg.getReceiver());
					addInitialState(msg.getReceiver());
				}
			}
		}
	}
	
	/*
	 *
	 * @see org.primordion.xholon.base.IInteraction#addMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon, int)
	 */
	public void addMessage(int signal, Object data, IXholon sender, IXholon receiver, int index)
	{
		if (excludeSystemMessages && isSystemMessage(signal)) {return;}
		if (messageV.size() <= MAX_MESSAGES_STORED) {
			if (lifeline.size() >= MAX_LIFELINES_STORED) {
				if ((lifeline.contains(sender)) && (lifeline.contains(receiver))) {
					saveMessage(signal, data, sender, receiver, index);
				}
			}
			else {
				saveMessage(signal, data, sender, receiver, index);
				if ((sender.getClass() != Port.class)
						&& (!lifeline.contains(sender))) {
					lifeline.addElement(sender);
					addInitialState(sender);
				}
				if ((receiver.getClass() != Port.class)
						&& (!lifeline.contains(receiver))) {
					lifeline.addElement(receiver);
					addInitialState(receiver);
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#addSyncMessage(org.primordion.xholon.base.Message)
	 */
	public void addSyncMessage(IMessage msg)
	{
		addMessage(msg);
		processReceivedMessage(msg);
	}
	
	/**
	 * Save a copy of the message in an internal data structure.
	 * @param msg The original message.
	 */
	protected void saveMessage(IMessage msg)
	{
		// if the sender is a Port, then this must be the remote end and this msg has already been saved
		if ((msg.getSender() != null) && (msg.getSender().getClass() != Port.class)) {
			IMessage saveMsg = new Message(msg);
			if (saveMsg.getReceiver().getClass() == Port.class) {
				// change receiver from the local Port to the remote Xholon
				saveMsg.setReceiver(((IPort)saveMsg.getReceiver()).getLink(saveMsg.getIndex()));
				if (saveMsg.getReceiver().getClass() == Port.class) {
					// receiver should be the port rather than a port replication
					if (saveMsg.getReceiver().getParentNode().getClass() == Port.class) {
						saveMsg.setReceiver(saveMsg.getReceiver().getParentNode());
					}
				}
			}
			if (sentMsgQ.enqueue(saveMsg) == IQueue.Q_FULL) {
				// TODO handle Q_FULL
			}
		}
	}
	
	/**
	 * Save a copy of the message in an internal data structure.
	 * @param signal A signal that identifies the type of message.
	 * @param data Optional data carried by the message.
	 * @param sender The sender of the message.
	 * @param receiver The intended receiver of the message.
	 * @param index A port replication index.
	 */
	protected void saveMessage(int signal, Object data, IXholon sender, IXholon receiver, int index)
	{
		// if the sender is a Port, then this must be the remote end and this msg has already been saved
		if ((sender != null) && (sender.getClass() != Port.class)) {
			IMessage saveMsg = new Message(signal, data, sender, receiver, index);
			if (saveMsg.getReceiver().getClass() == Port.class) {
				// change receiver from the local Port to the remote Xholon
				saveMsg.setReceiver(((IPort)saveMsg.getReceiver()).getLink(saveMsg.getIndex()));
				if (saveMsg.getReceiver().getClass() == Port.class) {
					// receiver should be the port rather than a port replication
					if (saveMsg.getReceiver().getParentNode().getClass() == Port.class) {
						saveMsg.setReceiver(saveMsg.getReceiver().getParentNode());
					}
				}
			}
			if (sentMsgQ.enqueue(saveMsg) == IQueue.Q_FULL) {
				// TODO handle Q_FULL
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#addState(org.primordion.xholon.base.IStateMachineEntity)
	 */
	public void addState(IStateMachineEntity state)
	{
		if (showStates) {
			messageV.addElement(state);
		}
	}
	
	/**
	 * Add the initial state to a lifeline, if the xholon owns a state machine.
	 * @param smOwner The xholon that owns (or may own) a state machine.
	 */
	protected void addInitialState(IXholon smOwner)
	{
		if (showStates) {
			// find a state machine node, if one exists
			IXholon node = smOwner.getFirstChild();
			while (node != null) {
				if (node.getXhcId() == org.primordion.xholon.common.mechanism.CeStateMachineEntity.StateMachineCE) {
					((IStateMachineEntity)node).notifyActiveSubStates();
					break;
				}
				node = node.getNextSibling();
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		if (msg == null) {return;}
		if (excludeSystemMessages && isSystemMessage(msg.getSignal())) {return;}
		// TODO more completely compare the msg argument with the msg from the Q to make sure thay are the same
		IMessage msgFromQ = (IMessage)sentMsgQ.dequeue();
		if (msgFromQ == null) {
			// TODO handle this condition
			//System.out.println("Interaction processReceivedMessage: sentMsgQ is empty");
			return;
		}
		if(msgFromQ.getSignal() == msg.getSignal()) {
			messageV.addElement(msgFromQ);
		}
		else {
			logger.error("Interaction processReceivedMessage: can't match " + msg + " with " + msgFromQ);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IInteraction#createSequenceDiagram(java.lang.String)
	 */
	public void createSequenceDiagram(String modelName)
	{
		if ((outputFormat & FORMAT_UML_GRAPH) == FORMAT_UML_GRAPH) {
			createSdUmlGraph(modelName);
		}
		if ((outputFormat & FORMAT_SDEDIT) == FORMAT_SDEDIT) {
			createSdSdedit(modelName, false);
		}
		if ((outputFormat & FORMAT_SDEDIT_SOCKET) == FORMAT_SDEDIT_SOCKET) {
			boolean succeeded = createSdSdedit(modelName, true);
			if (succeeded == false) {
				logger.error("Unable to create socket connection to sdedit server host:"
					+ socketHost + " port:" + new Integer(socketPort));
				logger.info("Start the version of sdedit in Xholon/lib,"
					+ " select its 'File --> Start/stop RT server' menu option, and try again.");
				if (outputFormat == FORMAT_SDEDIT_SOCKET) { // only do this if not already writing to file
					createSdSdedit(modelName, false);
				}
			}
		}
		if ((outputFormat & FORMAT_ROSE) == FORMAT_ROSE) {
			createSdRose(modelName);
		}
		if ((outputFormat & FORMAT_PLANTUML) == FORMAT_PLANTUML) {
			createSdPlantUml(modelName);
		}
		//FORMAT_WEBSD
		if ((outputFormat & FORMAT_WEBSD) == FORMAT_WEBSD) {
			createSdWebSd(modelName);
		}
	}
	
	/**
	 * Create a sequence diagram using Quick Sequence Diagram Editor (sdedit).
	 * @param modelName The name of this model.
	 */
	protected boolean createSdSdedit(String modelName, boolean useSocket)
	{
		boolean rc = false;
		int nextNum = 0; // for use when outputting states
		IXholon xhObj = null;
		int i;
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		// create any missing output directories
		/*File dirOut = new File(outputDir);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer out = null;
		Socket socket = null;*/
		//try {
			/*if (useSocket) {
				socket = new Socket(socketHost, socketPort);
			    out = new PrintWriter(socket.getOutputStream(), true);
			    sb.append(modelName + "\n");
			}
			else { // FORMAT_SDEDIT
				out = MiscIo.openOutputFile(outputDir + "seq" + now.getTime() + ".sd");
			}
			if (out == null) {return rc;}*/
			sb.append( "# Model: " + modelName );
			sb.append( "\n# Created by: Xholon http://www.primordion.com/Xholon/" );
			sb.append( "\n# Date:  " + now.getTime() + " " + now + "\n");
			sb.append( "\n# Define the objects\n" );
			// ex: hello:Hello[ap]
			for (i = 0; i <lifeline.size(); i++) {
				xhObj = (IXholon)lifeline.elementAt(i);
				sb.append( getLifelineName(xhObj) );
				sb.append( ":" );
				sb.append( crop(xhObj.getXhcName(), maxNameLen) );
				sb.append( "[ap]" );
				if (xhObj.getRoleName() != null) {
					sb.append(" \"" + xhObj.getRoleName() + "_" + xhObj.getId() + "\"");
				}
				sb.append( "\n" );
			}
			
			sb.append( "\n# Message sequences\n" );
			// ex: hello:world.SIGNAL_ONE(abc)
			for (i = 0; i < messageV.size(); i++) {
				IXholon xhNode = (IXholon)messageV.elementAt(i);
				// handle states
				if (xhNode.getXhType() == IXholonClass.XhtypeStateMachineEntityActive) {
					IStateMachineEntity state = (IStateMachineEntity)xhNode;
					sb.append("*" + nextNum + " "+ getLifelineName(state.getOwningXholon()) + "\n");
					sb.append(state.getRoleName() + "\n");
					sb.append("*" + nextNum + "\n");
					nextNum++;
					continue;
				}
				
				// handle messages
				IMessage msg = (IMessage)xhNode;
				// sender
				if (msg.getSender().getClass() == Port.class) {
					sb.append( getLifelineName(msg.getSender().getParentNode()) + ":" );
				}
				else {
					sb.append( getLifelineName(msg.getSender()) + ":" );
				}
				// receiver
				if (msg.getReceiver().getClass() == Port.class) {
					sb.append( getLifelineName(msg.getReceiver().getParentNode()) + "." );
				}
				else {
					sb.append( getLifelineName(msg.getReceiver()) + "." );
				}
				// signal, data
				if (msg.getSender().getClass() == Port.class) {
					IPort sender = (IPort)msg.getSender();
					if (sender.getIsConjugated()) {
						sb.append( sender.getRequiredInterface().getSignalName(msg.getSignal()) + "(" );
					}
					else {
						sb.append( sender.getProvidedInterface().getSignalName(msg.getSignal()) + "(" );
					}
					if (msg.getData() != null) {
						if (msg.getData().getClass() == String.class) {
							sb.append( crop( (String)msg.getData(), maxDataLen ) );
						}
						else {
							sb.append( crop(msg.getData().toString(), maxDataLen) );
						}
					}
					sb.append( ")\n" );
				}
				else if (msg.getReceiver().getClass() == Port.class) {
					IPort receiver = (IPort)msg.getReceiver();
					if (receiver.getIsConjugated()) {
						sb.append( receiver.getRequiredInterface().getSignalName(msg.getSignal()) + "(" );
					}
					else {
						sb.append( receiver.getProvidedInterface().getSignalName(msg.getSignal()) + "(" );
					}
					if (msg.getData() != null) {
						if (msg.getData().getClass() == String.class) {
							sb.append( crop( (String)msg.getData(), maxDataLen ) );
						}
						else {
							sb.append( crop(msg.getData().toString(), maxDataLen) );
						}
					}
					sb.append( ")\n" );
				}
				else {
					if (isSwingEntityMessage(msg.getSignal())) {
						//sb.append( SwingEntity.getSignalName(msg.getSignal()) + "(" );
					}
					else if (providedRequiredInterface == null) {
						sb.append( msg.getSignal ()+ "(" );
					}
					else {
						sb.append(providedRequiredInterface.getSignalName(msg.getSignal()) + "(" );
					}
					if (msg.getData() != null) {
						if (msg.getData().getClass() == String.class) {
							sb.append( crop( (String)msg.getData(), maxDataLen ) );
						}
						else {
							sb.append( crop(msg.getData().toString(), maxDataLen) );
						}
					}
					sb.append( ")\n" );
				}
				XholonGwtTabPanelHelper.addTab(sb.toString(), "sd", "seq" + now.getTime() + ".sd", false);
			}
			
			/*if (useSocket) {
				sb.append("end\n");
				out.write(sb.toString());
				out.close();
				socket.close();
			}
			else { // FORMAT_SDEDIT
				out.write(sb.toString());
				MiscIo.closeOutputFile( out );
			}*/
			rc = true; // successful
		/*} catch (UnknownHostException e) {
			logger.error("Unable to create socket connection to " + socketHost + " using port " + socketPort, e);
		} catch (IOException e) {
			logger.error("Unable to write to " + out, e);
		}*/
		return rc;
	}
	
	/**
	 * Create a sequence diagram using UMLGraph.
	 * The Windows version of pic2plot requires that lines are terminated by a LF rather than by CRLF.
	 * Therefore, each line of the output file will be terminated by LF whether it's running in Windows or Linux.
	 * @param modelName The name of this model.
	 */
	protected void createSdUmlGraph(String modelName)
	{
		IXholon xhObj = null;
		Message msg = null;
		int i;
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append( ".PS\012" );
		sb.append( "copy \"sequence.pic\";\012" );
		sb.append( "\012# Model: " + modelName );
		sb.append( "\012# Created by: Xholon http://www.primordion.com/Xholon/" );
		sb.append( "\012# Date:  " + now.getTime() + " " + now + "\012");
		sb.append( "\012# Define the objects\012" );
		// ex: sb.append( "object(O,\"o:Toolkit\");" );
		for (i = 0; i <lifeline.size(); i++) {
			sb.append( "object(" );
			xhObj = (IXholon)lifeline.elementAt(i);
			sb.append( "Xh" + xhObj.getId() + ",\"" );
			if (xhObj.getRoleName() != null) {
				sb.append(xhObj.getRoleName());
			}
			sb.append( ":" );
			sb.append( crop(xhObj.getXhcName(), maxNameLen) );
			sb.append( "\");\012" );
		}
		sb.append( "step();\012" );
		sb.append( "\012# Message sequences\012" );
		sb.append( "async();\012" ); // assume that all messages are asynchronous
		// ex: message(O,P,"handleExpose()");
		for (i = 0; i < messageV.size(); i++) {
			IXholon xhNode = (IXholon)messageV.elementAt(i);
			
			// handle states
			if (xhNode.getXhType() == IXholonClass.XhtypeStateMachineEntityActive) {
				// ignore
				continue;
			}
			
			// handle messages
			sb.append( "message(" );
			msg = (Message)xhNode;

			// sender
			if (msg.sender.getClass() == Port.class) {
				sb.append( "Xh" + msg.sender.getParentNode().getId() + "," );
			}
			else {
				sb.append( "Xh" + msg.sender.getId() + "," );
			}
			// receiver
			if (msg.receiver.getClass() == Port.class) {
				sb.append( "Xh" + msg.receiver.getParentNode().getId() + "," );
			}
			else {
				sb.append( "Xh" + msg.receiver.getId() + "," );
			}
			// signal, data
			if (msg.sender.getClass() == Port.class) {
				IPort sender = (IPort)msg.sender;
				if (sender.getIsConjugated()) {
					sb.append( "\"" + sender.getRequiredInterface().getSignalName(msg.signal) + "(" );
				}
				else {
					sb.append( "\"" + sender.getProvidedInterface().getSignalName(msg.signal) + "(" );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				sb.append( ")\");\012" );
			}
			else if (msg.receiver.getClass() == Port.class) {
				IPort receiver = (IPort)msg.receiver;
				if (receiver.getIsConjugated()) {
					sb.append( "\"" + receiver.getRequiredInterface().getSignalName(msg.signal) + "(" );
				}
				else {
					sb.append( "\"" + receiver.getProvidedInterface().getSignalName(msg.signal) + "(" );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				sb.append( ")\");\012" );
			}
			else {
				if (isSwingEntityMessage(msg.signal)) {
					//sb.append("\"" + SwingEntity.getSignalName(msg.signal) + "(");
				}
				else if (providedRequiredInterface == null) {
					sb.append("\"" + msg.signal + "(");
				}
				else {
					sb.append("\"" + providedRequiredInterface.getSignalName(msg.signal) + "(" );
				}
				
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				sb.append( ")\");\012" );
			}
		}
		sb.append( "\012# Complete the lifelines\012" );
		sb.append( "step();\012" );
		for (i = 0; i < lifeline.size(); i++) {
			sb.append( "complete(" );
			xhObj = (IXholon)lifeline.elementAt(i);
			sb.append( "Xh" + xhObj.getId() );
			sb.append( ");\012" );
		}			
		sb.append( ".PE\012" );
		
		XholonGwtTabPanelHelper.addTab(sb.toString(), "sd", "seq" + now.getTime() + ".pic", false);
		
		// create any missing output directories
		/*File dirOut = new File(outputDir);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer out = MiscIo.openOutputFile(outputDir + "seq" + now.getTime() + ".pic");
		if (out == null) {return;}
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			logger.error("Unable to write to " + out, e);
		}
		MiscIo.closeOutputFile( out );*/
	}
	
	/**
	 * Create a sequence diagram in Rational Rose ebs script format.
	 * @param modelName The name of this model.
	 */
	protected void createSdRose(String modelName)
	{
		IXholon xhObj = null;
		Message msg = null;
		int i;
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append("' Model: " + modelName);
		sb.append("\n' Created by: Xholon http://www.primordion.com/Xholon/");
		sb.append("\n' Date:  " + now.getTime() + " " + now);
		sb.append("\n'");
		sb.append("\n' To use:");
		sb.append("\n' - Select an existing use case by clicking on it in the browser.");
		sb.append("\n' - Run this script.");
		sb.append("\n");
		sb.append("\n' A Scenario Diagram can be either a Sequence Diagram or a Collaboration Diagram.");
		sb.append("\nConst SEQUENCE_DIAGRAM      = 1");
		sb.append("\nConst COLLABORATION_DIAGRAM = 2");
		sb.append("\n");
		sb.append("\n' A message might have either Simple or Return synchronization.");
		sb.append("\nConst SIMPLE_SYNCH = 0 ' the default");
		sb.append("\nConst RETURN_SYNCH = 6");
		sb.append("\n");
		sb.append("\nDim sNum As Integer");
		sb.append("\n");
		sb.append("\n' Get the next sequence number, for messages in the diagram.");
		sb.append("\nFunction GetSNum() As Integer");
		sb.append("\n  sNum = sNum + 1");
		sb.append("\n  GetSNum = sNum");
		sb.append("\nEnd Function");
		sb.append("\n");
		sb.append("\n' Create a Scenario Diagram");
		sb.append("\nSub CreateScenarioDiagram (theUseCase As UseCase, sdName As String)");
		sb.append("\n  Dim theSd As ScenarioDiagram");
		sb.append("\n  Set theSd = theUseCase.AddScenarioDiagram(sdName, SEQUENCE_DIAGRAM)");
		sb.append("\n");
		
		sb.append("\n  ' Define the objects\n");
		// ex: Set theInstance = theSd.AddInstance("InstanceName", "")
		for (i = 0; i <lifeline.size(); i++) {
			sb.append("  Set ");
			xhObj = (IXholon)lifeline.elementAt(i);
			sb.append("xh" + xhObj.getId());
			//if (xhObj.getRoleName() != null) {
			//	sb.append(xhObj.getRoleName());
			//}
			sb.append( " = theSd.AddInstance(\"" );
			sb.append( crop(xhObj.getXhcName(), maxNameLen) );
			sb.append( "\", \"\")\n" );
		}
		sb.append( "\n  ' Message sequences\n" );
		// ex: Set aMsg = theSd.CreateMessage("a message", instX, instY, GetSNum())
		for (i = 0; i < messageV.size(); i++) {
			IXholon xhNode = (IXholon)messageV.elementAt(i);
			
			// handle states
			if (xhNode.getXhType() == IXholonClass.XhtypeStateMachineEntityActive) {
				// ignore
				continue;
			}
			
			// handle messages
			sb.append( "  Set aMsg = theSd.CreateMessage(" );
			msg = (Message)xhNode;

			// signal, data
			if (msg.sender.getClass() == Port.class) {
				IPort sender = (IPort)msg.sender;
				if (sender.getIsConjugated()) {
					sb.append( "\"" + sender.getRequiredInterface().getSignalName(msg.signal) + "(" );
				}
				else {
					sb.append( "\"" + sender.getProvidedInterface().getSignalName(msg.signal) + "(" );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) + ")\", " );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) + ")\", " );
					}
				}
				else {
					sb.append(")\", ");
				}
			}
			else if (msg.receiver.getClass() == Port.class) {
				IPort receiver = (IPort)msg.receiver;
				if (receiver.getIsConjugated()) {
					sb.append( "\"" + receiver.getRequiredInterface().getSignalName(msg.signal) + "(" );
				}
				else {
					sb.append( "\"" + receiver.getProvidedInterface().getSignalName(msg.signal) + "(" );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) + ")\", " );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) + ")\", " );
					}
				}
				else {
					sb.append(")\", ");
				}
			}
			else {
				if (isSwingEntityMessage(msg.signal)) {
					//sb.append("\"" + SwingEntity.getSignalName(msg.signal) + "(");
				}
				else if (providedRequiredInterface == null) {
					sb.append("\"" + msg.signal + "(");
				}
				else {
					sb.append("\"" + providedRequiredInterface.getSignalName(msg.signal) + "(" );
				}
				
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) + ")\", " );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) + ")\", " );
					}
				}
				else {
					sb.append(")\", ");
				}
			}
			// sender
			if (msg.sender.getClass() == Port.class) {
				sb.append( "xh" + msg.sender.getParentNode().getId() + ", " );
			}
			else {
				sb.append( "xh" + msg.sender.getId() + ", " );
			}
			// receiver
			if (msg.receiver.getClass() == Port.class) {
				sb.append( "xh" + msg.receiver.getParentNode().getId() + ", " );
			}
			else {
				sb.append( "xh" + msg.receiver.getId() + ", " );
			}
			sb.append( "GetSNum())\n" );
		}
		sb.append("End Sub\n");
		sb.append("\n");
		sb.append("' Main\n");
		sb.append("Sub Main\n");
		sb.append("  #IF UNIX Then\n");
		sb.append("    \n");
		sb.append("  #Else\n");
		sb.append("    'ViewportOpen\n");
		sb.append("  #End If\n");
		sb.append("  Dim theModel As Model\n");
		sb.append("  Dim selectedUseCases As UseCaseCollection\n");
		sb.append("  Dim aUseCase As UseCase\n");
		sb.append("  sNum = 0\n");
		sb.append("  Set theModel = RoseApp.CurrentModel\n");
		sb.append("  Set selectedUseCases = theModel.GetSelectedUseCases()\n");
		sb.append("  For i = 1 To selectedUseCases.Count\n");
		sb.append("    Set aUseCase = selectedUseCases.GetAt(i)\n");
		sb.append("  Next i\n");
		sb.append("  Dim diagramName As String\n");
		sb.append("  diagramName = \"" + modelName + "\"\n");
		sb.append("  CreateScenarioDiagram aUseCase, diagramName\n");
		sb.append("End Sub\n");
		
		XholonGwtTabPanelHelper.addTab(sb.toString(), "sd", "seq" + now.getTime() + ".ebs", false);
		
		// create any missing output directories
		/*File dirOut = new File(outputDir);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer out = MiscIo.openOutputFile(outputDir + "seq" + now.getTime() + ".ebs");
		if (out == null) {return;}
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			logger.error("Unable to write to " + out, e);
		}
		MiscIo.closeOutputFile( out );*/
	}
	
	/**
	 * Create a sequence diagram in PlantUML format.
	 * @param modelName The name of this model.
	 */
	protected void createSdPlantUml(String modelName)
	{
		//IXholon xhObj = null;
		Message msg = null;
		int i;
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append("' Model: " + modelName + "\n")
		.append("' Created by: Xholon http://www.primordion.com/Xholon/\n")
		.append("' Date:  " + now.getTime() + " " + now + "\n\n")
		.append("' To view this file, use PlantUML from plantuml.sourceforge.net\n")
    .append("' See also: www.plantuml.com/plantuml\n")
    .append("' See also: www.planttext.com\n")
    .append("' See also: seeduml.com\n\n")
		.append("@startuml ./seq" + now.getTime() + ".png\n")
		.append("' Message sequences\n");
		for (i = 0; i < messageV.size(); i++) {
			IXholon xhNode = (IXholon)messageV.elementAt(i);
			
			// handle states
			if (xhNode.getXhType() == IXholonClass.XhtypeStateMachineEntityActive) {
				// ignore
				continue;
			}
			
			// handle messages
			msg = (Message)xhNode;
			
			// sender
			if (msg.sender.getClass() == Port.class) {
				sb.append( getLifelineName(msg.sender.getParentNode()) );
			}
			else {
				sb.append( getLifelineName(msg.sender) );
			}
			
			sb.append(" -> ");
			
			// receiver
			if (msg.receiver.getClass() == Port.class) {
				sb.append( getLifelineName(msg.receiver.getParentNode()) );
			}
			else {
				sb.append( getLifelineName(msg.receiver) );
			}
			
			// signal, data
			sb.append(" : ");
			if (msg.sender.getClass() == Port.class) {
				IPort sender = (IPort)msg.sender;
				if (sender.getIsConjugated()) {
					sb.append( sender.getRequiredInterface().getSignalName(msg.signal) );
				}
				else {
					sb.append( sender.getProvidedInterface().getSignalName(msg.signal) );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			else if (msg.receiver.getClass() == Port.class) {
				IPort receiver = (IPort)msg.receiver;
				if (receiver.getIsConjugated()) {
					sb.append( receiver.getRequiredInterface().getSignalName(msg.signal) );
				}
				else {
					sb.append( receiver.getProvidedInterface().getSignalName(msg.signal) );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			else {
				if (isSwingEntityMessage(msg.signal)) {
					//sb.append(SwingEntity.getSignalName(msg.signal));
				}
				else if (providedRequiredInterface == null) {
					sb.append("" + msg.signal);
				}
				else {
					sb.append(providedRequiredInterface.getSignalName(msg.signal) );
				}
				
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			
			sb.append( "\n" );
		}
		sb.append("@enduml\n");
		
		XholonGwtTabPanelHelper.addTab(sb.toString(), "sd", "seq" + now.getTime() + ".txt", false);
		
		// create any missing output directories
		/*File dirOut = new File(outputDir);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer out = MiscIo.openOutputFile(outputDir + "seq" + now.getTime() + ".txt");
		if (out == null) {return;}
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			logger.error("Unable to write to " + out, e);
		}
		MiscIo.closeOutputFile(out);*/
	}
	
	/**
	 * Create a sequence diagram in websequencediagram format.
	 * @param modelName The name of this model.
	 * @see <a href="http://www.websequencediagrams.com/">websequencediagrams site</a>
	 */
	protected void createSdWebSd(String modelName)
	{
	  Message msg = null;
		int i;
		Date now = new Date();
	  StringBuilder sb = new StringBuilder();
		sb.append("title " + modelName + "\n");
		sb.append("# To view sequence diagram, paste this text into http://www.websequencediagrams.com/\n");
		sb.append("# Created by: Xholon http://www.primordion.com/Xholon/\n");
		sb.append("# Date:  " + now.getTime() + " " + now + "\n");
		
		for (i = 0; i < messageV.size(); i++) {
			IXholon xhNode = (IXholon)messageV.elementAt(i);
			
			// handle states
			if (xhNode.getXhType() == IXholonClass.XhtypeStateMachineEntityActive) {
				// ignore
				continue;
			}
			
			// handle messages
			msg = (Message)xhNode;
			
			// sender
			if (msg.sender.getClass() == Port.class) {
				sb.append( getLifelineName(msg.sender.getParentNode()) );
			}
			else {
				sb.append( getLifelineName(msg.sender) );
			}
			
			sb.append("->");
			
			// receiver
			if (msg.receiver.getClass() == Port.class) {
				sb.append( getLifelineName(msg.receiver.getParentNode()) );
			}
			else {
				sb.append( getLifelineName(msg.receiver) );
			}
			
			// signal, data
			sb.append(": ");
			if (msg.sender.getClass() == Port.class) {
				IPort sender = (IPort)msg.sender;
				if (sender.getIsConjugated()) {
					sb.append( sender.getRequiredInterface().getSignalName(msg.signal) );
				}
				else {
					sb.append( sender.getProvidedInterface().getSignalName(msg.signal) );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			else if (msg.receiver.getClass() == Port.class) {
				IPort receiver = (IPort)msg.receiver;
				if (receiver.getIsConjugated()) {
					sb.append( receiver.getRequiredInterface().getSignalName(msg.signal) );
				}
				else {
					sb.append( receiver.getProvidedInterface().getSignalName(msg.signal) );
				}
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			else {
				if (isSwingEntityMessage(msg.signal)) {
					//sb.append(SwingEntity.getSignalName(msg.signal));
				}
				else if (providedRequiredInterface == null) {
					sb.append("" + msg.signal);
				}
				else {
					sb.append(providedRequiredInterface.getSignalName(msg.signal) );
				}
				
				if (msg.data != null) {
					if (msg.data.getClass() == String.class) {
						sb.append( crop( (String)msg.data, maxDataLen ) );
					}
					else {
						sb.append( crop(msg.data.toString(), maxDataLen) );
					}
				}
				else {
					//sb.append("");
				}
			}
			sb.append( "\n" );
		}
		//System.out.println(sb.toString());
		//showWebSd(sb.toString());
		XholonGwtTabPanelHelper.addTab(sb.toString(), "sd", "sequencediagram.txt", false);
	}
	
	//protected void showWebSd(String text) {
	//  
	//}
	
	/**
	 * Get the name of this Xholon lifeline object.
	 * @param xhObj A Xholon lifeline object.
	 * @return A unique name.
	 */
	protected String getLifelineName(IXholon xhObj)
	{
		String nameBuffer;
		if (xhObj == null) {
			// this can be caused by transition to PseudostateTerminte which destroys the owning xholon
			nameBuffer = "unknownClassName_";
		}
		else if (xhObj instanceof MessageSenderReceiver) {
			String cName = xhObj.getXhcName();
			if (cName == null || cName.length() == 0) {
				nameBuffer = "unknownClassName_";
			}
			else {
				nameBuffer = cName.substring(0,1).toLowerCase() + cName.substring(1) + "_";
			}
			if (xhObj.getId() < 0) {
				nameBuffer += "0";
			}
			else {
				nameBuffer += xhObj.getId();
			}
		}
		else if (xhObj.getXhc() == null) {
			nameBuffer = "unknownClassName_" + xhObj.getId();
		}
		else {
			String cName = xhObj.getXhcName();
			nameBuffer = cName.substring(0,1).toLowerCase() + cName.substring(1) + "_" + xhObj.getId();
			// Convention: first letter of enities are lower case, classes upper case
		}
		return nameBuffer;
	}
	
	/**
	 * Crop a string to a specified maximum length.
	 * @param inStr The string to crop.
	 * @param maxLen The desired maximum length of the returned string.
	 * @return The cropped string.
	 */
	protected String crop(String inStr, int maxLen) {
		if (inStr == null) {return "";}
		if (inStr.length() > maxLen) {
			return inStr.substring(0,maxLen);
		}
		else {
			return inStr;
		}
	}
	
	/**
	 * Does the specified signal ID indicate that this is a system level message?
	 * @param signal A signal ID.
	 * @return true or false
	 */
	protected boolean isSystemMessage(int signal)
	{
		if ((signal <= ISignal.SIGNAL_MAX_SYSTEM) && (signal >= ISignal.SIGNAL_MIN_SYSTEM)) {
			return true;
		}
		// determine whether additional system messages should be treated like regular system messages
		else if (excludeAdditionalSystemMessages
				&& (signal <= ISignal.SIGNAL_MAX_SYSTEM_ADDITIONAL)
				&& (signal >= ISignal.SIGNAL_MIN_SYSTEM_ADDITIONAL)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Does the specified signal ID indicate that this is a SwingEntity message?
	 * @param signal A signal ID.
	 * @return true or false
	 */
	protected boolean isSwingEntityMessage(int signal)
	{
		//if ((signal <= ISignal.SWIG_START) && (signal >= ISwingEntity.SWIG_END)) {
		//	return true;
		//}
		//else {
			return false;
		//}
	}
	
}
