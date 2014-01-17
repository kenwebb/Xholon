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

/**
 * ROOM/UML2 message.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Aug 17, 2005)
 */
public interface IMessage extends IXholon {

	/**
	 * Get the optional data included with the message.
	 * @return Any data that was sent with the message, or null.
	 */
	public abstract Object getData();

	/**
	 * Set the data to be sent with the message.
	 * @param data The data to include with the message.
	 */
	public abstract void setData(Object data);

	/**
	 * Get the intended receiver of the message.
	 * @return The receiver.
	 */
	public abstract IXholon getReceiver();

	/**
	 * Set the intended receiver of the message.
	 * @param receiver The receiver.
	 */
	public abstract void setReceiver(IXholon receiver);

	/**
	 * Get the sender of the message.
	 * @return The sender.
	 */
	public abstract IXholon getSender();

	/**
	 * Set which Xholon instance sent the message. 
	 * @param sender The sender.
	 */
	public abstract void setSender(IXholon sender);

	/**
	 * Get the signal that identifies the type of message.
	 * @return The signal.
	 */
	public abstract int getSignal();

	/**
	 * Set the signal that identifies the message type.
	 * @param signal The signal.
	 */
	public abstract void setSignal(int signal);

	/**
	 * Get the index.
	 * @return The index.
	 */
	public abstract int getIndex();

	/**
	 * Set the index.
	 * @param index The index.
	 */
	public abstract void setIndex(int index);

}