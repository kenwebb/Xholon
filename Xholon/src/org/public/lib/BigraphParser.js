/**
 * Bigraph Parser.
 * BigraphParser.js
 * Ken Webb  June 20, 2019
 * MIT License, Copyright (C) 2019 Ken Webb
 
 * TODO
 * - handle lines that start with "$"; these are domain-specific and need to be handled in a custom way
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

var me, spec, nodeDict, edgeDict, portDict, sitesSymbol, innerNamesSymbol, rootsSymbol, outerNamesSymbol, bginterface, custom, beh = {

postConfigure: function() {
  me = this.cnode.parent();
  console.log("Starting BigraphParser for " + me.name() + " ...");
  //me.println(me.name());
  spec = me.first().text().trim();
  nodeDict = me.nodeDict || {};
  edgeDict = me.edgeDict || {};
  portDict = me.portDict || {};
  bginterface = me.bginterface || {};
  // default values of symbols
  sitesSymbol = DEFAULT_SITES_SYMBOL;
  innerNamesSymbol = DEFAULT_INNERNAMES_SYMBOL;
  rootsSymbol = DEFAULT_ROOTS_SYMBOL;
  outerNamesSymbol = DEFAULT_OUTERNAMES_SYMBOL;
  bginterface = {};
  custom = {};
  me.first().remove();
  this.parseSpec(spec);
  this.cnode.remove();
  if (!me.first()) {
    //me.remove();
  }
  if (me["savedata"] == "true") {
    me.nodeDict = nodeDict;
    me.edgeDict = edgeDict;
    me.portDict = portDict;
    me.bginterface = bginterface;
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
    case "Σ_implName":
      this.parseImplName(larr1);
      break;
    case "Σ_xhType":
      this.parseXhType(larr1);
      break;
    //case "Σ_config":
    //  this.parseConfig(larr1);
    //  break;
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
    // IDecoration specifications
    case "_Color":
      this.parseDecoration(larr1, "Color");
      break;
    case "_Opacity":
      this.parseDecoration(larr1, "Opacity");
      break;
    case "_Font":
      this.parseDecoration(larr1, "Font");
      break;
    case "_Icon":
      this.parseDecoration(larr1, "Icon");
      break;
    case "_Symbol":
      this.parseDecoration(larr1, "Symbol");
      break;
    case "_Anno":
      this.parseDecoration(larr1, "Anno");
      break;
    case "_GridGenerator":
      this.parseGridGenerator(larr1);
      break;
    case "_AvatarKeyMap":
      this.parseAvatarKeyMap(larr1);
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
      else if (larr0.startsWith("_")) {
        me.println("CUSTOM_: " + larr0 + "|" + larr1);
        custom[larr0] = larr1;
      }
      else if (larr0.startsWith("$")) {
        me.println("CUSTOM$: " + larr0 + "|" + larr1);
        custom[larr0] = larr1;
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

// Σ_implName=({org.primordion.xholon.base.GridEntity},{(Space,org.primordion.xholon.base.GridEntity),...}),
parseImplName: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("},{");
  const ctrlSet = strArr[0].trim();
  const implNameSet = strArr[1].trim();
  const ctrlSetArr = ctrlSet.split(",");
  const implNameSetArr = implNameSet.substring(1,implNameSet.length-1 ).split("),(");
  const xhcRoot = $wnd.xh.root().xhc().parent();
  
  // add implNames to Inheritance Hierarchy class details
  let xhcCdStr = "<xholonClassDetails>";
  for (var j = 0; j < implNameSetArr.length; j++) {
    let implNamePair = implNameSetArr[j].split(",");
    let xhcName = implNamePair[0];
    let implName = implNamePair[1];
    if (implName != null) {
      xhcCdStr += "<" + xhcName + ' implName="' + implName + '">';
      xhcCdStr += "</" + xhcName + ">";
    }
  }
  xhcCdStr += "</xholonClassDetails>";
  xhcRoot.append(xhcCdStr);
},

// Σ_xhType=({XhtypeGridEntity,XhtypeGridEntityActivePassive},{(Space,XhtypeGridEntity),...}),
parseXhType: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("},{");
  const ctrlSet = strArr[0].trim();
  const xhTypeSet = strArr[1].trim();
  const ctrlSetArr = ctrlSet.split(",");
  const xhTypeSetArr = xhTypeSet.substring(1,xhTypeSet.length-1 ).split("),(");
  const xhcRoot = $wnd.xh.root().xhc().parent();
  
  // add xhTypes to Inheritance Hierarchy class details
  let xhcCdStr = "<xholonClassDetails>";
  for (var j = 0; j < xhTypeSetArr.length; j++) {
    let xhTypePair = xhTypeSetArr[j].split(",");
    let xhcName = xhTypePair[0];
    let xhType = xhTypePair[1];
    if (xhType != null) {
      xhcCdStr += "<" + xhcName + ' xhType="' + xhType + '">';
      xhcCdStr += "</" + xhcName + ">";
    }
  }
  xhcCdStr += "</xholonClassDetails>";
  xhcRoot.append(xhcCdStr);
},

// Σ_config=({Gmt,Gvt},{(instruction,Gvt)}),  NO
// Σ_config=({Gmt,Gvt},{(OceanCell,Gvt)}),
// example result: <OceanCell><config instruction="Gvt"></config></OceanCell>
parseConfig: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("},{");
  const ctrlSet = strArr[0].trim();
  const configSet = strArr[1].trim();
  const ctrlSetArr = ctrlSet.split(",");
  const configSetArr = configSet.substring(1,configSet.length-1 ).split("),(");
  const xhcRoot = $wnd.xh.root().xhc().parent();
  const attrName = "instruction";
  
  // add config to Inheritance Hierarchy class details
  let xhcCdStr = "<xholonClassDetails>";
  for (var j = 0; j < configSetArr.length; j++) {
    let configPair = configSetArr[j].split(",");
    //let attrName = configPair[0];
    let xhcName = configPair[0];
    let attrValue = configPair[1];
    if (attrValue != null) {
      xhcCdStr += '<' + xhcName + '><' + 'config ' + attrName + '="' + attrValue + '">';
      xhcCdStr += '</' + 'config' + '></' + xhcName + '>';
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
    let nodeStr = strArr[i].trim(); // ex: "a"
    nodeDict[nodeStr] = null;
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
    let edgeStr = strArr[i].trim(); // ex: "q"
    edgeDict[edgeStr] = null;
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
    let portStr = strArr[i].trim(); // ex: "b,1"
    portDict[portStr] = null;
  }
},

// {(a,sum),(b,send),(d,sum),(e,get),(f,sum),(g,get),(h,sum),(i,send),(j,get),(k,sum),(l,send)}
parseCtrlB: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("),(");
  const root = me; //me.parent();
  for (var i = 0; i < strArr.length; i++) {
    let pairArr = strArr[i].split(",");
    let nodeStr = pairArr[0]; // ex: "a"
    if (nodeDict[nodeStr] == null) {
      let ctrl = pairArr[1]; // ex: "sum"
      let xhStr = '<' + ctrl + ' roleName="' + nodeStr + '"/>';
      root.append(xhStr);
      nodeDict[nodeStr] = root.last();
    }
    else {
      // it might be undefined (it waas not an element in VB), or it might have a non-null value (it may be a duplicate on ctrlB)
      me.println("WARNING: control " + nodeStr + " is " + nodeDict[nodeStr]);
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
},

// ({red,orange,yellow,green,blue,purple},{(User,red),(Role,orange)})
parseDecoration: function(str, decoType) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("},{");
  const decoSet = strArr[0].trim(); // "red,orange,yellow,green,blue,purple"
  const xhcDecoSet = strArr[1].trim(); // {(User,red),(Role,orange)}
  const decoSetArr = decoSet.split(",");
  const xhcDecoSetArr = xhcDecoSet.substring(1,xhcDecoSet.length-1 ).split("),(");
  const xhcRoot = $wnd.xh.root().xhc().parent();
  // add decorations to Inheritance Hierarchy class details
  let xhcCdStr = "<xholonClassDetails>";
  for (var j = 0; j < xhcDecoSetArr.length; j++) {
    let xhcDecoPair = xhcDecoSetArr[j].split(",");
    let xhcName = xhcDecoPair[0];
    let decoName = xhcDecoPair[1];
    if ((decoName.length > 0) && (decoSetArr.includes(decoName))) {
      xhcCdStr += "<" + xhcName + ">";
      xhcCdStr += "<" + decoType + ">" + decoName + "</" + decoType + ">";
      xhcCdStr += "</" + xhcName + ">";
    }
  }
  xhcCdStr += "</xholonClassDetails>";
  xhcRoot.append(xhcCdStr);
},

/**
 * <GridGenerator rows="80" cols="120" gridType="Gvt" names="Space,FieldRow,OceanCell" columnColor="171c8f" gridViewerParams="IslandSystem/Space,7,Island Viewer,true" cellsCanSupplyOwnColor="true"/>
 * 
 * _rows=80,
 * _cols=120,
 * _gridType=Gvt,
 * _names=(Space,FieldRow,OceanCell),
 * _columnColor=171c8f,
 * _gridViewerParams=(IslandSystem/Space,7,Island Viewer,true),
 * _cellsCanSupplyOwnColor=true,
 * _GridGenerator=(_rows,_cols,_gridType,_names,_columnColor,_gridViewerParams,_cellsCanSupplyOwnColor),
 */
