<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Show all properties of the context node that are theoretically settable.
 * In practice, the values of only some of these properties should be set.
 */
var cnode = contextNodeKey;
println("Properties of " + cnode);
for (var prop in cnode) {
  if (prop.substr(0,3) == 'set') {
    var pname = prop.substr(3);
    var pname = pname.charAt(0).toLowerCase() + pname.substr(1);
    var pval = cnode[pname];
    if (pval !== undefined) {
      println(pname + ": " + pval);
    }
  }
}
]]></script>