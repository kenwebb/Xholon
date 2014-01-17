package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as D3 Treemap.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 5, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2Treemap extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2Treemap() {}
	
	/**
	 * Create a D3 Treemap from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  var margin = {top: 10, right: 10, bottom: 10, left: 10},
	      w = width - margin.left - margin.right,
        h = height - margin.top - margin.bottom,
        color = $wnd.d3.scale.category20c();
    
    var treemap = $wnd.d3.layout.treemap()
      .size([w, h])
      .sticky(true)
      .value(function(d) { return d.size; });
    
    var div = $wnd.d3.select(selection).append("div")
      .style("position", "relative")
      .style("width", (w + margin.left + margin.right) + "px")
      .style("height", (h + margin.top + margin.bottom) + "px")
      .style("left", margin.left + "px")
      .style("top", margin.top + "px");
    
    var treenode = div
      .datum(json)
      .selectAll(".node")
      .data(treemap.nodes)
      .enter()
      .append("div")
      .attr("class", "d3tmnode")
      .call(position)
      .style("background", function(d) { return d.children ? color(d.name) : null; })
      .attr("title", function(d) { return d.children ? null : d.name; })
      .text(function(d) { return d.children ? null : d.name; });
    
    function position() {
      this.style("left", function(d) { return d.x + "px"; })
          .style("top", function(d) { return d.y + "px"; })
          .style("width", function(d) { return Math.max(0, d.dx - 1) + "px"; })
          .style("height", function(d) { return Math.max(0, d.dy - 1) + "px"; });
    }
    
	}-*/;
	
}
