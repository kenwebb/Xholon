<Termite implName="org.primordion.user.app.Bestiary.Beast">
<MovingTermitebehavior implName="org.primordion.xholon.xholon.base.Behavior_gwtjs"><![CDATA[

var beh = new Object();

beh.postConfigure = function() {
  beh.termite = beh.cnode.getParentNode();
}

beh.act = function() {
  move();
}

function move() {
  var foundNewLocation = false;
  var count = 0;
  while ((!foundNewLocation) && (count < 10)) {
    var moveX = Math.floor(Math.random() * 3) - 1;
    var moveY = Math.floor(Math.random() * 3) - 1;
    if ((moveX == 0) && (moveY == 0)) {
      return;
    }
    var portX = -1;
    var portY = -1;
    if (moveX > 0) {
      portX = 1; //IGrid.P_EAST
    }
    else {
      portX = 3; //IGrid.P_WEST
    }
    if (moveY > 0) {
      portY = 0; //IGrid.P_NORTH
    }
    else {
      portY = 2; //IGrid.P_SOUTH
    }
    count++;
    var destination = beh.termite.getParentNode();
    if (moveX != 0) {
      destination = destination.getPort(portX);
    }
    if (moveY != 0) {
      destination = destination.getPort(portY);
    }
    var artifact = destination.getFirstChild();
    if (artifact) {
        switch (artifact.getXhc().getId()) {
        case 15: //CeBestiary.DoorCE:
          // can the beast move through the door?
          if (!canMoveThruDoor(artifact)) {
            destination = null;
          }
          break;
        case 16: //CeBestiary.WallSectionCE:
          destination = null;
        default:
          break;
        }
    }
    if (destination) {
      beh.termite.removeChild();
      beh.termite.appendChild(destination);
      foundNewLocation = true;
    }
  }
}

function canMoveThruDoor(door) {
  return false;
}

]]></MovingTermitebehavior>

<EatWoodTermitebehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
var beh = new Object();

beh.postConfigure = function() {
  beh.termite = beh.cnode.getParentNode();
}

beh.act = function() {
  eatWood();
}

function eatWood() {
  var cell = beh.termite.getParentNode().getNextSibling();
  if (cell) {
    var node = cell.getFirstChild();
    if ((node) && (node.getXhc().getName() == "WallSection")) {
      node.removeChild();
      return;
    }
  }
  else {
    cell = beh.termite.getParentNode().getPreviousSibling();
    if (cell) {
      var node = cell.getFirstChild();
      if ((node) && (node.getXhc().getName() == "WallSection")) {
        node.removeChild();
        return;
      }
    }
  }
}

]]></EatWoodTermitebehavior>
</Termite>
