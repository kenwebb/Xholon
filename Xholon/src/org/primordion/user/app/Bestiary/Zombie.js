<Zombie roleName="Bob" implName="org.primordion.user.app.Bestiary.Beast">
  <MovingZombiebehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
  
  var direction, zombie;
  
  var beh = {

    postConfigure: function() {
      direction = Math.floor(Math.random() * 8);
      zombie = this.cnode.parent();
    },

    act: function() {
      this.move();
    },

    move: function() {
      var destination = zombie.parent().port(direction);
      zombie.remove().appendTo(destination);
    },

    toString: function() {
      return "direction:" + direction;
    },

    testing: function(a, b, c) {
      console.log(a);
      console.log(b);
      console.log(c);
    }
  }
  ]]></MovingZombiebehavior>
</Zombie>
