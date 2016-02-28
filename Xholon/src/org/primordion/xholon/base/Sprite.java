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

package org.primordion.xholon.base;

/**
 * Sprite as understood in Scratch and Snap.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu">Scratch website</a>
 * @see <a href="http://snap.berkeley.edu">Snap website</a>
 * @since 0.9.1 (Created on January 15, 2016)
 */
public class Sprite extends AbstractScratchNode {
  
  /**
   * These blocks are specific to Sprites.
   */
  protected String[] spriteBlockNameArr = {
  // naiveName, scratchName, snapName, EnglishSentenceOrPhrase
  // Motion
  "movesteps", "forward:", "forward", "Move forward _ steps.",
  "turnrightdegrees", "turnRight:", "turn", "Turn right _ degrees.",
  "turnleftdegrees", "turnLeft:", "turnLeft", "Turn left _ degrees.",
  "pointindirection", "heading:", "setHeading", "Point in direction _.",
  "pointtowards", "pointTowards:", "doFaceTowards", "Point towards _.",
  "gotoxy", "gotoX:y:", "gotoXY", "Go to x _ y _.",
  "goto", "gotoSpriteOrMouse:", "doGotoObject", "Go to _.",
  "glidesecstoxy", "glideSecs:toX:y:elapsed:from:", "doGlide", "Glide _ seconds to x _ y _.",
  "changexby", "changeXposBy:", "changeXPosition", "Change x by _.",
  "setxto", "xpos:", "setXPosition", "Set x to _.",
  "changeyby", "changeYposBy:", "changeYPosition", "Change y by _.",
  "setyto", "ypos:", "setYPosition", "Set y to _.",
  "ifonedge,bounce", "bounceOffEdge", "bounceOffEdge", "If on edge, bounce.",
  "setrotationstyle", "setRotationStyle", "UNKNOWN", "Set rotation style _.",
  "mouse-pointer", "_mouse_", "UNKNOWN", "mouse-pointer", // a rectangle variable
  "left-right", "left-right", "UNKNOWN", "left-right", // a rectangle variable
  "xposition", "xpos", "xPosition", "x position", // an ellipse variable
  "yposition", "ypos", "yPosition", "y position", // an ellipse variable
  "direction", "direction", "direction", "direction", // an ellipse variable
  
  // Looks
  "sayforsecs", "say:duration:elapsed:from:", "doSayFor", "Say _ for _ seconds.",
  "say", "say:", "bubble", "Say _.",
  "thinkforsecs", "think:duration:elapsed:from:", "doThinkFor", "Think _ for _ seconds.",
  "think", "think:", "doThink", "Think _.",
  "show", "show", "show", "Show yourself.",
  "hide", "hide", "hide", "Hide yourself.",
  "switchcostumeto", "lookLike:", "doSwitchToCostume", "Switch costume to _.",
  "nextcostume", "nextCostume", "doWearNextCostume", "Next costume.",
  "changesizeby", "changeSizeBy:", "changeScale", "Change size by _.",
  "setsizeto%", "setSizeTo:", "setScale", "Set size to _ %.",
  "gotofront", "comeToFront", "comeToFront", "Go to front.",
  "gobacklayers", "goBackByLayers:", "goBack", "Go back _ layers.",
  "color", "color", "UNKNOWN", "color", // a rectangle variable
  
  // Sound
  
  // Pen
  "stamp", "stampCostume", "doStamp", "Stamp.",
  "pendown", "putPenDown", "down", "Pen down.",
  "penup", "putPenUp", "up", "Pen up.",
  // if using "setPenHueTo:", the color must be a number from 0 to 199 in rainbow order from red to purple, so 150 is a bluish-purpleish
  // if using "penColor:", the color must be a decimal rgba value, for example 2858785 is a shade of green
  //"setpencolorto", "penColor:", "setColor", // set pen color to [COLOR]
  "changepencolorby", "changePenHueBy:", "changeHue", "Change pen color by _.",
  "setpencolorto", "setPenHueTo:", "setHue", "Set pen color to _.", // set pen color to (NUMBER)  ex: set pen color to (150)
  "changepenshadeby", "changePenShadeBy:", "changeBrightness", "Change pen shade by _.",
  "setpenshadeto", "setPenShadeTo:", "setBrightness", "Set pen shade to _.",
  "changepensizeby", "changePenSizeBy:", "changeSize", "Change pen size by _.",
  "setpensizeto", "penSize:", "setSize", "Set pen size to _.",
  
  // Data
  "setto", "setVar:to:", "doSetVar", "Set _ to _.",
  "changeby", "changeVar:by:", "doChangeVar", "Change _ by _.",
  "showvariable", "showVariable", "doShowVar", "Show variable _.",
  "hidevariable", "hideVariable", "doHideVar", "Hide variable _.",
  "addto", "append:toList:", "doAddToList", "Add _ to _.",
  "deleteof", "deleteLine:ofList:", "doDeleteFromList", "Delete _ of _.",
  "insertatof", "insert:at:ofList:", "doInsertInList", "Insert _ at _ of _.",
  "replaceitemofwith", "setLine:ofList:to:", "doReplaceInList", "Replace item _ of _ with _.",
  "itemof", "getLine:ofList:", "reportListItem", "item _ of _",
  "lengthof", "lineCountOfList:", "reportListLength", "length of _.",
  "contains?", "list:contains:", "reportListContainsItem", "_ contains _?",
  "showlist", "showList:", "UNKNOWN", "Show list _.",
  "hidelist", "hideList:", "UNKNOWN", "Hide list _.",
  
  // Events
  "whenthisspriteclicked", "whenClicked", "receiveInteraction", ".",
  
  // Control
  "whenIstartasaclone", "whenCloned", "receiveOnClone", "When I start as a clone:",
  "deletethisclone", "deleteClone", "removeClone", "Delete this clone.",
  
  // Sensing
  "touching?", "touching:", "reportTouchingObject", "touching _?", // angle
  "touchingcolor?", "touchingColor:", "reportTouchingColor", "touching color _?", // angle
  "coloristouching?", "color:see:", "reportColorIsTouchingColor", "color _ is touching _?", // angle
  "distanceto", "distanceTo:", "reportDistanceTo", "distance to _" // ellipse
  
  // Operators
  
  // More Blocks
  
  // Xholon
  }; // end spriteBlockNameArr
  
  /**
   * Constructor.
   */
  public Sprite() {}
  
  /**
   * Make the Sprite- or Stage-specific array of block names.
   * Combine spriteBlockNameArr and blockNameArr ito one array.
   */
  protected String[] makeBlockNameArr() {
    int len = blockNameArr.length + spriteBlockNameArr.length;
    String[] arr = new String[len];
    int arrIx = 0;
    int i;
    for (i = 0; i < blockNameArr.length; i++) {
      arr[arrIx++] = blockNameArr[i];
    }
    for (i = 0; i < spriteBlockNameArr.length; i++) {
      arr[arrIx++] = spriteBlockNameArr[i];
    }
    return arr;
  }
  
}
