package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as a D3 Reingold–Tilford (radial) Tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 5, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2RadialTree extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2RadialTree() {}
	
	/**
	 * Create a D3 radial tree from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  var w = width * 8,
        h = height * 8;
    
    var tree = $wnd.d3.layout.tree()
      .size([w / 2, h / 2])
      .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth; });
    
    var diagonal = $wnd.d3.svg.diagonal.radial()
      .projection(function(d) { return [d.y, d.x / 180 * Math.PI]; });
    
    var svg = $wnd.d3.select(selection).append("svg")
      .attr("width", w)
      .attr("height", h)
      .append("g")
      .attr("transform", "translate(" + w / 2 + "," + h / 2 + ")");
    
    var nodes = tree.nodes(json);
    var links = tree.links(nodes);
    
    var link = svg.selectAll(".link")
      .data(links)
      .enter()
      .append("path")
      .attr("class", "d3treelink")
      .attr("d", diagonal);
    
    var node = svg.selectAll(".node")
      .data(nodes)
      .enter()
      .append("g")
      .attr("class", "d3treenode")
      .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; })
    
    node.append("circle")
      .attr("r", 4.5);
    
    node.append("text")
      .attr("dy", ".31em")
      .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
      .attr("transform", function(d) { return d.x < 180 ? "translate(8)" : "rotate(180)translate(-8)"; })
      .text(function(d) { return d.name; });
    
	}-*/;
	
}
