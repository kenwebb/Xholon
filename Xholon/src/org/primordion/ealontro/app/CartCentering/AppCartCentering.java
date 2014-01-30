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

package org.primordion.ealontro.app.CartCentering;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Cart Centering System with Genetic Programming.
 * <p>The Cart Centering System is a Xholon implementation of the example presented by John Koza
 * in his book Genetic Programming. It demonstrates how genetic programming can be used
 * to evolve tree-based behaviors.</p>
 * <p>"Objective: Find a time-optimal bang-bang control strategy to center a cart
 * on a one-dimensional frictionless track."</p>
 * <p>source: Koza, J. (1992). Genetic Programming. p.122-147</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 11, 2006)
 */
public class AppCartCentering extends Application {

	private static final double TAU = 0.02; // time step increment in seconds; 50
	protected boolean saveSnapshots = true;

	/**
	 * Constructor.
	 */
	public AppCartCentering()
	{
		super();
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		modelName = configFileName;
		super.initialize(configFileName);
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep * TAU);
		}
		XholonTime.sleep( getTimeStepInterval() );
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		root.preAct();
		
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep * TAU);
		}
		super.wrapup();
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.ealontro.app.AppCartCentering",
				"./config/ealontro/CartCentering/CartCentering_NoGP_xhn.xml");
	}
}
