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

//import java.util.Date; // GWT
//import java.util.Timer; // GWT
import com.google.gwt.user.client.Timer; // GWT

/**
 * Handle time in a Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on April 15, 2006)
 */
public class XholonTime extends XholonWithPorts implements IXholonTime, IXholon {
	private static final long serialVersionUID = 8877951293861782403L;
	
	//private Timer timer = null; // GWT
	
	/**
	 * constructor
	 */
	public XholonTime()
	{
		//timer = new Timer(); // GWT
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#timeoutRelative(org.primordion.xholon.base.IXholon, long)
	 */
	public XholonTimerTask timeoutRelative( IXholon clientNode, long delay )
	{
		final XholonTimerTask xhTask = new XholonTimerTask(clientNode, null, TIMERTYPE_RELATIVE, this);
		
		// GWT
		//timer.schedule(xhTask, delay);
		Timer timer = new Timer() {
		  @Override
		  public void run() {
		    xhTask.run();
		  }
		};
		timer.schedule((int)delay);
		
		return xhTask;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#timeoutRelative(org.primordion.xholon.base.IXholon, java.lang.Object, long)
	 */
	public XholonTimerTask timeoutRelative( IXholon clientNode, Object data, long delay )
	{
		final XholonTimerTask xhTask = new XholonTimerTask(clientNode, data, TIMERTYPE_RELATIVE, this);
				
		// GWT
		//timer.schedule(xhTask, delay);
		Timer timer = new Timer() {
		  @Override
		  public void run() {
		    xhTask.run();
		  }
		};
		timer.schedule((int)delay);
		
		return xhTask;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#timeoutRepeat(org.primordion.xholon.base.IXholon, long, long)
	 */
	public XholonTimerTask timeoutRepeat( IXholon clientNode, long delay, long period )
	{
		final XholonTimerTask xhTask = new XholonTimerTask(clientNode, null, TIMERTYPE_REPEAT, this);
		
		// GWT
		//timer.schedule(xhTask, delay, period);
		Timer timer = new Timer() {
		  @Override
		  public void run() {
		    xhTask.run();
		  }
		};
		timer.scheduleRepeating((int)period);
		
		return xhTask;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#timeoutRepeat(org.primordion.xholon.base.IXholon, java.lang.Object, long, long)
	 */
	public XholonTimerTask timeoutRepeat( IXholon clientNode, Object data, long delay, long period )
	{
		final XholonTimerTask xhTask = new XholonTimerTask(clientNode, data, TIMERTYPE_REPEAT, this);
		
		// GWT
		//timer.schedule(xhTask, delay, period);
		Timer timer = new Timer() {
		  @Override
		  public void run() {
		    xhTask.run();
		  }
		};
		timer.scheduleRepeating((int)period);
		
		return xhTask;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#timeoutAbsolute(org.primordion.xholon.base.IXholon, java.util.Date)
	 */
	/* GWT
	public XholonTimerTask timeoutAbsolute( IXholon clientNode, Date time )
	{
		XholonTimerTask xhTask = new XholonTimerTask(clientNode, null, TIMERTYPE_ABSOLUTE, this);
		timer.schedule(xhTask, time);
		return xhTask;
	}*/
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#cancel(org.primordion.xholon.base.XholonTimerTask)
	 */
	public void cancel(XholonTimerTask xhTimerTask)
	{
	  // TODO cancel the Timer as well
		xhTimerTask.cancel();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonTime#cancelAll()
	 */
	public void cancelAll()
	{
		//timer.cancel(); // GWT
	}
	
	/*
	 * This won't work with, and is unnecessary with, GWT.
	 * It's left here to allow compilation of existing apps that (now unnecessarily) call this method.
	 */
	public static void sleep(long delay) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#getName()
	 */
	public String getName()
	{
		return "XholonTime";
	}
}
