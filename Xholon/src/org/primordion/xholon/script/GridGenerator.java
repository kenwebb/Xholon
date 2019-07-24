/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.IXml2Xholon;

/**
 * Create a Xholon Grid.
 * This is a Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <p><pre>&lt;GridGenerator/></pre></p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 9, 2016)
 */
public class GridGenerator extends XholonScript {
  
  private static final int DEFAULT_ROWS = 20;
  private static final int DEFAULT_COLS = 20;
  private static final String[] DEFAULT_NAMES = {"Grid","Row","GridCell"};
  private static final String DEFAULT_GRID_TYPE = "Gmt";
  private static final String DEFAULT_COLUMN_COLOR = "FF00FF";
  private static final boolean DEFAULT_USE_GRID_VIEWER = true;
  private static final String DEFAULT_GRID_VIEWER_PARAMS = "descendant::Row/..,12,Xholon - Grid - Grid Viewer,true";
  
  private int rows = DEFAULT_ROWS;
  private int cols = DEFAULT_COLS;
  private String[] names = DEFAULT_NAMES;
  private String gridType = DEFAULT_GRID_TYPE;
  private String columnColor = DEFAULT_COLUMN_COLOR;
  private boolean cellsCanSupplyOwnColor = false;
  private String useGridViewer = DEFAULT_USE_GRID_VIEWER ? "true" : "false";
  private String gridViewerParams = DEFAULT_GRID_VIEWER_PARAMS;
  private String caption = null;
  
  private String nameGrid = null;
  private String nameRow = null;
  private String nameCol = null;
  
  // users will not normally need to change the following two
  private String gridEntityImplName = "org.primordion.xholon.base.GridEntity";
  private String gridPanelClassName = "org.primordion.xholon.io.GridPanelGeneric";
  
  /**
   * Optional style tag that should be added to HTML head
   */
  private String cssStyle = null; //"div#xhcanvas>canvas {border: 10px solid #FFFFFF;}";
  
  /**
   * Whether or not to build nameGrid, nameRow, and nameCol XholonClass nodes.
   */
  private boolean shouldBuildXhc = true;

  /**
   * Whether or not to build the CSH Grid subtree.
   */
  private boolean shouldBuildCsh = true;

  @Override
  public void postConfigure()
  {
    nameGrid = names[0];
    nameRow = names[1];
    nameCol = names[2];
    generate();
    if (cssStyle != null) {
      style(cssStyle);
    }
    super.postConfigure();
    this.removeChild();
  }
  
  /**
   * Generate a Xholon grid and a grid viewer.
   */
  protected void generate() {
    IApplication app = this.getApp();
    IXholonClass xhcRoot = app.getXhcRoot();
    IXholon service = this.getService("XholonHelperService");
    if (service != null) {
      if (shouldBuildXhc) {
        if (this.getClassNode(nameGrid) == null) {
          // IH
          String ihStr = new StringBuilder()
          .append("<")
          .append(nameGrid)
          .append(" xhType='XhtypeGridEntity' implName='")
          .append(gridEntityImplName)
          .append("'></")
          .append(nameGrid)
          .append(">")
          .toString();
          //this.println(ihStr);
          service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
          ihStr = new StringBuilder()
          .append("<")
          .append(nameRow)
          .append(" xhType='XhtypeGridEntity' implName='")
          .append(gridEntityImplName)
          .append("'></")
          .append(nameRow)
          .append(">")
          .toString();
          //this.println(ihStr);
          service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
          ihStr = new StringBuilder()
          .append("<")
          .append(nameCol)
          .append("/>")
          .toString();
          //this.println(ihStr);
          service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
          
          // optionally build more col names (ex: CoastCell,LandCell )
          if (names.length > 3) {
            StringBuilder ihSb = new StringBuilder()
            .append("<").append(IXml2Xholon.XML_FOREST).append("names>");
            for (int i = 3; i < names.length; i++) {
              ihSb.append("<")
              .append(names[i])
              .append("/>");
            }
            ihSb.append("</").append(IXml2Xholon.XML_FOREST).append("names>");
            ihStr = ihSb.toString();
            service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
          }
          // CD
          String cdStr = new StringBuilder()
          .append("<xholonClassDetails><")
          .append(nameCol)
          .append(" xhType='XhtypeGridEntityActivePassive' implName='")
          .append(gridEntityImplName)
          .append("'><config instruction='")
          .append(gridType)
          .append("'></config></")
          .append(nameCol)
          .append("></xholonClassDetails>")
          .toString();
          //this.println(cdStr);
          service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cdStr, xhcRoot); // -2013
          if (names.length > 3) {
            StringBuilder cdSb = new StringBuilder()
            .append("<xholonClassDetails>");
            for (int i = 3; i < names.length; i++) {
              cdSb.append("<")
              .append(names[i])
              .append(" xhType='XhtypeGridEntityActivePassive' implName='")
              .append(gridEntityImplName)
              .append("'/>");
            }
            cdSb.append("</xholonClassDetails>");
            cdStr = cdSb.toString();
            service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cdStr, xhcRoot); // -2013
          }
        }
      } // end if (shouldBuildXhc)
      if (shouldBuildCsh) {
        // CSH
        String cshStr = new StringBuilder()
        .append("<")
        .append(nameGrid)
        .append(" columnColor='")
        .append(columnColor)
        .append("'><")
        .append(nameRow)
        .append(" multiplicity='")
        .append(rows)
        .append("'><")
        .append(nameCol)
        .append(" multiplicity='")
        .append(cols)
        .append("'></")
        .append(nameCol)
        .append("></")
        .append(nameRow)
        .append("></")
        .append(nameGrid)
        .append(">")
        .toString();
        //this.println(cshStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cshStr, this.getParentNode()); // -2013
      } // end if (shouldBuildCsh)
      
