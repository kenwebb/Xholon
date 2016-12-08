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
[
  { "addIconsToPlaces":
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
    ]
  },
  { "outputPersonNames":
    [
      ["Jack", "images/losttv/MV5BMjE2MTUxNTQzM15BMl5BanBnXkFtZTcwODM3NjI5OQ@@._V1._SX100_SY140_.png"],
      ["Sayid", "images/losttv/MV5BMTc3OTIzMDAzOF5BMl5BanBnXkFtZTcwOTQ4MDIyMw@@._V1._SX100_SY140_.png"],
      ["Claire", "images/losttv/MV5BMTU3NzEwMzY0Ml5BMl5BanBnXkFtZTcwMjU2ODg4OQ@@._V1._SX100_SY140_.png"],
      ["Kate", "images/losttv/MV5BMTc3NjgxOTAwMV5BMl5BanBnXkFtZTcwNTQ5Nzg4OQ@@._V1_UY98_CR5,0,67,98_AL_.png"],
      ["Hurley", "images/losttv/MV5BMTc5NDE2NDEwOF5BMl5BanBnXkFtZTcwMDM4Nzg4OQ@@._V1._SX100_SY140_.png"],
      ["Boone", "images/losttv/MV5BMjM0NDY4MjAxM15BMl5BanBnXkFtZTcwNDU3NDk4OQ@@._V1._SX100_SY140_.png"],
      ["Shannon", "images/losttv/MV5BMTQ1MzAwOTAxMl5BMl5BanBnXkFtZTcwOTk2NDk4OQ@@._V1._SX100_SY140_.png"],
      ["Charlie", "images/losttv/MV5BNDI1OTIyODE0M15BMl5BanBnXkFtZTcwMzA5ODg4OQ@@._V1._SX100_SY140_.png"],
      ["Jin", "images/losttv/MV5BMTg5MjUyNzkzMl5BMl5BanBnXkFtZTcwODA1ODg4OQ@@._V1._SX100_SY140_.png"],
      ["Sun", "images/losttv/MV5BMTc1NDM4MDAzOF5BMl5BanBnXkFtZTcwMDc1ODg4OQ@@._V1._SX100_SY140_.png"],
      ["Walt", "images/losttv/MV5BNjExODgyMDU1N15BMl5BanBnXkFtZTcwNzE3NDk4OQ@@._V1._SX100_SY140_.png"],
      ["Michael", "images/losttv/MV5BMTcyNjU1ODYxM15BMl5BanBnXkFtZTcwMjI1NDk4OQ@@._V1._SX100_SY140_.png"],
      ["Rose", "images/losttv/MV5BMTQ0MTYyNzM2N15BMl5BanBnXkFtZTcwMTQ4NDk4OQ@@._V1._SX100_SY140_.png"],
      ["Sawyer", "images/losttv/MV5BMTM1NDEyNjcxOV5BMl5BanBnXkFtZTcwMTI4NjcxMw@@._V1._SX100_SY140_.png"],
      ["Flight Attendant #1", "images/losttv/MV5BMjAxMTQwMzY4MV5BMl5BanBnXkFtZTcwOTA3NzE5OQ@@._V1._SX100_SY140_.png"],
      ["Locke", "images/losttv/MV5BMjI1Njk0ODQ3N15BMl5BanBnXkFtZTcwNzUwMjI0Mw@@._V1._SX100_SY140_.png"],
      ["NARRATOR", "images/losttv/zelda5_circ.png"]
    ]
  }
]
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Dec 5, 2016)
 */
public class Screenplay extends XholonWithPorts {
  
  private String roleName = null;
  
  private String sceneLocationRoot = "Universe";
  
