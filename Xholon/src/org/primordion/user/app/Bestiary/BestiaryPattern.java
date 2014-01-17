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

package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.StringTokenizer;

/**
 * Instances of this class can be pasted into a Bestiary simulation at runtime.
 * <p>Syntax to use when pasting in patterns:</p>
<pre>
W wall section
P outdoor porch
D door
E indoor entrance
. interior section
_ exterior area
</pre>
 * <p>Examples:</p>
<pre>
&lt;BestiaryPattern x="10" y="10">
_WWWWW
_W...W
PDE..W
_W...W
_WWWWW
&lt;/BestiaryPattern>
</pre>
<pre>
&lt;BestiaryPattern>
_____P
_WWWWDWWWWWWW
_W...E......W
PDE.........W
_W..........W
_W..........W
_W.........EDP
_W..........W
PDE.........W
_W..........W
_W.......E..W
_WWWWWWWWDWWW
_________P
&lt;/BestiaryPattern>
</pre>
<pre>
&lt;BestiaryPattern x="25" y="45">
_WWWWW
_W...W
PDE..W
_W...W
_W..WWWW
_W.....WW
_W......WWWWWWWW
_W........W....W
PDE.......W....W
_W............EDP
_W........W....W
_WWWWW..WWWWWWWW
___W......W
___W......W
___WWWWWWWW
&lt;/BestiaryPattern>
</pre>
<pre>
&lt;BestiaryPattern x="10" y="10">
_WWWWW______WWWWWW
_W...WWWWWWWW....W
PDE..DDDDDDDD....W
_W...WWWWWWWW....W
_WWWWW______WWWWWW
&lt;/BestiaryPattern>
</pre>
<pre>
&lt;BestiaryPattern x="10" y="10">
____________WWWWWW
_WWWWWWWWWWWW....W
PDDDDDDDDDDDD....W
_WWWWWWWWWWWW....W
____________WWWWWW
&lt;/BestiaryPattern>
</pre>

 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 26, 2010)
 */
public class BestiaryPattern extends Xholon implements CeBestiary {
	
	/**
	 * X coordinate of the origin of the pattern.
	 */
	private int x = 0;
	
	/**
	 * Y coordinate of the origin of the pattern.
	 */
	private int y = 0;
	
	/**
	 * The gridCell at the upper left corner of the pattern.
	 */
	private IXholon origin = null;
	
	/**
	 * Pattern text read in from XML text.
	 */
	private String patternText = null;
	
	/**
	 * An action can be specified in the XML.
	 * ex: action="clear"
	 */
	private String action = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {
		patternText = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String() {
		return patternText;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		switch (getParentNode().getXhcId()) {
		case CeBestiary.GridCE:
			// use x,y to determine the origin of the pattern
			origin = getParentNode().getFirstChild();
			for (int row = 0; row < y; row++) {
				if (origin != null) {
					origin = origin.getNextSibling();
				}
			}
			if (origin != null) {
				origin = origin.getFirstChild();
				for (int col = 0; col < x; col++) {
					if (origin != null) {
						origin = origin.getNextSibling();
					}
				}
			}
			break;
		case CeBestiary.HabitatCellCE:
			// use this gridCell as the origin of the pattern
			origin = getParentNode();
			break;
		default:
			break;
		}
		if (patternText != null) {
			createPattern(patternText);
		}
		removeChild();
	}
	
	/**
	 * Create a specified pattern.
	 * @param patternStr A pattern string.
	 */
	protected void createPattern(String patternStr) {
		IXholon startOfRowNode = origin;
		IXholon node = startOfRowNode;
		StringTokenizer st = new StringTokenizer(patternStr, "\n");
		while (st.hasMoreTokens()) {
			String cols = st.nextToken();
			for (int col = 0; col < cols.length(); col++) {
				switch (cols.charAt(col)) {
				case 'W': // wall section
					node.appendChild("WallSection", null);
					node = node.getNextSibling();
					break;
				case 'D': // door
					node.appendChild("Door", null);
					node = node.getNextSibling();
					break;
				case 'P': // porch
					node.appendChild("Porch", null);
					node = node.getNextSibling();
					break;
				case 'E': // entrance
					node.appendChild("Entrance", null);
					node = node.getNextSibling();
					break;
				case '.': // house interior section
					node.appendChild("HouseInteriorSection", null);
					node = node.getNextSibling();
					break;
				case '_': // an outside section
					node = node.getNextSibling();
					break;
				default:
					break;
				}
			}
			startOfRowNode = startOfRowNode.getPort(2); // P_SOUTH = 2
			node = startOfRowNode;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrValue) {
		if ("x".equals(attrName)) {
			setX(Integer.parseInt(attrValue));
		}
		else if ("y".equals(attrName)) {
			setY(Integer.parseInt(attrValue));
		}
		else if ("action".equals(attrName)) {
			setAction(attrValue);
		}
		else {
			return super.setAttributeVal(attrName, attrValue);
		}
		return 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
