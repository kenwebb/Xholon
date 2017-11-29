'use strict'; // ???

/**
 * Operad Builder.
 * xhOperadBuilder.js
 *
 * Copyright (c) Ken Webb 2017.
 * 
 * see Xholon workbook: Operads - Domain Specific Language, and Parsing
 *     gist: 063bc93c7c5deb1a16c75d84b2170a58
 * 
 * see Xholon workbook: Operad Builder - example 1
 * 
 * license MIT
 * 
 * usage
 * xh.require("xhOperadBuilder");
 * xh.OperadBuilder
 * 
 * Example:
  <!-- Example Operad -->
  <OperadExample roleName="Example Operad">
    <Annotation>Example Operad</Annotation>
    <OperadBuilder>
    <Attribute_String><![CDATA[
# Example Operad
# 
pack P1 = {a1,b1,f1}
pack P2 = {b2,c2,d2,e2}
pack P3 = {e3,f3}
pack Q  = {ddd, ggg}
pack R  = {aR, fR, gR}
# 
bindings # dummy bindings
morphism comp: (P1, P2, P3, Q) -> R := {a1=aR, b1=b2, c2, d2=ddd, e2=e3, f1=f3=fR, ggg=gR}
  ]]></Attribute_String>
    </OperadBuilder>
  </OperadExample>
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

xh.OperadBuilder = function(me, dslContent) {

//var me;
var xhRoot,
    xmlStrIH,
    xmlStrCD,
    xmlStrCSH,
    commentToken,
    portNameFiller,
    nextCableName,
    removeIntermediates,
    removeUnusedCables,
    portsObj;

var beh = {
postConfigure: function() {
  "use strict";
  //me = this.cnode.parent();
  window.console.log("\n" + me);
  if (me == null) {return;}
  window.console.log("\n" + me.parent().name());
  // get xhRoot
  xhRoot = me.xpath("ancestor::OperadExample");
  if (xhRoot == null) {return;}
  // get xhcRoot
  var xhcRoot = xhRoot.xhc().parent();
  if (typeof dslContent == "undefined") {
    // get the DSL content
    var dslContentNode = me.xpath("./Attribute_String");
    if (dslContentNode == null) {return;}
    dslContent = dslContentNode.text();
    //me.println(dslContent);
  }
  
  var bindingsFound = false; // has the first "bindings" statement been found
  
  portsObj = {}; // dictionary of port names, where each port name points to a non-null cable node
  removeUnusedCables = true; // can set to false for debugging
  removeIntermediates = true; // can set to false for debugging
  nextCableName = 1; // for use in makeCablesAndBindings()
  portNameFiller = "\uFEFF";
  commentToken = "#";
  xmlStrIH = "<_-.XholonClass>\n"
    + "<Pack></Pack>\n"
    + "<Cable></Cable>\n"
    + "</_-.XholonClass>\n";
  xhcRoot.append(xmlStrIH);
  xmlStrCD = "<xholonClassDetails>\n"
    + "  <Pack xhType=\"XhtypePureActiveObject\"><Color>orange</Color></Pack>\n"
    + "  <Cable><Color>indigo</Color></Cable>\n"
    + "  <Avatar><Color>rgba(220,20,60,0.5)</Color></Avatar>\n"
    + "</xholonClassDetails>\n";
  xhcRoot.append(xmlStrCD);
  xmlStrCSH = "<_-.operadCSH>\n";
  var firstPack = null;
  var dslLineArr = dslContent.split("\n", 1000);
  for (var i = 0; i < dslLineArr.length; i++) {
    var dslLine = dslLineArr[i].trim();
    var commentTokenIndex = dslLine.indexOf(commentToken);
    if (commentTokenIndex != -1) {
      dslLine = dslLine.substring(0, commentTokenIndex).trim();
    }
    if (dslLine.length == 0) {continue;}
    me.println(dslLine);
    dslLine = dslLine.replace(/'/g, "′"); // replace the apostrophe (single quote) with the unicode prime character  U+2032	′
    // str = str.replace(/abc/g, '');
    //var dslTokenArr = dslLine.split(" ", 2);
    var ixSpace1 = dslLine.indexOf(" ");
    var dslStatementType = null;
    if (ixSpace1 == -1) {
      dslStatementType = dslLine;
    }
    else {
      dslStatementType = dslLine.substring(0, ixSpace1);
    }
    switch (dslStatementType) {
    case "node":
    case "pack":
      // pack P' = {x', v', z'}
      // pack P1 = {x1, y1}
      this.makePack(dslLine.substring(ixSpace1+1));
      break;
    case "cables":
      // cables C = {1, 2, 3, 4, 5, 6}
      // cables L = {X, Y, E, D, B, A}
      // cables L = {u,v,w,x,y, z}
      this.makeCables(dslLine.substring(ixSpace1+1));
      break;
    case "morphism":
      // morphism (R1, R2, R3) -> R'
      this.makeMorphism(dslLine.substring(ixSpace1+1));
      break;
    case "bindings":
      // bindings u2,u3 |-> u
      if (!bindingsFound) {
        xmlStrCSH += "</_-.operadCSH>\n";
        me.println(xmlStrCSH);
        xhRoot.append(xmlStrCSH);
        bindingsFound = true;
        xmlStrCSH = null;
        firstPack = me.xpath("../Pack");
      }
      this.makeBindings(dslLine.substring(ixSpace1+1), firstPack);
      break;
    //case commentToken: break;
    default: break;
    }
  }
  //xmlStrCSH += "</_-.operadCSH>\n";
  //me.println(xmlStrCSH);
  //xhRoot.append(xmlStrCSH);
  this.removeUnusedCables(me.xpath("../Pack")); // pass in the root pack
  for (var prop in portsObj) {window.console.log(prop + " " + portsObj[prop].name())};
  me.remove();
},

/*
P' = {x', v', z'}
P1 = {x1, y1}
*/
makePack: function(packStr) {
  "use strict";
  me.println("pack: " + packStr);
  var equalsIx = packStr.indexOf("=");
  if (equalsIx == -1) {return;}
  var startPortsIx = packStr.indexOf("{", equalsIx+1);
  if (startPortsIx == -1) {return;}
  var endPortsIx = packStr.indexOf("}", startPortsIx+1);
  if (endPortsIx == -1) {return;}
  var packName = packStr.substring(0, equalsIx).trim();
  xmlStrCSH += '  <Pack roleName="' + packName + '">\n';
  var portsStr = packStr.substring(startPortsIx+1, endPortsIx).trim();
  if (portsStr.length > 0) {
    var portsArr = portsStr.split(",");
    for (var i = 0; i < portsArr.length; i++) {
      var portName = portsArr[i].trim() + portNameFiller;
      //xmlStrCSH += '    <port name="' + portName + '" connector="../Cable[@roleName=' + "'" + 'TODO' + "'" + ']"></port>\n';
      xmlStrCSH += '    <port name="' + portName + '" connector="."></port>\n';
    }
  }
  xmlStrCSH += "  </Pack>\n";
},

