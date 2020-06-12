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
 * 
 * see also: my notes in my paper notebook from June 11/12 2020
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fftxt2xmlstr = {}

// (JSON, String) -> XMLString
xh.fftxt2xmlstr.transform = (params, fftxt) => {
  const words = fftxt.split(/\s/g);
  if (words.length == 0) {
    return null;
  }
  else if (words[0] == "") {
    // the first word is whitespace
    // length 2: <Role>words[1]</Role>
    // length 3+: <Annotation>words[1]+</Annotation>
    if (words.length == 1) {
      return null;
    }
    else if (words.length == 2) {
      // TODO the Role node is added as a child, but it doesn't really work as a roleName
      return '<Role>' + words[1] + '</Role>';
    }
    else {
      return '<Annotation>' + fftxt.trim() + '</Annotation>';
    }
  }
  else if (words.length == 1) {
    // the simplest case
    return '<' + words[0] + '/>';
  }
  else if (words.length == 2) {
    return '<' + words[0] + ' roleName="' + words[1] + '"/>';
  }
  else {
    return '<' + words[0] + ' roleName="' + words[1] + '"><Anno>' + fftxt + '</Anno></' + words[0] + '>';
  }
}

