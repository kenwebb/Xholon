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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
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
 * - get sound defaults working OK
 * - check for and use IDecoration values for Xholon node and for XholonClass
 * - each clone should be able to say is node.getName() name
 *  - use a Scratch index variable and a Scratch list
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
  private StringBuilder sbClones;
  private StringBuilder sbNamesList;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  protected String nameTemplate = "^^C^^^"; // don't include role name
  
  // IDs used in the Scratch file; these values need to be incremented each time they are used
  //protected int nextBaseLayerID = 1;
  //protected int nextPenLayerID = 0;
  protected int nextSoundID = 0;
  protected int nextIndexInLibrary = 1;
  
  // default stage sounds values
  protected String ssSoundNameDefault = "pop";
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
  
  // default sprite costumes values  spri(t)e (c)ostume
  protected String tcCostumeNameDefault = "costume1";
  protected int tcBaseLayerIDDefault = 1;
  protected String tcBaseLayerMD5Default = "09dc888b0b7df19f70d81588ae73420e.svg";
  protected int tcBitmapResolutionDefault = 1;
  protected int tcRotationCenterXDefault = 47;
  protected int tcRotationCenterYDefault = 55;
  
  // default info values
  // the following is an existing project of mine
  protected String nfProjectIDDefault = "92814753";
  protected String nfSwfVersionDefault = "v442";
  protected String nfFlashVersionDefault = "LNX 20,0,0,267";
  // Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36
  //protected String nfUserAgentDefault = "Mozilla\\/5.0 (X11; Linux x86_64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/47.0.2526.106 Safari\\/537.36";
  // TODO I may need to double-escape all "/" in the String
  // "Hello/You/There".replaceAll("/", "\\\\/");
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
    sbClones = new StringBuilder();
    sbNamesList = new StringBuilder();
    //nextBaseLayerID = 1;
    //nextPenLayerID = 0;
    nextSoundID = 0;
    nextIndexInLibrary = 1;
    nfScriptCount = 0;
    nfSpriteCount = 0;
    makeDefaults();
    writeStage();
    writeToTarget(sb.toString(), outFileName, outPath, root);
    //root.println(sbClones.toString());
  }
  
  /**
   * Make Scratch default values.
   */
  protected void makeDefaults() {
    String[] stageSoundsDefaultsArr = getStageSoundsDefaults().split(",");
    switch (stageSoundsDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 4: ssRateDefault = Integer.parseInt(stageSoundsDefaultsArr[3]);
      case 3: ssSampleCountDefault = Integer.parseInt(stageSoundsDefaultsArr[2]);
      case 2: ssMd5Default = stageSoundsDefaultsArr[1];
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
        sb.append(writeSprite(node, new StringBuilder()));
        xhClassSet.add(nodeIH);
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
  
  protected void writeStage() {
    sb
    .append("{\n")
    .append("  \"objName\": \"Stage\",\n")
    .append(writeVariables(new StringBuilder()))
    .append(writeLists(new StringBuilder()))
    .append(writeStageSounds(new StringBuilder()))
    .append(writeStageCostumes(new StringBuilder()))
    .append("  \"currentCostumeIndex\": 0,\n")
    .append("  \"penLayerMD5\": \"").append(stagePenLayerMD5Default).append("\",\n")
    .append("  \"penLayerID\": ").append(stagePenLayerIDDefault).append(",\n")
    .append("  \"tempoBPM\": ").append(stageTempoBPMDefault).append(",\n")
    .append("  \"videoAlpha\": ").append(stageVideoAlphaDefault).append(",\n")
    .append("  \"children\": [")
    ;
    // write sprites
    writeNode(root, 0); // root is level 0
    sb
    .append(writeSprite(root, new StringBuilder())) // write invisible root sprite with the clone script
    .append("],\n")
    .append(writeInfo(new StringBuilder()))
    .append("}")
    ;
  }
  
  protected String writeSprite(IXholon node, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("  \"objName\": \"").append(node.getName(nameTemplate)).append("\",\n")
    .append(writeSpriteScripts(node, new StringBuilder()))
    .append(writeSpriteSounds(new StringBuilder()))
    .append(writeSpriteCostumes(new StringBuilder()))
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
    .append("},")
    ;
    nfSpriteCount++;
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeSpriteScripts(IXholon node, StringBuilder sbLocal) {
    if (node == root) {
      sbLocal
      .append("  \"scripts\": [[25, 25, [[\"whenGreenFlag\"], [\"setVar:to:\", \"namesListIndex\", \"0\"], [\"deleteLine:ofList:\", \"all\", \"namesList\"],\n")
      .append(sbNamesList.toString())
      .append(sbClones.toString())
      .append("]]],\n")
      ;
    }
    else {
      sbLocal
      .append("  \"scripts\": [[25, 25, [[\"whenCloned\"], [\"show\"],")
      .append(" [\"changeVar:by:\", \"namesListIndex\", 1],")
      .append(" [\"say:\",")
      .append(" [\"getLine:ofList:\", [\"readVariable\", \"namesListIndex\"], \"namesList\"]")
      .append("]]]],\n");
    }
    nfScriptCount++;
    return sbLocal.toString();
  }
  
  protected String writeStageSounds(StringBuilder sbLocal) {
    if (!this.isShouldShowSounds()) {return "";}
    sbLocal
    .append("  \"sounds\": [");
    writeSound(ssSoundNameDefault, ssMd5Default, ssSampleCountDefault, ssRateDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected String writeSpriteSounds(StringBuilder sbLocal) {
    if (!this.isShouldShowSounds()) {return "";}
    sbLocal
    .append("  \"sounds\": [");
    writeSound("meow", "83c36d806dc92327b9e7049a565c6bff.wav", 18688, 22050, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected void writeSound(String soundName, String md5, int sampleCount, int rate, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"soundName\": \"").append(soundName).append("\",\n")
    .append("    \"soundID\": ").append(nextSoundID++).append(",\n")
    .append("    \"md5\": \"").append(md5).append("\",\n")
    .append("    \"sampleCount\":").append(sampleCount).append(",\n")
    .append("    \"rate\":").append(rate).append(",\n")
    .append("    \"format\": \"\"\n")
    .append("  }")
    ;
  }
  
  protected String writeStageCostumes(StringBuilder sbLocal) {
    sbLocal
    .append("  \"costumes\": [");
    writeCostume(scCostumeNameDefault, scBaseLayerIDDefault, scBaseLayerMD5Default, scBitmapResolutionDefault, scRotationCenterXDefault, scRotationCenterYDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected String writeSpriteCostumes(StringBuilder sbLocal) {
    sbLocal
    .append("  \"costumes\": [");
    //writeCostume("costume1", 1, "09dc888b0b7df19f70d81588ae73420e.svg", 1, 47, 55, sbLocal);
    writeCostume(tcCostumeNameDefault, tcBaseLayerIDDefault, tcBaseLayerMD5Default, tcBitmapResolutionDefault, tcRotationCenterXDefault, tcRotationCenterYDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected void writeCostume(String costumeName, int baseLayerID, String baseLayerMD5, int bitmapResolution, int rotationCenterX, int rotationCenterY, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"costumeName\": \"").append(costumeName).append("\",\n")
    .append("    \"baseLayerID\": ").append(baseLayerID).append(",\n")
    .append("    \"baseLayerMD5\": \"").append(baseLayerMD5).append("\",\n")
    .append("    \"bitmapResolution\":").append(bitmapResolution).append(",\n")
    .append("    \"rotationCenterX\":").append(rotationCenterX).append(",\n")
    .append("    \"rotationCenterY\":").append(rotationCenterY).append("\n")
    .append("  }")
    ;
  }
  
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
  
  protected String writeVariables(StringBuilder sbLocal) {
    sbLocal
    .append("  \"variables\": [{\n")
    .append("    \"name\": \"namesListIndex\",\n")
    .append("    \"value\": 0,\n")
    .append("    \"isPersistent\": false\n")
    .append("  }],\n")
    ;
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
    p.stageSoundsDefaults = "pop,83a9787d4cb6f3b7632b4ddfebf74367.wav,258,11025";
    p.stageCostumesDefaults = "backdrop1,3,739b5e2a2435f6e1ec2993791b423146.png,1,240,180";
    p.stageDefaults = "";
    p.spriteSoundsDefaults = "";
    p.spriteCostumesDefaults = "costume1,1,09dc888b0b7df19f70d81588ae73420e.svg,1,47,55";
    //p.infoDefaults = "92814753|v442|LNX 20,0,0,267|Mozilla\/5.0 (X11; Linux x86_64) AppleWebKit\/537.36 (KHTML, like Gecko) Chrome\/47.0.2526.106 Safari\/537.36";
    p.infoDefaults = "92814753|v442|LNX 20,0,0,267";
    p.shouldShowSounds = false;
    this.efParams = p;
  }-*/;

  public native String getStageSoundsDefaults() /*-{return this.efParams.stageSoundsDefaults;}-*/;
  //public native void setStageSoundsDefaults(String stageSoundsDefaults) /*-{this.efParams.stageSoundsDefaults = stageSoundsDefaults;}-*/;

  public native String getStageCostumesDefaults() /*-{return this.efParams.stageCostumesDefaults;}-*/;
  //public native void setStageCostumessDefaults(String stageCostumesDefaults) /*-{this.efParams.stageCostumesDefaults = stageCostumesDefaults;}-*/;

  public native String getSpriteSoundsDefaults() /*-{return this.efParams.spriteSoundsDefaults;}-*/;
  //public native void setSpriteSoundsDefaults(String spriteSoundsDefaults) /*-{this.efParams.spriteSoundsDefaults = spriteSoundsDefaults;}-*/;

  public native String getSpriteCostumesDefaults() /*-{return this.efParams.spriteCostumesDefaults;}-*/;
  //public native void setSpriteCostumessDefaults(String spriteCostumesDefaults) /*-{this.efParams.spriteCostumesDefaults = spriteCostumesDefaults;}-*/;

  public native String getInfoDefaults() /*-{return this.efParams.infoDefaults;}-*/;
  //public native void setInfoDefaultsDefaults(String infoDefaults) /*-{this.efParams.infoDefaults = infoDefaults;}-*/;
  
  /** Whether or not to show Stage and Sprite sounds. */
  public native boolean isShouldShowSounds() /*-{return this.efParams.shouldShowSounds;}-*/;
  //public native void setShouldShowSounds(boolean shouldShowSounds) /*-{this.efParams.shouldShowSounds = shouldShowSounds;}-*/;
  
}
