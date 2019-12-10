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
const ELLIPSIS = "&#x2026;"; // &hellip; …
const COMMENT = "#";
const NEWLINE = "\n";
const EQUALS = "=";

const SEP = COMMA;
const FOREST = "_-.xhforest";
const NOT_YET_IMPLEMENTED = "<Attribute_String>not yet implemented</Attribute_String>";


/**
 * Parse a Math Set.
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
  const arr = pparr[0].split(SEP); // ex: ["Planet","Mercury","Venus","Earth","Mars"]
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
  const arr = pparr[0].split(SEP); // ex: ["Planet","Mercury","Venus","Earth","Mars"]
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
  console.log(str2); // ({(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail),({(E,F),(F,E)},untrail)
  const arr1 = str2.substring(2, str2.length-1).split("),({");
  console.log(arr1); // ["(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail","(E,F),(F,E)},untrail"]
  arr1.forEach(function(item1) {
    // "(A,B),(A,G),(A,I),(B,A),(B,C),(B,J)},trail"
    const arr2 = item1.split("},");
    const pairs = arr2[0]; // (A,B),(A,G),(A,I),(B,A),(B,C),(B,J)
    const portName = arr2[1]; // trail
    console.log(pairs + " " + portName);
    const arr3 = pairs.substring(1,pairs.length-1).split("),(");
    arr3.forEach(function(item2) {
      const arr4 = item2.split(COMMA);
      const source = arr4[0];
      const target = arr4[1];
      console.log(source + " -" + portName + "-> " + target); // A -portName-> B
      /*
        <port name="trail" index="0" connector="../*[@roleName='B']"/>
        <port name="trail" index="1" connector="../*[@roleName='G']"/>
        <port name="trail" index="2" connector="../*[@roleName='I']"/>
      */
      if (nodes) {
        const arr5 = nodes.filter(function(node) {
          return node.roleName == source;
        });
        if ((arr5.length == 1) && (arr5[0][portName])) {
          arr5[0][portName].push(target);
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
  const roles = []; // the set of valid role names
  const ports = []; // the set of valid port names
  const lines = mstr.split(NEWLINE);
  lines.forEach(function(line) {
    console.log(line);
    // remove any comment
    var cpos = line.indexOf(COMMENT);
    if (cpos != -1) {
      line = line.substring(0, cpos);
      console.log(line);
    }
    line = line.trim();
    if (line.length == 0) {return;}
    const arr1 = line.split(EQUALS);
    if (arr1.length != 2) {return;}
    const lname = arr1[0].trim();
    let lstruct = arr1[1].replace(/ /g, "");
    lstruct = lstruct.substring(1,lstruct.length-1); // every line defines a Set; remove opening and closing brackets {}
    console.log(lname + "|" + lstruct);
    switch (lname) {
    case "nodes":
      lstruct.split(COMMA).forEach(function(nname) {
        // TODO handle ELLIPSIS
        nodes.push({});
      });
      console.log(nodes);
      break;
    case "roles":
      lstruct.split(COMMA).forEach(function(rname) {
        roles.push(rname);
      });
      console.log(roles);
      break;
    case "ports":
      lstruct.split(COMMA).forEach(function(pname) {
        ports.push(pname);
      });
      console.log(ports);
      nodes.forEach(function(node) {
        ports.forEach(function(port) {
          node[port] = [];
        });
      });
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
        nodes[nindex].roleName = rname;
      });
      break;
    case "typeMapping":
      lstruct.substring(1,lstruct.length-1).split("),(").forEach(function(pair) {
        const tmarr = pair.split(COMMA);
        if (tmarr.length != 2) {return;}
        const nindex = tmarr[0]; // index of node in nodes array
        const tname = tmarr[1]; // type (xhc, XholonClass name) of that node
        nodes[nindex].xhc = tname;
      });
      break;
    case "portMapping":
      pPorts02(LBRACE + lstruct + RBRACE, nodes);
      break;
    default: break;
    }
  });
  console.log(nodes);
  return NOT_YET_IMPLEMENTED;
}

export {pSet01, pSetPointed01, pSetPointed02, pPorts01, pPorts02, pPorts03, pSiblings01};

