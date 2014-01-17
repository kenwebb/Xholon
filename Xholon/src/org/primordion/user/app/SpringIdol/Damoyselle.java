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

package org.primordion.user.app.SpringIdol;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * Reimplementation of "Spring Idol" contest from:
 * Walls, Craig. (2008). Spring in Action, 2nd edition. Greenwich, CT: Manning.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on December 24, 2007)
 */
public class Damoyselle extends XholonWithPorts implements Poem {
	
	// Short poem: A une Damoyselle malade, by Clement Marot (1537)
	private static String[] LINES = {
		"\"Ma mignonne,",
		"Je vous donne",
		"Le bon jour.",
		"Le sejour",
		"C'est prison :",
		"Guerison",
		"Recouvrez,",
		"Puis ouvrez",
		"Vostre porte,",
		"Et qu'on sorte",
		"Vitement :",
		"Car Clement",
		"Le vous mande.",
		"Va friande",
		"De ta bouche,",
		"Qui se couche",
		"En danger",
		"Pour manger",
		"Confitures :",
		"Si tu dures",
		"Trop malade,",
		"Couleur fade",
		"Tu prendras,",
		"Et perdras",
		"L'embonpoint.",
		"Dieu te doint",
		"Sante bonne",
		"Ma mignonne.\""
	};
  
	public Damoyselle() {}

	public void recite() {
		for (int i = 0; i < LINES.length; i++) {
			println(LINES[i]);
		}
	}
}
