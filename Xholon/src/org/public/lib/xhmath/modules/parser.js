/**
 * parser.js
 * (C) Ken Webb, MIT license
 * December 7, 2019
 * 
 * Parse Math notation.
 * @see http://127.0.0.1:8888/wb/editwb.html?app=Saunders+Mac+Lane+-+Mathematics+Form+and+Function&src=lstr
 * 
 * TODO:
 * - add support for lib/BigraphParser.js
 * - add support for Operad DSL
 * - add support for any other Math DSL code I have developed
 * - 
 */

// tokens
const LPAREN = "(";
const RPAREN = ")";
const LBRACE = "{";
const RBRACE = "}";
const LBRACK = "[";
const RBRACK = "]";
const LANGLE = "&#x27e8;"; // ⟨
const RANGLE = "&#x27e9;"; // ⟩
const COMMA = ",";
const SEMICOLON = ";";
const INT = "[0-9]";
const LETTER = "[A-Z][a-z]";
const ELLIPSIS = "…"; // &#x2026; &hellip; …
const ELLIPSIS2 = "...";
const COMMENT = "#";
const NEWLINE = "\n";
const EQUALS = "=";

const SEP = COMMA;
const FOREST = "_-.xhforest";
const NOT_YET_IMPLEMENTED = "<Attribute_String>not yet implemented</Attribute_String>";
const DEFAULT_XHC_NAME = "MathObject"; // "XholonNull" "MathObject" the XholonClass name to use when none is explicitly provided
const DEFAULT_ROLE_NAME = null;
const OBJECT_PORTS = "ports";
const MAX_ELLIPSIS_EXPANSION = 1000;

// singular, plural, XML tagname
const DECORATIONS = {
color: ["color","colors","Color"],
opacity: ["opacity","opacities","Opacity"],
font: ["font","fonts","Font"],
icon: ["icon","icons","Icon"],
toolTip: ["toolTip","toolTips","ToolTip"],
symbol: ["symbol","symbols","Symbol"],
format: ["format","formats","Format"],
anno: ["anno","annos","Anno"],
// other Xholon node attributes
geo: ["geo","geos","Geo"],
sound: ["sound","sounds","Sound"]
};
const DEC_INDEX_SINGULAR = 0; // ex: "color"
const DEC_INDEX_PLURAL   = 1; // ex: "colors"
const DEC_INDEX_TAGNAME  = 2; // ex: "Color"

/**
 * Parse a Math Set.
 * The elements in the input Set are types (names of XholonClass nodes).
 * 
 * example:
var node = xh.root();
const xmlStr = xh.xhmath.pSet01("{One,Two,Three,Four}");
node.println(xmlStr);
node.append(xmlStr);
 * 
 * example result:
"<_-.xhforest><One/><Two/><Three/><Four/></_-.xhforest>"
 * pretty printed:
<_-.xhforest>
  <One/>
  <Two/>
  <Three/>
  <Four/>
</_-.xhforest>
 * 
 * locations = {MarkedLocation, UnmarkedLocation, Lookout, Building, Entrance}
 * 
 * @param mstr a Math notation string
 * @return an XML string
 */
function pSet01(mstr) {
  if (!mstr || mstr.length == 0) {return null;}
  const str = mstr.trim();
  const arr = str.substring(1, str.length-1).split(SEP);
  let xmlStr = "<" + FOREST + ">";
  arr.forEach(function(item) {
    const itemt = item.trim();
    if (itemt.length > 0) {
      xmlStr += "<" + itemt + "/>";
    }
  });
  xmlStr += "</" + FOREST + ">";
  return xmlStr;
}

