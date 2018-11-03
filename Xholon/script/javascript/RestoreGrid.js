<RestoreContainer>

<RestoreGrid implName="org.primordion.xholon.base.Behavior_gwtjs" removeExisting="true"><![CDATA[
/**
 * Restore the contents of a Xholon grid, such as from the Island game.
 * Paste or drag this entire file into any node.
 * This processes content saved by SaveGrid.js .
 */
var me, gridOwner, gridCellIncludeArr, beh = {
postConfigure: function() {
  me = this.cnode;
  var sgNode = me.next(); // SavedGrid node
  if (sgNode) {
    gridCellIncludeArr = null;
    if (sgNode.gridCellIncludeList) {
      gridCellIncludeArr = sgNode.gridCellIncludeList.split(",");
    }
    gridOwner = $wnd.xh.root().xpath(sgNode.gridOwnerXPath);
    if (!gridOwner) {
      $wnd.console.log("The XPath expression " + sgNode.gridOwnerXPath + " evaluates to null.");
      return;
    }
    if (me.removeExisting == "true") {
      this.removeAllExistingCellContents(gridOwner);
    }
    var ynow = 0; // y position (row) right now while iterating thru the grid
    var xnow = 0; // x position (gridCell) right now while iterating thru the grid
    var row = gridOwner.first();
    var cell = row.first();
    var sccNode = sgNode.first(); // SavedCellContent node
    while (sccNode) {
      $wnd.console.log(sccNode.gridCellType + " " + sccNode.xpos + " " + sccNode.ypos);
      if (sccNode.ypos == ynow) {
        // remain on the same row
        while (sccNode.xpos > xnow) {
          cell = cell.next();
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
          xnow++;
        }
      }
      var node = sccNode.first();
      while (node) {
        var nextNode = node.next();
        if ((node.xhc().name() == "Avatar") && (node["systemAvatar"])) {
          var ava = $wnd.xh.avatar();
          ava["energy"] = node["energy"];
          var invNode = node.first();
          while (invNode) {
            var nextInvNode = invNode.next();
            ava.append(invNode.remove());
            invNode = nextInvNode;
          }
          cell.append(ava.remove());
        }
        cell.append(node.remove());
        node = nextNode;
      }
      sccNode = sccNode.next();
      if (sccNode.xhc().name() == "SavedGridContents") {
        this.restoreGridContents(sccNode);
        sccNode = sccNode.next();
      }
    }
    me.parent().remove();
  }
},

restoreGridContents: function(sgcNode) {
  if (!sgcNode.first()) {return;}
  var sgcText = sgcNode.first().text();
  $wnd.console.log(sgcText);
  var row = gridOwner.first();
  var tindex = 0;
  while (row) {
    var cell = row.first();
    while (cell) {
      var achar = sgcText.charAt(tindex);
      if (achar.toLowerCase() == achar) {
        cell["incognita"] = true;
      }
      else {
        cell["incognita"] = false;
      }
      tindex++;
      cell = cell.next();
    }
    tindex++; // handle the "\n"
    row = row.next();
  }
},

removeAllExistingCellContents: function(gridOwner) {
  var row = gridOwner.first();
  var cell = null;
  while (row) {
    cell = row.first();
    while (cell) {
      if ((gridCellIncludeArr && gridCellIncludeArr.indexOf(cell.xhc().name()) != -1) || !gridCellIncludeArr) {
        var node = cell.first();
        while (node) {
          var nextNode = node.next();
          if ((node.xhc().name() == "Avatar") && (node == $wnd.xh.avatar())) {
            // retain this node
          }
          else {
            node.remove();
          }
          node = nextNode;
        }
      }
      cell = cell.next();
    }
    row = row.next();
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

<!-- TODO SavedGridContents -->

</SavedGrid>

</RestoreContainer>
