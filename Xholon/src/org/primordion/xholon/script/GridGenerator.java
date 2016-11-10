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
  private String useGridViewer = DEFAULT_USE_GRID_VIEWER ? "true" : "false";
  private String gridViewerParams = DEFAULT_GRID_VIEWER_PARAMS;
  
  private String nameGrid = null;
  private String nameRow = null;
  private String nameCol = null;
  
  @Override
  public void postConfigure()
  {
    nameGrid = names[0];
    nameRow = names[1];
    nameCol = names[2];
    generate();
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
      if (this.getClassNode(nameGrid) == null) {
        // IH
        String ihStr = new StringBuilder()
        .append("<")
        .append(nameGrid)
        .append(" xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'></")
        .append(nameGrid)
        .append(">")
        .toString();
        this.println(ihStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
        ihStr = new StringBuilder()
        .append("<")
        .append(nameRow)
        .append(" xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'></")
        .append(nameRow)
        .append(">")
        .toString();
        this.println(ihStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
        ihStr = new StringBuilder()
        .append("<")
        .append(nameCol)
        .append("/>")
        .toString();
        this.println(ihStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013
        // CD
        String cdStr = new StringBuilder()
        .append("<xholonClassDetails><")
        .append(nameCol)
        .append(" xhType='XhtypeGridEntityActivePassive' implName='org.primordion.xholon.base.GridEntity'><config instruction='")
        .append(gridType)
        .append("'></config></")
        .append(nameCol)
        .append("></xholonClassDetails>")
        .toString();
        this.println(cdStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cdStr, xhcRoot); // -2013
      }
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
      this.println(cshStr);
      service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cshStr, this.getParentNode()); // -2013
      IGrid grid = (IGrid)this.getParentNode().getLastChild();
      // grid viewer
      app.setParam("UseGridViewer", useGridViewer);
      app.setParam("GridPanelClassName", "org.primordion.xholon.io.GridPanelGeneric");
      app.setParam("GridViewerParams", gridViewerParams);
      IMessage rspMsg = app.sendSyncMessage(-1001, "createGridViewer", this); // -1001 see Application.processReceivedSyncMessage()
      IXholon view = app.getFirstChild().getNextSibling();
      service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, "<GridViewer></GridViewer>", view); // -2013
      // set default background color for grid;  -1001 see Application.processReceivedSyncMessage()
      app.sendSyncMessage(-1001, "setGridCellColor," + rspMsg.getData() + "," + getGrid_columnColor(grid), this);
    }
  }
  
  /**
   * 
   * TODO if the columnColor ends in "0" (ex: "FFFF00"), then trailing "0" characters are lost
   */
  protected native String getGrid_columnColor(IGrid grid) /*-{
    return grid.columnColor;
  }-*/;
  
  /*
   * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
   */
  public int setAttributeVal(String attrName, String attrVal) {
    if ("rows".equals(attrName)) {
      this.rows = Integer.parseInt(attrVal);
    }
    else if ("cols".equals(attrName)) {
      this.cols = Integer.parseInt(attrVal);
    }
    else if ("names".equals(attrName)) {
      String[] arr = attrVal.split(",");
      if (arr.length == 3) {
        this.names = arr;
      }
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
    return 0;
  }
  
  /**
   * Make a set of data plotter params, if they don't already exist.
   * @param app
   */
  /*protected void makeDataPlotterParams(IApplication app) {
    if (dataPlotterParams == null) {
      String modelName = app.getModelName();
      if (modelName == null) {
        modelName = "Title";
      }
      modelName.replace(',', '_'); // replace any commas, which are used as separators
      dataPlotterParams = modelName + DEFAULT_PARTIAL_DATAPLOTTERPARAMS;
    }
  }*/
  
  /**
   * Write a Plot script to an XML writer.
   * This has to be a static method,
   * because Plot nodes typically remove themselves from the tree,
   * and are therefore not available later to write themselves out.
   * @param xmlWriter
   * @param app
   */
  public static void writeXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, IApplication app) {
    /*xmlWriter.writeStartElement("Plot");
    xmlWriter.writeAttribute("mode", "ifNotAlready");
    String dp = "google2";
    if (app.getUseDataPlotter()) {
      if (app.getUseGnuplot()) {dp="gnuplot";}
      //else if (app.getUseGoogle()) {dp="google";}
      else if (app.getUseGoogle2()) {dp="google2";}
      else if (app.getUseC3()) {dp="c3";}
      else if (app.getUseNVD3()) {dp="nvd3";}
    }
    xmlWriter.writeAttribute("dataPlotter", dp);
    xmlWriter.writeAttribute("dataPlotterParams", app.getDataPlotterParams());
    xholon2xml.writeSpecial(app);
    xmlWriter.writeEndElement("Plot");*/
  }
  
}
