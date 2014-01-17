/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.logging;

/**
 * A null log framework, that can be used as a replacement for apache.commons.logging and log4j.
 * The initial purpose is to use this in GWT projects.
 * Use instead of org.apache.commons.logging.Log.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 8, 2013)
 */
public class Log {
	
	public boolean isDebugEnabled() {
		return false;
	}
	  
	  public boolean isErrorEnabled() {
		  return false;
	  }
	  
	  public boolean isFatalEnabled() {
		  return false;
	  }
	  
	  public  boolean isInfoEnabled() {
		  return false;
	  }
	  
	  public boolean isTraceEnabled() {
		  return false;
	  }
	  
	  public boolean isWarnEnabled() {
		  return false;
	  }
	  
	  public void trace(java.lang.Object arg0) {
		  
	  }
	  
	  public void trace(java.lang.Object arg0, java.lang.Throwable arg1) {}
	  
	  public void debug(java.lang.Object arg0) {}
	  
	  public void debug(java.lang.Object arg0, java.lang.Throwable arg1) {}
	  
	  public void info(java.lang.Object arg0) {}
	  
	  public void info(java.lang.Object arg0, java.lang.Throwable arg1) {}
	  
	  public void warn(java.lang.Object arg0) {}
	  
	  public void warn(java.lang.Object arg0, java.lang.Throwable arg1) {}
	  
	  public void error(java.lang.Object arg0) {}
	  
	  public void error(java.lang.Object arg0, java.lang.Throwable arg1) {}
	  
	  public void fatal(java.lang.Object arg0) {}
	  
	  public void fatal(java.lang.Object arg0, java.lang.Throwable arg1) {}
}
