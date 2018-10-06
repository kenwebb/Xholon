<Wanderer prob="0.20,0.24,0.26,0.30" roleName="Rover" implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
/**
 * Probabilities of: (the 4 probabilities must add up to 1.0)
 * [0] moving to parent (can never move higher than the wanderer's root)
 * [1] moving to first child
 * [2] moving to next sibling
 * [3] moving to previous sibling
 * TODO also prob of staying in same place
 */
var me, contextNode, root, prob, beh = {
  postConfigure: function() {
    me = this.cnode; // the Behavior_gwtjs.java instance
    contextNode = me.parent();
    root = contextNode;
    $wnd.console.log(me);
    $wnd.console.log(root);
    prob = [];
    var pa = me.prob.split(",");
    $wnd.console.log(pa);
    var sum = 0.0;
    for (var i = 0; i < pa.length; i++) {
      //var num = Number(pa[i]);
      sum += Number(pa[i]);
      prob.push(sum);
    }
    $wnd.console.log(prob);
  },
  
  act: function() {
    var r = Math.random();
    //$wnd.console.log(r);
    if (r < prob[0]) {
      $wnd.console.log("0 ");
      if (contextNode != root) {
        contextNode.parent().append(me.remove());
        contextNode = me.parent();
      }
    }
    else if (r < prob[1]) {
      $wnd.console.log("1 ");
      if (contextNode.first() && (contextNode.first() != me)) {
        contextNode.first().append(me.remove());
        contextNode = me.parent();
      }
    }
    else if (r < prob[2]) {
      $wnd.console.log("2 ");
      if (contextNode.next()) {
        contextNode.next().append(me.remove());
        contextNode = me.parent();
      }
    }
    else {
      $wnd.console.log("3");
      //if (me.parent().prev()) {
      //  contextNode.prev().append(me.remove());
      //  contextNode = me.parent();
      //}
    }
  }
}
]]></Wanderer>