/**
 * Parse a Math Set.
 * The elements in the input Set are names (Xholon roleNames).
 * 
 * const xmlStr = xh.xhmath.pSet02("{One,Two,Three,Four}");
 * 
 * example result:
<_-.xhforest><XholonNull roleName="One"/><XholonNull roleName="Two"/><XholonNull roleName="Three"/><XholonNull roleName="Four"/></_-.xhforest>
 * (pretty printed):
<_-.xhforest>
  <XholonNull roleName="One"/>
  <XholonNull roleName="Two"/>
  <XholonNull roleName="Three"/>
  <XholonNull roleName="Four"/>
</_-.xhforest>
 * 
 * @param mstr a Math notation string
 * @return an XML string
 */
function pSet02(mstr) {
  if (!mstr || mstr.length == 0) {return null;}
  const str = mstr.trim();
  const arr = str.substring(1, str.length-1).split(SEP);
  let xmlStr = "<" + FOREST + ">";
  arr.forEach(function(item) {
    const itemt = item.trim();
    if (itemt.length > 0) {
      xmlStr += '<' + DEFAULT_XHC_NAME + ' roleName="' + itemt + '"/>';
    }
  });
  xmlStr += "</" + FOREST + ">";
  return xmlStr;
}

/**
 * ({Planet,Mercury,Venus,Earth,Mars}, Planet)
<Planet>
  <Mercury/>
  <Venus/>
  <Earth/>
  <Mars/>
</Planet>
 * ({Planets,Mercury,Venus,Earth,Mars}, Planets)
 * locations = ({Location, MarkedLocation, UnmarkedLocation, Lookout, Building, Entrance}, Location)
 * 
xh.xhmath.pSetPointed01("({Planet,Mercury,Venus,Earth,Mars}, Planet)");
"<Planet><Mercury/><Venus/><Earth/><Mars/></Planet>"
 * 
 */
function pSetPointed01(mstr) {
  const pparr = ppSetPointed(mstr);
  if (!pparr) {return null;}
  const arr = expandEllipsis(pparr[0].split(SEP)); // ex: ["Planet","Mercury","Venus","Earth","Mars"]
  const basepoint = pparr[1];
  let xmlStr = "<" + basepoint + ">";
  arr.forEach(function(item) {
    const itemt = item.trim();
    if ((itemt.length > 0) && (itemt != basepoint)) {
      xmlStr += "<" + itemt + "/>";
    }
  });
  xmlStr += "</" + basepoint + ">";
  return xmlStr;
}

/**
 * 
 * ({Collectable, smoothpinkrock, jaggedgreyrock, pineneedle3, greenleaf, brownleaf, blueberry, blackberry, acorn, pinecone, birchbark, blackrock, ice, mud, stick, thorn, smallseed, seeds, feather, horsetail, fern, snow, bug, moss, lichen, smoothgreyrock, jaggedgreenrock, pineneedle2, yellowleaf, orangeleaf, redberry, yellowberry, chestnut, fircone, bluerock, slush, muck, knot, thistle, bigseed, tinyfeather, ginkoleaf}, Collectable)
<_-.xhforest>
<Collectable roleName="smoothpinkrock"/>
<Collectable roleName="jaggedgreyrock"/>
...
<Collectable roleName="ginkoleaf"/>
</_-.xhforest>
 * 
xh.xhmath.pSetPointed02("({Planet,Mercury,Venus,Earth,Mars}, Planet)");
"<_-.xhforest><Planet roleName=\"Mercury\"/><Planet roleName=\"Venus\"/><Planet roleName=\"Earth\"/><Planet roleName=\"Mars\"/></_-.xhforest>"
 * 
 */
function pSetPointed02(mstr) {
  const pparr = ppSetPointed(mstr);
  if (!pparr) {return null;}
  const arr = expandEllipsis(pparr[0].split(SEP)); // ex: ["Planet","Mercury","Venus","Earth","Mars"]
  const basepoint = pparr[1];
  let xmlStr = "<" + FOREST + ">";
  arr.forEach(function(item) {
    const itemt = item.trim();
    if ((itemt.length > 0) && (itemt != basepoint)) {
      xmlStr += '<' + basepoint + ' roleName="' + itemt + '"/>';
    }
  });
  xmlStr += "</" + FOREST + ">";
  return xmlStr;
}

