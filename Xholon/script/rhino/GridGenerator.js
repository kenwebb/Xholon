<script implName="lang:javascript:inline:"><![CDATA[
/*
 * This works if pasted into the Chameleon node.
 * But only if it's done through a XholonConsole, on the desktop or jnlp.
 */
var cnode = contextNodeKey;
var app = applicationKey;
var xhcRoot = app.getXhcRoot();
var service = cnode.getService('XholonHelperService');

// (1) Generate the xholon classes.
service.pasteLastChild(xhcRoot,
  "<Grid xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'/>");
service.pasteLastChild(xhcRoot,
  "<Row xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'/>");
service.pasteLastChild(xhcRoot, "<GridCell/>");
service.pasteLastChild(xhcRoot,
  "<xholonClassDetails><GridCell xhType='XhtypeGridEntityActivePassive' implName='org.primordion.xholon.base.GridEntity'><config instruction='Gmt'/></GridCell></xholonClassDetails>");

// (2) Generate the grid.
service.pasteLastChild(cnode,
  "<Grid><Row multiplicity='10'><GridCell multiplicity='10'></GridCell></Row></Grid>");

// (3) Generate and display the GridPanel.
app.setUseGridViewer(true);
app.setGridPanelClassName('org.primordion.xholon.io.GridPanelGeneric');
app.setGridViewerParams('descendant::Row/..,7,Xholon - MyApp - Grid Viewer,false');
var gvIndex = app.createGridViewer(-1);

// (4) Optionally set the grid background color.
//var gv = app.getGridViewer(gvIndex);
//gv.gridPanel.setGridCellColor(0xF0F0FF); // an RGB color

]]></script>
