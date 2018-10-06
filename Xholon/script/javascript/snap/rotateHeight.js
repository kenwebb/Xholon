<SvgViewBrowserbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
// Test writing a script that's a child of SvgViewBrowser.
// This is intended to work with Chameleon, and a simple XholonWorkbook with an SVG image.
// Every time step, it will rotate the Height rect.
var svgview, svg, height;
var beh = {
  postConfigure: function() {
    svgview = this.cnode.parent();
    svg = $wnd.Snap(svgview.obj());
    height = svg.select("g g rect");
    height.attr("transform", "rotate(0)");
  },
  act: function() {
    // rotate one degree about the center of the Height node
    height.transform(height.transform().localMatrix.rotate(1, 85, 25).toTransformString());
  },
  toString: function() {
    return "svg: " + svg + " height: " + height;
  }
}
]]></SvgViewBrowserbehavior>
