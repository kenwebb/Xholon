/* Cellontro - models & simulates cells and other biological entities
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

package org.primordion.cellontro.app;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IAbout;
import org.primordion.xholon.service.AbstractXholonService;

/**
 * Cell Model.
 * <p>This simulation of a biological cell includes enzymes that continuously transform
 * substrate chemicals into products, and lipid bilayers and transport proteins that move
 * chemicals between compartments.</p>
 * <p>For more information on this model, see:</p>
 * <p>Webb, K., & White, T. (2005). UML as a cell and biochemistry modeling language.
 * BioSystems, 80, 283�302.</p>
 * <p>Webb, K., & White, T. (2004). Cell Modeling using Agent-based Formalisms.
 * AAMAS 2004, New York.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class AppCell extends Application {

	/**
	 * Constructor.
	 */
	public AppCell()
	{
		super();
		chartViewer = null;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		super.initialize(configFileName);
	}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
	  //println("stepping...");
		root.preAct();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		root.act();
		root.postAct();
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
	  //println("wrapup");
		root.preAct();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#about()
	 */
	public void about()
	{
		String title = "About Cellontro";
		String text;
		if (modelName == null) {
			text = "Cellontro using " + aboutText;
		}
		else {
			text = modelName + "\n" + "Cellontro using " + aboutText;
		}
		IAbout about = (IAbout)getService(AbstractXholonService.XHSRV_ABOUT);
		if (about != null) {
			about.about(title, text, 350, 200);
		}
		else {
			System.out.println(title);
			System.out.println(text);
		}
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.cellontro.app.AppCell",
				"./config/cellontro/Cell/Cell_BioSystems_Jul03_xhn.xml");
	}
}
