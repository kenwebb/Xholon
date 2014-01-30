/* Ealontro - systems that evolve and adapt to their environment
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.ealontro.app.AntForaging;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IActivity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.util.MiscRandom;

/**
 * Ant Foraging System WITHOUT Genetic Programming.
 * <p>source: Koza, J. (1992). Genetic Programming. p.329-344</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on June 15, 2006)
 */
public class XhAntForaging extends AbstractGrid implements IActivity, CeAntForaging {

	// column and row constants
	protected static final int MOVE_COL_DIRECTION = 0; // move west or east
	protected static final int MOVE_ROW_DIRECTION = 1; // move north or south
	// pheromone constants
	protected static final double MAX_DROP_SIZE = 100.0; // dropSize when first pick up pheromone
	protected static final double MIN_DROP_SIZE =   1.0; // minmal dropSize on return to nest
	protected static final double DROP_SIZE_DECREMENT = 1.0; // amount to decrease dropSize each timestep
	protected static final double PHEROMONE_MIN_SIGNIFICANT = 0.0; // amount considered significant in ifPheromoneHere()
	protected static final double PHEROMONE_MIN_DISPLAY = 1.0; // display 'p' if at least this much pheromone (1.0)
	protected static boolean usePheromone = true; // whether or not to use pheromone, or just have ants move randomly
	protected static double pheromoneEvaporationRate = 0.0; // these 3 used by GridCell (the environment)
	protected static double pheromoneDiffusionRate   = 0.0;
	protected static boolean usePheromoneDiffusion = false;
	protected static int numNeighbors = 8; // Moore neighborhood where each node has 8 neighbors
	// food constants
	protected static final int FOOD_HAS_NONE = 0;
	protected static final int FOOD_HAS = 1;
	protected static final int FOOD_HAS_NONE_NEVER = -1; // for use by GridCell, so can restore on reconfigure()
	
	protected boolean hasMoved; // whether ant instance has had its turn to act() this timestep
	public boolean hasNest;
	public double pheromoneLevel;
	public double dropSize; // amount of pheromone the ant should drop each timestep
	protected int nestDistanceCol; // relative distance to the nest; used by ants
	protected int nestDistanceRow;
	public int food; // Ant, GridCell, AntSystem all use this for slightly different purposes
	protected int rawFitness;
	public String roleName;
	
