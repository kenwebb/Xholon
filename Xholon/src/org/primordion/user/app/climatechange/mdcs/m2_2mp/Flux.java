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

import org.primordion.xholon.util.StringHelper;

/**
 * A flux is an object (related to a mathematical vector, and a Xholon message)
 * that contains a magnitude and a direction.
 * It's roleName must be a specially-formatted combination of its initial point (Xholon sender),
 * terminal point (Xholon receiver), and optional subscript (Xholon signal).
 * The first character of the initial point and terminal point must be uppercase.
 * All other characters must be lowercase.
 * The optional subscript, if present, must be preceeded by a "_".
 * For example, a shortwave (sw) radiation flux from the Sun to Space
 * might have the following roleName:
 * <p>SunSpc_sw</p>
 * <p>The magnitude (value) of a flux should only be changed by the terminal point,
 * and should only be used by that terminal point or by a viewer.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 6, 2011)
 */
public class Flux extends Xhmdcs {
	
	/**
	 * Get a tex representation of this flux object. ex:
	 * <p>roleName: SunSpc_sw</p>
	 * <p>tex: {{Sun}{Spc}}_{sw}</p>
	 * @return
	 */
	public String getTex() {
		String regex = StringHelper.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      );
		String[] str = this.getRoleName().split(regex);
		String outStr = "{{" + str[0] + "}{" + str[1]+ "}}";
		if (str.length > 2) {
			outStr += "_" + "{" + str[2].substring(1) + "}";
		}
		return outStr;
	}
	
	/**
	 * Get a wikipedia-compatible tex representation of this flux object. ex:
	 * <p><strong>roleName</strong> SunSpc_sw</p>
	 * <p><strong>wiki tex</strong> : &lt;math>\overrightarrow{{Sun}{Spc}}_{sw} = 1367.0\;W/m^2&lt;/math></p>
	 * @return
	 */
	public String getWikiTex() {
		return ": <math>\\overrightarrow" + getTex() + " = "
			+ StringHelper.format("%.3f", this.getVal()) + "\\;"
			+ this.getUnits() + "</math>";
	}
	
	public String getSvgText() {
		return "TODO";
	}
	
	public String toString() {
		String outStr = "";
		if (this.getRoleName() != null) {
			outStr = this.getWikiTex();
		}
		return outStr;
	}
	
}
