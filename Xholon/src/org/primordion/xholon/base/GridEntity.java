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

package org.primordion.xholon.base;

/**
 * GridEntity is a general instantiable subclass of AbstractGrid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 9, 2007)
 */
public class GridEntity extends AbstractGrid implements IGrid {
	private static final long serialVersionUID = -5364699196794177001L;

	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {return null;}
	
	/**
	 * Prevent default Xholon behavior.
	 * A GridEntity cannot tolerate the creation of extra nodes, such as a Role node.
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {}
	
	public String toString() {
	  // OLD
		/*String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		return outStr;*/
		
		// NEW
		StringBuilder sb = new StringBuilder()
		.append(getName())
		.append(" row:")
		.append(getRow())
		.append(" col:")
		.append(getCol());
		if ((port != null) && (port.length > 0)) {
			sb.append(" [");
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					sb
					.append(" port:")
					.append(port[i].getName());
				}
			}
			sb.append("]");
		}
		if (hasChildNodes()) {
			sb
			.append(" children:");
			IXholon node = getFirstChild();
			while (node != null) {
				sb
				.append(" ")
				.append(node.getName());
				node = node.getNextSibling();
			}
		}
		return sb.toString();
	}
}
