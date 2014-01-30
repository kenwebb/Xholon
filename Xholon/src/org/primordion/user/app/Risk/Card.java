/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.user.app.Risk;

import org.primordion.xholon.base.IXholon;

/**
 * A player receives a card every turn that she/he/it conquers one or more territories.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Card extends XhRisk {
	
	/**
	 * Each territory card refers to a different Territory node.
	 */
	private IXholon territory = null;
	
	/**
	 * Each territory card refers to one of several Figure nodes.
	 */
	private IXholon figure = null;
	
	public IXholon getTerritory() {
		return territory;
	}

	public void setTerritory(IXholon territory) {
		this.territory = territory;
	}

	public IXholon getFigure() {
		return figure;
	}

	public void setFigure(IXholon figure) {
		this.figure = figure;
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#toString()
	 */
	public String toString()
	{
		String location = null;
		if ("Cards".equals(getParentNode().getXhcName())) {
			location = getParentNode().getName();
		}
		else { // OwnedCards
			location = getParentNode().getParentNode().getName();
		}
		if ("TerritoryCard".equals(getXhcName())) {
			return "Card: " + territory.getXhcName() + " " + figure.getXhcName() + " " + location;
		}
		else {
			return "Card: wild";
		}
	}
	
}
