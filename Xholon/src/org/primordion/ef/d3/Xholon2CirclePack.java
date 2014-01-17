package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.io.XholonGuiD3CirclePack;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model using D3 circle packing.
 * TODO after mouse hovers for about 0.5s, display toString() as the tooltip ?
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2CirclePack extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2CirclePack() {}
	
	/**
	 * Create a D3 Pack Layout from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected void createD3(JavaScriptObject json, int width, int height, Object selection) {
	  
	  IXholonGui xholonGui = new XholonGuiD3CirclePack("xhgraph", this.modelName, this.root.getApp());
	  //xholonGui.setXhdivId("xhgraph");
    xholonGui.showTree(root);
	  
	  /*var w = width,
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
      return !d.children;
    }).append("svg:text")
      .attr("dy", ".3em")
      .style("text-anchor", "middle")
      .style("font-size", function(d) {
        return d.r * 1.75;
      })
      .text(function(d) {
        return d.name.substring(0, 1);
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
    
    function handleClick(d, i) {
      var node = getXholonNode(d);
      if (node) {
        node.println(node.toString());
      }
      else {
        $wnd.xh.root().println("can't find " + d.name);
      }
    };
    
    function handleContextmenu(d, i) {
      var node = getXholonNode(d);
      if (node) {
        // prevent the browser from opening its default context menu
	      $wnd.d3.event.preventDefault();
	      $wnd.d3.event.stopPropagation();
	      
        $wnd.alert("Context menu for " + node.toString());
      }
    };
    
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
    
    function getXholonNode(d) {
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
        var cshRoot = app.@org.primordion.xholon.app.Application::getRoot()().getParentNode();
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
      //var isao = node.@org.primordion.xholon.base.IXholon::isActiveObject()();
      var xhc = node.@org.primordion.xholon.base.IXholon::getXhc()();
      //if (isao && xhc) {
      if (xhc) {
        // this is an ActiveObject node in the CSH
        var ports = node.ports();
        if (ports && ports.length) {
          var titles = svg.selectAll("g title");
          for (var i = 0; i < ports.length; i++) {
            //var portInfo = ports[i];
            //var reffedNode = portInfo.@org.primordion.xholon.base.PortInformation::getReffedNode()();
            var portInfo = ports[i].getVal_Object();
            var reffedNode = portInfo.reffedNode;
            if (reffedNode) {
              var svgTitleEle = titles.filter(function(d, i) {
                return d.name == reffedNode.getName();
              });
              if (svgTitleEle && svgTitleEle.node()) {
                var circleNode = svgTitleEle.node().nextElementSibling;
                if (circleNode) {
                  if (circleNode.style.fill) {
                    if (circleNode.style.fill == "#ffff00") { // "yellow"
                      circleNode.style.fill = null;
                    }
                    else {
                      circleNode.style.fill = invertColor(circleNode.style.fill);
                    }
                  }
                  else {
                    circleNode.style.fill = "yellow";
                  }
                  if (circleNode.style["fill-opacity"]) {
                    if (circleNode.style["fill-opacity"] == .98765) {
                      circleNode.style["fill-opacity"] = null;
                    }
                    else {
                      circleNode.style["fill-opacity"] = 1 - circleNode.style["fill-opacity"];
                    }
                  }
                  else {
                    circleNode.style["fill-opacity"] = .98765;
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
    }*/
    
	}
	
}
