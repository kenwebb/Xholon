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
  "xposition", "x position", "UNKNOWN", "x position", // an ellipse variable
  "yposition", "y position", "UNKNOWN", "y position", // an ellipse variable
  "direction", "direction", "UNKNOWN", "direction", // an ellipse variable
  
  // Looks
  "sayforsecs", "say:duration:elapsed:from:", "doSayFor", "Say _ for _ seconds.",
  "say", "say:", "bubble", "Say _.",
  "thinkforsecs", "think:duration:elapsed:from:", "doThinkFor", "Think _ for _ seconds.",
  "think", "think:", "doThink", "Think _.",
  "show", "show", "show", "Show yourself.",
  "hide", "hide", "hide", "Hide yourself.",
  "switchcostumeto", "lookLike:", "doSwitchToCostume", "Switch costume to _.",
  "nextcostume", "nextCostume", "doWearNextCostume", "Next costume.",
  //"switchbackdropto", "startScene", "UNKNOWN", "Switch backdrop to _.",
  //"changeeffectby", "changeGraphicEffect:by:", "changeEffect", "Change _ effect by _.",
  //"seteffectto", "setGraphicEffect:to:", "setEffect", "Set _ effect to _.",
  //"cleargraphiceffects", "filterReset", "clearEffects", "Clear graphic effects.",
  "changesizeby", "changeSizeBy:", "changeScale", "Change size by _.",
  "setsizeto%", "setSizeTo:", "setScale", "Set size to _ %.",
  "gotofront", "comeToFront", "comeToFront", "Go to front.",
  "gobacklayers", "goBackByLayers:", "goBack", "Go back _ layers.",
  "color", "color", "UNKNOWN", "color", // a rectangle variable
  
  // Sound
  /*
  "playsound", "playSound:", "playSound", "Play sound _.",
  "playsounduntildone", "doPlaySoundAndWait", "doPlaySoundUntilDone", "Play sound _ until done.",
  "stopallsounds", "stopAllSounds", "doStopAllSounds", "Stop all sounds.",
  "playdrumforbeats", "playDrum", "UNKNOWN", "Play drum _ for _ beats.",
  "restforbeats", "rest:elapsed:from:", "doRest", "Rest for _ beats.",
  "playnoteforbeats", "noteOn:duration:elapsed:from:", "doPlayNote", "Play note _ for _ beats.",
  "setinstrumentto", "instrument:", "UNKNOWN", "Set instrument to _.",
  "changevolumeby", "changeVolumeBy:", "UNKNOWN", "Change volume by _.",
  "setvolumeto%", "setVolumeTo:", "UNKNOWN", "Set volume to _ %.",
  "changetempoby", "changeTempoBy:", "doChangeTempo", "Change tempo by _.",
  "settempotobpm", "setTempoTo:", "doSetTempo", "Set tempo to _ bpm.",
  */
  
  // Pen
  //"clear", "clearPenTrails", "clear", "Clear.",
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
  //"whengreenflagclicked", "whenGreenFlag", "receiveGo", "When green flag clicked:",
  //"whenkeypressed", "whenKeyPressed", "receiveKey", ".",
  "whenthisspriteclicked", "whenClicked", "UNKNOWN", ".",
  //"whenbackdropswitchesto", "whenSceneStarts", "UNKNOWN", ".",
  //"when>", "whenSensorGreaterThan", "UNKNOWN", ".",
  //"whenIreceive", "whenIReceive", "receiveMessage", "When I receive _:",
  //"broadcast", "broadcast:", "doBroadcast", "Broadcast _.",
  //"broadcastandwait", "doBroadcastAndWait", "doBroadcastAndWait", ".",
  
  // Control
  //"waitsecs", "wait:elapsed:from:", "doWait", "Wait _ seconds.",
  //"repeat", "doRepeat", "doRepeat", "Repeat _ times:",
  //"forever", "doForever", "doForever", "Forever:",
  //"ifthen", "doIf", "doIf", "If _ then _",
  //"ifthenelse", "doIfElse", "doIfElse", "If _ then _ else _",
  //"waituntil", "doWaitUntil", "doWaitUntil", "Wait until _.",
  //"repeatuntil", "doUntil", "doUntil", "Repeat until _:",
  //"stop", "stopScripts", "doStopThis", "Stop all.",
  "whenIstartasaclone", "whenCloned", "receiveOnClone", "When I start as a clone:",
  //"createcloneof", "createCloneOf", "createClone", "Create clone of _.",
  "deletethisclone", "deleteClone", "removeClone", "Delete this clone.",
  
  // Sensing
  "touching?", "touching:", "reportTouchingObject", "touching _?", // angle
  "touchingcolor?", "touchingColor:", "reportTouchingColor", "touching color _?", // angle
  "coloristouching?", "color:see:", "reportColorIsTouchingColor", "color _ is touching _?", // angle
  "distanceto", "distanceTo:", "reportDistanceTo", "distance to _" // ellipse
  /*
  "askandwait", "doAsk", "doAsk", "Ask _ and wait.", // block
  "keypressed?", "keyPressed:", "reportKeyPressed", "key _ pressed?", // angle
  "mousedown?", "mousePressed", "reportMouseDown", "mouse down?", // angle
  "mousex", "mouseX", "reportMouseX", "mouse X", // ellipse
  "mousey", "mouseY", "reportMouseY", "mouse y", // ellipse
  "turnvideo", "setVideoState", "UNKNOWN", "Turn video _.",
  "setvideotransparencyto%", "setVideoTransparency", "UNKNOWN", "Set video transparency to _ %.",
  "resettimer", "timerReset", "doResetTimer", "Reset timer.",
  "of", "getAttribute:of:", "reportAttributeOf", "_ of _",
  "dayssince2000", "timestamp", "UNKNOWN", "days since 2000",
  "username", "getUserName", "UNKNOWN", "username"
  */
  
  // Operators
  /*
  "plus", "+", "reportSum", "_ + _",
  "minus", "-", "reportDifference", "_ - _",
  "times", "*", "reportProduct", "_ * _",
  "dividedby", "/", "reportQuotient", "_ / _",
  "pickrandomto", "randomFrom:to:", "reportRandom", "pick random _ to _",
  "lt", "<", "reportLessThan", "_ < _",
  "eq", "=", "reportEquals", "_ = _",
  "gt", ">", "reportGreaterThan", "_ > _",
  "and", "&", "reportAnd", "_ and _",
  "or", "|", "reportOr", "_ or _",
  "not", "not", "reportNot", "not _",
  "join", "concatenate:with:", "reportJoinWords", "join _ _",
  "letterof", "letter:of:", "reportLetter", "letter _ of _",
  "lengthof", "stringLength:", "reportStringSize", "length of _",
  "mod", "%", "reportModulus", "_ mod _",
  "round", "rounded", "reportRound", "round _",
  //"of", "computeFunction:of:", "UNKNOWN", ".",
  */
  
  // More Blocks
  //"define", "procDef", "UNKNOWN", "Define _:",
  
  // Xholon
  /*
  "consolelog", "consolelog", "consolelog", "consolelog _.",
  "xhprint", "xhprint", "xhprint", "xhprint _.",
  "xhprintln", "xhprintln", "xhprintln", "xhprintln _."
  */
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
