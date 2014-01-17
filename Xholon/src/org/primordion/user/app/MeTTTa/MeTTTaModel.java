/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.user.app.MeTTTa;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IMessage;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class MeTTTaModel extends XhMeTTTa implements IMeTTTaConstants {
	
	// ports
	private IXholon view = null;

	/** The current player for each position in the 3 x 3 grid. */
	private int[] grid = new int[9];
	
	/** The number of positions that have been played so far in this game. */
	private int numPositionsPlayed = 0;
	
	/** Current state of the game. Who's turn is it? */
	private int gameState = TURN_HUMAN;
	
	/**
	 * default constructor
	 */
	public MeTTTaModel() {
		for (int position = 0; position < 9; position++) {
			grid[position] = G_NULL;
		}
		numPositionsPlayed = 0;
	}
	
	@Override
	public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		case MeTTTaModelCE:
			switch (msg.getSignal()) {
			case SIG_HUMAN_SELECTION:
				// ignore; this message is only sent so it will appear in sequence diagrams
				break;
			}
			break;
		default:
			logger.warn("Model received an unexpected message:" + msg);
			break;
		}
	}
	
	/**
	 * Clear the TTT grid, and set number of positions played to 0.
	 * This should be called when starting a new game.
	 */
	public void clearGrid() {
		for (int position = 0; position < 9; position++) {
			grid[position] = G_NULL;
		}
		numPositionsPlayed = 0;
		// notify listeners (View) that the model has changed.
		view.sendMessage(SIG_MODEL_UPDATED, null, this);
	}
	
	/**
	 * Get a copy of the grid that contains the current values of the 9 TTT positions.
	 * @return The grid as a List.
	 */
	public List getGrid() {
		List clonedGrid = new ArrayList(grid.length);
		for (int position = 0; position < grid.length; position++) {
			clonedGrid.add(new Integer(grid[position]));
		}
		return clonedGrid;
	}
	
	/**
	 * Return which player, if any, currently occupies the specified position.
	 * @param position A number between 0 and 8.
	 * @return G_HUMAN=1, G_COMPUTER=2, or G_NULL=0.
	 */
	public int getPlayer(int position) {
		if ((position < 0) || (position > 8)) {return G_NULL;}
		return grid[position];
	}
	
	/**
	 * Set which player occupies the specified position.
	 * If the position is already occupied, it won't be changed.
	 * @param position A number between 0 and 8.
	 * @param player G_HUMAN=1, G_COMPUTER=2, or G_NULL=0
	 */
	public void setPlayer(int position, int player) {
		if ((position < 0) || (position > 8)) {return;}
		if ((player < G_NULL) || (player > G_COMPUTER)) {return;}
		if (isPlayed(position)) {return;}
		grid[position] = player;
		numPositionsPlayed++;
		// notify listeners (View) that the model has changed.
		view.sendMessage(SIG_MODEL_UPDATED, null, this);
	}
	
	/**
	 * Get the number of positions that have been played up to now.
	 * @return The number of positions played.
	 */
	public int getNumPositionsPlayed() {
		return numPositionsPlayed;
	}
	
	/**
	 * Has this position already been played by either player?
	 * @param position A number between 0 and 8.
	 * @return true or false
	 */
	public boolean isPlayed(int position) {
		if ((position < 0) || (position > 8)) {return true;}
		return grid[position] != G_NULL;
	}
	
	/**
	 * Is the game over?
	 * @return true or false
	 */
	public boolean isGameOver() {
		if (MeTTTaPojo.test4Win(grid, G_HUMAN) == GS_WIN) {return true;}
		if (MeTTTaPojo.test4Win(grid, G_COMPUTER) == GS_WIN) {return true;}
		return numPositionsPlayed == 9;
	}
	
	/**
	 * Get the view.
	 * @return
	 */
	public IXholon getView() {
		return view;
	}
	
	/**
	 * Set the view.
	 * @param view
	 */
	public void setView(IXholon view) {
		this.view = view;
	}

	/**
	 * Get the current game state.
	 * @return TURN_NULL, TURN_HUMAN, or TURN_COMPUTER
	 */
	public int getGameState() {
		return gameState;
	}
	
	/**
	 * Set the current game state.
	 * @param gameState TURN_NULL, TURN_HUMAN, or TURN_COMPUTER
	 */
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
}
