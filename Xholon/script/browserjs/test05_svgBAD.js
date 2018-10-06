<script implName="lang:BrowserJS:inline:"><![CDATA[
// this does NOT work; no SVG is displayed on the web page
// innerHTML does NOT work with SVG Web
var svg = document.getElementById("svg");
svg.innerHTML = "<script type='image/svg+xml'><svg width='100%' height='100%' version='1.1' xmlns='http://www.w3.org/2000/svg'><rect width='300' height='100' style='fill:rgb(0,0,255);stroke-width:1; stroke:rgb(0,0,0)'/></svg></script>"
nil;
]]></script>