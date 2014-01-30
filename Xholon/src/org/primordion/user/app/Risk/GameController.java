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

import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;

/**
 * Game Controller,
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class GameController extends XhRisk implements IRiskSignal {
	
	// ports
	protected GameModel model = null;
	protected GameView view = null;

	// Controller states
	public static final int STATE_INITIALIZING = 0;
	public static final int STATE_READY = 1;
	
	/**
	 * Current state of the controller.
	 */
	protected int controllerState = STATE_INITIALIZING;
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure()
	{
		controllerState = STATE_READY;
		//model.setupGame();
		model.getCards().shuffle();
		assignTerritories();
		view.colorTerritories();
	    super.postConfigure();
	    //worker.sendMessage(retrieveSignal, null, this);
	}
	
	/**
	 * Assign each territory to a player,
	 * and place one army on the territory.
	 * This is done at the start of a new game.
	 */
	protected void assignTerritories()
	{
		IXholon player = model.getPlayers().getFirstChild();
		IXholon card = model.getCards().getFirstChild();
		while (card != null) {
			if ("TerritoryCard".equals(card.getXhcName())) {
				IXholon territory = ((Card)card).getTerritory();
				((Territory)territory).setOccupier(player);
				((Player)player).decUnplacedArmies(1);
				((Territory)territory).setQuantityArmies(1);
				player = player.getNextSibling();
				if (player == null) {
					// start another iteration through the players
					player = model.getPlayers().getFirstChild();
				}
			}
			card = card.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		if (!doStateMachine(controllerState, msg.getSignal(), msg)) {
			// give the superclass a chance to process the message
			super.processReceivedMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		if (!doStateMachine(controllerState, msg.getSignal(), msg)) {
			return super.processReceivedSyncMessage(msg);
		}
		return null;
	}
	
	/**
	 * Do controller state machine.
	 * @param state The current state.
	 * @param event An event.
	 * @param msg A received message.
	 * @return Whether or not the event was able to be processed.
	 */
	protected boolean doStateMachine(int state, int event, IMessage msg)
	{
	    //System.out.println(msg + " " + msg.getData().getClass());
		boolean rc = true;
		IXholon sender = msg.getSender();
		switch (state) {
		case STATE_READY:
			switch (event) {
			case SIG_ATTACK:
				rc = attack(sender);
				break;
			case SIG_MOVE_ARMIES:
				rc = moveArmies(sender);
				break;
			case SIG_PLACE_ARMIES:
				rc = placeArmies(sender);
				break;
			case SIG_USE_CARDS:
				rc = useCards(sender);
				break;
			case SIG_END_TURN:
				rc = endTurn(sender);
				return true; // don't send an ack on an endTurn
			case SIG_START_TURN:
				rc = startTurn(sender);
				break;
			//case SIG_DO_TURN:
			//	rc = false;
			//	break;
			case SIG_SHOW_CARDS:
				rc = showCards(sender);
				return true; // don't send an ack
			case SIG_SHOW_TERRITORIES:
				rc = showTerritories(sender);
				return true; // don't send an ack
			case SIG_SHOW_CONTINENTS:
				rc = showContinents(sender);
				return true; // don't send an ack
			default:
				rc = false;
				break;
			} // end switch (event)
			break;
		default:
			rc = false;
			break;
		} // end switch (state)
		if (rc) {
			sender.sendMessage(SIG_ACK, null, this);
		}
		else {
			sender.sendMessage(SIG_NAK, null, this);
		}
		return rc;
	}
	
	/**
	 * Start a player's turn.
	 * @param player The player who is starting the turn.
	 */
	protected boolean startTurn(IXholon player)
	{
		boolean rc = true;
		System.out.println("Turn for player " + player + " has started.");
		// count reinforcement armies from territories
		int count = ((Player)player).countReinforcementArmies();
		// count reinforcement armies from continents
		List continents = ((Player)player).getOccupiedContinents();
		for (int i = 0; i < continents.size(); i++) {
			Continent continent = (Continent)continents.get(i);
			count += continent.getBonusArmies();
		}
		((Player)player).incUnplacedArmies(count);
		((Player)player).setConqueror(false);
		return rc;
	}
	
	/**
	 * Use (turn in) a set of cards.
	 * @param player The player who is turning in the cards.
	 */
	protected boolean useCards(IXholon player)
	{
		boolean rc = true;
		IXholon[] selectedNodes = getCurrentlySelectedNodes();
		if (selectedNodes.length != 3) {return false;}
		IXholon card1 = selectedNodes[0];
		IXholon card2 = selectedNodes[1];
		IXholon card3 = selectedNodes[2];
		if (!card1.getXhc().hasAncestor("Card")) {return false;}
		if (!card2.getXhc().hasAncestor("Card")) {return false;}
		if (!card3.getXhc().hasAncestor("Card")) {return false;}
		System.out.println("Player " + player + " is using cards "
				+ (Card)card1 + " "
				+ (Card)card2 + " "
				+ (Card)card3 + ".");
		int quantityExtraArmies = ((Cards)card1.getParentNode()).getExtraArmies();
		((Cards)card1.getParentNode()).incExtraArmies();
		((Player)player).incUnplacedArmies(quantityExtraArmies);
		// move the three cards back to the discard pile
		Cards cards = model.getCards();
		card1.removeChild();
		card1.appendChild(cards);
		card2.removeChild();
		card2.appendChild(cards);
		card3.removeChild();
		card3.appendChild(cards);
		return rc;
	}
	
	/**
	 * Place armies on a territory.
	 * @param player The player whose armies are being placed on the territory.
	 */
	protected boolean placeArmies(IXholon player)
	{
		boolean rc = true;
		IXholon[] selectedNodes = getCurrentlySelectedNodes();
		IXholon territory = null;
		if (selectedNodes.length == 0) {
			// no territory has been specified
			territory = ((Player)player).getRandomTerritory();
		}
		else if(!selectedNodes[0].getXhc().hasAncestor("Territory")) {
			// the specified node is not a territory node
			territory = ((Player)player).getRandomTerritory();
		}
		else {
			territory = selectedNodes[0];
		}
		int quantityArmies = 0;
		if (selectedNodes.length <= 1) {
			quantityArmies = Integer.MAX_VALUE; // All
		}
		else {
			for (int i = 1; i < selectedNodes.length; i++) {
				IXholon quantity = selectedNodes[i];
				quantityArmies += quantity.getVal_int();
			}
		}
		if (quantityArmies > ((Player)player).getUnplacedArmies()) {
			quantityArmies = ((Player)player).getUnplacedArmies();
		}
		System.out.println("Player " + player + " is placing " + quantityArmies + " armies on "
				+ territory.getXhcName() + ".");
		((Territory)territory).incQuantityArmies(quantityArmies);
		((Player)player).decUnplacedArmies(quantityArmies);
		return rc;
	}
	
	/**
	 * Attack one territory from another territory.
	 * @param player The player whose armies are attacking.
	 */
	protected boolean attack(IXholon player)
	{
		boolean rc = true;
		IXholon[] selectedNodes = getCurrentlySelectedNodes();
		boolean shouldSaveNodes = false;
		
		// get the territory the attack will be coming from
		IXholon fromTerritory = null;
		if (selectedNodes.length == 0) {
			// no attacking territory has been specified
			fromTerritory = ((Player)player).getRandomAttacker();
			shouldSaveNodes = true;
		}
		else if(!selectedNodes[0].getXhc().hasAncestor("Territory")) {
			// the specified node is not a territory node
			fromTerritory = ((Player)player).getRandomAttacker();
			shouldSaveNodes = true;
		}
		else {
			fromTerritory = selectedNodes[0];
		}
		println("---> from:" + fromTerritory);
		if (fromTerritory == null) {return false;}
		
		// get the territory the attack will be against
		IXholon againstTerritory = null;
		if (selectedNodes.length < 2) {
			// no defending territory has been specified
			againstTerritory = ((Player)player).getRandomDefender(fromTerritory);
			shouldSaveNodes = true;
		}
		else if(!selectedNodes[1].getXhc().hasAncestor("Territory")) {
			// the specified node is not a territory node
			againstTerritory = ((Player)player).getRandomDefender(fromTerritory);
			shouldSaveNodes = true;
		}
		else {
			againstTerritory = selectedNodes[1];
		}
		println("---> against:" + againstTerritory);
		if (againstTerritory == null) {return false;}
		
		// get the number of dice/armies to use in the attack
		int quantityDiceAttacker = 0;
		if (selectedNodes.length >= 2) {
			quantityDiceAttacker = selectedNodes[2].getVal_int();
		}
		if (quantityDiceAttacker == 0) {
			quantityDiceAttacker = ((Territory)fromTerritory).getQuantityArmies();
		}
		if (quantityDiceAttacker > 3) {
			quantityDiceAttacker = 3;
		}
		
		// 
		int quantityDiceDefender = getQuantityDiceDefender(quantityDiceAttacker, againstTerritory);
		rollDice(fromTerritory, againstTerritory, quantityDiceAttacker, quantityDiceDefender);
		System.out.println("Player " + player + " is attacking from " + fromTerritory.getXhcName()
				+ " against " + againstTerritory.getXhcName() + " using " + quantityDiceAttacker + " dice.");
		if (((Territory)againstTerritory).getQuantityArmies() <= 0) {
			// the territory has been conquered during this attack
			IXholon otherPlayer = ((Territory)againstTerritory).getOccupier();
			((Territory)fromTerritory).decQuantityArmies(quantityDiceAttacker);
			((Territory)againstTerritory).setQuantityArmies(quantityDiceAttacker);
			((Territory)againstTerritory).setOccupier(player);
			((Player)player).setConqueror(true);
			if (shouldSaveNodes) {
				IXholon[] saveNodes = new IXholon[3];
				saveNodes[0] = fromTerritory;
				saveNodes[1] = againstTerritory;
				saveNodes[2] = model.getQuantityAll(); // the All node
				saveSelectedNodes(saveNodes);
			}
			view.colorTerritory((Territory)againstTerritory);
			((Player)player).sendMessage(SIG_CONQUERED_TERRITORY, null, this);
			if (((Player)otherPlayer).countOccupiedTerritories() == 0) {
				// the other player has no more territories
				eliminateOtherPlayer(player, otherPlayer);
				// TODO player must turn in cards if he/she/it now has 5 or more cards
				((Player)player).sendMessage(SIG_ELIMINATED_PLAYER, null, this);
			}
		}
		else {
			// the territory was not conquered
		}
		return rc;
	}
	
	/**
	 * The other player has lost, and this player receives all of his/her/its assets.
	 * @param thisPlayer The winner.
	 * @param otherPlayer The loser.
	 */
	protected void eliminateOtherPlayer(IXholon thisPlayer, IXholon otherPlayer)
	{
		IXholon thisCards = thisPlayer.getFirstChild();
		IXholon otherCards = otherPlayer.getFirstChild();
		IXholon card = otherCards.getFirstChild();
		while (card != null) {
			IXholon nextCard = card.getNextSibling();
			card.appendChild(thisCards);
			card = nextCard;
		}
		otherPlayer.removeChild();
	}
	
	/**
	 * Get the quantity of dice that the defender will roll, given the quantity that the attacker will roll.
	 * @param quantityDiceAttacker The number of dice that the attacker will roll.
	 * @param againstTerritory The territory that is being attacked.
	 * @return The number of dice that the defender should roll.
	 */
	protected int getQuantityDiceDefender(int quantityDiceAttacker, IXholon againstTerritory)
	{
		int quantityDiceDefender = 0;
		switch (quantityDiceAttacker) {
		case 3:
			quantityDiceDefender = 2;
			break;
		case 2:
			quantityDiceDefender = 2;
			break;
		case 1:
			quantityDiceDefender = 1;
			break;
		default:
			break;
		}
		if (quantityDiceDefender > ((Territory)againstTerritory).getQuantityArmies()) {
			quantityDiceDefender = 1;
		}
		return quantityDiceDefender;
	}
	
	/**
	 * Roll dice, update the armies in each territory.
	 * @param fromTerritory The territory where the attack is coming from.
	 * @param againstTerritory The territory that the attack is being waged against.
	 * @param quantityDiceAttacker The number of dice used by the attacker.
	 * @param quantityDiceDefender The number of dic used by the defender.
	 */
	protected void rollDice(IXholon fromTerritory, IXholon againstTerritory, int quantityDiceAttacker, int quantityDiceDefender)
	{
		Dice dice = model.getDice();
		boolean[] outcome = dice.rollDice(quantityDiceAttacker, quantityDiceDefender);
		for (int i = 0; i < outcome.length; i++) {
			if (outcome[i] == true) {
				((Territory)againstTerritory).decQuantityArmies(1);
			}
			else {
				((Territory)fromTerritory).decQuantityArmies(1);
			}
		}
	}
	
	/**
	 * Move armies from one territory into another territory.
	 * @param player The player who owns the two territories.
	 */
	protected boolean moveArmies(IXholon player)
	{
		boolean rc = true;
		IXholon[] selectedNodes = getCurrentlySelectedNodes();
		if (selectedNodes.length < 2) {return false;}
		IXholon fromTerritory = selectedNodes[0];
		IXholon toTerritory = selectedNodes[1];
		int quantityArmies = 0;
		if (selectedNodes.length == 2) {
			quantityArmies = 1;
		}
		else {
			for (int i = 2; i < selectedNodes.length; i++) {
				IXholon quantity = selectedNodes[i];
				quantityArmies += quantity.getVal_int();
			}
			if (selectedNodes[2] == model.getQuantityAll()) {
				// all armies are being moved
				println("===> clearing selected nodes");
				clearSelectedNodes();
			}
		}
		if (quantityArmies > ((Territory)fromTerritory).getQuantityArmies() - 1) {
			quantityArmies = ((Territory)fromTerritory).getQuantityArmies() - 1;
		}
		System.out.println("Player " + player + " is moving " + quantityArmies + " armies from "
				+ fromTerritory.getXhcName() + " to " + toTerritory.getXhcName() + ".");
		((Territory)fromTerritory).decQuantityArmies(quantityArmies);
		((Territory)toTerritory).incQuantityArmies(quantityArmies);
		return rc;
	}
	
	/**
	 * End the current turn.
	 * @param player The player whose turn is ending.
	 */
	protected boolean endTurn(IXholon player)
	{
		boolean rc = true;
		System.out.println("Turn for player " + player + " has ended.");
		// collect a territory card, if the player has conquered at least one new territory
		if (((Player)player).isConqueror()) {
			Cards cards = model.getCards();
			IXholon card = cards.selectCard();
			card.appendChild(player.getFirstChild());
			((Player)player).setConqueror(false);
		}
		// locate the next player and send it a startTurn
		IXholon nextPlayer = player.getNextSibling();
		if (nextPlayer == null) {
			nextPlayer = player.getFirstSibling();
		}
		nextPlayer.sendMessage(SIG_STARTED_TURN, null, this);
		return rc;
	}
	
	/**
	 * Show all cards that are currently owned by a player.
	 * @param player The owner of the cards.
	 */
	protected boolean showCards(IXholon player)
	{
		boolean rc = true;
		System.out.println("Cards currently owned by " + player + ":");
		IXholon ownedCards = player.getFirstChild();
		if (ownedCards.hasChildNodes()) {
			IXholon card = ownedCards.getFirstChild();
			while (card != null) {
				System.out.println("  " + card);
				card = card.getNextSibling();
			}
		}
		else {
			System.out.println("  none");
		}
		return rc;
	}
	
	/**
	 * Show all territories that are currently occupied by a player.
	 * @param player The occupier of the territories.
	 */
	protected boolean showTerritories(IXholon player)
	{
		boolean rc = true;
		System.out.println("Territories currently occupied by " + player);
		List list = ((Player)player).getOccupiedTerritories();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("  " + list.get(i));
		}
		return rc;
	}
	
	/**
	 * Show all continents that are currently occupied by a player.
	 * @param player The occupier of the continents.
	 */
	protected boolean showContinents(IXholon player)
	{
		boolean rc = true;
		System.out.println("Continents currently occupied by " + player);
		List list = ((Player)player).getOccupiedContinents();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("  " + list.get(i));
		}
		return rc;
	}
	
	/**
	 * Get the IXholon nodes that are currently selected.
	 * @return An array of nodes, or null.
	 */
	protected IXholon[] getCurrentlySelectedNodes()
	{
		IXholon nss = getService(IXholonService.XHSRV_NODE_SELECTION);
		if (nss != null) {
			IMessage nodesMsg = nss.sendSyncMessage(
				NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
			return (IXholon[])nodesMsg.getData();
		}
		return null;
	}
	
	/**
	 * Save randomly selected nodes for later.
	 * This is used during an attack, to save attacker, defender, and quantity for use when moving armies.
	 * @param nodes
	 */
	protected void saveSelectedNodes(IXholon[] nodes)
	{
		IXholon nss = getService(IXholonService.XHSRV_NODE_SELECTION);
		if (nss != null) {
			nss.sendMessage(
				NodeSelectionService.SIG_REMEMBER_SELECTED_NODES_REQ, nodes, this);
		}
	}
	
	/**
	 * Clear selected nodes.
	 */
	protected void clearSelectedNodes()
	{
		IXholon nss = getService(IXholonService.XHSRV_NODE_SELECTION);
		if (nss != null) {
			IXholon[] nodes = new IXholon[0];
			nss.sendMessage(
				NodeSelectionService.SIG_REMEMBER_SELECTED_NODES_REQ, nodes, this);
		}
	}

	public IXholon getModel() {
		return model;
	}

	public void setModel(IXholon model) {
		this.model = (GameModel)model;
	}

	public IXholon getView() {
		return view;
	}

	public void setView(IXholon view) {
		this.view = (GameView)view;
	}

	public int getControllerState() {
		return controllerState;
	}

	public void setControllerState(int controllerState) {
		this.controllerState = controllerState;
	}
}