/*
{a'=a1, b1=b2, c2=c', d2=d', e2=e3, f1=f3=f'}
*/
makeCablesAndBindings: function(str) {
  "use strict";
  me.println("cables and bindings: " + str);
  var startIx = str.indexOf("{");
  if (startIx == -1) {return;}
  var endIx = str.indexOf("}", startIx+1);
  if (endIx == -1) {return;}
  var cb = str.substring(startIx+1, endIx).trim();
  var cbArr = cb.split(",");
  for (var i = 0; i < cbArr.length; i++) {
    var cbStr = cbArr[i].trim(); // ex: a'=a1
    var firstPack = me.xpath("../Pack");
    var portArr = cbStr.split("=");
    var packNodesArr = [];
    var portNamesArr = [];
    var cableNode = null;
    for (var j = 0; j < portArr.length; j++) {
      var portName = portArr[j].trim() + portNameFiller; // ex: a'
      if (cableNode == null) {
        //cableNode = this.findCableReffedByNamedPort(me.parent(), portName);
        cableNode = portsObj[portName];
        //window.console.log("existingCable: " + portName + " : " + cableNode);
      }
      var packNode = firstPack;
      this.findPacksWithPortNameEqualNull(me.parent(), portName, packNodesArr, portNamesArr);
    } // end for j
    if (cableNode == null) {
      var xmlStr = '<Cable roleName="' + nextCableName + '"></Cable>';
      xhRoot.append(xmlStr);
      //nextCableName++;
      cableNode = xhRoot.last();
    }
    me.println("CABLE " + nextCableName + " " + cableNode + " " + packNodesArr.length);
    nextCableName++; // put this here temporarily
    for (var k = 0; k < packNodesArr.length; k++) {
      packNodesArr[k][portNamesArr[k]] = cableNode;
      if (!portsObj[portNamesArr[k]]) {
        portsObj[portNamesArr[k]] = cableNode;
      }
      else {
        //portsObj[portNamesArr[k]] = cableNode; // TODO try this; no effect
      }
    }
  } // end for i
},

