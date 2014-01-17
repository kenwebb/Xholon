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

package org.primordion.user.app.Simple1d;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * 1D Cellular Automaton.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 15, 2007)
 */
public class Xh1dSimple extends AbstractGrid implements Ce1dSimple {

	// Signal transmitted between rows to coordinate which one is currently active
	public final static int S_DORULE = 0; // do CA rule
	
	// CA rule numbers from 0 to 255
	public static int caRuleNumber = 90; // an interesting default rule
	
	// 1D CA
	public boolean alive = false; // has to be public so reflection can set its value

	/**
	 * Constructor.
	 */
	public Xh1dSimple() {}

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
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure()
	{
		switch(xhc.getId()) {
		case CellularAutomaton1DCE:
			// set alive=true for center GridCell in first Row
			IXholon row = getFirstChild();
			int nGridCells = row.getNumChildren(false);
			IXholon center = row.getNthChild(nGridCells / 2, false);
			((Xh1dSimple)center).alive = true;
			// kick start the rule processing
			row.sendMessage(S_DORULE, null, this); // send message to first row to initiate rule processing
			break;
		default:
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case CellularAutomaton1DCE:
			processMessageQ();
			break;
		case GridCellCE:
			doRule();
			super.act();
			break;
		default:
			break;
		} // end switch
		//super.act(); // don't navigate the tree below this point
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		case RowCE: // row receives a P_DORULE msg
			// tell all of its children to act()
			getFirstChild().act();
			// tell next row to do rule processing next time step
			IXholon nextRow = getNextSibling(); // get next row
			if (nextRow != null) {
				nextRow.sendMessage(S_DORULE, null, this); // send message to next row to do rule processing
			}
			else {
				// last row; if this is a torus, then send message to first row
				Xh1dSimple gridCell = (Xh1dSimple)((Xh1dSimple)getFirstChild()).port[P_CAFUTURESELF];
				if (gridCell != null) {
					nextRow = gridCell.getParentNode();
					nextRow.sendMessage(S_DORULE, null, this); // send message to next row to do rule processing
				}
			}
			break;
		default:
			System.out.println("Xh1dSimple: message with no receiver " + msg);
			break;
		}
	}
	
	/**
	 * Do the CA rule.
	 * This is done by an instance of GridCell.
	 */
	protected void doRule()
	{
		Xh1dSimple lNeighbor = (Xh1dSimple)port[P_CALEFTNEIGHBOR];
		Xh1dSimple rNeighbor = (Xh1dSimple)port[P_CARIGHTNEIGHBOR];
		Xh1dSimple fSelf = (Xh1dSimple)port[P_CAFUTURESELF];
		if (fSelf == null) {return;} // last row
		fSelf.alive = false; // default value
		if (lNeighbor == null || rNeighbor == null) { // left or right edge
			fSelf.alive = alive;
			return;
		}
		if ((caRuleNumber & 0x01) == 0x01) { // 00000001
			if (!lNeighbor.alive && !alive && !rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x02) == 0x02) { // 00000010
			if (!lNeighbor.alive && !alive && rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x04) == 0x04) { // 00000100
			if (!lNeighbor.alive && alive && !rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x08) == 0x08) { // 00001000
			if (!lNeighbor.alive && alive && rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x10) == 0x10) { // 00010000
			if (lNeighbor.alive && !alive && !rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x20) == 0x20) { // 00100000
			if (lNeighbor.alive && !alive && rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x40) == 0x40) { // 01000000
			if (lNeighbor.alive && alive && !rNeighbor.alive) {fSelf.alive = true;}
		}
		if ((caRuleNumber & 0x80) == 0x80) { // 10000000
			if (lNeighbor.alive && alive && rNeighbor.alive) {fSelf.alive = true;}
		}
	}

	/**
	 * Set the CA rule number.
	 * @param caRuleNum The rule number.
	 */
	public static void setCaRuleNumber(int caRuleNum) {
		caRuleNumber = caRuleNum;
	}

	/**
	 * Return the CA rule number.
	 * @return The rule number.
	 */
	public static int getCaRuleNumber() {
		return caRuleNumber;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(int, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		toXmlAttributes_standard(xholon2xml, xmlWriter);
		toXmlAttribute(xholon2xml, xmlWriter, "Alive", Boolean.toString(alive), boolean.class);
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		switch(xhc.getId()) {
		case GridCellCE:
			return (getName() 
					+ " [alive:" + alive  
					+ "]");
		default:
			return (getName());
		}
	}
}
