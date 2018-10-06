<script implName="lang:BrowserJS:inline:"><![CDATA[
// this works; creates a circle at bottom of page
//create SVG root element
var svg = document.createElementNS(svgns, 'svg'); // don't need to pass in 'true'
svg.setAttribute('width', '300');
svg.setAttribute('height', '300');
// create an example circle and append it to SVG root element
var circle = document.createElementNS(svgns, 'circle');
circle.setAttribute('cx', 100);
circle.setAttribute('cy', 100);
circle.setAttribute('r', 20);
circle.setAttribute('fill', 'blue');
circle.setAttribute('stroke-width', '1px');
circle.setAttribute('stroke', 'black');
svg.appendChild(circle);
// Must use a callback to know when SVG is appended to page (this is slight
// divergence from standard). The following are supported ways to do this:
svg.addEventListener('SVGLoad', function() {
  svg = this; // this will correctly refer to your SVG root
  alert('loaded!');
}, false);
// also supported:
//svg.onsvgload = function() {
//  alert('loaded!');
//}
// now append the SVG root to our document
svgweb.appendChild(svg, document.body); // note that we call svgweb.appendChild
nil;
]]></script>
