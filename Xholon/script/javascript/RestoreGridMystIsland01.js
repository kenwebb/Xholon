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

<SavedGrid gridOwnerXPath="IslandSystem/Space" gridCellIncludeList="LandCell,CoastalCell" timestamp="1541335690552">

<SavedCellContent xpos="60" ypos="5" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="64" ypos="6" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="53" ypos="8" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="56" ypos="8" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="58" ypos="9" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="65" ypos="9" gridCellType="CoastalCell">
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

<SavedCellContent xpos="53" ypos="11" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="45" ypos="13" gridCellType="CoastalCell">
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

<SavedCellContent xpos="49" ypos="14" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="42" ypos="15" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="47" ypos="16" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="48" ypos="16" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="43" ypos="17" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="32" ypos="18" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="36" ypos="19" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="52" ypos="20" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="53" ypos="21" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="30" ypos="22" gridCellType="CoastalCell">
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

<SavedCellContent xpos="31" ypos="23" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="39" ypos="23" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="34" ypos="24" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="99" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="47" ypos="25" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="9" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="55" ypos="25" gridCellType="LandCell">
<Hut roleName="Cottage" mass="9999" energy="0">
  <Thorn energy="0" mass="1"></Thorn>
</Hut>
</SavedCellContent>

<SavedCellContent xpos="53" ypos="27" gridCellType="LandCell">
<Avatar roleName="Castaway" speechOut="0" energy="95397" islandID="0" systemAvatar="true">
  <FishingRod mass="3" energy="0"></FishingRod>
</Avatar>
</SavedCellContent>

<SavedCellContent xpos="53" ypos="28" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="4" energy="0" mass="1"></Stick>
</GreenTree>
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="4" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="48" ypos="29" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="55" ypos="29" gridCellType="LandCell">
<FreshWater energy="0" mass="1"></FreshWater>
</SavedCellContent>

<SavedCellContent xpos="60" ypos="29" gridCellType="CoastalCell">
<Hut roleName="Cottage" mass="9999" energy="0"></Hut>
</SavedCellContent>

<SavedCellContent xpos="52" ypos="33" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="52" ypos="35" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="38" ypos="36" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="59" ypos="36" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="42" ypos="39" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="56" ypos="39" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="8" energy="0" mass="1"></Thorn>
  <Vine maxClones="9" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="33" ypos="42" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="34" ypos="42" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="68" ypos="43" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="45" ypos="44" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="47" ypos="44" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="69" ypos="44" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="77" ypos="44" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="67" ypos="45" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="73" ypos="45" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="68" ypos="46" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="29" ypos="51" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="34" ypos="54" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="28" ypos="55" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="5" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="37" ypos="56" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="28" ypos="58" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="25" ypos="59" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="29" ypos="59" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="32" ypos="61" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="10" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="8" ypos="63" gridCellType="CoastalCell">
<City roleName="Ottawa" mass="9999" energy="0">
  <University roleName="Carleton" mass="9999" energy="0">
    <Library mass="9999" energy="0">
      <CoffeeShop roleName="Starbucks" mass="9999" energy="0">
        <IslandControlCentre mass="9999" energy="0">
          <SecretProjects>
            <Annotation>working on it</Annotation>
            <ElDorado>
              <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
            </ElDorado>
          </SecretProjects>
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
            <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
          </Avatar>
        </IslandControlCentre>
      </CoffeeShop>
    </Library>
    <TreeWanderer implName="org.primordion.xholon.script.TreeWanderer"></TreeWanderer>
  </University>
</City>
</SavedCellContent>

<SavedCellContent xpos="28" ypos="64" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="3" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="19" ypos="65" gridCellType="CoastalCell">
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

<SavedCellContent xpos="24" ypos="65" gridCellType="LandCell">
</SavedCellContent>

<SavedCellContent xpos="18" ypos="69" gridCellType="LandCell">
<GreenTree mass="9999" energy="0">
  <Fruit maxClones="10" energy="10" mass="1"></Fruit>
  <JuicyBerry maxClones="10" energy="10" mass="1"></JuicyBerry>
  <Stick maxClones="10" energy="0" mass="1"></Stick>
