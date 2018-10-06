<SubtreesReporter implName="org.primordion.xholon.base.Behavior_gwtjs" outTarget="outtab"><![CDATA[
// Report on a node's subtrees
// outTarget: "outtab" "consolelog"
var me, node, outstr, beh = {

postConfigure: function() {
  me = this.cnode;
  node = me.parent();
  outstr = "";
  var nodest = node["subtrees"];
  if (nodest) {
    // nodest should be an Object, but might be some other type such as "string" "undefined" or an array
    if (typeof nodest === "object") {
      if (Array.isArray(nodest)) {
        outstr += node.name() + " does not have subtrees, but it does have an Array subtrees property " + "\n";
      }
      else {
        outstr += node.name() + " has subtrees" + "\n";
        for (var subtreeName in nodest) {
          this.report(nodest[subtreeName]);
        }
      }
    }
    else {
      outstr += node.name() + " does not have subtrees, but it does have a subtrees property of type " + typeof nodest + "\n";
    }
  }
  else {
    outstr += node.name() + " does not have subtrees" + "\n";
  }
  switch (me.outTarget) {
  case "outtab":
    me.print(outstr);
    break;
  case "consolelog":
  default:
    $wnd.console.log(outstr);
    break;
  }
  me.remove();
},

report: function(stRoot) {
  if (!stRoot) {return;}
  var node = stRoot.first();
  outstr += "  " + stRoot.xhc().name() + " " + (node ? true : false) + "\n";
  while (node) {
    outstr += "    " + node.name() + "\n";
    node = node.next();
  }
}

}
//# sourceURL=SubtreesReporter.js
]]></SubtreesReporter>
