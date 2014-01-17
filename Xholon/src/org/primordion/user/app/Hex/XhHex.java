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

package org.primordion.user.app.Hex;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.MiscRandom;

/**
 * Hexagonal Grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 2, 2007)
 */
public class XhHex extends AbstractGrid implements CeHex{

	// Hex
	public boolean alive = false; // has to be public so reflection can set its value
	public String roleName = null;
	protected boolean hasMoved; // whether creature instance has had its turn to act() this timestep

	/**
	 * Constructor.
	 */
	public XhHex() {}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(boolean)
	 */
	public void setVal(boolean val) {alive = val;}

	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName() {return roleName;}

	/**
	 * Is this grid cell alive?
	 * @return true or false
	 */
	public boolean isAlive() {return alive;}
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		// Hex
		switch(xhc.getId()) {
		case CreatureCE:
			if (hasMoved == false) {
				int r = MiscRandom.getRandomInt(0, 6); // get random integer between 0 and 5 inclusive
				moveAdjacent(r);
			}
			break;
		case HexGridCellCE:
			break;
		case HexCE:
			processMessageQ();
			break;
		default:
			break;
		} // end switch

		super.act();
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		hasMoved = false;
		super.postAct();
	}

	/**
	 * Used by Creature to move to an adjacent grid cell.
	 * @param direction The direction it would like to move.
	 */
	protected void moveAdjacent(int direction)
	{
		IXholon newParentNode = getParentNode();
		newParentNode = ((AbstractGrid)newParentNode).port[direction]; // choose a new parent
		if ((newParentNode != null) && (((XhHex)newParentNode).isAlive())) {
			removeChild(); // detach subtree from parent and sibling links
			appendChild(newParentNode); // insert as the last child of the new parent
			hasMoved = true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(int, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		toXmlAttributes_standard(xholon2xml, xmlWriter);
		switch(xhc.getId()) {
		case HexGridCellCE:
			toXmlAttribute(xholon2xml, xmlWriter, "Alive", Boolean.toString(alive), boolean.class);
			break;
		default:
			break;
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		if (getVal() != 0.0) {
			outStr += " val:" + getVal();
		}
		return outStr;
	}
}
