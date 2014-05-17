package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model using D3 the Partition Layout.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 5, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2Partition extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	public Xholon2Partition() {}
	
	/**
	 * Create a D3 Partition Layout from the JSON data.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  var w = 960,
        h = 700,
        radius = Math.min(w, h) / 2,
        color = $wnd.d3.scale.category20c();

    var svg = $wnd.d3.select(selection).append("svg")
      .attr("width", w)
      .attr("height", h)
      .append("g")
      .attr("transform", "translate(" + w / 2 + "," + h * .52 + ")");

    var partition = $wnd.d3.layout.partition()
      .sort(null)
      .size([2 * Math.PI, radius * radius])
      .value(function(d) { return 1; });

    var arc = $wnd.d3.svg.arc()
      .startAngle(function(d) { return d.x; })
      .endAngle(function(d) { return d.x + d.dx; })
      .innerRadius(function(d) { return Math.sqrt(d.y); })
      .outerRadius(function(d) { return Math.sqrt(d.y + d.dy); });

    var path = svg.datum(json).selectAll("path")
      .data(partition.nodes)
      .enter()
      .append("path")
      .attr("display", function(d) { return d.depth ? null : "none"; }) // hide inner ring
      .attr("d", arc)
      .style("stroke", "#fff")
      .style("fill", function(d) { return color((d.children ? d : d.parent).name); })
      .style("fill-rule", "evenodd");
    
    $wnd.console.log(json);
    
    var tooltips= $wnd.d3.selectAll("path")
      .append("title")
      .classed("tooltip", true)
      .text(function(d) { return d.name });
    
	}-*/;
	
}
