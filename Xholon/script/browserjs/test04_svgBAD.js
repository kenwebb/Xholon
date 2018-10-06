<script implName="lang:BrowserJS:inline:"><![CDATA[
// innerHTML does NOT work with SVG Web
//var svg = document.getElementById("svgSvg");
//svg.innerHTML = "<g id='myGroup'><rect id='myRect' width='36.416' height='36.416' x='30' y='30' fill='red'/><text id='myText' x='80' y='80' font-family='Verdana' font-size='24' fill='blue'>hello world</text></g>";
$('svg#svgSvg').html("<g id='myGroup'><rect id='myRect' width='36.416' height='36.416' x='30' y='30' fill='red'/><text id='myText' x='80' y='80' font-family='Verdana' font-size='24' fill='blue'>hello world</text></g>");
alert(svg);
nil;
]]></script>