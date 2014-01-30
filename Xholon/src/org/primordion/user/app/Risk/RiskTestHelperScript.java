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
import org.primordion.xholon.script.XholonScript;

/**
 * This script can be pasted as the last child of any node in the game of Risk.
 * It performs a variety of tasks that may be useful in testing.
 * <p>&lt;RiskTestHelperScript/></p>
 * <p>&lt;RiskTestHelperScript action="1" numArmies="3"/></p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class RiskTestHelperScript extends XholonScript {
	
	// actions
	private static final int ADD_ARMIES_HERE = 1;
	private static final int ADD_ARMIES_TO_SUBTREE = 2;
	private static final int RECEIVE_CARD = 3;
	private static final int SHOW_HELP = 99;
	protected int action = ADD_ARMIES_HERE;
	
	// number of armies
	private static final int DEFAULT_NUM_ARMIES = 5;
	protected int numArmies = DEFAULT_NUM_ARMIES;
	
	// number of cards
	private static final int DEFAULT_NUM_CARDS = 1;
	protected int numCards = DEFAULT_NUM_CARDS;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		switch (action) {
		case ADD_ARMIES_HERE: addArmiesToNode(getParentNode()); break;
		case ADD_ARMIES_TO_SUBTREE: addArmiesToSubtree(); break;
		case RECEIVE_CARD: receiveCard(getParentNode()); break;
		case SHOW_HELP: showHelp(); break;
		default: showHelp(); break;
		}
		this.removeChild(); // remove self from the Xholon tree
	}
	
	/**
	 * Show help information about how to use this script.
	 */
	protected void showHelp() {
		println("<RiskTestHelperScript action='action' numArmies='numArmies'/>");
		println("where possible actions are:");
		println("1  Add armies to one territory.");
		println("2  Add armies to all territories within a subtree.");
		println("3  Receive cards for a player.");
		println("99 Show this help information.");
		println("and where optional numArmies is an integer greater than 0");
		println("Example: <RiskTestHelperScript action='1' numArmies='3'/>");
		println("Example: <RiskTestHelperScript action='3' numCards='3'/>");
	}
	
	/**
	 * Add armies to one territory.
	 */
	protected void addArmiesToNode(IXholon node) {
		if (node.getXhc().hasAncestor("Territory")) {
			((Territory)node).incQuantityArmies(numArmies);
		}
	}
	
	/**
	 * Add armies to all territories within a subtree.
	 */
	protected void addArmiesToSubtree() {
		getParentNode().visit(this);
	}
	
	/**
	 * Receive a card for a player.
	 */
	protected void receiveCard(IXholon node) {
		if (node.getXhc().hasAncestor("Player")) {
			IXholon controller = ((Player)node).getController();
			IXholon model = ((GameController)controller).getModel();
			Cards cards = ((GameModel)model).getCards();
			for (int i = 0; i < numCards; i++) {
				IXholon card = cards.selectCard();
				card.appendChild(node.getFirstChild());
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#visit(org.primordion.xholon.base.IXholon)
	 */
	public boolean visit(IXholon visitor) {
		switch (action) {
		case ADD_ARMIES_TO_SUBTREE: addArmiesToNode(visitor); break;
		default: break;
		}
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("action".equals(attrName)) {
			action = Integer.parseInt(attrVal);
		}
		else if ("numArmies".equals(attrName)) {
			numArmies = Integer.parseInt(attrVal);
		}
		else if ("numCards".equals(attrName)) {
			numCards = Integer.parseInt(attrVal);
		}
		else {
			println("RiskTestHelperScript: invalid attribute " + attrName + ". Usage:");
			showHelp();
		}
		return 0;
	}
	
}
