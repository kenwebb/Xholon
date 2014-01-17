/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.user.app.SpatialGames;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.util.MiscRandom;

/**
 * This is the detailed behavior of a sample Xholon application.
 * Source of model concept:
 *   Nowak, Martin A. (2006).
 *   Evolutionary Dynamics: Exploring the equations of life. Cambridge, MA: Belknap/Harvard.
 *   chapter 9 - Spatial Games
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7 (Created on September 16, 2007)
 * @see http://www.univie.ac.at/virtuallabs/ VirtualLabs in evolutionary game theory, by Christoph Hauert
 */
public class Xh09SpatialGames extends AbstractGrid implements Ce09SpatialGames {
	
	// payoff matrix
	private static final double PAYOFF_a = 1.0f;  // C interacts with a C
	private static final double PAYOFF_b = 0.0f;  // C interacts with a D
	private static       double PAYOFF_c = 1.65f; // D interacts with a C
	private static final double PAYOFF_d = 0.0f;  // D interacts with a D; also try 0.00001f
	private static final double PAYOFF_lowest = Double.MIN_VALUE; // lowest possible payoff
	
	// game setup
	public static final int LAYOUT_RANDOM = 0;
	public static final int LAYOUT_SINGLE_DEFECTOR = 1;
	private static int layout = LAYOUT_RANDOM;
	private static IXholon singleDefector = null;
	
	// variables
	protected double payoff = 0.0f; // payoff from previous step
	protected Xh09SpatialGames bestNeighbor = null;
	protected int oldXhcId = 0;
	
	// Specialized xholon classes that a GridCell can be
	private static IXholonClass cWasC = null;
	private static IXholonClass dWasD = null;
	private static IXholonClass cWasD = null;
	private static IXholonClass dWasC = null;

	/**
	 * Constructor.
	 */
	public Xh09SpatialGames() {}
	
	/**
	 * 
	 * @return
	 */
	public static double getPAYOFF_c()
	{
		return PAYOFF_c;
	}
	
