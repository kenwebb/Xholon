/**
 * Xholon Workbook
 *
 * Licensed under the MIT license  http://opensource.org/licenses/MIT .
 * Copyright (C) 2013 Ken Webb
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon/gwt/">Xholon Project website</a>
 * @since 0.1 (Created on Jan 15, 2012)
 */
(function () {

  // default colors
  var FILL_RECT = '#aad4ff'; // bluish
  var FILL_CIRCLE = '#aaffaa'; // greenish
  
  // default text
  var TEXT_DEFAULT = 'Block';
  
  // default id for rect
  var ID_RECT = 'System/Block';
  
  CodeMirror.svgHint = function(editor) {
    var cur = editor.getCursor();
    var token = editor.getTokenAt(cur);
    token = {start: cur.ch, end: cur.ch, string: "", state: token.state,
                       className: token.string == "." ? "property" : null};
    return {list: getCompletions(token),
            from: {line: cur.line, ch: token.start},
            to: {line: cur.line, ch: token.end}};
  }

/*
<rect fill="black" id="svg_5" y="66" x="79" height="20" width="20"/>
<text id="svg_7" y="42" x="47">hello</text>
<circle id="svg_8" fill="red" r="20" cy="83" cx="33"/>

<g id="svg_1">
  <rect stroke-width="1.0px" stroke="#a121f1" fill="white" height="60.000002" width="187.999994" y="20.999999" x="13" id="TrainSystem/ToyTrain"/>
  <text fill="black" font-size="8" font-family="Verdana" y="31" x="18">ToyTrain</text>
</g>
*/
  function getCompletions(token) {
    var idRect = ID_RECT;
    var textText = TEXT_DEFAULT;
    var editor = $('div.XholonApplications').xholonWorkbook('getEditor');
    var cshEditor = editor[3];
    //var cshContent = cshEditor.getSelection();
    var cshCur = cshEditor.getCursor();
    var cshToken = cshEditor.getTokenAt(cshCur);
    var cshContent = cshToken.state.tagName;
    if (cshContent && cshContent.length) {
      textText = cshContent;
      var cshTokenType = cshToken.state.type;
      if (cshToken.className == 'attribute') {
        if (cshToken.string == 'roleName') {
          // search for the value of the attribute
          var attrValue = '';
          var curLine = cshEditor.getLine(cshCur.line);
          var startPos = curLine.indexOf('"', cshCur.ch);
          if (startPos != -1) {
            startPos++;
            var endPos = curLine.indexOf('"', startPos);
            if (endPos != -1) {
              attrValue = curLine.substring(startPos, endPos);
            }
          }
          cshContent += "[@roleName='"+attrValue+"']";
          cshTokenType = 'openTag';
        }
      }
      if (cshTokenType == 'openTag') {
        var parent = cshToken.state.context;
        while (parent) {
          cshContent = parent.tagName + '/' + cshContent;
          parent = parent.prev;
        }
        idRect = cshContent;
      }
      else {
        // closeTag or null
        idRect = cshContent;
      }
    }
    var found = [
'<rect id="'+idRect+'" fill="'+FILL_RECT+'" height="20" width="30"/>',
'<text>'+textText+'</text>',
'<circle id="'+idRect+'" fill="'+FILL_CIRCLE+'" r="20"/>',
'<g></g>',
'<g><rect id="'+idRect+'" fill="'+FILL_RECT+'" height="20" width="30"/><text>'+textText+'</text></g>',
'<svg width="300" height="300" xmlns="http://www.w3.org/2000/svg"></svg>',
'<SvgClient><Attribute_String roleName="setup">${MODELNAME_DEFAULT},http://upload.wikimedia.org/wikipedia/commons/6/6b/Bitmap_VS_SVG.svg</Attribute_String></SvgClient>'
];
    return found;
  }
})();
