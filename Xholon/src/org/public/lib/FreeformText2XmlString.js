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
 - ex: if I drag "Licorice" into a Cats node, the result should be <Cat roleName="Licorice"/>
 - ex: if I drag "John Doe" into a Names node, the result should be <Name roleName="John Doe"/>
 - optionally, after creating one of these nodes, it could be dragged somewhere else
 - could do the the same with Notes/Note References/Reference etc.
- maybe make it easy for node types to be subclass of exist types like Attribute_String, Script, etc.
 - by first dragging TEXT to IH Attribute_String, Script, etc.
 - and then dragging it to CSH location
- two-step process:
 1. TYPE:     drag text (CONTENT) to a container/dropzone that specifies its type (XholonClass) ex: drag text to a <Attribute_Strings/> container to create an <Attribute_String/> node
 2. LOCATION: then drag that node to the desired location of that node
 - the result is CONTENT+TYPE+LOCATION
- another possible process:
 - drag text and hover over a TYPE node (which generates the node, and then continue dragging the new node to its LOCATION (all one drag action))
- use the standardCollectionFuncs to implement any of these processes
 * 
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fftxt2xmlstr = {}

xh.fftxt2xmlstr.XML_FOREST = "_-.";

xh.fftxt2xmlstr.defaultParams = {
  roleAction: "replace",
  standardCollectionNames: ["Notes", "References", "Cats"],
  // OR
  standardCollectionFuncs: {
  //Container: Function
    Notes: note => '<Note>' + note + '</Note>',
    References: ref => '<Reference>' + ref + '</Reference>',
    Cats: cat => '<Cat roleName="' + cat + '"/>',
    Attribute_Strings: str => '<Attribute_String roleName="' + str.substring(0,10) + '">' + str + '</Attribute_String>',
    Annotations: str => '<' + xh.fftxt2xmlstr.XML_FOREST + 'anno><Annotation>' + str.trim() + '</Annotation></' + xh.fftxt2xmlstr.XML_FOREST + 'anno>'
  }
}
// console.log(defaultParams.roleAction); // replace
// console.log(defaultParams.standardCollectionFuncs.Cats("Meow")); // <Cat roleName="Meow/>

// (String, JSON, String) -> XMLString
// TODO add new arg: nodeXhcName  ex: "Notes"  CutCopyPaste.java will need to pass this in
xh.fftxt2xmlstr.transform = (nodeXhcName, argParams, fftxt) => {
  console.log("nodeXhcName: " + nodeXhcName);
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

