<SaveGrid implName="org.primordion.xholon.base.Behavior_gwtjs" outTarget="webpage" gridOwnerXPath="IslandSystem/Space" gridCellIncludeList="LandCell,CoastalCell" nodeExcludeList="Fish,Cat"><![CDATA[
/**
 * Save the contents of a Xholon grid, such as in the Island game.
 * Paste or drag this entire file into any node.
 * Use RestoreGrid.js to restore a grid saved by this script.
 * outTarget: "outtab" "consolelog" "alert" "webpage"
 * gridOwnerXPath: "IslandSystem/Space"
 * gridCellIncludeList: "LandCell,CoastalCell"
 * nodeExcludeList: "Fish,Cat"
 * 
 * TODO
 * - 
 */
var me, gridOwner, outstr, gridCellIncludeArr, nodeExcludeArr, beh = {

postConfigure: function() {
  me = this.cnode;
  me.remove();
  outstr = "";
  $wnd.xh.avatar()["systemAvatar"] = true; // mark the system Avatar
  gridCellIncludeArr = null;
  nodeExcludeArr = null;
  if (me.gridCellIncludeList) {
    gridCellIncludeArr = me.gridCellIncludeList.split(",");
  }
  if (me.nodeExcludeList) {
    nodeExcludeArr = me.nodeExcludeList.split(",");
  }
  if (!gridCellIncludeArr) {
    outstr += "The gridCellIncludeList must contain at least one name.";
    $wnd.console.log(outstr);
    return;
  }
  gridOwner = $wnd.xh.root().xpath(me.gridOwnerXPath);
  if (!gridOwner) {
    outstr += "The XPath expression " + me.gridOwnerXPath + " evaluates to null.";
    $wnd.console.log(outstr);
    return;
  }
  //$wnd.console.log("SaveGrid OK");
  outstr += '<SavedGrid gridOwnerXPath="' + me.gridOwnerXPath + '" gridCellIncludeList="' + me.gridCellIncludeList + '" timestamp="' + Date.now() + '">\n\n';
  var ypos = 0;
  var xpos = 0;
  var row = gridOwner.first();
  while (row) {
    var cell = row.first();
    while (cell) {
      this.saveCellContent(cell, xpos, ypos);
      cell = cell.next();
      xpos++;
    }
    row = row.next();
    ypos++;
    xpos = 0;
  }
  outstr += '</SavedGrid>\n';
  delete $wnd.xh.avatar()["systemAvatar"]; // unmark the system Avatar
  switch (me.outTarget) {
  case "outtab":
    me.print(outstr);
    break;
  case "alert": // only shows a small part of the text
    alert(outstr);
    break;
  case "webpage":
    var xhdiv = $doc.querySelector("body");
    if (xhdiv) {
      var saveDiv = $doc.createElement("PRE");
      xhdiv.appendChild(saveDiv);
      saveDiv.innerText = outstr;
    }
    break;
  case "consolelog":
  default:
    $wnd.console.log(outstr);
    break;
  }
},

/**
 * <SavedCellContent xpos="11" ypos="22" gridCellType="LandCell">
 */
saveCellContent: function(gridCell, xpos, ypos) {
  if (gridCell.first() && (gridCellIncludeArr.indexOf(gridCell.xhc().name()) != -1)) {
    outstr += '<SavedCellContent xpos="' + xpos + '" ypos="' + ypos + '" gridCellType="' + gridCell.xhc().name() + '">\n';
    var node = gridCell.first();
    while (node) {
      if (nodeExcludeArr.indexOf(node.xhc().name()) == -1) {
        outstr += $wnd.xh.xport("Xml", node, "{}", false, true);
        outstr += '\n';
      }
      node = node.next();
    }
    outstr += '</SavedCellContent>\n\n';
  }
}

}
//# sourceURL=SaveGrid.js
]]></SaveGrid>
