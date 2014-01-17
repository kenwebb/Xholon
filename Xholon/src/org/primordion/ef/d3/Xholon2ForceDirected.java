package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import org.primordion.xholon.base.IXholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as a D3 Force-Directed Graph.
 * TODO
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 7, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 * @see <a href="http://bl.ocks.org/mbostock/4062045">D3 example</a>
 */
public class Xholon2ForceDirected extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	protected String outFileName;
	protected String outPath = "./ef/d3/";
	protected String modelName;
	protected IXholon root;
	private StringBuilder sb;
	
	private boolean shouldShowStateMachineEntities = false;
	
	public Xholon2ForceDirected() {}
	
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
	
	public void writeAll() {
	  if (root == null) {return;}
		sb = new StringBuilder();
		
		writeNode(root, 0); // root is level 0
		String jsonStr = sb.toString();
		
		writeToTarget(jsonStr, outFileName, outPath, root);
		
		JavaScriptObject json = ((JSONObject)JSONParser.parseLenient(jsonStr)).getJavaScriptObject();
		int width = 500;
		int height = 500;
		createD3(json, width, height, "#xhgraph");
	}
	
	protected void writeNode(IXholon node, int level) {
	  // TODO
	  sb.append("{}");
	}
	
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  // TODO
	  $wnd.alert("D3 Force-Directed Diagram not yet implemented");
	  return;
	  
	  var width = 960,
    height = 500;

    var color = d3.scale.category20();

    var force = d3.layout.force()
        .charge(-120)
        .linkDistance(30)
        .size([width, height]);

    var svg = d3.select(selection).append("svg")
        .attr("width", width)
        .attr("height", height);

    //d3.json("miserables.json", function(error, graph) {
      force
          .nodes(json.nodes)
          .links(json.links)
          .start();

      var link = svg.selectAll(".link")
          .data(json.links)
        .enter().append("line")
          .attr("class", "link")
          .style("stroke-width", function(d) { return Math.sqrt(d.value); });

      var node = svg.selectAll(".node")
          .data(json.nodes)
        .enter().append("circle")
          .attr("class", "node")
          .attr("r", 5)
          .style("fill", function(d) { return color(d.group); })
          .call(force.drag);

      node.append("title")
          .text(function(d) { return d.name; });

      force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });

        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });
      });
    //});
	}-*/;
	
}
