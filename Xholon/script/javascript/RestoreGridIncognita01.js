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

<SavedGrid gridOwnerXPath="IslandSystem/Space" gridCellIncludeList="LandCell,CoastalCell" timestamp="1541249234528">

<SavedCellContent xpos="91" ypos="5" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="93" ypos="6" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="82" ypos="7" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
<Avatar roleName="Castaway" speechOut="0" energy="92065" islandID="0" systemAvatar="true">
  <FishingRod mass="3" energy="0"></FishingRod>
  <Fruit energy="10" mass="1"></Fruit>
  <JuicyBerry energy="10" mass="1"></JuicyBerry>
  <Stick energy="0" mass="1"></Stick>
</Avatar>
</SavedCellContent>

<SavedCellContent xpos="90" ypos="7" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="91" ypos="7" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="86" ypos="8" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="87" ypos="9" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="85" ypos="12" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="83" ypos="13" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="32" ypos="14" gridCellType="CoastalCell">
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

<SavedCellContent xpos="91" ypos="14" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="92" ypos="14" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="89" ypos="15" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="21" ypos="16" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="22" ypos="16" gridCellType="CoastalCell">
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

<SavedCellContent xpos="23" ypos="16" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="96" ypos="16" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="96" ypos="17" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="85" ypos="18" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="26" ypos="20" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="9" energy="0" mass="1"></Thorn>
  <Vine maxClones="9" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="83" ypos="20" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="26" ypos="21" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="80" ypos="21" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="91" ypos="21" gridCellType="CoastalCell">
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

<SavedCellContent xpos="83" ypos="22" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="90" ypos="24" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="22" ypos="25" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="35" ypos="25" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="8" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="9" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="8" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="22" ypos="32" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="66" ypos="40" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="70" ypos="41" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="64" ypos="42" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="55" ypos="43" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="67" ypos="45" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="50" ypos="46" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="53" ypos="48" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="54" ypos="48" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="70" ypos="49" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="85" ypos="49" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="55" ypos="50" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="56" ypos="50" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="71" ypos="50" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="76" ypos="50" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="52" ypos="51" gridCellType="CoastalCell">
<City roleName="Ottawa" mass="9999" energy="0">
  <University roleName="Carleton" mass="9999" energy="0">
    <Library mass="9999" energy="0">
      <CoffeeShop roleName="Starbucks" mass="9999" energy="0">
        <IslandControlCentre mass="9999" energy="0">
          <SecretProjects>
            <Annotation>working on it</Annotation>
            <ElDorado></ElDorado>
            <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
          </SecretProjects>
        </IslandControlCentre>
      </CoffeeShop>
      <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
      <Avatar roleName="Troll" speechOut="0">
        <Attribute_String>[Troll will repeatedly look for and take students from anywhere in the city, and will drop them in the IslandControlCentre];
wait 1;
exit;
take *treeWanderer;
exit;
wait 1;
enter library;
wait 1;
enter *coffeeShop;
wait 1;
enter islandControlCentre;
drop *treeWanderer;
wait 1;
exit;
wait 1;
</Attribute_String>
      </Avatar>
    </Library>
    <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
    <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
  </University>
</City>
</SavedCellContent>

<SavedCellContent xpos="70" ypos="51" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="74" ypos="51" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="61" ypos="52" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="91" ypos="52" gridCellType="CoastalCell">
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

<SavedCellContent xpos="85" ypos="53" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="80" ypos="54" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="83" ypos="54" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="60" ypos="55" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="70" ypos="55" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="76" ypos="55" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="79" ypos="55" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="56" ypos="56" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="64" ypos="56" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="89" ypos="56" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="73" ypos="58" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="77" ypos="58" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="68" ypos="60" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="70" ypos="60" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="82" ypos="60" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="85" ypos="60" gridCellType="CoastalCell">
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

