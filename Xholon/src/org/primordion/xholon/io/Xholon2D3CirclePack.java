package org.primordion.xholon.io;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.IXholonService;

/**
 * Display the structure of a Xholon model using D3 circle packing.
 * TODO after mouse hovers for about 0.5s, display toString() as the tooltip ?
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2D3CirclePack implements EventListener {
	
	/** A service that provides additional methods for IXholon instances. */
  protected IXholon xholonHelperService = null;
  
  /** The current application. */
  protected IApplication app = Application.getApplication();
  
  /** constructor */
	public Xholon2D3CirclePack() {}
	
	@Override
  public void onBrowserEvent(Event event) {
    //System.out.println("Received event: " + event.getType());
    if (event.getTypeInt() == Event.ONMOUSEDOWN) {
      // createD3(...) already handles this event
      consoleLog("ONMOUSEDOWN Xholon2D3CirclePack");
    }
    else if (event.getTypeInt() == Event.ONMOUSEUP) {
      // createD3(...) already handles this event
      consoleLog("ONMOUSEUP Xholon2D3CirclePack");
    }
    else if (event.getTypeInt() == Event.ONCONTEXTMENU) {
      // createD3(...) already handles this event
      consoleLog("ONCONTEXTMENU Xholon2D3CirclePack");
    }
    else if ("dragover".equals(event.getType())) {
      handleDragOverEvent(event);
    }
    else if ("drop".equals(event.getType())) {
      handleDropEvent(event);
    }
    else {
      consoleLog(event.getType());
      consoleLog(event.getEventTarget());
      consoleLog(event.getDataTransfer());
    }
  }
  
  /**
   * in SvgViewBrowser, this is necessary; drop fails to do anything without this ???
   */
  public void handleDragOverEvent(Event doe) {
    doe.preventDefault();
  }
  
  /**
	 * 
	 */
  public void handleDropEvent(Event de) {
    // Event is a wrapper for (subclass of) NativeEvent
    String data = de.getDataTransfer().getData("text");
    Element ele = Element.as(de.getEventTarget());
    if (!"g".equals(ele.getNodeName())) {
      // try to get the <g> parent of a SVG shape (circle, ellipse, rect, etc.) or text element
      ele = ele.getParentElement();
    }
    de.stopPropagation();
    de.preventDefault();
    if (data == null) {return;}
    
    // handleDrop; paste data into node
    String svgNodeName = ele.getNodeName();
    String svgElementId = ele.getId();
    if (svgElementId == null) {return;}
    // the svgElementId is an XPath expression; use it to find the IXholon
    IXholon node = findXholonNode(svgElementId);
    if (node != null) {
      // assume this is XML content
      sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMDROP, data, node);
      // do not refresh the SVG content
    }
  }
  
  /**
   * Find an IXholon node.
   * @param svgElementId (ex: "helloWorldSystem_0")
   * @return An IXholon node, or null.
   */
  protected IXholon findXholonNode(String svgElementId) {
    IXholon theRootNode = app.getRoot();
    IXholon node = ((Xholon)theRootNode).getXPath()
      .evaluate("descendant-or-self::*[@name='" + svgElementId + "']", theRootNode);
    if (node == null) {
      if (theRootNode.getXhcName().equals(svgElementId)) {
        node = theRootNode;
      }
    }
    return node;
  }
  
  /**
   * Send a synchronous message to the XholonHelperService.
   * @param signal
   * @param data
   * @param sender
   * @return
   */
  protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
  {
    // send the request to the XholonHelperService by sending it a sync message
    if (xholonHelperService == null) {
      xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    if (xholonHelperService == null) {
      return null;
    }
    else {
      if (sender == null) {sender = app;}
      return xholonHelperService.sendSyncMessage(signal, data, sender);
    }
  }
  
  protected native void consoleLog(Object obj) /*-{
    $wnd.console.log(obj);
  }-*/;
	
	protected void enableEvents(Element svg) {
	  // enable events
    //DOM.sinkEvents(svg, Event.ONCLICK | Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONCONTEXTMENU);
    DOM.sinkBitlessEvent(svg, "dragover");
    DOM.sinkBitlessEvent(svg, "drop");
    DOM.setEventListener(svg, this);
	}
	
	/**
   * Get all referencing nodes as an array.
   * @param node
   * @return An array of IXholon objects, or an empty array.
   */
  public static Object[] searchForReferencingNodes(IXholon node) {
    return node.searchForReferencingNodes().toArray();
  }
	
	/**
	 * Create a D3 Pack Layout from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"|"#xhanim"
	 * This is called directly by io.XholonGuiD3CirclePack.showTree(), and by
	 * ef.d3.Xholon2CirclePack.java createD3() through io.XholonGuiD3CirclePack.showTree()
	 * @param json 
	 * @param efParams User-specified parameters such as through dat.GUI
	 * @param width 
	 * @param height 
	 * @param selection 
	 * @param gui 
	 */
	protected native void createD3(JavaScriptObject json, JavaScriptObject efParams, int width, int height, Object selection, IXholonGui gui) /*-{
	  var w = width,
    h = height,
    format = $wnd.d3.format(",d"),
    sort = null,
    mode = null,
    labelContainers = false,
    includeId = false,
    shape = "circle",
    maxSvg = 50; // max allowable number of SVG subtrees, to prevent running out of memory
    
    if (efParams) {
      sort = efParams.sort;
      mode = efParams.mode;
      labelContainers = efParams.labelContainers;
      includeId = efParams.includeId;
      shape = efParams.shape;
      maxSvg = efParams.maxSvg;
      if (efParams.width != -1) {w = efParams.width;}
      if (efParams.height != -1) {h = efParams.height;}
      if (efParams.selection) {selection = efParams.selection;}
    }
    
    var pack = $wnd.d3.layout.pack()
      .size([w - 4, h - 4])
      .value(function(d) {
        return d.size;
      });
    
    // TODO allow passing in a new comparator function
    if (sort) {
      switch(sort) {
      case "default": break;
      case "disable": pack.sort(null); break;
      case "ascending": pack.sort($wnd.d3.ascending); break;
      case "descending": pack.sort($wnd.d3.descending); break;
      default: break;
      }
    }
    
    var selectionNode = $wnd.d3.select(selection);
    if ((maxSvg < 1)
      || ((!selectionNode.empty())
        && (selectionNode.node().childNodes.length > maxSvg))) {
          // prevent number of SVG subtrees from becoming arbitrarily large
          return;
        }
    var hidden = false;
    
    // selectionNode.html(null) only works if selectionNode is an HTML node; can't be a SVG node
    if (mode) {
      switch(mode) {
      case "new": break;
      case "replace": selectionNode.html(null); break;
      case "tween":
        // set hidden to true only if selectionNode already has an "svg" element
        if (!selectionNode.select("svg").empty()) {
          hidden = true;
        }
        break;
      default: break;
      }
    }
    
    var svg = selectionNode.append("svg")
      .classed("hidden", hidden)
      .attr("width", w)
      .attr("height", h)
      .append("g") // the top-level g
      .attr("transform", function(d) {
        var sx = 1;
        var sy = 1;
        var cx = w / 2;
        var cy = h / 2;
        if (w > h) {sx = w/h;}
        else if (h > w) {sy = h/w;}
        var m = "matrix(" + sx + ", 0, 0, " + sy + ", " + (cx-sx*cx+2) + ", " + (cy-sy*cy+2) + ")";
        return m;
      });
    
    this.@org.primordion.xholon.io.Xholon2D3CirclePack::enableEvents(Lcom/google/gwt/dom/client/Element;)(svg.node().parentNode);
    
    var node = svg
      .data([json])
      .selectAll("g.node")
      .data(pack.nodes)
      .enter()
      .append("g")
      .attr("class", function(d) {
        var xhclass = "";
        if (d.xhclass) {
          xhclass = " " + d.xhclass;
        }
        if (d.dummy) {return "d3cpdummy" + xhclass;}
        return d.children ? "d3cpnode" + xhclass : "d3cpleaf d3cpnode" + xhclass;
      })
      .attr("transform", function(d) {
        return "translate(" + d.x + "," + d.y + ")";
      });
    
    // TODO I may want to include a unique prefix to ensure that the id is unique in the HTML tree
    if (includeId) {
      node.attr("id", function(d) {
        return d.name;
      });
    }
    
    node.append("title")
      .text(function(d) {
        return d.name;
      });
    
    switch (shape) {
    case "circle":
      node.append(shape)
        .attr("r", function(d) {return d.r;})
        //.style("stroke", function(d) {return d.color;})
        .style("fill", function(d) {return d.color;})
        .style("fill-opacity", function(d) {return d.opacity;});
      break;
    case "ellipse": // TODO this is just a placeholder for now
      node.append(shape)
        .attr("rx", function(d) {return d.r;})
        .attr("ry", function(d) {return d.r;})
        .style("fill", function(d) {return d.color;})
        .style("fill-opacity", function(d) {return d.opacity;});
      break;
    case "rect": // TODO this is just a placeholder for now; a fully rounded square
      node.append(shape)
        .attr("width", function(d) {return d.r * 2;}) // Math.sqrt(2*d.r*d.r)
        .attr("height", function(d) {return d.r * 2;})
        .attr("rx", function(d) {return d.r;})
        .attr("ry", function(d) {return d.r;})
        //.attr("transform", function(d) { return "translate(-" + d.r + ",-" + d.r + ")";})
        .attr("x", function(d) {return -d.r;})
        .attr("y", function(d) {return -d.r;})
        .style("fill", function(d) {return d.color;})
        .style("fill-opacity", function(d) {return d.opacity;});
      break;
    default: break; // TODO handle path, g, embedded svg, etc.
    }
    
    node.filter(function(d) {
      return !(d.children || d.dummy);
    }).append("text")
      .attr("dy", ".3em")
      .style("text-anchor", "middle")
      .style("font-size", function(d) {
        return (d.r * 1.75) + "px";
      })
      .text(function(d) {
        if (d.symbol) {return d.symbol;}
        var dname = d.name.substring(0, 1);
        if ((dname == ":") && (d.name.length > 1)) {
          dname = d.name.substring(1, 2);
        }
        return dname;
      });
    
    // optionally place small text at top of container nodes (nodes that have children)
    if (labelContainers) {
      node.filter(function(d) {
        return d.children;
      }).append("text")
        .attr("dy", function(d) {
          var px = d.r - 8; // (circleRadius - (fontsize + 5 - 1))
          if (px < 0) {px = 0;}
          return "-" + px + "px";
        })
        .style("text-anchor", "middle")
        .style("font-size", function(d) {
          return "12px";
        })
        .text(function(d) {
          if (d.symbol) {return d.symbol;}
          var dname = d.name.substring(0, 1);
          if ((dname == ":") && (d.name.length > 1)) {
            dname = d.name.substring(1, 2);
          }
          return dname;
        });
    }
    
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
    //  $wnd.console.log("drag");
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
    function handleDragStart(d, i) {
    //  $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
	    $wnd.console.log("handleDragStart");
      $wnd.console.log(d);
      $wnd.console.log(i);
    }
    // nothing happens
    function handleDrag(d, i) {
    //  $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
	    $wnd.console.log("handleDrag");
      $wnd.console.log(d);
      $wnd.console.log(i);
    }
    function handleDragEnd(d, i) {
      // Dec 21 2014 this only sort-of works sporadically
      //   can't get the dataTransfer
      //   will need to work to get the destination node
      $wnd.d3.event.sourceEvent.preventDefault();
	  //  $wnd.d3.event.sourceEvent.stopPropagation();
      $wnd.console.log("handleDragEnd");
      $wnd.console.log(d);
      $wnd.console.log(i);
      $wnd.console.log($wnd.d3.event);
      $wnd.console.log($wnd.d3.event.sourceEvent);
      $wnd.console.log($wnd.d3.event.sourceEvent.dataTransfer); // null
      //$wnd.console.log($wnd.d3.event.sourceEvent.dataTransfer.getData("text"));
      //$wnd.alert("handleDragEnd");
    }
    
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
      var titles = svg.selectAll("g title");
      var xhc = node.@org.primordion.xholon.base.IXholon::getXhc()();
      if (xhc) {
        
        // toggle a Xholon node's XholonClass node
        var xhcTitle = titles.filter(function(d, i) {
          return d.name == xhc.name();
        });
        findAndStyleReferencedNode(xhcTitle);
        
        // toggle the Xholon node's ports
        var ports = node.ports();
        if (ports && ports.length) {
          // this is an ActiveObject node in the CSH
          for (var i = 0; i < ports.length; i++) {
            var portInfo = ports[i].obj();
            var reffedNode = portInfo.reffedNode;
            if (reffedNode) {
              var title = titles.filter(function(d, i) {
                return d.name == reffedNode.name();
              });
              findAndStyleReferencedNode(title);
            }
          }
        }
        
        // toggle other IXholon nodes that reference this node
        // use a different way of highlighting; highlight the stroke color
        var reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForReferencingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        if (reffingNodes && reffingNodes.length) {
          for (var i = 0; i < reffingNodes.length; i++) {
            var reffingNode = reffingNodes[i];
            if (reffingNode) {
              var title = titles.filter(function(d, i) {
                return d.name == reffingNode.name();
              });
              findAndStyleReferencingNode(title);
            }
          }
        }
      }
      else {
        // this is a XholonClass node
        // toggle IXholon nodes that reference this XholonClassnode
        // highlight the stroke color
        var reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForReferencingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        if (reffingNodes && reffingNodes.length) {
          for (var i = 0; i < reffingNodes.length; i++) {
            var reffingNode = reffingNodes[i];
            if (reffingNode) {
              var title = titles.filter(function(d, i) {
                return d.name == reffingNode.name();
              });
              findAndStyleReferencingNode(title);
            }
          }
        }
      }
    }
    
    
    // find a D3 circle node that's referenced by another node, and
    // invert or set its fill and fill-opacity styles
    function findAndStyleReferencedNode(title) {
      if (title.empty()) {return;}
      var parent = $wnd.d3.select(title.node().parentNode);
      var circleNode = parent.select(shape);
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
    
    // find a D3 circle node that references another node, and
    // invert or set its stroke and stroke-width styles
    function findAndStyleReferencingNode(title) {
      if (title.empty()) {return;}
      var parent = $wnd.d3.select(title.node().parentNode);
      var circleNode = parent.select(shape);
      if (circleNode) {
        // stroke-width
        var styleStrokeWidth = circleNode.style("stroke-width");
        // TODO don't depend on these specific values
        if (styleStrokeWidth == "0px") {styleStrokeWidth = "1px";}
        else if (styleStrokeWidth == "1px") {styleStrokeWidth = "0px";}
        else if (styleStrokeWidth == ".5px") {styleStrokeWidth = "1.5px";}
        else {styleStrokeWidth = ".5px";}
        circleNode.style("stroke-width", styleStrokeWidth);
        // stroke
        var styleStroke = circleNode.style("stroke");
        if (styleStroke) {
          var styleStrokeHex = $wnd.d3.rgb(styleStroke).toString();
          if (styleStrokeHex == "#ffff00") { // "yellow"
            circleNode.style("stroke", null);
          }
          else {
            circleNode.style("stroke", invertColor(styleStrokeHex));
          }
        }
        else {
          circleNode.style("stroke", "yellow");
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
