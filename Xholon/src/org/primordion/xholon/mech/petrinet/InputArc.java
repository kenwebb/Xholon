/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet;

import org.primordion.xholon.base.IXholon;

/**
 * An Arc that extends from an input Place to a Transition.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 20, 2012)
 */
public class InputArc extends Arc {
	private static final long serialVersionUID = -7201188517143210326L;

	/*
	 * @see org.primordion.xholon.mech.petrinet.Arc#performVoidActivity(org.primordion.xholon.base.IXholon)
	 */
	public void performVoidActivity(IXholon activity) {
		if (place != null) {
			place.decVal(this.getVal());
		}
	}

}
