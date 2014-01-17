<Termite implName="org.primordion.user.app.Bestiary.Beast">
<MovingTermitebehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[

var termite;

var beh = {

  postConfigure: function() {
    termite = this.cnode.parent();
  },

  act: function() {
    this.move();
  },

  move: function() {
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
      var destination = termite.parent();
      if (moveX != 0) {
        destination = destination.port(portX);
      }
      if (moveY != 0) {
        destination = destination.port(portY);
      }
      var artifact = destination.first();
      if (artifact) {
          switch (artifact.xhc().id()) {
          case 15: //CeBestiary.DoorCE:
            // can the beast move through the door?
            if (!this.canMoveThruDoor(artifact)) {
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
        termite.remove().appendto(destination);
        foundNewLocation = true;
      }
    }
  },

  canMoveThruDoor: function(door) {
    return false;
  }
}

]]></MovingTermitebehavior>

<EatWoodTermitebehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[

var termite;

var beh = {

  postConfigure: function() {
    termite = this.cnode.parent();
  },

  act: function() {
    this.eatWood();
  },

  eatWood: function() {
    var cell = termite.parent().next();
    if (cell) {
      var node = cell.first();
      if ((node) && (node.xhc().name() == "WallSection")) {
        node.remove();
        return;
      }
    }
    else {
      cell = termite.parent().prev();
      if (cell) {
        var node = cell.first();
        if ((node) && (node.xhc().name() == "WallSection")) {
          node.remove();
          return;
        }
      }
    }
  }

}

]]></EatWoodTermitebehavior>
</Termite>
