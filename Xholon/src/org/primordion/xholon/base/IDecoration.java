/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.xholon.base;

/**
 * A set of extra optional attributes that can decorate a Mechanism, XholonClass, or Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on March 21, 2010)
 */
public interface IDecoration {

	public abstract String getColor();

	public abstract void setColor(String color);

	public abstract String getOpacity();

	public abstract void setOpacity(String opacity);

	public abstract String getFont();

	public abstract void setFont(String font);

	public abstract String getIcon();

	public abstract void setIcon(String icon);

	public abstract String getToolTip();

	public abstract void setToolTip(String toolTip);
	
	public abstract String getSymbol();
	
	public abstract void setSymbol(String symbol);
	
	public abstract String getFormat();
	
	public abstract void setFormat(String format);
	
	public abstract String getAnno();
	
	public abstract void setAnno(String anno);
	
	public abstract boolean hasAnno();
	
	public abstract void showAnno();

}
