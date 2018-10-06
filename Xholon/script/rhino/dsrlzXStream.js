<script implName="lang:javascript:inline:"><![CDATA[
// Deserialize to the context node from a file using XStream.
var cnode = contextNodeKey;
println('Deserializing: ' + cnode);
var s = cnode.getService("JavaSerializationService-XStream");
if (s != null) {
  var sig = org.primordion.xholon.service.javaserialization.IJavaSerialization.SIG_DESERIALIZE_REQ;
  //var fileName = "test.xml";
  var fileName = "/home/ken/workspace/Xholon/ef/serialize/xstream/ReactionNetworkSystem_0_1357834352513.xml";
  msg = s.sendSyncMessage(sig, fileName, cnode);
  if (msg != null) {
    newTest = msg.getData();
    newTest.appendChild(cnode);
    newTest.postConfigure();
  }
}
]]></script>