/**
 * Pre-parse a pointed set.
 * @param mstr a Math notation string
 * ex: "({Planet,Mercury,Venus,Earth,Mars}, Planet)"
 * @return an array of two strings, or null
 * ex: ["Planet,Mercury,Venus,Earth,Mars", "Planet"]
 */
function ppSetPointed(mstr) {
  if (!mstr || mstr.length == 0) {return null;}
  const str = mstr.trim();
  if (!str.startsWith(LPAREN) || (!str.endsWith(RPAREN))) {return null;};
  const startpos = str.indexOf(LBRACE);
  if (startpos == -1) {return null;}
  const endpos = str.indexOf(RBRACE, startpos);
  if (endpos == -1) {return null;}
  const bpstartpos = str.lastIndexOf(SEP);
  if (bpstartpos == -1) {return null;}
  const basepoint = str.substring(bpstartpos+1, str.length-1).trim(); // ex: "Planet"
  return [str.substring(startpos+1, endpos), basepoint];
}

/**
 * Expand ellipsis.
 * ex: ["0","…","11"] or ["0","...","11"]  is returned as ["0","1","2","3","4","5","6","7","8","9","10","11"]
 */
function expandEllipsis(arrin) {
  if ((arrin.length == 3) && ((arrin[1] == ELLIPSIS) || (arrin[1] == ELLIPSIS2))) {
    // ex: 0,…,11 or 0,...,11
    // counting - here we use arithmetic/addition
    const arrout = [];
    const range = Number(arrin[2]) - Number(arrin[0]) + 1;
    const nsize = range <= MAX_ELLIPSIS_EXPANSION ? range : MAX_ELLIPSIS_EXPANSION;
    for (var ix = 0; ix < nsize; ix++) {
      arrout.push(ix.toString());
    }
    return arrout;
  }
  else {
    return arrin;
  }
}

/**
 * 
 * Ports01 ports as a set of triples/tuples
 * {(A,B,trail), (A,G,trail), ..., (E,D,trail), (E,H,trail), (E,F,untrail), ...}
 */
function pPorts01(mstr) {
  // TODO
  return NOT_YET_IMPLEMENTED;
}

/**
 * ports
 * 
 * Ports02 ports as a set of structures/tuples/pairs, where each pair contains a set of pairs (a subset of Location x Location) and a label (the port name)
 * Set of pairs equipped with a label, where the label is the name of the link/function ex: trail, untrail, port, parent, type, etc.
 * by convention the set always comes first in this type of structure
{
  (
    {(A,B), (A,G), (A,I), (B,A), (B,C), (B,J), ...},
    trail
  ),
  (
    {(E,F), (F,E), ...},
    untrail
  )
}
 * OR
 * {({(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail),({(E,F),(F,E)},untrail)}
 * 
 */
function pPorts02(mstr, nodes) {
  // TODO
  if (!mstr || mstr.length == 0) {return null;}
  const str1 = mstr.replace(/ /g, "");
  if (!str1.startsWith(LBRACE) || (!str1.endsWith(RBRACE))) {return null;};
  const str2 = str1.substring(1, str1.length-1);
  //console.log(str2); // ({(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail),({(E,F),(F,E)},untrail)
  const arr1 = str2.substring(2, str2.length-1).split("),({");
  //console.log(arr1); // ["(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail","(E,F),(F,E)},untrail"]
  arr1.forEach(function(item1) {
    // "(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail"
    const arr2 = item1.split("},");
    const pairs = arr2[0]; // (A,B),(A,G),(A,I),(B,A),(B,C),(B,J)
    const portName = arr2[1]; // trail
    //console.log(pairs + " " + portName);
    const arr3 = pairs.substring(1,pairs.length-1).split("),(");
    arr3.forEach(function(item2) {
      const arr4 = item2.split(COMMA);
      const source = arr4[0];
      const target = arr4[1];
      //console.log(source + " -" + portName + "-> " + target); // A -portName-> B
      /*
        <port name="trail" index="0" connector="../*[@roleName='B']"/>
        <port name="trail" index="1" connector="../*[@roleName='G']"/>
        <port name="trail" index="2" connector="../*[@roleName='I']"/>
      */
      if (nodes) {
        const arr5 = nodes.filter(function(node) {
          return node.roleName == source;
        });
        if ((arr5.length == 1) && (arr5[0][OBJECT_PORTS][portName])) {
          arr5[0][OBJECT_PORTS][portName].push(target);
        }
      }
    });
  });
  // return ["A,trail,B", ""]
  return NOT_YET_IMPLEMENTED;
}

