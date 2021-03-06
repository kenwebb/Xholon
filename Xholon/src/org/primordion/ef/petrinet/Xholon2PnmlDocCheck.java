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

package org.primordion.ef.petrinet;

/**
 * Export a Xholon model in Petri Net Markup Language (PNML) XML format.
 * The resulting file can be read by the PNML Document Checker.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on September 7, 2012)
 * @see <a href="http://pnml.lip6.fr/pnmlvalidation/">PNML Document Checker</a>
 */
@SuppressWarnings("serial")
public class Xholon2PnmlDocCheck extends Xholon2Pnml {
	
	/**
	 * constructor
	 */
	public Xholon2PnmlDocCheck() {
		super();
		this.setUseDocCheckSettings(true);
		this.setUsePipeSettings(false);
		this.setUseVantedSettings(false);
	}
	
}
