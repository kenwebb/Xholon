<ScareCatToDeathDogbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[

var dog;

var beh = {

  postConfigure: function() {
    dog = this.cnode.parent();
  },

  act: function() {
    this.scareCatToDeath();
  },

  scareCatToDeath: function() {
    var node = dog.next();
    while (node) {
      if ((node != dog)
          && (node.xhc().name() == "Cat")) {
        // make it "cat"atonic by removing all behaviors
        this.removeCatBehaviors(node);
        return;
      }
      node = node.next();
    }
  },

  removeCatBehaviors: function(cat) {
    var b = cat.first();
    while (b) {
      var temp = b;
      b = b.next();
      temp.remove();
    }
  }
}

]]></ScareCatToDeathDogbehavior>