      // GridCellPattern
      IXholon gcp = this.getFirstChild();
      while (gcp != null) {
        if ("GridCellPattern".equals(gcp.getXhcName())) {
          // TODO or have a separate GridCellPattern XholonClass ???  see: "Island B5" workbook
        }
        gcp = gcp.getNextSibling();
      }
      
      IGrid grid = null;
      if (this.getParentNode().getLastChild() == this) {
        grid = (IGrid)this.getPreviousSibling();
      }
      else {
        grid = (IGrid)this.getParentNode().getLastChild();
      }
      
      // grid viewer
      app.setParam("UseGridViewer", useGridViewer);
      app.setParam("GridPanelClassName", gridPanelClassName);
      app.setParam("GridViewerParams", gridViewerParams);
      IMessage rspMsg = app.sendSyncMessage(-1001, "createGridViewer", this); // -1001 see Application.processReceivedSyncMessage()
      IXholon view = app.getFirstChild().getNextSibling();
      service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, "<GridViewer></GridViewer>", view); // -2013
      // set default background color for grid;  -1001 see Application.processReceivedSyncMessage()
      Object rdata = rspMsg.getData();
      String ccolor = getGrid_columnColor(grid);
      if (ccolor == null) { // null or undefined
        ccolor = this.columnColor;
      }
      String sdata = new StringBuilder()
      .append("setGridCellColor,")
      .append(rdata)
      .append(",")
      .append(ccolor)
      .toString();
      //this.consoleLog(rdata);
      //this.consoleLog(ccolor);
      //this.consoleLog(sdata);
      app.sendSyncMessage(-1001, sdata, this);
      // IGridPanel.setCellsCanSupplyOwnColor(this.cellsCanSupplyOwnColor);
      String scdata = new StringBuilder()
      .append("setCellsCanSupplyOwnColor,")
      .append(rdata)
      .append(",")
      .append(this.cellsCanSupplyOwnColor)
      .toString();
      app.sendSyncMessage(-1001, scdata, this);
      
      if (caption != null) {
        this.caption(caption);
      }
    }
  }
  
  /**
   * Get the grid's column color.
   * @param grid an IGrid instance
   * @return a color String (ex: "FFFF01")
   */
  protected native String getGrid_columnColor(IGrid grid) /*-{
    return grid.columnColor;
  }-*/;
  
  protected native void style(String cssStyle) /*-{
    $wnd.xh.css.style(cssStyle);
  }-*/;
  
  protected native void caption(String caption) /*-{
    var div = $doc.querySelector("div#xhcanvas");
    // I have no choice but to repeat the "div#xhcanvas" below
    var canvas = div.querySelector("div#xhcanvas>canvas:last-of-type");
    if (canvas) {
      var figureDiv = $doc.createElement('div');
      var figure = $doc.createElement('figure');
      figureDiv.appendChild(figure);
      div.insertBefore(figureDiv, canvas);
      figure.appendChild(canvas);
      var figcaption = $doc.createElement('figcaption');
      if (caption == "ID") {caption = canvas.id;}
      else if (caption == "CLASS") {caption = canvas.className;}
      figcaption.innerHTML = caption;
      figure.appendChild(figcaption);
    }
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("rows".equals(attrName)) {
      this.rows = Integer.parseInt(attrVal);
    }
    else if ("cols".equals(attrName)) {
      this.cols = Integer.parseInt(attrVal);
    }
    else if ("names".equals(attrName)) {
      String[] arr = attrVal.split(",");
      //if (arr.length == 3) {
        this.names = arr;
      //}
    }
    else if ("gridType".equals(attrName)) {
      this.gridType = attrVal;
    }
    else if ("columnColor".equals(attrName)) {
      this.columnColor = attrVal;
    }
    else if ("useGridViewer".equals(attrName)) {
      this.useGridViewer = attrVal;
    }
    else if ("gridViewerParams".equals(attrName)) {
      this.gridViewerParams = attrVal;
    }
    else if ("cssStyle".equals(attrName)) {
      this.cssStyle = attrVal;
    }
    else if ("gridEntityImplName".equals(attrName)) {
      this.gridEntityImplName = attrVal;
    }
    else if ("gridPanelClassName".equals(attrName)) {
      this.gridPanelClassName = attrVal;
    }
    else if ("caption".equals(attrName)) {
      this.caption = attrVal;
    }
    else if ("cellsCanSupplyOwnColor".equals(attrName)) {
      this.cellsCanSupplyOwnColor = Boolean.parseBoolean(attrVal);
    }
    else if ("shouldBuildXhc".equals(attrName)) {
      this.shouldBuildXhc = Boolean.parseBoolean(attrVal);
    }
    else if ("shouldBuildCsh".equals(attrName)) {
      this.shouldBuildCsh = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }
  
}
