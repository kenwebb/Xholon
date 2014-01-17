<script implName="lang:javascript:inline:"><![CDATA[
/*
 * This works if pasted into a GridOwner node.
 * Can paste directly into a node.
 * 
 * Can subsequently paste place nodes into the grid as:
 * <Glucose><MovingPlaceBehavior/></Glucose>
 * <Hexokinase><MovingTransitionBehavior/><FiringTransitionBehavior/></Hexokinase>
 */
var app = org.primordion.xholon.app.Application.getApplication(); //applicationKey;
var xhcRoot = app.getXhcRoot();
var cnode = app.getRoot().findFirstChildWithXhClass("PetriNet").getNextSibling(); //contextNodeKey;
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
  "<Grid><Row multiplicity='100'><GridCell multiplicity='100'></GridCell></Row></Grid>");

// (3) Generate and display the GridPanel.
app.setUseGridViewer(true);
app.setGridPanelClassName('org.primordion.xholon.io.GridPanelGeneric');
app.setGridViewerParams('descendant::Row/..,5,Xholon - ' + cnode.getXhcName() + ' - Grid Viewer,false');
var gvIndex = app.createGridViewer(-1);

// (4) Optionally set the grid background color.
var gv = app.getGridViewer(gvIndex);
gv.gridPanel.setGridCellColor(0x000000); // an RGB color 0xF0F0FF

]]></script>