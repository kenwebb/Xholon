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
 - ex: if I drag "Licorice" into a Cats node, the result should be <Cat>Licorice</Cat>
 * 
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fftxt2xmlstr = {}

xh.fftxt2xmlstr.defaultParams = {
  roleAction: "replace"
}

// (JSON, String) -> XMLString
xh.fftxt2xmlstr.transform = (argParams, fftxt) => {
  const params = argParams || xh.fftxt2xmlstr.defaultParams;
  const words = fftxt.split(/\s/g);
  if (words.length == 0) {
    return null;
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

