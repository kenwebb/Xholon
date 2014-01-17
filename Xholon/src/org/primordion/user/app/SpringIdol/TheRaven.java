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
public class TheRaven extends XholonWithPorts implements Poem {
	
	// Part of the poem: The Raven, by Edgar Allan Poe (1845)
	private static String[] LINES = {
		"\"Once upon a midnight dreary, while I pondered, weak and weary,",
		"Over many a quaint and curious volume of forgotten lore,",
		"While I nodded, nearly napping, suddenly there came a tapping,",
		"As of some one gently rapping, rapping at my chamber door.",
		"'Tis some visiter,' I muttered, 'tapping at my chamber door -",
		"Only this, and nothing more.'",
		
		"Ah, distinctly I remember it was in the bleak December,",
		"And each separate dying ember wrought its ghost upon the floor.",
		"Eagerly I wished the morrow; - vainly I had tried to borrow",
		"From my books surcease of sorrow - sorrow for the lost Lenore -",
		"For the rare and radiant maiden whom the angels name Lenore -",
		"Nameless here for evermore.\""
	};
  
	public TheRaven() {}
	
	public void recite() {
		for (int i = 0; i < LINES.length; i++) {
			println(LINES[i]);
		}
	}
}
