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

package org.primordion.xholon.script;

import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * This can be used as the superclass for Java and non-Java scripts.
 * It's been tested with Groovy, JRuby, Beanshell, and Java.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 8, 2009)
 */
public class XholonScript extends Xholon {
	
	/**
	 * Unconditionally write an object to standard out.
	 * This overrides the default behavior in Xholon.print(Object)
	 * where it only prints if Msg.appM == true .
	 * @see org.primordion.xholon.base.IXholon#print(java.lang.Object)
	 */
	//public void print(Object obj) { // GWT
	//	System.out.print(obj);
	//}
	
	/**
	 * Unconditionally write an object to standard out.
	 * This overrides the default behavior in Xholon.println(Object)
	 * where it only prints if Msg.appM == true .
	 * @see org.primordion.xholon.base.IXholon#println(java.lang.Object)
	 */
	//public void println(Object obj) { // GWT
	//	System.out.println(obj);
	//}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		// implName="lang:jruby:inline:...."
		String implNameComplete = this.getXhc().getImplName();
		int pos = implNameComplete.indexOf(":inline:");
		if (pos == -1) {
			xmlWriter.writeAttribute("implName", implNameComplete);
		}
		else {
			String implName = implNameComplete.substring(0, pos+8);
			String scriptContent = implNameComplete.substring(pos+8);
			xmlWriter.writeAttribute("implName", implName);
			xmlWriter.writeText("<![CDATA[\n" + scriptContent + "\n]]>");
		}
	}
	
}
