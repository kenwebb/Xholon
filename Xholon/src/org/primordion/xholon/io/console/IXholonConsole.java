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

package org.primordion.xholon.io.console;

//import java.io.File; // GWT

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;

/**
 * Provide an Xholon console capability for interacting with a Xholon application.
 * A XholonConsole can be opened for any node in the Xholon tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 15, 2009)
 */
public interface IXholonConsole {
	
	// setters and getters
	
	public abstract String getRoleName();

	public abstract void setRoleName(String roleName);

	//public abstract String getXmlIhFilePathAndName();

	//public abstract void setXmlIhFilePathAndName(String xmlIhFilePathAndName);

	//public abstract String getXmlCdFilePathAndName();

	//public abstract void setXmlCdFilePathAndName(String xmlCdFilePathAndName);

	//public abstract String getXmlFilePathAndName();

	//public abstract void setXmlFilePathAndName(String xmlFilePathAndName);

	public abstract IXholon getContext();

	public abstract void setContext(IXholon context);

	public abstract IXholon getCommandPane();

	public abstract void setCommandPane(IXholon commandPane);
	
	public abstract void setResult(String result, boolean replace);

	//public abstract IXholon getResultPane();

	//public abstract void setResultPane(IXholon resultPane);

	//public abstract File getLatestOpenedFile(); // GWT

	//public abstract void setLatestOpenedFile(File latestOpenedFile); // GWT
	
	public abstract void log(Object obj);
	
	public abstract String getMode();

	public abstract void setMode(String mode);
	
	public abstract Object getWidget();
	
	public abstract void setTabHeader();
  
	/*
	 * @see org.primordion.xholon.base.Xholon#getApp()
	 */
	public abstract IApplication getApp();

	/*
	 * @see org.primordion.xholon.base.Xholon#setApp(org.primordion.xholon.app.IApplication)
	 */
	public abstract void setApp(IApplication app);

	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public abstract void postConfigure();

	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public abstract void processReceivedMessage(IMessage msg);
	
}
