/**
 * FreeformText2XmlString.js
 * (C) Ken Webb, MIT license
 * June 12, 2020
 * 
 * Transform free-form text to a Xholon-compatible XML String.
 * The free-form unstructured text would typically be English words, phrases, sentences, lists, etc.
 * This content could also be in some other human language.
 * 
 * This JavaScript library may be invoked from CutCopyPaste.java adjustPastedContent, when the content is plain free-form (non-XML) text.
 * 
 * @example
 * Usage:
 * http://127.0.0.1:8080/war/Xholon.html?app=APPNAME&src=lstr&gui=clsc&jslib=FreeformText2XmlString
 * http://127.0.0.1:8080/war/XholonInteract.html?app=6901ef51d5fcb2d86a21&src=gist&gui=none&jslib=FreeformText2XmlString
 * http://127.0.0.1:8888/Xholon.html?app=6ee032b8913680e72191ffea48dca46f&src=gist&gui=clsc&jslib=nearley/nearley,nearley/fipaAcl,fipa,FreeformText2XmlString
 * 
 * see also: my notes in my paper notebook from June 11/12 2020
 * 
 * Examples for testing:
One
Two deux
Three trois quatre

 eins
 testing
 
 a b c
 
NO <Hello><Role action="replace">howdy</Role></Hello>

<Hello><Role roleName="howdy" action="replace"></Role></Hello>
<Hello><Role roleName="howdy" action="replace"/></Hello>

NO <Hello><Anno>howdy</Anno></Hello>
<Hello><Annotation>Mercury Venus</Annotation></Hello>

Four
Five
Six
Ten
Licorice

<Role roleName="june13" action="replace"></Role>
<Role roleName="june13" action="replace"/>
<Role roleName="kjune13" action="replace"></Role>

References
Notes
 *
 * TODO
- DONE if the input String is a single word, and if it starts with "http", then it should be treated as an Annotation
- optionally append new text to an existing annotation, where the new text will be preceded by a newline
- Xholon workbook:
 - provide a Xholon subtree that includes nodes for workbook sections: Notes References IH CD CSH etc.
 - maybe create a new Xholon mechanism with node types that can properly write-out parts of a workbook
- optionally add new XholonClass names to the IH as well as to the CSH
- use default child names:
 - DONE ex: if I drag "Licorice" into a Cats node, the result should be <Cat roleName="Licorice"/>
 - ex: if I drag "John Doe" into a Names node, the result should be <Name roleName="John Doe"/>
 - optionally, after creating one of these nodes, it could be dragged somewhere else
 - could do the the same with Notes/Note References/Reference etc.
- maybe make it easy for node types to be subclass of existing types like Attribute_String, Script, etc.
 - by first dragging TEXT to IH Attribute_String, Script, etc.
 - and then dragging it to CSH location
- two-step process:
 1. TYPE:     drag text (CONTENT) to a container/dropzone that specifies its type (XholonClass) ex: drag text to a <Attribute_Strings/> container to create an <Attribute_String/> node
 2. LOCATION: then drag that node to the desired location of that node
 - the result is CONTENT+TYPE+LOCATION
- another possible process:
 - drag text and hover over a TYPE node (which generates the node, and then continue dragging the new node to its LOCATION (all one drag action))
- use the standardCollectionFuncs to implement any of these processes
- provide support for creating Xholon Animate nodes
- provide support for creating DropZones subtrees, and Xholon Workbook subtrees
- be able to drag a clone of a node, rather than the node itself
 - https://github.com/taye/interact.js/blob/888188dd6c033e7f7b526b06854749ca3ae7c843/docs/faq.md
 - Clone target draggable
 * 
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fftxt2xmlstr = {}

xh.fftxt2xmlstr.XML_FOREST = "_-.";
xh.fftxt2xmlstr.ROLENAME_LEN = 20;
xh.fftxt2xmlstr.DEFAULT_GUI_DIVNAME = "#xhanim";
xh.fftxt2xmlstr.DEFAULT_GUI_CHILD_DIVNAMES = ["one", "two"];
xh.fftxt2xmlstr.DEFAULT_LINE_SEPARATOR = "\n";

xh.fftxt2xmlstr.defaultParams = {
  roleAction: "replace",
  lineSeparator: xh.fftxt2xmlstr.DEFAULT_LINE_SEPARATOR,
  standardCollectionNames: ["Notes", "References", "XholonWorkbooks", "Cats"],
  // OR
  standardCollectionFuncs: {
  //Container: Function
    Notes: note => '<Note roleName="' + note.replace(/[^a-zA-Z0-9 ]/g, "").trim().substring(0,xh.fftxt2xmlstr.ROLENAME_LEN) + '">' + note + '</Note>',
    Note: note => '<Note roleName="' + note.replace(/[^a-zA-Z0-9 ]/g, "").trim().substring(0,xh.fftxt2xmlstr.ROLENAME_LEN) + '">' + note + '</Note>',
    References: ref => '<Reference roleName="' + ref.replace(/[^a-zA-Z0-9 ]/g, "").trim().substring(0,xh.fftxt2xmlstr.ROLENAME_LEN) + '">' + ref + '</Reference>',
    Reference: ref => '<Reference roleName="' + ref.replace(/[^a-zA-Z0-9 ]/g, "").trim().substring(0,xh.fftxt2xmlstr.ROLENAME_LEN) + '">' + ref + '</Reference>',
    Cats: cat => '<Cat roleName="' + cat + '"/>',
    Attribute_Strings: str => '<Attribute_String roleName="' + str.replace(/[^a-zA-Z0-9 ]/g, "").trim().substring(0,xh.fftxt2xmlstr.ROLENAME_LEN) + '">' + str + '</Attribute_String>',
    Annotations: str => '<' + xh.fftxt2xmlstr.XML_FOREST + 'anno><Annotation>' + str.trim() + '</Annotation></' + xh.fftxt2xmlstr.XML_FOREST + 'anno>',
    XholonWorkbooks: str => `
<XholonModule>
  <XholonMap>
    <Attribute_String roleName="ih"><![CDATA[
<_-.XholonClass>
  <${str}/>
</_-.XholonClass>
    ]]></Attribute_String>
    <Attribute_String roleName="cd"><![CDATA[
<xholonClassDetails></xholonClassDetails>
    ]]></Attribute_String>
    <Attribute_String roleName="csh"><![CDATA[
<_-.csh>
  <${str}/>
</_-.csh>
    ]]></Attribute_String>
  </XholonMap>
</XholonModule>`
  }
}
// console.log(defaultParams.roleAction); // replace
// console.log(defaultParams.standardCollectionFuncs.Cats("Meow")); // <Cat roleName="Meow/>

// (String, JSON, String) -> XMLString
// nodeXhcName  ex: "Notes"  CutCopyPaste.java needs to pass this in
xh.fftxt2xmlstr.transform = (nodeXhcName, argParams, fftxt) => {
  const lines = fftxt.split(xh.fftxt2xmlstr.defaultParams.lineSeparator);
  var subtree = "";
  // fftxt may consist of multiple lines of text, separated by newline "\n"
  if (lines.length > 1) {
    subtree += "<" + xh.fftxt2xmlstr.XML_FOREST + "subtree>";
    lines.forEach((line) => {
      // exclude blank lines
      if (line.trim().length > 0) {
        subtree += xh.fftxt2xmlstr.transform1(nodeXhcName, argParams, line);
      }
    })
    subtree += "</" + xh.fftxt2xmlstr.XML_FOREST + "subtree>";
  }
  else {
    subtree += xh.fftxt2xmlstr.transform1(nodeXhcName, argParams, fftxt);
  }
  return subtree;
}

xh.fftxt2xmlstr.transform1 = (nodeXhcName, argParams, fftxt) => {
  //console.log("nodeXhcName: " + nodeXhcName);
  const params = argParams || xh.fftxt2xmlstr.defaultParams;
  const words = fftxt.split(/\s/g);
  if (words.length == 0) {
    return null;
  }
  else if (nodeXhcName && (nodeXhcName in params.standardCollectionFuncs)) {
    return params.standardCollectionFuncs[nodeXhcName](fftxt);
  }
  else if (words[0] == "") {
    // the first word is whitespace
    // length 2: <Role roleName="words[1]" action="params.roleAction"/>
    // length 3+: <Annotation>words[1]+</Annotation>
    if (words.length == 1) {
      return null;
    }
    else if (words.length == 2) {
      return '<Role roleName="' + words[1] + '" action="' + params.roleAction + '"/>';
    }
    else {
      return '<Annotation>' + fftxt.trim() + '</Annotation>';
    }
  }
  else if (words.length == 1) {
    if (words[0].trim().startsWith("http")) {
      return '<Annotation>' + fftxt.trim() + '</Annotation>';
    }
    else {
      return '<' + words[0] + '/>';
    }
  }
  else if (words.length == 2) {
    return '<' + words[0] + ' roleName="' + words[1] + '"/>';
  }
  else {
    return '<' + words[0] + ' roleName="' + words[1] + '"><Anno>' + fftxt + '</Anno></' + words[0] + '>';
  }
}

// ex: xh.fftxt2xmlstr.configGui("#xhanim", ["one", "two"], true);
// ex: xh.fftxt2xmlstr.configGui(null, null, true);
// 
xh.fftxt2xmlstr.configGui = (xhDivName, childDivNames, caption) => {
  // SVG caption
  if (caption) {
    xh.svg = {};
    xh.svg.caption = document.createElement("p");
    xh.svg.caption.textContent = xh.param("ModelName");
  }
  
  var div = document.querySelector(xhDivName || xh.fftxt2xmlstr.DEFAULT_GUI_DIVNAME);
  
  var cDivNamesArr = childDivNames || xh.fftxt2xmlstr.DEFAULT_GUI_CHILD_DIVNAMES;
  cDivNamesArr.forEach((cDivName, index) => {
    const cDiv = document.createElement("div");
    cDiv.setAttribute("id", cDivName);
    div.appendChild(cDiv);
    if (caption && (index == 0)) {
      cDiv.appendChild(xh.svg.caption);
    }
  })
}

// ex: xh.fftxt2xmlstr.configInteract();
xh.fftxt2xmlstr.configInteract = () => {
 var selection = '#xhanim>#two>svg g.d3cpnode,#one>svg g.d3cpnode'; //'#xhgraph>svg g.d3cpnode';
 
 // Drag
 var drag = interact(selection).draggable({
  onstart: function (event) {
   var target = event.target;
   if (!target.getAttribute('data-x') && target.transform) {
    target.setAttribute('data-x', target.transform.baseVal[0].matrix.e);
    target.setAttribute('data-y', target.transform.baseVal[0].matrix.f);
    //target.transform = "";
   }
  },
  
  // call this function on every dragmove event
  onmove: function (event) {
   var target = event.target,
    // keep the dragged position in the data-x/data-y attributes
    x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
    y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;
    
   // translate the element
   target.style.webkitTransform = target.style.transform =
     'translate(' + x + 'px, ' + y + 'px)';
 
   // update the position attributes
   target.setAttribute('data-x', x);
   target.setAttribute('data-y', y);
  },
  
  // call this function on every dragend event
  onend: function (event) {
   //var textEl = event.target.querySelector('p');
   //textEl && (textEl.textContent = 'moved a distance of '
   // + (Math.sqrt(event.dx * event.dx + event.dy * event.dy)|0) + 'px');
  }
 }).draggable({ inertia: true }); //.inertia(true); // changed 2020
 
 // Drop
 // drop works with the above drag, when I drag from a leaf node to another node
 var drop = interact(selection).dropzone({
  ondropactivate: function (event) {
   // add active dropzone feedback
   //console.log("ondropactivate");
   event.target.classList.add('drop-active');
  },
  
  ondragenter: function (event) {
   var draggableElement = event.relatedTarget,
    dropzoneElement = event.target;
   //console.log("ondragenter");
   
   // feedback the possibility of a drop
   dropzoneElement.classList.add('drop-target');
   draggableElement.classList.add('can-drop');
   //draggableElement.querySelector("text").textContent = 'N';
  },
  
  ondragleave: function (event) {
   // remove the drop feedback style
   //console.log("ondragleave");
   event.target.classList.remove('drop-target');
   event.relatedTarget.classList.remove('can-drop');
   //event.relatedTarget.querySelector("text").textContent = 'T';
  },
  
  ondrop: function (event) {
    var xhroot = xh.root();
    var svgchld = event.relatedTarget;
    var svgprnt = event.target;
    var xhchld = xhroot.xpath("descendant-or-self::*[@name='" + svgchld.id + "']");
    var xhprnt = xhroot.xpath("descendant-or-self::*[@name='" + svgprnt.id + "']");
    if (xhchld && xhprnt) {
      if (xhchld.parent() != xhprnt) {
        xhprnt.append(xhchld.remove());
        xhroot.println(xhchld.name() + " has moved to " + xhprnt.name());
      }
      else {
        xhroot.println(xhchld.name() + " has stayed inside " + xhprnt.name());
      }
    }
    else {
      xhroot.println(svgchld.id + " has unknown location");
    }
  },
  
  ondropdeactivate: function (event) {
   // remove active dropzone feedback
   //console.log("ondropdeactivate");
   event.target.classList.remove('drop-active');
   event.target.classList.remove('drop-target');
  }
 });
}

// IXholon_subtree -> Text
// ex: xh.fftxt2xmlstr.getNodesText(node, "", 0, "");
// ex: xh.fftxt2xmlstr.getNodesText(node, "", 0, "- ");
// ex: xh.fftxt2xmlstr.getNodesText(temp0, "", -1, "");
xh.fftxt2xmlstr.getNodesText = (node, str, level, lprefix) => {
  if (str == null) {
    str = "";
  }
  if (str.length == 0) {
    // this is the root node of the subtree (probably <Nodes/>)
    str += new Date().toLocaleString() + "\n";
  }
  else {
    var nodestr = node.text();
    if (nodestr) {
      switch (level) {
      case 0: str += ""; break;
      case 1: str += " "; break;
      case 2: str += "  "; break;
      case 3: str += "   "; break;
      case 4: str += "    "; break;
      default: str += "     "; break;
      }
    	str += lprefix + nodestr + "\n";
    }
  }
  var cnode = node.first();
  while (cnode) {
    str = xh.fftxt2xmlstr.getNodesText(cnode, str, level + 1, lprefix);
    cnode = cnode.next();
  }
  return str;
}

// IXholon_subtree -> Text
// ex: xh.fftxt2xmlstr.getRefsText(node, "", 0, 0);
xh.fftxt2xmlstr.getRefsText = (node, str, level, counter) => {
  if (str == null) {
    str = "";
  }
  if (str.length == 0) {
    // this is the root node of the subtree (probably <References/>)
    str += "References\n----------\n";
  }
  else {
    var nodestr = node.text();
    if (nodestr) {
      switch (level) {
      case 1: str += "\n(" + counter + ") "; break;
      case 2: str += "    "; break;
      default: str += "    "; break;
      }
    	str += nodestr + "\n";
    }
  }
  var cnode = node.first();
  while (cnode) {
    str = xh.fftxt2xmlstr.getRefsText(cnode, str, level + 1, counter++);
    cnode = cnode.next();
  }
  return str;
}