/*
C = {1, 2, 3, 4, 5, 6}
L = {X, Y, E, D, B, A}
L = {u,v,w,x,y, z}
*/
makeCables: function(cablesStr) {
  "use strict";
  me.println("cables: " + cablesStr);
  var startCablesIx = cablesStr.indexOf("{");
  if (startCablesIx == -1) {return;}
  var endCablesIx = cablesStr.indexOf("}", startCablesIx+1);
  if (endCablesIx == -1) {return;}
  var cables = cablesStr.substring(startCablesIx+1, endCablesIx).trim();
  var cablesArr = cables.split(",");
  for (var i = 0; i < cablesArr.length; i++) {
    //var cableName = cablesArr[i].trim();
    //xmlStrCSH += '  <Cable roleName="' + cableName + '"></Cable>\n';
    this.makeCable(cablesArr[i].trim());
  }
},

makeCable: function(cableName) {
  xmlStrCSH += '  <Cable roleName="' + cableName + '"></Cable>\n';
},

/*
u2,u3 |-> u
*/
makeBindings: function(bindingsStr, firstPack) {
  "use strict";
  me.println("bindings: " + bindingsStr);
  if (!firstPack) {return;}
  var bindingsArr = bindingsStr.split("|->");
  if (bindingsArr.length != 2) {return;}
  var portList = bindingsArr[0].trim();
  var cableName = bindingsArr[1].trim();
  if (portList.length == 0) {return;}
  if (cableName.length == 0) {return;}
  //var opex = me.parent(); // OperadExample node
  var portArr = portList.split(",");
  for (var i = 0; i < portArr.length; i++) {
    var portName = portArr[i].trim() + portNameFiller;
    var packNode = firstPack;
    while (packNode && (packNode.xhc().name() == "Pack")) {
      //if (packNode[portName] === null) {
      if (typeof packNode[portName] !== 'undefined') {
        // the port has been created (!== undefined) and has a value of null
        var remoteNode = packNode.xpath("../Cable[@roleName='" + cableName + "']");
        if (!remoteNode) {
          // in some models, packs connect directly to other packs
          remoteNode = packNode.xpath("../Pack[@roleName='" + cableName + "']");
        }
        if (remoteNode) {
          packNode[portName] = remoteNode;
        }
      }
      packNode = packNode.next()
    }
  }
},

/*
(R1, R2, R3) -> R'
*/
makeMorphism: function(morphStr) {
  "use strict";
  me.println("morph: " + morphStr);
  var morphArr = morphStr.split("->");
  if (morphArr.length != 2) {return;}
  var packList = morphArr[0].trim();
  var parentPackName = morphArr[1].trim();
  if (packList.length == 0) {return;}
  if (parentPackName.length == 0) {return;}
  var part2Arr = parentPackName.split(":=");
  if (part2Arr.length == 2) {
    parentPackName = part2Arr[0].trim();
    this.makeCablesAndBindings(part2Arr[1].trim());
  }
  var parentNode = me.xpath("../Pack[@roleName='" + parentPackName + "']");
  if (!parentNode) {return;}
  var startIx = packList.indexOf("(");
  if (startIx == -1) {return;}
  var endIx = packList.indexOf(")", startIx+1);
  if (endIx == -1) {return;}
  var packs = packList.substring(startIx+1, endIx).trim();
  var packArr = packs.split(",");
  for (var i = 0; i < packArr.length; i++) {
    var packRoleName = packArr[i].trim();
    var packNode = me.xpath("../Pack[@roleName='" + packRoleName + "']");
    if (packNode) {
      if (removeIntermediates && packNode.first()) {
        // remove the intermediate node
        var childNode = packNode.first();
        while (childNode) {
          var nextChild = childNode.next();
          parentNode.append(childNode.remove());
          childNode = nextChild;
        }
        packNode.remove();
      }
      else {
        parentNode.append(packNode.remove());
      }
    }
  }
  // also put all the cables inside the new parent node; TODO ask if this is the correct thing to do
  var cable = parentNode.xpath("../Cable");
  while (cable && (cable.xhc().name() == "Cable")) {
    var nextCable = cable.next();
    parentNode.append(cable.remove());
    cable = nextCable;
  }
},

