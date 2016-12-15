/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.mech.story;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Screenplay
 * 
 * Example of JSON for use in various functions:
{
  "addIconsToPlaces":
    [
      ["Beach", null, "images/losttv/640px-20100726_Kalamitsi_Beach_Ionian_Sea_Lefkada_island_Greece.png"],
      ["Beach", "Crash site", "images/losttv/MV5BMTQ4ODYyNDAwNF5BMl5BanBnXkFtZTgwODgyNDY2MjE@._V1_.png"],
      ["Front section of the plane", null, "images/losttv/MV5BOGZlZDRhYjMtYWEyYy00MDk3LTkzNTItZDNiMzc2YmZjNGE2XkEyXkFqcGdeQXVyNTY1MDAxMzc@._V1_.png"],
      ["Plane", null, "images/losttv/AAEAAQAAAAAAAAMiAAAAJGVlYTU5Y2YyLWQwMzYtNDlmZS04MDdlLWI0ZjJjZWRhYjk4ZQ.png"],
      ["Jungle", null, "images/losttv/Tioman_Rainforest.png"],
      ["Beach area", null, "images/losttv/mokuleia-beach-2.png"],
      ["Mountain", null, "images/losttv/hawa49879.png"],
      ["Beach area", "Bamboo forest", "images/losttv/MV5BNmQ4YzA3OTktYmIwOS00OTI5LWIyNGUtYzA1Njc0YTVjZmQzXkEyXkFqcGdeQXVyNTY1MDAxMzc@._V1_.png"],
      ["Overgrown grass field", null, "images/losttv/640px-Setaria_verticillata_W_IMG_1085.png"],
      ["Tree trunk", null, "images/losttv/Yellow_birch_trunk.png"]
    ],
  "formatPersonName":
    ["BOONE","CHARLIE","CLAIRE","HURLEY","JACK","JIN","KATE","LOCKE","MAN","MICHAEL","PILOT","ROSE","SAYID","SAWYER","SHANNON","SUN","WALT","FLIGHT ATTENDANT #1","FLIGHT ATTENDANT #2","FLIGHT ATTENDANT #3"],
  "outputPersonNames":
    {
    "Jack": "images/losttv/MV5BMjE2MTUxNTQzM15BMl5BanBnXkFtZTcwODM3NjI5OQ@@._V1._SX100_SY140_.png",
    "Sayid": "images/losttv/MV5BMTc3OTIzMDAzOF5BMl5BanBnXkFtZTcwOTQ4MDIyMw@@._V1._SX100_SY140_.png",
    "Claire": "images/losttv/MV5BMTU3NzEwMzY0Ml5BMl5BanBnXkFtZTcwMjU2ODg4OQ@@._V1._SX100_SY140_.png",
    "Kate": "images/losttv/MV5BMTc3NjgxOTAwMV5BMl5BanBnXkFtZTcwNTQ5Nzg4OQ@@._V1_UY98_CR5,0,67,98_AL_.png",
    "Hurley": "images/losttv/MV5BMTc5NDE2NDEwOF5BMl5BanBnXkFtZTcwMDM4Nzg4OQ@@._V1._SX100_SY140_.png",
    "Boone": "images/losttv/MV5BMjM0NDY4MjAxM15BMl5BanBnXkFtZTcwNDU3NDk4OQ@@._V1._SX100_SY140_.png",
    "Shannon": "images/losttv/MV5BMTQ1MzAwOTAxMl5BMl5BanBnXkFtZTcwOTk2NDk4OQ@@._V1._SX100_SY140_.png",
    "Charlie": "images/losttv/MV5BNDI1OTIyODE0M15BMl5BanBnXkFtZTcwMzA5ODg4OQ@@._V1._SX100_SY140_.png",
    "Jin": "images/losttv/MV5BMTg5MjUyNzkzMl5BMl5BanBnXkFtZTcwODA1ODg4OQ@@._V1._SX100_SY140_.png",
    "Sun": "images/losttv/MV5BMTc1NDM4MDAzOF5BMl5BanBnXkFtZTcwMDc1ODg4OQ@@._V1._SX100_SY140_.png",
    "Walt": "images/losttv/MV5BNjExODgyMDU1N15BMl5BanBnXkFtZTcwNzE3NDk4OQ@@._V1._SX100_SY140_.png",
    "Michael": "images/losttv/MV5BMTcyNjU1ODYxM15BMl5BanBnXkFtZTcwMjI1NDk4OQ@@._V1._SX100_SY140_.png",
    "Rose": "images/losttv/MV5BMTQ0MTYyNzM2N15BMl5BanBnXkFtZTcwMTQ4NDk4OQ@@._V1._SX100_SY140_.png",
    "Sawyer": "images/losttv/MV5BMTM1NDEyNjcxOV5BMl5BanBnXkFtZTcwMTI4NjcxMw@@._V1._SX100_SY140_.png",
    "Flight Attendant #1": "images/losttv/MV5BMjAxMTQwMzY4MV5BMl5BanBnXkFtZTcwOTA3NzE5OQ@@._V1._SX100_SY140_.png",
    "Locke": "images/losttv/MV5BMjI1Njk0ODQ3N15BMl5BanBnXkFtZTcwNzUwMjI0Mw@@._V1._SX100_SY140_.png",
    "NARRATOR": "images/losttv/zelda5_circ.png"
    }
}
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Dec 5, 2016)
 */
