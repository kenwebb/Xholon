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

package org.primordion.xholon.io;

import com.google.gwt.canvas.dom.client.CssColor;

import java.util.HashMap;
import java.util.Map;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * A graphic panel in which to display the 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 5, 2010)
 */
public class GridPanelGeneric extends GridPanel implements IGridPanel {
  private static final long serialVersionUID = 1L;
  
  /**
   * Maps a XholonClass id to a Color.
   * There's a bug? that makes it impossible to look-up the CssColor directly,
   * so this has to be a String-to-String mapping.
   */
  private Map<String, String> colorMap = new HashMap<String, String>();
  
  /**
   * RGB colors that can be applied to nodes in the grid.
   */
  protected String seriesColor[] = {
      "#FF0000","#00FF00","#0000FF","#FFFF00",
      "#FF00FF","#00FFFF","#007FFF","#7F00FF",
      "#7FFF00","#FF007F"};
  
  protected int nextColorIndex = 0;
  
  /**
   * Color of a grid cell.
   */
  protected CssColor gridCellColor = CssColor.make("black"); //Color.BLACK;
  
  /**
   * Constructor
   */
  public GridPanelGeneric() {super();}
  
  /**
   * Constructor
   * @param gridOwner Xholon that owns the grid. */
  public GridPanelGeneric(IXholon gridOwner) {super(gridOwner);}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
   */
  public CssColor getColor(IXholon xhNode)
  {
    // NEW
    if (ClassHelper.isAssignableFrom(AbstractGrid.class, xhNode.getClass())) {
      // xhNode is a GridCell
      if (xhNode.hasChildNodes() && (!useShapes || (useShapes && (((IDecoration)xhNode.getLastChild().getXhc()).getSymbol() == null) ))) {
        // no shape has been defined for the agent, so get the agent's color
        return this.getAgentColor(xhNode.getLastChild());
      }
      else if (this.cellsCanSupplyOwnColor) {
        String ncolor = ((IDecoration)xhNode.getXhc()).getColor();
        if (ncolor != null) {
          return CssColor.make(ncolor);
        }
        else {
          return gridCellColor;
        }
      }
      else {
        return gridCellColor;
      }
    }
    else {
      // xhNode is an "Agent" within a GridCell
      // this is only called to set the color of a shape, so there's no need to check if useShapes
      return this.getAgentColor(xhNode);
    }
  }
  
  /**
   * Get an agent's color.
   */
  protected CssColor getAgentColor(IXholon xhNode) {
    String color = ((IDecoration)xhNode).getColor();
    if (color != null) {
      return CssColor.make(color);
    }
    String xhcName = xhNode.getXhcName();
    if (colorMap.containsKey(xhcName)) {
      return CssColor.make(colorMap.get(xhcName));
    }
    else {
      color = ((IDecoration)xhNode.getXhc()).getColor();
      if (color != null) {
        colorMap.put(xhcName, color);
      }
      else {
        color = seriesColor[nextColorIndex];
        colorMap.put(xhcName, color);
        nextColorIndex++;
        if (nextColorIndex >= seriesColor.length) {
          // start over with the same set of colors
          nextColorIndex = 0;
        }
      }
      return CssColor.make(color);
    }
  }
  
  @Override
  public int getShape(IXholon xhNode) {
    int shapeId = super.getShape(xhNode); //IGridPanel.GPSHAPE_NOSHAPE;
    String shapeName = ((IDecoration)xhNode.getXhc()).getSymbol();
    if (shapeName != null) {
      switch(shapeName) {
      case "Circle": shapeId = IGridPanel.GPSHAPE_CIRCLE; break;
      case "Triangle": shapeId = IGridPanel.GPSHAPE_TRIANGLE; break;
      case "Rectangle": shapeId = IGridPanel.GPSHAPE_RECTANGLE; break;
      case "Pentagon": shapeId = IGridPanel.GPSHAPE_PENTAGON; break;
      case "Hexagon": shapeId = IGridPanel.GPSHAPE_HEXAGON; break;
      case "Octogon": shapeId = IGridPanel.GPSHAPE_OCTOGON; break;
      case "Star": shapeId = IGridPanel.GPSHAPE_STAR; break;
      case "Turtle": shapeId = IGridPanel.GPSHAPE_TURTLE; break;
      case "SmallCircle": shapeId = IGridPanel.GPSHAPE_SMALLCIRCLE; break;
      case "SmallRectangle": shapeId = IGridPanel.GPSHAPE_SMALLRECTANGLE; break;
      case "ReverseTriangle": shapeId = IGridPanel.GPSHAPE_REVERSETRIANGLE; break;
      case "Cross": shapeId = IGridPanel.GPSHAPE_CROSS; break;
      case "Diamond": shapeId = IGridPanel.GPSHAPE_DIAMOND; break;
      case "Wye": shapeId = IGridPanel.GPSHAPE_WYE; break;
      case "LRTriangle": shapeId = IGridPanel.GPSHAPE_LRTRIANGLE; break;
      case "RLTriangle": shapeId = IGridPanel.GPSHAPE_RLTRIANGLE; break;
      default:
        if (shapeName.startsWith(JSCODE_INDICATOR)) { // "js:"
          // this is JavaScript code to create a shape
          shapeId = GPSHAPE_JAVASCRIPTCODE;
        }
        break;
      }
    }
    return shapeId;
  }
  
  public String[] getSeriesColor() {
    return seriesColor;
  }

  public void setSeriesColor(String[] seriesColor) {
    this.seriesColor = seriesColor;
  }

  public CssColor getGridCellColor() {
    return gridCellColor;
  }

  public void setGridCellColor(CssColor gridCellColor) {
    this.gridCellColor = gridCellColor;
  }
  
  public void setGridCellColor(int rgbColor) {
    String rgbColorStr = "rgb(" + ((rgbColor >> 16) & 0xff) + "," + ((rgbColor >> 8) & 0xff) + "," + (rgbColor & 0xff) + ")";
    this.gridCellColor = CssColor.make(rgbColorStr);
  }
  
}
