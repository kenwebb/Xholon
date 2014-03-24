/*!
 * A Xholon Workbook is a document (typically XML) for designing and implementing a Xholon application.
 * This client-side javascript file is packaged as a jQuery plugin.
 * For use with Xholon GWT Edition.
 *
 * Licensed under the MIT license  http://opensource.org/licenses/MIT .
 * Copyright (C) 2013, 2014 Ken Webb
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon/gwt/">Xholon Project website</a>
 * @since 0.1 (Created on January 21, 2012)
*/

(function($) {

  // Editor indexes
  var ED_NOTES = 0;
  var ED_PARAMS = 1; //4; // optional params editor
  var ED_IH = 2; //1;
  var ED_CD = 3; //2;
  var ED_CSH = 4; //3;
  // plus 2 or more script language editors
  var ED_SVG = 6; // 6 or higher
  
  /** Name of the model in the current workbook. */
  var modelName = 'Unknown';
  
  /** Array of CodeMirror editors. */
  var editor = new Array();
  
  /** The onChange timer */
  var onChangeTimeout;
  
  /** The onChange setTimeout() delay in ms. */
  var onChangeDelay = 5000; // was 400
  
  /** Whether or not to store changes to localStorage. */
  var shouldStoreChanges = false;
  
  /** Whether or not editor changes have been saved as a Gist. */
  var changesSavedAsGist = true;
  var savegithubButton = null; //$('button.savegithub')
  
  /** The CodeMirror editor whose mode is currently being updated. */
  var updateEditor = null;
  
  /** Information supplied by CodeMirror about the mode update. */
  var updateInfo = null;
  
  /**
   * "runsrc=none" tells editwb to NOT specify "src=lstr" when running the edited app,
   * which is what is expected for a Java-based app
   */
  var runsrc = "";
  
  /** Xholon webEdition: CSH rootName (ex: 'PhysicalSystem') */
  //var $cshRootName = 'PhysicalSystem';
  
  // Xholon webEdition: Application-specific code to insert into the xh script
  //var $appSpecific_postConfigure = '';
  //var $appSpecific_preAct = '';
  //var $appSpecific_act = '';
  //var $appSpecific_postAct = '';
  //var $appSpecific_processReceivedMessage = '';
  //var $appSpecific_toString = '';
  //var $appSpecificBehaviors = '';
  
  // Xholon webEdition: SVG to insert into the HTML
  //var $svgStr = '';
  //var $svgWidth = '0';
  //var $svgHeight = '0';
  
  // Xholon webEdition: jQuery dialog
  //var runtimeOverlay = null;
  //var xhApps = null;
  
  // Xholon webEdition: XML DOM
  //var xmlRoot = null;
  
  /**
   * Set a new theme, based on a user's selection.
   * @param node A DOM select element.
   */
  function selectTheme(node) {
    var theme = node.options[node.selectedIndex].innerHTML;
    $.each(editor, function(index, anEditor) {
      anEditor.setOption("theme", theme);
      anEditor.refresh();
    });
  }
  
  /**
   * Handle the onChange event.
   */
  function handleOnChange() {
    //console.log("handleOnChange()");
    if (changesSavedAsGist == true) {
      changesSavedAsGist = false;
      // to let the user know that there are changes not yet saved as a Gist
      savegithubButton.css('background-color', '#FCF2F2');
    }
    
    // update the modelName if it's changed
    if (updateEditor == editor[ED_NOTES]) {
      updateModelName();
    }
    
    updateModeOnChange();
    storeChanges();
  }
  
  /**
   * Update the model name in the notes Title,
   * in the html body h2,
   * and in the html head title.
   */
  function updateModelName() {
    editor[ED_NOTES].eachLine( function(lineHandle) {
      var str = lineHandle.text;
      if (str.indexOf("Title:") == 0) {
        // Title: This is a New Title
        title = str.substring(6).trim();
        if (!(title == modelName)) {
          modelName = title;
          // update the HTML titles
          $('h2.modelname > a').text(title);
          $('head > title').text(title);
        }
      }
    });
  }
  
  /**
   * Store changes to localStorage.
   */
  function storeChanges() {
    if (shouldStoreChanges) {
      var content = assembleXmlContent();
      localStorage.setItem(modelName, content);
    }
  }
  
  /**
   * Update the mode of a script editor.
   * This version of updateMode is typically called from the onChange event handler.
   */
  function updateModeOnChange() {
    if ((updateInfo.from.line == 0) && (updateInfo.to.line == 0)) {
      updateMode(updateEditor);
    }
  }
  
  /**
   * Update the mode of a script editor.
   * @param anEditor
   */
  function updateMode(anEditor) {
    var firstLine = anEditor.getLine(0);
    if (firstLine.indexOf("gwtjs") != -1) {anEditor.setOption("mode","javascript");}
  }
  
  /**
   * This method is for use by a Class Details (cd) editor.
   * 
   */
  function addAllChildren(anEditor) {
    //console.log("addAllChildren()");
    var str = '';
    var cur = anEditor.getCursor();
    var token = anEditor.getTokenAt(cur);
    var nodeName = token.state.context.tagName;
    //console.log(nodeName);
    var cshEditor = editor[ED_CSH];
    var cshContent = cshEditor.getValue();
    var cshStart = cshContent.indexOf("<" + nodeName);
    if (cshStart != -1) {
      cshStart = cshContent.indexOf("<", cshStart+1);
    }
    if (cshStart != -1) {
      var cshEnd = cshContent.indexOf("</" + nodeName);
      if (cshEnd != -1) {
        var nodeContent = cshContent.substring(cshStart, cshEnd);
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
              str += '<port name="' + childNameAttr + '" connector="' + childName + '"/>\n';
            } // end if
          } // end else
          childStart = nodeContent.indexOf("</", childEnd);
        } // end while
      } // end if
    } // end if
    if (str.length > 0) {
      anEditor.replaceRange(str, cur, cur);
    }
  }
  
  /**
   * This method is for use by a Class Details (cd) editor.
   */
  function addAllSelected(anEditor) {
    //console.log("addAllSelected()");
    var str = '';
    var cur = anEditor.getCursor();
    var token = anEditor.getTokenAt(cur);
    var nodeName = token.state.context.tagName;
    var cshEditor = editor[ED_CSH];
    var cshContent = cshEditor.getSelection();
    // TODO trim the selection
    //console.log(cshContent);
    if (cshContent.charAt(0) == '<') {
      // assume this is one or more XML elements
      // TODO
    }
    else {
      // assume this is a single highlighted token
      var nodeContent = cshContent;
      var childName = nodeContent;
      if (childName.length > 0) {
        var childNameAttr = childName.charAt(0).toLowerCase();
        if (childName.length > 1) {
          childNameAttr += childName.substring(1);
        }
        
        var $xml = editor2Xml(cshEditor);
        var $xmlNodeFrom = $xml.find(nodeName);
        var $xmlNodeTo = $xml.find(childName);
        var $xmlNodeRoot = $xml.children();
        if ($xmlNodeFrom.length > 0 && $xmlNodeTo.length > 0 && $xmlNodeRoot.length > 0) {
          var xpathExpression = getXPathExpression($xmlNodeFrom, $xmlNodeTo, $xmlNodeRoot);
          str += '<port name="' + childNameAttr + '" connector="' + xpathExpression + '"/>';
        }
      } // end if
    }
    if (str.length > 0) {
      anEditor.replaceRange(str, cur, cur);
    }
  }
  
  /**
   * Get a jQuery representation of the XML in an editor.
   * @param anEditor
   * @return A jQuery object.
   */
  function editor2Xml(anEditor) {
    var xmlString = anEditor.getValue();
    var xmlDoc = $.parseXML(xmlString);
    return $(xmlDoc);
  }
  
  /**
   * Find the XPath expression for a path from one XML node to another XML node.
   * @param sourceNode A jQuery-wrapped element in the XML DOM.
   * @param reffedNode A jQuery-wrapped element in the XML DOM.
   * @param rootNode The jQuery-wrapped root element in the XML DOM.
   * @return An XPath expression.
   */
  function getXPathExpression(sourceNode, reffedNode, rootNode) {
    var fromStr = sourceNode[0].nodeName;
    var toStr = reffedNode[0].nodeName;
    var rootStr = rootNode[0].nodeName;
    if (reffedNode.parent().is(fromStr)) {
      return toStr;
    }
    return 'ancestor::' + rootStr + '/descendant::' + toStr;
  }
  
  /**
   * Get selected range.
   * @param anEditor
   * @return A CodeMirror range object.
   */
  //function getSelectedRange(anEditor) {
  //  return { from: anEditor.getCursor(true), to: anEditor.getCursor(false) };
  //}
  
  /**
   * Auto-format selection (CodeMirror).
   * @param anEditor
   */
  function autoFormatSelection(anEditor) {
    //var range = getSelectedRange(anEditor);
    //anEditor.autoFormatRange(range.from, range.to); // doesn't work
  }
  
  /**
   * Auto-indent selection (CodeMirror).
   * @param anEditor
   */
  function autoIndentSelection(anEditor) {
    if (anEditor.somethingSelected()) {
      var startLine = anEditor.getCursor("start").line;
      var endLine = anEditor.getCursor("end").line;
      anEditor.operation(function() {
        for (var line = startLine; line <= endLine; line++) {
          anEditor.indentLine(line);
        }
      });
    }
  }
  
  /**
   * De-emphasize the first and last lines of an editor (CodeMirror).
   */
  function dmphasizeFirstLastLines(anEditor) {
    anEditor.addLineClass(0,"text","dmphasize");
    anEditor.addLineClass(anEditor.lineCount()-1,"text","dmphasize");
  }
  
  /**
   * Does an editor contain SVG ?
   * @return true or false
   */
  function isSvg(anEditor) {
    var firstLine = anEditor.getLine(0);
    if (firstLine && firstLine.indexOf("<SvgClient>") != -1) {
      return true;
    }
    return false;
  }
  
  /**
   * Does an editor contain params ?
   * @return true or false
   */
  function isParams(anEditor) {
    var firstLine = anEditor.getLine(0);
    if (firstLine && firstLine.indexOf("<params>") != -1) {
      return true;
    }
    return false;
  }
  
  /**
   * Save content as a post to a GitHub gist.
   */
  function saveAsGist(content) {
    var desc = 'Xholon Workbook';
    // replace amp chars in content
    content = content.replace(/&/g,"%26");
    content = content.replace(/\+/g,"%2B");
    var anEditor = editor[ED_NOTES];
    for (var i = 0; i < anEditor.lineCount(); i++) {
      var aLine = anEditor.getLine(i);
      var pos = aLine.indexOf('Title:');
      if (pos != -1) {
        desc = aLine.substring(pos + 6);
      }
    }
    var gu = $('input#gu').val();
    var gp = $('input#gp').val();
    var gistid = $('p.gistid').text();
    $.ajax({
      type: 'POST',
      url: 'http://www.primordion.com/Xholon/wb/savewb.php',
      data: 'd=' + desc + '&c=' + content + '&gp=' + gp + '&gu=' + gu + '&gid=' + gistid,
      success: function(result) {
        // extract and display the gistid from the returned JSON
        var obj = jQuery.parseJSON(result);
        var urlStr = obj.url;
        if (urlStr && urlStr.length > 0) {
          var resultGistid = urlStr.substr(urlStr.lastIndexOf('/')+1);
          $('p.gistid').text(resultGistid);
          changesSavedAsGist = true;
          savegithubButton.css('background-color', '');
        }
        else {
          alert(result);
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        alert(textStatus);
      }
    });
  }
  
  /**
   * Save content by showing it in a separate browser window/tab.
   */
  function saveToWindow(content) {
    var uriContent = 'data:text/plain;base64,' + Base64.encode(content);
    // Note: it's not possible to successfully specify a filename for the file
    window.open(uriContent, '_blank', 'width=' + 500
        + ',height=' + 400 + ',status=yes,resizable=yes,menubar,scrollbars');
  }
  
  /**
   * Assemble the workbook content into a single XML structure.
   * @return the assembled XML content
   */
  function assembleXmlContent() {
    var content = '<?xml version="1.0" encoding="UTF-8"?>\n';
    content += '<!--Xholon Workbook http://www.primordion.com/Xholon/gwt/ MIT License, Copyright (C) Ken Webb, ' + new Date().toString() + '-->\n';
    content += '<XholonWorkbook>';
    $.each(editor, function(index, anEditor) {
      var editorValue = anEditor.getValue();
      if (editorValue.length > 1) {
        content += '\n\n';
      }
      if (index == ED_NOTES) {content += '<Notes><![CDATA[\n';} // start notes
      content += editorValue;
      if (index == ED_NOTES) {content += '\n]]></Notes>';} // end notes
    });
    content += '\n\n</XholonWorkbook>';
    return content;
  }
  
  // Xholon runtime
  
  /**
   * Run workbook in a separate window using Xholon GWT.
   */
  function runInWindow(gui) {
    // store content to localStorage
    storeChanges();
    var uri = "../Xholon.html?app=" + modelName;
    if (!runsrc) {
      // this is NOT a Java-based app
      uri += "&src=lstr";
    }
    uri +="&gui=" + gui;
    window.open(uri, modelName, 'width=' + 1000
        + ',height=' + 800 + ',status=yes,resizable=yes,menubar,scrollbars');
  }
  
  /**
   * Create an SVG string from the Workbook,
   * if there's a SvgClient element that has SVG content.
   * Also set the SVG width and height.
   */
  /*function svgStr() {
    var svg1 = xmlRoot.children('SvgClient');
    if (svg1.length) {
      $svgStr = $.trim(svg1.children('Attribute_String').first().text().substring('data:image/svg+xml,'.length));
      if ($svgStr) {
        var svgElemStart = $svgStr.indexOf('<svg');
        if (svgElemStart != -1) {
          var svgElemEnd = $svgStr.indexOf('>', svgElemStart);
          if (svgElemEnd != -1) {
            var svgElem = $svgStr.substring(svgElemStart, svgElemEnd);
            // viewBox (ex: viewBox="0.0 0.0 189.0 462.0")
            var vbStartPos = svgElem.indexOf('viewBox="');
            if (vbStartPos != -1) {
              vbStartPos = vbStartPos + 9;
              var vbEndPos = svgElem.indexOf('"', vbStartPos);
              var vbElem = svgElem.substring(vbStartPos, vbEndPos);
              var vbArray = vbElem.split(' ');
              $svgWidth = vbArray[2];
              $svgHeight = vbArray[3];
            }
            else {
              // width
              var wStartPos = svgElem.indexOf('width="');
              if (wStartPos != -1) {
                wStartPos = wStartPos + 7;
                var wEndPos = svgElem.indexOf('"', wStartPos);
                $svgWidth = svgElem.substring(wStartPos, wEndPos);
              }
              // height
              var hStartPos = svgElem.indexOf('height="');
              if (hStartPos != -1) {
                hStartPos = hStartPos + 8;
                var hEndPos = svgElem.indexOf('"', hStartPos);
                $svgHeight = svgElem.substring(hStartPos, hEndPos);
              }
            }
          }
        }
      }
    }
  }*/
  
  /**
   * Get the default XholonWorkbook from the primordion site.
   * @param wbName Workbook name (ex: "defaultXholonWorkbook.xml" "Test1")
   * where "Test1" would be the name of a file in localStorage.
   */
  function getDefaultXholonWorkbook(wbName) {
    var app = getParameterByName("app");
    var src = getParameterByName("src");
    runsrc = getParameterByName("runsrc");
    //console.log("app: " + app);
    //console.log("src: " + src);
    if (app == "") {
      app = wbName;
    }
    if (src == "") {
      //console.log("getting from primordion");
      $.get(app, function(data) {
        populateTextAreas(data);
        configureEditors();
        editor[ED_NOTES].focus(); // give focus to the notes editor
      });
    }
    else if ((src == "lstr") && app) {
      //console.log("getting from localStorage");
      var xmlStr = localStorage.getItem(app);
      // parse as XML
      if (xmlStr) {
        var xmlDoc = $.parseXML(xmlStr);
        populateTextAreas(xmlDoc);
        configureEditors();
        updateModelName();
        editor[ED_NOTES].focus(); // give focus to the notes editor
      }
    }
    else if ((src == "gist") && app) {
      //console.log("getting from jsonp");
      var gistApi = "https://api.github.com/gists/" + app + "?callback=?";
      $.getJSON( gistApi, {
        format: "json"
      })
      .done( function(json) {
        //console.log(json);
        var jsonStr = JSON.stringify(json);
        //console.log(jsonStr);
        sessionStorage.setItem("editwbJsonStr", jsonStr);
        var xmlStr = json["data"]["files"]["xholonWorkbook.xml"]["content"];
        //console.log(xmlStr);
        // parse as XML
        if (xmlStr) {
          var xmlDoc = $.parseXML(xmlStr);
          populateTextAreas(xmlDoc);
          configureEditors();
          updateModelName();
          editor[ED_NOTES].focus(); // give focus to the notes editor
        }
      })
      .fail( function(jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        //console.log( "Request Failed: " + err );
      });
    }
    else {
      //console.log("getting from unknown");
    }
  }
  
  /**
   * Get the value of a URL search parameter.
   * @param name "app" or "src" or "runsrc"
   * examples of location.search:
   *  "?app=Test1&src=lstr"  look in localStorage
   *  ""  use defaultXholonWorkbook.xml from primordion site
   *  "?app=mywb.xml"  use mywb.xml from primordion site
   * @see stackoverflow 901115
   */
  function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
  }
  
  /**
   * Populate the text areas with content from the XholonWorkbook XML doc.
   * @param xholonWorkbookDoc an Xml Document
   */
  function populateTextAreas(xholonWorkbookDoc) {
    var root = xholonWorkbookDoc.documentElement;
    var xmlNode = root.firstElementChild;
    
    // notes
    $("div#xhednotes textarea")[0].firstChild.textContent = xmlNode.firstChild.nodeValue.trim() + "\n";
    xmlNode = xmlNode.nextElementSibling;
    
    // params
    if (xmlNode && (xmlNode.tagName.indexOf("params") != -1)) {
      $("div#xhedparams textarea")[0].firstChild.textContent = xmlSerialize(xmlNode);
      $("div#xhedparams").show();
      xmlNode = xmlNode.nextElementSibling;
    }
    
    // ih
    while (xmlNode.nodeName != "_-.XholonClass") {
      xmlNode = xmlNode.nextElementSibling;
    }
    $("div#xhedih textarea")[0].firstChild.textContent = xmlSerialize(xmlNode);
    xmlNode = xmlNode.nextElementSibling;
    
    // cd
    $("div#xhedcd textarea")[0].firstChild.textContent = xmlSerialize(xmlNode);
    xmlNode = xmlNode.nextElementSibling;
    
    // csh
    $("div#xhedcsh textarea")[0].firstChild.textContent = xmlSerialize(xmlNode);
    xmlNode = xmlNode.nextElementSibling;
    
    // behaviors
    var behNum = 0;
    while (xmlNode && (xmlNode.tagName.indexOf("behavior") != -1)) {
      behNum++;
      $("div#xhedbehs")
      .append("<div  id=\"xhedbeh" + behNum + "\">"
       + "<textarea class=\"javascript\">"
       //+ xmlNode.outerHTML
       + xmlSerialize(xmlNode)
       + "</textarea>"
       + "<p style=\"margin:1px 0px 1px 0px;\"></p>"
       + "</div>");
      xmlNode = xmlNode.nextElementSibling;
    }
    
    // svg
    if (xmlNode) {
      $("div#xhedsvg textarea")[0].firstChild.textContent = xmlSerialize(xmlNode);
    }

  }
  
  /**
   * Cross-browser XML serialization.
   * @param xmlNode
   */
  function xmlSerialize(xmlNode) {
    // https://developer.mozilla.org/en/docs/XMLSerializer
    // Chrome FF Opera Safari IE9?
    // but serializeToString method not implemented in IE9 ?
    try {
      return (new XMLSerializer()).serializeToString(xmlNode);
    }
    catch (e) {
      try {
        // IE
        return xmlNode.xml;
      }
      catch (e) {
        return "";
      }
    }
    return "";
  }
  
  /**
   * Configure the text areas as editors.
   */
  function configureEditors() {
    $('textarea').each( function(index) {
      editor[index] = CodeMirror.fromTextArea(this, {
        mode: {
          name: $(this).attr("class"),
          alignCDATA: true // for use with XML mode
        },
        lineNumbers: false,
        lineWrapping: true
      });
      editor[index].setOption("theme", $("div.selectTheme > select option:selected").val());
      var firstLine = editor[index].getLine(0);
      if (firstLine && firstLine.indexOf(" implName") != -1) {
        // implName="org.primordion.xholon.base.Behavior_gwtjs"
        if (firstLine.indexOf("\"org.primordion.xholon.base.Behavior_gwtjs\"") != -1) {
          dmphasizeFirstLastLines(editor[index]);
          updateMode(editor[index]);
        }
      }
      
      // handle onChange events
      editor[index].on("change",
        function(changeEditor, changeInfo) {
          clearTimeout(onChangeTimeout);
          // following 2 variables are for use by updateModeOnChange
          updateEditor = changeEditor;
          updateInfo = changeInfo;
          onChangeTimeout = setTimeout(handleOnChange, onChangeDelay);
        }
      );
      
      // autocomplete for Notes
      if (index == ED_NOTES) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.notesHint);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();}
        });
      }
      // autocomplete for Inheritance Hierarchy
      else if (index == ED_IH) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.quantityihHint);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          "Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
        });
        editor[index].setOption("autoCloseTags", true);
        dmphasizeFirstLastLines(editor[index]);
      }
      // autocomplete for Class Details
      else if (index == ED_CD) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.quantitycdHint);},
          "Ctrl-,": function(cm) {addAllSelected(editor[index]);},
          "Ctrl-.": function(cm) {addAllChildren(editor[index]);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          "Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
       });
       editor[index].setOption("autoCloseTags", true);
       dmphasizeFirstLastLines(editor[index]);
      }
      // autocomplete for Composite Structure Hierarchy
      else if (index == ED_CSH) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.quantitycshHint);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          "Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
        });
        editor[index].setOption("autoCloseTags", true);
        dmphasizeFirstLastLines(editor[index]);
      }
      // autocomplete for params
      else if ((index == ED_PARAMS) && (isParams(editor[index]))) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.paramsHint);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          "Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
        });
        editor[index].setOption("autoCloseTags", true);
        dmphasizeFirstLastLines(editor[index]);
      }
      // autocomplete for SVG
      else if ((index >= ED_SVG) && (isSvg(editor[index]))) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.svgHint);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          "Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
        });
        editor[index].setOption("autoCloseTags", true);
        dmphasizeFirstLastLines(editor[index]);
        ED_SVG = index; // set ED_SVG to it's actual value, in case there are extra behavior editors
      }
      // autocomplete for JavaScript behaviors
      else if (index > ED_CSH) {
        editor[index].setOption("extraKeys", {
          "Ctrl-Space": function(cm) {CodeMirror.showHint(cm, CodeMirror.hint.javascript);},
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();},
          //"Ctrl-/": function(cm) {autoIndentSelection(editor[index]);},
          Tab: function(cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces, "end", "+input");
          }
        });
      }
      // undo redo for all other editors
      else {
        editor[index].setOption("extraKeys", {
          "Ctrl-Z": function(cm) {editor[index].undo();},
          "Ctrl-Shift-Z": function(cm) {editor[index].redo();}
        });
      }
      
    });  
  }
  
  // methods
  var methods = {
  
  /**
   * postConfigure
   */
  postConfigure : function() {
    
    (function(){
      // remove layerX and layerY which are used by jQuery and deprecated in Chrome
      // see: http://stackoverflow.com/questions/7825448/webkit-issues-with-event-layerx-and-event-layery
      var all = $.event.props,
          len = all.length,
          res = [];
      while (len--) {
        var el = all[len];
        if (el != 'layerX' && el != 'layerY') res.push(el);
      }
      $.event.props = res;
    }());

    modelName = $.trim($('h2.modelname > a').text());
    savegithubButton = $('button.savegithub');
    
    getDefaultXholonWorkbook("defaultXholonWorkbook.xml");
    
    if (('localStorage' in window) && (window['localStorage'] !== null)) {
      shouldStoreChanges = true;
    }
    else {
      shouldStoreChanges = false;
    }

    $('button.savecontent').click(function(event) {
      var content = assembleXmlContent();
      saveToWindow(content);
    });
    
    $('button.savegithub').click(function(event) {
      var content = assembleXmlContent();
      saveAsGist(content);
      changesSavedAsGist = true;
    });
    
    /*$('button.selectall').click(function() {
      $.each(editor, function(index, anEditor) {
        var lineCount = anEditor.lineCount();
        var lenLastLine = anEditor.getLine(lineCount-1).length;
        anEditor.setSelection({line:0,ch:0}, {line:lineCount-1, ch:lenLastLine});
      });
    });*/
    
    /*$('button.unselectall').click(function() {
      $.each(editor, function(index, anEditor) {
        anEditor.setSelection({line:0,ch:0}, {line:0, ch:0});
      });
      $('body')[0].scrollIntoView();
    });*/
    
    $('button.refresh').click(function() {
      $.each(editor, function(index, anEditor) {
        anEditor.refresh();
      });
    });
    
    $('button.runclsc').click(function() {
      runInWindow('clsc');
    });
    
    $('button.rund3cp').click(function() {
      runInWindow('d3cp');
    });
    
    $('button.runnone').click(function() {
      runInWindow('none');
    });
    
    $('div.selectTheme > select').bind('change keyup',function () {
      selectTheme(this);
    });
    
    return this;
    // end postConfigure
  },
  
  /**
   * getEditor
   */
  getEditor : function() {
    return editor;
  },
  
  /**
   * Assemble the workbook content into a single XML structure.
   * @return the assembled XML content
   */
  assemble : function() {
    return assembleXmlContent();
  },
  
  /**
   * Toggle whether or not to continuously save changes to localStorage.
   */
  toggleStore : function() {
    if (shouldStoreChanges) {
      shouldStoreChanges = false;
    }
    else {
      shouldStoreChanges = true;
    }
  },
  
  /**
   * Get the onChange timeout delay.
   * If a delay value is passed in, and if delay > 0, then set the internal onChangeDelay to that value.
   * @param delay The setTimeout() delay in ms (optional).
   */
  storeTimeout : function(delay) {
    if (delay && delay > 0) {
      onChangeDelay = delay;
    }
    return onChangeDelay;
  }
  
  }; // end methods
  
  /**
   * not sure if this is needed
   */
  $.fn.xholonWorkbook = function(method) {
    if ( methods[method] ) {
      return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return methods.postConfigure.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' does not exist on jQuery.xholonWorkbook' );
    }
    return this;
  };
})( jQuery );

