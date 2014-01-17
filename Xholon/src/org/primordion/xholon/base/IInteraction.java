/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

/**
 * UML 2.0 Interaction.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on April 16, 2007)
 * TODO lifeline has been converted from a Set to a Vector (Dec. 4, 2006).
 * It now inefficiently has to call Vector.contains() to prevent duplicate entries in lifeline.
 * It should probably make use of Hashtable.
 */
public interface IInteraction extends IXholon {

	// if intension is to create a graphical Sequence Diagram, then use: 100, 20, 8, 20
	
	/** Max number of messages stored, */
	public static final int MAX_MESSAGES_STORED = 500;
	
	/** Max number of objects to keep track of. */
	public static final int MAX_LIFELINES_STORED = 100;

	/** Default max length of a class or role name to output. */
	public static final int MAX_NAME_LEN = 15;
	
	/** Default max length of msg.data contents to output. */
	public static final int MAX_DATA_LEN = 20;

	// Output formats for Sequence Diagrams
	public static final int FORMAT_NONE          = 0; // none
	public static final int FORMAT_UML_GRAPH     = 1; // UMLGraph pic format
	public static final int FORMAT_SDEDIT        = 2; // Quick Sequence Diagram Editor (sdedit)
	public static final int FORMAT_SDEDIT_SOCKET = 4; // Quick Sequence Diagram Editor (sdedit) socket
	public static final int FORMAT_ROSE          = 8; // Rational Rose ebs script
	public static final int FORMAT_PLANTUML      = 16; // PlantUML
	public static final int FORMAT_WEBSD         = 32; // http://www.websequencediagrams.com/

	/**
	 * Set the output format.
	 * @param outputFormat One of FORMAT_UML_GRAPH, FORMAT_SDEDIT, FORMAT_SDEDIT_SOCKET.
	 */
	public void setOutputFormat(int outputFormat);
	
	/**
	 * Set the host for a socket connection, if using sdedit as a sequence diagram server.
	 * @param socketHost A valid socket host (ex: localhost).
	 */
	public void setSocketHost(String socketHost);
	
	/**
	 * Set the port for a socket connection, if using sdedit as a sequence diagram server.
	 * @param socketPort A valid socket port (ex: 60001).
	 */
	public void setSocketPort(int socketPort);
	
	/**
	 * Set whether or not to show states on the sequence diagram.
	 * @param showStates true or false
	 */
	public void setShowStates(boolean showStates);
	
	/**
	 * Set whether or not to exclude system messages from the sequence diagram.
	 * @param excludeSystemMessages true or false
	 */
	public void setExcludeSystemMessages(boolean excludeSystemMessages);
	
	/**
	 * Set whether or not to exclude additional system messages from the sequence diagram.
	 * @param excludeAdditionalSystemMessages true or false
	 */
	public void setExcludeAdditionalSystemMessages(boolean excludeAdditionalSystemMessages);
	
	/**
	 * Set max length of a class or role name to output.
	 * @param maxNameLen The maximum length of the name.
	 */
	public void setMaxNameLen(int maxNameLen);
	
	/**
	 * Set max length of msg.data contents to output.
	 * @param maxDataLen The maximum length of the data.
	 */
	public void setMaxDataLen(int maxDataLen);
	
	/**
	 * Set an optional provided and/or required interface.
	 * @param providedRequiredInterface A fully initialized instance of IPortInterface.
	 */
	public void setProvidedRequiredInterface(IPortInterface providedRequiredInterface);
	
	/**
	 * Get the output format.
	 * @return One of FORMAT_UML_GRAPH, FORMAT_SDEDIT, FORMAT_SDEDIT_SOCKET.
	 */
	public int getOutputFormat();
	
	/**
	 * Get the host for a socket connection, if using sdedit as a sequence diagram server.
	 * @return A socket host (ex: localhost).
	 */
	public String getSocketHost();
	
	/**
	 * Get the port for a socket connection, if using sdedit as a sequence diagram server.
	 * @return A socket port (ex: 60001).
	 */
	public int getSocketPort();
	
	/**
	 * Get whether or not states will be shown on the sequence diagram.
	 * @return true or false
	 */
	public boolean getShowStates();

	/**
	 * Get whether or not to exclude system messages from the sequence diagram.
	 * @return true or false
	 */
	public boolean getExcludeSystemMessages();
	
	/**
	 * Get whether or not to exclude additional system messages from the sequence diagram.
	 * @return true or false
	 */
	public boolean getExcludeAdditionalSystemMessages();
	
	/**
	 * Get max length of a class or role name to output.
	 * @return The maximum length.
	 */
	public int getMaxNameLen();
	
	/**
	 * Get max length of msg.data contents to output.
	 * @return The maximum length.
	 */
	public int getMaxDataLen();
	
	/**
	 * Get an optional provided and/or required interface.
	 * @return A fully initialized instance of IPortInterface, or null.
	 */
	 public IPortInterface getProvidedRequiredInterface();
	
	/**
	 * Add a message to the interaction.
	 * @param msg The message.
	 */
	public abstract void addMessage(IMessage msg);

	/**
	 * Add a message to the interaction.
	 * @param signal A signal that identifies the type of message.
	 * @param data Optional data carried by the message.
	 * @param sender The sender of the message.
	 * @param receiver The intended receiver of the message.
	 * @param index A port replication index.
	 */
	public abstract void addMessage(int signal, Object data, IXholon sender,
			IXholon receiver, int index);
	
	/**
	 * Add a Synchronous message to the interaction.
	 * @param msg The message.
	 */
	public abstract void addSyncMessage(IMessage msg);
	
	/**
	 * Add a state to the sequence diagram, but only if getShowStates() == true.
	 * @param state A state within a state machine.
	 */
	public abstract void addState(IStateMachineEntity state);
	
	/**
	 * Process a message that was previously sent from one Xholon instance (the sender)
	 * and added to the Interaction,
	 * and has now been received by another Xholon instance (the receiver).
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public abstract void processReceivedMessage(IMessage msg);
	
	/**
	 * Create a sequence diagram from the interaction.
	 * @param modelName The name of this model.
	 */
	public abstract void createSequenceDiagram(String modelName);
}
