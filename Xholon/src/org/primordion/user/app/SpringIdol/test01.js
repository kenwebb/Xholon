<?xml version="1.0" encoding="UTF-8"?>
<Script implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
var beh = new Object();

beh.postConfigure = function() {
  console.log("postConfigure 1");
  var kenny = beh.cnode.getParentNode();
  kenny.println(kenny.getPort("instrument").getName());
  console.log(beh.cnode);
  beh.cnode.removeChild();
}
]]></Script>
