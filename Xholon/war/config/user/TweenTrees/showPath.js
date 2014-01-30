<script implName="lang:javascript:inline:"><![CDATA[
var cnode = contextNodeKey;
println('  context node: ' + cnode);

var tween1 = cnode.getPort(0);
println('tween1: ' + tween1);

var tween2 = tween1.getPort(0);
println('tween2: ' + tween2);

var tween3 = tween2.getPort(0);
println('tween3: ' + tween3);

var tween4 = tween3.getPort(0);
println('tween4: ' + tween4);

]]></script>