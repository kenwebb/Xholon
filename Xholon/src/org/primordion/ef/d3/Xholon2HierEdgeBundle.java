package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;

import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;

import org.primordion.ef.AbstractXholon2ExternalFormat;

//import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model in D3 Hierarchy JSON format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 * @see <a href="http://bl.ocks.org/mbostock/1044242">D3 example</a>
 */
public class Xholon2HierEdgeBundle extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  /**
   * A dummy string prefixed to each node path name.
   * The JavaScript code expects every name to be a concatenation of at least 2 parts, separated by "/".
   * The name prefix allows the root node to have at least 2 parts.
   * examples: xh/HelloWorldSystem  xh/HelloWorldSystem/Hello
   */
  protected static final String NAME_PREFIX = "xh";
  
  protected String outFileName;
	protected String outPath = "./ef/d3/";
	protected String modelName;
	protected IXholon root;
	private StringBuilder sb;
	
	private String indent = "                              ";
	private boolean shouldShowStateMachineEntities = false;
	
	private int numNodes = 0;
	
	public Xholon2HierEdgeBundle() {}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
	  if (root == null) {return false;}
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + ".json";
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
	  if (root == null) {return;}
		sb = new StringBuilder();
		// JSON doesn't allow comments
		numNodes = 0;
		sb.append("[\n");
		writeNode(root, 0, NAME_PREFIX); // root is level 0
		String jsonStr = sb.toString();
		// remove final ",\n" and append a new "\n"
		int ix = jsonStr.lastIndexOf(",");
		if (ix != -1) {
		  jsonStr = jsonStr.substring(0, ix) + "\n";
		}
		jsonStr = jsonStr + "]\n";
		writeToTarget(jsonStr, outFileName, outPath, root);
		if (root.getApp().isUseGwt()) {
		  int width = 100;
		  int height = 100;
		  if (numNodes > 1000) {
		    width = 1000;
		    height = 1000;
		  }
		  else if (numNodes > 100) {
		    width = numNodes;
		    height = numNodes;
		  }
		  JavaScriptObject json = ((JSONArray)JSONParser.parseLenient(jsonStr)).getJavaScriptObject();
		  createD3(json, width, height, "#xhgraph");
		}
	}
	
	/**
	 * Write one node, and its child nodes.
	 * example:
[
{"name":"flare.analytics.cluster.AgglomerativeCluster","size":3938,"ports":["flare.animate.Transitioner","flare.vis.data.DataList","flare.util.math.IMatrix","flare.analytics.cluster.MergeEdge","flare.analytics.cluster.HierarchicalCluster","flare.vis.data.Data"]},
{"name":"flare.animate.Easing","size":17010,"ports":["flare.animate.Transition"]},
{"name":"flare.analytics.cluster.MergeEdge","size":743,"ports":[]},
{"name":"flare.vis.Visualization","size":16540,"ports":["flare.animate.Transitioner","flare.vis.operator.IOperator","flare.animate.Scheduler","flare.vis.events.VisualizationEvent","flare.vis.data.Tree","flare.vis.events.DataEvent","flare.vis.axis.Axes","flare.vis.axis.CartesianAxes","flare.util.Displays","flare.vis.operator.OperatorList","flare.vis.controls.ControlList","flare.animate.ISchedulable","flare.vis.data.Data"]}
]
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level, String pathName) {
		numNodes++;
		pathName = pathName + "/" + node.getName();
		sb
		.append("{\"name\": \"")
		.append(pathName)
		.append("\",");
		sb
		.append("\"size\":")
		.append(level+1)
		.append(",");
		sb
		.append("\"ports\":")
		// outgoing ports
		.append(getPortStr(node));
		sb.append("},\n");
		
		// only show nested state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
				// do nothing
		}
		else if (node.hasChildNodes()) {
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1, pathName);
				childNode = childNode.getNextSibling();
			}
		}
	}
	
	/**
	 * Get a String containing a JSON list of all outgoing ports.
	 */
	protected String getPortStr(IXholon node) {
	  StringBuilder sbp = new StringBuilder();
		List portList = node.getAllPorts();
		int len = portList.size();
		sbp.append("[");
		for (int i = 0; i < len; i++) {
			// ex: ["flare.animate.Transitioner","flare.vis.data.DataList"]
			String remoteNodeName = getPathName(((PortInformation)portList.get(i)).getReffedNode());
			sbp
			.append("\"")
			.append(remoteNodeName)
			.append("\"");
			if (i < len-1) {
			  sbp.append(",");
			}
		}
		sbp.append("]");
		return sbp.toString();
	}
	
	/**
	 * Get full CSH path name for a remote IXholon node.
	 * @param node
	 * @return (ex: "xh/helloWorldSystem_0/hello_1"
	 */
	protected String getPathName(IXholon node) {
  	String pn = "";
  	while (node != root) {
  	  pn = "/" + node.getName() + pn;
  	  node = node.getParentNode();
  	}
  	pn = NAME_PREFIX + "/" + root.getName() + pn;
  	return pn;
	}
	
	/**
	 * Create a D3 construct from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	var diameter = 960,
    radius = diameter / 2,
    innerRadius = radius - 120;
  
  var cluster = $wnd.d3.layout.cluster()
    .size([360, innerRadius])
    .sort(null)
    .value(function(d) { return d.size; });
  
  var bundle = $wnd.d3.layout.bundle();
  
  var line = $wnd.d3.svg.line.radial()
    .interpolate("bundle")
    .tension(.85)
    .radius(function(d) { return d.y; })
    .angle(function(d) { return d.x / 180 * Math.PI; });
  
  var svg = $wnd.d3.select(selection).append("svg")
    .attr("width", diameter)
    .attr("height", diameter)
    .append("g")
    .attr("transform", "translate(" + radius + "," + radius + ")");
  
  var packages = {
  
    // Lazily construct the package hierarchy from class names.
    root: function(classes) {
      var map = {};
      
      function find(name, data) {
        //console.log("find start");
        var node = map[name], i;
        if (!node) {
          node = map[name] = data || {name: name, children: []};
          if (name.length) {
            node.parent = find(name.substring(0, i = name.lastIndexOf("/")));
            if (!node.parent.children) {
              // KSW added this
              node.parent.children = [];
            }
            node.parent.children.push(node);
            node.key = name.substring(i + 1);
          }
        }
        //console.log("find end");
        return node;
      }
      
      classes.forEach(function(d) {
        find(d.name, d);
      });
      return map[""];
    },

    // Return a list of ports for the given array of nodes.
    ports: function(nodes) {
      var map = {},
          ports = [];

      // Compute a map from name to node.
      nodes.forEach(function(d) {
        map[d.name] = d;
      });

      // For each port, construct a link from the source to target node.
      nodes.forEach(function(d) {
        if (d.ports) d.ports.forEach(function(i) {
          ports.push({source: map[d.name], target: map[i]});
        });
      });
      return ports;
    }
  };
  
  var nodes = cluster.nodes(packages.root(json)),
      links = packages.ports(nodes);
  
  svg.selectAll(".link")
    .data(bundle(links))
    .enter().append("path")
    .attr("class", "d3heblink")
    .attr("d", line);
  
  svg.selectAll(".node")
    .data(nodes.filter(function(n) { return !n.children; }))
    .enter().append("g")
    .attr("class", "d3hebnode")
    .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; })
    .append("text")
    .attr("dx", function(d) { return d.x < 180 ? 8 : -8; })
    .attr("dy", ".31em")
    .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
    .attr("transform", function(d) { return d.x < 180 ? null : "rotate(180)"; })
    .text(function(d) { return d.key; });
  
  }-*/;
	
}
