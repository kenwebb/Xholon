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

import java.util.List;
import java.util.Random;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 17, 2008)
*/
public class MeTTTaPojo implements IMeTTTaPojo, IMeTTTaConstants {

	/** How likely it is that a completely random move will be selected.
	 *  The lower the value, the more likely a random move is.
	 *  A value between 1 and 10 inclusive.
	 *  A good default value is 8.
	 *  To turn off random moves, set the value to 10;
	 */
	protected static final int CMOVE_CUTOFF = 8;
	
	/** Random number generator. */
	private static Random random = new Random();
	
	/** Groups of positions that constitute a win. */
	protected static WinPos winPos[];
	
	// static initializer
	static {
		winPos = new WinPos[8];
		for (int i = 0; i < 8; i++) {
			winPos[i] = new WinPos();
		}
		setGameVars();
	}
	
	/**
	 * Combinations of positions that constitute a win.
	 */
	protected static class WinPos {
		protected int wPos1;
		protected int wPos2;
		protected int wPos3;
	}
	
	/* 
	 * @see org.primordion.user.app.MeTTTa.IMeTTTaBusinessDelegate#doComputerMove(java.util.List)
	 */
	public int getCalculatedMove(List grid) {
		Object[] gInteger = grid.toArray();
		int[] gInt = new int[gInteger.length];
		for (int i = 0; i < gInteger.length; i++) {
			gInt[i] = ((Integer)gInteger[i]).intValue();
		}
		int moveNum = getMoveNum(gInt);
		int position;
		if (getRandomInt(1, 11) > CMOVE_CUTOFF) {
			position = getRandMove(gInt, moveNum);
		}
		else {
			position = getCalcMove(gInt, moveNum);
		}
		//org.primordion.xholon.base.XholonTime.sleep(2000);
		return position;
	}
	
	/**
	 * Compute the move number.
	 * @param grid The positions that have been played or not played up to now.
	 * @return A move number, between 1 and 9.
	 */
	protected int getMoveNum(int[] grid) {
		int currentMoveNum = 0;
		// count the number of moves already made
		for (int i = 0; i < grid.length; i++) {
			if (!(grid[i] == G_NULL)) {
				currentMoveNum++;
			}
		}
		// return number of moves so far + 1
		return currentMoveNum + 1;
	}
	
	/**
	 * Get a random computer move.
	 * @param grid The positions that have been played or not played up to now.
	 * @param moveNum A move number, between 1 and 9.
	 * @return A position in the grid.
	 */
	protected int getRandMove(int[] grid, int moveNum) {
		int position = -1;
		int numNull = 0;
		int rval = getRandomInt(0, 9 - moveNum);
		for (int i = 0; i < 9; i++) {
			if (grid[i] == G_NULL) {
				if (rval == numNull) {
					position = i;
					break;
				}
				numNull++;
			}
		}
		return position;
	}
	
	/**
	 * Calculate the computer's next move.
	 * @param grid The positions that have been played or not played up to now.
	 * @param moveNum A move number, between 1 and 9.
	 * @return A position in the grid.
	 */
	protected int getCalcMove(int[] grid, int moveNum) {
		int position;
		position = selectWin(grid, moveNum);
		if (position != -1) {return position;}
		position = selectNoLose(grid, moveNum);
		if (position != -1) {return position;}
		position = selectBest(grid, moveNum);
		while (position == -1) {
			position = getRandMove(grid, moveNum);
		}
		return position;
	}
	
	/**
	 * Select an immediate winning move.
	 * @param grid The positions that have been played or not played up to now.
	 * @param moveNum A move number, between 1 and 9.
	 * @return A position in the grid.
	 */
	protected int selectWin(int[] grid, int moveNum) {
		int position = -1;
		for (int i = 0; i < 9; i++) {
			if (grid[i] == G_NULL) {
				grid[i] = G_COMPUTER;
				int result = test4Win(grid, G_COMPUTER);
				grid[i] = G_NULL;
				if (result == GS_WIN) {
					position = i;
					break;
				}
			}
		}
		return position;
	}
	
