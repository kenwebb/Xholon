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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.Misc;

/**
 * This script adds a port to the XholonWithPorts (xhType="XhtypePureActiveObject") instance
 * that is the parent of this script.
 * The port fieldName must already exist in the parent node.
 * This script removes itself after adding the port.
 * <p>See the user.app.testNodePorts package for examples.</p>
 * <p>This class works with 3 types of field names ("port" array, scalar, array), for example:</p>
<pre>
port[0] three ff[0] ff[1]
</pre>
 * <p>Example usage:</p>
<pre>
&lt;Two>
  &lt;port name="port" index="0" connector="#xpointer(ancestor::TheSystem/One)"/>
  &lt;port name="three" connector="#xpointer(ancestor::TheSystem/Three)"/>
  &lt;port name="ff" index="0" connector="#xpointer(ancestor::TheSystem/Four)"/>
  &lt;port name="ff" index="1" connector="#xpointer(ancestor::TheSystem/Five)"/>
&lt;/Two>
</pre>
 * <p>These are some scenarios that have been tested and that work:</p>
 * <ol>
 * <li>Have content as a normal part of csh.xml .</li>
 * <li>At runtime, paste in a node with one or more port nodes.</li>
 * <li>At runtime, paste in a single port for an existing node.</li>
 * <li>At runtime, paste in a forest (ex: &lt;_-.ports>) of ports for an existing node.</li>
 * </ol>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 16, 2013)
 */
@SuppressWarnings("serial")
public class port extends XholonScript {
	
	/**
	 * Information about the contents of this port node.
	 */
	protected PortInformation pi = new PortInformation(null, null);
	
	@Override
	public void configure() {
		IXholon contextNode = parentNode;
		if (contextNode instanceof XholonWithPorts) {
			String instructions = "";
			String indexStr = "";
			// assume that parent is a XholonWithPorts
			if ("port".equals(pi.getFieldName())) {
				// this is a "port" port array
				parentNode.setPorts();
			}
			else {
				instructions += "P";
				if (pi.getFieldNameIndex() == PortInformation.PORTINFO_NOTANARRAY) {
					indexStr = "[-1]"; // scalar
				}
			}
			instructions += pi.getLocalName() + indexStr
					+ "=\"" + pi.getXpathExpression() + "\"";
			//System.out.println(instructions);
			// port[0]="#xpointer(ancestor::TheSystem/One)"
			// Pthree[-1]="#xpointer(ancestor::TheSystem/Three)"
			// Pff[0]="#xpointer(ancestor::TheSystem/Four)"
			// Pff[1]="#xpointer(ancestor::TheSystem/Five)"
			((XholonWithPorts)parentNode).configurePorts(instructions, 0);
		}
		else {
			Xholon.getLogger().error("Unable to add port " + pi + " to " + contextNode
					+ " because it is not a subclass of XholonWithPorts.");
		}
		super.configure();
		removeChild();
	}
	
	@Override
	public int setAttributeVal(String attrName, String attrVal) {
		if ("name".equals(attrName)) {
			pi.setFieldName(attrVal);
		}
		else if ("connector".equals(attrName)) {
			pi.setXpathExpression(attrVal);
		}
		else if ("index".equals(attrName)) {
			pi.setFieldNameIndex(Misc.atoi(attrVal, 0));
			pi.setFieldNameIndexStr(attrVal);
		}
		return 0;
	}
	
}
