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

//import java.util.TimerTask; // GWT

import org.primordion.xholon.app.IApplication;

/**
 * Xholon Timer Task.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on April 15, 2006)
 */
public class XholonTimerTask { //extends TimerTask { // GWT

	/** The xholon node that owns this timer task, and that will receive a message when the timeout expires. */
	private IXholon xhtClientNode;
	
	/** Optional user data that will be included with the timeout message. */
	private Object userData;
	
	/** Type of timer - Relative, Repeat, or Absolute */
	private int timerType;
	
	/**
	 * The instance of XholonTime (a xholon node),
	 * that will serve as the sender of the message when the timeout expires. */
	private IXholon xhtXholonTime = null;
	
	/**
	 * The application that owns the sender and receiver of the timeout message.
	 */
	private IApplication app = null;
	
	/**
	 * Constructor.
	 * @param xhtClientNode The xholon node that owns this timer task.
	 * @param userData Optional user data that will be included with the timeout message.
	 * @param timerType Whether this is a relative, repeat, or absolute timer.
	 * @param xhtXholonTime The instance of XholonTime that will serve as the sender of messages.
	 */
	XholonTimerTask( IXholon xhtClientNode, Object userData, int timerType, IXholon xhtXholonTime )
	{
		this.xhtClientNode = xhtClientNode;
		this.userData = userData;
		this.timerType = timerType;
		this.xhtXholonTime = xhtXholonTime;
		app = xhtClientNode.getApp();
	}
	
	/*
	 * @see java.util.TimerTask#run()
	 */
	public void run()
	{
		// Send a message if the application is currently running or stepping, or if it's a one-time timeout.
		// Otherwise ignore the timeout.
		switch (app.getControllerState()) {
		case IControl.CS_RUNNING:
		case IControl.CS_STEPPING:
			xhtClientNode.sendMessage(ISignal.SIGNAL_TIMEOUT, userData, xhtXholonTime);
			break;
		case IControl.CS_INITIALIZED:
		case IControl.CS_LOADED:
		case IControl.CS_PAUSED:
			// allow relative and absolute timouts onto the message Q, but block repeating timouts
			if (timerType != IXholonTime.TIMERTYPE_REPEAT) {
				xhtClientNode.sendMessage(ISignal.SIGNAL_TIMEOUT, userData, xhtXholonTime);
			}
			break;
		case IControl.CS_STOPPED:
			// block all timouts
			cancel();
			break;
		default:
			break;
		}
	}
	
	public void cancel() { // GWT
	  // TODO
	}
	
}