	/**
	 * Select a move to block an immediate win by other player.
	 * @param grid The positions that have been played or not played up to now.
	 * @param moveNum A move number, between 1 and 9.
	 * @return A position in the grid.
	 */
	protected int selectNoLose(int[] grid, int moveNum) {
		int position = -1;
		for (int i = 0; i < 9; i++) {
			if (grid[i] == G_NULL) {
				grid[i] = G_HUMAN;
				int result = test4Win(grid, G_HUMAN);
				grid[i] = G_NULL;
				if (result == GS_WIN) {
					position = i;
					break;
				}
			}
		}
		return position;
	}
	
	/**
	 * Select the best offensive move.
	 * @param grid The positions that have been played or not played up to now.
	 * @param moveNum A move number, between 1 and 9.
	 * @return A position in the grid.
	 */
	protected int selectBest(int[] grid, int moveNum) {
		int position = -1;
		switch (moveNum) {
		case 1:
			position = -1;
			break;
		case 2:
			if (grid[4] == G_HUMAN) {
				switch (getRandomInt(0, 4)) {
				case 0: position = 0; break;
				case 1: position = 2; break;
				case 2: position = 6; break;
				case 3: position = 8; break;
				default: break;
				}
			}
			else if (  (grid[0] == G_HUMAN)
					|| (grid[2] == G_HUMAN)
					|| (grid[6] == G_HUMAN)
					|| (grid[8] == G_HUMAN)) {
				position = 4;
			}
			else {
				for (int i = 1; i < 9; i+=2) {
					if (grid[i] == G_HUMAN) {
						position = 9 - i;
						break;
					}
				}
			}
			break;
		default:
			position = selectBest_3_8(grid);
			break;
		}
		return position;
	}
	
	/**
	 * Select best offensive move, if this is 3rd to 8th move.
	 * @param grid The positions that have been played or not played up to now.
	 * @return A position in the grid.
	 */
	protected int selectBest_3_8(int[] grid) {
		int bestPos = -1;
		int previousScore = 0;
		int currentScore = 0;
		int g = G_COMPUTER;
		int gn = G_NULL;
		
		for (int testPos = 0; testPos < 9; testPos++) {
			if (grid[testPos] == G_NULL) {
				currentScore = 1;
				switch (testPos) {
				case 0:
					if ((grid[3] == g) && (grid[6] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[3] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[1] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[2] == g) && (grid[1] == gn)) {currentScore++;}
					break;
				case 1:
					if ((grid[0] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[2] == g) && (grid[0] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[7] == gn)) {currentScore++;}
					if ((grid[7] == g) && (grid[4] == gn)) {currentScore++;}
					break;
				case 2:
					if ((grid[5] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[5] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[6] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[0] == g) && (grid[1] == gn)) {currentScore++;}
					if ((grid[1] == g) && (grid[0] == gn)) {currentScore++;}
					break;
				case 3:
					if ((grid[0] == g) && (grid[6] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[0] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[5] == gn)) {currentScore++;}
					if ((grid[5] == g) && (grid[4] == gn)) {currentScore++;}
					break;
				case 4:
					if ((grid[0] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[0] == gn)) {currentScore++;}
					if ((grid[1] == g) && (grid[7] == gn)) {currentScore++;}
					if ((grid[7] == g) && (grid[1] == gn)) {currentScore++;}
					if ((grid[2] == g) && (grid[6] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[3] == g) && (grid[5] == gn)) {currentScore++;}
					if ((grid[5] == g) && (grid[3] == gn)) {currentScore++;}
					break;
				case 5:
					if ((grid[2] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[3] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[3] == gn)) {currentScore++;}
					break;
				case 6:
					if ((grid[0] == g) && (grid[3] == gn)) {currentScore++;}
					if ((grid[3] == g) && (grid[0] == gn)) {currentScore++;}
					if ((grid[2] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[7] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[7] == gn)) {currentScore++;}
					break;
				case 7:
					if ((grid[1] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[1] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[8] == gn)) {currentScore++;}
					if ((grid[8] == g) && (grid[6] == gn)) {currentScore++;}
					break;
				case 8:
					if ((grid[0] == g) && (grid[4] == gn)) {currentScore++;}
					if ((grid[4] == g) && (grid[0] == gn)) {currentScore++;}
					if ((grid[2] == g) && (grid[5] == gn)) {currentScore++;}
					if ((grid[5] == g) && (grid[2] == gn)) {currentScore++;}
					if ((grid[6] == g) && (grid[7] == gn)) {currentScore++;}
					if ((grid[7] == g) && (grid[6] == gn)) {currentScore++;}
					break;
				default:
					break;
				} // end switch
				if (currentScore > previousScore) {
					bestPos = testPos;
					previousScore = currentScore;
				}
				if (currentScore == previousScore) {
					if (getRandomInt(1, 3) == 1) {
						bestPos = testPos;
					}
				}
			} // end if
		} // end for
		return bestPos;
	}
	
