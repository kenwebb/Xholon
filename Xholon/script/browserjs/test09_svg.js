<script implName="lang:BrowserJS:inline:"><![CDATA[
// create a circle; this works OK
// but I have to refresh the page by viewing the new elemets using Firebug if I force the use of Flash
var el = document.createElementNS(svgns, 'circle');
el.setAttribute('cx', 100);
el.setAttribute('cy', 100);
el.setAttribute('r', 20);
el.setAttribute('fill', 'yellow');
el.setAttribute('stroke-width', '1px');
el.setAttribute('stroke', 'black');
var svgSvg = document.getElementById("svgSvg");
svgSvg.setAttribute('width', 350);
svgSvg.setAttribute('height', 150);
svgSvg.appendChild(el);
nil;
]]></script>