<SavedGridContents><Attribute_String>
oOOOOOOooOOOOoooooooooooooooooOoooooooooooooooooooooooooooOOOoOOoOoOoOOOooooooooooooooooooooooooooooooOOOoOooOoOOOOoOOOo
OoOOOOooOOOOoOoOoooooooooooooooOoooooooooooOooooooooooooooooooOOoooooOoOoooooooooooooooooOoooooooooooooOoOOooOoOOOoOOOOO
OOOOOOooOOOOOOooOoooooooooooooooooooooooooOOOoOOooooooooooOoOooOOoooooOooooooooooooooooOoOoOooooOoOooOOooooOOoOOOoOOOOOO
OOOoOOoOOOOOOOOOooooooooooooooooooooooooOooooooOoOooooooooOOoOoooooooOoOoooooooooOooooOooOoOOooooOOOooOoooOoOooooOOOOOOO
OOOOOOOOOOOoOOOOOOOoooooooooooooooooooooOoooOoooOoooooooooOooOooooooooOOoooooooooCCCCCCCCCCCCoOooOoOOOoOOoooooooOooOOOOO
ooooOOOOOOOOOOOOOoOooooooooooooooooooooOoooOOooooooooooooooooOoooooooOOoooooooooOCLLCLLLCCLLCoOOOOOoooOoooooOoooooooOOOO
ooooOOOOOOOOOOOoooooooooooooooooooooOoOoooOoOOOooooooooooooOoooooooooOOOoooooooCCCCLLLLLLLLCCCCooOoooooOooooooooooOoOoOO
OOOOOOoOoOoooOOooooOoooooooooooooooOoooooooOOoooooooooooooOoooooooooOOoooooooooCLLLLLLLLLLLLLLCoOoOoOOoOooooooooOoooooOO
oOOooOoOOOoooOooooOooooooooooooooooooooooooOOooooooooooooOoOOOooooooOOOOOooOOooCLLLLLLLLLLLLLLCooOooOOOOooOOooooOOOooOOO
OOoOOOOoOoOOOoOooOOoooooooooooooooOoOOoooOOOoOoooooooOoOooOooooooooOOOOoOoOoOooCCCCLLLLLLLLLCCCoOooooOOOooooOoooooooOooO
oOoOOoOOOoOoOooOOOOOoooooooooooooooOoOooooooooooooooOooOooooOOooooooOOOOOOoOOOoOoCCCCCLLLLCCCooooooooOoooOOOoOoOOooooooO
oOOoOOOOOOOOoOooOOooooooooooooooooOooooooooooOOooOooOOOoooOoOOOoooOoooooOOOOooooOCLLLLLLLLLCOooooooOoooOOoOoOoOOOOooooOO
oOOoOOOooooOooOOOOOooooooooooooooooOOOOoOoOooooOOoOoOOOoooOOOOoOOooOOoOOOOOOoOooCCCLLLLLLLLCOOooooooooOOoOoOoooOOOoOoooo
OOoOOOOOOoOOOOooOoOooOOOOOOOOoooooOooooOOOoOOooOoOOOOooooOoOOOOOOOOOOoooOOoOoOOOCLLLLLLLLLLCCCOOOOOOOOOOOoOooOooooOooooO
OOOOOOOOoOOOOOOooOoooCCCCCCCCCCCCOoOOooOOOOOoOooooOoOooooooOoOoOOOoOoooOOOOooOoOCCLLLLLLLLLLLCCCCCCCCCoOoOOoOOOOooOoOooO
OOOOOOOOoOOOOOoOooooOCLLCLLLCCLLCooOoOOOOoOoOOoOoOOoOOOoooOOOoooOOOoooOoOOOoOooOoCCCLLLLLLLLLLLLLLLLLCOOOOoOOOOOoOOOOOOO
OoOOOOOOOOOOOoooooocCCCLLLLLLLLCCCCOoOoOOoOoOOOooOoOOOoooOOOoOooooOOooooOoOoOooOOOCLLLLLLLLLLLLLLLLCCCOOooOOOooOoOoooOoO
OOoOOOOoOOOOOooooooCLLLLLLLLLLLLLLCoOoOoOOooOoooOOooOoOoOOOoOOOOooOOOOoooOOOoooooOCCCLLLLLLLLLLLLLCCOOOOOoOOOOOOOOoooooo
oOOOOooOOOooOooooooCLLLLLLLLLLLLLLCoooOOoOoOoooooooooooOOoOOOOoOOOOOOOooooOooOooOOCLLLLLLLLLCCCCCCCOOOOOoOOOOoOOOOOooOOO
oOOOoOOOOOOOoooOOooCCCCLLLLLLLLLCCCoOooOOOoOOoOOoOOoooooOOOOOOOOOOOOOoOooooOooOooCCCCCLLLLCCCOOOOOOOOOoOOoOOOOOOOOOooooo
oOoooOOOOOooOOoOOooOOCCCCCLLLLCCCOOoOoOOOOooOoOoOOOoooOooOoooOOOoOOOoOOOOoOoooOOOCLLLLLLLLLLCOOoOoOOOOOooOOOOOOoOOooOOOo
OooooOOOOoOOOOOoooooOCLLLLLLLLLCOOoooOooOooooooooOOoOoOOoooooOOOOOOOOOOOOOoOOoOCCCCLLLLLLLLCCCCoOOOoOOOOOoOoOOOOoOOoooOO
oOoooOOOoOooooooooooCCCLLLLLLLLCOOooOOOOoOoooooooOOOoOOOOoOoOOOOOooooOOOOOoooOOCLLLLLLLLLLLLLLCooOOOoOOooOOOOOoOOOOoOoOO
oooOoOOoOOOOoOOOOoooCLLLLLLLLLLCCCOOOOOOOOOOOoOOOoOOOOOOoOoOOoOoOOOOOOOOOOOOOOoCLLLLLLLLLLLLLLCoOOOoOooooooOOoooOoOOOoOO
OooOOOOOOOOOOooOooOoCCLLLLLLLLLLLCCCCCCCCCoOoOooOOooOoOoOOOOOOOoOOoOOOOOOoOOOOOCCCCLLLLLLLLLCCCOOOooOoOoooOoooOoooOooooO
oOOOooOOOooooOoOooooOCCCLLLLLLLLLLLLLLLLLCoooOoOOOOOOOoOOOOoOOOOOOOOOOOOOOOOOOoOOOCCCCLLLLCCCOOOOooooOoOOOOooOOooooooooo
OOOOoOoOOOOOOoOooooooOCLLLLLLLLLLLLLLLLCCCooOoOoOOoOOOOOOOOOoOoOooOOOOOOOOOoOOOOoOOOOCCCCCCOOoOooOOOoooooOOOooooooooOoOO
oOOOOOOoOoOooOoOooooooCCCLLLLLLLLLLLLLCCOOooOooOOoOOOOooOOOOOoOOOOOoOOOOOOOOooOOOOOOoOOOOOOOoOOOOoOOooOooOOoooooOooooOOo
oOoOOOOOooooOOoOooooooCLLLLLLLLLCCCCCCCOOoOoooOooOoOOOOOOoOOOOoOoOOOOOOOOOoOOOoOoOoooooooOooooOOOOOOOOOOOOooOoOooooooooo
OoOOoooOOooOOOOOoooooCCCCCLLLLCCCOoOOOOOoOooOoOooOoOOOOOOOOOOOOOoOOOoOOOOOOOOOoOOoOOoooooooooOOOOOoOOoOoOOOOOOooooooOooo
oOoOooOooooOoOOoooooOCLLLLLLLLLLCoooOooooOoOoOOOoOoOooOOoOOOOoOOOOOOOOoOOoOooOOOoOooooooooOoOoOOOOOooOOOoOoooOOoOooooooO
ooOoOooOoooooOOooooCCCCLLLLLLLLCCCCoOOOoOOOOoOOOOOoOOoOOOOOOOOOOOOOOOoOOOOOoOOOOOOOoOoooOOOOOOoOOOOoOoOOOOoOoOOooooooOOO
oOOOOooOooooOOOOOOoCLLLLLLLLLLLLLLCoOoOOooOOOOOoOOOOOOOoOOOOOOOOOOoOOOOOoOOOOOOOOOOooooOoOOoOoOOOOOoOOOOOoooOoOoooOoOOoo
OOOOOOOOOoOoOOOOoOoCLLLLLLLLLLLLLLCoooOoOOooOOOOOOOOOoOoOOOOoOOOOOOOoOOoOOOOOOOOOOOOOOOoOOoOOOOOOOoOOOOOOoooOOOooooooOOO
OOOOOOOOOOOooooooooCCCCLLLLLLLLLCCCooOooOooOOoOOOOOOOOoOOOOOoOOOOOOOOooOOooOOOOOOOOOOOOOOOOoOOOOOOoOOOOOOOOoOoOOoOooOOoo
oOOOOOOOOOOooooooOoOOOCCCCLLLLCCCOOOOOoOooOOOooOOOOOOOoOOOooOOOooOooooOOOOoOOOoOOOOOOOOOOOOOOOOOooOOOOOoOoOOOoOooooooOOo
oOOOOOOOOOOoOooooooooooOoCCCCCCOOOoooooooOoOOoOoOOOOoOOOoOOooOOOoooOoOOOOOOoOOOOOOOOOOOOOOOOOOooOOOOooOOOOoOOOOOOOooOOOO
OOOOoOOOOOoooOoOoooooooOooOOOOOOOOoooOoOOooooOOOOOoOOOOoooooOOOoooooooOoOOOOOOOOoOOoOooOOOOOOOoOOOoOooOoOOOOOooOOooOOooo
OOOOOoOOoOoooooOoooOOoOOOOOoOOoOooooooooooooOOOoOOOoOoOooooOOOOOoOOOooOOOOOOOOOOOOOOoOOOOOOOOOoOOOOOOOOoOOOoOoooOoOOoooo
ooOOOOoooOooooooooooooooooOOoOoOoooOoooOoooooOoooOOoooOOoooCCCCCOCCCOOCCCCCCCOOOOoooOOOOOOOOOoOoOOOOOoOOOOoOOOoOooOOoooo
ooooOOoOOoOooooooooooooooooooOOOOoooOooooooooooOOoOooooOooCCLCLCCCLCCCCLCCLLCCoOoOOoOOOOOOOOOOooOooOOOOOOOOoOoOoOOOOoooO
oOoOoOOOOOooooooooooooooooOooOOOOOoOOooOooooooooooOOoOoOocCCLLLLLLLLLLLLLLLLLCooOOOOOOOOOOOOOOOoOOOoOOOOOOOoooOOOoOooooo
ooOOOOOOOOOooooOoOooooOoOOoooOOOOOOoOOOooOooooOOoOOOooOCCCLLLLLLLLLLLLLLLLLCCCoOOOOOOOOOoOOOOOOOOoOOOOOOooOOOoOOoooooooO
oooOoOOoOooooooooOoooooooOOOOOoOOOoOoooooooooooOoOOOOOOCLLLLLLLLLLLLLLLLLCCCOOOOOoOOOOOOOOOOOOOOooOOOOOoOOoooOoOoooooooo
ooOOOOOOOOoOooOooooooooooOOOOOooOoOOoOooooooooooOOOCCCCCCLLLLLLLLLLLLLLLLLLCCOOOOOOOOOOOOOOoOOOOOoOOOOoOOoooooOooooooOOo
ooooooOoOOooooOoOoooooooooOOooOOoOoooOooooooOOooOCCCLLLLLLLLLLLLLLLLLLLLLLLLCCCOOOOOOoOOOOoOOoOOOOoOOOooOooOOOoOoOooOoOo
oOooooOoOOOOoooooooooooooOOOOOOoooooooooooooooOooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCCCCCOOOOoOOOOOOOOoOOooOoooOoOoOooooooo
oOooooooOOOOOoooooooooOoOOOOOoOOooOooooOOoOoOOoooCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCOoOOOOOOOOOOooOoOoooooooooooooO
ooooOooooOOoOOooooOoooooOoOOOOoooooooOoooooOOooooOCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCooOoOOOOOOooooooOoooOooooOooooo
ooOoooOoOoOooOoooooOooOOOOOOOOOoOooooOooooOooOOoooOCCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCCCOOOOOOOOOoOoooOOOoooOoOoooooooo
oOOooooOoooOoooooOoooooOOOOOOOoOooooOooooooooOoooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCOOOOOOOOOoOOOoOooooooooOOOoOOooooOo
oooooOOooOOOOooooOooooooOOOOOOOOoOoooooooooOOoOoooooCCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCOOOOOOOOOoooOoooOooooooOoOoooooO
OoOooOOOoOOoOoOoOoooOOooOoOoOOOOoooooooooOoOooOoooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCCOOOOOOOOooooOoooOOooooooooOO
oooOooooooOOOooOoooooooOoOOOOOOOOOoOOoooOOOoooooooooCCCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCoooOOOooooOoooooOooooooOo
oOOooooOoooooooOoooOoOoOOOOOOOoOoOooOooOoOOoooOoooooOOoCCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOOooOooooooOoOoOOoOOoOOoo
oOOOoooOoOOOoooooOooOoOOOOOOOOoOOOoOOooOoOOooooOoooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCoOOOOooOooooooooooooooOOo
OOOoooooooOoOoOOOoooOoOOOOOoOoOOoOOOOoooOOOOOOoooOooooooCCCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCOOOOOOOooooooooooOOOOooOOoO
OOooOOooooooOOOOooooooOooooOooOOoooOOOOOOoOOooOoooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCoOoOOOOOOooooooooooooooooOoOO
OOOOOOoooOooOoOOoooooOoOoooOooOOOoooOooOOoOoOOOOooooOoooCCCCCCCLLLLLLLLLLLLLLLLLLLLLCCCCOOOOoOOooOOoOOooooooooOooooooOoo
oOOOOOooOoooOoOOooooOooOOOOOOOOooooooooooooOooOoooooooooooOOOCLLLLLLLLLLLLLLLLLLLLLLLCOOoOoOoOooooooOoOOOoOoOOooOOoOOooo
oOoOOooooooooOoooooooOoOooOOOOOooooOOooOoooooOoOoooooooooooooCCCLLLCCCCCCCCCCCCLLLLCCCoooooooooooooooooOOoooOoOOOOoooOoo
ooOOOOOoOOooOOoOooooOOoOOOoOOOooOooOoooooooooooooooooOooooooooocCCCCOOOOOOOOOOCCCCCCOOooooooooooooooooooooOooooOooooooOo
OOOOOOoooooooooooooOOooOOoOooooooooOooooooOoOooOooooooooooooooooOOOoooooooooooOOOOOOooooooooooooOoooooooooOooooooooooooO
OooOOoooooooOooOoOoOoooooOOoooooooOooooooooooooooOooooooooooooooooooooOoooooooOOoooooooooooooooooOOoOoOoooooOoooOOoooooo
OoOOOooOooOoOOooOoOoOoooooOoooooooOOooOoooOooooooooooOooooOoooooooooooOooooooOoooooooooooooooooooOOooOOoOOOOOooooOOOOooo
OooooooooOoOOoooOOOOoooOOOOOooooOooooooooOoooooooooooooooooooooooooooOOOoooooooooooooooooooooooooOOoOOOOOOOOOoooOooOOOOo
OOOoooOOoooOOOOOOOOOoooOOOoOooooooooOooooooooooooooooOOoOOoooooooooOooOoOOoooOooooooooooooooooooOoOoOoOOOOOOOoOOoooOoOOo
OoooooooooOOOOOOOOOOooOOOOooooooooooooooooooooOooOoooooOoooooooooooooooOOOoOooooooooooooooooooooOoOOoOOOOOOOOOOOooOOOooo
OoooooooooOOOOoOoOOOOooOOOoooooooOoooooooOooOoooooOOoooooooooooooooooOOOOOoooooooooooooooooOooOoOOOOooOOOOOOOOOOOOOoOOOO
OOoooooooOOOOOOOoooOOoOOoOOooOooooooooooooooooooooooooooOooOoOooooooOoooooooooooooooooooooOoOOOoOOOOOOOOOOOOOoOOOoOOOOOo
OoooOooooOOoOOOOoOooOOoOOoooooooooooOooOooooooOooooOoooooooooooooooOoooooooooooooooooooOooOoOoOoOOOOOOOOOOOOOOOOOOoOOOOO
OOOooooOoOOOOoOoooOOOOOOOooOoOoOoooooooooooOoooooOoooooooooooooOOoOOooooooooooooooooooooooooooOOOOOOOOOOOOOoOOOOoOOOOOOo
OOOooOOOoOoOOOOoooOOOOOOOOOOooooooooooooooooooooooooooooooooooOOooooooooooooooooooooooooOOoooOOOOOOOoOOOOOOOOOOOOOOOOOOO
oooOoOoOOoOOOOOoOoOOOOOOOOOOOooOooooooooooooooooooooooooooOooooOoooooooooooooooooooooooooooOoooOOoOOoOOOOOOOOOOoOOOOOOOO
oOOOOOOOOOOOOoOOOOOOOOOOOOOOOOOOooooooooooOooooooooooooooooooOoOoooooooooooooooooooooooooooooooOOOoOoOOOOOOOOOooOOOOOOOO
OOOOOOOOOOOOOOOoooOOoOOOOOOOOOoOooooooooooooooooooooooooooooooOOooooooooooooooooooooooooooooOooooooooOOOOOOOOoOOoOOoOOOO
OOOOOOOoOOOOOOoOoOOoOOoOoOOOOOooOooooooooooooooooooooooooooooOoOoooooooooooooooooooooooooooooooooooooooOOOOOoOoOOOOooOOO
oOOOOoOOOOOooOOooooOOOoooOOOOOOOooooooooooooooooooooooooooooooOooooooooooooooooooooooooooooooooooOoooooooooOooOOoOOooOOO
OOOOOoOOOOooooOooooOOOooooOOooooooOoooooooooooooooooooooooooOOooooooooOoooooooooooooooooooooooOoooooOOoOOOOOoOOOOoOOOOOO
oOOOOOOOOOOOoooOooooooooooOoooOOOOooooooooooooooooooooooooooOOooOOOOOOoOoooooooooooooooooooOooooooooOoOoOOooOoOoOOoOOooO
</Attribute_String></SavedGridContents>

</SavedGrid>

</RestoreContainer>
