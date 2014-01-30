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

/**
 * Dice is a container for a set of Die objects.
 * Players roll dice to determine the outcomes of battles (attacks).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Dice extends XhRisk {
	
	/**
	 * Roll dice, to determine the outcome of an attack.
	 * @param quantityDiceAttacker The number of dice used by the attacker.
	 * @param quantityDiceDefender The number of dic used by the defender.
	 * @return An outcome, as a boolean array,
	 * with one item in the array for each pair of attacker/defender dice rolled.
	 * A value of true reports a win by the attacker.
	 * A value of false reports a win by the defender.
	 */
	public boolean[] rollDice(int quantityDiceAttacker, int quantityDiceDefender)
	{
		int i;
		reset();
		Die dieAttacker[] = new Die[quantityDiceAttacker];
		Die nextDie = (Die)getFirstChild();
		for (i = 0; i < quantityDiceAttacker; i++) {
			dieAttacker[i] = nextDie; //new Die();
			nextDie = (Die)nextDie.getNextSibling();
		}
		Die dieDefender[] = new Die[quantityDiceDefender];
		while ("AttackerDie".equals(nextDie.getXhcName())) {
			nextDie = (Die)nextDie.getNextSibling();
		}
		for (i = 0; i < quantityDiceDefender; i++) {
			dieDefender[i] = nextDie; //new Die();
			nextDie = (Die)nextDie.getNextSibling();
		}
		// roll the dice
		for (i = 0; i < dieAttacker.length; i++) {
			dieAttacker[i].roll();
		}
		for (i = 0; i < dieDefender.length; i++) {
			dieDefender[i].roll();
		}
		// sort the results, to allow higher attacker rolls to be compared with higher defender rolls
		if (quantityDiceAttacker == 3) {
			if (dieAttacker[0].getVal_int() < dieAttacker[1].getVal_int()) {
				swap(dieAttacker[0], dieAttacker[1]);
			}
			if (dieAttacker[0].getVal_int() < dieAttacker[2].getVal_int()) {
				swap(dieAttacker[0], dieAttacker[2]);
			}
			if (dieAttacker[1].getVal_int() < dieAttacker[2].getVal_int()) {
				swap(dieAttacker[1], dieAttacker[2]);
			}
		}
		else if (quantityDiceAttacker == 2) {
			if (dieAttacker[0].getVal_int() < dieAttacker[1].getVal_int()) {
				swap(dieAttacker[0], dieAttacker[1]);
			}
		}
		if (quantityDiceDefender == 2) {
			if (dieDefender[0].getVal_int() < dieDefender[1].getVal_int()) {
				swap(dieDefender[0], dieDefender[1]);
			}
		}
		boolean[] outcome = new boolean[dieDefender.length];
		// compare attacker and defender die in order
		for (i = 0; i < dieDefender.length; i++) {
			if (dieAttacker[i].getVal_int() > dieDefender[i].getVal_int()) {
				outcome[i] = true;
			}
			else {
				// defender wins if it's a tie
				outcome[i] = false;
			}
		}
		return outcome;
	}
	
	/**
	 * Swap two Die values.
	 * @param one The first die.
	 * @param two The second die.
	 */
	protected void swap(Die one, Die two) {
		int temp = one.getVal_int();
		one.setVal(two.getVal_int());
		two.setVal(temp);
	}
	
	/**
	 * Set the dice to their default values.
	 */
	public void reset()
	{
		Die die = (Die)getFirstChild();
		while (die != null) {
			die.reset();
			die = (Die)die.getNextSibling();
		}
	}
	
}
