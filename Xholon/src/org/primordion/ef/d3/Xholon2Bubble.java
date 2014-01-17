package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as D3 bubble chart.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 5, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2Bubble extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2Bubble() {}
	
	/**
	 * Create a D3 Bubble Chart from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  var w = width,
    h = height,
    format = $wnd.d3.format(",d")
    color = $wnd.d3.scale.category20c();
    
    var bubble = $wnd.d3.layout.pack()
      .sort(null)
      .size([w, h])
      .padding(1.5);
    
    var svg = $wnd.d3.select(selection).append("svg")
      .attr("width", w)
      .attr("height", h)
      .attr("class", "bubble");
    
    var node = svg
      //.data([json])
      .selectAll(".node")
      .data(bubble.nodes(classes(json))
      .filter(function(d) { return !d.children; }))
      .enter()
      .append("g")
      .attr("class", "d3bubnode")
      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
    
    node.append("title")
      .text(function(d) {return d.className;});
    
    node.append("circle")
      .attr("r", function(d) {return d.r;})
      .style("fill", function(d) { return color(d.packageName); });
    
    node.append("text")
      .attr("dy", ".3em")
      .style("text-anchor", "middle")
      .text(function(d) { return d.className.substring(0, d.r / 3); });
    
    // Returns a flattened hierarchy containing all leaf nodes under the root.
    function classes(root) {
      var classes = [];

      function recurse(name, node) {
        if (node.children) node.children.forEach(function(child) { recurse(node.name, child); });
        else classes.push({packageName: name, className: node.name, value: node.size});
      }

      recurse(null, root);
      return {children: classes};
    }
    
	}-*/;
	
}
