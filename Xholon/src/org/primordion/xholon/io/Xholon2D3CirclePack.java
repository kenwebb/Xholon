package org.primordion.xholon.io;

import com.google.gwt.core.client.JavaScriptObject;

//import org.primordion.xholon.io.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model using D3 circle packing.
 * TODO after mouse hovers for about 0.5s, display toString() as the tooltip ?
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2D3CirclePack {
	
	public Xholon2D3CirclePack() {}
	
	/**
	 * Create a D3 Pack Layout from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection, IXholonGui gui) /*-{
	  var w = width,
    h = height,
    format = $wnd.d3.format(",d");
    
    var pack = $wnd.d3.layout.pack()
      .size([w - 4, h - 4])
      .value(function(d) {
        return d.size;
      });
    
    var svg = $wnd.d3.select(selection).append("svg:svg")
      .attr("width", w)
      .attr("height", h)
      .append("svg:g")
      .attr("transform", "translate(2, 2)");
    
    var node = svg
      .data([json])
      .selectAll("g.node")
      .data(pack.nodes)
      .enter()
      .append("svg:g")
      .attr("class", function(d) {
        return d.children ? "d3cpnode" : "d3cpleaf d3cpnode";
      })
      .attr("transform", function(d) {
        return "translate(" + d.x + "," + d.y + ")";
      });
    
    node.append("svg:title")
      .text(function(d) {
        return d.name;
      });
    
    node.append("svg:circle")
      .attr("r", function(d) {
        return d.r;
      })
      .style("fill", function(d) {
        return d.color;
      })
      .style("fill-opacity", function(d) {
        return d.opacity;
      });
    
    node.filter(function(d) {
      return !(d.children || d.dummy);
    }).append("svg:text")
      .attr("dy", ".3em")
      .style("text-anchor", "middle")
      .style("font-size", function(d) {
        return (d.r * 1.75) + "px";
      })
      .text(function(d) {
        //console.log("d.name: " + d.name);
        var dname = d.name.substring(0, 1);
        if ((dname == ":") && (d.name.length > 1)) {
          dname = d.name.substring(1, 2);
        }
        return dname;
      });
    
    node.on("click", function(d, i) {
      handleClick(d, i);
    });
    
    node.on("contextmenu", function(d, i) {
      handleContextmenu(d, i);
    });
    
    node.on("dblclick", function(d, i) {
      handleDblclick(d, i);
    });
    
    node.on("mouseover", function(d, i) {
      handleMouseOverOut(d, i);
    });
    
    node.on("mouseout", function(d, i) {
      handleMouseOverOut(d, i);
    });
    
    // does this work ?   NO ?
    //node.on("drag", function(d, i) {
    //  console.log("drag");
    //  handleDrag(d, i);
    //});
    
    // does this work ?   yes
    //svg.call($wnd.d3.behavior.drag().on("dragstart", handleDragStart));
    //svg.call($wnd.d3.behavior.drag().on("drag", handleDrag));
    //svg.call($wnd.d3.behavior.drag().on("dragend", handleDragEnd)); // drop
    
    function handleClick(d, i) {
      if (d.dummy) {d = d.parent;}
      $wnd.d3.event.preventDefault();
	    $wnd.d3.event.stopPropagation();
      var isCtrlPressed = $wnd.d3.event.ctrlKey;
      gui.@org.primordion.xholon.io.IXholonGui::handleNodeSelection(Ljava/lang/String;Ljava/lang/Object;Z)(d.name, d, isCtrlPressed);
    }
    
    // public abstract void makeContextMenu(Object guiItem, int posX, int posY);
    function handleContextmenu(d, i) {
      if ($wnd.d3.event.shiftKey) {return;} // allow browser default contextMenu
      if (d.dummy) {d = d.parent;}
      $wnd.d3.event.preventDefault();
	    $wnd.d3.event.stopPropagation();
      var posX = 0;
      var posY = $wnd.d3.event.pageY;
      gui.@org.primordion.xholon.io.IXholonGui::makeContextMenu(Ljava/lang/Object;II)(d, posX, posY);
    }
    
    function handleDblclick(d, i) {
      // TODO detect single vs double click
      var node = getXholonNode(d);
      if (node) {
        $wnd.alert("Double click for " + node.toString());
      }
    };
    
    function handleMouseOverOut(d, i) {
      var node = getXholonNode(d);
      if (node) {
        togglePorts(node);
      }
    };
    
    // nothing happens
    //function handleDragStart(d, i) {
    //  $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
    //  console.log("Xholon2D3CirclePack dragStart 1");
    //  console.log(d);
    //  console.log($wnd.d3.event);
    //}
    // nothing happens
    //function handleDrag(d, i) {
    //  $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
    //  console.log("Xholon2D3CirclePack drag 1");
    //  console.log(d);
    //  console.log($wnd.d3.event);
    //}
    //function handleDragEnd(d, i) {
    //  $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
	  //  console.log("Xholon2D3CirclePack dragEnd 1");
	  //  console.log($wnd.d3.event); // not useful
	  //  console.log($wnd.d3.event.sourceEvent); // mouseup native event
    //  console.log($wnd.d3.select(this)); // d3-wrapped SVG node
    //  console.log(d); // d3 object with children
    //  console.log(i);
    //  if (d.name == "Application") {
        // TODO
        // public void handleDrop(String nodeName, Object data) {
        // there's no easy way to get the dropped data; nodeName = d.name
    //  }
    //}
    
    function getXholonNode(d) {
      if (d.dummy) {d = d.parent;}
      var node = null;
      var app = $wnd.xh.app();
      var root = $wnd.xh.root();
      if (isInHierarchy(d, "InheritanceHierarchy")) {
        node = root.@org.primordion.xholon.base.IXholon::getClassNode(Ljava/lang/String;)(d.name);
      }
      else if (isInHierarchy(d, "MechanismHierarchy")) {
        var mechRoot = app.@org.primordion.xholon.app.Application::getMechRoot()();
        if (mechRoot) {
          node = $wnd.xh.xpath("descendant-or-self::*[@name='" + d.name + "']", mechRoot);
        }
      }
      else if (isInHierarchy(d, "CompositeStructureHierarchy")) {
        var cshRoot = app.@org.primordion.xholon.app.Application::getRoot()().parent(); //getParentNode();
        if (cshRoot) {
          node = $wnd.xh.xpath("descendant-or-self::*[@name='" + d.name + "']", cshRoot);
        }
      }
      else {
        // this must be a top-level control node
        node = $wnd.xh.xpath("descendant-or-self::*[@name='" + d.name + "']", $wnd.xh.app());
      }
      return node;
    }
    
    // Toggle the display of ports.
    // @param node A IXholon node. from anywhere in the overall tree.
    function togglePorts(node) {
      var xhc = node.@org.primordion.xholon.base.IXholon::getXhc()();
      if (xhc) {
        // this is an ActiveObject node in the CSH
        var ports = node.ports();
        if (ports && ports.length) {
          var titles = svg.selectAll("g title");
          for (var i = 0; i < ports.length; i++) {
            var portInfo = ports[i].obj(); //getVal_Object();
            var reffedNode = portInfo.reffedNode;
            if (reffedNode) {
              var title = titles.filter(function(d, i) {
                return d.name == reffedNode.name(); //getName();
              });
              if (title) {
                //var circleNode = svgTitleEle.node().nextElementSibling;
                var parent = $wnd.d3.select(title.node().parentNode);
                var circleNode = parent.select("circle");
                if (circleNode) {
                  
                  // fill
                  var styleFill = circleNode.style("fill");
                  if (styleFill) { // != ""
                    var styleFillHex = $wnd.d3.rgb(styleFill).toString();
                    if (styleFillHex == "#ffff00") { // "yellow"
                      circleNode.style("fill", null);
                    }
                    else {
                      circleNode.style("fill", invertColor(styleFillHex));
                    }
                  }
                  else {
                    circleNode.style("fill", "yellow");
                  }
                  
                  // fill-opacity
                  var styleOpac = circleNode.style("fill-opacity"); // --> "0.1" or "0.98765" or ""
                  if (styleOpac) { // != ""
                    if (styleOpac == 0.98765) {
                      circleNode.style("fill-opacity", null);
                    }
                    else {
                      circleNode.style("fill-opacity", 1 - styleOpac);
                    }
                  }
                  else {
                    circleNode.style("fill-opacity", 0.98765);
                  }
                }
              }
            }
          }
        }
      }
    }
    
    // invertColor
    // http://stackoverflow.com/questions/9600295/automatically-change-text-color-to-assure-readability
    function invertColor(hexTripletColor) {
      var color = hexTripletColor;
      color = color.substring(1);           // remove #
      color = parseInt(color, 16);          // convert to integer
      color = 0xFFFFFF ^ color;             // invert three bytes
      color = color.toString(16);           // convert to hex
      color = ("000000" + color).slice(-6); // pad with leading zeros
      color = "#" + color;                  // prepend #
      return color;
    }
    
    function isInHierarchy(d, hierName) {
      while (d != null) {
        if (d.name == hierName) {
          return true;
        }
        d = d.parent;
      }
      return false;
    }
    
	}-*/;
	
}
