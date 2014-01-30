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

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.XholonCollections;

/**
 * Cards is a container for Card objects.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Cards extends XhRisk {
	
	/**
	 * The number of extra armies that a player receives by turning in a set of cards.
	 * This number is increased each time a set of cards is turned in.
	 */
	private int extraArmies = 4;
	
	/**
	 * A card can be currently owned by a player, or it can be in one of two piles:
	 * <ol>
	 * <li>selection pile</li>
	 * <li>discard pile</li>
	 * </ol>
	 * Both of these piles are stored as children of the Cards node.
	 * All of the cards in the selection pile come before the cards in the discard pile.
	 * The selectionPileSize variable is used to separate the two piles.
	 * If selectionPileSize == the total number of cards, then all cards are in the selection pile.
	 * If selectionPileSize == 0, then all cards are in the discard pile.
	 * The cards must be shuffled when selectionPileSize == 0.
	 */
	private int selectionPileSize = 0;
	
	public int getExtraArmies() {
		return extraArmies;
	}
	
	public void setExtraArmies(int extraArmies) {
		this.extraArmies = extraArmies;
	}

	/**
	 * Increment the number of extra armies, after a set of cards has been turned in.
	 */
	public void incExtraArmies()
	{
		switch(extraArmies) {
		case 4:  extraArmies =  6; break;
		case 6:  extraArmies =  8; break;
		case 8:  extraArmies = 10; break;
		case 10: extraArmies = 12; break;
		case 12: extraArmies = 15; break;
		default: extraArmies += 5; break;
		}
	}
	
	/**
	 * Get the card at the top of the selection pile.
	 * If the pile is empty, then shuffle the discard pile to obtain a new selection pile.
	 * @return
	 */
	public IXholon selectCard() {
		if (selectionPileSize == 0) {
			shuffle();
		}
		IXholon card = getFirstChild();
		card.removeChild();
		selectionPileSize--;
		return card;
	}
	
	/**
	 * Place a used card on the discard pile. 
	 * @param card
	 */
	public void discardCard(IXholon card) {
		card.appendChild(this);
	}
	
	/**
	 * Shuffle the cards.
	 */
	public void shuffle()
	{
		int numCards = getNumChildren(false);
		List list = new ArrayList(numCards);
		int i;
		for (i = 0; i < numCards; i++) {
			IXholon card = getFirstChild();
			list.add(card);
			card.removeChild();
		}
		XholonCollections.shuffle(list);
		for (i = 0; i < numCards; i++) {
			((IXholon)list.get(i)).appendChild(this);
		}
		selectionPileSize = getNumChildren(false);
	}

	/*
	 * @see org.primordion.user.app.Risk.XhRisk#postConfigure()
	 */
	public void postConfigure()
	{
		// initialize the territory and figure in each card
		IXPath xpath = getXPath();
		IXholon world = xpath.evaluate("descendant::World", this.getRootNode());
		IXholon continent = world.getFirstChild();
		IXholon territory = continent.getFirstChild();
		
		IXholon figures = xpath.evaluate("descendant::Figures", this.getRootNode());
		IXholon figure = figures.getFirstChild();
		
		IXholon node = getFirstChild();
		while (node != null) {
			if ("TerritoryCard".equals(node.getXhcName())) {
				((Card)node).setTerritory(territory);
				((Card)node).setFigure(figure);
				territory = territory.getNextSibling();
				if (territory == null) {
					continent = continent.getNextSibling();
					if (continent == null) {
						break;
					}
					territory = continent.getFirstChild();
					if (territory == null) {
						break;
					}
				}
				figure = figure.getNextSibling();
				if (figure == null) {
					// prepare to repeat the cycle
					figure = figures.getFirstChild();
				}
			}
			node = node.getNextSibling();
		}
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#toString()
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer()
		.append(" extraArmies:" + extraArmies)
		.append(" selectionPileSize:" + selectionPileSize)
		.append(" discardPileSize:" + (getNumChildren(false) - selectionPileSize));
		return super.toString() + sb;
	}
	
}
