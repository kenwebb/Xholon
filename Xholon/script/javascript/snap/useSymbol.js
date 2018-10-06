<script><![CDATA[
/*
<symbol id="shape2">
  <circle cx="25" cy="25" r="25" />
</symbol>
<use xlink:href="#shape2" x="50" y="25" />
*/
(function(selection, symbolId, shape, attrs) {
  
  // create a symbol object
  var paper = $wnd.Snap(selection);
  var symbol = paper.el("symbol");
  symbol.node.id = symbolId;
  
  // create some content for the symbol
  var content = paper.el(shape).attr(attrs);
  symbol.append(content);
  
  // use the symbol multiple times
  paper.append(symbol.use().attr({x: 50, y: 15, fill: "red"}));
  paper.append(symbol.use().attr({x: 70, y: 20, fill: "green"}));
  paper.append(symbol.use().attr({x: 90, y: 5, fill: "blue"}));
  
})(
"#xhsvg svg", "symbol1", "circle", {cx: 10, cy: 10, r: 10}
);
]]></script>
