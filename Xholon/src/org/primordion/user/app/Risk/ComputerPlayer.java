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
 * An automated player.
 * The behavior of this class is implemented using a SwingStates state machine.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://swingstates.sourceforge.net/">SwingStates website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class ComputerPlayer extends Player {
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#postConfigure()
	 */
	public void postConfigure() {
		createStateMachine();
		// only allow the standard "show" actions
		enableDisableAllActions(false);
		enableStandardActions();
		super.postConfigure();
	}
	
	/**
	 * Action state in the game of Risk.
	 * This subclass of State adds several actions that are valid no matter which state the state machine is in.
	 */
	class RiskActionState extends State {
		Transition showCards = new Event("showCards") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_CARDS, null, ComputerPlayer.this);
			}
		};
		Transition showTerritories = new Event("showTerritories") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_TERRITORIES, null, ComputerPlayer.this);
			}
		};
		Transition showContinents = new Event("showContinents") {
			public void action() {
				getController().sendSyncMessage(IRiskSignal.SIG_SHOW_CONTINENTS, null, ComputerPlayer.this);
			}
		};
	}
	
	/**
	 * Create and start the actions state machine.
	 */
	@SuppressWarnings("unused")
	public void createStateMachine()
	{
	  println("Starting ComputerPlayer createStateMachine ...");
		actionStateMachine = new StateMachine() {
			
			
			/************************ State settingUpGame ********************
			 * 
			 * A new game is being set up.
			 */
			public State settingUpGame = new RiskActionState() {
				public void enter() {
					println("entering state: settingUpGame");
				}
				Transition setUpDone = new Event("setUpDone", ">> waiting") {};
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
				}
				Transition startTurn = new Event("startTurn") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_START_TURN, null, ComputerPlayer.this);
					}
				};
				Transition ack = new Event("ack", ">> hasUnplacedArmies") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_PLACE_ARMIES, null, ComputerPlayer.this);
					}
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
				}
				Transition ack = new Event("ack", ">> mayAttack") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_ATTACK, null, ComputerPlayer.this);
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
				}
				Transition conqueredTerritory = new Event("conqueredTerritory", ">> wonInvasion") {};
				Transition ack = new Event("ack", ">> mayAttack") {
					// the attack took place as requested
					public void action() {
						// the territory was not conquered on this attack, but the attack itself may have been successful
						getController().sendMessage(IRiskSignal.SIG_ATTACK, null, ComputerPlayer.this);
					}
				};
				Transition nak = new Event("nak", ">> waiting") {
					// the attack could not take place; no territory to attack from, etc.
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_END_TURN, null, ComputerPlayer.this);
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
				}
				Transition ack = new Event("ack", ">> mayAttack") {
					public void action() {
						getController().sendMessage(IRiskSignal.SIG_MOVE_ARMIES, null, ComputerPlayer.this);
					}
				};
				public void leave() {
					println("leaving state: wonInvasion");
				}
			};
			
		};
		println("Ending ComputerPlayer createStateMachine 1");
		actionStateMachine.processEvent(new VirtualEvent("setUpDone"));
		println("Ending ComputerPlayer createStateMachine 2");
	}
	
}
