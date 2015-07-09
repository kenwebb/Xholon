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
 * A signal is just an int, that identifies a type of Message.
 * ISignal includes all system level signal IDs, those that could be used in any application.
 * A signal ID should be unique, at least within any given application.
 * <p>There are three ranges of signals:</p>
 * <ol>
 * <li>User domain-specific signals can use any positive integer <strong>>= 1 and &lt;= Integer.MAX_VALUE</strong> .
 * These are the message signals used in applications.</li>
 * <li>Core system messages use any integer <strong>&lt;= 0 and >= -999</strong> .
 * All or most of these message signals will be listed in this ISignal interface.</li>
 * <li>Additional system messages can use any negative integer <strong>&lt;= -1000 and >= Integer.MIN_VALE</strong> .
 * These message signals tend to be used to communicate with Xholon Services,
 * both services supplied as part of the Xholon framework, and user-created services.</li>
 * </ol>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.4 (Created on September 5, 2006)
 */
public interface ISignal {
	
	// Signal ranges
	
	/**
	 * <p>The maximum in the allowable range for user domain-specific signal IDs.</p>
	 */
	public static final int SIGNAL_MAX_USER = Integer.MAX_VALUE;
	
	/**
	 * <p>The minimum in the allowable range for user domain-specific signal IDs.</p>
	 */
	public static final int SIGNAL_MIN_USER = 1;
	
	/**
	 * <p>The maximum in the allowable range for core system level signal IDs.</p>
	 */
	public static final int SIGNAL_MAX_SYSTEM = 0;
	
	/**
	 * <p>The minimum in the allowable range for core system level signal IDs.</p>
	 */
	public static final int SIGNAL_MIN_SYSTEM = -999;
	
	/**
	 * <p>The maximum in the allowable range for additional non-core system level signal IDs.</p>
	 */
	public static final int SIGNAL_MAX_SYSTEM_ADDITIONAL = -1000;
	
	/**
	 * <p>The minimum in the allowable range for additional non-core system level signal IDs.</p>
	 */
	public static final int SIGNAL_MIN_SYSTEM_ADDITIONAL = Integer.MIN_VALUE;
	
	
	// Core system signals
	
	/**
	 * <p>An internal signal used by StateMachineEntity.initializeStateMachine().</p>
	 */
	public static final int SIGNAL_INIT_FSM = 0; //
	
	/**
	 * <p>Timeout signal sent in a message from a concrete instance of IXholonTime to a xholon instance.</p>
	 * <p>ex: xhtClientNode.sendMessage(SIGNAL_TIMEOUT, null, xhtXholonTime);</p>
	 */
	public static final int SIGNAL_TIMEOUT = -1; // no data
	
	/**
	 * <p>A signal sent in a message to request that a UML do activity be executed in a state machine.</p>
	 */
	public static final int SIGNAL_DOACTIVITY = -2;
	
	/**
	 * <p>A meaningless signal that can be sent for testing/debugging purposes.</p>
	 */
	public static final int SIGNAL_DUMMY = -3;
	
	/**
	 * <p>If a trigger has this value, then any signal will fire that transition (subject to guards).</p>
	 */
	public static final int SIGNAL_ANY = -4;
	
	/**
	 * <p>A signal sent by an IObservable to an observer.</p>
	 */
	public static final int SIGNAL_UPDATE = -5;
	
	/**
	 * <p>A signal sent to an instance of OrNode,
	 * intended to be processed by that node rather than by its child node.</p>
	 */
	public static final int SIGNAL_ORNODE = -6;
	
	/**
	 * <p>A signal instructing a parent to remove its child.</p>
	 */
	public static final int SIGNAL_REMOVE_CHILD = -7;
	
	/**
	 * <p>A signal sent as a response to an unknown request signal.</p>
	 */
	public static final int SIGNAL_UNKNOWN = -8;
	
	/**
	 * <p>A signal sent by an instance of XholonConsole to its context node.
	 * This will almost certainly contain a command or data entered by a human user.</p>
	 */
	public static final int SIGNAL_XHOLON_CONSOLE_REQ = -9;
	
	/**
	 * <p>A response message from a context node to its XholonConsole.</p>
	 */
	public static final int SIGNAL_XHOLON_CONSOLE_RSP = -10;
	
	
	/**
	 * <p>Start of the range of values for Swing signals.</p>
	 */
	public static final int SWIG_START   = -1000;
	
	// Edit Action signals
	