/**
 * 
 * Ports03 ports as a simple set of pairs; the label "port" is assumed, and the index auto-increments (ex: port[0] port[1] ...)
 * {(Aaa,Bbb),(Aaa,Ccc), (Ddd,Eee), ...}
 */
function pPorts03(mstr) {
  // TODO
  return NOT_YET_IMPLEMENTED;
}

/**
 * 
# process all of this stuff together
nodes = {0,1,2,3,4,5,6,7,8,9,10,11} # or use shorthand {0,…,11}; these are JavaScript Array indexes
roles = {A,B,C,D,E,F,G,H,I,J,K,L}
# assume that types are already defined
roleMapping = {(0,A), (1,B), (2,C), (3,D), (4,E), (5,F), (6,G), (7,H), (8,I), (9,J), (10,K), (11,L)} # {(0,A), ..., (11,L)}
typeMapping = {(0,MarkedLocation), (1,MarkedLocation), (2,MarkedLocation), (3,MarkedLocation), (4,MarkedLocation), (5,UnmarkedLocation), (6,Lookout), (7,Lookout), (8,Building), (9,Entrance), (10,Entrance), (11,Entrance)}
ports = {trail,untrail}
portMapping = {({(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail),({(E,F),(F,E)},untrail)} # using roleNames rather than id
 */
