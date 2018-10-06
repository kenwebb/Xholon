<script implName="lang:javascript:inline:"><![CDATA[
// Deserialize to the context node from a file using Kryo.
var cnode = contextNodeKey;
println('Deserializing: ' + cnode);
var s = cnode.getService("JavaSerializationService-Kryo");
if (s != null) {
  var sig = org.primordion.xholon.service.javaserialization.IJavaSerialization.SIG_DESERIALIZE_REQ;
  msg = s.sendSyncMessage(sig, "test.bin", cnode);
  if (msg != null) {
    newTest = msg.getData();
    newTest.appendChild(cnode);
    newTest.postConfigure();
  }
}
]]></script>
