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

package org.primordion.xholon.base;

/**
 * A result structure returned by IXholon.handleNodeSelection(), typically to a GUI.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on December 28, 2008)
 */
public class HandleNodeSelectionResult {
	
	// commands
	public static final int HNSR_NULL = 0;
	public static final int HNSR_REFRESH = 1;
	
	/** A String with information about that node, that could for example be displayed. */
	private String toString = null;
	
	/** An optional command, to be invoked by the GUI. (ex: Refresh) */
	private int command = HNSR_NULL;
	
	/** Some optional information that can be used by the GUI. */
	private String information = null;
	
	/**
	 * constructor
	 */
	public HandleNodeSelectionResult() {
		this(null, HNSR_NULL, null);
	}
	
	/**
	 * constructor
	 * @param toString
	 */
	public HandleNodeSelectionResult(String toString) {
		this(toString, HNSR_NULL, null);
	}
	
	/**
	 * constructor
	 * @param toString A String with information about that node, that could for example be displayed.
	 * @param command An optional command, to be invoked by the GUI. (ex: Refresh)
	 * @param information Some optional information that can be used by the GUI.
	 */
	public HandleNodeSelectionResult(String toString, int command, String information) {
		setToString(toString);
		setCommand(command);
		setInformation(information);
	}
	
	public String getToString() {
		return toString;
	}

	public void setToString(String toString) {
		this.toString = toString;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
}
