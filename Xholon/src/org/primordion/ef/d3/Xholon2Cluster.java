package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as a D3 Cluster Dendrogram.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2Cluster extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2Cluster() {}
	
	/**
	 * Create a D3 cluster from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  var w = 974,
    h = height * 8;
    
    var cluster = $wnd.d3.layout.cluster()
      .size([w, h]);
    
    var diagonal = $wnd.d3.svg.diagonal()
      .projection(function(d) { return [d.y, d.x]; });
    
    var svg = $wnd.d3.select(selection).append("svg")
      .attr("width", "100%")
      .attr("height", h)
      .append("g")
      .attr("transform", "translate(20,0)");
    
    var nodes = cluster.nodes(json);
    // fix the vertical separation between nodes
    nodes.forEach(function(d) {
      if (d.depth < 4) {
        d.x = d.x * (1.0 - (d.depth * 0.15));
      }
      else {
        d.x = d.x * 0.55;
      }
      //d.x = d.x * 0.5;
    });

    var links = cluster.links(nodes);
    
    var link = svg.selectAll(".link")
      .data(links)
      .enter()
      .append("path")
      .attr("class", "d3clslink")
      .attr("d", diagonal);
    
    var node = svg.selectAll(".node")
      .data(nodes)
      .enter()
      .append("g")
      .attr("class", "d3clsnode")
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
    
    node.append("circle")
      .attr("r", 4.5);
    
    node.append("text")
      .attr("dx", function(d) {
        var offset = 8;
        if (d.children && (d != json)) {offset = -8;}
        return offset;
        //return d.children ? -8 : 8;
      })
      .attr("dy", 3)
      .style("text-anchor", function(d) {
        var anchor = "start";
        if (d.children && (d != json)) {anchor = "end";}
        return anchor;
       })
      .text(function(d) { return d.name; });
    
	}-*/;
	
}