	/**
	 * Test for a win.
	 * @param grid The positions that have been played or not played up to now.
	 * @param player Player: G_HUMAN or G_COMPUTER
	 * @return Game status: GS_WIN or GS_NOTYET
	 */
	public static int test4Win(int[] grid, int player) {
		int winStatus = GS_NOTYET;
		for (int i = 0; i < 8; i++) {
			if (	   (grid[winPos[i].wPos1] == player)
					&& (grid[winPos[i].wPos2] == player)
					&& (grid[winPos[i].wPos3] == player)) {
				winStatus = GS_WIN;
				break;
			}
		}
		return winStatus;
	}
	
	/**
	 * Set game parameters.
	 */
	public static void setGameVars() {
		winPos[0].wPos1 = 0;
		winPos[0].wPos2 = 1;
		winPos[0].wPos3 = 2;
		
		winPos[1].wPos1 = 3;
		winPos[1].wPos2 = 4;
		winPos[1].wPos3 = 5;
		
		winPos[2].wPos1 = 6;
		winPos[2].wPos2 = 7;
		winPos[2].wPos3 = 8;
		
		winPos[3].wPos1 = 0;
		winPos[3].wPos2 = 3;
		winPos[3].wPos3 = 6;
		
		winPos[4].wPos1 = 1;
		winPos[4].wPos2 = 4;
		winPos[4].wPos3 = 7;
		
		winPos[5].wPos1 = 2;
		winPos[5].wPos2 = 5;
		winPos[5].wPos3 = 8;
		
		winPos[6].wPos1 = 0;
		winPos[6].wPos2 = 4;
		winPos[6].wPos3 = 8;
		
		winPos[7].wPos1 = 2;
		winPos[7].wPos2 = 4;
		winPos[7].wPos3 = 6;
	}
	
	/**
	 * Get random integer >= minVal and < maxVal.
	 * @param minVal The result will be >= this value.
	 * @param maxVal The result will be < this value.
	 * @return A pseudo-random integer.
	 */
	public static int getRandomInt( int minVal, int maxVal )
	{
		float rVal = (random.nextFloat() * (maxVal - minVal)) + minVal;
		int rNum = (int)rVal;
		
		return rNum;
	}
	
	/**
	 * main, for testing
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("MeTTTaPojo main() started ...");
		MeTTTaPojo mp = new MeTTTaPojo();
		List grid = new java.util.ArrayList(9);
		for (int i = 0; i < 9; i++) {
			int val = 0;
			grid.add(new Integer(val));
		}
		int position = mp.getCalculatedMove(grid);
		System.out.println("position:" + position);
	}
}
