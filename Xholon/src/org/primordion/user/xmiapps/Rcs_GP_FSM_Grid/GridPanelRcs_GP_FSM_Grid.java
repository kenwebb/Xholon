package org.primordion.user.xmiapps.Rcs_GP_FSM_Grid;

import com.google.gwt.canvas.dom.client.CssColor;
//import java.awt.Color;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
	Rcs_GP_FSM_Grid application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   12/02/2007</p>
	<p>File:   Rcs_GP_FSM_Grid.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Fri Feb 09 19:06:28 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/

/**
 * A graphic panel in which to display the 2D grid.
 */
public class GridPanelRcs_GP_FSM_Grid extends GridPanel implements IGridPanel, CeRcs_GP_FSM_Grid {
private static final long serialVersionUID = 1L;

/**
 * Constructor
 */
public GridPanelRcs_GP_FSM_Grid() {super();}

/**
 * Constructor
 * @param gridOwner Xholon that owns the grid. */
public GridPanelRcs_GP_FSM_Grid(IXholon gridOwner) {super(gridOwner);}

/*
 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
 */
public CssColor getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	case GridCellCE: // TODO edit this
		if (xhNode.hasChildNodes()) {
			switch (xhNode.getFirstChild().getXhcId()) {
			case GPaseCE:        return CssColor.make("pink"); //Color.PINK;
			case PKinaseCE:      return CssColor.make("orange"); //Color.ORANGE;
			case PPhosphataseCE: return CssColor.make("magenta"); //Color.MAGENTA;
			case G1PCE:          return CssColor.make("cyan"); //Color.CYAN;
			case GlyCE:          return CssColor.make("blue"); //Color.BLUE;
			default:
				return CssColor.make("gray"); //Color.GRAY;
			}
		}
		else {
			return CssColor.make("white"); //Color.WHITE;
		}
	default:
		return CssColor.make("gray"); //Color.GRAY;
	}
}
}
