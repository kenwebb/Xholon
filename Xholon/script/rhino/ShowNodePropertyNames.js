<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Show all properties of the context node that are theoretically settable.
 * In practice, the values of only some of these properties should be set.
 */
var cnode = contextNodeKey;
println(cnode);
for (var prop in cnode) {
  if (prop.substr(0,3) == 'set') {
    println(prop);
  }
}
]]></script>
