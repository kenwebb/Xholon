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
 * The Model in Risk consists of all the domain-specific nodes,
 * those nodes that have to do specifically with the game of Risk.
 * The GameModel class provides direct access to the most important of these nodes.
 * Most of these are container nodes,
 * for example &lt;Players> that contains all the Player nodes in a game.
 * It's primarily used by the GameController.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class GameModel extends XhRisk {
	
	/**
	 * The &lt;Players> container node in the Xholon tree.
	 */
	private Players players = null;
	
	/**
	 * The &lt;Dice> container node in the Xholon tree.
	 */
	private Dice dice = null;
	
	/**
	 * The &lt;Cards> container node in the Xholon tree.
	 */
	private Cards cards = null;
	
	/**
	 * The &lt;Quantities> container node in the Xholon tree.
	 */
	private IXholon quantities = null;
	
	/**
	 * The &lt;All> quantity node in the Xholon tree.
	 */
	private Quantity quantityAll = null;
	
	/**
	 * The &lt;World> container node in the Xholon tree.
	 */
	private IXholon world = null;
	
	public Players getPlayers() {
		return players;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}

	public Cards getCards() {
		return cards;
	}

	public void setCards(Cards cards) {
		this.cards = cards;
	}

	public IXholon getWorld() {
		return world;
	}

	public void setWorld(IXholon world) {
		this.world = world;
	}

	public IXholon getQuantities() {
		return quantities;
	}

	public void setQuantities(IXholon quantities) {
		this.quantities = quantities;
	}

	public Quantity getQuantityAll() {
		return quantityAll;
	}

	public void setQuantityAll(Quantity quantityAll) {
		this.quantityAll = quantityAll;
	}
	
}
