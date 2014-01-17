/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.GameOfLife;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.service.IXholonService;
//import org.primordion.xholon.service.gameengine.IGameEngine;

/**
 * Conway's classic Game of Life. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 8, 2005)
 */
public class XhGameOfLife extends AbstractGrid {

	// Game of Life
	private int numNeighborsAlive = 0;
	public boolean alive = false; // has to be public so reflection can set its value
	
	/** Game Engine that can show the GOL grid. */
	//private IXholon gameEngine = null;
	
	/** Whether of not to use the game engine. */
	//private boolean shouldUseGameEngine = false;
	
	/**
	 * Constructor.
	 */
	public XhGameOfLife() {}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_boolean()
	 */
	public boolean getVal_boolean() {return alive;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(boolean)
	 */
	public void setVal(boolean val) {alive = val;}

	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		// Game of Life
		switch(xhc.getId()) {
		case CeGameOfLife.GridCellCE:
			numNeighborsAlive = 0;
			break;
		default:
			break;
		} // end switch
		
		super.preAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		// Game of Life
		switch(xhc.getId()) {
		case CeGameOfLife.GameOfLifeCE:
			//if (shouldUseGameEngine) {
			//	if (gameEngine == null) {
			//		gameEngine = getService(IXholonService.XHSRV_GAME_ENGINE);
			//		String params = "Game of Life,1000,1000,false,orthogonal,10,10,100,100";
			//		gameEngine.sendSyncMessage(IGameEngine.SIG_PROCESS_REQ, params, this);
			//	}
			//	//String params = "Game of Life,1000,1000,false,orthogonal,10,10,100,100";
			//	gameEngine.sendSyncMessage(IGameEngine.SIG_PROCESS_REQ, null, this);
			//}
			break;
		case CeGameOfLife.GridCellCE:
			if (port[P_NORTH]     != null) {if (((XhGameOfLife)port[P_NORTH]).alive)     {numNeighborsAlive++;}}
			if (port[P_NORTHEAST] != null) {if (((XhGameOfLife)port[P_NORTHEAST]).alive) {numNeighborsAlive++;}}
			if (port[P_EAST]      != null) {if (((XhGameOfLife)port[P_EAST]).alive)      {numNeighborsAlive++;}}
			if (port[P_SOUTHEAST] != null) {if (((XhGameOfLife)port[P_SOUTHEAST]).alive) {numNeighborsAlive++;}}
			if (port[P_SOUTH]     != null) {if (((XhGameOfLife)port[P_SOUTH]).alive)     {numNeighborsAlive++;}}
			if (port[P_SOUTHWEST] != null) {if (((XhGameOfLife)port[P_SOUTHWEST]).alive) {numNeighborsAlive++;}}
			if (port[P_WEST]      != null) {if (((XhGameOfLife)port[P_WEST]).alive)      {numNeighborsAlive++;}}
			if (port[P_NORTHWEST] != null) {if (((XhGameOfLife)port[P_NORTHWEST]).alive) {numNeighborsAlive++;}}
			break;
		default:
			break;
		} // end switch

		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		// Game of Life
		switch(xhc.getId()) {
		case CeGameOfLife.GridCellCE:
			if (numNeighborsAlive == 2) {
				// no change
			}
			else if (numNeighborsAlive == 3) {
				alive = true;
			}
			else { // 0, 1, 4, 5, 6, 7, 8 neighbors alive
				alive = false;
			}
			break;
		default:
			break;
		} // end switch
		
		super.postAct();
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		switch(xhc.getId()) {
		case CeGameOfLife.GridCellCE:
			return (getName() 
					+ " [alive:" + alive  
					+ "]");
		default:
			return (getName());
		}
	}

	//public boolean isShouldUseGameEngine() {
	//	return shouldUseGameEngine;
	//}

	//public void setShouldUseGameEngine(boolean shouldUseGameEngine) {
	//	this.shouldUseGameEngine = shouldUseGameEngine;
	//}
}
