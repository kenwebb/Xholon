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

import org.fr.lri.swingstates.events.VirtualEvent;
import org.fr.lri.swingstates.sm.State;
import org.fr.lri.swingstates.sm.StateMachine;
import org.fr.lri.swingstates.sm.Transition;
import org.fr.lri.swingstates.sm.transitions.Event;

/**
 * A human player.
 * The behavior of this class is implemented using a SwingStates state machine.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://swingstates.sourceforge.net/">SwingStates website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class HumanPlayer extends Player {
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#postConfigure()
	 */
	public void postConfigure() {
		createStateMachine();
		super.postConfigure();
	}
	
	/**
	 * Action state in the game of Risk.
	 * This subclass of State adds several actions that are valid no matter which state the state machine is in.
	 */
	class RiskActionState extends State {
	  public RiskActionState() {
	    super();
	    println("RiskActionState() constructor");
	    this.addTransition(showCards);
	    this.addTransition(showTerritories);
	    this.addTransition(showContinents);
	  }
	  Transition showCards = new Event("showCards") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_CARDS, null, HumanPlayer.this);
			}
		};
	  Transition showTerritories = new Event("showTerritories") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_TERRITORIES, null, HumanPlayer.this);
			}
		};
	  Transition showContinents = new Event("showContinents") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_CONTINENTS, null, HumanPlayer.this);
			}
		};
	}
	
	/**
	 * Create and start the actions state machine.
	 */
	@SuppressWarnings("unused")
	public void createStateMachine()
	{
		println("Starting HumanPlayer createStateMachine ...");
		consoleLog("Starting HumanPlayer createStateMachine ...");
		
		actionStateMachine = new StateMachine() {
			
			/************************ State settingUpGame ********************
			 * 
			 * A new game is being set up.
			 */
			public final State settingUpGame = new RiskActionState() {
				public void enter() {
					println("entering state: settingUpGame");
				}
				Transition setUpDone = new Event("setUpDone", ">> waiting") {};
				//setUpDone.appendChild(settingUpGame); // won't compile
				//this.addTransition(setUpDone); // won't compile
				public void leave() {
					println("leaving state: settingUpGame");
				}
			};
			
			/************************ State waiting ********************
			 * 
			 * This player is waiting for the game to begin, or for other players to finish their turns.
			 */
			public State waiting = new RiskActionState() {
				public void enter() {
					println("entering state: waiting");
					enableDisableAllActions(false);
					enableStandardActions();
					enableDisableAction(startTurnAction, true);
				}
				Transition startTurn = new Event("startTurn", ">> hasUnplacedArmies") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_START_TURN, null, HumanPlayer.this);
					};
				};
				public void leave() {
					println("leaving state: waiting");
				}
			};
			
			/************************ State hasUnplacedArmies ********************
			 * 
			 * This player has armies that have not yet been placed on owned territories.
			 */
			public State hasUnplacedArmies = new RiskActionState() {
				public void enter() {
					println("entering state: hasUnplacedArmies");
					enableDisableAllActions(false);
					enableStandardActions();
					if (hasCards()) {enableDisableAction(useCardsAction, true);}
					enableDisableAction(placeArmiesAction, true);
				}
				Transition placeArmiesOnly = new Event("placeArmies", ">> hasUnplacedArmies") {
					// this simulates an unconditional transition to a choice point
					// it returns false to force the state machine to also try other transitions
					public boolean guard() {
						getController().sendSyncMessage(IRiskSignal.SIG_PLACE_ARMIES, null, HumanPlayer.this);
						return false;
					}
				};
				Transition placeArmies = new Event("placeArmies", ">> mayAttack") {
					public boolean guard() {
						if (hasFivePlusCards()) {return false;}
						if (hasUnplacedArmies()) {return false;}
						return true;
					}
					public void action() {
						System.out.println("placeArmies action");
					}
				};
				Transition useCards = new Event("useCards", ">> hasUnplacedArmies") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_USE_CARDS, null, HumanPlayer.this);
					}
				};
				public void leave() {
					println("leaving state: hasUnplacedArmies");
				}
			};
			
			/************************ State mayAttack ********************
			 * 
			 * This player may attack other player's territories, if she/he wishes.
			 */
			public State mayAttack = new RiskActionState() {
				public void enter() {
					println("entering state: mayAttack");
					enableDisableAllActions(false);
					enableStandardActions();
					enableDisableAction(attackAction, true);
					enableDisableAction(moveArmiesAction, true);
					enableDisableAction(endTurnAction, true);
				}
				Transition attack = new Event("attack", ">> mayAttack") {
					public boolean guard() {
						if (hasFivePlusCards()) {return false;}
						if (hasUnplacedArmies()) {return false;}
						return true;
					}
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_ATTACK, null, HumanPlayer.this);
					}
				};
				/**
				 * The conqueredTerritory event originates with the GameController.
				 */
				Transition conqueredTerritory = new Event("conqueredTerritory", ">> wonInvasion") {};
				/**
				 * The eliminatedPlayer event originates with the GameController.
				 */
				Transition eliminatedPlayer = new Event("eliminatedPlayer", ">> hasUnplacedArmies") {};
				Transition moveArmies = new Event("moveArmies", ">> done") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_MOVE_ARMIES, null, HumanPlayer.this);
					}
				};
				Transition endTurn = new Event("endTurn", ">> waiting") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_END_TURN, null, HumanPlayer.this);
					}
				};
				public void leave() {
					println("leaving state: mayAttack");
				}
			};
			
			/************************ State wonInvasion ********************
			 * 
			 * This attacking player has won an invasion and has conquered a territory.
			 */
			public State wonInvasion = new RiskActionState() {
				public void enter() {
					println("entering state: wonInvasion");
					enableDisableAllActions(false);
					enableStandardActions();
					enableDisableAction(moveArmiesAction, true);
					enableDisableAction(moveArmiesNoneAction, true);
				}
				Transition moveArmies = new Event("moveArmies", ">> mayAttack") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_MOVE_ARMIES, null, HumanPlayer.this);
					}
				};
				Transition moveArmiesNone = new Event("moveArmiesNone", ">> mayAttack") {};
				public void leave() {
					println("leaving state: wonInvasion");
				}
			};
			
			/************************ State done ********************
			 * 
			 * There is nothing more that this player can do this turn.
			 */
			public State done = new RiskActionState() {
				public void enter() {
					println("entering state: done");
					enableDisableAllActions(false);
					enableStandardActions();
					enableDisableAction(endTurnAction, true);
				}
				Transition endTurn = new Event("endTurn", ">> waiting") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_END_TURN, null, HumanPlayer.this);
					}
				};
				public void leave() {
					println("leaving state: done");
				}
			};
			
		};
		println("Ending HumanPlayer createStateMachine 1");
		actionStateMachine.processEvent(new VirtualEvent("setUpDone"));
		println("Ending HumanPlayer createStateMachine 2");
	}
	
}
