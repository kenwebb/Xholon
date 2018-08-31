package org.primordion.xholon.io;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

import org.client.RCImages;

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
    if ((svgElementId == null) || (svgElementId.length() == 0)) {return;}
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
   * Get all referencing nodes as an array, using the getLinks() method, to obtain non-port ports.
   * @param node
   * @return An array of IXholon objects, or an empty array.
   */
  public static Object[] searchForLinkingNodes(IXholon node) {
    return node.searchForLinkingNodes().toArray();
  }
  
  /**
   * Get a named system image, as defined in the Xholon RCImages class.
   * @param The name of an image (ex: "Control_control_play_blue").
   * @return A data URL, or null.
   */
  protected static String rcImages(String resourceName) {
    ImageResource ir = (ImageResource)RCImages.INSTANCE.getResource(resourceName);
    if (ir == null) {return null;}
	  return ir.getSafeUri().asString();
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
    labelContainers = false, // true false
    labelContainersOptions = "top", // "top" "bottom" "center"
    includeId = false,
    shape = "circle",
    shapeParams = "5,5"; // rect rx,ry
    maxSvg = 50, // max allowable number of SVG subtrees, to prevent running out of memory
    maxChars = 1, // max allowable number of chars in the standard text
    fontSizeMultiplier = 1.75, // used to control the font-size of leaf nodes
    marble = "", // alternative content, in place of the standard text
    supportTouch = false,
    useIcons = false,
    iconPos = "outside",
    useAnno = false,
    annoPos = "outside",
    _jsdata = false
    nonportPorts = false;
    togglePortColors = false;
    
    if (efParams) {
      sort = efParams.sort;
      mode = efParams.mode;
      labelContainers = efParams.labelContainers;
      labelContainersOptions = efParams.labelContainersOptions;
      includeId = efParams.includeId;
      shape = efParams.shape;
      shapeParams = efParams.shapeParams;
      maxSvg = efParams.maxSvg;
      maxChars = efParams.maxChars;
      fontSizeMultiplier = efParams.fontSizeMultiplier;
      marble = efParams.marble;
      if (marble) {
        marble = $wnd.JSON.parse(marble);
      }
      if (efParams.width != -1) {w = efParams.width;}
      if (efParams.height != -1) {h = efParams.height;}
      if (efParams.selection) {selection = efParams.selection;}
      supportTouch = efParams.supportTouch;
      useIcons = efParams.useIcons;
      iconPos = efParams.iconPos;
      useAnno = efParams.useAnno;
      annoPos = efParams.annoPos;
      _jsdata = efParams._jsdata;
      nonportPorts = efParams.nonportPorts;
      togglePortColors = efParams.togglePortColors;
    }
    
    var zoom = $wnd.d3.behavior.zoom()
      .on("zoom", redraw);
    
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
    var overlay = false;
    
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
        zoom = function() {return;}
        break;
      case "video":
        // TODO
        selectionNode = $wnd.d3.select($doc.createElement("div"));
        //.attr("id", function(d) {
        //  return "frame_" + $wnd.xh.param("TimeStep");
        //});
        break;
      case "overlay":
        overlay = true;
        selectionNode.classed("divoverlay", true);
        selectionNode.style("height", h + "px");
        break;
      default: break;
      }
    }
    
    var svg = selectionNode
      .append("svg")
      .classed("hidden", hidden)
      .classed("overlay", overlay)
      .attr("width", w)
      .attr("height", h)
      .call(zoom)
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
        if (_jsdata) {
          set_jsdata(d);
        }
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
      var rxRy = shapeParams.split(",");
      node.append(shape)
        .attr("width", function(d) {return d.r * 2;}) // Math.sqrt(2*d.r*d.r)
        .attr("height", function(d) {return d.r * 2;})
        .attr("rx", rxRy[0]) //function(d) {return d.r;})
        .attr("ry", rxRy[1]) //function(d) {return d.r;})
        //.attr("transform", function(d) { return "translate(-" + d.r + ",-" + d.r + ")";})
        .attr("x", function(d) {return -d.r;})
        .attr("y", function(d) {return -d.r;})
        .style("fill", function(d) {return d.color;})
        .style("fill-opacity", function(d) {return d.opacity;});
      break;
    default: break; // TODO handle path, g, embedded svg, etc.
    }
    
    node.filter(function(d) {
      //$wnd.console.log(d);
      if (d.dummy) {return false;}
      if (d.icon) {return false;}
      // display centered containers, only when they are symbols
      if (labelContainersOptions == "center" && d.symbol) {return true;}
      return !d.children; // || (labelContainersOptions == "center");
    }).append("text")
      .attr("dy", ".3em")
      .style("text-anchor", "middle")
      .style("font-size", function(d) {
        return (d.r * fontSizeMultiplier / maxChars) + "px";
      })
      .classed("marblesymbol", function() {
        if (marble) {return true;}
        else {return false;}
      })
      .text(function(d) {
        if (d.symbol) {return d.symbol;}
        var dname = d.name.substring(0, maxChars);
        var posColon = dname.indexOf(":");
        if (posColon != -1) {
          if ((posColon == 0) && (d.name.length > maxChars)) {
            dname = d.name.substring(1, maxChars+1);
          }
          else {
            dname = dname.substring(0, posColon);
          }
        }
        return dname;
      });
    
    // icons
    // TODO provide size options "ouside"(default) "inside"(bounding box) "image"(original image size) "MxN"
    if (useIcons) {
      node.filter(function(d) {
        return d.icon;
      }).append("image")
        .attr("xlink:href", function(d) {
          var iconArr = iconPos.split(" ");
          switch (iconArr[0]) {
          case "inside":
            d.spclwh = Math.sqrt(Math.pow((d.r * 2), 2) / 2);
            d.spclx = d.spcly = d.spclwh / (-2);
            break;
          case "outside":
          default:
            d.spclwh = d.r * 2;
            d.spclx = d.spcly = d.r * (-1);
            break;
          }
          if (iconArr.length > 1) {
            handleCardinalDirections(d, iconArr[1]);
          }
          var dicon = d.icon;
          if (dicon.substring(0,7) == "system:") {
            dicon = dicon.substring(7);
            dicon = @org.primordion.xholon.io.Xholon2D3CirclePack::rcImages(Ljava/lang/String;)(dicon);
          }
          return dicon;
        })
        .attr("x", function(d) {return d.spclx;}) //{return d.r * (-1);})
        .attr("y", function(d) {return d.spcly;}) //{return d.r * (-1);})
        .attr("width", function(d) {return d.spclwh;}) //{return d.r * 2;})
        .attr("height", function(d) {return d.spclwh;}) //{return d.r * 2;})
        .style("opacity", function(d) {
          if (d.opacity == 0) {return 1.0;} // this probably involves a dummy circle
          return d.opacity;
        });
        // TODO viewBox and preserveAspectRatio don't work correctly yet
        //.attr("viewBox", function(d) {return "" + d.spclx + " " + d.spcly + " " + d.spclwh + " " + d.spclwh;})
        //.attr("preserveAspectRatio", function(d) {return "xMinYMin meet";}) // "defer ..." ?
    }
    
    // annotations
    if (useAnno) {
      node.filter(function(d) {
        return d.anno;
      }).append("text")
        .attr("dy", function(d) {
          var annoArr = annoPos.split(" ");
          switch (annoArr[0]) {
          case "inside":
            d.spclwh = Math.sqrt(Math.pow((d.r * 2), 2) / 2);
            d.spclx = d.spcly = d.spclwh / (-2);
            break;
          case "outside":
          default:
            d.spclwh = d.r * 2;
            d.spclx = d.spcly = d.r * (-1);
            break;
          }
          if (annoArr.length > 1) {
            handleCardinalDirections(d, annoArr[1]);
          }
          return ".02em"; //".05em";
        })
        .attr("x", function(d) {return d.spclx;}) //{return d.r * (-1);})
        .attr("y", function(d) {return d.spcly;}) //{return d.r * (-1);})
        .classed("annotext", true)
        .text(function(d) {
          return d.anno;
        })
      svg.selectAll("text.annotext")
        .call(wrap, 100); //150);
    }
    
    if (marble) {
      // this is for marble.type == "default"; there may also be other marble types
      // another marble type could be "gear"
      //   see bl.ocks.org/1353700
      //   see https://github.com/liabru/gears-d3-js
      // ex: brick_46  Quail:blocksAndBricks_37
      // TODO optionally add a small red circle whose radius is proportional to a value
      var mnode = node.filter(function(d) {
        return !(d.children || d.dummy);
      });
      mnode.append("text") // roleName
        .attr("dy", "0.0em")
        .style("text-anchor", "middle")
        .style("font-size", function(d) {
          var dr = d.r > 22 ? 11 : d.r * 0.5;
          return dr + "px";
        })
        .classed("marbletext1", true)
        .text(function(d) {
          var ix = d.name.indexOf(":");
          if (ix == -1) {
            return "";
          }
          else {
            var roleName = d.name.substring(0, ix);
            return roleName.substring(0, marble.maxChars);
          }
        });
      mnode.append("text") // XholonClass name
        .attr("dy", "1.2em")
        .style("text-anchor", "middle")
        .style("font-size", function(d) {
          var dr = d.r > 22 ? 11 : d.r * 0.5;
          return dr + "px";
        })
        .classed("marbletext2", true)
        .text(function(d) {
          var xholonClassName = "";
          var ix = d.name.indexOf(":");
          if (ix == -1) {
            xholonClassName = d.name;
          }
          else {
            xholonClassName = d.name.substring(ix+1);
          }
          ix = xholonClassName.lastIndexOf("_");
          if (ix == -1) {
            return xholonClassName;
          }
          else {
            xholonClassName = xholonClassName.substring(0, ix);
            return xholonClassName.substring(0, marble.maxChars);
          }
        });
    } // end if(marble) 
    
    // optionally place small text at top or bottom of container nodes (nodes that have children)
    if (labelContainers) {
      node.filter(function(d) {
        return d.children;
      }).append("text")
        .attr("dy", function(d) {
          if (labelContainersOptions == "bottom") {
            return d.r + "px";
          }
          else { // "top"
            var px = d.r - 8; // (circleRadius - (fontsize + 5 - 1))
            if (px < 0) {px = 0;}
            return "-" + px + "px";
          }
        })
        .style("text-anchor", "middle")
        .style("font-size", function(d) {
          // TODO this should vary in size
          return "12px";
        })
        .text(function(d) {
          if (d.symbol) {return d.symbol;}
          var dname = d.name.substring(0, maxChars);
          var posColon = dname.indexOf(":");
          if (posColon != -1) {
            if ((posColon == 0) && (d.name.length > maxChars)) {
              dname = d.name.substring(1, maxChars+1);
            }
            else {
              dname = dname.substring(0, posColon);
            }
          }
          return dname;
        });
    }
    
    if (supportTouch && $wnd.Hammer) {
      //$wnd.console.log("starting Hammer ...");
      //$wnd.console.log(svg); //selection);
      //$wnd.console.log(svg.node());
      // svg is a d3 array, and is in fact the outermost g
      var hammer = new $wnd.Hammer.Manager(svg.node());
      //$wnd.console.log(hammer);
      
      // be able to distinguish single from double tap
      // see http://hammerjs.github.io/require-failure/
      //var singleTap = new $wnd.Hammer.Tap({event: 'singletap', taps: 1});
      //var doubleTap = new $wnd.Hammer.Tap({event: 'doubletap', taps: 2});
      var tap = new $wnd.Hammer.Tap();
      var press = new $wnd.Hammer.Press();
      var pan = new $wnd.Hammer.Pan();
      //hammer.add([tap, press, pan]); // the order of these may be important
      hammer.add([pan, press, tap]);
      //hammer.add([doubleTap, singleTap, press, pan]); // the order of these is important
      //doubleTap.recognizeWith(singleTap);
      //singleTap.requireFailure(doubleTap);
      
      var shapeTarget = null; // circle ellipse rect etc.
      var panStartTarget = null; // g
      var panStartTargetMatrix = null;
      var panStartTargetMatrixE = 0;
      var panStartTargetMatrixF = 0;
      var panMoveTarget = null; // ev.target.parentElement found during panmove
      
      //hammer.on('singletap doubletap press panstart panmove panend', function(ev) {
      hammer.on('tap press panstart panmove panend', function(ev) {
        //$wnd.console.log(ev);
        //$wnd.console.log(ev.target);
        //$wnd.console.log(ev.target.__data__);
        //$wnd.console.log(ev.type + " " + ev.pointerType + " " + ev.tapCount + " "
        // + ev.target.parentNode.getAttribute("id")); // shows the g that the circle is contained in
        switch (ev.type) {
        //case "singletap":
        case "tap":
          //if (ev.tapCount == 1) {
            ev.preventDefault();
            handleTap(ev.target.__data__);
          //}
          //else { // assume tapCount == 2
            // use "doubletap" event instead
            //ev.preventDefault();
            //handleDbltap(ev.target.__data__, ev.srcEvent.pageX, ev.srcEvent.pageY);
          //}
          break;
        //case "doubletap":
        //  ev.preventDefault();
        //  handleDbltap(ev.target.__data__, ev.srcEvent.pageX, ev.srcEvent.pageY);
        //  break;
        case "press": // Contextmenu
          ev.preventDefault();
          handlePress(ev.target.__data__, ev.srcEvent.pageX, ev.srcEvent.pageY);
          break;
        case "panstart":
          ev.preventDefault();
          shapeTarget = ev.target; // circle etc.
          panStartTarget = shapeTarget.parentElement; // g
          if (panStartTarget == panStartTarget.parentElement.firstElementChild) {
            // TODO don't allow dragging first (root) g node
            // cancel the pan/drag
            console.log("should not be allowed to drag first (root) g node");
            //hammer.stop(); // maybe use stop(true); ???
            //return;
          }
          // http://stackoverflow.com/questions/24202104/svg-drag-and-drop
          // following statement allows us to find out where the dragged element is dropped (see panend)
          shapeTarget.setAttribute("pointer-events", "none");
          // make the moved SVG g element the top element so it can be clicked or touched again
          panStartTarget.parentNode.appendChild(panStartTarget);
          panStartTargetMatrix = panStartTarget.transform.baseVal.getItem(0).matrix; // getCTM() doesn't work
          panStartTargetMatrixE = panStartTargetMatrix.e;
          panStartTargetMatrixF = panStartTargetMatrix.f;
          //$wnd.console.log("panstart");
          //$wnd.console.log(ev);
          panStartTargetMatrix.e = panStartTargetMatrixE + ev.deltaX;
          panStartTargetMatrix.f = panStartTargetMatrixF + ev.deltaY;
          panMoveTarget = panStartTarget; // ensure that it has a non-null value
          break;
        case "panmove":
          ev.preventDefault();
          //$wnd.console.log("panmove");
          //$wnd.console.log(ev.target);
          panStartTargetMatrix.e = panStartTargetMatrixE + ev.deltaX;
          panStartTargetMatrix.f = panStartTargetMatrixF + ev.deltaY;
          // keep track of last event target != the node that's being dragged
          if (ev.target.parentElement && (panStartTarget != ev.target.parentElement)) {
            panMoveTarget = ev.target.parentElement;
          }
          break;
        case "panend":
          ev.preventDefault();
          shapeTarget.setAttribute("pointer-events", "");
          //$wnd.console.log("panend");
          //$wnd.console.log(ev);
          //$wnd.console.log(ev.target);
          var xhroot = $wnd.xh.root();
          var svgchld = panStartTarget;
          var svgprnt = ev.target.parentElement;
          if (panStartTarget == svgprnt) {
            svgprnt = panMoveTarget; // use last event target != the node that's being dragged
          }
          //$wnd.console.log(svgchld);
          //$wnd.console.log(svgprnt);
          var xhchld = xhroot.xpath("descendant-or-self::*[@name='" + svgchld.id + "']");
          var xhprnt = xhroot.xpath("descendant-or-self::*[@name='" + svgprnt.id + "']");
          //$wnd.console.log(xhchld);
          //$wnd.console.log(xhprnt);
          // TODO prevent dangerous moves; fix z-order of moved nodes?
          if (xhchld && xhprnt) {
            if (xhchld == xhprnt) {
              //xhroot.println(xhchld.name() + " has stayed inside its original parent");
            }
            else if (xhchld.parent() != xhprnt) {
              // prevent a node from appending it's parent or one of it's ancestors
              var xhnode = xhprnt;
              while (xhnode != null) {
                if (xhnode == xhchld) {
                  //xhroot.println(xhchld.name() + " can't be moved to a child or descendant " + xhprnt.name());
                  return;
                }
                xhnode = xhnode.parent();
              }
              xhprnt.append(xhchld.remove());
              //xhroot.println(xhchld.name() + " has moved to " + xhprnt.name());
            }
            else {
              //xhroot.println(xhchld.name() + " has stayed inside " + xhprnt.name());
            }
          }
          else {
            //xhroot.println(svgchld.id + " has unknown location");
          }
          break;
        default: break;
        }
      });
      //$wnd.console.log("... end Hammer");
    }
    else {
      node.on("click", function(d, i) {
        handleClick(d, i);
      });
      
      node.on("contextmenu", function(d, i) {
        handleContextmenu(d, i);
      });
      
      node.on("dblclick", function(d, i) {
        handleDblclick(d, i);
      });
    }
    
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
    
    if (mode == "video") {
      // TODO write to file system ?
      var frameSvg = selectionNode.node().firstElementChild;
      var ts = $wnd.xh.param("TimeStep");
      frameSvg.setAttribute("id", "frame_" + ts);
      if (ts != "0") {
        frameSvg.setAttribute("class", "hidden");
      }
      var frameStr = new $wnd.XMLSerializer().serializeToString(frameSvg);
      //$wnd.console.log(frameStr);
      var p = $doc.createElement("p");
      p.textContent = frameStr;
      $doc.querySelector("body").appendChild(p);
    }
    
    // Hammer.js single tap
    function handleTap(d) {
      if (d === undefined) {return;}
      if (d.dummy) {d = d.parent;}
      var isCtrlPressed = false;
      gui.@org.primordion.xholon.io.IXholonGui::handleNodeSelection(Ljava/lang/String;Ljava/lang/Object;Z)(d.name, d, isCtrlPressed);
    }
    
    // Hammer.js press
    function handlePress(d, x, y) {
      if (d === undefined) {return;}
      if (d.dummy) {d = d.parent;}
      var posX = 0;
      var posY = y;
      gui.@org.primordion.xholon.io.IXholonGui::makeContextMenu(Ljava/lang/Object;II)(d, posX, posY);
    }
    
    // Hammer.js double tap
    function handleDbltap(d, x, y) {
      //if (d === undefined) {return;}
      handlePress(d, x, y);
    }
    
    function handleClick(d, i) {
      if (d.dummy) {d = d.parent;}
      $wnd.d3.event.preventDefault();
	    $wnd.d3.event.stopPropagation();
      var isCtrlPressed = $wnd.d3.event.ctrlKey;
      gui.@org.primordion.xholon.io.IXholonGui::handleNodeSelection(Ljava/lang/String;Ljava/lang/Object;Z)(d.name, d, isCtrlPressed);
    }
    
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
      // see http://stackoverflow.com/questions/21020564/how-to-distinguish-between-single-mouse-click-and-double-click-on-same-node-elem
      // see https://gist.github.com/tmcw/4067674 and http://bl.ocks.org/tmcw/4067674
      //var node = getXholonNode(d);
      //if (node) {
      //  $wnd.alert("Double click for " + node.toString());
      //}
      handleContextmenu(d, i);
    };
    
    function handleMouseOverOut(d, i) {
      var node = getXholonNode(d);
      if (node && togglePortColors) {
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
        var ports = null;
        if (nonportPorts) { // May 28 2017
          ports = node.links(false, true);
        }
        else {
          ports = node.ports();
        }
        if (ports && ports.length) {
          // this is an ActiveObject node in the CSH
          for (var i = 0; i < ports.length; i++) {
            var portInfo = null;
            if (nonportPorts) { // May 28 2017
              portInfo = ports[i];
            }
            else {
              portInfo = ports[i].obj();
            }
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
        var reffingNodes = null;
        if (nonportPorts) { // May 28 2017
          reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForLinkingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        }
        else {
          reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForReferencingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        }
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
        var reffingNodes = null;
        if (nonportPorts) { // May 28 2017
          reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForLinkingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        }
        else {
          reffingNodes = @org.primordion.xholon.io.Xholon2D3CirclePack::searchForReferencingNodes(Lorg/primordion/xholon/base/IXholon;)(node);
        }
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
    
    // redraw - to be called on zoom event
    // don't scale the stroke-width
    function redraw() {
      svg.style("stroke-width", 0.1 / $wnd.d3.event.scale + "px");
      svg.attr("transform", "translate(" + $wnd.d3.event.translate + ")"  + " scale(" + $wnd.d3.event.scale + ")");
    }
    
    function set_jsdata(d) {
      var xhnode = getXholonNode(d);
      var x = d.x;
      var y = d.y;
      var w = 0;
      var h = 0;
      if (d.r) {
        w = d.r * 2;
        h = d.r * 2;
      }
      _set_jsdata(xhnode, x, y, w, h);
    }
    
    function _set_jsdata(xhnode, x, y, w, h) {
      if (!x) {x = 0;}
      if (!y) {y = 0;}
      xhnode._jsdata = {}
      xhnode._jsdata.posx = x;
      xhnode._jsdata.posy = y;
      if (w) {xhnode._jsdata.posw = w;}
      if (h) {xhnode._jsdata.posh = h;}
    }
    
    // from "Wrapping Long Labels" https://bl.ocks.org/mbostock/7555321
    function wrap(text, width) {
      text.each(function() {
        var text = $wnd.d3.select(this),
            words = text.text().split(/\s+/).reverse(),
            word,
            line = [],
            lineNumber = 0,
            lineHeight = 1.1, // ems
            y = text.attr("y"),
            dy = parseFloat(text.attr("dy")),
            tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
        while (word = words.pop()) {
          line.push(word);
          tspan.text(line.join(" "));
          if (tspan.node().getComputedTextLength() > width) {
            line.pop();
            tspan.text(line.join(" "));
            line = [word];
            tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
          }
        }
      });
    }
    
    // Handle cardinal directions, if specified for icon or anno.
    // cardir cardinal direction
    function handleCardinalDirections(d, cardir) {
      // all of these cardinal directions are half-size (spclwh), and each is positioned at a different place (spclx spcly)
      // nw  n ne
      // w   m e
      // sw  s se
      var wh = d.spclwh / 2; // icon or anno wh
      var x = d.spclx; // icon or anno x
      var y = d.spcly; // icon or anno y
      switch (cardir) {
      // top row
      case "nw": break;
      case "n":  d.spclx = x + (wh / 2); break;
      case "ne": d.spclx = x + wh; break;
      // middle row
      case "w": d.spcly = y + (wh / 2); break;
      case "m": d.spclx = x + (wh / 2); d.spcly = y + (wh / 2); break;
      case "e": d.spclx = x + wh; d.spcly = y + (wh / 2); break;
      // bottom row
      case "sw": d.spcly = y + (wh / 2); break;
      case "s":  d.spclx = x + (wh / 2); d.spcly = y + wh; break;
      case "se": d.spclx = x + wh; d.spcly = y + wh; break;
      default: break;
      }
    }
    
	}-*/;  // end protected native void createD3(...)
	
}
