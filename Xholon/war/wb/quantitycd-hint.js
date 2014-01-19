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
  CodeMirror.quantitycdHint = function(anEditor) {
    var cur = anEditor.getCursor();
    var token = anEditor.getTokenAt(cur);
    token = {start: cur.ch, end: cur.ch, string: "", state: token.state,
                       className: token.string == "." ? "property" : null};
    
    return {list: getCompletions(token),
            from: {line: cur.line, ch: token.start},
            to: {line: cur.line, ch: token.end}};
  }

  function getCompletions(token) {
    var found = [];
    var nodeName = token.state.context.tagName;
    var editor = $('div.XholonApplications').xholonWorkbook('getEditor');
    var cshEditor = editor[3];
    var cshContent = cshEditor.getValue();
    //alert("[" + nodeName + "]" + cshContent);
    // TODO gets wrong node if there's a container that also starts with nodeName
    // ex: Projectile Projectiles ProjectileVelocity
    // I need to find an exact match
    var cshStart = cshContent.indexOf("<" + nodeName);
    if (cshStart != -1) {
      cshStart = cshContent.indexOf("<", cshStart+1);
    }
    if (cshStart != -1) {
      var cshEnd = cshContent.indexOf("</" + nodeName);
      if (cshEnd != -1) {
        var nodeContent = cshContent.substring(cshStart, cshEnd);
        //alert(nodeContent);
        var childStart = nodeContent.indexOf("</");
        while (childStart != -1) {
          childStart += 2;
          var childEnd = nodeContent.indexOf(">", childStart);
          if (childEnd == -1) {
            break; // oops
          }
          else {
            var childName = nodeContent.substring(childStart, childEnd);
            if (childName.length > 0) {
              var childNameAttr = childName.charAt(0).toLowerCase();
              if (childName.length > 1) {
                childNameAttr += childName.substring(1);
              }
              var str = '<port name="' + childNameAttr + '" connector="' + childName + '"/>';
              found.push(str);
            } // end if
          } // end else
          childStart = nodeContent.indexOf("</", childEnd);
        } // end while
      } // end if
    } // end if
    
    // default if no matches were found
    if (found.length == 0) {
      found.push('<port name="" connector=""/>');
    }
    return found;
  }
})();
