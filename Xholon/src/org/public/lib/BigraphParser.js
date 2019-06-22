/**
 * Bigraph Parser.
 * BigraphParser.js
 * Ken Webb  June 20, 2019
 * MIT License, Copyright (C) 2019 Ken Webb
*/

if (typeof xh == "undefined") {
  xh = {};
}

if (typeof xh.bigraph == "undefined") {
  xh.bigraph = {};
}

(function($wnd, $doc) {

$wnd.xh.DefaultContent = {}

$wnd.xh.DefaultContent.BigraphParser = `

const DEFAULT_SITES_SYMBOL = "m";
const DEFAULT_INNERNAMES_SYMBOL = "X";
const DEFAULT_ROOTS_SYMBOL = "n";
const DEFAULT_OUTERNAMES_SYMBOL = "Y";

var me, spec, nodeDict, edgeDict, portDict, sitesSymbol, innerNamesSymbol, rootsSymbol, outerNamesSymbol, bginterface, beh = {

postConfigure: function() {
  me = this.cnode.parent();
  //me.println(me.name());
  spec = me.first().text().trim();
  nodeDict = {};
  edgeDict = {};
  portDict = {};
  // default values of symbols
  sitesSymbol = DEFAULT_SITES_SYMBOL;
  innerNamesSymbol = DEFAULT_INNERNAMES_SYMBOL;
  rootsSymbol = DEFAULT_ROOTS_SYMBOL;
  outerNamesSymbol = DEFAULT_OUTERNAMES_SYMBOL;
  bginterface = {};
  me.first().remove();
  this.parseSpec(spec);
  this.cnode.remove();
  if (!me.first()) {
    //me.remove();
  }
},

parseSpec: function(spec) {
  const arr = spec.split("\\n"); // there must be 2 back-slashes here
  for (var i = 0; i < arr.length; i++) {
    const line = arr[i].trim();
    const larr = line.split("=");
    const larr0 = larr[0].trim();
    const larr1 = (larr.length == 1) ? "" : larr[1].substring(0, larr[1].length - (larr[1].endsWith(",") ? 1 : 0)).trim(); // remove trailing "," if it exists
    //me.println("" + (i+1) + " " + larr0 + " = " + larr1);
    switch (larr0) {
    case "B":
      this.parseBigraph(larr1);
      break;
    case "Σ":
      this.parseSignature(larr1);
      break;
    case "VB":
      this.parseVB(larr1);
      break;
    case "EB":
      this.parseEB(larr1);
      break;
    case "PB":
      this.parsePB(larr1);
      break;
    case "ctrlB":
      this.parseCtrlB(larr1);
      break;
    case "prntB":
      this.parsePrntB(larr1);
      break;
    case "linkB":
      this.parseLinkB(larr1);
      break;
    default:
      //me.println("DEFAULT: " + line);
      if (line.length == 0) {
        // this is a blank line
      }
      else if (line.startsWith("#")) {
        me.println("COMMENT: " + line.substring(1));
      }
      else if (larr1.startsWith("{")) {
        // this is a custom (not yet part of the Bigraph specification B)
        me.println("CUSTOM: " + larr0 + "|" + larr1);
      }
      else {
        // m=0,X=∅,n=1,Y={c,co,t}
        const line2 = line.substring(0, line.length - (line.endsWith(",") ? 1 : 0)).trim(); // remove trailing "," if it exists
        const fixedLine = this.preParseInterface(line2);
        this.parseInterface(fixedLine);
      }
      break;
    }
  }
},

// (VB,EB,PB,ctrlB,prntB,linkB):〈m,X〉→〈n,Y〉
parseBigraph: function(str) {
  const strArr = str.split(":");
  const strArr2 = strArr[1].substring(1,strArr[1].length-1).split("〉→〈");
  const innerInterface = strArr2[0].split(",");
  const outerInterface = strArr2[1].split(",");
  //me.println(innerInterface + " " + outerInterface);
  sitesSymbol = innerInterface[0]; // "m"
  innerNamesSymbol = innerInterface[1]; // "X"
  rootsSymbol = outerInterface[0]; // "n"
  outerNamesSymbol = outerInterface[1]; // "Y"
  //me.println(sitesSymbol + " " + innerNamesSymbol + " " + rootsSymbol + " " + outerNamesSymbol);
},

/**
 * @param str example: m=0,X=∅,n=1,Y={c,co,t}
 * @return example: m=0|X=∅|n=1|Y={c,co,t}
 */
preParseInterface: function(str) {
  // generate 5 segments  example: m 0,X ∅,n 1,Y {c,co,t}
  const segmentArr = str.split("=");
  const seg1 = segmentArr[1].trim();
  const seg2 = segmentArr[2].trim();
  const seg3 = segmentArr[3].trim();
  const seg1len = seg1.length;
  const seg2len = seg2.length;
  const seg3len = seg3.length;
  // fix segments 1 2 3
  const segment0 = segmentArr[0].trim();
  const segment1 = seg1.substring(0,seg1len-2) + "|" + seg1.substring(seg1len-1);
  const segment2 = seg2.substring(0,seg2len-2) + "|" + seg2.substring(seg2len-1);
  const segment3 = seg3.substring(0,seg3len-2) + "|" + seg3.substring(seg3len-1);
  const segment4 = segmentArr[4].trim();
  return segment0 + "=" + segment1 + "=" + segment2 + "=" + segment3 + "=" + segment4;
},

// m=0,X=∅,n=1,Y={c,co,t}
parseInterface: function(str) {
  const strArr = str.split("|");
  for (var i = 0; i < strArr.length; i++) {
    let itemArr = strArr[i].split("=");
    switch (itemArr[0]) {
    case sitesSymbol: bginterface[sitesSymbol] = itemArr[1]; break;
    case innerNamesSymbol:
      // this might be ∅ or the first part of {a,b,c}
      bginterface[innerNamesSymbol] = itemArr[1];
      break;
    case rootsSymbol: bginterface[rootsSymbol] = itemArr[1]; break;
    case outerNamesSymbol:
      // this might be ∅ or the first part of {a,b,c}
      bginterface[outerNamesSymbol] = itemArr[1];
      break;
    default: me.println("unknown " + strArr[i]); break;
    }
  }
  //me.println(JSON.stringify(bginterface));
},

// ({get,send,sum},{(get,1),(send,1),(sum,0)})
parseSignature: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("},{");
  const ctrlSet = strArr[0].trim();
  const portAritySet = strArr[1].trim();
  const ctrlSetArr = ctrlSet.split(",");
  const portAritySetArr = portAritySet.substring(1,portAritySet.length-1 ).split("),(");
  
  // add nodes to Inheritance Hierarchy
  const xhcRoot = $wnd.xh.root().xhc().parent();
  let xhcStr = "<_-.ih>";
  for (var i = 0; i < ctrlSetArr.length; i++) {
    xhcStr += "<" + ctrlSetArr[i] + "/>";
  }
  xhcStr += "</_-.ih>";
  xhcRoot.append(xhcStr);
  
  // add ports to Inheritance Hierarchy class details
  let xhcCdStr = "<xholonClassDetails>";
  for (var j = 0; j < portAritySetArr.length; j++) {
    let portArityPair = portAritySetArr[j].split(",");
    // ignore if arity is 0
    let xhcName = portArityPair[0];
    let portCount = portArityPair[1];
    if (portCount > 0) {
      xhcCdStr += "<" + xhcName + ' xhType="XhtypePureActiveObject">';
      for (var k = 0; k < portCount; k++) {
        xhcCdStr += '<port name="port" index="' + k + '" connector="null"></port>';
      }
      xhcCdStr += "</" + xhcName + ">";
    }
  }
  xhcCdStr += "</xholonClassDetails>";
  xhcRoot.append(xhcCdStr);
},

// {a,b,d,e,f,g,h,i,j,k,l}
parseVB: function(str) {
  if (str == "∅") {
    return; // the set of vertices = the empty set
  }
  const str2 = str.substring(1,str.length-1);
  const strArr = str2.split(",");
  for (var i = 0; i < strArr.length; i++) {
    let node = strArr[i]; // ex: "a"
    nodeDict[node] = null;
  }
},

// {q,r,s}
parseEB: function(str) {
  if (str == "∅") {
    return; // the set of edges = the empty set
  }
  const str2 = str.substring(1,str.length-1);
  const strArr = str2.split(",");
  for (var i = 0; i < strArr.length; i++) {
    let edge = strArr[i]; // ex: "q"
    edgeDict[edge] = null;
  }
},

// {p(b,1),p(e,1),p(g,1),p(i,1),p(j,1),p(l,1)}
parsePB: function(str) {
  if (str == "∅") {
    return; // the set of ports = the empty set
  }
  const str2 = str.substring(3,str.length-2);
  const strArr = str2.split("),p(");
  for (var i = 0; i < strArr.length; i++) {
    let port = strArr[i]; // ex: "b,1"
    portDict[port] = null;
  }
},

// {(a,sum),(b,send),(d,sum),(e,get),(f,sum),(g,get),(h,sum),(i,send),(j,get),(k,sum),(l,send)}
parseCtrlB: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("),(");
  const root = me; //me.parent();
  for (var i = 0; i < strArr.length; i++) {
    let pairArr = strArr[i].split(",");
    let node = pairArr[0]; // ex: "a"
    if (nodeDict[node] == null) {
      let ctrl = pairArr[1]; // ex: "sum"
      let xhStr = '<' + ctrl + ' roleName="' + node + '"/>';
      root.append(xhStr);
      nodeDict[node] = root.last();
    }
    else {
      // it might be undefined (it waas not an element in VB), or it might have a non-null value (it may be a duplicate on ctrlB)
      me.println("WARNING: control " + node + " is " + nodeDict[node]);
    }
  }
},

// {(a,0),(f,0),(b,a),(d,b),(e,d),(g,f),(h,g),(i,h),(j,f),(k,j),(l,k)}
parsePrntB: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("),(");
  const root = me; //me.parent();
  for (var i = 0; i < strArr.length; i++) {
    let pairArr = strArr[i].split(",");
    let nodeStr = pairArr[0]; // ex: "b"
    let prntStr = pairArr[1]; // ex: "a"
    let node = nodeDict[nodeStr];
    let prnt = root;
    if (prntStr != "0") {
      prnt = nodeDict[prntStr];
    }
    if (node == null) {me.println("WARNING: " + nodeStr + " is null");}
    else if (prnt == null) {me.println("WARNING: " + nodeStr + " is null");}
    else {
      prnt.append(node.remove())
    }
  }  
},

// {(p(b,1),c),(p(e,1),co),(p(g,1),c),(p(i,1),co),(p(j,1),c),(p(l,1),t)}
parseLinkB: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("),(");
  for (var i = 0; i < strArr.length; i++) {
    let pairArr = strArr[i].split(",");
    let portStr = pairArr[0] + "," + pairArr[1]; // ex: "p(b,1)"
    let portRefStr = pairArr[2]; // ex: "c"
    //me.println(portStr + " -> " + portRefStr);
    const sourceArr = portStr.substring(2, portStr.length-1).split(","); // ex: "b,1"
    const sourceStr = sourceArr[0]; // ex: "b"
    const sourcePortNum = sourceArr[1] - 1; // ex: "1"
    //me.println(sourceStr + "." + sourcePortNum + " -> " + portRefStr);
    // check if this port is defined in portDict
    if (portDict[sourceStr + "," + sourcePortNum] == null) {
      const srcNode = nodeDict[sourceStr];
      const trgNode = nodeDict[portRefStr];
      if (srcNode == null) {me.println("WARNING: " + sourceStr + " is null");}
      else if (trgNode == null) {me.println("WARNING: " + portRefStr + " is null");}
      else {
        //me.println(srcNode.name() + "(" + sourcePortNum + ") -> " + trgNode.name());
        srcNode.port(sourcePortNum, trgNode);
        portDict[sourceStr + "," + sourcePortNum] == trgNode;
      }
    }
    else {
      me.println("WARNING: port " + sourceStr + "," + sourcePortNum + " is " + portDict[sourceStr + "," + sourcePortNum]);
    }
  }  
}

}
//# sourceURL=BigraphParser.js
`;

})(window, document); // end (function()

