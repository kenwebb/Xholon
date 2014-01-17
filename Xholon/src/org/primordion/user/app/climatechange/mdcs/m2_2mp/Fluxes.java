/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.StringHelper;

/**
 * Fluxes is a container for Flux objects.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 7, 2011)
 */
public class Fluxes extends Xhmdcs {
	
	private static final String showAllValues = "Show all values";
	private static final String showWikiMath = "Show wiki math";
	/** action list */
	private String[] actions = {showAllValues,showWikiMath};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		if (action == null) {return;}
		if (action.equals(showAllValues)) {
			System.out.println();
			IXholon flux = this.getFirstChild();
			while (flux != null) {
				System.out.println(flux.getRoleName() + " = " + StringHelper.format("%.3f", flux.getVal()));
				flux = flux.getNextSibling();
			}
		}
		else if (action.equals(showWikiMath)) {
			System.out.println();
			IXholon flux = this.getFirstChild();
			while (flux != null) {
				System.out.println(flux.toString());
				flux = flux.getNextSibling();
			}
		}
	}
}