	/**
	 * The signals in this series are all extended system messages
	 * that won't be processed directly by app-specific classes.
	 * The first set of system messages are
	 * "edit action signals", typically caused by a user event in the Xholon GUI.
	 */
	public static final int SIGNAL_SYSTEM_MESSAGE = -2000;
	
	/**
	 * copyToClipboard
	 */
	public static final int ACTION_COPY_TOCLIPBOARD = -2001;
	
	/**
	 * cutToClipboard
	 */
	public static final int ACTION_CUT_TOCLIPBOARD = -2002;
	
	/**
	 * pasteLastChildFromClipboard
	 */
	public static final int ACTION_PASTE_LASTCHILD_FROMCLIPBOARD = -2003;
	
	/**
	 * pasteFirstChildFromClipboard
	 */
	public static final int ACTION_PASTE_FIRSTCHILD_FROMCLIPBOARD = -2004;
	
	/**
	 * pasteAfterFromClipboard
	 */
	public static final int ACTION_PASTE_AFTER_FROMCLIPBOARD = -2005;
	
	/**
	 * pasteBeforeFromClipboard
	 */
	public static final int ACTION_PASTE_BEFORE_FROMCLIPBOARD = -2006;
	
	/**
	 * pasteReplacementFromClipboard
	 */
	public static final int ACTION_PASTE_REPLACEMENT_FROMCLIPBOARD = -2007;
	
	/**
	 * xqueryThruClipboard
	 */
	public static final int ACTION_XQUERY_THRUCLIPBOARD = -2008;
	
	/**
	 * pasteMergeFromClipboard
	 */
	public static final int ACTION_PASTE_MERGE_FROMCLIPBOARD = -2009;
	
	/**
	 * Start an instance of the XQuery GUI.
	 */
	public static final int ACTION_START_XQUERY_GUI = -2010;
	
	/**
	 * Start an instance of the Xholon Console.
	 */
	public static final int ACTION_START_XHOLON_CONSOLE = -2011;
	
	/**
	 * Start an instance of the Xholon Web Browser.
	 */
	public static final int ACTION_START_WEB_BROWSER = -2012;
	
	/**
	 * pasteLastChildFromString
	 */
	public static final int ACTION_PASTE_LASTCHILD_FROMSTRING = -2013;
	
	/**
	 * pasteFirstChildFromString
	 */
	public static final int ACTION_PASTE_FIRSTCHILD_FROMSTRING = -2014;
	
	/**
	 * pasteAfterFromString
	 */
	public static final int ACTION_PASTE_AFTER_FROMSTRING = -2015;
	
	/**
	 * pasteBeforeFromString
	 */
	public static final int ACTION_PASTE_BEFORE_FROMSTRING = -2016;
	
	/**
	 * pasteReplacementFromString
	 */
	public static final int ACTION_PASTE_REPLACEMENT_FROMSTRING = -2017;
	
	/**
	 * pasteMergeFromString
	 */
	public static final int ACTION_PASTE_MERGE_FROMSTRING = -2019;
	
	/**
	 * pasteLastChildFromDrop
	 */
	public static final int ACTION_PASTE_LASTCHILD_FROMDROP = -2020;
	
	/**
	 * pasteFirstChildFromDrop
	 */
	public static final int ACTION_PASTE_FIRSTCHILD_FROMDROP = -2021;
	
	/**
	 * pasteAfterFromDrop
	 */
	public static final int ACTION_PASTE_AFTER_FROMDROP = -2022;
	
	/**
	 * Generate a subtree from a String that contains one or more roleNames separated by a separator.
	 * This is used by the "Movie Script Parser" XholonWorkbook.
	 */
  public static final int ACTION_PASTE_MERGE_FROMROLENAMESTRING = -2023;
	
	// XML Data Transfer signals
	
	/**
	 * The receiver should take some appropriate action when it receives an XML Transfer Object
	 * message. The data in the message should be an instance of XholonXmlTransferObject.java.
	 */
	public static final int SIGNAL_XML_TO = -2100;
	
	// Xholon Service signals
	
	/**
	 * The maximum in the range of signals that may be used to distinguish messages
	 * between a XholonService and its clients, or between two different XholonServices.
	 * The use of this range is optional.
	 */
	public static final int SIGNAL_MAX_XHOLON_SERVICE = -3000;
	
	/**
	 * The minimum in the range of signals that may be used to distinguish messages
	 * between a XholonService and its clients, or between two different XholonServices.
	 * The use of this range is optional.
	 */
	public static final int SIGNAL_MIN_XHOLON_SERVICE = -3999;
	
}
