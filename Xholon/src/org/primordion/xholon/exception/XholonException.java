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

package org.primordion.xholon.exception;

/**
 * An abstract class from which all Xholon Exception classes should be derived.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on November 12, 2007)
 */
public abstract class XholonException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public XholonException()
	{
		super();
	}
	
	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message - the detail message.
	 * The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public XholonException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message - the detail message
	 * (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause - the cause (which is saved for later retrieval by the Throwable.getCause() method).
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public XholonException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Constructs a new exception with the specified cause and a detail message of
	 * (cause==null ? null : cause.toString())
	 * (which typically contains the class and detail message of cause).
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public XholonException(Throwable cause)
	{
		super(cause);
	}
}
