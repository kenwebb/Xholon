<script implName="lang:BrowserJS:inline:"><![CDATA[
// this works; creates a circle at bottom of page
//create SVG root element
var svg = document.createElementNS(svgns, 'svg'); // don't need to pass in 'true'
//svg.setId('svgSvg');
svg.setAttribute('width', '350');
svg.setAttribute('height', '150');
//svg.setAttribute('border', '1px solid black');
alert(svg);
// create an example circle and append it to SVG root element
var circle = document.createElementNS(svgns, 'circle');
circle.setAttribute('cx', 100);
circle.setAttribute('cy', 100);
circle.setAttribute('r', 20);
circle.setAttribute('fill', 'blue');
circle.setAttribute('stroke-width', '1px');
circle.setAttribute('stroke', 'black');
alert(circle);
svg.appendChild(circle);
// Must use a callback to know when SVG is appended to page (this is slight
// divergence from standard). The following are supported ways to do this:
svg.addEventListener('SVGLoad', function() {
  svg = this; // this will correctly refer to your SVG root
  alert('loaded!');
}, false);
// now append the SVG root to our document
var svgScript = document.getElementById('svgScript');
alert(svgScript);
svgScript.appendChild(svg);
//svgweb.appendChild(svg, document.body); // works
//svgweb.appendChild(svg, svgScript); // does NOT work
nil;
]]></script>