/*
Remove unused cables. These are cables that are not referenced by any Pack.
@param the root Pack
*/
removeUnusedCables: function(parentNode) {
  "use strict";
  if (!removeUnusedCables) {return;}
  if (parentNode == null) {return;}
  var node = parentNode.first(); // node may be a Pack or a Cable
  while (node) {
    var nextNode = node.next();
    if (node.xhc().name() == "Cable") {
      var packArr = this.findPacksThatRefCable(node);
      if (packArr.length == 0) {
        // there are no Packs that reference this Cable, so remove the Cable
        node.remove();
      }
    }
    node = nextNode;
  }
},

/*
Find all Pack nodes that reference (through one or more ports) a specified Cable node.
This function is used by morphism() to determine if a cable should be retained (should be appended to a new parent node).
TODO should this be recursive?
@param a Cable node
@returns an array of Pack nodes, or an empty array
*/
findPacksThatRefCable: function(cableNode) {
  "use strict";
  var packArr = [];
  if (cableNode == null) {return packArr;}
  var parentNode = cableNode.parent(); // first check the cable's parent node which should be a Pack
  var linksArr = parentNode.links(false, true);
  for (var i = 0; i < linksArr.length; i++) {
    var reffedNode = linksArr[i].reffedNode
    if (cableNode == reffedNode) {
      packArr.push(parentNode);
    }
  }
  var node = parentNode.first();
  while (node) {
    if (node.xhc().name() == "Pack") {
      var linksArr = node.links(false, true);
      for (var i = 0; i < linksArr.length; i++) {
        var reffedNode = linksArr[i].reffedNode;
        if (cableNode == reffedNode) {
          packArr.push(parentNode);
        }
      }
    }
    node = node.next()
  }
  return packArr;
},

/*
Find the Pack that has a specified port name, and return the Cable node referenced by that port.
Recursively search the entire subtree.
@param node a Pack or other node
@param portName
@returns a Cable node or null
*/
/*findCableReffedByNamedPort: function(node, portName) {
  if (node == null) {return null;}
  if (node.xhc().name() == "Cable") {return null;}
  if (node[portName]) {
    return node[portName];
  }
  var childNode = node.first();
  while (childNode) {
    var reffedNode = this.findCableReffedByNamedPort(childNode, portName);
    if (reffedNode) {
      return reffedNode;
    }
    childNode = childNode.next();
  }
  return null;
},*/

/*
Find Packs that already have a specified portName, where the portName's value is still null.
@param node a Pack or other node
@param portName 
@param packNodesArr an array of Pack nodes
@param portNamesArr an array of corresponding Port names (both arrays must remain the same size)
*/
findPacksWithPortNameEqualNull(node, portName, packNodesArr, portNamesArr) {
  "use strict";
  if (node == null) {return;}
  if (node.xhc().name() == "Cable") {return;}
  //if (node[portName] === null) {
  if (typeof node[portName] !== 'undefined') {
    // the port has been created (!== undefined) and has a value of null
    packNodesArr.push(node);
    portNamesArr.push(portName);
  }
  var childNode = node.first();
  while (childNode) {
    this.findPacksWithPortNameEqualNull(childNode, portName, packNodesArr, portNamesArr);
    childNode = childNode.next();
  }
}

} // end var beh = {

beh.postConfigure();

} // end xh.OperadBuilder = function(me) {