public class Screenplay extends XholonWithPorts {
  
  private static final String SCREENPLAY_FORMAT_IMSDB    = "imsdb"; // DEFAULT
  private static final String SCREENPLAY_FORMAT_FOUNTAIN = "fountain";
  
  private String roleName = null;
  
  /**
   * Format of the screenplay text.
   * imsdb    - HTML <b> format used at http://www.imsdb.com/
   * fountain - format defined at http://fountain.io/
   */
  private String format = SCREENPLAY_FORMAT_IMSDB;
  
  private String sceneLocationRoot = "Universe";
  
  private String timewords = "NIGHT, DAY, EVENING";
  
  /**
   * Whether or not to support speech.
   */
  private boolean speech = false;
  
  // a JSON string
  private String options = null;
  
  private Object fountainTokenized = null;
  
  public Screenplay() {
    requireFountain();
  }
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  private String val = null;
  
  @Override
  public String getVal_String() {
    return val;
  }
  
  @Override
  public void setVal(String val) {
    this.val = val;
  }
  
  @Override
  public Object getVal_Object() {
    return val;
  }
  
  @Override
  public void setVal(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void postConfigure() {
    if (val == null) {
      // the screenplay and (optionally) the options, may be in child Attribute_String nodes
      IXholon node = this.getFirstChild();
      while (node != null) {
        String rname = node.getRoleName();
        if (rname != null) {
          switch (rname) {
          case "screenplay": this.setVal(node.getVal_String());   break;
          case "options":    this.options = node.getVal_String(); break;
          default: break;
          }
        }
        IXholon nextSib = node.getNextSibling();
        node.removeChild();
        node = nextSib;
      }
    }
    switch (format) {
    case SCREENPLAY_FORMAT_IMSDB:
      this.parseScreenplay(format, sceneLocationRoot, timewords, speech, options, null);
      break;
    case SCREENPLAY_FORMAT_FOUNTAIN:
      this.fountainTokenized = this.tokenizeFountain(val);
      if (this.fountainTokenized != null) {
        this.parseScreenplay(format, sceneLocationRoot, timewords, speech, options, this.fountainTokenized);
      }
      break;
    default:
      break;
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    switch (format) {
    case SCREENPLAY_FORMAT_IMSDB:
      break;
    case SCREENPLAY_FORMAT_FOUNTAIN:
      // this code should only be run once
      if (this.fountainTokenized == null) {
        this.fountainTokenized = this.tokenizeFountain(val);
        if (this.fountainTokenized != null) {
          this.parseScreenplay(format, sceneLocationRoot, timewords, speech, options, this.fountainTokenized);
        }
      }
      break;
    default:
      break;
    }
    super.act();
  }
  
  /**
   * Parse the screenplay.
   */
  protected native void parseScreenplay(String format, String sceneLocationRoot, String timewords, boolean speech, String options, Object fountainTokenized) /*-{
  var debugStr = "";
  
  // DEBUG
  var debugFlow = function(str) {
    debugStr += str + "\n";
  }
  
  debugFlow("parseScreenplay start");
  
  var $this = this;
  if ((format == "imsdb") && (this.text() == null)) {
    this.println("The text of the screenplay is missing.");
    return;
  }
  
  var timestep = 0;
  var personXsn = $wnd.xh.util.newXholonSortedNode().obj("!!person");
  personXsn = $wnd.xh.util.newXholonSortedNode().obj("NARRATOR");
  personXsn = $wnd.xh.util.newXholonSortedNode().obj("UNKNOWN PERSON");
  var placeXsn = $wnd.xh.util.newXholonSortedNode().obj("!!place");
  var storyNode = this.xpath("ancestor::StorySystem/Story");
  var scenesXmlStr = "";
  var avatarScriptActive = false;
  var sceneObj = null;
  var trimmedScriptText = "";
  var service = $wnd.xh.service("XholonHelperService");
  
  var timewordsArr = ["NIGHT", "DAY"];
  if (timewords) {
    timewordsArr = timewords.split(",");
  }
  timewordsXsn = $wnd.xh.util.newXholonSortedNode().obj(timewordsArr[0]);
  for (var i = 1; i < timewordsArr.length; i++) {
    timewordsXsn.add(timewordsArr[i]);
  }
  timewordsXsn.inOrderPrint(0);
  $wnd.xh.movie.timewordsXsn = timewordsXsn;
  
  String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1).toLowerCase();
  }
  
  var addIconsToPlacesArr = null;
  var formatPersonNameArr = null;
  var outputPersonNamesObj = null;
  if (options) {
    var json = $wnd.JSON.parse(options);
    if (json) {
      addIconsToPlacesArr = json["addIconsToPlaces"];
      formatPersonNameArr = json["formatPersonName"];
      outputPersonNamesObj = json["outputPersonNames"];
    }
  }
  
  // DEBUG
  var debugFlow = function(str) {
    debugStr += str + "\n";
  }
  
  // PARSE
  var formatPersonName = function(wordArr) {
    debugFlow("formatPersonName");
    var wordsOrig = wordArr.join(" ");
    switch (wordsOrig) {
    case "":
      return "NARRATOR"; // TODO handle this properly
    default:
      if (formatPersonNameArr && formatPersonNameArr.length > 0) {
        // there is an explicit list of words that should be recognized as names
        if (formatPersonNameArr.indexOf(wordsOrig) != -1) {
          return capitalize(wordArr);
        }
        else {
          // this person is not in the list of recognized names
          return "UNKNOWN PERSON"; // TODO handle this properly
        }
      }
      else {
        // there is no explicit list of names
        return capitalize(wordArr);
      }
    }
  }
  
  // PARSE
  var capitalize = function(wordArr) {
    for (var i = 0; i < wordArr.length; i++) {
      wordArr[i] = wordArr[i].capitalize();
    }
    var str = wordArr.join(" ");
    return str;
  }
  
  // PARSE IMSDB
  var processElement = function(el) {
    debugFlow("processElement");
    if (!el) {return;}
    switch(el.nodeName) {
    case "B":
      processB(el);
      break;
    default:
      break;
    }
  }
  
  // PARSE IMSDB
  var processB = function(el) {
    debugFlow("processB");
    var str = el.firstChild.nodeValue.trim();
    trimmedScriptText += str + "\n"; // write out a trimmed version of the text
    if (!storyNode.role()) {
      storyNode.role(str);
      return;
    }
    var arr = str.split(" ");
    switch (arr[0]) {
    case "INT.":
      processScene(str);
      processPlace(str, 5); //.substring(5));
      break;
    case "INT./EXT.":
      processScene(str);
      processPlace(str, 10); //.substring(10));
      break;
    case "EXT.":
      processScene(str);
      processPlace(str, 5); //.substring(5));
      break;
    case "EXT./INT.":
      processScene(str);
      processPlace(str, 10); //.substring(10));
      break;
    case "ANGLE":
      
      break;
    case "CLOSEUP":
      
      break;
    default:
      processPerson(arr);
      break;  
    }
  }
  
  // PARSE FOUNTAIN
  var processSceneHeading = function(str) {
    debugFlow("processSceneHeading");
    var arr = str.split(" ");
    switch (arr[0].toUpperCase()) {
    case "INT":
    case "INT.":
    case "INT/EXT":
    case "INT./EXT":
    case "INT./EXT.":
    case "I/E":
    case "I/E.":
    case "EXT":
    case "EXT.":
    //case "EXT./INT.": // not specified for Fountain
    case "EST":
    case "EST.":
      processScene(str);
      processPlace(str, arr[0].length+1);
      break;
    default:
      // ex: .THIS IS A SCENE HEADING
      processScene(str);
      processPlace(str, 0);
      break;  
    }
  }
  
  // PARSE
  var processPerson = function(arr) {
    debugFlow("processPerson");
    // the contents of the array is probably the name of a person or other character
    var len = arr.length;
    if (len == 0) {return;}
    // remove abbreviations at end of word arr; possibly more than one abbreviations
    var looking4Abbrevs = true;
    while (looking4Abbrevs && (len > 1)) {
      switch (arr[len-1].toUpperCase()) {
        case "(V.O.)":
        case "(O.S.)":
        case "(O.C.)":
        case "(CONT'D)": // or (cont'd)
        case "(V.0.)": // a common error that uses zero instead of oh
        case "(V.O.)(CONT'D)": // a combination with no space between the two
          arr.pop();
          len = arr.length;
          break;
        default:
          looking4Abbrevs = false;
          break;
      }
    }
    if (scenesXmlStr.length > 0) {
      var personName = formatPersonName(arr);
      personXsn.add(personName);
      if (avatarScriptActive) {
        // ?
      }
      avatarScriptActive = true;
      
      if (!sceneObj[personName]) {
        sceneObj[personName] = {};
        sceneObj[personName].prevSeqNum = 0;
        sceneObj[personName].text = "";
      }
      sceneObj.currentPersonRoleName = personName;
    }
  }
  
  // PARSE
  var processPlace = function(sceneName, startPos) {
    debugFlow("processPlace");
    // create and use a modified sceneName
    var modSceneName = $wnd.xh.movie.makePlaceRoleName(sceneName, startPos, timewordsXsn, true);
    if (modSceneName.length > 0) {
      //var service = $wnd.xh.service("XholonHelperService");
      var placesNode = $this.xpath("ancestor::StorySystem/Story/" + sceneLocationRoot);
      var data = [modSceneName, " - ", "SceneLocation"];
      service.call(-2023, data, placesNode); // ISignal.ACTION_PASTE_MERGE_FROMROLENAMESTRING = -2023
    }
  }
  
  // PARSE
  var processScene = function(slug) {
    debugFlow("processScene");
    // the scene "slug" line (INT or EXT, location, time)
    if (!slug || slug.length == 0) {return;}
    if (sceneObj) {
      // write out the complete scene
      scenesXmlStr += '<Scene roleName="' + sceneObj.slug + '" duration="' + sceneObj.seqNum + '">\n';
      if (sceneObj.anno.length > 0) {
        scenesXmlStr += ' <Annotation>' + sceneObj.anno + '</Annotation>\n';
      }
      if (avatarScriptActive) {
        for (var personName in sceneObj) {
          if (typeof sceneObj[personName] === "object") {
            scenesXmlStr += '\n <AvatarScript avatar="' + personName + '"><Attribute_String><' + '!' + '[CDATA[\n';
            scenesXmlStr += sceneObj[personName].text;
            scenesXmlStr += ' ]' + ']' + '></Attribute_String></AvatarScript>\n';
          }
        }
        avatarScriptActive = false;
      }
      scenesXmlStr += "</Scene>\n\n";
    }
    sceneObj = {};
    sceneObj.slug = slug;
    sceneObj.seqNum = 0;
    sceneObj.anno = "";
  }
  
  // PARSE
  var processTextNode = function(text) {
    debugFlow("processTextNode");
    //var text = tn.nodeValue;
    text = removeCrLfs(text);
    if (text.length == 0) {return;}
    trimmedScriptText += text.trim() + "\n"; // to write out a trimmed version of the text
    if (avatarScriptActive) {
      // convert semi-colons into commas (Avatar IF language requires this)
      if (sceneObj) {
        var cprn = sceneObj.currentPersonRoleName;
        if (sceneObj.seqNum > sceneObj[cprn].prevSeqNum) {
          sceneObj[cprn].text += 'wait ' + (sceneObj.seqNum - sceneObj[cprn].prevSeqNum) + ';\n';
        }
        if (speech) {
          sceneObj[cprn].text += 'out caption,speech,anim ' + text.replace(/;/g, ',') + ';\n'; // SPEECH
        }
        else {
          sceneObj[cprn].text += 'out caption,anim '        + text.replace(/;/g, ',') + ';\n';
        }
        sceneObj.seqNum++;
        sceneObj[cprn].prevSeqNum = sceneObj.seqNum;
      }
    }
    else if (sceneObj) {
      if (sceneObj.anno.length > 0) {
        // separate existing text from new text with a space character
        sceneObj.anno += " ";
      }
      sceneObj.anno += text;
    }
  }
  
  // PARSE
  var removeCrLfs = function(str) {
    // replace whitespace (CR, LF, duplicate spaces) with a single space
    // trim white space from front and back
    str = str.replace(/\s+/g, ' ').trim();
    return str;
  }
  
  // NOT USED
  //var toString = function() {
  //  return $this.text();
  //}
  
  // PARSE IMSDB start
  if (format == "imsdb") {
    debugFlow("PARSE IMSDB start");
    var el = $wnd.document.createElement("div");
    el.innerHTML = this.text();
    var pre = el.querySelector("pre");
    var node = pre.firstChild;
    while (node) {
      switch (node.nodeType) {
        case 1: // Element b
          processElement(node);
          break;
        case 3: // TextNode
          processTextNode(node.nodeValue);
          break;
        default:
          break;
      }
      node = node.nextSibling;
    }
  }
  // PARSE FOUNTAIN start
  else if (format == "fountain") {
    var tokens = fountainTokenized.tokens;
    $wnd.console.log(tokens.length);
    for (var i = 0; i < tokens.length; i++) {
      var token = tokens[i];
      if (token.text) {
        token.text = token.text.replace("&", "&amp;");
        // TODO also replace < with &lt;  ???
      }
      switch (token.type) {
        // title types
        case 'title': break;
        case 'credit': break;
        case 'author': break;
        case 'authors': break;
        case 'source': break;
        case 'notes': break;
        case 'draft_date': break;
        case 'date': break;
        case 'contact': break;
        case 'copyright': break;
        // 
        case 'scene_heading':
          if (scenesXmlStr.length == 0) {
            // give scenesXmlStr some content, so processPerson() will work correctly in the first scene
            scenesXmlStr = "\n";
          }
          processSceneHeading(token.text);
          break;
        case 'transition': break;
        case 'dual_dialogue_begin': break;
        case 'dialogue_begin': break;
        case 'character':
          processPerson(token.text.split(" "));
          break;
        case 'parenthetical':
          processTextNode(token.text);
          break;
        case 'dialogue':
          processTextNode(token.text);
          break;
        case 'dialogue_end': break;
        case 'dual_dialogue_end': break;
        case 'section': break;
        case 'synopsis': break;
        case 'note': break;
        case 'boneyard_begin': break;
        case 'boneyard_end': break;
        case 'action':
          processTextNode(token.text);
          break;
        case 'centered':
          // an action
          processTextNode(token.text);
          break;
        case 'page_break': break;
        case 'line_break': break;
        default:
          break;
      }
    }
  }
  
  // OUTPUT
  var outputPersonNames = function() {
    debugFlow("outputPersonNames");
    var personArr = personXsn.toArray();
    var personXmlStr = "";
    for (var i = 0; i < personArr.length; i++) {
      var wordStr = personArr[i].obj();
      personXmlStr += '<Character roleName="' + wordStr + '">';
      if (outputPersonNamesObj) {
        var iconName = outputPersonNamesObj[wordStr];
        if (iconName) {
          personXmlStr += "<Icon>" + iconName + "</Icon>";
        }
      }
      personXmlStr += '</Character>\n';
    }
    personXmlStr = '<_-.characters>\n' + personXmlStr + '</_-.characters>\n';
    $this.println(personXmlStr);
    var peopleNode = $this.xpath("ancestor::StorySystem/Story/Characters");
    if (peopleNode) {
      peopleNode.append(personXmlStr);
    }
    
    var p = peopleNode.first();
    while (p) {
      p.action('param caption #xhanim>span:nth-child(2);param anim grow 1.1;');
      p = p.next();
    }
  }
  
  // OUTPUT
  var outputScenes = function() {
    debugFlow("outputScenes");
    // outputScenes is only called once
    if (scenesXmlStr.length > 0) {
      if (avatarScriptActive) {
        avatarScriptActive = false;
      }
      if (sceneObj) {
        // write out the last scene
        scenesXmlStr += '<Scene roleName="' + sceneObj.slug + '" duration="' + sceneObj.seqNum + '">\n';
        if (sceneObj.anno.length > 0) {
          scenesXmlStr += ' <Annotation>' + sceneObj.anno + '</Annotation>\n';
        }
        //if (avatarScriptActive) { // commented out Dec 1, 2016
          for (var personName in sceneObj) {
            if (typeof sceneObj[personName] === "object") {
              scenesXmlStr += '\n <AvatarScript avatar="' + personName + '"><Attribute_String><' + '!' + '[CDATA[\n';
              scenesXmlStr += sceneObj[personName].text;
              scenesXmlStr += ' ]' + ']' + '></Attribute_String></AvatarScript>\n';
            }
          }
          avatarScriptActive = false;
        //}
        scenesXmlStr += "</Scene>\n\n";
      }
      
    }
    scenesXmlStr = "<_-.scenes>\n\n" + scenesXmlStr + "</_-.scenes>\n";
    $this.println(scenesXmlStr);
    var scenesNode = $this.xpath("ancestor::StorySystem/Story/Scenes");
    if (scenesNode) {
      scenesNode.append(scenesXmlStr);
    }
  }
  
  // OUTPUT
  var addIconsToPlaces = function() {
    debugFlow("addIconsToPlaces");
    if (addIconsToPlacesArr) {
      for (var i = 0; i < addIconsToPlacesArr.length; i++) {
        var innerArr = addIconsToPlacesArr[i];
        addIconToPlace(innerArr[0], innerArr[1], innerArr[2]);
      }
    }
  }
  
  // OUTPUT
  var addIconToPlace = function(placeRoleName1, placeRoleName2, iconUrl) {
    debugFlow("addIconToPlace");
    var place = $this.xpath("ancestor::StorySystem/Story/" + sceneLocationRoot + "/SceneLocation[@roleName='" + placeRoleName1 + "']");
    if (place == null) {return;}
    if (placeRoleName2 != null) {
      place = place.xpath("SceneLocation[@roleName='" + placeRoleName2 + "']");
      if (place == null) {return;}
    }
    var iconStr = "<Icon>" + iconUrl + "</Icon>";
    service.call(-2019, iconStr, place); // ISignal.ACTION_PASTE_MERGE_FROMSTRING = -2019
  }
  
  // OUTPUT
  outputPersonNames();
  outputScenes();
  addIconsToPlaces();
  //$wnd.console.log(trimmedScriptText);
  
  // DEBUG
  $wnd.console.log(debugStr);
  }-*/;
  
  /**
   * Let fountain.js tokenize the screenplay that's in fountain format.
   * @param script The fountain-format screenplay script.
   * @return A JavaScript Object, containing an array of tokenized parts of the script.
   */
  protected native String tokenizeFountain(String script) /*-{
    $wnd.console.log("tokenizeFountain() is trying to find fountain ...");
    var parsed = null;
    if ($wnd.fountain) {
      parsed = $wnd.fountain.parse(script, true);
      $wnd.console.log(parsed);
    }
    return parsed;
  }-*/;

  /**
   * Load the fountain JavaScript file.
   */
  protected native void requireFountain() /*-{
    $wnd.xh.require("fountain");
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("format".equals(attrName)) {
      this.format = attrVal;
    }
    if ("sceneLocationRoot".equals(attrName)) {
      this.sceneLocationRoot = attrVal;
    }
    else if ("timewords".equals(attrName)) {
      this.timewords = attrVal;
    }
    else if ("speech".equals(attrName)) {
      this.speech = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }

  @Override
  public String toString() {
    return this.getName() + " " + getVal_String();
  }
  
}
