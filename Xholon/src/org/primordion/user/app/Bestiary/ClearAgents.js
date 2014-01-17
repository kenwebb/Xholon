<script><![CDATA[
/*
Clear Agents
Clear all agents from the simulation.
An agent is any child of a HabitatCell, for example:
  - a Cat, Human or other active object
  - a House or other more passive object
This JavaScript script does two things:
  1. dequeue all objects from the Beasts Q
  2. remove all objects in the tree that are children or descendants of a HabitatCell node
You can run this script on the Bestiary node, or any node in the tree that's a descendant of Bestiary.
*/
function clearBeastQ(qNode) {
  qNode.action('empty');
}
function clearAgents(node) {
  myXhcName = node.xhc().name();
  if (myXhcName == 'Beasts') {
    clearBeastQ(node);
  }
  else if (myXhcName == 'HabitatCell') {
    // remove all children of the HabitatCell
    myChild = node.first();
    while (myChild) {
      myChildTemp = myChild.next();
      myChild.remove();
      myChild = myChildTemp; // get sibling of removed node, if any
    }
  }
  node = node.first();
  while (node) {
    clearAgents(node);
    node = node.next();
  }
}
var contextNode = $wnd.xh.root();
clearAgents(contextNode);
]]></script>
