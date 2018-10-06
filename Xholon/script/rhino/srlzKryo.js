<script implName="lang:javascript:inline:"><![CDATA[
// Serialize the context node and its subtree to a file using Kryo.
var cnode = contextNodeKey;
println('Serializing: ' + cnode);
var s = cnode.getService("JavaSerializationService-Kryo");
if (s != null) {
  var sig = org.primordion.xholon.service.javaserialization.IJavaSerialization.SIG_SERIALIZE_REQ;
  s.sendSyncMessage(sig, "test.bin", cnode);
}
]]></script>