function pSiblings01(mstr) {
  if (!mstr || mstr.length == 0) {return null;}
  const nodes = [];
  const types = []; // new XholonClass types
  const roles = []; // the set of valid role names
  const ports = []; // the set of valid port names
  const decorations = {}; // colors and other Xholon decorations can be added to this Object
  const lines = mstr.split(NEWLINE);
  lines.forEach(function(line) {
    //console.log(line);
    // remove any comment
    var cpos = line.indexOf(COMMENT);
    if (cpos != -1) {
      line = line.substring(0, cpos);
      //console.log(line);
    }
    line = line.trim();
    if (line.length == 0) {return;}
    const arr1 = line.split(EQUALS);
    if (arr1.length != 2) {return;}
    const lname = arr1[0].trim();
    let lstruct = arr1[1];
    if (lname != "annoMapping") {
      lstruct = lstruct.replace(/ /g, "");
    }
    lstruct = lstruct.substring(1,lstruct.length-1); // every line defines a Set; remove opening and closing brackets {}
    //console.log(lname + "|" + lstruct);
    switch (lname) {
    case "nodes":
      // nodes = {0,1,2,3,4,5,6,7,8,9,10,11}
      // or use shorthand {0,…,11} or {0,...,11}
      // see "Saunders Mac Lane - Mathematics Form and Function" chapter 1, for a discussion about counting vs matching
      // ex: if there are 12 items in the "nodes" set, then the nodes Array will be filled with 12 instances of an empty JavaScript Object {}
      const narr = lstruct.split(COMMA);
      if ((narr.length == 3) && ((narr[1] == ELLIPSIS) || (narr[1] == ELLIPSIS2))) {
        // ex: 0,…,11 or 0,...,11
        // counting - here we use arithmetic/addition
        const nsize = Number(narr[2]) - Number(narr[0]) + 1;
        for (var ix = 0; ix < nsize; ix++) {
          nodes.push({});
        }
      }
      else {
        // ex: 0,1,2,3,4,5,6,7,8,9,10,11 or 0,11,1,10,2,9,3,8,4,7,5,6 or un,deux,trois,quatre,cinq,six,sept,huit,neuf,dix,onze,douze or whatever
        // matching - here we simply create a new JavaScript Object for each item in the narr Array; we don't need to count or to use arithmetic
        // the actual value of each Array item doesn't matter
        narr.forEach(() => nodes.push({}));
      }
      //console.log(JSON.stringify(nodes));
      break;
    case "types":
      lstruct.split(COMMA).forEach(tname => types.push(tname));
      break;
    case "roles":
      lstruct.split(COMMA).forEach(rname => roles.push(rname));
      break;
    case "ports":
      lstruct.split(COMMA).forEach(pname => ports.push(pname));
      nodes.forEach(node => ports.forEach(port => {
        if (!node[OBJECT_PORTS]) {
          node[OBJECT_PORTS] = {};
        }
        node[OBJECT_PORTS][port] = [];
      }));
      break;
    case "roleMapping":
      // roleMapping = {(0,A), (1,B), (2,C), (3,D), (4,E), (5,F), (6,G), (7,H), (8,I), (9,J), (10,K), (11,L)}
      // nodes[index].roleName = rname.trim();
      lstruct.substring(1,lstruct.length-1).split("),(").forEach(function(pair) {
        const rmarr = pair.split(COMMA);
        if (rmarr.length != 2) {return;}
        const nindex = rmarr[0]; // index of node in nodes array
        const rname = rmarr[1]; // roleName of that node
        console.log(nindex + "|" + rname);
        if (nodes[nindex]) {
          nodes[nindex].roleName = rname;
        }
      });
      break;
    case "typeMapping":
      lstruct.substring(1,lstruct.length-1).split("),(").forEach(function(pair) {
        const tmarr = pair.split(COMMA);
        if (tmarr.length != 2) {return;}
        const nindex = tmarr[0]; // index of node in nodes array
        const tname = tmarr[1]; // type (xhc, XholonClass name) of that node
        console.log(nindex + "|" + tname);
        if (nodes[nindex]) {
          nodes[nindex].xhc = tname;
        }
      });
      break;
    case "portMapping":
      pPorts02(LBRACE + lstruct + RBRACE, nodes);
      break;
    // Xholon node decorations
    case "colors":
      // colors = {red,orange,yellow,green,blue,purple}
    case "opacities":
    case "fonts":
    case "icons":
    case "toolTips":
    case "symbols":
    case "formats":
    case "annos":
      if (!decorations[lname]) {decorations[lname] = [];}
      const dec = decorations[lname];
      lstruct.split(COMMA).forEach(decname => dec.push(decname));
      break;
    // other Xholon node attributes
    case "geos":
    case "sounds":
      // TODO
      break;
    case "colorMapping":
    case "opacityMapping":
    case "fontMapping":
    case "iconMapping":
    case "toolTipMapping":
    case "symbolMapping":
    case "formatMapping":
    case "annoMapping":
    case "geoMapping":
    case "soundMapping":
      let dectype = lname.substring(0, lname.length - 7); // "Mapping" is 7 characters long
      doDecorationMapping(lstruct, nodes, dectype);
      break;
    default: break;
    }
  });
  //console.log(nodes);
  console.log(JSON.stringify(nodes));
  // TODO also return types; return a JSO with three sub-objects called "ih", "cd", and "csh" OR return a XholonModule
  return jso2xholon(nodes, ports);
}

/**
 * Do decoration mapping.
 * ex: colorMapping = {(0,red), (1,purple), (2,green), (3,blue)}
 * @param struct (ex: "(0,red),(1,purple),(2,green),(3,blue)")
 * @param nodes 
 * @param dectype (ex: "color")
 */
