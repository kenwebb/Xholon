/**
 * parser.js
 * (C) Ken Webb, MIT license
 * December 7, 2019
 * 
 * Parse Math notation.
 * 
 * TODO:
 * - 
 */

const SEP = ",";
const FOREST = "_-.xhforest";

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
  if (!str.startsWith("(") || (!str.endsWith(")"))) {return null;};
  const startpos = str.indexOf("{");
  if (startpos == -1) {return null;}
  const endpos = str.indexOf("}", startpos);
  if (endpos == -1) {return null;}
  const bpstartpos = str.lastIndexOf(SEP);
  if (bpstartpos == -1) {return null;}
  const basepoint = str.substring(bpstartpos+1, str.length-1).trim(); // ex: "Planet"
  return [str.substring(startpos+1, endpos), basepoint];
}

export {pSet01, pSetPointed01, pSetPointed02};