parseGridGenerator: function(str) {
  const ggArr = str.substring(1, str.length-1).split(",");
  me.println(ggArr);
  let ggStr = '<GridGenerator';
  for (var i = 0; i < ggArr.length; i++) {
    let item = ggArr[i].trim();
    let itemValue = custom[item];
    if (itemValue) {
      let attrName = item.substring(1);
      let attrValue = itemValue.startsWith("(") ? itemValue.substring(1, itemValue.length-1) : itemValue;
      ggStr += ' ' + attrName + '="' + attrValue + '"';
    }
  }
  ggStr += '/>';
  me.println(ggStr);
  me.append(ggStr);
},

/**
 * _AvatarKeyMap={(UP,go link0),(RIGHT,go link1),(DOWN,go link2),(LEFT,go link3)},
 * 
let akm = JSON.parse(xh.avatarKeyMap());
akm["UP"]    = "go link0";
akm["RIGHT"] = "go link1";
akm["DOWN"]  = "go link2";
akm["LEFT"]  = "go link3";
xh.avatarKeyMap(JSON.stringify(akm));
 * 
 */
parseAvatarKeyMap: function(str) {
  const str2 = str.substring(2,str.length-2);
  const strArr = str2.split("),(");
  const akm = JSON.parse($wnd.xh.avatarKeyMap());
  for (var i = 0; i < strArr.length; i++) {
    let akmPairArr = strArr[i].split(",");
    akm[akmPairArr[0]] = akmPairArr[1];
  }
  $wnd.xh.avatarKeyMap(JSON.stringify(akm));
}

}
//# sourceURL=BigraphParser.js
`;

})(window, document); // end (function()

