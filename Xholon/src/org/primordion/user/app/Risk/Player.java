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
import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;

import org.fr.lri.swingstates.sm.StateMachine;

/**
 * The Player class represents human and computer players as they play a game of Risk.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public abstract class Player extends XhRisk {
	
	/**
	 * The MVC controller processes events that are generated by a Player.
	 */
	private IXholon controller = null;
	
	/**
	 * Armies acquired during a turn that have not yet been placed on territories.
	 */
	private int unplacedArmies = 0;
	
	/**
	 * Whether or not this player has conquered at least one territory in the current turn.
	 */
	private boolean conqueror = false;
	
	/**
	 * The color that identifies this player and his/her/its territories.
	 */
	private String color = "#ffffff";
	
	/**
	 * The state machine that governs the actions that a player may take at any time.
	 */
	protected StateMachine actionStateMachine = null;
	
	public IXholon getController() {
		return controller;
	}

	public void setController(IXholon controller) {
		this.controller = controller;
	}

	public int getUnplacedArmies() {
		return unplacedArmies;
	}

	public void setUnplacedArmies(int unplacedArmies) {
		this.unplacedArmies = unplacedArmies;
	}
	
	public void incUnplacedArmies(int quantity) {
		unplacedArmies += quantity;
	}
	
	public void decUnplacedArmies(int quantity) {
		unplacedArmies -= quantity;
	}
	
	public boolean isConqueror() {
		return conqueror;
	}

	public void setConqueror(boolean conqueror) {
		this.conqueror = conqueror;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Does this player have unplaced armies?
	 * @return true or false
	 */
	protected boolean hasUnplacedArmies() {
		return (unplacedArmies > 0);
	}
	
	/**
	 * Does this player have five or more cards?
	 * @return true or false
	 */
	protected boolean hasFivePlusCards() {
		return (getFirstChild().getNumChildren(false) >= 5);
	}
	
	/**
	 * Does this player have any cards?
	 * @return true or false
	 */
	protected boolean hasCards() {
		return (getFirstChild().getNumChildren(false) > 0);
	}

	/**
	 * Count the number of reinforcement armies that this player will acquire
	 * at the start of the current turn.
	 * This is based on the current number of territories held.
	 * @return
	 */
	public int countReinforcementArmies()
	{
		int count = countOccupiedTerritories() / 3;
		if (count < 3) {
			count = 3;
		}
		return count;
	}
	
	/**
	 * Count the number of territories currently occupied by this player.
	 * @return
	 */
	public int countOccupiedTerritories()
	{
		int occupiedTerritories = 0;
		// locate the first territory, which is located in a different part of the tree
		IXholon continent = ((Players)getParentNode()).getWorld().getFirstChild();
		IXholon territory = continent.getFirstChild();
		while (territory != null) {
			if (((Territory)territory).getOccupier() == this) {
				// the currently located territory is occupied by this player, so add 1 to the count
				occupiedTerritories++;
			}
			// locate the next territory, which may be in a different continent
			territory = territory.getNextSibling();
			if (territory == null) {
				continent = continent.getNextSibling();
				if (continent != null) {
					territory = continent.getFirstChild();
				}
			}
		}
		return occupiedTerritories;
	}
	
	/**
	 * Get a list of all territories currently occupied by this player.
	 * @return
	 */
	public List getOccupiedTerritories()
	{
		List list = new ArrayList();
		// locate the first territory, which is located in a different part of the tree
		IXholon continent = ((Players)getParentNode()).getWorld().getFirstChild();
		IXholon territory = continent.getFirstChild();
		while (territory != null) {
			if (((Territory)territory).getOccupier() == this) {
				// the currently located territory is occupied by this player, so add it to the list
				list.add(territory);
			}
			// locate the next territory, which may be in a different continent
			territory = territory.getNextSibling();
			if (territory == null) {
				continent = continent.getNextSibling();
				if (continent != null) {
					territory = continent.getFirstChild();
				}
			}
		}
		return list;
	}
	
	/**
	 * Get a list of all continents currently completely occupied by this player.
	 * @return
	 */
	public List getOccupiedContinents()
	{
		List list = new ArrayList();
		IXholon continent = ((Players)getParentNode()).getWorld().getFirstChild();
		while (continent != null) {
			IXholon territory = continent.getFirstChild();
			while (territory != null) {
				if (((Territory)territory).getOccupier() != this) {
					break;
				}
				territory = territory.getNextSibling();
			}
			if (territory == null) {
				// this player occupies all territories in this continent 
				list.add(continent);
			}
			continent = continent.getNextSibling();
		}
		return list;
	}
	
	/**
	 * Get a random territory from among those currently occupied by this player.
	 * @return
	 */
	public IXholon getRandomTerritory()
	{
		List occupiedTerritories = getOccupiedTerritories();
		int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, occupiedTerritories.size());
		return (IXholon)occupiedTerritories.get(r);
	}
	
	/**
	 * Get a random territory to attack from.
	 * It must be a territory that is occupied by this player,
	 * and it must have more than one army,
	 * and it must have at least one adjacent territory that can be attacked.
	 * @return A territory, or null.
	 */
	public IXholon getRandomAttacker()
	{
		List occupiedTerritories = getOccupiedTerritories();
		Territory territory = null;
		for (int i = 0; i < occupiedTerritories.size() * 2; i++) {
			int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, occupiedTerritories.size());
			territory = (Territory)occupiedTerritories.get(r);
			if (territory.getQuantityArmies() > 1) {
				return territory;
			}
		}
		// random search failed, so find the first potential attacker if there is one
		for (int i = 0; i < occupiedTerritories.size(); i++) {
			territory = (Territory)occupiedTerritories.get(i);
			if ((territory.getQuantityArmies() > 1) && (hasPotentialDefender(territory))) {
				return territory;
			}
		}
		return null;
	}
	
	/**
	 * Get a random territory to defend.
	 * It must be a territory that's adjacent to the attacking territory,
	 * that is not occupied by the player who occupies the attacking territory.
	 * @param attackingTerritory The territory that will attack the defender.
	 * @return A territory, or null.
	 */
	public IXholon getRandomDefender(IXholon attackingTerritory)
	{
		// adjacent territories are referenced in the Territory port array
		// calculate the actual number of ports in the port array; this will be <= the maximum number
		int actualNum = 0;
		int maxNum = getApp().getMaxPorts();
		for (int i = 0; i < maxNum; i++) {
			if (attackingTerritory.getPort(i) == null) {break;}
			actualNum++;
		}
		Territory territory = null;
		for (int i = 0; i < actualNum * 2; i++) {
			int index = org.primordion.xholon.util.MiscRandom.getRandomInt(0, actualNum);
			territory = (Territory)attackingTerritory.getPort(index);
			if (territory.getOccupier() != this) {
				return territory;
			}
		}
		// random search failed, so find the first potential defender if there is one
		for (int i = 0; i < actualNum; i++) {
			territory = (Territory)attackingTerritory.getPort(i);
			if (territory.getOccupier() != this) {
				return territory;
			}
		}
		return null;
	}
	
	/**
	 * Get the first neighbor that can be attacked.
	 * The nighbor must not be occupied by the same player as the attacking territory.
	 * @param attackingTerritory The territory that will attack the defending neighbor.
	 * @return A neighboring territory, or null.
	 */
	public IXholon getFirstDefender(IXholon attackingTerritory)
	{
		int actualNum = 0;
		int maxNum = getApp().getMaxPorts();
		for (int i = 0; i < maxNum; i++) {
			if (attackingTerritory.getPort(i) == null) {break;}
			actualNum++;
		}
		Territory neighbor = null;
		for (int i = 0; i < actualNum; i++) {
			neighbor = (Territory)attackingTerritory.getPort(i);
			if (neighbor.getOccupier() != this) {
				return neighbor;
			}
		}
		return null;
	}
	
	/**
	 * Does the attacking territory have at least one neighbor that can be attacked?
	 * @param attackingTerritory
	 * @return true or false
	 */
	public boolean hasPotentialDefender(IXholon attackingTerritory)
	{
		return (getFirstDefender(attackingTerritory) == null) ? false : true;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case IRiskSignal.SIG_CONQUERED_TERRITORY:
			actionStateMachine.processEvent("conqueredTerritory");
			break;
		case IRiskSignal.SIG_ELIMINATED_PLAYER:
			actionStateMachine.processEvent("eliminatedPlayer");
			break;
		case IRiskSignal.SIG_STARTED_TURN:
			actionStateMachine.processEvent("startTurn");
			break;
		case IRiskSignal.SIG_ACK:
			actionStateMachine.processEvent("ack");
			break;
		case IRiskSignal.SIG_NAK:
			actionStateMachine.processEvent("nak");
			break;
		case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
			String command = (String)msg.getData();
			println("Command received:[" + command + "]");
			break;
		default:
			// give the superclass a chance to process the message
			super.processReceivedMessage(msg);
			break;
		}
	}
	
	// Actions that a human or other player can request.
	protected static final String startTurnAction = "Start turn";
	protected static final String attackAction = "Attack";
	protected static final String moveArmiesAction = "Move armies";
	protected static final String moveArmiesNoneAction = "Move armies none";
	protected static final String placeArmiesAction = "Place armies";
	protected static final String useCardsAction = "Use cards";
	protected static final String endTurnAction = "End turn";
	protected static final String showCardsAction = "Show cards";
	protected static final String showTerritoriesAction = "Show territories";
	protected static final String showContinentsAction = "Show continents";
	// actions that are NOT in the action list
	protected static final String doTurnAction = "Do turn";
	
	/** action list */
	private String[] actions = {
		startTurnAction,
		useCardsAction,
		placeArmiesAction,
		attackAction,
		moveArmiesAction,
		moveArmiesNoneAction,
		endTurnAction,
		showCardsAction,
		showTerritoriesAction,
		showContinentsAction
	};
	
	/** At any given time, some actions are enabled (true) and others are disabled (false). */
	private boolean[] actionsEnabled = {
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true
	};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		int count = 0;
		for (int i = 0; i < actionsEnabled.length; i++) {
			if (actionsEnabled[i]) {
				count++;
			}
		}
		String[] possibleActions = new String[count];
		count = 0;
		for (int i = 0; i < actionsEnabled.length; i++) {
			if (actionsEnabled[i]) {
				possibleActions[count] = actions[i];
				count++;
			}
		}
		return possibleActions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/**
	 * Enable or disable a specified action.
	 * @param actionName The name of the action to enable or disable.
	 * @param enableDisable Whether to enable (true) or disable (false) the action.
	 */
	public void enableDisableAction(String actionName, boolean enableDisable)
	{
		if (actionName == null) {return;}
		for (int i = 0; i < actions.length; i++) {
			if (actionName.equals(actions[i])) {
				actionsEnabled[i] = enableDisable;
				break;
			}
		}
	}
	
	/**
	 * Enable or disable all actions.
	 * @param enableDisable Whether all actions should be enabled (true) or disabled (false).
	 */
	public void enableDisableAllActions(boolean enableDisable)
	{
		for (int i = 0; i < actionsEnabled.length; i++) {
			actionsEnabled[i] = enableDisable;
		}
	}
	
	/**
	 * Enable standard actions that are available in any state.
	 */
	public void enableStandardActions()
	{
		enableDisableAction(showCardsAction, true);
		enableDisableAction(showTerritoriesAction, true);
		enableDisableAction(showContinentsAction, true);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		if (action == null) {return;}
		int signal = IRiskSignal.SIG_NULL;
		if (action.equals(startTurnAction)) {
			actionStateMachine.processEvent("startTurn");
			return;
		}
		else if (action.equals(useCardsAction)) {
			actionStateMachine.processEvent("useCards");
			return;
		}
		else if (action.equals(placeArmiesAction)) {
			actionStateMachine.processEvent("placeArmies");
			return;
		}
		else if (action.equals(attackAction)) {
			actionStateMachine.processEvent("attack");
			return;
		}
		else if (action.equals(moveArmiesAction)) {
			actionStateMachine.processEvent("moveArmies");
			return;
		}
		else if (action.equals(moveArmiesNoneAction)) {
			actionStateMachine.processEvent("moveArmiesNone");
			return;
		}
		else if (action.equals(endTurnAction)) {
			actionStateMachine.processEvent("endTurn");
			return;
		}
		else if (action.equals(showCardsAction)) {
			actionStateMachine.processEvent("showCards");
			return;
		}
		else if (action.equals(showTerritoriesAction)) {
			actionStateMachine.processEvent("showTerritories");
			return;
		}
		else if (action.equals(showContinentsAction)) {
			actionStateMachine.processEvent("showContinents");
			return;
		}
		controller.sendMessage(signal, null, this);
	}
	
	public int setAttributeVal(String attrName, String attrVal)
	{
		if ("color".equals(attrName)) {
			color = attrVal;
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#toString()
	 */
	public String toString()
	{
		return super.toString() + " unplacedArmies:" + unplacedArmies;
	}
	
}