  // a JSON string
  private String options = null;
  
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
          case "screenplay": this.setVal(node.getVal_String()); break;
          case "options": this.options = node.getVal_String(); break;
          default: break;
          }
        }
        node = node.getNextSibling();
      }
    }
    this.screenplaybehaviorPostConfigure(sceneLocationRoot, options);
    super.postConfigure();
  }
  
  protected native void screenplaybehaviorPostConfigure(String sceneLocationRoot, String options) /*-{
  var $this = this;
  if (this.text() == null) {
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
  if (this.timewords) {
    timewordsArr = this.timewords.split(",");
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
  
  var addIconsToPlaces = function() {
    if (options) {
      var json = $wnd.JSON.parse(options);
      if (json) {
        outerArr = json["addIconsToPlaces"];
        if (outerArr) {
          for (var i = 0; i < outerArr.length; i++) {
            var innerArr = outerArr[i];
            addIconToPlace(innerArr[0], innerArr[1], innerArr[2]);
          }
        }
      }
    }
    //addIconToPlace("Beach", null, "images/losttv/640px-20100726_Kalamitsi_Beach_Ionian_Sea_Lefkada_island_Greece.png");
    //addIconToPlace("Beach", "Crash site", "images/losttv/MV5BMTQ4ODYyNDAwNF5BMl5BanBnXkFtZTgwODgyNDY2MjE@._V1_.png");
    //addIconToPlace("Front section of the plane", null, "images/losttv/MV5BOGZlZDRhYjMtYWEyYy00MDk3LTkzNTItZDNiMzc2YmZjNGE2XkEyXkFqcGdeQXVyNTY1MDAxMzc@._V1_.png");
    //addIconToPlace("Plane", null, "images/losttv/AAEAAQAAAAAAAAMiAAAAJGVlYTU5Y2YyLWQwMzYtNDlmZS04MDdlLWI0ZjJjZWRhYjk4ZQ.png");
    //addIconToPlace("Jungle", null, "images/losttv/Tioman_Rainforest.png");
    //addIconToPlace("Beach area", null, "images/losttv/mokuleia-beach-2.png");
    //addIconToPlace("Mountain", null, "images/losttv/hawa49879.png");
    //addIconToPlace("Beach area", "Bamboo forest", "images/losttv/MV5BNmQ4YzA3OTktYmIwOS00OTI5LWIyNGUtYzA1Njc0YTVjZmQzXkEyXkFqcGdeQXVyNTY1MDAxMzc@._V1_.png");
    //addIconToPlace("Overgrown grass field", null, "images/losttv/640px-Setaria_verticillata_W_IMG_1085.png");
    //addIconToPlace("Tree trunk", null, "images/losttv/Yellow_birch_trunk.png");
  }

  var addIconToPlace = function(placeRoleName1, placeRoleName2, iconUrl) {
    var place = $this.xpath("ancestor::StorySystem/Story/" + sceneLocationRoot + "/SceneLocation[@roleName='" + placeRoleName1 + "']");
    if (place == null) {return;}
    if (placeRoleName2 != null) {
      place = place.xpath("SceneLocation[@roleName='" + placeRoleName2 + "']");
      if (place == null) {return;}
    }
    var iconStr = "<Icon>" + iconUrl + "</Icon>";
    service.call(-2019, iconStr, place); // ISignal.ACTION_PASTE_MERGE_FROMSTRING = -2019
  }

  var formatPersonName = function(wordArr) {
    // In Lost, I need to manually process the individual person names, because the movie script includes lots of other similar non-name text
    switch (wordArr[0]) {
      case "":
        return "NARRATOR"; // TODO handle this properly
      case "BOONE":
      case "CHARLIE":
      case "CLAIRE":
      //case "FEMALE": //FEMALE VOICE
      case "HURLEY":
      case "JACK":
      case "JIN":
      case "KATE":
      case "LOCKE":
      case "MICHAEL":
      case "ROSE":
      case "SAYID":
      case "SAWYER":
      case "SHANNON":
      case "SUN":
      case "WALT":
        return wordArr[0].capitalize();
      //case "MALE": //MALE VOICE
      case "MAN":
        if (wordArr.length > 1) {
          return "UNKNOWN PERSON";
        }
        break;
      case "PILOT":
        if (wordArr.length > 1) {
          return "UNKNOWN PERSON";
        }
        break;
      case "FLIGHT": // FLIGHT ATTENDANT #1  or #3
        if (wordArr[1] == "ATTENDANT") {
          break;
        }
        else {
          return "UNKNOWN PERSON";
        }
      default:
        return "UNKNOWN PERSON"; // TODO handle this properly
    }
    for (var j = 0; j < wordArr.length; j++) {
      wordArr[j] = wordArr[j].capitalize();
    }
    var pname = wordArr.join(" ");
    return pname;
  }

  var outputPersonNames = function() {
    var iconsObj = null;
    if (options) {
      var json = $wnd.JSON.parse(options);
      if (json) {
        var iconsObj = json["outputPersonNames"];
      }
    }
    var personArr = personXsn.toArray();
    var personXmlStr = "";
    for (var i = 0; i < personArr.length; i++) {
      var wordStr = personArr[i].obj();
      personXmlStr += '<Character roleName="' + wordStr + '">';
      if (iconsObj) {
        var iconName = iconsObj[wordStr];
        if (iconName) {
          personXmlStr += "<Icon>" + iconName + "</Icon>";
        }
      }
      
      // the icons are all originally from https://images-na.ssl-images-amazon.com/images/
      //if (wordStr == "Jack") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMjE2MTUxNTQzM15BMl5BanBnXkFtZTcwODM3NjI5OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Sayid") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTc3OTIzMDAzOF5BMl5BanBnXkFtZTcwOTQ4MDIyMw@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Claire") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTU3NzEwMzY0Ml5BMl5BanBnXkFtZTcwMjU2ODg4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Kate") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTc3NjgxOTAwMV5BMl5BanBnXkFtZTcwNTQ5Nzg4OQ@@._V1_UY98_CR5,0,67,98_AL_.png" + "</Icon>";
      //}
      //else if (wordStr == "Hurley") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTc5NDE2NDEwOF5BMl5BanBnXkFtZTcwMDM4Nzg4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Boone") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMjM0NDY4MjAxM15BMl5BanBnXkFtZTcwNDU3NDk4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Shannon") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTQ1MzAwOTAxMl5BMl5BanBnXkFtZTcwOTk2NDk4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Charlie") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BNDI1OTIyODE0M15BMl5BanBnXkFtZTcwMzA5ODg4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Jin") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTg5MjUyNzkzMl5BMl5BanBnXkFtZTcwODA1ODg4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Sun") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTc1NDM4MDAzOF5BMl5BanBnXkFtZTcwMDc1ODg4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Walt") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BNjExODgyMDU1N15BMl5BanBnXkFtZTcwNzE3NDk4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Michael") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTcyNjU1ODYxM15BMl5BanBnXkFtZTcwMjI1NDk4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Rose") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTQ0MTYyNzM2N15BMl5BanBnXkFtZTcwMTQ4NDk4OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Sawyer") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMTM1NDEyNjcxOV5BMl5BanBnXkFtZTcwMTI4NjcxMw@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Flight Attendant #1") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMjAxMTQwMzY4MV5BMl5BanBnXkFtZTcwOTA3NzE5OQ@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "Locke") {
      //  personXmlStr += "<Icon>" + "images/losttv/MV5BMjI1Njk0ODQ3N15BMl5BanBnXkFtZTcwNzUwMjI0Mw@@._V1._SX100_SY140_.png" + "</Icon>";
      //}
      //else if (wordStr == "NARRATOR") {
      //  personXmlStr += "<Icon>" + "images/losttv/zelda5_circ.png" + "</Icon>";
      //}
      
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

  var outputScenes = function() {
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

  var processElement = function(el) {
    if (!el) {return;}
    switch(el.nodeName) {
    case "B":
      processB(el);
      break;
    default:
      break;
    }
  }

  var processB = function(el) {
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

  var processPerson = function(arr) {
    // the contents of the array is probably the name of a person or other character
    var len = arr.length;
    if (len == 0) {return;}
    // remove abbreviations at end of word arr  (V.O.) (O.S.) (O.C.) others?
    if (len > 1) {
      switch (arr[len-1]) {
        // TODO there may be several of these in sequence  (V.O.) (CONT'D)
        case "(V.O.)":
        case "(O.S.)":
        case "(O.C.)":
        case "(CONT'D)": // or (cont'd)
          arr.pop();
          break;
        default:
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

  var processPlace = function(sceneName, startPos) {
    // create and use a modified sceneName
    var modSceneName = $wnd.xh.movie.makePlaceRoleName(sceneName, startPos, timewordsXsn, true);
    if (modSceneName.length > 0) {
      //var service = $wnd.xh.service("XholonHelperService");
      var placesNode = $this.xpath("ancestor::StorySystem/Story/" + sceneLocationRoot);
      var data = [modSceneName, " - ", "SceneLocation"];
      service.call(-2023, data, placesNode); // ISignal.ACTION_PASTE_MERGE_FROMROLENAMESTRING = -2023
    }
  }

  var processScene = function(slug) {
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

  var processTextNode = function(tn) {
    var text = tn.nodeValue;
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
        sceneObj[cprn].text += 'out caption,anim ' + text.replace(/;/g, ',') + ';\n';
        //sceneObj[cprn].text += 'out caption,speech,anim ' + text.replace(/;/g, ',') + ';\n'; // SPEECH
        sceneObj.seqNum++;
        sceneObj[cprn].prevSeqNum = sceneObj.seqNum;
      }
    }
    else if (sceneObj) {
      sceneObj.anno += text;
    }
  }

  var removeCrLfs = function(str) {
    // replace whitespace (CR, LF, duplicate spaces) with a single space
    // trim white space from front and back
    str = str.replace(/\s+/g, ' ').trim();
    return str;
  }

  var toString = function() {
    return $this.text();
  }

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
        processTextNode(node);
        break;
      default:
        break;
    }
    node = node.nextSibling;
  }

  outputPersonNames();
  outputScenes();
  addIconsToPlaces();
  $wnd.console.log(trimmedScriptText);
  }-*/;

  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("sceneLocationRoot".equals(attrName)) {
      this.sceneLocationRoot = attrVal;
    }
    return 0;
  }

  @Override
  public String toString() {
    return this.getName() + " " + getVal_String();
  }
  
}
