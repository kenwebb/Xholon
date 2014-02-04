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

package org.primordion.user.app.PingPong;

import org.primordion.xholon.app.Application;
//import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Msg;

/**
 * Ping Pong. This is a sample Xholon application.
 * <p>Two active objects of the same class collaborate to count from 0 to 19.
 * They coordinate their behaviors by writing directly into each others
 * public variables, through ports. Compare this with the Hello World tutorial
 * in which two active objects collaborate by passing messages through ports.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 18, 2005)
 */
public class AppPingPong extends Application {

	/** Constructor. */
	public AppPingPong() {super();}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#process()
	 */
	public void process()
	{
		if (Msg.appM) {println("");}
		super.process();
	}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		root.postAct();
		if (Msg.appM) {println("");}
		root.postAct();
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.AppPingPong",
				"./config/PingPong/PingPong_xhn.xml");
	}
}
