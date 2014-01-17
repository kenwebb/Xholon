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
import org.primordion.xholon.io.IAbout;
import org.primordion.xholon.service.AbstractXholonService;

/**
 * Cell Model with Autopoiesis.
 * <p>This is a variation of the Cell Model in which the cell bilayer contains lipids.
 * These lipids are created within the Cytosol. Because they are hydrophobic, the water
 * in the cytosol tends to push them towards the cell bilayer, which gradually incorporates them.
 * The activity of the cell bilayer is causally dependent on the quantity of lipids it contains.</p>
 * <p>For more information, see:
 * Webb, K., & White, T. (2004).
 * Combining Analysis and Synthesis in a Model of a Biological Cell.
 * SAC '04, March 14-17, 2004, Nicosia, Cyprus.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 20, 2005)
 */
public class AppCellAutop extends Application {

	/**
	 * Constructor.
	 */
	public AppCellAutop()
	{
		super();
		chartViewer = null;
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
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
		appMain(args, "org.primordion.cellontro.app.AppCellAutop",
				"./config/cellontro/CellAutop/Autop_xhn.xml");
	}
}
