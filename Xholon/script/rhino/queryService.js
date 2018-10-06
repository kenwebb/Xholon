<script implName="lang:javascript:inline:"><![CDATA[
var cnode = contextNodeKey;
var app = applicationKey;
var snodes = selectedNodesKey;
println('  context node: ' + cnode);

var s = cnode.getService("XmlDatabaseService-BaseX");
println('service ' + s.getClass());
s.sendMessage(999, null, cnode);

var s = cnode.getService("JcrService-Jackrabbit");
println('service ' + s.getClass());

var s = cnode.getService("JavaSerializationService-Jse");
println('service ' + s.getClass());

var s = cnode.getService("AboutService");
println('service ' + s.getClass());

//var s = cnode.getService("ServiceLocatorService");
//println('service ' + s.doAction(s.getActionList()[0]));


]]></script>
