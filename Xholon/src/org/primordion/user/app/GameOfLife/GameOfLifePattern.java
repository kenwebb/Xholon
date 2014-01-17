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

package org.primordion.user.app.GameOfLife;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.StringTokenizer;

/**
 * There are many interesting patterns in the Game of Life.
 * Instances of this class can be pasted into a GameOfLife simulation at runtime.
 * Many patterns can be copied from
 * <a href="http://www.argentum.freeserve.co.uk/lex.htm">Life Lexicon</a>
 * <p>Examples of the syntax to use when pasting in patterns:</p>
<pre>
&lt;GameOfLifePattern action="clear">&lt;Acorn/>&lt;/GameOfLifePattern>
</pre>
<pre>
&lt;GameOfLifePattern action="clear">&lt;/GameOfLifePattern>
</pre>
<pre>
&lt;GameOfLifePattern>&lt;Glider/>&lt;/GameOfLifePattern>
</pre>
<pre>
&lt;GameOfLifePattern>&lt;RPentomino/>&lt;/GameOfLifePattern>
</pre>
<pre>
&lt;GameOfLifePattern x="10" y="10">
.O.
..O
OOO
&lt;/GameOfLifePattern>
</pre>
The next pattern (B29) is copied directly from the Life Lexicon site.
<pre>
&lt;GameOfLifePattern action="clear">
	.......OOO.......
	.......O.........
	OOO......O.......
	O......O.O.......
	.O....OO.OOOO....
	...OOOO.OOOOO.OO.
	....OO.......OO.O
&lt;/GameOfLifePattern>
</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 8, 2010)
 */
public class GameOfLifePattern extends Xholon implements CeGameOfLife {
	
	private static final String P_GLIDER = ".O.\n..O\nOOO";
	
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
		//System.out.println(val);
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
		if (action != null) {
			if ("clear".equals(action)) {
				clear();
			}
		}
		switch (getParentNode().getXhcId()) {
		case CeGameOfLife.GameOfLifeCE:
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
		case CeGameOfLife.GridCellCE:
			// use this gridCell as the origin of the pattern
			origin = getParentNode();
			break;
		default:
			break;
		}
		// is there a child node that specifies the pattern?
		if (this.hasChildNodes()) {
			String patternName = getFirstChild().getXhcName();
			if ("Acorn".equals(patternName)) {
				createPattern(acorn());
			}
			else if ("Glider".equals(patternName)) {
				createPattern(P_GLIDER);
			}
			else if ("Herschel".equals(patternName)) {
				createPattern(herschel());
			}
			else if ("RPentomino".equals(patternName)) {
				createPattern(rPentomino());
			}
			else if ("clear".equals(patternName)) {
				clear();
			}
		}
		else {
			// TODO the pattern must be in the form of XML text
			if (patternText != null) {
				createPattern(patternText);
			}
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
				//case '_':
				case '.':
					node.setVal(false);
					node = node.getNextSibling();
					break;
				//case '#':
				case 'O':
				case '*':
					node.setVal(true);
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
	
	/**
	 * Clear the entire grid.
	 */
	protected void clear() {
		IXholon node = null;
		switch (getParentNode().getXhcId()) {
		case CeGameOfLife.GameOfLifeCE:
			node = getParentNode();
			break;
		case CeGameOfLife.RowCE:
			node = getParentNode().getParentNode();
			break;
		case CeGameOfLife.GridCellCE:
			node = getParentNode().getParentNode().getParentNode();
			break;
		default:
			break;
		}
		if (node != null) {
			IXholon row = node.getFirstChild();
			while (row != null) {
				IXholon col = row.getFirstChild();
				while (col != null) {
					col.setVal(false);
					col = col.getNextSibling();
				}
				row = row.getNextSibling();
			}
		}
	}
	
	/**
	 * Acorn
	 * @return
	 */
	protected String acorn() {
		return new StringBuilder()
		.append(".O.....\n")
		.append("...O...\n")
		.append("OO..OOO\n")
		.toString();
	}
	
	/**
	 * Herschel
	 * @return
	 */
	protected String herschel() {
		return new StringBuilder()
		.append("O..\n")
		.append("OOO\n")
		.append("O.O\n")
		.append("..O\n")
		.toString();
	}
	
	/**
	 * RPentomino
	 * @return
	 */
	protected String rPentomino() {
		return new StringBuilder()
		.append(".OO\n")
		.append("OO.\n")
		.append(".O.\n")
		.toString();
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
