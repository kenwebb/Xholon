/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.ef.program;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.client.GwtEnvironment;

import org.primordion.xholon.base.AbstractScratchNode;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Sprite;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in Scratch 2 format.
 * 
 * To prepare a project that can be uploaded to the Scratch editor:
 * - create a new local project folder, for example "myHelloWorld"
 * - save the exported content to the project folder, in a file called "project.json"
 * - include properly named .png .svg .wav files in the project folder
 *  - the IDs in project.json, and the file names, need to line up with each other
 *  - the md5 values also need to line up
 *   - md5sum 0.png   (on Linux)
 * - create a .zip file from the project folder
 * - change the file extension from ".zip" to ".sb2"
 * - using the online Scratch editor, replace an existing project by selecting "Upload from your computer" in the File menu
 *  - this project must have the same projectID as found in the Xholon-generated JSON file
 * 
 * Notes:
 * - 
 * 
 * TODO:
 * - calculate x,y,z positions for sprites
 *  - use d3 or other layout engine
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu/">Scratch website</a>
 * @see <a href="http://wiki.scratch.mit.edu/wiki/Scratch_File_Format_(2.0)">Scratch File Format (2.0)</a>
 * @since 0.9.1 (Created on December 27, 2015)
 */
@SuppressWarnings("serial")
public class Xholon2Scratch extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/sb2/"; // JSON inside sb2 zip file
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbSprites;
  private StringBuilder sbClones;
  private StringBuilder sbNamesList;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  protected String nameTemplate = "^^C^^^"; // don't include role name
  
  // IDs used in the Scratch file; these values need to be incremented each time they are used
  protected int nextIndexInLibrary = 1;
  
  // default stage sounds values
  protected String ssSoundNameDefault = "pop";
  protected int ssSoundIDDefault = 1;
  protected String ssMd5Default = "83a9787d4cb6f3b7632b4ddfebf74367.wav";
  protected int ssSampleCountDefault = 258;
  protected int ssRateDefault = 11025;
  
  // default stage costumes values  (s)tage (c)ostume
  protected String scCostumeNameDefault = "backdrop1";
  protected int scBaseLayerIDDefault = 3;
  protected String scBaseLayerMD5Default = "739b5e2a2435f6e1ec2993791b423146.png";
  protected int scBitmapResolutionDefault = 1;
  protected int scRotationCenterXDefault = 240;
  protected int scRotationCenterYDefault = 180;
  
  // default stage values
  protected String stagePenLayerMD5Default = "5c81a336fab8be57adc039a8a2b33ca9.png";
  protected int stagePenLayerIDDefault = 0;
  protected int stageTempoBPMDefault = 60;
  protected float stageVideoAlphaDefault = 0.5f;
  
  // default sprite sounds values  spri(t)e (s)ound
  protected String tsSoundNameDefault = "meow";
  protected int tsSoundIDDefault = 0;
  protected String tsMd5Default = "83c36d806dc92327b9e7049a565c6bff.wav";
  protected int tsSampleCountDefault = 18688;
  protected int tsRateDefault = 22050;
  
  // default sprite costumes values  spri(t)e (c)ostume
  protected String tcCostumeNameDefault = "costume1";
  protected int tcBaseLayerIDDefault = 1;
  protected String tcBaseLayerMD5Default = "09dc888b0b7df19f70d81588ae73420e.svg";
  protected int tcBitmapResolutionDefault = 1;
  protected int tcRotationCenterXDefault = 47;
  protected int tcRotationCenterYDefault = 55;
  
  // default info values
  protected String nfProjectIDDefault = "92814753"; // this is an existing project of mine
  protected String nfSwfVersionDefault = "v442";
  protected String nfFlashVersionDefault = "LNX 20,0,0,267";
  protected String nfUserAgentDefault = GwtEnvironment.navUserAgent.replaceAll("/", "\\\\/");
  
  // info counts
  protected int nfScriptCount = 0;
  protected int nfSpriteCount = 0;
  
  // list of unique Xholon class nodes, to avoid duplicates while writing out
  protected Set<IXholonClass> xhClassSet;
  
  /**
   * Constructor.
   */
  public Xholon2Scratch() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".json";
    }
    else {
      this.outFileName = mmFileName;
    }
    this.modelName = modelName;
    this.root = root;
    xhClassSet = new LinkedHashSet<IXholonClass>(); // a Set that maintains the insertion order
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    sbSprites = new StringBuilder();
    sbClones = new StringBuilder();
    sbNamesList = new StringBuilder();
    nextIndexInLibrary = 1;
    nfScriptCount = 0;
    nfSpriteCount = 0;
    makeDefaults();
    writeStage(root);
    writeToTarget(sb.toString(), outFileName, outPath, root);
    if (isPhosphorus()) {
      if (isPhosphorusPlayer()) {
        compileAndRunWithPhosphorusPlayer(sb.toString());
      }
      else {
        int width = -1; //480;
        int height = -1; //360;
        String[] wh = getStageWidthHeight().split(",");
        switch (wh.length) {
        case 2:
          width = Integer.parseInt(wh[0]);
          height = Integer.parseInt(wh[1]);
          break;
        default: break;
        }
        compileAndRunWithPhosphorus(sb.toString(), getSelection(), width, height, isWritePhosphorusCode());
      }
    }
  }
  
  /**
   * Make Scratch default values.
   */
  protected void makeDefaults() {
    String[] stageSoundsDefaultsArr = getStageSoundsDefaults().split(",");
    switch (stageSoundsDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 5: ssRateDefault = Integer.parseInt(stageSoundsDefaultsArr[4]);
      case 4: ssSampleCountDefault = Integer.parseInt(stageSoundsDefaultsArr[3]);
      case 3: ssMd5Default = stageSoundsDefaultsArr[2];
      case 2: ssSoundIDDefault = Integer.parseInt(stageSoundsDefaultsArr[1]);
      case 1: ssSoundNameDefault = stageSoundsDefaultsArr[0];
      default: break;
    }
    
    String[] stageCostumesDefaultsArr = getStageCostumesDefaults().split(",");
    switch (stageCostumesDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 6: scRotationCenterYDefault = Integer.parseInt(stageCostumesDefaultsArr[5]);
      case 5: scRotationCenterXDefault = Integer.parseInt(stageCostumesDefaultsArr[4]);
      case 4: scBitmapResolutionDefault = Integer.parseInt(stageCostumesDefaultsArr[3]);
      case 3: scBaseLayerMD5Default = stageCostumesDefaultsArr[2];
      case 2: scBaseLayerIDDefault = Integer.parseInt(stageCostumesDefaultsArr[1]);
      case 1: scCostumeNameDefault = stageCostumesDefaultsArr[0];
      default: break;
    }
    
    String[] stageDefaultsArr = getStageDefaults().split(",");
    switch (stageDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 4: stageVideoAlphaDefault = Float.parseFloat(stageDefaultsArr[3]);
      case 3: stageTempoBPMDefault = Integer.parseInt(stageDefaultsArr[2]);
      case 2: stagePenLayerIDDefault = Integer.parseInt(stageDefaultsArr[1]);
      case 1: stagePenLayerMD5Default = stageDefaultsArr[0];
      default: break;
    }
    
    String[] spriteSoundsDefaultsArr = getSpriteSoundsDefaults().split(",");
    switch (spriteSoundsDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 5: tsRateDefault = Integer.parseInt(spriteSoundsDefaultsArr[4]);
      case 4: tsSampleCountDefault = Integer.parseInt(spriteSoundsDefaultsArr[3]);
      case 3: tsMd5Default = spriteSoundsDefaultsArr[2];
      case 2: tsSoundIDDefault = Integer.parseInt(spriteSoundsDefaultsArr[1]);
      case 1: tsSoundNameDefault = spriteSoundsDefaultsArr[0];
      default: break;
    }
    
    String[] spriteCostumesDefaultsArr = getSpriteCostumesDefaults().split(",");
    switch (spriteCostumesDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 6: tcRotationCenterYDefault = Integer.parseInt(spriteCostumesDefaultsArr[5]);
      case 5: tcRotationCenterXDefault = Integer.parseInt(spriteCostumesDefaultsArr[4]);
      case 4: tcBitmapResolutionDefault = Integer.parseInt(spriteCostumesDefaultsArr[3]);
      case 3: tcBaseLayerMD5Default = spriteCostumesDefaultsArr[2];
      case 2: tcBaseLayerIDDefault = Integer.parseInt(spriteCostumesDefaultsArr[1]);
      case 1: tcCostumeNameDefault = spriteCostumesDefaultsArr[0];
      default: break;
    }
    
    String[] infoDefaultsArr = getInfoDefaults().split("|");
    switch (infoDefaultsArr.length) {
      // the case statements fall through (no break;)
      //case 4: nfUserAgentDefault = infoDefaultsArr[3];
      case 3: nfFlashVersionDefault = infoDefaultsArr[2];
      case 2: nfSwfVersionDefault = infoDefaultsArr[1];
      case 1: nfProjectIDDefault = infoDefaultsArr[0];
      default: break;
    }
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    // never show state machine nodes
    if (node.getXhcId() == CeStateMachineEntity.StateMachineCE) {return;}
    
    if (node.hasChildNodes()) {
      // TODO write out this non-leaf node as a Scratch Stage costume ?
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
      }
    }
    else {
      // write-out this leaf node as a Scratch Sprite
      IXholonClass nodeIH = node.getXhc();
      if (!xhClassSet.contains(nodeIH)) {
        // write out this node's XholonClass as an invisible Scratch Sprite
        sbSprites.append(writeSprite(node, new StringBuilder()));
        if (!isSprite(node)) {
          xhClassSet.add(nodeIH);
        }
      }
      writeClone(node);
    }
  }
  
  /**
   * Write out this node as a Scratch clone
   * ["append:toList:", "hello_2", "namesList"],
   * ["createCloneOf", "Airplane"]
   */
  protected void writeClone(IXholon node) {
    if (isSprite(node)) {return;}
    String xhcName = node.getXhcName();
    //if ("Stage".equals(xhcName)) {
    if (isStage(node)) {
      // don't write Stage as a Sprite
      return;
    }
    else if (xhcName.endsWith("behavior")) {
      return;
    }
    sbNamesList
    .append("[\"append:toList:\", \"")
    .append(node.getName())
    .append("\", \"namesList\"")
    .append("],\n")
    ;
    sbClones
    .append("[\"createCloneOf\", \"")
    .append(node.getName(nameTemplate))
    .append("\"],\n")
    ;
    
  }
  
  /**
   * Write the single Stage object.
   * @param stageNode A node that may be a Stage node and may contain Stage scripts.
   */
  protected void writeStage(IXholon stageNode) {
    // write sprites to sbSprites
    writeNode(root, 0); // root is level 0
    
    sb
    .append("{\n")
    .append("  \"objName\": \"Stage\",\n")
    .append(writeStageVariables(new StringBuilder()))
    .append(writeLists(new StringBuilder()))
    
    // write Stage scripts, as specified in a Stage node (typically root)
    .append(writeStageScripts(stageNode, new StringBuilder()))
    
    .append(writeStageSounds(new StringBuilder()))
    .append(writeStageCostumes(stageNode, new StringBuilder()))
    .append("  \"currentCostumeIndex\": 0,\n")
    .append("  \"penLayerMD5\": \"").append(stagePenLayerMD5Default).append("\",\n")
    .append("  \"penLayerID\": ").append(stagePenLayerIDDefault).append(",\n")
    .append("  \"tempoBPM\": ").append(stageTempoBPMDefault).append(",\n")
    .append("  \"videoAlpha\": ").append(stageVideoAlphaDefault).append(",\n")
    .append("  \"children\": [")
    ;
    sb.append(sbSprites.toString());
    sb
    //.append(writeSprite(root, new StringBuilder())) // write invisible root sprite with the clone script; NO this is already done by Stage
    .append("],\n")
    .append(writeInfo(new StringBuilder()))
    .append("}")
    ;
  }
  
  protected String writeSprite(IXholon node, StringBuilder sbLocal) {
    String nodeName = node.getName(nameTemplate);
    String xhcName = node.getXhcName();
    //if ("Stage".equals(xhcName)) {
    if (isStage(node)) {
      // don't write Stage as a Sprite
      return "";
    }
    else if (xhcName.endsWith("behavior")) {
      return "";
    }
    if (isSprite(node)) {
      nodeName = node.getRoleName();
      if (nodeName == null) {
        nodeName = node.getName();
      }
    }
    sbLocal
    .append("{\n")
    .append("  \"objName\": \"").append(nodeName).append("\",\n")
    .append(writeSpriteVariables(node, new StringBuilder()))
    .append(writeSpriteScripts(node, new StringBuilder()))
    .append(writeSpriteSounds(new StringBuilder()))
    .append(writeSpriteCostumes(node, new StringBuilder()))
    .append("  \"currentCostumeIndex\": 0,\n")
    .append("  \"scratchX\": 0,\n")
    .append("  \"scratchY\": 0,\n")
    .append("  \"scale\": 1,\n")
    .append("  \"direction\": 90,\n")
    .append("  \"rotationStyle\": \"normal\",\n")
    .append("  \"isDraggable\": false,\n")
    .append("  \"indexInLibrary\": ").append(nextIndexInLibrary++).append(",\n")
    .append("  \"visible\": false,\n") // the sprite is invisible, because it exists only as something to be cloned
    .append("  \"spriteInfo\": {\n")
    .append("  }\n")
    .append("},") // TODO don't writeout "," if this is the last Sprite
    ;
    nfSpriteCount++;
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeStageScripts(IXholon node, StringBuilder sbLocal) {
    if (isStage(node)) {
      // write any scripts contained within the node, if the node is a Stage
      node.sendSyncMessage(AbstractScratchNode.SIGNAL_SCRIPTS_WRITEXHXML_REQ, isWriteXhXmlStr(), this);
      node.sendSyncMessage(AbstractScratchNode.SIGNAL_SCRIPTS_WRITEENGTXT_REQ, isWriteEnglishText(), this);
      // get the Stage's script in Scratch JSON String format
      IMessage msg = node.sendSyncMessage(AbstractScratchNode.SIGNAL_SCRIPTS_SCRATCHJSON_REQ, null, this);
      if (msg != null) {
        String scripts = (String)msg.getData();
        if ((scripts != null) && (scripts.length() > 0)) {
          sbLocal
          .append(scripts)
          .append(",\n");
        }
      }
    }
    else {
      sbLocal
      .append("  \"scripts\": [[25, 25, [[\"whenGreenFlag\"], [\"setVar:to:\", \"spriteIndex\", \"0\"], [\"deleteLine:ofList:\", \"all\", \"namesList\"],\n")
      .append(sbNamesList.toString())
      .append(sbClones.toString())
      .append("]]],\n")
      ;
    }
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeSpriteScripts(IXholon node, StringBuilder sbLocal) {
    if (isSprite(node)) {
      node.sendSyncMessage(Sprite.SIGNAL_SCRIPTS_WRITEXHXML_REQ, isWriteXhXmlStr(), this);
      node.sendSyncMessage(Sprite.SIGNAL_SCRIPTS_WRITEENGTXT_REQ, isWriteEnglishText(), this);
      // get the Sprite's script in Scratch JSON String format
      IMessage msg = node.sendSyncMessage(Sprite.SIGNAL_SCRIPTS_SCRATCHJSON_REQ, null, this);
      if (msg != null) {
        String scripts = (String)msg.getData();
        if (scripts != null) {
          sbLocal
          .append(scripts)
          .append(",\n");
        }
      }
    }
    else if (node == root) {
      sbLocal.append(writeStageScripts(node, new StringBuilder()));
    }
    else {
      sbLocal
      .append("  \"scripts\": [[25, 25, [[\"whenCloned\"], [\"show\"],\n");
      int spriteSizePercentage = getSpriteSizePercentage();
      if ((spriteSizePercentage > 0) && (spriteSizePercentage != 100)) {
        sbLocal
        .append("      [\"setSizeTo:\", ")
        .append(spriteSizePercentage)
        .append("],\n");
      }
      sbLocal
      .append("      [\"changeGraphicEffect:by:\", \"color\", [\"*\", [\"readVariable\", \"spriteIndex\"], 7]],\n")
      .append("      [\"heading:\", [\"*\", [\"readVariable\", \"spriteIndex\"], ")
      .append(getSpriteHeadingMultiplier())
      .append("]],\n")
      .append("      [\"forward:\", [\"*\", [\"readVariable\", \"spriteIndex\"], ")
      .append(getSpriteForwardMultiplier())
      .append("]],\n")
      .append("      [\"heading:\", 90],\n")
      .append("      [\"changeVar:by:\", \"spriteIndex\", 1],\n")
      .append("      [\"setVar:to:\", \"name\", [\"getLine:ofList:\", [\"readVariable\", \"spriteIndex\"], \"namesList\"]]]],\n")
      .append("  [25, 300, [[\"whenClicked\"], [\"say:duration:elapsed:from:\", [\"readVariable\", \"name\"], 2]]]],\n");
      // TODO "whenClicked" only works with touch and not with mouse (on my touch-screen laptop)
    }
    // TODO nfScriptCount should include scripts generated by Sprite.java and Stage.java; but the Scratch site and phsphorus don't care
    nfScriptCount++;
    return sbLocal.toString();
  }
  
  protected String writeStageSounds(StringBuilder sbLocal) {
    if (!this.isShouldShowSounds()) {return "";}
    sbLocal
    .append("  \"sounds\": [");
    writeSound(ssSoundNameDefault, ssSoundIDDefault, ssMd5Default, ssSampleCountDefault, ssRateDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected String writeSpriteSounds(StringBuilder sbLocal) {
    if (!this.isShouldShowSounds()) {return "";}
    sbLocal
    .append("  \"sounds\": [");
    //writeSound("meow", 0, "83c36d806dc92327b9e7049a565c6bff.wav", 18688, 22050, sbLocal);
    writeSound(tsSoundNameDefault, tsSoundIDDefault, tsMd5Default, tsSampleCountDefault, tsRateDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected void writeSound(String soundName, int soundID, String md5, int sampleCount, int rate, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"soundName\": \"").append(soundName).append("\",\n")
    .append("    \"soundID\": ").append(soundID).append(",\n")
    .append("    \"md5\": \"").append(md5).append("\",\n")
    .append("    \"sampleCount\": ").append(sampleCount).append(",\n")
    .append("    \"rate\": ").append(rate).append(",\n")
    .append("    \"format\": \"\"\n")
    .append("  }")
    ;
  }
  
  /**
   * Write Stage costumes.
   */
  protected String writeStageCostumes(IXholon stageNode, StringBuilder sbLocal) {
    String icon = ((IDecoration)stageNode).getIcon();
    if (icon == null) {
      icon = ((IDecoration)stageNode.getXhc()).getIcon();
    }
    if (icon == null) {
      icon = scBaseLayerMD5Default;
    }
    int baseLayerID = scBaseLayerIDDefault;
    sbLocal.append("  \"costumes\": [");
    if (icon.startsWith(IDecoration.ICON_SEPARATOR)) {
      // the Stage has multiple costumes
      String[] iconArr = icon.substring(1).split(IDecoration.ICON_SEPARATOR);
      for (int i = 0; i < iconArr.length; i++) {
        String costumeName = scCostumeNameDefault + baseLayerID;
        writeCostume(costumeName, baseLayerID, iconArr[i], scBitmapResolutionDefault, scRotationCenterXDefault, scRotationCenterYDefault, sbLocal);
        baseLayerID++;
        if (i < iconArr.length-1) {
          sbLocal.append(",\n");
        }
      }
    }
    else {
      // the Stage has only one costume
      writeCostume(scCostumeNameDefault, baseLayerID, icon, scBitmapResolutionDefault, scRotationCenterXDefault, scRotationCenterYDefault, sbLocal);
    }
    sbLocal.append("],\n");
    return sbLocal.toString();
  }
  
  /**
   * Write Sprite costumes.
   */
  protected String writeSpriteCostumes(IXholon spriteNode, StringBuilder sbLocal) {
    String icon = ((IDecoration)spriteNode).getIcon();
    if (icon == null) {
      icon = ((IDecoration)spriteNode.getXhc()).getIcon();
    }
    if (icon == null) {
      icon = tcBaseLayerMD5Default;
    }
    int baseLayerID = tcBaseLayerIDDefault;
    sbLocal.append("  \"costumes\": [");
    if (icon.startsWith(IDecoration.ICON_SEPARATOR)) {
      // the Sprite has multiple costumes
      String[] iconArr = icon.substring(1).split(IDecoration.ICON_SEPARATOR);
      for (int i = 0; i < iconArr.length; i++) {
        String costumeName = tcCostumeNameDefault + baseLayerID;
        //writeCostume("costume1", 1, "09dc888b0b7df19f70d81588ae73420e.svg", 1, 47, 55, sbLocal);
        writeCostume(costumeName, baseLayerID, iconArr[i], tcBitmapResolutionDefault, tcRotationCenterXDefault, tcRotationCenterYDefault, sbLocal);
        baseLayerID++;
        if (i < iconArr.length-1) {
          sbLocal.append(",\n");
        }
      }
    }
    else {
      // the Sprite has only one costume
      writeCostume(tcCostumeNameDefault, baseLayerID, icon, tcBitmapResolutionDefault, tcRotationCenterXDefault, tcRotationCenterYDefault, sbLocal);
    }
    sbLocal.append("],\n");
    return sbLocal.toString();
  }
  
  /**
   * Write one Stage or Sprite costume.
   */
  protected void writeCostume(String costumeName, int baseLayerID, String baseLayerMD5, int bitmapResolution, int rotationCenterX, int rotationCenterY, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"costumeName\": \"").append(costumeName).append("\",\n")
    .append("    \"baseLayerID\": ").append(baseLayerID).append(",\n")
    .append("    \"baseLayerMD5\": \"").append(baseLayerMD5).append("\",\n")
    .append("    \"bitmapResolution\":").append(bitmapResolution).append(",\n") // this may be different for different images
    .append("    \"rotationCenterX\":").append(rotationCenterX).append(",\n")
    .append("    \"rotationCenterY\":").append(rotationCenterY).append("\n")
    .append("  }")
    ;
  }
  
  /**
   * Write Scratch info.
   */
  protected String writeInfo(StringBuilder sbLocal) {
    sbLocal
    .append("  \"info\": {\n")
    .append("    \"videoOn\": false,\n")
    .append("    \"userAgent\": \"").append(nfUserAgentDefault).append("\",\n")
    .append("    \"hasCloudData\": false,\n")
    .append("    \"projectID\": \"").append(nfProjectIDDefault).append("\",\n")
    .append("    \"scriptCount\": ").append(nfScriptCount).append(",\n")
    .append("    \"swfVersion\": \"").append(nfSwfVersionDefault).append("\",\n")
    .append("    \"spriteCount\": ").append(nfSpriteCount).append(",\n")
    .append("    \"flashVersion\": \"").append(nfFlashVersionDefault).append("\"\n")
    .append("  }\n")
    ;
    return sbLocal.toString();
  }
  
  protected String writeStageVariables(StringBuilder sbLocal) {
    sbLocal
    .append("  \"variables\": [{\n")
    .append("    \"name\": \"spriteIndex\",\n")
    .append("    \"value\": 0,\n")
    .append("    \"isPersistent\": false\n")
    .append("  }],\n")
    ;
    return sbLocal.toString();
  }
  
  protected String writeSpriteVariables(IXholon node, StringBuilder sbLocal) {
    if (!isSprite(node)) {
      sbLocal
      .append("  \"variables\": [{\n")
      .append("    \"name\": \"name\",\n")
      .append("    \"value\": 0,\n")
      .append("    \"isPersistent\": false\n")
      .append("  }],\n")
      ;
    }
    return sbLocal.toString();
  }
  
  protected String writeLists(StringBuilder sbLocal) {
    sbLocal
    .append("  \"lists\": [{\n")
    .append("    \"listName\": \"namesList\",\n")
    .append("    \"contents\": [],\n")
    .append("    \"isPersistent\": false,\n")
    // TODO x y width height ?
    .append("    \"visible\": false\n")
    .append("  }],\n")
    ;
    return sbLocal.toString();
  }
  
  /**
   * Each Sprite node (instance of Sprite.java) will likely have its own script, so these nodes should not be built using cloning.
   */
  protected boolean isSprite(IXholon node) {
    return node.getXhc().hasAncestor("Sprite");
  }
  
  /**
   * Each Stage node (instance of Stage.java) will likely have its own script, so these nodes should not be built using cloning.
   */
  protected boolean isStage(IXholon node) {
    return node.getXhc().hasAncestor("Stage");
  }
  
  public String getOutFileName() {
    return outFileName;
  }

  public void setOutFileName(String outFileName) {
    this.outFileName = outFileName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public IXholon getRoot() {
    return root;
  }

  public void setRoot(IXholon root) {
    this.root = root;
  }

  public String getNameTemplate() {
    return nameTemplate;
  }

  public void setNameTemplate(String nameTemplate) {
    this.nameTemplate = nameTemplate;
  }

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.stageSoundsDefaults = "pop,1,83a9787d4cb6f3b7632b4ddfebf74367.wav,258,11025";
    p.stageCostumesDefaults = "backdrop1,3,739b5e2a2435f6e1ec2993791b423146.png,1,240,180";
    p.stageDefaults = "5c81a336fab8be57adc039a8a2b33ca9.png,0,60,0.5";
    p.stageWidthHeight = "480,360";
    p.spriteSoundsDefaults = "meow,0,83c36d806dc92327b9e7049a565c6bff.wav,18688,22050";
    p.spriteCostumesDefaults = "costume1,1,09dc888b0b7df19f70d81588ae73420e.svg,1,47,55";
    p.spriteSizePercentage = 100;
    p.spriteHeadingMultiplier = 1;
    p.spriteForwardMultiplier = 3;
    p.infoDefaults = "92814753|v442|LNX 20,0,0,267";
    p.shouldShowSounds = false;
    p.phosphorus = true;
    p.phosphorusPlayer = false;
    p.selection = "#xhcanvas";
    p.writeXhXmlStr = false;
    p.writePhosphorusCode = false;
    p.writeEnglishText = false;
    this.efParams = p;
  }-*/;

  public native String getStageSoundsDefaults() /*-{return this.efParams.stageSoundsDefaults;}-*/;
  //public native void setStageSoundsDefaults(String stageSoundsDefaults) /*-{this.efParams.stageSoundsDefaults = stageSoundsDefaults;}-*/;

  public native String getStageCostumesDefaults() /*-{return this.efParams.stageCostumesDefaults;}-*/;
  //public native void setStageCostumessDefaults(String stageCostumesDefaults) /*-{this.efParams.stageCostumesDefaults = stageCostumesDefaults;}-*/;

  public native String getStageDefaults() /*-{return this.efParams.stageDefaults;}-*/;
  //public native void setStageDefaults(String stageDefaults) /*-{this.efParams.stageDefaults = stageDefaults;}-*/;

  public native String getStageWidthHeight() /*-{return this.efParams.stageWidthHeight;}-*/;
  //public native void setStageWidthHeight(String stageWidthHeight) /*-{this.efParams.stageWidthHeight = stageWidthHeight;}-*/;

  public native String getSpriteSoundsDefaults() /*-{return this.efParams.spriteSoundsDefaults;}-*/;
  //public native void setSpriteSoundsDefaults(String spriteSoundsDefaults) /*-{this.efParams.spriteSoundsDefaults = spriteSoundsDefaults;}-*/;

  public native String getSpriteCostumesDefaults() /*-{return this.efParams.spriteCostumesDefaults;}-*/;
  //public native void setSpriteCostumessDefaults(String spriteCostumesDefaults) /*-{this.efParams.spriteCostumesDefaults = spriteCostumesDefaults;}-*/;
  
  public native int getSpriteSizePercentage() /*-{return this.efParams.spriteSizePercentage;}-*/;
  //public native void setSpriteSizePercentage(String spriteSizePercentage) /*-{this.efParams.spriteSizePercentage = spriteSizePercentage;}-*/;

  public native int getSpriteHeadingMultiplier() /*-{return this.efParams.spriteHeadingMultiplier;}-*/;
  //public native void setSpriteHeadingMultiplier(String spriteHeadingMultiplier) /*-{this.efParams.spriteHeadingMultiplier = spriteHeadingMultiplier;}-*/;

  public native int getSpriteForwardMultiplier() /*-{return this.efParams.spriteForwardMultiplier;}-*/;
  //public native void setSpriteForwardMultiplier(String spriteForwardMultiplier) /*-{this.efParams.spriteForwardMultiplier = spriteForwardMultiplier;}-*/;

  public native String getInfoDefaults() /*-{return this.efParams.infoDefaults;}-*/;
  //public native void setInfoDefaults(String infoDefaults) /*-{this.efParams.infoDefaults = infoDefaults;}-*/;
  
  /** Whether or not to show Stage and Sprite sounds. */
  public native boolean isShouldShowSounds() /*-{return this.efParams.shouldShowSounds;}-*/;
  //public native void setShouldShowSounds(boolean shouldShowSounds) /*-{this.efParams.shouldShowSounds = shouldShowSounds;}-*/;
  
  /** Whether or not to have phosphorus compile and run the generated Scratch project. */
  public native boolean isPhosphorus() /*-{return this.efParams.phosphorus;}-*/;
  //public native void setPhosphorus(boolean phosphorus) /*-{this.efParams.phosphorus = phosphorus;}-*/;
  
  /** Whether or not to use the phosphorus player. */
  public native boolean isPhosphorusPlayer() /*-{return this.efParams.phosphorusPlayer;}-*/;
  //public native void setPhosphorusPlayer(boolean phosphorusPlayer) /*-{this.efParams.phosphorusPlayer = phosphorusPlayer;}-*/;
  
  public native String getSelection() /*-{return this.efParams.selection;}-*/;
  //public native void setSelection(String selection) /*-{this.efParams.selection = selection;}-*/;
  
  /** Whether or not to request the Sprites to write out their generated xhXmlStr, mostly for debug purposes. */
  public native boolean isWriteXhXmlStr() /*-{return this.efParams.writeXhXmlStr;}-*/;
  //public native void setWriteXhXmlStr(boolean writeXhXmlStr) /*-{this.efParams.writeXhXmlStr = writeXhXmlStr;}-*/;
  
  /** Whether or not to request phosphorus.js to write out its generated JavaScript code, mostly for debug purposes. */
  public native boolean isWritePhosphorusCode() /*-{return this.efParams.writePhosphorusCode;}-*/;
  //public native void setWritePhosphorusCode(boolean writePhosphorusCode) /*-{this.efParams.writePhosphorusCode = writePhosphorusCode;}-*/;
  
  /** Whether or not to request the Sprites to write out their scripts as human-readable English text. */
  public native boolean isWriteEnglishText() /*-{return this.efParams.writeEnglishText;}-*/;
  //public native void setWriteEnglishText(boolean writeEnglishText) /*-{this.efParams.writeEnglishText = writeEnglishText;}-*/;
  
  /**
   * Compile and run the Scratch JSON project using the phosphorus code and the phosphorus player.
   * phosphorus "changeGraphicEffect:by:", "color" is not yet implemented for sprites
   * @param jsonStr 
   * http://phosphorus.github.io/
   * https://github.com/nathan/phosphorus
   */
  protected native void compileAndRunWithPhosphorusPlayer(String jsonStr) /*-{
    var P = $wnd.P;
    if (P) {
      var player = $doc.querySelector('#xhphosphorus');
      if (player) {
        // make the phosphorus player area visible
        player.style.display = "block";
      }
      var json = P.IO.parseJSONish(jsonStr);
      var callback = function(stage) {$wnd.console.log("callback");$wnd.console.log(stage);}
      var request = P.IO.loadJSONProject(json, callback, this);
      if (request) {
        P.player.showProgress(request, function(stage) {
          stage.triggerGreenFlag();
        });
      }
    }
  }-*/;
  
  /**
   * Compile and run the Scratch JSON project using the phosphorus code, but without using the phosphorus player.
   * @param jsonStr 
   * @param selection CSS selection specifying where phosphorus should place the Scratch Stage
   * http://phosphorus.github.io/
   * https://github.com/nathan/phosphorus
   */
  protected native void compileAndRunWithPhosphorus(String jsonStr, String selection, int width, int height, boolean writePhosphorusCode) /*-{
    var P = $wnd.P;
    
    function showError(e) {
      $wnd.console.error(e.stack);
    }
    
    function showProgress(request, loadCallback) {
      request.onload = function(s) {
        var zoom = 1; //stage ? stage.zoom : 1;
        $wnd.stage = stage = s;
        stage.start();
        stage.setZoom(zoom);
        stage.handleError = showError;
        var player = $doc.querySelector(selection); //'#xhcanvas');
        player.style.height = stage.root.style.height;
        player.appendChild(stage.root);
        if (selection == "#xhsvg") {
          // player should now contain an SVG div, followed by a canvas div
          // add styles to allow both to appear together
          var svgDiv = player.firstElementChild;
          if (svgDiv != stage.root) {
            svgDiv.style.position = "absolute";
            svgDiv.style.zIndex = 1;
          }
        }
        stage.focus();
        if (loadCallback) {
          loadCallback(stage);
          loadCallback = null;
        }
      };
      request.onerror = function(e) {
        $wnd.console.log(e);
      };
      request.onprogress = function(e) {
        //$wnd.console.log(e);
      };
    }
    
    if (P) {
      P.xhInit(width,height);
      P.setWritePhosphorusCode(writePhosphorusCode);
      var json = P.IO.parseJSONish(jsonStr);
      var callback = function(stage) {$wnd.console.log("callback");$wnd.console.log(stage);}
      var request = P.IO.loadJSONProject(json, callback, this);
      if (request) {
        showProgress(request, function(stage) {
          stage.triggerGreenFlag();
        });
        
      }
    }
  }-*/;
  
}
