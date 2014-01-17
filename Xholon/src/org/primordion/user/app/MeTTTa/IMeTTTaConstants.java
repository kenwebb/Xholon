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

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 17, 2008)
*/
public interface IMeTTTaConstants {

	// Occupant of a grid position
	/** This grid position not yet played. */
	public static final int G_NULL = 0;
	/** This grid position played by human. */
	public static final int G_HUMAN = 1;
	/** This grid position played by computer. */
	public static final int G_COMPUTER = 2;

	// Game statuses
	/** More of the game remains to be played. */
	public static final int GS_MORE = 0;
	/** Computer has won. */
	public static final int GS_CWIN = 1;
	/** Human has won. */
	public static final int GS_HWIN = 2;
	/** The game was a draw. */
	public static final int GS_DRAW = 3;

	// Game statuses used in test4Win()
	/** Either player has won. */
	public static final int GS_WIN = 8;
	/** Game not yet won by either player. */
	public static final int GS_NOTYET = 9;
	
	// Controller states
	/** Not known who's turn it is. */
	public static final int TURN_NULL     = 0;
	/** Waiting for the Human player to make a move. */
	public static final int TURN_HUMAN    = 1;
	/** Waiting for the Computer player to make a move. */
	public static final int TURN_COMPUTER = 2;
}
