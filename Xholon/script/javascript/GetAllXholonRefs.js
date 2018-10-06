/*
 * Use JavaScript to return all references from this node to other Xholon nodes.
 * TODO construct a JavaScript Object or Array, or a JSON object/String
 *  - include the same fields as in PortInformation
 * Options:
 * - return as the IXholon JavaScript object
 * - return as an XPath expression
 * - return as the IXholon name()
 */
var attrs = function(node) {
  var $wnd = $wnd || window;
  var outArr = [];
  var obj = {};
  obj.fieldName = "this";
  obj.fieldNameIndex = -1;
  obj.fieldNameIndexStr = "-1";
  obj.reffedNode = node;
  obj.xpathExpression = "./";
  outArr.push(obj);
  //console.log("Properties of " + node);
  for (var prop in node) {
    var pname = prop;
    if (pname == null) {continue;}
    if (pname == "constructor") {continue;}
    var pval = node[pname];
    if (pval == null) {continue;}
    if (typeof pval == "object") {
      if (Array.isArray(pval)) {
        // this may be an array of ports
        //console.log("\n" + pname + ": " + pval.length);
        for (var i = 0; i < pval.length; i++) {
          if (pval[i] && $wnd.xh.isXholonNode(pval[i])) {
            if (pval[i] == node.port(i)) {
              pname = "port";
            }
            //console.log(pname + i + ":");
            //console.log(pval[i]);
            var obj = {};
            obj.fieldName = pname;
            obj.fieldNameIndex = i;
            obj.fieldNameIndexStr = "" + i;
            obj.reffedNode = pval[i];
            obj.xpathExpression = $wnd.xh.xpathExpr(pval[i], $wnd.xh.root());
            if (!obj.xpathExpression) {
              obj.xpathExpression = $wnd.xh.xpathExpr(pval[i], $wnd.xh.app());
            }
            outArr.push(obj);
          }
        }
      }
      else {
        if (!$wnd.xh.isXholonNode(pval)) {continue;}
        if (pval == node.parent()) {pname = "parent";}
        else if (pval == node.first()) {pname = "first";}
        else if (pval == node.next()) {pname = "next";}
        else if (pval == node.xhc()) {pname = "xhc";}
        else if (pval == $wnd.xh.app()) {pname = "app";}
        //console.log("\n" + pname + ":");
        //console.log(pval);
        var obj = {};
        obj.fieldName = pname;
        obj.fieldNameIndex = -1;
        obj.fieldNameIndexStr = "-1";
        obj.reffedNode = pval;
        obj.xpathExpression = $wnd.xh.xpathExpr(pval, $wnd.xh.root());
        if (!obj.xpathExpression) {
          obj.xpathExpression = $wnd.xh.xpathExpr(pval[i], $wnd.xh.app());
        }
        outArr.push(obj);
      }
    }
  }
  return outArr;
};
