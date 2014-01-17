<Zombie roleName="Bob" implName="org.primordion.user.app.Bestiary.Beast">
  <MovingZombiebehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
    var beh = {}
    beh.direction = Math.floor(Math.random() * 8);

    beh.postConfigure = function() {
      beh.zombie = this.cnode.getParentNode();
      beh.destination = this.zombie.getParentNode();
    }

    beh.act = function() {
      this.move();
    }

    beh.move = function() {
      this.destination = this.destination.getPort(this.direction);
      if (this.destination) {
        this.zombie.removeChild();
        this.zombie.appendChild(this.destination);
      }
    }

    beh.toString = function() {
      return "direction:" + this.direction;
    }

    beh.testing = function(a, b, c) {
      console.log(a);
      console.log(b);
      console.log(c);
    }
  ]]></MovingZombiebehavior>
</Zombie>
