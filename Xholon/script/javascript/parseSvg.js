// Parse an SVG image, and extract x,y postions.
//<script><![CDATA[
// Parse an SVG image, and extract x,y postions.
// This can be used inside GWT Xholon, or in Firebug or Developer Tools.
// I'm testing this with my BioSystems cell model, on a d3cp image.
// It works on d3cp, d3 Bubble, partly on some other d3.
// It doesn't work (yet) on Graphviz or MindMap.
(function (selection, xpathexpr) {
  if (typeof $wnd == "undefined") {$wnd = window;}
  if (typeof $doc == "undefined") {$doc = document;}
  if ((selection === undefined) || (selection === null)) {
    selection = "#xhgraph";
  }
  if ((xpathexpr === undefined) || (xpathexpr === null)) {
    xpathexpr = ".";
  }
  
  var makeXholonArray = function(xhnode, arr) {
    arr.push(xhnode);
    xhnode = xhnode.first();
    while (xhnode) {
      makeXholonArray(xhnode, arr);
      xhnode = xhnode.next();
    }
  }
  
  var svg = null;
  try {
    svg = $doc.querySelector(selection + ">svg");
  } catch(e) {
    $wnd.console.log(e); // SYNTAX_ERR
    return;
  }
  if (!svg) {return;}
  var g = svg.querySelector("g>g"); // this works for d3 CirclePack
  if (!g) {
    g = svg.querySelector("g"); // this works for d3 Bubble
  }
  
  var root = $wnd.xh.root();
  //root.println(root.name() + " " + selection);
  
  var xhnode = root.xpath(xpathexpr); // use xpath to find root of subtree that corresponds to SVG subtree
  //$wnd.console.log(xhnode.name());
  var xharr = [];
  makeXholonArray(xhnode, xharr);
  //for (var i = 0; i < xharr.length; i++) {
  //  $wnd.console.log(xharr[i].name());  
  //}
  
  var svgnode = g;
  //$wnd.console.log(svgnode.id);
  
  var i = 0;
  while (svgnode && (i < xharr.length)) {
    // ignore elements with class = "d3cpdummy"
    if (!svgnode.classList.contains("d3cpdummy")) {
      var matrix = svgnode.transform.baseVal.getItem(0).matrix;
      var radius = 0;
      var circle = svgnode.querySelector("circle");
      if (circle) {
        radius = circle.r.baseVal.value;
      }
      var x = matrix.e - radius;
      var y = matrix.f - radius;
      var xh = xharr[i];
      var svgnodeId = svgnode.id;
      if (!svgnodeId) {
        var titlenode = svgnode.querySelector("title");
        if (titlenode) {
          svgnodeId = titlenode.textContent; // this works for d3 Bubble
        }
      }
      if (!svgnodeId) {
        var textnode = svgnode.querySelector("text");
        if (textnode) {
          svgnodeId = textnode.textContent; // this should work for d3 Cluster
        }
      }
      $wnd.console.log(svgnodeId + " " + xh.name() + " " + x + "," + y);
      if (xh.name() != svgnodeId) {
        // skip xharr[i] that don't match the current svgnode; Xholon nodes that weren't written to SVG
        i++;
        continue;
      }
      // add posx posy posw posh to the Xholon xh node
      xh._jsdata = {}
      xh._jsdata.posx = x;
      xh._jsdata.posy = y;
      xh._jsdata.posw = radius * 2; // width
      xh._jsdata.posh = radius * 2; // height
      $wnd.console.log(xh);
      i++;
    }
    svgnode = svgnode.nextElementSibling;
  }
}("div#xhgraph", "."))
//]]></script>
