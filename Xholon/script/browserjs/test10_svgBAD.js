<script implName="lang:BrowserJS:inline:"><![CDATA[
// Dynamically Creating an SVG OBJECT; see SVG Web detailed doc
// this does not work yet
var obj = document.createElementNS('object', true);
obj.setAttribute('type', 'image/svg+xml');
//obj.setAttribute('data', 'rectangles.svg');
obj.setAttribute('data', "data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg'><rect id='myRect' width='36.416' height='36.416' x='30' y='30' fill='red'/></svg>");
obj.setAttribute('width', '500');
obj.setAttribute('height', '500');
obj.addEventListener('load', function() {
  alert('loaded!');
}, false);
if (window.svgweb) {
	  window.svgweb.appendChild(obj, document.body);
	} else {
	  document.body.appendChild(obj);
	}
nil;
]]></script>
