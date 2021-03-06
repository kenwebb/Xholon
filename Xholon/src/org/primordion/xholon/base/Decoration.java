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

import java.io.Serializable;

/**
 * A set of extra optional attributes that can decorate a Mechanism, XholonClass, or Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on March 21, 2010)
 */
public class Decoration implements IDecoration, Serializable {
	private static final long serialVersionUID = 5183710640376231471L;

	/**
	 * Optional color designation, for use in GUIs.
	 * ex: &lt;Color>0xFF0000&lt;/Color>
	 */
	private String color = null;
	
	/**
	 * Optional color opacity designation, for use in GUIs.
	 * 0.0 to 1.0
	 */
	private String opacity = null;
	
	/**
	 * Optional font designation, for use in GUIs.
	 * ex: &lt;Font>Arial-BOLD-18&lt;/Font>
	 */
	private String font = null;
	
	/**
	 * Optional icon designation, for use in GUIs.
	 * ex: &lt;Icon>images/OrNode/OrNode.png&lt;/Icon>
	 */
	private String icon = null;
	
	/**
	 * Optional toolTip designation, for use in GUIs.
	 * ex: &lt;ToolTip>This is some tool tip text.&lt;/ToolTip>
	 */
	private String toolTip = null;
	
	/**
	 * Optional symbol, that can be used in various ways.
	 * Typically it would be a mathematical, physics,
	 * or other standard symbol used with objects of that class.
	 * ex: &lt;Symbol>⊕&lt;/Symbol>
	 */
	private String symbol = null;
	
	/**
	 * Optional String.format() value.
	 * ex: &lt;Format>%.3f test %s&lt;/Format>
	 * ex use: String.format("%.3f test %s", myDouble, myString)
	 */
	private String format = null;
	
	/**
	 * Optional annotation.
	 */
	private IAnnotation anno = null;
	
	/** 
	 * Optional geographical information.
	 * It might be a pair of csv coordinates (in LatLng order as used by Leaflet),
	 * or a GeoJSON or TopoJSON string.
	 * ex: 45.421529600000000000,-75.697193099999990000
	 * ex: { "type": "Point", "coordinates": [100.0, 0.0] }
	 */
	private String geo = null;
	
	/**
	 * Optional sound value, such as the name of a .wav file.
	 * Used in Xholon2Scratch class.
	 */
	private String sound = null;
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getColor()
	 */
	public String getColor() {
		return color;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setColor(java.lang.String)
	 */
	public void setColor(String color) {
		if ((color != null) && (color.startsWith("hex+a"))) {
			// ex: <Color>hex+a #123456 0.8</Color>
			// convert this to: rgba(18,52,86,0.8)
			String[] arr = color.split(" ");
			if (arr.length == 3) {
				String colorStr = arr[1];
				if (colorStr.length() == 7) {
					int r = Integer.valueOf( colorStr.substring( 1, 3 ), 16 );
					int g = Integer.valueOf( colorStr.substring( 3, 5 ), 16 );
					int b = Integer.valueOf( colorStr.substring( 5, 7 ), 16 );
					this.color = "rgba(" + r + "," + g + "," + b + "," + arr[2] + ")";
				}
			}
		}
		else {
			this.color = color;
		}
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getOpacity()
	 */
	public String getOpacity() {
		return opacity;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setOpacity(java.lang.String)
	 */
	public void setOpacity(String opacity) {
		this.opacity = opacity;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getFont()
	 */
	public String getFont() {
		return font;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setFont(java.lang.String)
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getIcon()
	 */
	public String getIcon() {
		return icon;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setIcon(java.lang.String)
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getToolTip()
	 */
	public String getToolTip() {
		return toolTip;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setToolTip(java.lang.String)
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getSymbol()
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setSymbol(java.lang.String)
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getFormat()
	 */
	public String getFormat() {
		return format;
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setFormat(java.lang.String)
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getAnno()
	 */
	public String getAnno() {
		return this.anno.getVal_String();
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setAnno(java.lang.String)
	 */
	public void setAnno(String anno) {
		this.anno = new Annotation(anno);
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#hasAnno()
	 */
	public boolean hasAnno() {
	  if (this.anno == null) {return false;}
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#showAnno()
	 */
	public void showAnno() {
		if (this.anno != null) {
			this.anno.showAnnotation();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getGeo()
	 */
	public String getGeo() {
		return geo;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setGeo(java.lang.String)
	 */
	public void setGeo(String geo) {
		this.geo = geo;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getSound()
	 */
	public String getSound() {
		return sound;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setSound(java.lang.String)
	 */
	public void setSound(String sound) {
		this.sound = sound;
	}
	
}