function doDecorationMapping(struct, nodes, dectype) {
  struct.substring(1,struct.length-1).split("),(").forEach(function(pair) {
    const decarr = pair.split(COMMA);
    if (decarr.length != 2) {return;}
    const nindex = decarr[0]; // index of node in nodes array
    const decval = decarr[1]; // ex: "red"
    console.log(nindex + "|" + decval);
    if (nodes[nindex]) {
      if (!nodes[nindex].decorations) {
        nodes[nindex].decorations = {};
      }
      nodes[nindex].decorations[dectype] = decval;
    }
  });
}

/** 
 * Parse a JSON string.
 * example:
xh.xhmath.pJson01('[{"roleName":"un","xhc":"Mars","ports":{"moi":["un","deux","trois"]}},{"roleName":"deux","xhc":"Mars","ports":{"moi":["un","deux)(deux"]}},{"roleName":"trois","xhc":"Mars","ports":{"moi":["un","deux","trois"]}}]', null);
 * example:
xh.xhmath.pJson01('[{"roleName":"un","xhc":"Mars","ports":{"moi":["un","deux","trois"]}},{"roleName":"deux","xhc":"Mars","ports":{"moi":["un","deux)(deux"]}},{"roleName":"trois","xhc":"Mars","ports":{"moi":["un","deux","trois"]}}]', ["moi"]);
 * example:
xh.root().append(xh.xhmath.pJson01('[{"roleName":"un","xhc":"Mars","ports":{"arrow":["un","deux","trois"]}},{"roleName":"deux","xhc":"Mars","ports":{"arrow":["un","deux","trois"]}},{"roleName":"trois","xhc":"Mars","ports":{"arrow":["un","deux","trois"]}}]'));
 * 
 * @param json a JSON string
 * @param ports an optional array of port names (ex: ["one","two"]), or null or undefined
 * @return a Xholon XML string
 */
function pJson01(json, ports) {
  return jso2xholon(JSON.parse(json), ports);
}

/**
 * jso2xholon
 */
function jso2xholon(jso, ports) {
  let xmlStr = '<' + FOREST + '>\n';
  jso.forEach(function(obj) {
    // {"roleName":"I","xhc":"Building","trail":["A"],"untrail":["F"]},
    xmlStr += '<' + (obj.xhc || DEFAULT_XHC_NAME);
    const rname = obj.roleName || DEFAULT_ROLE_NAME;
    if (rname) {
      xmlStr += ' roleName="' + rname + '"';
    }
    xmlStr += '>\n';
    /*if (obj.decorations && obj.decorations["color"]) {
      // TODO just testing color for now
      xmlStr += "<Color>" + obj.decorations["color"] + "</Color>\n";
    }*/
    if (obj.decorations) {
      Object.keys(obj.decorations).forEach(function(dname) {
        let darr = DECORATIONS[dname];
        if (darr) {
          xmlStr += "<" + darr[DEC_INDEX_TAGNAME] + ">" + obj.decorations[darr[DEC_INDEX_SINGULAR]] + "</" + darr[DEC_INDEX_TAGNAME] + ">\n";
        }
      });
    }
    if (!ports) {
      ports = Object.keys(obj[OBJECT_PORTS]);
    }
    ports.forEach(function(pname) {
      obj[OBJECT_PORTS][pname].forEach(function(item, index) {
        xmlStr += '<port name="' + pname + '" index="' + index + '" connector="../*[@roleName=' + "'" + item + "'" + ']"/>\n';
      });
    });
    xmlStr += '</' + (obj.xhc || DEFAULT_XHC_NAME) + '>\n';
  });
  xmlStr += '</' + FOREST + '>';
  console.log(xmlStr);
  return xmlStr;
}

export {pSet01, pSet02, pSetPointed01, pSetPointed02, pPorts01, pPorts02, pPorts03, pSiblings01, pJson01};

