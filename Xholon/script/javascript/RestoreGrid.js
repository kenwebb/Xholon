<RestoreContainer>

<!--<RestoreGrid implName="org.primordion.xholon.base.Behavior_gwtjs" gridOwnerXPath="RestoreContainer" gridCellIncludeList="LandCell,CoastalCell">-->
<RestoreGrid implName="org.primordion.xholon.base.Behavior_gwtjs" removeExisting="true"><![CDATA[
/**
 * Restore the contents of a Xholon grid, such as from the Island game.
 * Paste or drag this entire file into any node.
 * This processes content saved by SaveGrid.js .
 */
var me, gridOwner, gridCellIncludeArr, beh = {
postConfigure: function() {
  $wnd.console.log("RESTORE");
  me = this.cnode;
  $wnd.console.log(me);
  var sgNode = me.next(); // SavedGrid node
  $wnd.console.log(sgNode);
  if (sgNode) {
    gridCellIncludeArr = null;
    if (sgNode.gridCellIncludeList) {
      gridCellIncludeArr = sgNode.gridCellIncludeList.split(",");
    }
    gridOwner = $wnd.xh.root().xpath(sgNode.gridOwnerXPath);
    $wnd.console.log(gridOwner);
    if (!gridOwner) {
      $wnd.console.log("The XPath expression " + me.gridOwnerXPath + " evaluates to null.");
      return;
    }
    var ynow = 0; // y position (row) right now while iterating thru the grid
    var xnow = 0; // x position (gridCell) right now while iterating thru the grid
    var row = gridOwner.first();
    var cell = row.first();
    this.removeExistingCellContents(cell);
    var sccNode = sgNode.first(); // SavedCellContent node
    while (sccNode) {
      //var nextSccNode = sccNode.next();
      $wnd.console.log(sccNode.gridCellType + " " + sccNode.xpos + " " + sccNode.ypos);
      if (sccNode.ypos == ynow) {
        // remain on the same row
        while (sccNode.xpos > xnow) {
          cell = cell.next();
          this.removeExistingCellContents(cell);
          xnow++;
        }
      }
      else {
        // find a new row
        while (sccNode.ypos > ynow) {
          row = row.next();
          ynow++;
        }
        cell = row.first();
        xnow = 0;
        while (sccNode.xpos > xnow) {
          cell = cell.next();
          this.removeExistingCellContents(cell);
          xnow++;
        }
      }
      //cell.append(sccNode.first().remove());
      var node = sccNode.first();
      while (node) {
        var nextNode = node.next();
        cell.append(node.remove());
        node = nextNode;
      }
      sccNode = sccNode.next(); //nextSccNode;
    }
    //sgNode.remove();
    me.parent().remove();
  }
},

removeExistingCellContents: function(cell) {
  $wnd.console.log("trying " + cell.name());
  if (gridCellIncludeArr && gridCellIncludeArr.indexOf(cell.xhc().name()) != -1) {
    var node = cell.first();
    while (node) {
      var nextNode = node.next();
      $wnd.console.log("removing " + node.name());
      node.remove(); // TODO nothing happens
      node = nextNode;
    }
  }
}
}
//# sourceURL=RestoreGrid.js
]]></RestoreGrid>

<!-- SavedGrid XML String should go here -->
<!-- example: -->
<SavedGrid gridOwnerXPath="IslandSystem/Space" gridCellIncludeList="LandCell,CoastalCell" timestamp="1540917275703">

<SavedCellContent xpos="91" ypos="6" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="84" ypos="60" gridCellType="CoastalCell">
<TreasureChest mass="1000">
  <Stick maxClones="1" energy="0" mass="1"></Stick>
  <Vine maxClones="1" energy="0" mass="1"></Vine>
  <Thorn maxClones="1" energy="0" mass="1"></Thorn>
  <CatTreat maxClones="10" energy="0" mass="1"></CatTreat>
  <Volleyball roleName="Wilson" maxClones="1" energy="0" mass="1">
    <Annotation>https://en.wikipedia.org/wiki/Cast_Away</Annotation>
  </Volleyball>
</TreasureChest>
</SavedCellContent>

</SavedGrid>

</RestoreContainer>
