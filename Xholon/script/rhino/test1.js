/*
 * This is a Xholon script using JavaScript.
 */

<script implName="lang:javascript:inline:">

// print some stuff
print('Hello, world! from JavaScriptThing\n')
println(2+5)
println(new java.util.Date())

// get array of currently selected nodes
var selectedNodes = selectedNodesKey;

// get an array with unique entries
var mySet = new java.util.HashSet();
for(var i in selectedNodes) {
  mySet.add(selectedNodes[i].getXhcName());
}
selectedNodes = mySet.toArray();

// sort without a comparator function
//selectedNodes.sort();

// sort the currently selected nodes
selectedNodes.sort(function(n,m) {
  var s = n.getXhcName();
  var t = m.getXhcName();
  if (s &lt; t) return -1;
  else if (s > t) return 1;
  else return 0;
});

// print all currently selected nodes
for(var i in selectedNodes) {
  println(selectedNodes[i]); // will invoke Java/Xholon toString()
  print(selectedNodes[i].getXhcName()); // will invoke Xholon method
  print(" ");
  println(selectedNodes[i].getXhcId()); // will invoke Xholon method
}

// print the application
var app = applicationKey;
println(app);

// get a unique array of node names, sorted by type
/*
var selectedNodes = selectedNodesKey;
var mySet = new java.util.HashSet();
for(var i in selectedNodes) {
  mySet.add(selectedNodes[i].getXhcName());
}
selectedNodes = mySet.toArray();
selectedNodes.sort();
for(var i in selectedNodes) {
  println(selectedNodes[i]);
}
*/

// create a new IXholon node and return it to the Java app
//new org.primordion.user.app.Chameleon.XhChameleon

</script>