	/**
	 * Constructor.
	 */
	public XhAntForaging() {}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		hasMoved = false;
		hasNest = false;
		pheromoneLevel = 0.0;
		dropSize = 0.0;
		nestDistanceCol = 0;
		nestDistanceRow = 0;
		food = FOOD_HAS_NONE_NEVER;
		rawFitness = 0;
	}
	
	//                          Setters and Getters
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal() {
		switch (getXhcId()) {
		case FoodCE:
			// called on Food xholon by chart
			return ((XhAntForaging)port[P_BEHAVIOR]).getRawFitness();
		default:
			// getVal() should not be called on any other Xholon
			System.err.println("XhAntForaging getVal() called on: " + getName());
			return 0.0;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.AbstractGrid#setPorts()
	 */
	public void setPorts()
	{
		switch(xhc.getId()) {
		case AntCE:
		case FoodCE:
		case GeneticProgramCE:
			// Because these nodes inherit from AbstractGrid but are not really grid nodes,
			// they won't normally get their ports set.
			// It's being done manually here.
			port = new IXholon[1];
			break;
		default:
			super.setPorts();
			break;
		}
	}

	/**
	 * Set food quantity.
	 * @param fq The food quantity.
	 */
	public void setFoodQuantity(int fq)
	{
		food = fq;
	}
	/**
	 * Get food quantity.
	 * @return The food quantity.
	 */
	public int getFoodQuantity()
	{
		return food;
	}
	
	public void setFood(double fq) {
	  food = (int)fq;
	}
	public double getFood() {
	  return food;
	}
	
	@Override
	public int setAttributeVal(String attrName, String attrValue) {
	  if ("food".equals(attrName)) {
	    this.setFood(Integer.parseInt(attrValue));
	  }
	  else if ("hasNest".equals(attrName)) {
	    hasNest = Boolean.parseBoolean(attrValue);
	  }
	  return 0;
	}
	
	/**
	 * Set current fitness score for this GeneticProgram (typically used to reset to zero).
	 * @param rawFitness The new raw fitness value.
	 */
	public void setRawFitness(int rawFitness) {
		this.rawFitness = rawFitness;
	}
	/**
	 * Get current fitness score for this GeneticProgram.
	 * @return The current raw fitness value.
	 */
	public int getRawFitness() {
		return rawFitness;
	}
	
	/**
	 * Adjust the fitness. Called by Ant.
	 * Accumulated within the Behavior instance referenced by Ant.
	 */
	public void adjustRawFitness()
	{
		if ((port != null) && (port[P_BEHAVIOR] != null)) {
			((XhAntForaging)port[P_BEHAVIOR]).incRawFitness(1);
		}
	}
	
	/**
	 * Increase the raw fitness of this GeneticProgram.
	 * @param amount Amount by which to increase the fitness.
	 */
	public void incRawFitness(int amount) {
		rawFitness += amount;
	}
	
	/**
	 * Determine whether GridCell or Ant have any food.
	 * @return true or false
	 */
	public boolean hasFood()
	{
		return food > 0 ? true : false;
	}
	
	/**
	 * Move directly to nest as part of reconfiguration.
	 * This restores the ant's position before starting a new GP generation.
	 */
	protected void moveDirectlyToNest()
	{
		IXholon newParentNode = getParentNode();
		while (!((nestDistanceCol == 0) && (nestDistanceRow == 0))) {
			if (nestDistanceCol > 0) {
				nestDistanceCol--;
				newParentNode = ((AbstractGrid)newParentNode).port[P_WEST];
			}
			else if (nestDistanceCol < 0) {
				nestDistanceCol++;
				newParentNode = ((AbstractGrid)newParentNode).port[P_EAST];
			}
			if (nestDistanceRow > 0) {
				nestDistanceRow--;
				newParentNode = ((AbstractGrid)newParentNode).port[P_NORTH];
			}
			else if (nestDistanceRow < 0) {
				nestDistanceRow++;
				newParentNode = ((AbstractGrid)newParentNode).port[P_SOUTH];
			}
		}
		removeChild(); // detach subtree from parent and sibling links
		appendChild(newParentNode); // insert as the last child of the new parent
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		// find Structure, because needs to be done before I print the grid
		// Structure's nextSibling is GeneticProgram
		switch(xhc.getId()) {
		case StructureCE:
			if (Msg.appM) {
				System.out.println(" Food at nest: " + ((XhAntForaging)nextSibling).getRawFitness());
			}
			break;
		case GridCellCE:
			if (Msg.appM) {
				// display whether or not there's an Ant here
				if (firstChild != null) {
					int numChildren = getChildNodes(false).size();
					if (numChildren > 9) {
						numChildren = 0;
					}
					System.out.print(numChildren + " ");
				}
				else if (hasNest) {
					System.out.print("n ");
				}
				else if (hasFood()) {
					System.out.print("f ");
				}
				else if (getPheromoneLevel() >= PHEROMONE_MIN_DISPLAY) {
					System.out.print("p ");
				}
				else {
					System.out.print("_ ");
				}
				if (nextSibling == null) { // end of this row
					System.out.println();
					if (parentNode.getNextSibling() == null) { // end of last row
						System.out.println();
					}
				}
			}
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
		int i;
		super.act();
		switch(xhc.getId()) {
		case PopulationCE: // debug only
			//System.out.println("act() Population");
			break;
		case GeneticProgramCE: // debug only
			//System.out.println("act() GeneticProgram");
			break;
		case AntCE:
			if (hasMoved == false) {
				if (port[P_BEHAVIOR] == null) {
					// default behavior if nothing else is available
					if (ifCarryingFood()) {
						dropPheromone();
						moveToNest();
					}
					else if (ifFoodHere()) {
						pickUp();
						moveToNest();
					}
					else {
						if (!moveToAdjacentFoodElse()) {
							if (!moveToAdjacentPheromoneElse()) {
								moveRandom();
							}
						}
					}
				}
				else {
					// behavior as specified by GeneticProgram behavior
					//((XhAntForaging)port[P_BEHAVIOR]).executeBehavior(this);
					performBooleanActivity(port[P_BEHAVIOR].getFirstChild());
				}
			}
			break;
		case GridCellCE:
			double qPher = pheromoneLevel;
			if (qPher > 0.0 ) {
				decPheromoneLevel();
				if (usePheromoneDiffusion) {
					qPher = pheromoneLevel * pheromoneDiffusionRate;
					decPheromoneLevel( qPher );
					qPher /= numNeighbors; // 8 neighbors
					for (i = 0; i < numNeighbors; i++) {
						((XhAntForaging)port[i]).incPheromoneLevel( qPher );
					}
				}
			}
			break;
		default:
			break;
		} // end switch
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		hasMoved = false;
		super.postAct();
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#performBooleanActivity(org.primordion.xholon.base.IXholon)
	 */
	public boolean performBooleanActivity(IXholon activity)
	{
		boolean rc;
		switch(activity.getXhcId()) {
		case PfDropPheromoneCE:
			rc = dropPheromone();
			break;
		case PfIfCarryingFoodCE:
			rc = ifCarryingFood();
			break;
		case PfIfFoodHereCE:
			rc = ifFoodHere();
			break;
		case PfMoveRandomCE:
			rc = moveRandom();
			break;
		case PfMoveToAdjacentFoodElseCE:
			rc = moveToAdjacentFoodElse();
			break;
		case PfMoveToAdjacentPheromoneElseCE:
			rc = moveToAdjacentPheromoneElse();
			break;
		case PfMoveToNestCE:
			rc = moveToNest();
			break;
		case PfPickUpCE:
			rc = pickUp();
			break;
		case PfWrapperCE:
			rc = true;
			break;
		default:
			System.out.println("XhAntForaging performBooleanActivity() unknown type: " + activity.getXhcId());
			rc = false;
		break;
		}
		if (rc) {
			if (activity.getFirstChild() != null) {
				performBooleanActivity(activity.getFirstChild());
			}
		}
		else {
			if (activity.getNextSibling() != null) {
				performBooleanActivity(activity.getNextSibling());
			}
		}
		return rc;
	}

	/**
	 * Primitive Function - Change direction randomly, and move to the adjacent grid cell
	 * in that direction.
	 * @return true
	 */
	protected boolean moveRandom()
	{
		int rNum = MiscRandom.getRandomInt(0,numNeighbors);
		moveAdjacent(rNum);
		rNum = MiscRandom.getRandomInt(0,numNeighbors);
		moveAdjacent(rNum);
		return true;
	}
	
	/**
	 * Primitive Function - Take one step towards nest.
	 * @return true
	 */
	protected boolean moveToNest()
	{
		if (atAnthill()) {
			return dropFood();
		}
		else {
			int moveColOrRow;
			if (nestDistanceCol == 0) {
				moveColOrRow = MOVE_ROW_DIRECTION;
			}
			else if (nestDistanceRow == 0) {
				moveColOrRow = MOVE_COL_DIRECTION;
			}
			else {
				moveColOrRow = MiscRandom.getRandomInt(0,2);
			}
			int direction = P_STAYHERE;
			switch (moveColOrRow) {
			case MOVE_COL_DIRECTION: // move one column
				if (nestDistanceCol > 0) {
					direction = P_WEST;
				}
				else if (nestDistanceCol < 0) {
					direction = P_EAST;
				}
				break;
			case MOVE_ROW_DIRECTION: // move one row
				if (nestDistanceRow > 0) {
					direction = P_NORTH;
				}
				else if (nestDistanceRow < 0) {
					direction = P_SOUTH;
				}
				break;
			default:
				break;
			} // end switch
			if (direction != P_STAYHERE) {
				moveAdjacent(direction);
			}
			return true;
		}
	}
	
	/**
	 * Primitive Function - If food at current location, pick it up.
	 * @return true
	 */
	protected boolean pickUp()
	{
		if (!hasFood()) {
			if (((XhAntForaging)parentNode).hasFood()) {
				((XhAntForaging)parentNode).setFoodQuantity(((XhAntForaging)parentNode).getFoodQuantity() - 1);
				setFoodQuantity(FOOD_HAS);
				//adjustRawFitness(FITNESS_ANT_FINDS_FOOD);
			}
			dropSize = MAX_DROP_SIZE;
		}
		return true;
	}
	
	/**
	 * Primitive Function - If the ant is carrying food, drop pheromone on the way back to nest.
	 * @return true
	 */
	protected boolean dropPheromone()
	{
		if (usePheromone && hasFood()) {
			((XhAntForaging)parentNode).pheromoneLevel += dropSize;
			//for (int dir = 0; dir < numNeighbors; dir++) {
			//	((XhAntForaging)((XhAntForaging)parent).getPort(dir)).pheromoneLevel += dropSize;
			//}
			dropSize -= DROP_SIZE_DECREMENT;
			if (dropSize < MIN_DROP_SIZE) {
				dropSize = MIN_DROP_SIZE;
			}
		}
		return true;
	}
	
	/**
	 * Primitive Function - Is there food at the ant's current location?
	 * @return true or false
	 */
	protected boolean ifFoodHere()
	{
		return ((XhAntForaging)parentNode).hasFood();
	}
	
	/**
	 * Primitive Function - Is the ant currently carrying food?
	 * @return true or false
	 */
	protected boolean ifCarryingFood()
	{
		return hasFood();
	}
	
	/**
	 * Primitive Function - Take one step towards adjacent food, if any.
	 * @return true or false
	 */
	protected boolean moveToAdjacentFoodElse()
	{
		XhAntForaging node = (XhAntForaging)parentNode;
		for (int direction = 0; direction < numNeighbors; direction++) {
			if (((XhAntForaging)node.port[direction]).hasFood()) {
				moveAdjacent(direction);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Primitive Function - Take one step towards adjacent pheromone, if any.
	 * @return true or false
	 */
	protected boolean moveToAdjacentPheromoneElse()
	{
		if (usePheromone) {
			if (ifPheromoneHere()) {
				//return moveAlongPheromoneTrail();
				moveAlongPheromoneTrail();
			}
			XhAntForaging node = (XhAntForaging)parentNode;
			for (int direction = 0; direction < numNeighbors; direction++) {
				if (((XhAntForaging)node.port[direction]).pheromoneLevel > 0.0) {
					moveAdjacent(direction);
					//return true;
					break;
				}
			}
		}
		return false;
	}

	/**
	 * Helper Function - Is the ant currently at the anthill/nest?
	 * @return true or false
	 */
	protected boolean atAnthill()
	{
		return ((XhAntForaging)parentNode).hasNest;
	}
	
	/**
	 * Helper Function - Drop food at nest.
	 * @return true
	 */
	protected boolean dropFood()
	{
		if (hasFood()) {
			adjustRawFitness();
			setFoodQuantity(FOOD_HAS_NONE);
		}
		return true;
	}
	
	/**
	 * Helper Function - Is there pheromone at the ant's current location?
	 * @return true or false
	 */
	protected boolean ifPheromoneHere()
	{
		if (usePheromone) {
			return (((XhAntForaging)parentNode).pheromoneLevel > PHEROMONE_MIN_SIGNIFICANT);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Helper Function - Move along the pheromone trail, away from the nest.
	 * @return true or false
	 */
	protected boolean moveAlongPheromoneTrail()
	{
		if (!usePheromone) {
			return false;
		}
		int moveColOrRow;
		if (nestDistanceCol == 0) {
			moveColOrRow = MOVE_ROW_DIRECTION;
		}
		else if (nestDistanceRow == 0) {
			moveColOrRow = MOVE_COL_DIRECTION;
		}
		else {
			moveColOrRow = MiscRandom.getRandomInt(0,2);
		}
		int direction = P_STAYHERE;
		switch (moveColOrRow) {
		case MOVE_COL_DIRECTION: // move one column
			if (nestDistanceCol > 0) {
				direction = P_EAST;
			}
			else if (nestDistanceCol < 0) {
				direction = P_WEST;
			}
			break;
		case MOVE_ROW_DIRECTION: // move one row
			if (nestDistanceRow > 0) {
				direction = P_SOUTH;
			}
			else if (nestDistanceRow < 0) {
				direction = P_NORTH;
			}
			break;
		default:
			break;
		} // end switch
		
		if (direction == P_STAYHERE) {
			return false;
		}
		else {
			moveAdjacent(direction);
			return true;
		}
	}
	
	/**
	 * Helper Function - Move to an adjacent location.
	 * @param direction The direction to move (P_NORTH, P_NORTHEAST, P_EAST, etc.).
	 */
	protected void moveAdjacent(int direction)
	{
		IXholon newParentNode = getParentNode();
		//if (((XhAntForaging)newParentNode).hasNest) {
		//	adjustRawFitness(FITNESS_ANT_LEAVES_NEST);
		//}
		newParentNode = ((AbstractGrid)newParentNode).port[direction]; // choose a new parent
		if (newParentNode != null) {
			removeChild(); // detach subtree from parent and sibling links
			appendChild(newParentNode); // insert as the last child of the new parent
			hasMoved = true;
			switch (direction) { // integrate position relative to nest
			case P_NORTH:                        nestDistanceRow--; break;
			case P_NORTHEAST: nestDistanceCol++; nestDistanceRow--; break;
			case P_EAST:      nestDistanceCol++;                    break;
			case P_SOUTHEAST: nestDistanceCol++; nestDistanceRow++; break;
			case P_SOUTH:                        nestDistanceRow++; break;
			case P_SOUTHWEST: nestDistanceCol--; nestDistanceRow++; break;
			case P_WEST:      nestDistanceCol--;                    break;
			case P_NORTHWEST: nestDistanceCol--; nestDistanceRow--; break;
			default: break;
			}
		}
	}
	
	//                           Setters and Getters
	//	 usePheromone
	/**
	 * Set whether or not to use pheromone in the simulation.
	 * @param usePher true or false
	 */
	public static void setUsePheromone( boolean usePher ) { usePheromone = usePher; }
	/**
	 * Get whether or not pheromone is being used in the simulationa.
	 * @return true or false
	 */
	public static boolean getUsePheromone() { return usePheromone; }
	
	//	 pheromoneEvaporationRate
	/**
	 * Set pheromone evaporation rate.
	 * @param per The new pheromone evaporation rate.
	 */
	public static void setPheromoneEvaporationRate (double per ) { pheromoneEvaporationRate = per; }
	/**
	 * Get the current pheromone evaporation rate.
	 * @return The pheromone evaporation rate.
	 */
	public static double getPheromoneEvaporationRate () { return pheromoneEvaporationRate; }

	//	 pheromoneDiffusionRate
	/**
	 * Set pheromone diffusion rate.
	 * @param pdr The new pheromone diffusion rate.
	 */
	public static void setPheromoneDiffusionRate( double pdr ) { pheromoneDiffusionRate = pdr; }
	/**
	 * Get the current pheromone diffusion rate.
	 * @return The pheromone diffusion rate.
	 */
	public static double getPheromoneDiffusionRate() { return pheromoneDiffusionRate; }

	//	 usePheromoneDiffusion
	/**
	 * Set whether or not to use pheromone diffusion in the simulation.
	 * @param upd true or false
	 */
	public static void setUsePheromoneDiffusion( boolean upd ) { usePheromoneDiffusion = upd; }
	/**
	 * Get whether or not pheromone diffusion is being used in the simulation.
	 * @return true or false
	 */
	public static boolean getUsePheromoneDiffusion() { return usePheromoneDiffusion; }

	//	 pheromoneLevel
	/**
	 * Set the pheromone level.
	 * @param pLev The pheromone level.
	 */
	protected void setPheromoneLevel( double pLev ) { pheromoneLevel = pLev; }
	/**
	 * Get the pheromone level.
	 * @return The pheromone level.
	 */
	protected double getPheromoneLevel() { return pheromoneLevel; }
	/**
	 * Increase the pheromone level.
	 * @param inc Amount by which to increase the pheromone level.
	 */
	protected void incPheromoneLevel( double inc )
	{
		pheromoneLevel += inc;
	}
	/**
	 * Decrease the pheromone level. The amount is not allowed to go below 0.
	 * @param dec Amount by which to decrease the pheromone level.
	 */
	protected void decPheromoneLevel( double dec )
	{
		if (pheromoneLevel >= dec) {
			pheromoneLevel -= dec;
		}
	}

	/**
	 * Decrease the pheromone level at a specific GridCell.
	 * This effectively evaporates the pheromone.
	 */
	protected void decPheromoneLevel()
	{
		double dec;
		if (pheromoneEvaporationRate == 0.0) {return;}
		dec = pheromoneEvaporationRate * pheromoneLevel;
		if (dec == 0.0) {dec = 1.0;} // handle round off
		if (pheromoneLevel >= dec) {
			pheromoneLevel -= dec;
		}
		if (pheromoneLevel <= 1.0) {
			pheromoneLevel = 0.0;
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		switch(xhc.getId()) {
		case GeneticProgramCE:
			outStr += ", sizeOfPfTree:" + (treeSize() - 1)
					+ "]";
			break;
		case AntForagingSystemCE:
			break;
		case GridCellCE:
			outStr += " [hasNest:" + hasNest 
					+ ", hasFood:" + hasFood() + " (" + getFoodQuantity() + ")"
					+ ", pheromoneLevel:" + pheromoneLevel 
					+ "]";
			break;
		case AntCE:
			outStr += " [hasFood:" + hasFood() 
					+ ", nestDistanceCol:" + nestDistanceCol 
					+ ", nestDistanceRow:" + nestDistanceRow
					+ ", port:" + port[0]
					+ "]";
			break;
		case FoodCE:
			outStr += " [food at nest:" + getVal() + "]";
			break;
		default:
			break;
		} // end switch
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		return outStr;
	}
}