</GreenTree>
</SavedCellContent>

<SavedCellContent xpos="26" ypos="69" gridCellType="LandCell">
<Spring mass="9999" energy="0">
  <FreshWater maxClones="100" energy="0" mass="1"></FreshWater>
</Spring>
</SavedCellContent>

<SavedCellContent xpos="17" ypos="73" gridCellType="LandCell">
<RedTree mass="9999" energy="0">
  <PricklyFruit maxClones="5" energy="10" mass="1"></PricklyFruit>
  <Thorn maxClones="10" energy="0" mass="1"></Thorn>
  <Vine maxClones="10" energy="0" mass="1"></Vine>
</RedTree>
</SavedCellContent>

<SavedCellContent xpos="18" ypos="73" gridCellType="CoastalCell">
</SavedCellContent>

<SavedCellContent xpos="8" ypos="74" gridCellType="CoastalCell">
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
ooooooooooooooooooooooooooooooooooooooooooooOOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooOooOoooooooooooooooooooOOoOOoooooOooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooooOooooooooooOOOOOOOOoooOOOOOOOOOoOoOoooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooOOOOCCCCCCCCOOOOOOOOOOooooooOooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooooooooOOOCCCCLLLLLLLLCCCOoOOoOOoOOooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooooooOOCCCLLLLLLLLLCCCCCOCCoOoOooooooooOOooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooooooooooOCCLLLLLLLLLLLCOOOOOCOOcOoooOOOOoooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooooOCLLLLLLLLLLCCCOOCCCOOCocooOooOooOoOooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooooOOCLLLLLLLLCCCOOOOCLLCOOoCcOOOOOOoOOooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooooooOCCLLLLLLLLCOOOOOCCLLCCoooOooooOOoooOOOoOooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooooOCLLLLLLLLCCOooOCCLLLCOOoOOOooooOooooOoOooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooooOCLLLLLLLCCOOooOCLLLLCOOoooooOOooooooOOOOooOoooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooooooooooooOCLLLLLLLCOOooOOCLLLLLCooOOooOoOoOooOoOOoOOOooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooOOOOOOOCLLLLLLCCOooOOCCLLLLCCOoOOOoOOOOooOOOOooOOOOooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooooOOOCCCCCCCLLLLLLCOOOOOCCLLLLLLCOOOOoOOOOOoOOOOoOOooOoOooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooooOOCCCLLLLLLLLLLLLCOOCCCCLLLLLLLCOOOooOOOOOOOOoOoooooOoooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooOCCLLLLLLLLLLLLLLCCCCLLLLLLLLLLCOOOOOooOOOOOOOOOOOOOoooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOooooOoOOOOOOOOOOoOOOOooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOoOoooOoOOOOOOOOOOOoooOooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOooOOoOOOOOOOOOOOOooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLCOOOOOOooOOOOOOOOOOOooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLCOoOooOoOoOoOOOOOOOOoooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOOOoOOOOOoooOOOOOOoooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOCCOoOOOOOOOOOOOOOoooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLCOoCCOOOOOOOOOOoOOOooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLCOOCCOOOOOOOoooOoOOoOooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLCOOCCOOOOOOoOoOoOOoOoooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOCCOOOOOOOOooOoOoooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOCCOOOOOOoOoOooooOoooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOOCOOOOOOoooooooOOoooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLCOOOOOOCCCOooooooOooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLCOOCCCLLCooooooooOOooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLCCLLLLCOoOoOooooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOOoOoOoooOooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCOoOoOOooOoooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOOOOooOooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOCOOooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCLCCooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOoooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCOOooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCOOooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCOOOOooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCCCCCoooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCOoooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLLCCCCCCCLLLLLLLLLLLLLLLLLLLLLLLCCCCCOooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooOCLLLLLLLLLLLLLLLCCOOOOOOOCCCCLLLLLLLLLLLLLLLLLLLCOOOOoooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooCLLLLLLLLLLLLLLLCOOoooooooOOOOCCCLLLLLLLLLLLLLLCCOoooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooCLLLLLLLLLLLLLLCOoooooooooooooOOOCCLLLLLLLCCCCCOOooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooCLLLLLLLLLLLLLCOoooooooooooooooooOOCCCCCCCOOOOOooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooooOCLLLLLLLLLLLLCOooooooooooooooooooOoOOOOOOOoooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooOCLLLLLLLLLLLLLCoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooCLLLLLLLLLLLLLCOoooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooCLLLLLLLLLLLLCOooooooooooooooooooOOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooCLLLLLLLLLLLLCooooooooooooooooooooOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooooOCLLLLLLLLLLLCOooooooooooooooooooOoOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooOCLLLLLLLLLLLLCoooooooooooooooooooOOOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooCLLLLLLLLLLLLCOoooooooooooooooooooooOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooooOCLLLLLLLLLLLLCoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooOCLLLLLLLLLLLLLCoooooooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooCLLLLLLLLLLLLCCOoooooooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooooooooooooooooooOCLLLLLLLLLLLLCOooooooooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooOoooooooooooOCLLLLLLLLLLLLLCoooooooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooOCCooooooooooOCLLLLLLLLLLLLLCOoooooooooooooooooooooooOOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooCLCooooooooooCLLLLLLLLLLLLLCOooooooooooooooooooooooOOOOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooCLCOoooooooOOCLLLLLLLLLLCCCOoooooooooooooooooooooooOOOOOoooooooOoOoooooooooooooooooooooooooooooooooooooooooooooooo
ooooooCLCCooooooOCCLLLLLLLLLLLCOOooooooooooooooooooooooOooOoOOOOooooOooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooCLLLCoooooOCLLLLLLLLLLLLCCooooooooooooooooooooooooOOOOOOOOoooOooOOOoooooooooooooooooooooooooooooooooooooooooooooooo
oooooCLLLCoOoOOCLLLLLLLLLLLLCOOooooooooooooooooooooooooooooOoOOooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
oooooCLLLCOCOCCLLLLLLLLLLLLCOoooooooooooooooooooooooooooOOooOooooOoooOoOoooooooooooooooooooooooooooooooooooooooooooooooo
oooooCLLLCCLCLLLLLLLLLLLLCCOoooooooooooooooooooooOoooooooOOOooOOoooooOOooooooooooooooooooooooooooooooooooooooooooooooooo
oooooCLLLLLLLLLLLLLLLLLCCCOooooooooooooooooooooOoOooooooooOOOOooooooooooOOoooooooooooooooooooooooooooooooooooooooooooooo
oooooOCLLLLLLLLLLLLLCCCCOOoooooooooooooooooOooOooOooooooOoOOOOoooooooooOoooooooooooooooooooooooooooooooooooooooooooooooo
ooooooOCLLLLLLLLLLCCCOOOoooooooooooooooOOooOOOoOOooooooooooOOOoooooooooOOooooooooooooooooooooooooooooooooooooooooooooooo
oooooooOCCCCCCCCCCOOOooooooooooooooooooooOOooooOooooooooOOOOOooooooooooooOOooooooooooooooooooooooooooooooooooooooooooooo
ooooooooOOOOOOOOOOooooooooooooooooooooooOOOOoOoOoOoooooooooOOOOooooooooOoooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooOoooOOoooOooooooooOoooooooooooooooOooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooOOOoOoOOoooOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooooOOOoOoOoOOOoooooooooooooooooooooOoOOoooooooooooooooooooooooooooooooooooooooo
ooooooooooooooooooooooooooooooooooooooooooOOoOOoooOOoooOoooooooooooooooooooooOoooooooooooooooooooooooooooooooooooooooooo
</Attribute_String></SavedGridContents>

</SavedGrid>

</RestoreContainer>
