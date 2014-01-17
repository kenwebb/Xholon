package org.primordion.user.app.Bestiary;

import com.google.gwt.canvas.dom.client.CssColor;
//import com.google.gwt.event.dom.client.MouseDownEvent;

//import java.awt.Color;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class GridPanelBestiary extends GridPanel implements IGridPanel, CeBestiary {
	private static final long serialVersionUID = 1L;
	
	// default color, color of new beasts such as Dog
	private static final CssColor COLOR_DEFAULT = CssColor.make("magenta"); //Color.MAGENTA;
	// color of the habitat background
	private static final CssColor COLOR_HABITAT = CssColor.make("#007B00"); //HTML ForestGreen (deep)
	// house parts
	private static final CssColor COLOR_DOOR_CLOSED = CssColor.make("#BF9A00"); //between orange and forestgreen
	private static final CssColor COLOR_DOOR_OPENED = CssColor.make("#738E00"); //between forestgreen and orange
	private static final CssColor COLOR_ENTRANCE = CssColor.make("green"); //Color.GREEN;
	private static final CssColor COLOR_HOUSE_INTERIOR_SECTION = CssColor.make("black"); //Color.DARK_GRAY;
	private static final CssColor COLOR_PORCH = CssColor.make("green"); //Color.GREEN;
	private static final CssColor COLOR_WALL_SECTION = CssColor.make("orange"); //Color.ORANGE;
	// beasts
	private static final CssColor COLOR_HUMAN = CssColor.make("#D2B48C"); //HTML tan
	
	private static final int LICORICE_ID = 2;
	private IXholon licorice = null;
	
	/**
	 * Constructor
	 */
	public GridPanelBestiary() {super();}
	
	/**
	 * Constructor
	 * @param gridOwner Xholon that owns the grid. */
	public GridPanelBestiary(IXholon gridOwner) {super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		switch(xhNode.getXhcId()) {
		case HabitatCellCE:
			if (xhNode.hasChildNodes() && !useShapes) {
				switch (xhNode.getLastChild().getXhcId()) {
				case WallSectionCE:
					return COLOR_WALL_SECTION;
				case DoorCE:
					return getDoorColor(xhNode);
				case PorchCE:
					return COLOR_PORCH;
				case EntranceCE:
					return COLOR_ENTRANCE;
				case HouseInteriorSectionCE:
					return COLOR_HOUSE_INTERIOR_SECTION;
				case CatCE:
					return getCatColor(xhNode.getLastChild());
				case HumanCE:
					return COLOR_HUMAN;
				default:
					return COLOR_DEFAULT;
				}
			}
			else if (xhNode.hasChildNodes()) {
				switch (xhNode.getLastChild().getXhcId()) {
				case WallSectionCE:
					return COLOR_WALL_SECTION;
				case DoorCE:
					return getDoorColor(xhNode);
				case PorchCE:
					return COLOR_PORCH;
				case EntranceCE:
					return COLOR_ENTRANCE;
				case HouseInteriorSectionCE:
					return COLOR_HOUSE_INTERIOR_SECTION;
				case CatCE:
					return getCatColor(xhNode.getLastChild());
				case HumanCE:
					return COLOR_HUMAN;
				default:
					return COLOR_DEFAULT;
				}
			}
			else {
				return COLOR_HABITAT;
			}
		case CatCE:
			return getCatColor(xhNode);
		case HumanCE:
			return COLOR_HUMAN;
		case WallSectionCE:
			return COLOR_WALL_SECTION;
		case DoorCE:
		case PorchCE:
		case EntranceCE:
			return COLOR_ENTRANCE;
		case HouseInteriorSectionCE:
			return COLOR_HOUSE_INTERIOR_SECTION;
		default:
			return COLOR_DEFAULT;
		}
	}
	
	/**
	 * Get the Cat color.
	 * @param xhNode
	 * @return
	 */
	protected CssColor getCatColor(IXholon xhNode) {
		if (xhNode.getId() == LICORICE_ID) {
		  licorice = xhNode;
		  //xhNode.consoleLog(licorice + " is green");
			return CssColor.make("#9ACD32"); //Color.GREEN; YellowGreen
		}
		int bugSizeRed = (int)((Cat)xhNode).getCatSize();
		if (bugSizeRed > 255) {bugSizeRed = 255;}
		//return new Color( 255, 255-bugSizeRed, 255-bugSizeRed );
		return CssColor.make("rgb(" + (255-bugSizeRed) + "," + (255-bugSizeRed) + "," + (255-bugSizeRed) + ")");
	}
	
	/**
	 * Get the Door color.
	 * @param xhNode
	 * @return
	 */
	protected CssColor getDoorColor(IXholon xhNode) {
		int doorStatus = xhNode.getLastChild().getVal_int();
		if (doorStatus == 0) {return COLOR_DOOR_CLOSED;} // closed
		else {return COLOR_DOOR_OPENED;} // opened
	}
	
	/*
	 * @see org.primordion.xholon.io.GridPanel#getShape(org.primordion.xholon.base.IXholon)
	 */
	public int getShape(IXholon xhNode)
	{
		switch(xhNode.getXhcId()) {
		case CatCE:
			return GPSHAPE_CIRCLE;
		case HumanCE:
			return GPSHAPE_HEXAGON;
		default:
			return GPSHAPE_NOSHAPE;
		}
	}
}
