package org.primordion.user.xmiapps.__XholonTemplate__;

import com.google.gwt.canvas.dom.client.CssColor;
//import java.awt.Color;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
	__XholonTemplate__ application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   __XholonTemplate__.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/

/**
 * A graphic panel in which to display the 2D grid.
 */
public class GridPanel__XholonTemplate__ extends GridPanel implements IGridPanel, Ce__XholonTemplate__ {
private static final long serialVersionUID = 1L;

/**
 * Constructor
 */
public GridPanel__XholonTemplate__() {super();}

/**
 * Constructor
 * @param gridOwner Xholon that owns the grid. */
public GridPanel__XholonTemplate__(IXholon gridOwner) {super(gridOwner);}

/*
 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
 */
public CssColor getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	//case GridCellCE: // TODO edit this
	//	if (xhNode.hasChildNodes()) {
	//		switch (xhNode.getFirstChild().getXhcId()) {
	//		default:
	//			return CssColor.make("blue"); //Color.BLUE;
	//		}
	//	}
	//	else {
	//		return CssColor.make("white"); //Color.WHITE;
	//	}
	default:
		return CssColor.make("gray"); //Color.GRAY;
	}
}
}