	/**
	 * 
	 * @param payoffC
	 */
	public static void setPAYOFF_c(double payoffC)
	{
		PAYOFF_c = payoffC;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getLayout()
	{
		return layout;
	}
	
	/**
	 * 
	 * @param layoutArg
	 */
	public static void setLayout(int layoutArg)
	{
		layout = layoutArg;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.AbstractGrid#postConfigure()
	 */
	public void postConfigure()
	{
		switch(getXhcId()) {
		case SpatialGameCE:
			cWasC = getClassNode(CwasCCE);
			dWasD = getClassNode(DwasDCE);
			cWasD = getClassNode(CwasDCE);
			dWasC = getClassNode(DwasCCE);
			if (layout == LAYOUT_SINGLE_DEFECTOR) {
				// find single defector at center of grid
				IXholon row = getFirstChild(); // get first row
				int rowCount = -1;
				while (row != null) {
					rowCount ++;
					row = row.getNextSibling();
				}
				row = getFirstChild();
				for (int i = 0; i < rowCount / 2; i++) {
					row = row.getNextSibling();
				}
				IXholon col = row.getFirstChild();
				int colCount = -1;
				while (col != null) {
					colCount ++;
					col = col.getNextSibling();
				}
				col = row.getFirstChild();
				for (int i = 0; i < colCount / 2; i++) {
					col = col.getNextSibling();
				}
				singleDefector = col;
			}
			break;
		case GridCellCE:
			switch (layout) {
			case LAYOUT_RANDOM:
				int rInt = MiscRandom.getRandomInt(0, 10);
				switch (rInt) {
				case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
					setXhc(cWasC);
					break;
				case 9:
					setXhc(dWasD);
					break;
				default:
					System.out.println("postConfigure: random int = " + rInt);
					break;
				}
				break;
			case LAYOUT_SINGLE_DEFECTOR:
				if (this == singleDefector) {
					setXhc(dWasD);
				}
				else {
					setXhc(cWasC);
				}
				break;
			}
			break;
		default:
			break;
		} // end switch
		
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		switch(getXhcId()) {
		case CwasCCE:
		case DwasDCE:
		case CwasDCE:
		case DwasCCE:
			payoff = 0.0f;
			for (int i = P_NORTH; i < P_NORTH + 8; i++) {
				sumPayoff(i);
			}
			oldXhcId = getXhcId();
			break;
		default:
			break;
		}
		
		super.preAct();
	}
	
	/**
	 * Sum the payoff of this closest neighbor.
	 * @param neighborNum The port number (0 to 7) of a closest neighbor.
	 */
	protected void sumPayoff(int neighborNum) {
		switch(getXhcId()) {
		case CwasCCE: // I am currently a Cooperator
		case CwasDCE:
			switch (port[neighborNum].getXhcId()) {
			case CwasCCE: // I am interacting with another Cooperator
			case CwasDCE:
				payoff += PAYOFF_a;
				break;
			case DwasDCE: // I am interacting with a Defector
			case DwasCCE:
				payoff += PAYOFF_b;
				break;
			default:
				break;
			}
			break;
		case DwasDCE: // I am currently a Defector
		case DwasCCE:
			switch (port[neighborNum].getXhcId()) {
			case CwasCCE: // I am interacting with a Cooperator
			case CwasDCE:
				payoff += PAYOFF_c;
				break;
			case DwasDCE: // I am interacting with another Defector
			case DwasCCE:
				payoff += PAYOFF_d;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(getXhcId()) {
		case CwasCCE: // I am currently a Cooperator
		case CwasDCE:
			findBestNeighbor();
			if (bestNeighbor != null) {
				switch (bestNeighbor.oldXhcId) {
				case CwasCCE: // I am interacting with another Cooperator
				case CwasDCE:
					if (bestNeighbor.payoff >= payoff) {
						setXhc(cWasC);
					}
					else {
						setXhc(cWasC);
					}
					break;
				case DwasDCE: // I am interacting with a Defector
				case DwasCCE:
					if (bestNeighbor.payoff >= payoff) {
						setXhc(dWasC);
					}
					else {
						setXhc(cWasC);
					}
					break;
				default:
					break;
				}
			}
			else {
				setXhc(cWasC);
			}
			break;
		case DwasDCE: // I am currently a Defector
		case DwasCCE:
			findBestNeighbor();
			if (bestNeighbor != null) {
				switch (bestNeighbor.oldXhcId) {
				case CwasCCE: // I am interacting with a Cooperator
				case CwasDCE:
					if (bestNeighbor.payoff >= payoff) {
						setXhc(cWasD);
					}
					else {
						setXhc(dWasD);
					}
					break;
				case DwasDCE: // I am interacting with another Defector
				case DwasCCE:
					if (bestNeighbor.payoff >= payoff) {
						setXhc(dWasD);
					}
					else {
						setXhc(dWasD);
					}
					break;
				default:
					break;
				}
			}
			else {
				setXhc(dWasD);
			}
			break;
		default:
			break;
		}
		super.act();
	}
	
	/**
	 * Find neighbor with highest payoff.
	 */
	protected void findBestNeighbor()
	{
		double highestNeighborPayoff = PAYOFF_lowest;
		bestNeighbor = null;
		for (int i = P_NORTH; i < P_NORTH + 8; i++) {
			if ((port[i] != null) && (((Xh09SpatialGames)port[i]).payoff > highestNeighborPayoff))
				{highestNeighborPayoff = ((Xh09SpatialGames)port[i]).payoff;
				bestNeighbor = (Xh09SpatialGames)port[i];}
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		switch(xhc.getId()) {
		case CwasCCE:
		case DwasDCE:
		case CwasDCE:
		case DwasCCE:
			String bnPayoff = (bestNeighbor == null) ? "no best neighbor" : "" + bestNeighbor.payoff;
			return (getName() 
					+ " [payoff:" + payoff
					+ " highestNeighborPayoff:" + bnPayoff
					+ "]");
		default:
			return (getName());
		}
	}
}
