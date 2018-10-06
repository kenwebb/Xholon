<script svgSelector="#xhanim > svg"><![CDATA[
/**
 * Draw directed arcs in d3cp.
 * Ken Webb
 * April 18, 2018
 * 
 * drawing lines in d3:
 * https://www.dashingd3js.com/svg-paths-and-d3js
 * 
 * some possible values for svgSelector:
 *  #xhgraph > svg
 *  #xhsvg > div > svg
 *  #xhanim > svg
 */
//$wnd = window;
//$doc = document;
$wnd.console.log(this);
$wnd.console.log(this.cnode);
var lineFunction = $wnd.d3.svg.line()
 .x(function(d) { return d.x; })
 .y(function(d) { return d.y; })
 .interpolate("linear");
//var svg = $wnd.d3.select(this.svgSelector);
var svg = $doc.querySelector(this.svgSelector);
$wnd.console.log(svg);
// https://stackoverflow.com/questions/36579339/how-to-draw-line-with-arrow-using-d3-js
// TODO arrow is wierd
/*svg.append("svg:defs").append("svg:marker")
    .attr("id", "arrow")
    .attr("refX", 15)
    .attr("refY", -1.5)
    .attr("markerWidth", 6)
    .attr("markerHeight", 6)
    .attr("orient", "auto")
    .append("path")
    .attr("d", "M 0 -5 10 10")
    .style("stroke", "indigo");*/

// http://bl.ocks.org/tomgp/d59de83f771ca2b6f1d4
// this arrow is OK
$wnd.d3.select(svg).append("svg:defs").append("svg:marker")
		.attr({
			"id":"arrow",
			"viewBox":"0 -5 10 10",
			"refX":5,
			"refY":0,
			"markerWidth":4,
			"markerHeight":4,
			"orient":"auto"
		})
		.append("path")
			.attr("d", "M0,-5L10,0L0,5")
			.style("stroke", "blue")
			.attr("class","arrowHead");

//var svgContainer = $wnd.d3.select("#xhgraph > svg > g");
//$wnd.console.log(svgContainer);
var svgG = $doc.querySelector(this.svgSelector + " > g");
var svgContainer = $wnd.d3.select(svgG);
var svgNode = svgG.firstElementChild;
while (svgNode && svgNode.tagName == "g") {
  $wnd.console.log(svgNode.className);
  $wnd.console.log(svgNode.classList);
  if (svgNode.className.baseVal != "d3cpdummy") {
    $wnd.console.log(svgNode); // SVG
    var d3Data = svgNode["__data__"];
    $wnd.console.log(d3Data); // d3 data
    var str = svgNode.firstElementChild.textContent; // title
    $wnd.console.log(str);
    var xhNode = $wnd.xh.root().xpath("descendant-or-self::*[@name='" + str + "']");
    if (xhNode) {
      $wnd.console.log("Found Xholon node " + xhNode.name());
      // TODO get xhNode.links(); and draw line for each link
      var linksArr = xhNode.links(false, true);
      if (linksArr && linksArr.length > 0) {
        for (var i = 0; i < linksArr.length; i++) {
          var link = linksArr[i]; // link is a Xholon node
          $wnd.console.log(link);
          
          var reffedName = link.reffedNode.name().replace(":", "\\:"); // : has to be escaped
          $wnd.console.log(reffedName);
          var svgNodeRemote = svgG.querySelector("g#" + reffedName);
          if (svgNodeRemote) {
            $wnd.console.log("Found svgNodeRemote " + svgNodeRemote);
            var d3DataRemote = svgNodeRemote["__data__"];
            /*
            var lineData = [ { "x": d3Data.x,   "y": d3Data.y},  { "x": d3DataRemote.x,  "y": d3DataRemote.y}];
            // svgG.append("<path>M148.418526767233,493.9829414758341L168.418526767233,513.9829414758341</path>");
            svgContainer.append("path")
             .attr("d", lineFunction(lineData))
             .attr("stroke", "blue")
             .attr("stroke-width", 2)
             .attr("fill", "none")
             .attr("marker-end", "url(#triangle)");
            */
            svgContainer.append("line")
            .attr("x1", d3Data.x)
            .attr("y1", d3Data.y)
            .attr("x2", d3DataRemote.x)
            .attr("y2", d3DataRemote.y)          
            .attr("stroke-width", 1)
            .attr("stroke", "blue")
            .attr("marker-end", "url(#arrow)");
          }
        }
      }
    }
    else {
      $wnd.console.log("Unable to find Xholon node" + str);
    }
  }
  svgNode = svgNode.nextElementSibling;
}
//# sourceURL=drawDirectedArcs.js
]]></script>
